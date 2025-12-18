package com.adventofcode.y2024;

import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Day3Tests {

    private final Memory example = Memory.from(Path.of("src", "test", "resources", "2024", "examples", "day3"));
    private final Memory example2 = Memory.from(Path.of("src", "test", "resources", "2024", "examples", "day3_2"));
    private final Memory answer = Memory.from(Path.of("src", "test", "resources", "2024", "challenges", "day3"));

    @Test
    @DisplayName("Part one example")
    void a() {
        var expected = 161;
        var actual = example.product(ScanLevel.MUL);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Part one challenge")
    void b() {
        var expected = 156388521;
        var actual = answer.product(ScanLevel.MUL);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Part two challenge")
    void c() {
        var expected = 48;
        var actual = example2.product(ScanLevel.FULL);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Part two example")
    void d() {
        var expected = 75920122;
        var actual = answer.product(ScanLevel.FULL);
        Assertions.assertEquals(expected, actual);
    }
}
