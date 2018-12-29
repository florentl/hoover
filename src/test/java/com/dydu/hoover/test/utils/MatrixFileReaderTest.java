package com.dydu.hoover.test.utils;

import com.dydu.hoover.utils.MatrixFileReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Test MatrixFileReader methods.
 * 
 * @author mat
 */
public final class MatrixFileReaderTest {

    @Test
    public void readFile() throws IOException {
        String fileName = getClass().getResource("/maze1.txt").getPath();
        MatrixFileReader matrixFileReader = new MatrixFileReader();
        String[][] maze = matrixFileReader.readFile(fileName);
        Assert.assertNotNull(maze);
        Assert.assertEquals(7, maze[0].length);
        Assert.assertEquals("M", maze[0][0]);
        Assert.assertEquals(" ", maze[2][6]);
    }

    @Test(expected = IOException.class)
    public void readFileWithError() throws IOException {
        new MatrixFileReader().readFile("null");
    }
}
