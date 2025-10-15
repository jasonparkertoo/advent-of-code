package com.adventofcode.y2024;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

record Block(int id, int length) {
    boolean isFree() {
        return id < 0;
    }
}

record Harddrive(String diskMap) {
    List<Block> blocks() {
        // Each character index -> length -> block
        final int[] fileId = { 0 }; // need mutable counter inside stream
        return IntStream.range(0, this.diskMap.length())
                .mapToObj(i -> {
                    int len = this.diskMap.charAt(i) - '0';
                    if (len == 0)
                        return null;
                    return i % 2 == 0
                            ? new Block(fileId[0]++, len)
                            : new Block(-1, len); // free
                })
                .filter(Objects::nonNull)
                .toList();
    }

    long checksum() {
        var blocks = blocks();
        var compacted = compact(blocks);
        return checksum(compacted);
    }

    private List<Block> compact(List<Block> original) {
        // Expand into sequence of IDs/-1 using streams
        List<Integer> disk = original.stream()
                .flatMap(b -> IntStream.range(0, b.length()).mapToObj(_ -> b.id()))
                .collect(Collectors.toCollection(ArrayList::new));

        int left = 0, right = disk.size() - 1;
        while (true) {
            while (left < right && disk.get(left) != -1)
                left++;
            while (left < right && disk.get(right) == -1)
                right--;
            if (left >= right)
                break;

            disk.set(left, disk.get(right));
            disk.set(right, -1);
            left++;
            right--;
        }

        // Collapse back into Blocks using grouping by runs
        List<Block> result = new ArrayList<>();
        Iterator<Integer> it = disk.iterator();
        if (!it.hasNext())
            return List.of();

        int current = it.next();
        int count = 1;
        while (it.hasNext()) {
            int next = it.next();
            if (next == current) {
                count++;
            } else {
                result.add(new Block(current, count));
                current = next;
                count = 1;
            }
        }
        result.add(new Block(current, count));
        return result;
    }

    private long checksum(List<Block> blocks) {
        // Stream of block -> stream of (position * id), then sum
        final int[] pos = { 0 };
        return blocks.stream()
                .flatMapToLong(b -> {
                    if (b.isFree()) {
                        pos[0] += b.length();
                        return LongStream.empty();
                    } else {
                        int start = pos[0];
                        pos[0] += b.length();
                        // positions [start, start+b.length)
                        return IntStream.range(0, b.length())
                                .mapToLong(i -> (long) (start + i) * b.id());
                    }
                })
                .sum();
    }
}
