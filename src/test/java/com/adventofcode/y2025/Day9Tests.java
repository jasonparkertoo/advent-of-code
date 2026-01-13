package com.adventofcode.y2025;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Day.DAY9;
import static com.adventofcode.input.Year.YEAR_2025;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;

class Day9Tests {
    
    private final Day9 example = new Day9(new Data(EXAMPLE, YEAR_2025, DAY9));
    private final Day9 challenge = new Day9(new Data(CHALLENGE, YEAR_2025, DAY9));
    
    @Test
    void TestA() {
        assertEquals(50L, example.findLargestRectangle());
    }
    
    @Test
    void TestB() {
        assertEquals(4756718172L, challenge.findLargestRectangle());
    }
}