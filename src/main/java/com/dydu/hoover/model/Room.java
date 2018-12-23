package com.dydu.hoover.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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

    public Room(String[][] matrix) {
        roomMatrix = matrix;
        nbLines =  matrix.length;
        nbColumns = matrix.length >0 ? matrix[0].length : 0;
    }

    /**
     * Return a random non wall position within the room
     * @return
     */
    public Position getRandomPosition() {

        List<Position> emptyPositions = new ArrayList<>();

        IntStream.range(0,nbLines).forEach(
                line -> IntStream.range(0,nbColumns).forEach (
                        col -> {
                            if(NOT_CLEANED.equals(roomMatrix[line][col])) {
                                emptyPositions.add(new Position(line,col));
                            }
                        })
        );

        return emptyPositions.get(new Random().nextInt(emptyPositions.size()));
    }

    /**
     * Tells if the room cleaning is over
     * @return
     */
    public boolean cleaningDone() {
        return Arrays.stream(roomMatrix)
                .noneMatch(row -> Arrays.asList(row)
                        .contains(NOT_CLEANED));
    }


    /**
     *
     * Check if room matrix is well formed and contains only allowed characters
     *
     * @return
     */
    public boolean roomCheck() {

        boolean valid = true;
        List<String> allowed = Arrays.asList("M", NOT_CLEANED);

        for(String[] row : roomMatrix) {
            if(row.length != nbColumns)  {
                return false;
            }

            if(Arrays.stream(row).anyMatch(s -> !allowed.contains(s))) {
                return false;
            }
        }

        //test if each case has a valid move
        /*
        for (int line = 0; line < lines; line++) {
            for (int col = 0; col < columns; col++) {
                if (EMPTY_CASE.equals(roomMatrix[line][col])) {
                    List<Coordinates> possibleMoves =
                            new Coordinates(line,col)
                                    .getCoordinatesAround()
                                    .stream()
                                    .filter(this::isValidMove)
                                    .collect(Collectors.toList());
                    if (possibleMoves.isEmpty()) {
                        throw new RoomException("Matrix not valid : no possible moves for case "
                                + new Coordinates(line,col).toString());
                    }
                }
            }
        }*/

        return valid;
    }




}
