package com.dydu.hoover.test.model;

import com.dydu.hoover.model.Position;
import com.dydu.hoover.model.Room;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RoomTest {

    @Test
    public void testRandomPosition() {
        String[][] matrix
                = {
                {"M", "M", "M", "M"},
                {"M", "M", " ", "M"},
                {"M", "M", "M", "M"},
                {"M", " ", " ", "M"},
                {"M", "M", "M", "M"}};

        List possibles= List.of(new Position(1,2),
                new Position(3,1),
                new Position(3,2));
        Room room = new Room(matrix);
        Assert.assertTrue(possibles.contains(room.getRandomPosition()));
    }

    @Test
    public void testCleaningDone() {
        String[][] matrix
                = {
                {"M", "M", "M", "M"},
                {"M", "M", "C", "M"},
                {"M", "M", "M", "M"},
                {"M", "C", "C", "M"},
                {"M", "M", "M", "M"}};
        Room room = new Room(matrix);
        Assert.assertTrue(room.cleaningDone());
    }

    @Test
    public void testCleaningNotDone() {
        String[][] matrix
                = {
                {"M", "M", "M", "M"},
                {"M", "M", " ", "M"},
                {"M", "M", "M", "M"},
                {"M", " ", " ", "M"},
                {"M", "M", "M", "M"}};
        Room room = new Room(matrix);
        Assert.assertFalse(room.cleaningDone());
    }

    @Test
    public void testRoomUnauthorizedCharacters() {
        String[][] matrix
                = {
                {"M", "M", "M", "M", "M", "M", "M"},
                {"M", "x", "M", " ", " ", "M", "M"},
                {"M", "M", " ", " ", "M", " ", "M"},
                {"M", " ", " ", "M", "M", " ", "M"},
                {"M", "M", "M", "M", "M", "M", "M"}};
        Room room = new Room(matrix);
        Assert.assertFalse(room.roomCheck());
    }

    @Test
    public void testRoomOk() {
        String[][] matrix
                = {
                {"M", "M", "M", "M", "M", "M", "M"},
                {"M", " ", " ", " ", " ", "M", "M"},
                {"M", "M", " ", " ", " ", " ", "M"},
                {"M", " ", " ", "M", "M", " ", "M"},
                {"M", "M", "M", "M", "M", "M", "M"}};

        Room room = new Room(matrix);
        Assert.assertTrue(room.roomCheck());
    }

    @Test
    public void testRoomBadMatrixStructure() {
        String[][] roomArray
                = {
                {"M", "M", "M", "M", "M", "M", "M"},
                {"M", " ", "M", " ", " "},
                {"M", " ", " ", " ", "M", " ", "M"},
                {"M", " ", " ", "M", " ", " ", "M"},
                {"M", "M", "M", "M", "M", "M", "M"}};
        Room room = new Room(roomArray);
        Assert.assertFalse(room.roomCheck());
    }

}