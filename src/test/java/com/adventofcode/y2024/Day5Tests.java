package com.adventofcode.y2024;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;
import com.adventofcode.input.Day;
import com.adventofcode.input.Year;

class Day5Tests {

    private final Data exampleData = new Data(EXAMPLE, Year.YEAR_2024, Day.DAY5);
    private final Data challengeData = new Data(CHALLENGE, Year.YEAR_2024, Day.DAY5);
    
    @Test
    void testA() {
        final var queue = new PrintQueue(exampleData);
        final var data = queue.parseData();

        var expected = 143;
        var actual= queue.sumMiddlePageNumbers(data);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() {
        final var queue = new PrintQueue(challengeData);
        final var data = queue.parseData();

        var expected = 5747;
        var actual = queue.sumMiddlePageNumbers(data);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() {
        final var queue = new PrintQueue(exampleData);
        final var data = queue.parseData();

        var expected = 123;
        var actual = queue.sumIncorrectMiddlePageNumbers(data);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() {
        final var queue = new PrintQueue(challengeData);
        final var data = queue.parseData();

        var expected = 5502;
        var actual = queue.sumIncorrectMiddlePageNumbers(data);
        Assertions.assertEquals(expected, actual);
    }
}
