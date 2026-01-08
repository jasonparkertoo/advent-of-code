package com.adventofcode.y2025;

import com.adventofcode.input.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

record Day6(Data d) {
    public static final Function<List<String>, List<List<String>>> dataTransformer = data ->
        data
            .stream()
            .map(r -> Arrays.asList(r.trim().split("\\s+")))
            .toList();

    long calculateGrandTotal() {
        var data = this.d.transform(dataTransformer);
        if (data.isEmpty()) return 0L;

        int columns = data.getFirst().size();
        long[] sums = new long[columns];
        long[] prods = new long[columns];
        Arrays.fill(prods, 1L);

        IntStream.range(0, data.size() - 1).forEach(i -> {
            var row = data.get(i);
            IntStream.range(0, row.size()).forEach(j -> {
                int val = Integer.parseInt(row.get(j));
                sums[j] += val;
                prods[j] *= val;
            });
        });

        var lastRow = data.getLast();
        return IntStream.range(0, lastRow.size())
            .mapToLong(i -> "+".equals(lastRow.get(i)) ? sums[i] : prods[i])
            .sum();
    }

    /**
     * applies the operation to all numbers in the identified group.
     *
     * @param nums number to use for the calulation
     * @param op   the operation to perform
     * @return the result of the calculation
     */
    long calculateBlock(List<Long> nums, String op) {
        return nums
            .stream()
            .reduce((a, b) -> "+".equals(op) ? a + b : a * b)
            .orElse(0L);
    }

    private static final String ADDITION_OPERATOR = "+";
    private static final String MULTIPLICATION_OPERATOR = "*";

    long calculateGrandTotal2() {
        var lines = this.d.getLines();

        if (lines.isEmpty()) return 0L;

        var columns = compileColumns(lines);

        long totalSum = 0L;
        List<Long> currentBlockNumbers = new ArrayList<>();
        String operator = "";

        for (String column : columns) {
            if (column.trim().isEmpty()) {
                if (!currentBlockNumbers.isEmpty() && !operator.isBlank()) {
                    totalSum += calculateBlock(currentBlockNumbers, operator);
                }
                currentBlockNumbers = new ArrayList<>();
                operator = "";
                continue;
            }

            String op = column.substring(column.length() - 1);
            if (ADDITION_OPERATOR.equals(op) || MULTIPLICATION_OPERATOR.equals(op)) {
                operator = op;
            }

            var digitBuilder = new StringBuilder();
            for (var i = 0; i < column.length(); i++) {
                char c = column.charAt(i);
                if (Character.isDigit(c)) {
                    digitBuilder.append(c);
                }
            }

            if (digitBuilder.isEmpty()) {
                continue;
            }

            long num = Long.parseLong(digitBuilder.toString());
            currentBlockNumbers.add(num);
        }

        if (!currentBlockNumbers.isEmpty() && !operator.isBlank()) {
            totalSum += calculateBlock(currentBlockNumbers, operator);
        }

        return totalSum;
    }

    private static ArrayList<String> compileColumns(List<String> lines) {
        int maxLength = lines.stream().mapToInt(String::length).max().orElse(0);

        List<String> cols = IntStream.range(0, maxLength)
            .mapToObj(i ->
                lines
                    .stream()
                    .map(l -> i < l.length() ? String.valueOf(l.charAt(i)) : " ")
                    .collect(Collectors.joining())
            )
            .toList();

        return new ArrayList<>(cols);
    }
}
