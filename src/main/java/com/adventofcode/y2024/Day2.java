package com.adventofcode.y2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Reports(List<List<Integer>> entries) {
    static Reports of(final Path p) {
        try (Stream<String> s = Files.lines(p)) {
            List<List<Integer>> entries = s
                    .filter(l -> !l.isEmpty())
                    .map(l -> Arrays.stream(l.trim().split("\\s+"))
                            .map(Integer::parseInt)
                            .toList())
                    .filter(list -> !list.isEmpty())
                    .toList();
            return new Reports(entries);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

record Safe() {
    static boolean report(final List<Integer> levels) {
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
}

record Analyze() {
    static long numberOfSafeReports(final Reports r) {
        return r.entries().stream()
                .filter(Safe::report)
                .count();
    }

    static long numberOfTolerableReports(final Reports r) {
        return r.entries().stream()
                .filter(report -> Safe.report(report) || IntStream.range(0, report.size())
                        .mapToObj(i -> {
                            var candidate = new ArrayList<>(report);
                            candidate.remove(i);
                            return candidate;
                        })
                        .anyMatch(Safe::report))
                .count();
    }
}
