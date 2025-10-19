package com.adventofcode.y2024;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Point(int r, int c) {
}

class LavaTrails {
    private final int[][] grid;
    private final int rows, cols;
    private final Map<Point, Set<Point>> memo = new HashMap<>();

    LavaTrails(List<String> lines) {
        this.rows = lines.size();
        this.cols = lines.getFirst().length();
        this.grid = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = lines.get(r).charAt(c) - '0';
            }
        }
    }

    private int height(Point p) {
        return grid[p.r()][p.c()];
    }

    private List<Point> neighbors(Point p) {
        return Stream.of(
                new Point(p.r() - 1, p.c()),
                new Point(p.r() + 1, p.c()),
                new Point(p.r(), p.c() - 1),
                new Point(p.r(), p.c() + 1))
                .filter(n -> n.r() >= 0 && n.c() >= 0 && n.r() < rows && n.c() < cols)
                .toList();
    }

    private Set<Point> reachableNines(Point p) {
        var cached = memo.get(p);
        if (cached != null)
            return cached;

        int h = height(p);
        Set<Point> result = (h == 9)
                ? Set.of(p)
                : neighbors(p).stream()
                        .filter(n -> height(n) == h + 1)
                        .flatMap(n -> reachableNines(n).stream())
                        .collect(Collectors.toUnmodifiableSet());

        memo.put(p, result);
        return result;
    }

    int totalTrailheadScore() {
        return IntStream.range(0, rows)
                .boxed()
                .flatMap(r -> IntStream.range(0, cols)
                        .mapToObj(c -> new Point(r, c)))
                .filter(p -> height(p) == 0)
                .mapToInt(p -> reachableNines(p).size())
                .sum();
    }

    private int countPaths(Point point, Map<Point, Integer> memo) {
        if (memo.containsKey(point)) {
            return memo.get(point);
        }

        var h = height(point);

        int count = 0;
        if (h == 9) {
            count = 1;
        } else {
            for (Point p : neighbors(point)) {
                if (height(p) == h+1) {
                    count += countPaths(p, memo);
                }
            }
        }

        memo.put(point, count);
        return count;
    }
    
    int totalTrailheadRating() {
        var sum = 0;
        for (var row = 0; row < this.rows; row++) {
            for (var col = 0; col < this.cols; col++) {
                var p = new Point(row, col);
                if (height(p) == 0) {
                    var memo = new HashMap<Point, Integer>();
                    sum += countPaths(p, memo);
                }
            }
        }
        return sum;
    }
}
