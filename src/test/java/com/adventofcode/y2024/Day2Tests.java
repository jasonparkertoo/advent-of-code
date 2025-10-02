package com.adventofcode.y2024;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class Day2Tests {
    private final Reports exampleReports = Reports.of(Path.of("src", "test", "resources", "examples", "day2"));
    private final Reports challengeReports = Reports.of(Path.of("src", "test", "resources", "challenges", "day2"));

    @Test
    @DisplayName("Part one example")
    void A() {
        var expected = 2L;
        var actual = Analyze.numberOfSafeReports(exampleReports);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Part one challenge")
    void B() {
        var expected = 606L;
        var actual = Analyze.numberOfSafeReports(challengeReports);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Part two example")
    void C() {
        var expected = 4L;
        var actual = Analyze.numberOfTolerableReports(exampleReports);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Part two challenge")
    void D() {
        var expected = 644L;
        var actual = Analyze.numberOfTolerableReports(challengeReports);
        Assertions.assertEquals(actual, expected);
    }
}
