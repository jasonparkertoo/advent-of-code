package com.adventofcode.input;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DataTest {

    private static final Path RESOURCE_DIR = Path.of("src", "test", "resources", "tests");

    @Test
    void testGetExampleData() {
        var p = RESOURCE_DIR.resolve("data");
        var data = Data.fromFile(p, EXAMPLE);

        var expected = List.of("abcd");
        var actual = data.getLines();

        assertEquals(expected, actual);
    }

    @Test
    void testGetChallengeData() {
        var p = RESOURCE_DIR.resolve("data");
        var data = Data.fromFile(p, CHALLENGE);

        var expected = List.of("efgh");
        var actual = data.getLines();

        assertEquals(expected, actual);
    }
}