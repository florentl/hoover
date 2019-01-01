package com.dydu.hoover.test.model;

import com.dydu.hoover.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PathManagerTest {

    @Test
    public void testPositionUp() {
        Position currentPosition = new Position(1, 1);
        Position upperPosition = new Position(0,1);
        List<Position> positions = new ArrayList<>();
        positions.add(upperPosition);
        PathManager pathFinder= new PathManager();
        Assert.assertEquals(pathFinder.getPosition(currentPosition, positions, PathManager.UP), upperPosition);
    }

    @Test
    public void testPositionDown() {
        Position currentPosition = new Position(1, 1);
        Position downPosition = new Position(2,1);
        List<Position> positions = new ArrayList<>();
        positions.add(downPosition);
        Assert.assertEquals(new PathManager().getPosition(currentPosition, positions,
                PathManager.DOWN), downPosition);
    }

    @Test
    public void testPositionRight() {
        Position currentPosition = new Position(1, 1);
        Position positionRight = new Position(1,2);
        List<Position> positions = new ArrayList<>();
        positions.add(positionRight);
        Assert.assertEquals(new PathManager().getPosition(currentPosition, positions,
                PathManager.RIGHT), positionRight);
    }

    @Test
    public void testPositionLeft() {
        Position currentPosition = new Position(1, 1);
        Position positionLeft = new Position(1,0);
        List<Position> positions = new ArrayList<>();
        positions.add(positionLeft);
        Assert.assertEquals(new PathManager().getPosition(currentPosition, positions,
                PathManager.LEFT), positionLeft);
    }

    @Test
    public void testNoPositionFound() {
        Position currentPosition = new Position(1, 1);
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(5,5));
        positions.add(new Position(4,4));
        Assert.assertNull(new PathManager().getPosition(currentPosition, positions, PathManager.LEFT));
    }

    @Test
    public void testNewDirectionUp() {
        Position currentPosition = new Position(1, 1);
        Position upperPosition = new Position(0,1);
        Assert.assertEquals(new PathManager().getNewDirection(currentPosition, upperPosition),
                PathManager.UP);
    }

    @Test
    public void testNewDirectionDown() {
        Position currentPosition = new Position(1, 1);
        Position positionDown = new Position(2,1);
        Assert.assertEquals(new PathManager().getNewDirection(currentPosition, positionDown),
                PathManager.DOWN);
    }

    @Test
    public void testNewDirectionRight() {
        Position currentPosition = new Position(1, 1);
        Position positionRight = new Position(1,2);
        Assert.assertEquals(new PathManager().getNewDirection(currentPosition, positionRight),
                PathManager.RIGHT);
    }

    @Test
    public void testNewDirectionLeft() {
        Position currentPosition = new Position(1, 1);
        Position positionLeft = new Position(1,0);
        Assert.assertEquals(new PathManager().getNewDirection(currentPosition, positionLeft),
                PathManager.LEFT);
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

        new PathManager().cleanPathes(pathes);

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

        new PathManager().cleanPathes(pathes);

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
        Path path = new PathManager(room).shortestPathToNextPosition(new Position(2, 1));
        Assert.assertEquals(10, path.getPositions().size());
    }

}