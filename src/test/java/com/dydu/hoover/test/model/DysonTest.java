package com.dydu.hoover.test.model;

import com.dydu.hoover.model.Dyson;
import com.dydu.hoover.model.Path;
import com.dydu.hoover.model.Position;
import com.dydu.hoover.model.Room;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DysonTest {

    @Test
    public void testPositionUp() {
        Position currentPosition = new Position(1, 1);
        Position upperPosition = new Position(0,1);
        List<Position> positions = new ArrayList<>();
        positions.add(upperPosition);
        Dyson myHoover = new Dyson();
        myHoover.setCurrentPosition(currentPosition);
        Assert.assertEquals(myHoover.getPosition(positions, Dyson.UP), upperPosition);
    }

    @Test
    public void testPositionDown() {
        Position currentPosition = new Position(1, 1);
        Position downPosition = new Position(2,1);
        List<Position> positions = new ArrayList<>();
        positions.add(downPosition);
        Dyson myHoover = new Dyson();
        myHoover.setCurrentPosition(currentPosition);
        Assert.assertEquals(myHoover.getPosition(positions, Dyson.DOWN), downPosition);
    }

    @Test
    public void testPositionRight() {
        Position currentPosition = new Position(1, 1);
        Position positionRight = new Position(1,2);
        List<Position> positions = new ArrayList<>();
        positions.add(positionRight);
        Dyson myHoover = new Dyson();
        myHoover.setCurrentPosition(currentPosition);
        Assert.assertEquals(myHoover.getPosition(positions, Dyson.RIGHT), positionRight);
    }

    @Test
    public void testPositionLeft() {
        Position currentPosition = new Position(1, 1);
        Position positionLeft = new Position(1,0);
        List<Position> positions = new ArrayList<>();
        positions.add(positionLeft);
        Dyson myHoover = new Dyson();
        myHoover.setCurrentPosition(currentPosition);
        Assert.assertEquals(myHoover.getPosition(positions, Dyson.LEFT), positionLeft);
    }

    @Test
    public void testNoPositionFound() {
        Position currentPosition = new Position(1, 1);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(5,5));
        positions.add(new Position(4,4));
        Dyson myHoover = new Dyson();
        myHoover.setCurrentPosition(currentPosition);
        Assert.assertNull(myHoover.getPosition(positions, Dyson.LEFT));
    }

    @Test
    public void testNewDirectionUp() {
        Position currentPosition = new Position(1, 1);
        Position upperPosition = new Position(0,1);
        Dyson myHoover = new Dyson();
        myHoover.setCurrentPosition(currentPosition);
        myHoover.setNewDirection(upperPosition);
        Assert.assertEquals(myHoover.getCurrentDirection(), Dyson.UP);
    }

    @Test
    public void testNewDirectionDown() {
        Position currentPosition = new Position(1, 1);
        Position positionDown = new Position(2,1);
        Dyson myHoover = new Dyson();
        myHoover.setCurrentPosition(currentPosition);
        myHoover.setNewDirection(positionDown);
        Assert.assertEquals(myHoover.getCurrentDirection(), Dyson.DOWN);
    }

    @Test
    public void testNewDirectionRight() {
        Position currentPosition = new Position(1, 1);
        Position positionRight = new Position(1,2);
        Dyson myHoover = new Dyson();
        myHoover.setCurrentPosition(currentPosition);
        myHoover.setNewDirection(positionRight);
        Assert.assertEquals(myHoover.getCurrentDirection(), Dyson.RIGHT);
    }

    @Test
    public void testNewDirectionLeft() {
        Position currentPosition = new Position(1, 1);
        Position positionLeft = new Position(1,0);
        Dyson myHoover = new Dyson();
        myHoover.setCurrentPosition(currentPosition);
        myHoover.setNewDirection(positionLeft);
        Assert.assertEquals(myHoover.getCurrentDirection(), Dyson.LEFT);
    }

    @Test
    public void testCleanPathes() {

        Position p00= new Position(0,0);
        Position p01= new Position(0,1);
        Position p10= new Position(1,0);
        Position p11= new Position(1,1);

        Path shortest = new Path(p00);
        shortest.getPositions().add(p01);

        Path longest = new Path(p00);
        longest.getPositions().add(p10);
        longest.getPositions().add(p11);
        longest.getPositions().add(p01);

        Path deaEnd = new Path(p00);
        deaEnd.setDeadEnd(true);

        List<Path> pathes = new ArrayList<>();
        pathes.add(shortest);
        pathes.add(longest);
        pathes.add(deaEnd);

        Dyson hoover = new Dyson();
        hoover.cleanPathes(pathes);

        Assert.assertTrue(pathes.size() == 1 && pathes.get(0).equals(shortest));
    }

    @Test
    public void testCleanPathesWithSameLength() {

        Position p00= new Position(0,0);
        Position p01= new Position(0,1);
        Position p10= new Position(1,0);
        Position p11= new Position(1,1);

        Path p1 = new Path(p00);
        p1.getPositions().add(p01);
        p1.getPositions().add(p11);

        Path p2 = new Path(p00);
        p2.getPositions().add(p10);
        p2.getPositions().add(p11);

        List<Path> pathes = new ArrayList<>();
        pathes.add(p1);
        pathes.add(p2);

        Dyson hoover = new Dyson();
        hoover.cleanPathes(pathes);

        Assert.assertTrue(pathes.size() == 1 && pathes.get(0).equals(p1));
    }

    @Test
    public void testShortestPath() {
        String[][] matrix
                = {
                {"M", "M", "M", "M", "M", "M", "M"},
                {"M", "C", "C", "C", "C", "M", "M"},
                {"M", "C", "C", "C", "C", "C", "M"},
                {"M", "C", "C", "C", "C", "C", "M"},
                {"M", "C", "C", "C", "C", "C", "M"},
                {"M", "C", "M", "C", "C", "C", "M"},
                {"M", "C", "C", "C", "M", "C", "M"},
                {"M", "C", "M", "C", "M", "C", "M"},
                {"M", "C", "C", "C", "C", " ", "M"},
                {"M", "M", "M", "M", "M", "M", "M"}};
        Room room = new Room(matrix);
        Dyson hoover = new Dyson();

        Path path = hoover.shortestPathToNextPosition(new Position(2, 1), room);
        Assert.assertTrue(path.getPositions().size() == 11);
    }

    @Test
    public void testMoveAlongPath() {
        String[][] matrix
                = {
                {"M", "M", "M", "M", "M", "M", "M"},
                {"M", " ", " ", " ", " ", " ", "M"},
                {"M", "M", "M", "M", "M", "M", "M"}};
        Room room = new Room(matrix);
        Position p11 = new Position(1,1);
        Position p12 = new Position(1,2);
        Position p13 = new Position(1,3);
        Dyson hoover = new Dyson();
        hoover.setCurrentPosition(p11);
        hoover.getMoves().add(p11);

        Path path = new Path(p11);
        path.getPositions().add(p12);
        path.getPositions().add(p13);

        hoover.moveAlongPath(path, room);

        Assert.assertTrue(hoover.getCurrentPosition().equals(p13)
                && hoover.getMoves().size() == 3
                && Room.CLEANED.equals(room.getPositionValue(p13))
                && Dyson.RIGHT == hoover.getCurrentDirection());
    }

}