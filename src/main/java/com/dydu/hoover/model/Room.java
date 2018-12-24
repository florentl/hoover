package com.dydu.hoover.model;

import com.dydu.hoover.exceptions.InvalidRoomContentException;
import com.dydu.hoover.exceptions.InvalidRoomStructureException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Room model, the room is stored in a String[][] matrix
 */
public class Room {

    public static final String NOT_CLEANED = " ";
    public static final String CLEANED = "C";
    public static final String WALL = "M";

    private String[][] roomMatrix;
    private int nbLines;
    private int nbColumns;
    private Set<Position> accessiblePositions;

    public Room(String[][] matrix) {
        roomMatrix = matrix;
        nbLines =  matrix.length;
        nbColumns = matrix.length >0 ? matrix[0].length : 0;
        accessiblePositions = new HashSet<>();
    }

    /**
     * Return all non wall position within the room
     * @return
     */
    public List<Position> getPositionsToClean() {

        List<Position> positions = new ArrayList<>();
        IntStream.range(0,nbLines).forEach(
                line -> IntStream.range(0,nbColumns).forEach (
                        col -> {
                            if(NOT_CLEANED.equals(roomMatrix[line][col])) {
                                positions.add(new Position(line,col));
                            }
                        })
        );

        return positions;
    }

    /**
     * Return a random non wall position within the room
     * @return
     */
    public Position getRandomPosition() {
        List<Position> positionsToClean = getPositionsToClean();
        return positionsToClean.get(new Random().nextInt(positionsToClean.size()));
    }

    /**
     * Tells if the room cleaning is over
     * @return
     */
    public boolean cleaningDone() {
        return Arrays.stream(roomMatrix)
                .noneMatch(row -> Arrays.asList(row).contains(NOT_CLEANED));
    }


    /**
     *
     * Check if room matrix is well formed, with only reachable positions,
     * and contains only allowed characters
     *
     * @return
     */
    public boolean roomCheck() throws InvalidRoomStructureException, InvalidRoomContentException {

        List<String> allowed = Arrays.asList(WALL, NOT_CLEANED);

        for(String[] row : roomMatrix) {
            if(row.length != nbColumns)  {
                throw new InvalidRoomStructureException("Wrong room structure, all lines must have same length !");
            }

            if(Arrays.stream(row).anyMatch(s -> !allowed.contains(s))) {
                throw new InvalidRoomContentException("Unauthorized characters found in room content !");
            }
        }

        Set<Position> positions = new HashSet<>();
        positions.add(getRandomPosition());
        if(!isAllPositionsAccessible(positions)) {
            throw new InvalidRoomStructureException("Wrong room structure : some positions are not accessible by the hoover !");
        }

        return true;
    }

    /**
     * Check if all positions are accessible in the room, recursively, using contamination algorithm
     * @param positions
     * @return
     */
    public boolean isAllPositionsAccessible(Set<Position> positions) {

        if(positions.isEmpty()) {
            return accessiblePositions.size() == getPositionsToClean().size();
        }

        accessiblePositions.addAll(positions);
        Set<Position> nextStepPositions = new HashSet<>();
        for(Position position : positions) {
            nextStepPositions.addAll(
                    position.getPositionsAround(nbLines, nbColumns)
                    .stream()
                    .filter(p -> !accessiblePositions.contains(p) &&
                            !WALL.equals(roomMatrix[p.getLine()][p.getColumn()]))
                    .collect(Collectors.toList())
            );
        }
        return isAllPositionsAccessible(nextStepPositions);
    }

}
