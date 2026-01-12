package com.adventofcode.y2025;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Day.DAY8;
import static com.adventofcode.input.Year.YEAR_2025;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;

class Day8Tests {
    
    private final Day8 exampleSolution = new Day8(new Data(EXAMPLE, YEAR_2025, DAY8));
    private final Day8 challengeSolution = new Day8(new Data(CHALLENGE, YEAR_2025, DAY8));
    
    @Test
    void testA() {
        assertEquals(40, exampleSolution.productOfThreeLargestCircuits(10));
    }

    @Test
    void testB() {
        assertEquals(330786, challengeSolution.productOfThreeLargestCircuits(1000));
    }    
    
    @Test
    void testC() {
        assertEquals(25272L, exampleSolution.productOfLastConnection());
    }

    @Test
    void testD() {
        assertEquals(3276581616L, challengeSolution.productOfLastConnection());
    }
}