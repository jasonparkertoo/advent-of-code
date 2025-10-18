package com.adventofcode.y2024;

import com.adventofcode.y2024.util.PathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

public class Day10Tests {

    private static final String DAY = "day10";

    @Test
    void testA() throws IOException {
        var topographicMap = Files.readAllLines(PathUtil.getExampleData(DAY));
        var lavaTrails = new LavaTrails(topographicMap);

        var expected = 36;
        var actual = lavaTrails.totalTrailheadScore();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() throws IOException {
        var topographicMap = Files.readAllLines(PathUtil.getChallengeData(DAY));
        var lavaTrails = new LavaTrails(topographicMap);

        var expected = 776;
        var actual = lavaTrails.totalTrailheadScore();

        Assertions.assertEquals(expected, actual);
    }
}