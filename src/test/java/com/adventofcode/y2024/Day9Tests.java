package com.adventofcode.y2024;

import static com.adventofcode.y2024.Harddrive.CompactMethod.LEFT;
import static com.adventofcode.y2024.Harddrive.CompactMethod.NORM;
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
        var actual = hd.checksum(NORM);

        assertEquals(expected, actual);
    }

    @Test
    void testB() throws IOException {
        var lines = Files.readAllLines(PathUtil.getChallengeData(DAY));
        var hd = new Harddrive(lines.getFirst());

        var expected = 6262891638328L;
        var actual = hd.checksum(NORM);

        assertEquals(expected, actual);
    }

    @Test
    void testC() throws IOException {
        var lines = Files.readAllLines(PathUtil.getExampleData(DAY));
        var hd = new Harddrive(lines.getFirst());

        var expected = 2858;
        var actual = hd.checksum(LEFT);

        assertEquals(expected, actual);
    }

    @Test
    void testD() throws IOException {
        var lines = Files.readAllLines(PathUtil.getChallengeData(DAY));
        var hd = new Harddrive(lines.getFirst());

        var expected = 6287317016845L;
        var actual = hd.checksum(LEFT);

        assertEquals(expected, actual);
    }
}