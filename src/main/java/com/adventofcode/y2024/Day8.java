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
    int countUniqueLocations() {
        int height = scan.size();
        int width = scan.getFirst().length();

        // Parse antennas
        List<Antenna> antennas = IntStream.range(0, height)
                .boxed()
                .flatMap(y -> IntStream.range(0, width)
                        .mapToObj(x -> {
                            char c = scan.get(y).charAt(x);
                            return c != '.' ? new Antenna(c, new Pos(x, y)) : null;
                        }))
                .filter(Objects::nonNull)
                .toList();

        // Group by frequency
        Map<Character, List<Antenna>> byFreq = antennas.stream()
                .collect(Collectors.groupingBy(Antenna::freq));

        Set<Pos> antinodes = new HashSet<>();

        byFreq.values().forEach(group -> {
            for (int i = 0; i < group.size(); i++) {
                for (int j = i + 1; j < group.size(); j++) {
                    var a = group.get(i).pos();
                    var b = group.get(j).pos();
                    int dx = b.x() - a.x();
                    int dy = b.y() - a.y();

                    // antinode beyond A
                    Pos ant1 = new Pos(a.x() - dx, a.y() - dy);
                    // antinode beyond B
                    Pos ant2 = new Pos(b.x() + dx, b.y() + dy);

                    Stream.of(ant1, ant2)
                            .filter(p -> p.x() >= 0 && p.y() >= 0 && p.x() < width && p.y() < height)
                            .forEach(antinodes::add);
                }
            }
        });

        return antinodes.size();
    }
}
