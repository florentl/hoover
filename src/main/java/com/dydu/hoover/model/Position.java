package com.dydu.hoover.model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Position defines a case in the room with line and column indexes
 */
public class Position {

    //line index in the room
    private int line;
    //column index in the room
    private int column;

    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "[" + line + "," + column + "]";
    }

    /**
     * A valid position is as follow :
     * 0 <= line < number of lines in the room
     * 0 <= column < number of columns in the room
     * @param nbLines
     * @param nbColumns
     * @return
     */
    public boolean isValid (int nbLines, int nbColumns) {
        return line >= 0 && line < nbLines && column >= 0 && column < nbColumns;
    }

    /**
     * Return all valid positions (down, up, right and left) around the position instance, depending on total
     * room lines and columns
     * @return
     */
    public List<Position> getPositionsAround(int totalLines, int totalColumns) {

        List<Position> positions = List.of(new Position(line + 1, column),
                new Position(line - 1, column),
                new Position(line, column + 1),
                new Position(line, column - 1));

        return positions
                .stream()
                .filter(p -> p.isValid(totalLines, totalColumns))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        Position p = (Position)obj;
        return line == p.getLine() && column == p.getColumn();
    }
}
