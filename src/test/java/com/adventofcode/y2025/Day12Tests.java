package com.adventofcode.y2025;

import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.Day.DAY12;
import static com.adventofcode.input.Year.YEAR_2025;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.adventofcode.input.Data;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Day12}.
 */
class Day12Tests {

    private final Day12 exSolution = new Day12(new Data(EXAMPLE, YEAR_2025, DAY12));
    private final Day12 chSolution = new Day12(new Data(CHALLENGE, YEAR_2025, DAY12));

    @Test
    void testA() {
        assertEquals(2, exSolution.countValidRegions());
    }

    @Test
    void testB() {
        assertEquals(427, chSolution.countValidRegions());
    }
}
