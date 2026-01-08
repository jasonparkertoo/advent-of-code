package com.adventofcode.input;

import static com.adventofcode.input.DataSet.EXAMPLE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Data {
    private static final String DEMARCATION_MARKER = "-BREAK-";
    private static final Path RESOURCE_DIR = Path.of("src", "test", "resources");

    private final List<String> data;

    public Data(DataSet dataSet, Year year, Day day) {
        final var file = Path.of(RESOURCE_DIR.toString(), year.getYear(), day.getDay());
        try (Stream<String> stream = Files.lines(file)) {
            this.data = dataSet.equals(EXAMPLE)
                    ? stream.takeWhile(l -> !l.equals(DEMARCATION_MARKER)).toList()
                    : stream.dropWhile(l -> !l.equals(DEMARCATION_MARKER)).skip(1).toList();
        } catch (IOException ex) {
            throw new IllegalArgumentException("you suck!", ex);
        }
    }

    public Data(List<String> data) {
        this.data = data;
    }

    public static Data fromFile(final Path file, DataSet dataSet) {
        try (Stream<String> stream = Files.lines(file)) {
            var lines = dataSet.equals(EXAMPLE)
                    ? stream.takeWhile(l -> !l.equals(DEMARCATION_MARKER)).toList()
                    : stream.dropWhile(l -> !l.equals(DEMARCATION_MARKER)).skip(1).toList();
            return new Data(lines);
        } catch (IOException ex) {
            throw new IllegalArgumentException("you suck!", ex);
        }
    }

    public List<String> getLines() {
        return this.data;
    }

    public String getLine(int n) {
        if (n < this.data.size() || n < 1) {
            throw new IllegalArgumentException("invalid line number: " + n);
        }
        return this.data.getFirst();
    }
    
    public List<List<String>> asGrid() {
        final Function<List<String>, List<List<String>>> dataTransformer = data ->
            data.stream()
                .map(r -> r.chars().mapToObj(c -> String.valueOf((char)c)).toList())
                .toList();
        return this.transform(dataTransformer);
    }
    
    public <T> T transform(Function<List<String>, T> fn) {
        return fn.apply(this.data);
    }
}
