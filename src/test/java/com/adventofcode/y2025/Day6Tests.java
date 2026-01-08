package com.adventofcode.y2025;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Day.DAY6;
import static com.adventofcode.input.Year.YEAR_2025;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;

class Day6Tests {

    private final Day6 example = new Day6(new Data(EXAMPLE, YEAR_2025, DAY6));
    private final Day6 challenge = new Day6(new Data(CHALLENGE, YEAR_2025, DAY6));

    @Test
    void testA() {
        assertEquals(4277556L, example.calculateGrandTotal());
    }

    @Test
    void testB() {
        assertEquals(5595593539811L, challenge.calculateGrandTotal());
    }

    @Test
    void testC() {
        assertEquals(3263827L, example.calculateGrandTotal2());
    }

    @Test
    void testD() {
        assertEquals(10153315705125L, challenge.calculateGrandTotal2());
    }
}