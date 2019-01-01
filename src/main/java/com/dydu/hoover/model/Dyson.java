package com.dydu.hoover.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Class representing the hoover with its current position in the room,
 * the direction taken from last move to current position
 * and the list of all moves done
 */
public class Dyson {

    private Position currentPosition;

    private int currentDirection;

    private List<Position> moves;

    public Dyson() {
        this.currentDirection = PathManager.UP;
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
        PathManager pathFinder = new PathManager(room);

        //set the hoover on random position and clean this position
        Position randomPosition = room.getRandomPosition();
        move(room, new Path(randomPosition));

        while(!room.cleaningDone()) {
            notCleanedPositions = currentPosition.getPositionsAround(nbLines, nbColumns, notCleanedPredicate);
            //there is no positions to clean which are directly accessible,
            //hoover moves to the closest position to clean
            if (notCleanedPositions.isEmpty()) {
                move(room, pathFinder.shortestPathToNextPosition(currentPosition));
                currentDirection = pathFinder.getNewDirection(moves.get(moves.size() - 2),
                        currentPosition);
            } else {
                //we choose position to clean according the direction, if possible
                nextPosition = pathFinder.getPosition(currentPosition, notCleanedPositions, currentDirection);
                //if not possible we choose the first one and set new direction
                if(nextPosition == null) {
                    nextPosition = notCleanedPositions.get(0);
                    currentDirection = pathFinder.getNewDirection(currentPosition, nextPosition);
                }
                move(room, new Path(nextPosition));
            }
        }
    }


    /**
     * Move the hover along the path, last one is the position to clean
     */
    public void move(Room room, Path path) {
        LinkedList<Position> positions = path.getPositions();
        moves.addAll(positions);

        Position positionToClean = positions.getLast();
        currentPosition = positionToClean;
        room.cleanPosition(positionToClean);
    }

    public List<Position> getMoves() {
        return moves;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }
}

