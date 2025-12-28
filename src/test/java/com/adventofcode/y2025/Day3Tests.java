package com.adventofcode.y2025;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Day.DAY3;
import static com.adventofcode.input.Year.YEAR_2025;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;

class Day3Tests {

    private static final Data exampleData = new Data(EXAMPLE, YEAR_2025, DAY3);
    private static final Data challengeData = new Data(CHALLENGE, YEAR_2025, DAY3);
        
    @Test
    void testA() {
        var day3 = new Day3(exampleData);
        var results = day3.totalOutputVoltage();

        var expected = 357;
        var actual = results.getFirst();

        assertEquals(expected, actual);
    }

    @Test
    void testB() {
        var day3 = new Day3(challengeData);
        var results = day3.totalOutputVoltage();

        var expected = 17376;
        var actual = results.getFirst();

        assertEquals(expected, actual);
    }
}
