package com.adventofcode.y2024;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import com.adventofcode.input.Data;

record Block(int id, int length) {
    boolean isFree() {
        return id < 0;
    }
}

record Harddrive(Data data) {
    List<Block> blocks() {
        var diskMap = this.data.getLines().getFirst();
        
        // Each character index -> length -> block
        final int[] fileId = {0}; // need mutable counter inside stream
        return IntStream.range(0, diskMap.length())
                .mapToObj(i -> {
                    int len = diskMap.charAt(i) - '0';
                    if (len == 0)
                        return null;
                    return i % 2 == 0
                            ? new Block(fileId[0]++, len)
                            : new Block(-1, len); // free
                })
                .filter(Objects::nonNull)
                .toList();
    }

    enum CompactMethod {
        NORM, LEFT
    }

    long checksum(CompactMethod method) {
        var blocks = switch (method) {
            case LEFT -> compactLeft();
            case NORM -> compact();
        };
        return checksum(blocks);
    }

    private List<Block> compact() {
        var blocks = blocks();

        // Expand into sequence of IDs/-1 using streams
        List<Integer> disk = blocks.stream()
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

    private List<Block> compactLeft() {
        var diskMap = this.data.getLines().getFirst();
        
        List<Block> blocks = new ArrayList<>();
        boolean isFile = true;
        int fileId = 0;

        // Parse disk map into blocks once
        for (char c : diskMap.toCharArray()) {
            int length = c - '0';
            if (length > 0) {
                blocks.add(new Block(isFile ? fileId++ : -1, length));
            }
            isFile = !isFile;
        }

        // Move whole files left, in reverse ID order
        for (int moveId = fileId - 1; moveId >= 0; moveId--) {
            // Find the file block to move
            int fileIndex = -1;
            Block fileBlock = null;
            for (int i = 0; i < blocks.size(); i++) {
                Block b = blocks.get(i);
                if (b.id() == moveId) {
                    fileIndex = i;
                    fileBlock = b;
                    break;
                }
            }
            if (fileBlock == null) continue;

            // Find free space before the file
            int targetIndex = -1;
            for (int i = 0; i < fileIndex; i++) {
                Block b = blocks.get(i);
                if (b.isFree() && b.length() >= fileBlock.length()) {
                    targetIndex = i;
                    break;
                }
            }
            if (targetIndex == -1) continue;

            Block freeBlock = blocks.get(targetIndex);

            // Move the file block
            List<Block> updated = new ArrayList<>();

            // Copy blocks before target
            for (int i = 0; i < targetIndex; i++) {
                updated.add(blocks.get(i));
            }

            // Add file block in free space
            updated.add(new Block(fileBlock.id(), fileBlock.length()));

            // Handle remaining free space
            int remaining = freeBlock.length() - fileBlock.length();
            if (remaining > 0) {
                updated.add(new Block(-1, remaining));
            }

            // Copy blocks between target and file (excluding both)
            for (int i = targetIndex + 1; i < fileIndex; i++) {
                updated.add(blocks.get(i));
            }

            // Replace file block with free space
            updated.add(new Block(-1, fileBlock.length()));

            // Copy blocks after file
            for (int i = fileIndex + 1; i < blocks.size(); i++) {
                updated.add(blocks.get(i));
            }

            blocks = updated;

            // Merge adjacent free blocks
            List<Block> merged = new ArrayList<>();
            for (Block block : blocks) {
                if (!merged.isEmpty() && merged.getLast().isFree() && block.isFree()) {
                    Block last = merged.removeLast();
                    merged.add(new Block(-1, last.length() + block.length()));
                } else {
                    merged.add(block);
                }
            }
            blocks = merged;
        }

        return blocks;
    }

    private long checksum(List<Block> blocks) {
        // Stream of block -> stream of (position * id), then sum
        final int[] pos = {0};
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
