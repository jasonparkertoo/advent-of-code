package com.adventofcode.y2025;

import com.adventofcode.input.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

record Day11(Data data) {

    private int numberOfPaths(String input) {
        Map<String, List<String>> graph = Arrays.stream(input.split("\n"))
                .filter(val -> !val.isEmpty())
                .map(val -> {
                    var parts = val.split(": ");
                    var device = parts[0];
                    var outputs = parts[1].split(" ");
                    return new HashMap.SimpleEntry<>(device, Arrays.asList(outputs));
                })
                .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);

        return countPaths(graph, "you", "out");
    }

    int numberOfDifferentPaths() {
        var input = this.data.getLines().stream()
                .filter(line -> !line.isEmpty())
                .reduce(new StringBuilder(), (sb, line) -> sb.append(line).append("\n"), (sb1, sb2) -> sb1.append(sb2.toString()))
                .toString();

        return this.numberOfPaths(input);
    }

    private int countPaths(Map<String, List<String>> graph, String current, String target) {
        if (current.equals(target)) return 1;

        List<String> outputs = graph.get(current);
        if (outputs == null || outputs.isEmpty()) {
            return 0;
        }

        return outputs.stream()
                .mapToInt(nextDevice -> countPaths(graph, nextDevice, target))
                .sum();
    }

    public long numberOfDifferentPathsWithBoth() {
        Map<String, List<String>> graph = this.data.getLines().stream()
                .filter(line -> !line.isEmpty())
                .map(line -> {
                    var parts = line.split(": ");
                    var device = parts[0];
                    var outputs = parts[1].split(" ");
                    return new HashMap.SimpleEntry<>(device, Arrays.asList(outputs));
                })
                .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
        var memo = new HashMap<MemoKey, Long>();
        return countPathsWithBothMemo(graph, new MemoKey("svr", false, false), memo);
    }

    private record MemoKey(String current, boolean seenDAC, boolean seenFFT) {
    }
    
    private long countPathsWithBothMemo(Map<String, List<String>> graph, MemoKey key, Map<MemoKey, Long> memo) {
        var current = key.current();
        var cache = new boolean[2];
        cache[0] = key.seenDAC();
        cache[1] = key.seenFFT();

        var result = memo.get(key);
        if (result != null) {
            return result;
        }

        if ("dac".equals(current)) {
            cache[0] = true;
        }
        if ("fft".equals(current)) {
            cache[1] = true;
        }

        if ("out".equals(current)) {
            if (cache[0] && cache[1]) {
                memo.put(key, 1L);
                return 1;
            }
            memo.put(key, 0L);
            return 0L;
        }

        var outputs = graph.get(current);
        if (outputs == null || outputs.isEmpty()) {
            memo.put(key, 0L);
            return 0;
        }

        var total = outputs.stream()
                .mapToLong(next -> countPathsWithBothMemo(graph, new MemoKey(next, cache[0], cache[1]), memo))
                .sum();

        memo.put(key, total);
        return total;
    }

}