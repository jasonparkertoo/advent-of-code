package com.adventofcode.y2024;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Day.DAY9;
import static com.adventofcode.input.Year.YEAR_2024;
import static com.adventofcode.y2024.Harddrive.CompactMethod.LEFT;
import static com.adventofcode.y2024.Harddrive.CompactMethod.NORM;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;

import java.io.IOException;

public class Day9Tests {

    private final Data exampleData = new Data(EXAMPLE, YEAR_2024, DAY9);
    private final Data challengeData = new Data(CHALLENGE, YEAR_2024, DAY9);
    
    @Test
    void testA() throws IOException {
        var hd = new Harddrive(exampleData);

        var expected = 1928;
        var actual = hd.checksum(NORM);

        assertEquals(expected, actual);
    }

    @Test
    void testB() throws IOException {
        var hd = new Harddrive(challengeData);

        var expected = 6262891638328L;
        var actual = hd.checksum(NORM);

        assertEquals(expected, actual);
    }

    @Test
    void testC() throws IOException {
        var hd = new Harddrive(exampleData);

        var expected = 2858;
        var actual = hd.checksum(LEFT);

        assertEquals(expected, actual);
    }

    @Test
    void testD() throws IOException {
        var hd = new Harddrive(challengeData);

        var expected = 6287317016845L;
        var actual = hd.checksum(LEFT);

        assertEquals(expected, actual);
    }
}