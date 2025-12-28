package com.adventofcode.y2024;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static com.adventofcode.input.Day.DAY10;
import static com.adventofcode.input.Year.YEAR_2024;

import java.io.IOException;

public class Day10Tests {

    private final Data exampleData = new Data(EXAMPLE, YEAR_2024, DAY10);
    private final Data challengeData = new Data(CHALLENGE, YEAR_2024, DAY10);
    
    @Test
    void testA() throws IOException {
        var lavaTrails = new LavaTrails(exampleData);

        var expected = 36;
        var actual = lavaTrails.totalTrailheadScore();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() throws IOException {
        var lavaTrails = new LavaTrails(challengeData);

        var expected = 776;
        var actual = lavaTrails.totalTrailheadScore();

        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    void testC() throws IOException {
        var lavaTrails = new LavaTrails(exampleData);

        var expected = 81;
        var actual = lavaTrails.totalTrailheadRating();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() throws IOException {
        var lavaTrails = new LavaTrails(challengeData);

        var expected = 1657;
        var actual = lavaTrails.totalTrailheadRating();

        Assertions.assertEquals(expected, actual);
    }
}