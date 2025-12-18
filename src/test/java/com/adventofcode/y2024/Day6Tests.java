package com.adventofcode.y2024;

import static com.adventofcode.util.Day.DAY6;
import static com.adventofcode.util.Year.YEAR_2024;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.util.PathUtil;

class Day6Tests {

    @Test
    void testA() {
        var maze = Maze.generate(PathUtil.getExampleDataPath(YEAR_2024, DAY6));
        var path = maze.explore();

        var expected = 41;
        var actual = path.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() {
        var maze = Maze.generate(PathUtil.getChallengeDataPath(YEAR_2024, DAY6));
        var path = maze.explore();

        var expected = 5551;
        var actual = path.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() {
        var maze = Maze.generate(PathUtil.getExampleDataPath(YEAR_2024, DAY6));

        var expected = 6;
        var actual = maze.countLoopPositions();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() {
        var maze = Maze.generate(PathUtil.getChallengeDataPath(YEAR_2024, DAY6));

        var expected = 1939; // 1778 to low
        var actual = maze.countLoopPositions();

        Assertions.assertEquals(expected, actual);
    }
}