package com.adventofcode.y2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import com.adventofcode.input.Data;

interface Day2 {

    static long numberOfSafeReports(final Data d) {
        return Day2.getReports(d).stream()
                .filter(Day2::isSafe)
                .count();
    }

    static long numberOfTolerableReports(final Data d) {
        return Day2.getReports(d).stream()
                .filter(report -> isSafe(report) || IntStream.range(0, report.size())
                        .mapToObj(i -> {
                            var candidate = new ArrayList<>(report);
                            candidate.remove(i);
                            return candidate;
                        })
                        .anyMatch(Day2::isSafe))
                .count();
    }

    private static boolean isSafe(final List<Integer> levels) {
        final int MIN_STEP = 1;
        final int MAX_STEP = 3;

        var diffs = IntStream.range(1, levels.size())
                .map(i -> levels.get(i - 1) - levels.get(i))
                .boxed()
                .toList();

        boolean strictlyIncreasing = diffs.stream().allMatch(n -> -MAX_STEP <= n && n <= -MIN_STEP);
        boolean strictlyDecreasing = diffs.stream().allMatch(n -> MIN_STEP <= n && n <= MAX_STEP);
        return strictlyIncreasing || strictlyDecreasing;
    }
    
    private static List<List<Integer>> getReports(final Data d) {
        return d.getLines().stream()
                .filter(l -> !l.isEmpty())
                .map(l -> Arrays.stream(l.trim().split("\\s+"))
                        .map(Integer::parseInt)
                        .toList())
                .filter(list -> !list.isEmpty())
                .toList();
    }
}
