package com.dydu.hoover.test.model;

import com.dydu.hoover.model.Dyson;
import com.dydu.hoover.model.Position;
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

}