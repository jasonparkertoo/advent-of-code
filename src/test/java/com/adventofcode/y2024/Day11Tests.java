package com.adventofcode.y2024;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Day.DAY11;
import static com.adventofcode.input.Year.YEAR_2024;

public class Day11Tests {

    private static final Data exampleData = new Data(EXAMPLE, YEAR_2024, DAY11);
    private static final Data challengeData = new Data(CHALLENGE, YEAR_2024, DAY11);

    @Test
    void testA() {
        var stones = new Stones(exampleData);

        var expected = 55312;
        var actual = stones.numberOfStones(25);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() {
        var stones = new Stones(challengeData);

        var expected = 202019;
        var actual = stones.numberOfStones(25);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() {
        var stones = new Stones(challengeData);

        var expected = 239321955280205L;
        var actual = stones.numberOfStones(75);

        Assertions.assertEquals(expected, actual);
    }
}
