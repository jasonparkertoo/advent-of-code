package com.adventofcode.y2024;

import org.junit.jupiter.api.*;

import com.adventofcode.input.Data;
import com.adventofcode.input.Day;
import com.adventofcode.input.Year;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.y2024.SearchFunction.*;

class Day4Tests {
    private final Data exampleData = new Data(EXAMPLE, Year.YEAR_2024, Day.DAY4);
    private final Data challengeData = new Data(CHALLENGE, Year.YEAR_2024, Day.DAY4);
    
    @Test
    void a() {
        final Puzzle ex = Puzzle.of(exampleData);
        
        var expected = 18;
        var searchFunction = SearchFunction.HORIZONTAL_SEARCH_FUNCTION
                .and(VERTICAL_SEARCH_FUNCTION)
                .and(LEFT_TO_RIGHT_DIAGONAL_SEARCH_FUNCTION)
                .and(RIGHT_TO_LEFT_DIAGONAL_SEARCH_FUNCTION);

        var actual = ex.count("xmas", searchFunction);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void b() {
        final Puzzle ch = Puzzle.of(challengeData);
        
        var expected = 2504;
        var searchFunction = SearchFunction.HORIZONTAL_SEARCH_FUNCTION
                .and(VERTICAL_SEARCH_FUNCTION)
                .and(LEFT_TO_RIGHT_DIAGONAL_SEARCH_FUNCTION)
                .and(RIGHT_TO_LEFT_DIAGONAL_SEARCH_FUNCTION);

        var actual = ch.count("xmas", searchFunction);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void c() {
        final Puzzle ex = Puzzle.of(exampleData);
        
        var expected = 9;
        var actual = ex.count("mas", X_PATTERN_SEARCH_FUNCTION);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void d() {
        final Puzzle ch = Puzzle.of(challengeData);
        
        var expected = 1923; // 1879 low, 19180 high, 1923
        var actual = ch.count("mas", X_PATTERN_SEARCH_FUNCTION);
        Assertions.assertEquals(expected, actual);
    }
}