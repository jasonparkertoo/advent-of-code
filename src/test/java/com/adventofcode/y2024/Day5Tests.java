package com.adventofcode.y2024;

import static com.adventofcode.util.Day.DAY5;
import static com.adventofcode.util.Year.YEAR_2024;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.util.PathUtil;

class Day5Tests {

    @Test
    void testA() {
        final var queue = new PrintQueue(PathUtil.getExampleData(YEAR_2024, DAY5));
        final var data = queue.parseData();

        var expected = 143;
        var actual= queue.sumMiddlePageNumbers(data);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() {
        final var queue = new PrintQueue(PathUtil.getChallengeData(YEAR_2024, DAY5));
        final var data = queue.parseData();

        var expected = 5747;
        var actual = queue.sumMiddlePageNumbers(data);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() {
        final var queue = new PrintQueue(PathUtil.getExampleData(YEAR_2024, DAY5));
        final var data = queue.parseData();

        var expected = 123;
        var actual = queue.sumIncorrectMiddlePageNumbers(data);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() {
        final var queue = new PrintQueue(PathUtil.getChallengeData(YEAR_2024, DAY5));
        final var data = queue.parseData();

        var expected = 5502;
        var actual = queue.sumIncorrectMiddlePageNumbers(data);
        Assertions.assertEquals(expected, actual);
    }
}
