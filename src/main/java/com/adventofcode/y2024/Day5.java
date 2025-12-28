package com.adventofcode.y2024;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.adventofcode.input.Data;

record Rule(Integer left, Integer right) {
}

record SafetyManual(List<Integer> pageNumbers, List<Rule> rules) {
}

class PrintQueue {
    final Data d;

    PrintQueue(Data d) {
        this.d = d;
    }

    public List<SafetyManual> parseData() {
        var data = d.getLines();

        final var pageNumbers = data.stream()
                .filter(l -> l.contains(","))
                .map(l -> Arrays.stream(l.split(",")).map(Integer::parseInt).toList())
                .toList();

        final var rules = data.stream()
                .filter(l -> l.contains("|"))
                .map(l -> l.split("\\|"))
                .map(a -> new Rule(Integer.parseInt(a[0]), Integer.parseInt(a[1])))
                .toList();

        final BiPredicate<List<Integer>, Rule> matchesRule = (u, r) ->
                u.contains(r.left()) && u.contains(r.right());

        return pageNumbers.stream()
                .map(u -> new SafetyManual(u, rules.stream()
                        .filter(r -> matchesRule.test(u, r))
                        .toList()))
                .toList();
    }

    BiPredicate<SafetyManual, Rule> conformsToRule = (s, r) -> {
        var left = s.pageNumbers().indexOf(r.left());
        var right = s.pageNumbers().indexOf(r.right());
        return left < right; // left should come before right
    };

    int sumMiddlePageNumbers(List<SafetyManual> safetyManuals) {
        return safetyManuals.stream()
                .filter(s -> s.rules().stream().allMatch(r -> conformsToRule.test(s, r)))
                .mapToInt(s -> s.pageNumbers().get(s.pageNumbers().size() / 2))
                .sum();
    }

    int sumIncorrectMiddlePageNumbers(List<SafetyManual> safetyManuals) {
        return safetyManuals.stream()
                .filter(s -> s.rules().stream().anyMatch(r -> !conformsToRule.test(s, r)))
                .map(this::correctPageOrder)
                .mapToInt(correctedPages -> correctedPages.get(correctedPages.size() / 2))
                .sum();
    }

    private List<Integer> correctPageOrder(SafetyManual manual) {
        // Build graph and in-degree using streams
        Map<Integer, Set<Integer>> graph = manual.pageNumbers().stream()
                .collect(Collectors.toMap(
                        page -> page,
                        _ -> new HashSet<>()
                ));

        Map<Integer, Long> inDegree = manual.pageNumbers().stream()
                .collect(Collectors.toMap(
                        page -> page,
                        _ -> 0L
                ));

        // Add edges based on rules
        manual.rules().stream()
                .filter(rule -> manual.pageNumbers().contains(rule.left()) && manual.pageNumbers().contains(rule.right()))
                .forEach(rule -> {
                    graph.get(rule.left()).add(rule.right());
                    inDegree.put(rule.right(), inDegree.get(rule.right()) + 1);
                });

        // Topological sort using streams
        return topologicalSort(graph, inDegree);
    }

    private List<Integer> topologicalSort(Map<Integer, Set<Integer>> graph, Map<Integer, Long> inDegree) {
        return Stream.generate(() -> {
                    // Find nodes with in-degree 0
                    List<Integer> zeroDegreeNodes = inDegree.entrySet().stream()
                            .filter(entry -> entry.getValue() == 0)
                            .map(Map.Entry::getKey)
                            .toList();

                    // Remove these nodes and update in-degrees
                    zeroDegreeNodes.forEach(node -> {
                        inDegree.remove(node);
                        graph.get(node).forEach(neighbor ->
                                inDegree.put(neighbor, inDegree.get(neighbor) - 1)
                        );
                    });

                    return zeroDegreeNodes;
                })
                .takeWhile(nodes -> !nodes.isEmpty())
                .flatMap(List::stream)
                .toList();
    }
}