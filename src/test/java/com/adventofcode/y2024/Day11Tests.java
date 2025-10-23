package com.adventofcode.y2024;

import com.adventofcode.y2024.util.PathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Day11Tests {

    private static final String DAY = "day11";

    private final static List<Long> exampleData = new ArrayList<>();
    private final static List<Long> challengeData = new ArrayList<>();

    @BeforeAll
    static void init() {
        try {
            var exampleLine = Files.readAllLines(PathUtil.getExampleData(DAY)).getFirst();
            for (var stone : exampleLine.split(" ")) {
                exampleData.add(Long.valueOf(stone));
            }
            var challengeLine = Files.readAllLines(PathUtil.getChallengeData(DAY)).getFirst();
            for (var stone : challengeLine.split(" ")) {
                challengeData.add(Long.valueOf(stone));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestA() {
        var stones = new Stones(exampleData);

        var expected = 55312;
        var actual = stones.numberOfStones(25, exampleData);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void TestB() {
        var stones = new Stones(challengeData);

        var expected = 202019;
        var actual = stones.numberOfStones(25, challengeData);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void TestC() {
        var stones = new Stones(challengeData);

        var expected = 239321955280205L;
        var actual = stones.numberOfStones(75, challengeData);

        Assertions.assertEquals(expected, actual);
    }
}
