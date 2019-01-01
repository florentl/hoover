package com.dydu.hoover.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class in charge of move management
 */

public class PathManager {

    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    private Room room;

    public PathManager() {}

    public PathManager(Room room) {
        this.room = room;
    }

    /**
     * Find a position in the list corresponding to the given direction
     */
    public Position getPosition(Position currentPosition, List<Position> positions, int direction) {

        Predicate<Position> predicate = null;
        switch(direction) {
            case UP:
                predicate = p -> p.getLine() == (currentPosition.getLine() - 1);
                break;

            case DOWN:
                predicate = p -> p.getLine() == (currentPosition.getLine() + 1);
                break;

            case RIGHT:
                predicate = p -> p.getColumn() == (currentPosition.getColumn() + 1);
                break;

            case LEFT:
                predicate = p -> p.getColumn() == (currentPosition.getColumn() - 1);
                break;
        }

        return positions
                .stream()
                .filter(Objects.requireNonNull(predicate))
                .findAny()
                .orElse(null);
    }


    /**
     * New direction according the two positions given in parameter
     */
    public int getNewDirection(Position positionFrom, Position positionTo) {
        int lineDelta = positionFrom.getLine() - positionTo.getLine();
        int columnDelta = positionFrom.getColumn() - positionTo.getColumn();
        int direction = 0;
        switch(lineDelta) {
            case 0 :
                switch(columnDelta) {
                    case 1 :
                        direction = LEFT;
                        break;
                    case -1 :
                        direction = RIGHT;
                        break;
                }
                break;
            case 1 :
                direction = UP;
                break;
            case -1 :
                direction = DOWN;
                break;
        }
        return direction;
    }


    /**
     * Find the shortest path from start position to the next position to clean in the room
     */
    public Path shortestPathToNextPosition(Position start) {

        final List<Path> pathes = new ArrayList<>();
        boolean notCleanedFound = false;
        int nbLines = room.getNbLines();
        int nbColumns = room.getNbColumns();
        Predicate<Position> notWallPredicate = p -> !Room.WALL.equals(room.getPositionValue(p));
        Predicate<Path> pathWithNotCleanedPredicate = path -> Room.NOT_CLEANED.equals(
                room.getPositionValue(path.getPositions().getLast()));

        //pathes are created from positions around position
        start.getPositionsAround(nbLines, nbColumns, notWallPredicate)
                .forEach(p -> pathes.add(new Path(p)));

        while (!notCleanedFound) {
            List<Path> additionalPathes = new ArrayList<>();
            pathes.forEach(path -> {
                //all positions around last position in path which are not already in the path,
                //as we don't want to go backward
                List<Position> positionsAround = path.getPositions()
                        .getLast()
                        .getPositionsAround(nbLines, nbColumns,
                                notWallPredicate.and(p -> !path.getPositions().contains(p)));
                //position surrounded by walls : it's a dead end
                if(positionsAround.isEmpty()) {
                    path.setDeadEnd(true);
                } else {
                    //for each position p around : one position will be added to the current path
                    //and for the others new pathes will be created, based on the current, with position p
                    //in last position
                    Position firstPositionAround = positionsAround.remove(0);
                    positionsAround.forEach(p -> {
                        Path additionalPath = new Path(path);
                        additionalPath.getPositions().add(p);
                        additionalPathes.add(additionalPath);
                    });
                    path.getPositions().add(firstPositionAround);
                }
            });
            pathes.addAll(additionalPathes);
            //we keep just one path by last position, and we remove dead ends
            cleanPathes(pathes);
            notCleanedFound = pathes
                    .stream()
                    .anyMatch(pathWithNotCleanedPredicate);
        }

        return pathes
                .stream()
                .filter(pathWithNotCleanedPredicate)
                .min(Comparator.comparing(Path::getLength))
                .get();
    }

    /**
     * Remove dead ends paths.
     * Pathes leading to same position are filtered to keep the shortest one
     */
    public void cleanPathes(List<Path> pathes) {
        List<Path> filteredList = new ArrayList<>();
        pathes.stream()
                .filter(p -> !p.isDeadEnd())
                .collect(Collectors.groupingBy(p -> p.getPositions().getLast()))
                .forEach((key,value) -> filteredList.add(
                        value.stream()
                                .min(Comparator.comparing(Path::getLength))
                                .get())
                );

        pathes.clear();
        pathes.addAll(filteredList);
    }
}
