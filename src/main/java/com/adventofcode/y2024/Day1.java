package com.adventofcode.y2024;

import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Entry(String s) {
    int left() {
        var n = s.strip().substring(0, s.indexOf(" "));
        return Integer.parseInt(n);
    }

    int right() {
        var n = s.strip().substring(s.indexOf(" "));
        return Integer.parseInt(n.strip());
    }
}

record Day1(List<Entry> entries) {

    static Day1 of(final Path p) {
        try (Stream<String> stream = Files.lines(p)) {
            var entries = stream
                    .map(Entry::new)
                    .toList();
            return new Day1(entries);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    int totalDistance() {
        final var left = this.entries().stream()
            .map(Entry::left)
            .sorted()
            .toList();
        final var right = this.entries().stream()
            .map(Entry::right)
            .sorted()
            .toList();
        return IntStream.range(0, left.size())
                .map(i -> Math.abs(left.get(i) - right.get(i)))
                .sum();
    }

    int similarityScore() {
        final Map<Integer, Integer> om = this.entries().stream()
                .map(Entry::right)
                .collect(toMap(k -> k, _ -> 1, (ov, _) -> ov + 1));
        return this.entries().stream()
                .mapToInt(e -> om.getOrDefault(e.left(), 0) * e.left())
                .sum();
    }
}
