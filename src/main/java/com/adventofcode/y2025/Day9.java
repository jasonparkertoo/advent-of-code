package com.adventofcode.y2025;

import com.adventofcode.input.Data;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

record Day9(Data d) {
    private static final Function<List<String>, List<Point>> dataTransformer = data ->
        data
            .stream()
            .map(e -> {
                var parts = e.split(",");
                return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            })
            .toList();

    private record Point(long row, long column) {
        long calculateArea(Point p) {
            var a = Math.abs(p.column - this.column) + 1;
            var b = Math.abs(p.row - this.row) + 1;
            return a * b;
        }
    }

    long findLargestRectangle() {
        final var points = d.transform(dataTransformer);
        return IntStream.range(0, points.size())
            .mapToObj(i ->
                IntStream.range(i + 1, points.size())
                        .mapToLong(j -> points.get(i).calculateArea(points.get(j)))
            )
            .flatMapToLong(s -> s)
            .max()
            .orElse(0L);
    }
}
