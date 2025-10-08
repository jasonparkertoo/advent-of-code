package com.adventofcode.y2024;

import com.adventofcode.y2024.util.PathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day7Tests {

    private static final String DAY = "day7";

    @Test
    void testA() {
        var bridgeRepair = BridgeRepair.parseEquations(PathUtil.getExampleData(DAY));

        var expected = 3749;
        var actual = bridgeRepair.totalCalibrationResult();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testB() {
        var bridgeRepair = BridgeRepair.parseEquations(PathUtil.getChallengeData(DAY));

        var expected = 3598800864292L; //3749
        var actual = bridgeRepair.totalCalibrationResult();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testC() {
        var bridgeRepair = BridgeRepair.parseEquations(PathUtil.getExampleData(DAY));

        var expected = 11387L;
        var actual = bridgeRepair.totalCalibrationResultWithConcat();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testD() {
        var bridgeRepair = BridgeRepair.parseEquations(PathUtil.getChallengeData(DAY));

        var expected = 340362529351427L;
        var actual = bridgeRepair.totalCalibrationResultWithConcat();

        Assertions.assertEquals(expected, actual);
    }
}