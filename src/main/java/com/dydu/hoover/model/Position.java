package com.dydu.hoover.model;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Position defines a case in the room with line and column indexes
 */
public class Position {

    private int line;
    private int column;

    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "[" + line + "," + column + "]";
    }

    /**
     * A valid position has line and column indexes between 0 (inclusive) and total line / column number
     * @param totalLines
     * @param totalColumns
     * @return
     */
    public boolean isValid(int totalLines, int totalColumns) {
        return line >= 0 && line < totalLines && column >= 0 && column < totalColumns;
    }

    /**
     * Return all valid positions (down, up, right and left) around the position instance, depending on total
     * room lines and columns. Additional predicate can also be used
     * @return
     */
     public List<Position> getPositionsAround(int totalLines, int totalColumns,
                                             Predicate<Position> additional) {

        List<Position> positions = List.of(new Position(line + 1, column),
                new Position(line - 1, column),
                new Position(line, column + 1),
                new Position(line, column - 1));
        Predicate<Position> positionPredicate = p -> p.isValid(totalLines, totalColumns);
        if(additional != null) {
            positionPredicate = positionPredicate.and(additional);
        }

        return positions
                .stream()
                .filter(positionPredicate)
                .collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, column);
    }

    @Override
    public boolean equals(Object o) {
        Position p = (Position)o;
        return p.getColumn() == column && p.getLine() == line;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }


}
