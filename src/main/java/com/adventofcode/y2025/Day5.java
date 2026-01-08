package com.adventofcode.y2025;

import com.adventofcode.input.Data;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

record Day5(Data d) {
    record Range(long start, long end) {}

    private List<Range> parseRanges() {
        return d
            .getLines()
            .stream()
            .takeWhile(l -> l.contains("-"))
            .map(this::parseRange)
            .collect(Collectors.toList());
    }

    private Range parseRange(String line) {
        var parts = line.split("-");
        return new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

    private List<Long> parseIds(List<Range> ranges) {
        int skipCount = ranges.size() + 1;
        return d.getLines().stream().skip(skipCount).map(Long::parseLong).collect(Collectors.toList());
    }

    public long getFreshCount() {
        final var ranges = parseRanges();
        final var ids = parseIds(ranges);
        return ids
            .stream()
            .filter(id -> ranges.stream().anyMatch(r -> id >= r.start && id <= r.end))
            .count();
    }

    public long totalRangeCount() {
        return parseRanges()
            .stream()
            .sorted(Comparator.comparingLong(r -> r.start))
            .collect(
                () -> new java.util.ArrayList<Range>(),
                (list, r) -> {
                    if (list.isEmpty() || r.start > list.get(list.size() - 1).end + 1) {
                        list.add(r);
                    } else {
                        Range last = list.get(list.size() - 1);
                        list.set(list.size() - 1, new Range(last.start, Math.max(last.end, r.end)));
                    }
                },
                (a, b) -> a.addAll(b)
            )
            .stream()
            .mapToLong(r -> r.end - r.start + 1)
            .sum();
    }
}
