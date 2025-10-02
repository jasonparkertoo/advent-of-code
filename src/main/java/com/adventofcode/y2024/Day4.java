package com.adventofcode.y2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Puzzle(List<List<String>> letters) {
    static Puzzle of(final Path p) {
        try (Stream<String> stream = Files.lines(p)) {
            var l = stream
                    .map(row -> Arrays.asList(row.split("")))
                    .toList();
            return new Puzzle(l);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    Long count(String word, SearchFunction sf) {
        var letters = List.of(word.toUpperCase().split(""));
        return sf.apply(letters, this);
    }
}

interface SearchFunction extends BiFunction<List<String>, Puzzle, Long> {
    SearchFunction HORIZONTAL_SEARCH_FUNCTION = (w, p) -> p.letters().stream()
            .mapToLong(strings -> IntStream.range(0, strings.size() - 3)
                    .mapToObj(col -> strings.subList(col, col + 4))
                    .filter(curr -> w.equals(curr) || w.equals(curr.reversed()))
                    .count())
            .sum();

    SearchFunction VERTICAL_SEARCH_FUNCTION = (w, p) -> IntStream.range(0, p.letters().getFirst().size())
            .mapToLong(col -> IntStream.range(0, p.letters().size() - 3)
                    .mapToObj(
                            row -> IntStream.range(0, 4).mapToObj(i -> p.letters().get(row + i).get(col)).toList())
                    .filter(curr -> w.equals(curr) || w.equals(curr.reversed()))
                    .count())
            .sum();

    SearchFunction RIGHT_TO_LEFT_DIAGONAL_SEARCH_FUNCTION = (w, p) -> {
        var count = 0L;
        for (var i = 0; i < p.letters().size() - 3; i++) {
            for (var j = 3; j < p.letters().size(); j++) {
                List<String> curr = new ArrayList<>();

                var row = i;
                var col = j;
                while (col >= j - 3) {
                    curr.add(p.letters().get(row++).get(col--));
                }
                count += (w.equals(curr) || w.equals(curr.reversed())) ? 1 : 0;
            }
        }
        return count;
    };

    SearchFunction LEFT_TO_RIGHT_DIAGONAL_SEARCH_FUNCTION = (w, p) -> {
        var count = 0L;
        for (var i = 0; i < p.letters().size() - 3; i++) {
            for (var j = 0; j < p.letters().get(i).size() - 3; j++) {
                var curr = new ArrayList<String>();

                var row = i;
                var col = j;
                while (col <= j + 3) {
                    curr.add(p.letters().get(row++).get(col++));
                }

                count += (w.equals(curr) || w.equals(curr.reversed())) ? 1 : 0;
            }
        }
        return count;
    };

    BiPredicate<List<String>, List<String>> MATCHES = (w, candidate) -> w.equals(candidate) || w.equals(candidate.reversed());

    SearchFunction X_PATTERN_SEARCH_FUNCTION = (w, p) -> IntStream.range(0, p.letters().size() - 2)
            .mapToObj(i -> IntStream.range(0, p.letters().get(i).size() - 2)
                    .mapToLong(j -> {
                        final var diag1 = List.of(
                                p.letters().get(i).get(j),
                                p.letters().get(i + 1).get(j + 1),
                                p.letters().get(i + 2).get(j + 2));

                        final var diag2 = List.of(
                                p.letters().get(i).get(j + 2),
                                p.letters().get(i + 1).get(j + 1),
                                p.letters().get(i + 2).get(j));

                        return (MATCHES.test(w, diag1) && MATCHES.test(w, diag2)) ? 1L : 0L;
                    })
                    .sum())
            .mapToLong(Long::longValue)
            .sum();

    default SearchFunction and(SearchFunction other) {
        return (w, p) -> this.apply(w, p) + other.apply(w, p);
    }
}
