package com.adventofcode.y2024;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;
import com.adventofcode.input.Day;
import com.adventofcode.input.Year;

class Day3Tests {

    private final Data exampleData = new Data(EXAMPLE, Year.YEAR_2024, Day.DAY3);
    private final Data challengeData = new Data(CHALLENGE, Year.YEAR_2024, Day.DAY3);

    @Test
    void testA() {
        var memory = new Memory(exampleData);
        
        var expected = 161;
        var actual = memory.product(ScanLevel.MUL);
        
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() {
        var memory = new Memory(challengeData);
        
        var expected = 156388521;
        var actual = memory.product(ScanLevel.MUL);
        
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() {
        var memory = new Memory(exampleData);
        
        var expected = 48;
        var actual = memory.product(ScanLevel.FULL);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() {
        var memory = new Memory(challengeData);
        var expected = 75920122;
        var actual = memory.product(ScanLevel.FULL);
        Assertions.assertEquals(expected, actual);
    }
}
