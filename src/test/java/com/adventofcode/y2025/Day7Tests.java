package com.adventofcode.y2025;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Day.DAY7;
import static com.adventofcode.input.Year.YEAR_2025;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.adventofcode.input.Data;
import org.junit.jupiter.api.Test;

class Day7Tests {

    private final Day7 example = new Day7(new Data(EXAMPLE, YEAR_2025, DAY7));
    private final Day7 challenge = new Day7(new Data(CHALLENGE, YEAR_2025, DAY7));

    @Test
    void testA() {
        assertEquals(21, example.countBeamSplits());
    }

    @Test
    void testB() {
        assertEquals(1533, challenge.countBeamSplits());
    }

    @Test
    void testC() {
        assertEquals(40, example.countTimelines());
    }

    @Test
    void testD() {
        assertEquals(10733529153890L, challenge.countTimelines());
    }
}
