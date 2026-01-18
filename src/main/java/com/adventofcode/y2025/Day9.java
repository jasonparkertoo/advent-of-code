package com.adventofcode.y2025;

import com.adventofcode.input.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

record Day9(Data d) {

    private static final Function<List<String>, List<Point>> POINT_PARSER = data -> data
            .stream()
            .map(e -> {
                var parts = e.split(",");
                return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            })
            .toList();

    private record Point(long row, long column) {
        long calculateArea(Point p) {
            var width = Math.abs(p.column - this.column) + 1;
            var height = Math.abs(p.row - this.row) + 1;
            return width * height;
        }
    }

    private record Bounds(long minRow, long maxRow, long minCol, long maxCol) {
    }

    private record Interval(long start, long end) {
        boolean contains(long min, long max) {
            return start <= min && end >= max;
        }

        static Interval of(long a, long b) {
            return new Interval(Math.min(a, b), Math.max(a, b));
        }
    }

    long findLargestRectangle() {
        final var points = d.transform(POINT_PARSER);
        return IntStream.range(0, points.size())
                .mapToObj(i -> IntStream.range(i + 1, points.size())
                        .mapToLong(j -> points.get(i).calculateArea(points.get(j))))
                .flatMapToLong(s -> s)
                .max()
                .orElse(0L);
    }

    long findLargestRectangleOfAny() {
        var points = this.d.transform(POINT_PARSER);
        if (points.size() < 2)
            return 0;

        var bounds = calculateBounds(points);

        // Pre-compute valid X-intervals for every Y-row in the bounding box
        var rowRanges = buildValidRowRanges(points, bounds);

        long maxArea = 0;

        // Iterate every pair of points as potential corners of the rectangle
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                var p1 = points.get(i);
                var p2 = points.get(j);

                long area = p1.calculateArea(p2);
                if (area <= maxArea)
                    continue;

                if (isRectangleValid(p1, p2, rowRanges)) {
                    maxArea = area;
                }
            }
        }
        return maxArea;
    }

    private Bounds calculateBounds(List<Point> points) {
        var minRow = points.getFirst().row();
        var maxRow = points.getFirst().row();
        var minCol = points.getFirst().column();
        var maxCol = points.getFirst().column();

        for (var p : points) {
            minRow = Math.min(minRow, p.row());
            maxRow = Math.max(maxRow, p.row());
            minCol = Math.min(minCol, p.column());
            maxCol = Math.max(maxCol, p.column());
        }
        return new Bounds(minRow, maxRow, minCol, maxCol);
    }

    /**
     * Removes collinear vertices from the polygon to simplify edge processing.
     * For example, A-B-C where A, B, C are collinear -> A-C.
     */
    private List<Point> removeCollinearPoints(List<Point> points) {
        if (points.size() < 3)
            return points;

        var result = new ArrayList<>(points);
        boolean changed = true;

        // Repeatedly remove collinear points until stable
        while (changed) {
            changed = false;
            if (result.size() < 3)
                break;

            var reduced = new ArrayList<Point>();
            var n = result.size();

            for (int i = 0; i < n; i++) {
                var p1 = result.get((i - 1 + n) % n);
                var p2 = result.get(i);
                var p3 = result.get((i + 1) % n);

                boolean collinearRow = (p1.row() == p2.row() && p2.row() == p3.row());
                boolean collinearCol = (p1.column() == p2.column() && p2.column() == p3.column());

                if (!collinearRow && !collinearCol) {
                    reduced.add(p2);
                } else {
                    changed = true; // p2 is skipped
                }
            }
            if (changed) {
                result = reduced;
            }
        }
        return result;
    }

    private Map<Long, List<Interval>> buildValidRowRanges(List<Point> polygon, Bounds bounds) {
        var simplePoly = removeCollinearPoints(polygon);
        var rows = new HashMap<Long, List<Interval>>();

        for (long row = bounds.minRow; row <= bounds.maxRow; row++) {
            var intervals = new ArrayList<Interval>();

            // Strategy:
            // 1. Horizontal Edges: Any horizontal edge on this row implies validity between
            // endpoints.
            // 2. Interior Scanline: Find vertical edges crossing 'row + 0.5'.
            // Pairs of crossings define interior intervals.

            intervals.addAll(findHorizontalSegments(simplePoly, row));
            intervals.addAll(findInteriorSegments(simplePoly, row));

            rows.put(row, mergeIntervals(intervals));
        }
        return rows;
    }

    private List<Interval> findHorizontalSegments(List<Point> polygon, long row) {
        var segments = new ArrayList<Interval>();
        for (int i = 0; i < polygon.size(); i++) {
            var p1 = polygon.get(i);
            var p2 = polygon.get((i + 1) % polygon.size());
            // Check for horizontal edge on this row
            if (p1.row() == p2.row() && p1.row() == row) {
                segments.add(Interval.of(p1.column(), p2.column()));
            }
        }
        return segments;
    }

    private List<Interval> findInteriorSegments(List<Point> polygon, long row) {
        var crossingXs = new ArrayList<Long>();
        for (int i = 0; i < polygon.size(); i++) {
            var p1 = polygon.get(i);
            var p2 = polygon.get((i + 1) % polygon.size());

            // Check for vertical edge crossing the "middle" of the row (y + 0.5)
            if (p1.column() == p2.column()) {
                var minY = Math.min(p1.row(), p2.row());
                var maxY = Math.max(p1.row(), p2.row());
                if (minY <= row && maxY > row) {
                    crossingXs.add(p1.column());
                }
            }
        }
        crossingXs.sort(Long::compare);

        var segments = new ArrayList<Interval>();
        // Pairs of crossings define the interior: [In, Out], [In, Out]...
        for (int i = 0; i + 1 < crossingXs.size(); i += 2) {
            segments.add(Interval.of(crossingXs.get(i), crossingXs.get(i + 1)));
        }
        return segments;
    }

    private List<Interval> mergeIntervals(List<Interval> intervals) {
        if (intervals.isEmpty())
            return intervals;

        intervals.sort((a, b) -> Long.compare(a.start, b.start));

        var merged = new ArrayList<Interval>();
        var current = intervals.getFirst();

        for (int i = 1; i < intervals.size(); i++) {
            var next = intervals.get(i);
            // Merge if overlapping or adjacent (e.g., [1,5] and [6,10] merge to [1,10])
            if (next.start <= current.end + 1) {
                current = new Interval(current.start, Math.max(current.end, next.end));
            } else {
                merged.add(current);
                current = next;
            }
        }
        merged.add(current);
        return merged;
    }

    private boolean isRectangleValid(Point p1, Point p2, Map<Long, List<Interval>> rowRanges) {
        long minCol = Math.min(p1.column(), p2.column());
        long maxCol = Math.max(p1.column(), p2.column());
        long minRow = Math.min(p1.row(), p2.row());
        long maxRow = Math.max(p1.row(), p2.row());

        for (long r = minRow; r <= maxRow; r++) {
            if (!rowRanges.containsKey(r))
                return false;

            boolean covered = false;
            for (var interval : rowRanges.get(r)) {
                if (interval.contains(minCol, maxCol)) {
                    covered = true;
                    break;
                }
            }
            if (!covered)
                return false;
        }
        return true;
    }
}
