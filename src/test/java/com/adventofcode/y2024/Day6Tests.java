package com.adventofcode.y2024;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;
import com.adventofcode.input.Day;
import com.adventofcode.input.Year;

class Day6Tests {

    private final Data exampleData = new Data(EXAMPLE, Year.YEAR_2024, Day.DAY6);
    private final Data challengeData = new Data(CHALLENGE, Year.YEAR_2024, Day.DAY6);
    
    @Test
    void testA() {
        var maze = Maze.generate(exampleData);
        var path = maze.explore();

        var expected = 41;
        var actual = path.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() {
        var maze = Maze.generate(challengeData);
        var path = maze.explore();

        var expected = 5551;
        var actual = path.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() {
        var maze = Maze.generate(exampleData);

        var expected = 6;
        var actual = maze.countLoopPositions();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() {
        var maze = Maze.generate(challengeData);

        var expected = 1939; // 1778 to low
        var actual = maze.countLoopPositions();

        Assertions.assertEquals(expected, actual);
    }
}