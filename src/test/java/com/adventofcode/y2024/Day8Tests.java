package com.adventofcode.y2024;

import com.adventofcode.y2024.util.PathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

class Day8Tests {
    private static final String DAY = "day8";

    @Test
    void testA() throws IOException {
        List<String> scan = Files.readAllLines(PathUtil.getExampleData(DAY));
        var city = new City(scan);

        var expected = 14;
        var actual = city.countUniqueLocations();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() throws IOException {
        List<String> scan = Files.readAllLines(PathUtil.getChallengeData(DAY));
        var city = new City(scan);

        var expected = 276;
        var actual = city.countUniqueLocations();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() throws IOException {
        List<String> scan = Files.readAllLines(PathUtil.getExampleData(DAY));
        var city = new City(scan);

        var expected = 34;
        var actual = city.countUniqueLocationsHarmonics();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() throws IOException {
        List<String> scan = Files.readAllLines(PathUtil.getChallengeData(DAY));
        var city = new City(scan);

        var expected = 991;
        var actual = city.countUniqueLocationsHarmonics();

        Assertions.assertEquals(expected, actual);
    }
}