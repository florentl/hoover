package com.dydu.hoover.exceptions;

public class InvalidRoomContentException extends Exception {

    public static final String UNAUTHORIZED_CHARACTERS_FOUND = "Unauthorized characters found in room content !";

    public InvalidRoomContentException(String message) {
        super(message);
    }
}
