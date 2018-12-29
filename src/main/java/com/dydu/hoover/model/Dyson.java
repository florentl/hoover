package com.dydu.hoover.model;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class representing the hoover with its current position in the room,
 * the direction taken from last move to current position
 * and the list of all moves done
 */
public class Dyson {

    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    private Position currentPosition;

    private int currentDirection;

    private List<Position> moves;

    public Dyson() {
        this.currentDirection = UP;
        this.moves = new ArrayList<>();
    }

    /**
     * Hoover clean method.
     * Try to keep one direction whenever it's possible.
     * When hoover is surrounded by wall or already visited positions, it will move
     * to closest unvisited position
     */
    public void clean(Room room) {
        int nbLines = room.getNbLines();
        int nbColumns = room.getNbColumns();
        List<Position> notCleanedPositions;
        Position nextPosition;
        Predicate<Position> notCleanedPredicate = p -> Room.NOT_CLEANED.equals(room.getPositionValue(p));

        //set the hoover on random position and clean this position
        currentPosition = room.getRandomPosition();
        moveAndClean(room, currentPosition);

        while(!room.cleaningDone()) {
            notCleanedPositions = currentPosition.getPositionsAround(nbLines, nbColumns, notCleanedPredicate);
            //there is no positions to clean which are directly accessible,
            //hoover find the closest position to clean and move to it
            if (notCleanedPositions.isEmpty()) {
                moveAlongPath(shortestPathToNextPosition(currentPosition, room), room);
            } else {
                //we choose position to clean according the direction, if possible
                nextPosition = getPosition(notCleanedPositions, currentDirection);
                //if not possible we choose the first one and set new direction
                if(nextPosition == null) {
                    nextPosition = notCleanedPositions.get(0);
                    setNewDirection(nextPosition);
                }
                moveAndClean(room, nextPosition);
            }
        }
    }


    /**
     * Find a position in the list corresponding to the given direction
     */
    public Position getPosition(List<Position> positions, int direction) {

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
     * Setting the new direction according the position given in parameter
     */
    public void setNewDirection(Position p) {
        int lineDelta = currentPosition.getLine() - p.getLine();
        int columnDelta = currentPosition.getColumn() - p.getColumn();

        switch(lineDelta) {
            case 0 :
                switch(columnDelta) {
                    case 1 :
                        currentDirection = LEFT;
                        break;
                    case -1 :
                        currentDirection = RIGHT;
                        break;
                }
                break;
            case 1 :
                currentDirection = UP;
                break;
            case -1 :
                currentDirection = DOWN;
                break;
        }
    }

    /**
     * Hoover moves to position p and clean it
     */
    private void moveAndClean(Room room, Position p) {
        moves.add(p);
        room.cleanPosition(p);
        currentPosition = p;
    }

    /**
     * Move the hover along the path, first position in the path is the current hover position,
     * last one is the position to clean
     */
    public void moveAlongPath(Path path, Room room) {
        LinkedList<Position> positions = path.getPositions();

        //first position is the current, already recorded in moves
        positions.remove(0);

        //last position is the one to clean
        Position positionToClean = positions.getLast();
        positions.remove(positionToClean);

        //recording all hoover moves
        moves.addAll(positions);

        //set new direction
        currentPosition = positions.getLast();
        setNewDirection(positionToClean);

        //finally move and clean position
        moveAndClean(room, positionToClean);
    }


    /**
     * Find the shortest path from start position to the next position to clean
     */
    public Path shortestPathToNextPosition(Position start, Room room) {

        final List<Path> pathes = new ArrayList<>();
        boolean notCleanedFound = false;
        int nbLines = room.getNbLines();
        int nbColumns = room.getNbColumns();
        Predicate<Position> notWallPredicate = p -> !Room.WALL.equals(room.getPositionValue(p));
        Predicate<Path> pathWithNotCleanedPredicate = path -> Room.NOT_CLEANED.equals(
                room.getPositionValue(path.getPositions().getLast()));

        //pathes are created from start position
        start.getPositionsAround(nbLines, nbColumns, notWallPredicate)
                .forEach(p -> pathes.add(new Path(start)));

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


    public List<Position> getMoves() {
        return moves;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }
}

