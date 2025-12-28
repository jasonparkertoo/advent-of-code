package com.adventofcode.y2024;

import com.adventofcode.input.Data;
import org.junit.jupiter.api.Test;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Day.DAY1;
import static com.adventofcode.input.Year.YEAR_2024;
import static com.adventofcode.y2024.Day1.similarityScore;
import static com.adventofcode.y2024.Day1.totalDistance;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Tests {

    private final Data exampleData = new Data(EXAMPLE, YEAR_2024, DAY1);
    private final Data challengeData = new Data(CHALLENGE, YEAR_2024, DAY1);

    @Test
    void testA() {
        var expected = 11;
        var actual = totalDistance(exampleData);
        assertEquals(actual, expected);
    }

    @Test
    void testB() {
        var expected = 2285373;
        var actual = totalDistance(challengeData);
        assertEquals(actual, expected);
    }

    @Test
    void testC() {
        var expected = 31;
        var actual = similarityScore(exampleData);
        assertEquals(actual, expected);
    }

    @Test
    void testD() {
        var expected = 21142653;
        var actual = similarityScore(challengeData);
        assertEquals(actual, expected);
    }
}
