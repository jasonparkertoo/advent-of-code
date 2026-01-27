package com.adventofcode.y2025;

import com.adventofcode.input.Data;
import org.junit.jupiter.api.Test;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Day.DAY11;
import static com.adventofcode.input.Year.YEAR_2025;
import static org.junit.jupiter.api.Assertions.*;

class Day11Test {

    private final Day11 exSolution = new Day11(new Data(EXAMPLE, YEAR_2025, DAY11));
    private final Day11 chSolution = new Day11(new Data(CHALLENGE, YEAR_2025, DAY11));

    @Test
    void testA() {
        assertEquals(5, exSolution.numberOfDifferentPaths());
    }

    @Test
    void testB() {
        assertEquals(764, chSolution.numberOfDifferentPaths());
    }

    @Test
    void testC() {
        assertEquals(462444153119850L, chSolution.numberOfDifferentPathsWithBoth());
    }
}