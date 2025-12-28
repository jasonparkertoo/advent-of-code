package com.adventofcode.y2024;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Day.DAY2;
import static com.adventofcode.input.Year.YEAR_2024;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;

public class Day2Tests {
    private final Data exampleData = new Data(EXAMPLE, YEAR_2024, DAY2);
    private final Data challengeData = new Data(CHALLENGE, YEAR_2024, DAY2);
    
    @Test
    void testA() {
        var expected = 2L;
        var actual = Day2.numberOfSafeReports(exampleData);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() {
        var expected = 606L;
        var actual = Day2.numberOfSafeReports(challengeData);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() {
        var expected = 4L;
        var actual = Day2.numberOfTolerableReports(exampleData);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() {
        var expected = 644L;
        var actual = Day2.numberOfTolerableReports(challengeData);
        Assertions.assertEquals(expected, actual);
    }
}
