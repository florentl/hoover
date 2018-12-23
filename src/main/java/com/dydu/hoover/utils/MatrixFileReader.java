package com.dydu.hoover.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Fill a matrix from file.<br/>
 * <br/>
 * Files are in the classpath.
 *
 * @author mat
 */
public final class MatrixFileReader {

    public String[][] readFile(String fileName) throws IOException {

        String[][] matrix = Files.lines(Paths.get(fileName))
                .map(l -> l.chars()
                        .mapToObj(c -> String.valueOf((char)c))
                        .toArray(String[]::new)
                ).toArray(String[][]::new);

        return matrix;
    }
}
