package com.adventofcode.y2024;

import com.adventofcode.input.Data;

import static java.util.stream.Collectors.toMap;

import java.util.*;
import java.util.stream.IntStream;

interface Day1 {

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
    
    static int totalDistance(final Data d) {
        final var entries = Day1.getEntries(d);
        final var left = entries.stream()
            .map(Entry::left)
            .sorted()
            .toList();
        final var right = entries.stream()
            .map(Entry::right)
            .sorted()
            .toList();
        return IntStream.range(0, left.size())
                .map(i -> Math.abs(left.get(i) - right.get(i)))
                .sum();
    }

    static int similarityScore(final Data d) {
        final var entries = Day1.getEntries(d);
        final Map<Integer, Integer> om = entries.stream()
                .map(Entry::right)
                .collect(toMap(k -> k, _ -> 1, (ov, _) -> ov + 1));
        return entries.stream()
                .mapToInt(e -> om.getOrDefault(e.left(), 0) * e.left())
                .sum();
    }

    private static List<Entry> getEntries(final Data d) {
        return d.getLines().stream()
                .map(Entry::new)
                .toList();
    }
}
