package com.adventofcode.y2024;

import static com.adventofcode.input.DataSet.CHALLENGE;
import static com.adventofcode.input.DataSet.EXAMPLE;

import com.adventofcode.input.Data;
import com.adventofcode.input.Day;
import com.adventofcode.input.Year;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day7Tests {

    private final Data exampleData = new Data(EXAMPLE, Year.YEAR_2024, Day.DAY7);
    private final Data challengeData = new Data(CHALLENGE, Year.YEAR_2024, Day.DAY7);

    @Test
    void testA() {
        var bridgeRepair = BridgeRepair.parseEquations(exampleData);

        var expected = 3749;
        var actual = bridgeRepair.totalCalibrationResult();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() {
        var bridgeRepair = BridgeRepair.parseEquations(challengeData);

        var expected = 3598800864292L; //3749
        var actual = bridgeRepair.totalCalibrationResult();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() {
        var bridgeRepair = BridgeRepair.parseEquations(exampleData);

        var expected = 11387L;
        var actual = bridgeRepair.totalCalibrationResultWithConcat();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() {
        var bridgeRepair = BridgeRepair.parseEquations(challengeData);

        var expected = 340362529351427L;
        var actual = bridgeRepair.totalCalibrationResultWithConcat();

        Assertions.assertEquals(expected, actual);
    }
}
