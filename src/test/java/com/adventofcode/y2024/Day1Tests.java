package com.adventofcode.y2024;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class Day1Tests {
    private static final Day1 example = Day1
            .of(Path.of("src", "test", "resources", "examples", "day1"));
    private static final Day1 challenge = Day1
            .of(Path.of("src", "test", "resources", "challenges", "day1"));

    @Test
    @DisplayName("Part one example")
    void partOneTestA() {
        var expected = 11;
        var actual = example.totalDistance();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Part one answer")
    void partOneTestB() {
        var expected = 2285373;
        var actual = challenge.totalDistance();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Part two example")
    void partTwoTestC() {
        var expected = 31;
        var actual = example.similarityScore();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Part two answer")
    void partOneTestD() {
        var expected = 21142653;
        var actual = challenge.similarityScore();
        Assertions.assertEquals(actual, expected);
    }
}
