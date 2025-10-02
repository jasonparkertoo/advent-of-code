package com.adventofcode.y2024;

import com.adventofcode.y2024.util.PathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day6Tests {

    private static final String DAY = "day6";

    @Test
    void testA() {
        var maze = Maze.generate(PathUtil.getExampleData(DAY));
        var path = maze.explore();

        var expected = 41;
        var actual = path.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() {
        var maze = Maze.generate(PathUtil.getChallengeData(DAY));
        var path = maze.explore();

        var expected = 5551;
        var actual = path.size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() {
        var maze = Maze.generate(PathUtil.getExampleData(DAY));

        var expected = 6;
        var actual = maze.countLoopPositions();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() {
        var maze = Maze.generate(PathUtil.getChallengeData(DAY));

        var expected = 1939; // 1778 to low
        var actual = maze.countLoopPositions();

        Assertions.assertEquals(expected, actual);
    }
}