package com.dydu.hoover.test.model;

import com.dydu.hoover.model.Position;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class PositionTest {

    @Test
    public void testPositionValues() {
        Position position = new Position(1, 3);
        Assert.assertEquals(1, position.getLine());
        Assert.assertEquals(3, position.getColumn());
    }

    @Test
    public void testPositionEquals() {
        Position position = new Position(1, 3);
        Position position2 = new Position(1, 3);
        Assert.assertTrue(position.equals(position2));
    }

    @Test
    public void testPositionNotEquals() {
        Position position = new Position(1, 3);
        Position position2 = new Position(1, 1);
        Assert.assertFalse(position.equals(position2));
    }

    @Test
    public void testPositionValid() {
        Assert.assertTrue(new Position(1,1).isValid(2,2));
    }

    @Test
    public void testPositionNotValid() {
        Assert.assertFalse(new Position(3,3).isValid(4,3));
    }

    @Test
    public void testPositionAroundOrigin() {
        Position origin = new Position(0, 0);
        List<Position> positionsAround = origin.getPositionsAround(3,3, null);
        Assert.assertTrue(
                positionsAround.size() == 2
                        && positionsAround.contains(new Position(0,1))
                        && positionsAround.contains(new Position(1,0))
        );
    }

    @Test
    public void testPositionAroundRightBottom() {
        Position rightBottom = new Position(2, 2);
        List<Position> positionsAround = rightBottom.getPositionsAround(3,3, null);
        Assert.assertTrue(
                positionsAround.size() == 2
                        && positionsAround.contains(new Position(1,2))
                        && positionsAround.contains(new Position(2,1))
        );
    }

    @Test
    public void testPositionAround() {
        Position rightBottom = new Position(2, 2);
        List<Position> positionsAround = rightBottom.getPositionsAround(5,5, null);
        Assert.assertTrue(
                positionsAround.size() == 4
                        && positionsAround.contains(new Position(1,2))
                        && positionsAround.contains(new Position(2,1))
                        && positionsAround.contains(new Position(3,2))
                        && positionsAround.contains(new Position(2,3)));
    }

}
