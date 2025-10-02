package com.adventofcode.y2024;

import com.adventofcode.y2024.util.PathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day5Tests {

    private static final String DAY = "day5";

    @Test
    void testA() {
        final var queue = new PrintQueue(PathUtil.getExampleData(DAY));
        final var data = queue.parseData();

        var expected = 143;
        var actual= queue.sumMiddlePageNumbers(data);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() {
        final var queue = new PrintQueue(PathUtil.getChallengeData(DAY));
        final var data = queue.parseData();

        var expected = 5747;
        var actual = queue.sumMiddlePageNumbers(data);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() {
        final var queue = new PrintQueue(PathUtil.getExampleData(DAY));
        final var data = queue.parseData();

        var expected = 123;
        var actual = queue.sumIncorrectMiddlePageNumbers(data);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() {
        final var queue = new PrintQueue(PathUtil.getChallengeData(DAY));
        final var data = queue.parseData();

        var expected = 5502;
        var actual = queue.sumIncorrectMiddlePageNumbers(data);
        Assertions.assertEquals(expected, actual);
    }
}
