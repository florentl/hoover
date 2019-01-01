package com.dydu.hoover.test.model;

import com.dydu.hoover.model.*;
import org.junit.Assert;
import org.junit.Test;

public class DysonTest {

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
        Path path = new Path(p11);
        path.getPositions().add(p12);
        path.getPositions().add(p13);

        Dyson hoover = new Dyson();
        hoover.move(room, path);

        Assert.assertTrue(hoover.getCurrentPosition().equals(p13)
                && hoover.getMoves().size() == 3
                && Room.CLEANED.equals(room.getPositionValue(p13)));
    }

}
