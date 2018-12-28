package com.dydu.hoover.model;

import java.util.LinkedList;

public class Path {

    private LinkedList<Position> positions;
    private boolean deadEnd;

    public Path(Position start) {
        positions = new LinkedList<>();
        positions.add(start);
        deadEnd = false;
    }

    public Path(Path path) {
        positions = new LinkedList<>();
        positions.addAll(path.getPositions());
        deadEnd = false;
    }

    public LinkedList<Position> getPositions() {
        return positions;
    }

    public Integer getLength() {
        return positions.size();
    }

    public boolean isDeadEnd() {
        return deadEnd;
    }

    public void setDeadEnd(boolean deadEnd) {
        this.deadEnd = deadEnd;
    }

    @Override
    public String toString() {
        return positions.toString();
    }
}
