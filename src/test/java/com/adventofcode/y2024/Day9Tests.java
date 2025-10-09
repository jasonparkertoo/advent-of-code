package com.adventofcode.y2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.adventofcode.y2024.util.PathUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

public class Day9Tests {

    private static final String DAY = "day9";

    @Test
    void testA() throws IOException {
        var lines = Files.readAllLines(PathUtil.getExampleData(DAY));
        var hd = new Harddrive(lines.getFirst());

        var expected = 1928;
        var actual = hd.checksum();

        assertEquals(expected, actual);
    }

    @Test
    void testB() throws IOException {
        var lines = Files.readAllLines(PathUtil.getChallengeData(DAY));
        var hd = new Harddrive(lines.getFirst());

        var expected = 6262891638328L;
        var actual = hd.checksum();

        assertEquals(expected, actual);
    }
}