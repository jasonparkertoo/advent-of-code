package com.adventofcode.y2024;

import org.junit.jupiter.api.*;

import java.nio.file.Path;

import static com.adventofcode.y2024.SearchFunction.*;

class Day4Tests {
    final Puzzle ex = Puzzle.of(Path.of("src", "test", "resources", "examples", "day4"));
    final Puzzle ch = Puzzle.of(Path.of("src", "test", "resources", "challenges", "day4"));

    @Test
    @DisplayName("Part one example")
    void a() {
        var expected = 18;
        var searchFunction = SearchFunction.HORIZONTAL_SEARCH_FUNCTION
                .and(VERTICAL_SEARCH_FUNCTION)
                .and(LEFT_TO_RIGHT_DIAGONAL_SEARCH_FUNCTION)
                .and(RIGHT_TO_LEFT_DIAGONAL_SEARCH_FUNCTION);

        var actual = ex.count("xmas", searchFunction);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Part one challenge")
    void b() {
        var expected = 2504;
        var searchFunction = SearchFunction.HORIZONTAL_SEARCH_FUNCTION
                .and(VERTICAL_SEARCH_FUNCTION)
                .and(LEFT_TO_RIGHT_DIAGONAL_SEARCH_FUNCTION)
                .and(RIGHT_TO_LEFT_DIAGONAL_SEARCH_FUNCTION);

        var actual = ch.count("xmas", searchFunction);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Part two example")
    void c() {
        var expected = 9;
        var actual = ex.count("mas", X_PATTERN_SEARCH_FUNCTION);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Part two challenge")
    void d() {
        var expected = 1923; // 1879 low, 19180 high, 1923
        var actual = ch.count("mas", X_PATTERN_SEARCH_FUNCTION);
        Assertions.assertEquals(expected, actual);
    }
}