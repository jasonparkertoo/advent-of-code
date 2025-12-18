package com.adventofcode.y2024;

import static com.adventofcode.util.Day.DAY7;
import static com.adventofcode.util.Year.YEAR_2024;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.adventofcode.util.PathUtil;

public class Day7Tests {

    @Test
    void testA() {
        var bridgeRepair = BridgeRepair.parseEquations(PathUtil.getExampleData(YEAR_2024, DAY7));

        var expected = 3749;
        var actual = bridgeRepair.totalCalibrationResult();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() {
        var bridgeRepair = BridgeRepair.parseEquations(PathUtil.getChallengeData(YEAR_2024, DAY7));

        var expected = 3598800864292L; //3749
        var actual = bridgeRepair.totalCalibrationResult();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() {
        var bridgeRepair = BridgeRepair.parseEquations(PathUtil.getExampleData(YEAR_2024, DAY7));

        var expected = 11387L;
        var actual = bridgeRepair.totalCalibrationResultWithConcat();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() {
        var bridgeRepair = BridgeRepair.parseEquations(PathUtil.getChallengeData(YEAR_2024, DAY7));

        var expected = 340362529351427L;
        var actual = bridgeRepair.totalCalibrationResultWithConcat();

        Assertions.assertEquals(expected, actual);
    }
}