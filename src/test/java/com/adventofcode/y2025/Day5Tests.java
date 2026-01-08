package com.adventofcode.y2025;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Day.DAY5;
import static com.adventofcode.input.Year.YEAR_2025;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.adventofcode.input.Data;
import org.junit.jupiter.api.Test;

class Day5Tests {

    private final Day5 example = new Day5(new Data(EXAMPLE, YEAR_2025, DAY5));
    private final Day5 challenge = new Day5(new Data(CHALLENGE, YEAR_2025, DAY5));

    @Test
    void testA() {
        assertEquals(3L, example.getFreshCount());
    }

    @Test
    void testB() {
        assertEquals(896L, challenge.getFreshCount());
    }

    @Test
    void testC() {
        assertEquals(14L, example.totalRangeCount());
    }

    @Test
    void testD() {
        assertEquals(346240317247002L, challenge.totalRangeCount());
    }
}
