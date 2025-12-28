package com.adventofcode.y2024;

import com.adventofcode.input.Data;
import java.util.ArrayList;
import java.util.List;

record Equation(Long result, List<Long> numbers) {}

record BridgeRepair(List<Equation> equations) {
    static BridgeRepair parseEquations(final Data d) {
        var eq = d
            .getLines()
            .stream()
            .map(l -> {
                long result = Long.MIN_VALUE;
                var numbers = new ArrayList<Long>();
                for (var num : l.split("\\s")) {
                    if (num.contains(":")) {
                        result = Long.parseLong(num.replace(":", ""));
                    } else {
                        numbers.add(Long.parseLong(num));
                    }
                }
                return new Equation(result, numbers);
            })
            .toList();
        return new BridgeRepair(eq);
    }

    boolean isValid(List<Long> numbers, long target, int index, long currentValue) {
        // Base case: processed all numbers
        if (index == numbers.size()) {
            return currentValue == target;
        }

        long nextNumber = numbers.get(index);

        // Try addition
        if (isValid(numbers, target, index + 1, currentValue + nextNumber)) {
            return true;
        }

        // Try multiplication
        return isValid(numbers, target, index + 1, currentValue * nextNumber);
    }

    boolean isValidWithConcat(List<Long> numbers, long target, int index, long currentValue) {
        // Base case: processed all numbers
        if (index == numbers.size()) {
            return currentValue == target;
        }

        long nextNumber = numbers.get(index);

        // Try addition
        if (isValidWithConcat(numbers, target, index + 1, currentValue + nextNumber)) {
            return true;
        }

        // Try multiplication
        if (isValidWithConcat(numbers, target, index + 1, currentValue * nextNumber)) {
            return true;
        }

        // Try concatenation (only in the concat version)
        return isValidWithConcat(numbers, target, index + 1, Long.parseLong(currentValue + "" + nextNumber));
    }

    long totalCalibrationResult() {
        return this.equations.stream()
            .filter(eq -> isValid(eq.numbers(), eq.result(), 1, eq.numbers().getFirst()))
            .mapToLong(Equation::result)
            .sum();
    }

    long totalCalibrationResultWithConcat() {
        return this.equations.stream()
            .filter(eq -> isValidWithConcat(eq.numbers(), eq.result(), 1, eq.numbers().getFirst()))
            .mapToLong(Equation::result)
            .sum();
    }
}
