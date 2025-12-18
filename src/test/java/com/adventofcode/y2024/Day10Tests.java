package com.adventofcode.y2024;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.util.PathUtil;

import static com.adventofcode.util.Day.DAY10;
import static com.adventofcode.util.Year.YEAR_2024;

import java.io.IOException;
import java.nio.file.Files;

public class Day10Tests {

    @Test
    void testA() throws IOException {
        var topographicMap = Files.readAllLines(PathUtil.getExampleData(YEAR_2024, DAY10));
        var lavaTrails = new LavaTrails(topographicMap);

        var expected = 36;
        var actual = lavaTrails.totalTrailheadScore();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() throws IOException {
        var topographicMap = Files.readAllLines(PathUtil.getChallengeData(YEAR_2024, DAY10));
        var lavaTrails = new LavaTrails(topographicMap);

        var expected = 776;
        var actual = lavaTrails.totalTrailheadScore();

        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    void testC() throws IOException {
        var topographicMap = Files.readAllLines(PathUtil.getExampleData(YEAR_2024, DAY10));
        var lavaTrails = new LavaTrails(topographicMap);

        var expected = 81;
        var actual = lavaTrails.totalTrailheadRating();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() throws IOException {
        var topographicMap = Files.readAllLines(PathUtil.getChallengeData(YEAR_2024, DAY10));
        var lavaTrails = new LavaTrails(topographicMap);

        var expected = 1657;
        var actual = lavaTrails.totalTrailheadRating();

        Assertions.assertEquals(expected, actual);
    }
}