package com.dydu.hoover.test.model;

import com.dydu.hoover.exceptions.InvalidRoomContentException;
import com.dydu.hoover.exceptions.InvalidRoomStructureException;
import com.dydu.hoover.model.Position;
import com.dydu.hoover.model.Room;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomTest {

    @Test
    public void testRandomPosition() {
        String[][] matrix
                = {
                {"M", "M", "M", "M"},
                {"M", "M", " ", "M"},
                {"M", "M", " ", "M"},
                {"M", " ", " ", "M"},
                {"M", "M", "M", "M"}};

        List possibles = List.of(new Position(1,2),
                new Position(2,2),
                new Position(3,2),
                new Position(3,1));
        Room room = new Room(matrix);
        Position randomPosition = room.getRandomPosition();
        Assert.assertTrue(possibles.contains(randomPosition));
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
    public void testRoomPositionsToClean() {
        String[][] matrix
                = {
                {"M", "M", "M", "M", "M", "M", "M"},
                {"M", "M", "M", " ", " ", " ", "M"},
                {"M", "M", " ", " ", "M", " ", "M"},
                {"M", " ", " ", "M", " ", " ", "M"},
                {"M", "M", "M", "M", "M", "M", "M"}};
        Room room = new Room(matrix);
        List<Position> positions = room.getPositionsToClean();
        Assert.assertEquals(10, positions.size());
    }

    @Test(expected = InvalidRoomContentException.class)
    public void testRoomUnauthorizedCharacters() throws InvalidRoomContentException,
            InvalidRoomStructureException {
        String[][] matrix
                = {
                {"M", "M", "M", "M", "M", "M", "M"},
                {"M", "x", "M", " ", " ", "M", "M"},
                {"M", "M", " ", " ", "M", " ", "M"},
                {"M", " ", " ", "M", "M", " ", "M"},
                {"M", "M", "M", "M", "M", "M", "M"}};
        new Room(matrix).roomCheck();
    }

    @Test(expected = InvalidRoomStructureException.class)
    public void testRoomBadMatrixStructure() throws InvalidRoomContentException,
            InvalidRoomStructureException {
        String[][] matrix
                = {
                {"M", "M", "M", "M", "M", "M", "M"},
                {"M", " ", "M", " ", " "},
                {"M", " ", " ", " ", "M", " ", "M"},
                {"M", " ", " ", "M", " ", " ", "M"},
                {"M", "M", "M", "M", "M", "M", "M"}};
        new Room(matrix).roomCheck();
    }

    @Test
    public void testAllPositionsNotAccessible() {
        String[][] matrix
                = {
                {"M", "M", "M", "M", "M"},
                {"M", " ", " ", " ", "M"},
                {"M", "M", "M", " ", "M"},
                {"M", " ", " ", "M", "M"},
                {"M", "M", "M", "M", "M"}};
        Room room = new Room(matrix);
        Set<Position> positions = new HashSet<>();
        positions.add(room.getRandomPosition());
        Assert.assertFalse(room.isAllPositionsAccessible(positions));
    }

    @Test
    public void testAllPositionsAccessible() {
        String[][] matrix
                = {
                {"M", "M", "M", "M", "M"},
                {"M", " ", " ", " ", "M"},
                {"M", "M", "M", " ", "M"},
                {"M", " ", " ", " ", "M"},
                {"M", " ", "M", " ", "M"},
                {"M", " ", " ", " ", "M"},
                {"M", " ", "M", " ", "M"},
                {"M", " ", " ", " ", "M"},
                {"M", " ", "M", " ", "M"},
                {"M", " ", "M", " ", "M"},
                {"M", " ", "M", "M", "M"},
                {"M", "M", "M", "M", "M"}};
        Room room = new Room(matrix);
        Set<Position> positions = new HashSet<>();
        positions.add(room.getRandomPosition());
        Assert.assertTrue(room.isAllPositionsAccessible(positions));
    }

    @Test
    public void testRoomOk() throws InvalidRoomContentException,
            InvalidRoomStructureException {
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

}