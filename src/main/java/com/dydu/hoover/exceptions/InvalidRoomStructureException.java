package com.dydu.hoover.exceptions;

public class InvalidRoomStructureException extends Exception {

    public static final String WRONG_ROOM_STRUCTURE_EMPTY = "Wrong room structure, room is empty !";
    public static final String WRONG_ROOM_STRUCTURE_LINES_LENGTH = "Wrong room structure, all lines must have same length !";
    public static final String WRONG_ROOM_STRUCTURE_POSITIONS_NOT_ACCESSIBLE = "Wrong room structure : some positions are not accessible by the hoover !";

    public InvalidRoomStructureException(String message) {
        super(message);
    }
}
