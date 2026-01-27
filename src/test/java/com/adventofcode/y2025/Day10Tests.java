package com.adventofcode.y2025;

import static org.junit.jupiter.api.Assertions.*;

import com.adventofcode.input.Data;
import com.adventofcode.input.DataSet;
import com.adventofcode.input.Day;
import com.adventofcode.input.Year;

import org.junit.jupiter.api.Test;

class Day10Tests {

    private final Day10 exSolution = new Day10(new Data(DataSet.EXAMPLE, Year.YEAR_2025, Day.DAY10));
    private final Day10 chSolution = new Day10(new Data(DataSet.CHALLENGE, Year.YEAR_2025, Day.DAY10));

    @Test
    void testA() {
        assertEquals(7, exSolution.fewestButtonPresses());
    }

    @Test
    void testB() {
        assertEquals(404, chSolution.fewestButtonPresses());
    }

     @Test
     void testC() {
         assertEquals(33, exSolution.calculateScore());
     }

     @Test
     void testD() {
         assertEquals(16474, chSolution.calculateScore());
     }
}
