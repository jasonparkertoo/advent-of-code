package com.adventofcode.y2025;

import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.Day.DAY4;
import static com.adventofcode.input.Year.YEAR_2025;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;

class Day4Tests {
    
    private static final Data exampleData = new Data(EXAMPLE, YEAR_2025, DAY4);
    private static final Data challengeData = new Data(CHALLENGE, YEAR_2025, DAY4);
    
    @Test
    void testA() {
        var day4 = new Day4(exampleData);

        var expected = 13;
        var actual = day4.countAccessible(3);

        assertEquals(expected, actual);
    }

    @Test
    void testB() {
        var day4 = new Day4(challengeData);

        var expected = 1416;
        var actual = day4.countAccessible(3);

        assertEquals(expected, actual);
    }
}