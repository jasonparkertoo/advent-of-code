package com.adventofcode.y2024;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.input.Data;
import com.adventofcode.input.Day;
import com.adventofcode.input.Year;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;

import java.io.IOException;

class Day8Tests {

    private final Data exampleData = new Data(EXAMPLE, Year.YEAR_2024, Day.DAY8);
    private final Data challengeData = new Data(CHALLENGE, Year.YEAR_2024, Day.DAY8);
    
    @Test
    void testA() throws IOException {
        var city = new City(exampleData);

        var expected = 14;
        var actual = city.countUniqueLocations();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() throws IOException {
        var city = new City(challengeData);

        var expected = 276;
        var actual = city.countUniqueLocations();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() throws IOException {
        var city = new City(exampleData);

        var expected = 34;
        var actual = city.countUniqueLocationsHarmonics();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() throws IOException {
        var city = new City(challengeData);

        var expected = 991;
        var actual = city.countUniqueLocationsHarmonics();

        Assertions.assertEquals(expected, actual);
    }
}