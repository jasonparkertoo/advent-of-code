package com.adventofcode.y2024;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Pos(int x, int y) {
}

record Antenna(char freq, Pos pos) {
}

record City(List<String> scan) {

    // --------------------
    // Public API
    // --------------------
    int countUniqueLocations() {
        return countAntinodes(this::antinodesPart1);
    }

    int countUniqueLocationsHarmonics() {
        return countAntinodes(this::antinodesPart2);
    }

    // --------------------
    // Core shared logic
    // --------------------
    private int countAntinodes(AntinodeStrategy strategy) {
        int height = scan.size();
        int width = scan.getFirst().length();

        List<Antenna> antennas = parseAntennas();
        Map<Character, List<Antenna>> byFreq = antennas.stream()
                .collect(Collectors.groupingBy(Antenna::freq));

        Set<Pos> antinodes = new HashSet<>();

        byFreq.values().forEach(group -> {
            if (group.size() < 2) return;
            for (int i = 0; i < group.size(); i++) {
                for (int j = i + 1; j < group.size(); j++) {
                    antinodes.addAll(strategy.generate(group.get(i).pos(), group.get(j).pos(), width, height));
                }
            }
        });

        return antinodes.size();
    }

    private List<Antenna> parseAntennas() {
        int height = scan.size();
        int width = scan.getFirst().length();

        return IntStream.range(0, height)
                .boxed()
                .flatMap(y -> IntStream.range(0, width)
                        .mapToObj(x -> {
                            char c = scan.get(y).charAt(x);
                            return c != '.' ? new Antenna(c, new Pos(x, y)) : null;
                        }))
                .filter(Objects::nonNull)
                .toList();
    }

    // --------------------
    // Strategies
    // --------------------
    private Set<Pos> antinodesPart1(Pos a, Pos b, int width, int height) {
        int dx = b.x() - a.x();
        int dy = b.y() - a.y();

        Pos ant1 = new Pos(a.x() - dx, a.y() - dy);
        Pos ant2 = new Pos(b.x() + dx, b.y() + dy);

        return Stream.of(ant1, ant2)
                .filter(p -> inBounds(p, width, height))
                .collect(Collectors.toSet());
    }

    private Set<Pos> antinodesPart2(Pos a, Pos b, int width, int height) {
        int dx = b.x() - a.x();
        int dy = b.y() - a.y();

        int g = gcd(Math.abs(dx), Math.abs(dy));
        int sx = dx / g;
        int sy = dy / g;

        // Step back to edge
        int bx = a.x();
        int by = a.y();
        while (inBounds(new Pos(bx - sx, by - sy), width, height)) {
            bx -= sx;
            by -= sy;
        }

        // Walk forward along line
        Set<Pos> result = new HashSet<>();
        int px = bx;
        int py = by;
        while (inBounds(new Pos(px, py), width, height)) {
            result.add(new Pos(px, py));
            px += sx;
            py += sy;
        }

        return result;
    }

    private static boolean inBounds(Pos p, int width, int height) {
        return p.x() >= 0 && p.y() >= 0 && p.x() < width && p.y() < height;
    }

    private static int gcd(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return Math.abs(a);
    }

    // Strategy functional interface
    @FunctionalInterface
    private interface AntinodeStrategy {
        Set<Pos> generate(Pos a, Pos b, int width, int height);
    }
}
