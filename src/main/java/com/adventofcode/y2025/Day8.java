package com.adventofcode.y2025;

import com.adventofcode.input.Data;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

record JunctionBox(int x, int y, int z) {}

record Pair(long distance, int indexA, int indexB) {
    public static List<Pair> generatePairs(List<JunctionBox> boxes) {
        int n = boxes.size();

        return IntStream.range(0, n)
            .boxed()
            .flatMap(i ->
                IntStream.rangeClosed(i + 1, n - 1).mapToObj(j -> createPair(boxes.get(i), boxes.get(j), i, j))
            )
            .sorted(Comparator.comparingLong(Pair::distance))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private static Pair createPair(JunctionBox a, JunctionBox b, int i, int j) {
        long dx = (long) a.x() - b.x();
        long dy = (long) a.y() - b.y();
        long dz = (long) a.z() - b.z();
        long distance = dx * dx + dy * dy + dz * dz;
        return new Pair(distance, i, j);
    }
}

class DSU {

    private final List<Integer> parent;
    private final List<Integer> sizes;

    public DSU(int n) {
        parent = new ArrayList<>();
        sizes = new ArrayList<>();
        for (var i = 0; i < n; i++) {
            parent.add(i);
            sizes.add(1);
        }
    }

    int find(int v) {
        if (this.parent.get(v) != v) {
            this.parent.set(v, this.find(this.parent.get(v)));
        }
        return this.parent.get(v);
    }

    boolean union(int x, int y) {
        var rootX = this.find(x);
        var rootY = this.find(y);

        if (rootX == rootY) {
            return false;
        }

        if (this.sizes.get(rootX) < this.sizes.get(rootY)) {
            int temp = rootX;
            rootX = rootY;
            rootY = temp;
        }

        this.parent.set(rootY, rootX);
        var s = this.sizes.get(rootX) + this.sizes.get(rootY);
        this.sizes.set(rootX, s);

        return true;
    }

    public static List<Integer> process(List<Pair> pairs, int k, int totalBoxes) {
        DSU dsu = new DSU(totalBoxes);
        int connections = 0;
        List<Integer> sizesAtConnection = new ArrayList<>();

        for (Pair p : pairs) {
            if (dsu.union(p.indexA(), p.indexB())) {
                connections++;
                if (connections == k) {
                    // Record the sizes of all components after k connections
                    Map<Integer, Integer> componentSizes = new HashMap<>();
                    for (int i = 0; i < totalBoxes; i++) {
                        int root = dsu.find(i);
                        componentSizes.put(root, componentSizes.getOrDefault(root, 0) + 1);
                    }
                    sizesAtConnection.addAll(componentSizes.values());
                    break;
                }
            }
        }

        return sizesAtConnection;
    }
}

record Day8(Data d) {
    private static final Function<List<String>, List<List<Integer>>> dataTransformer = data ->
        data
            .stream()
            .map(r -> Arrays.asList(r.split(",")))
            .map(r -> r.stream().map(Integer::parseInt).toList())
            .toList();

    private long findLastConnectionXProduct(List<JunctionBox> boxes, List<Pair> pairs) {
        var dsu = new DSU(boxes.size());

        for (var p : pairs) {
            var merged = dsu.union(p.indexA(), p.indexB());

            if (merged) {
                var root = dsu.find(0);
                var allConnected = true;
                for (var i = 0; i < boxes.size(); i++) {
                    if (dsu.find(i) != root) {
                        allConnected = false;
                        break;
                    }
                }

                if (allConnected) {
                    return Math.multiplyExact(boxes.get(p.indexA()).x(), (long) boxes.get(p.indexB()).x());
                }
            }
        }
        return 0L;
    }

    long productOfLastConnection() {
        final List<List<Integer>> data = this.d.transform(dataTransformer);
        final List<JunctionBox> boxes = data
            .stream()
            .map(coordinate -> new JunctionBox(coordinate.getFirst(), coordinate.get(1), coordinate.get(2)))
            .toList();
        var pairs = Pair.generatePairs(boxes);
        return findLastConnectionXProduct(boxes, pairs);
    }

    int productOfThreeLargestCircuits(int k) {
        // Transform input data into JunctionBox list
        var data = this.d.transform(dataTransformer);
        var boxes = data
            .stream()
            .map(coord -> new JunctionBox(coord.getFirst(), coord.get(1), coord.getLast()))
            .toList();

        // Generate all pairs sorted by distance and perform the first k unions
        var pairs = Pair.generatePairs(boxes);
        var dsu = new DSU(boxes.size());
        for (int i = 0; i < Math.min(k, pairs.size()); i++) {
            dsu.union(pairs.get(i).indexA(), pairs.get(i).indexB());
        }

        // Count component sizes using the DSU state
        Map<Integer, Integer> sizeMap = new HashMap<>();
        for (int i = 0; i < boxes.size(); i++) {
            int root = dsu.find(i);
            sizeMap.merge(root, 1, Integer::sum);
        }

        // Compute product of three largest component sizes
        return sizeMap
            .values()
            .stream()
            .sorted(Comparator.reverseOrder())
            .limit(3)
            .mapToInt(Integer::intValue)
            .reduce(1, Math::multiplyExact);
    }
}
