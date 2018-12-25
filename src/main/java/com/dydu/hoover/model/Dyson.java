package com.dydu.hoover.model;

import java.util.ArrayList;
import java.util.List;
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
     * Hoover clean method. Try to keep one direction whenever it's possible.
     * When hoover is surrounded by wall or already visited positions, it will move
     * to closest unvisited position
     * @param room
     */
    public void clean(Room room) {
        int nbLines = room.getNbLines();
        int nbColumns = room.getNbColumns();
        List<Position> uncleanedPositions;
        Position nextPosition = null;

        //set the hoover on random position and clean this position
        moveAndClean(room, currentPosition);

        while(!room.cleaningDone()) {
            uncleanedPositions = currentPosition.getPositionsAround(nbLines, nbColumns)
                    .stream()
                    .filter(p -> Room.NOT_CLEANED.equals(room.getPositionValue(p)))
                    .collect(Collectors.toList());
            if (uncleanedPositions.isEmpty()) {
                //hover moves to closest uncleaned position
            } else {
                //we choose position to clean according the direction, if possible
                nextPosition = getPosition(uncleanedPositions, currentDirection);
                if(nextPosition == null) {
                    nextPosition = uncleanedPositions.get(0);
                    setNewDirection(nextPosition);
                }
            }
            moveAndClean(room, nextPosition);
        }

    }


    /**
     * Find a position in the list corresponding to the given direction
     * @param positions
     * @param direction
     * @return
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
                .filter(predicate)
                .findAny()
                .orElse(null);
    }

    /**
     * Setting the new direction according the position given in parameter
     * @param p
     * @return
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
     * @param room
     * @param p
     */
    public void moveAndClean(Room room, Position p) {
        moves.add(p);
        room.cleanPosition(p);
        currentPosition = p;
    }

    public List<Position> getMoves() {
        return moves;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }
}

