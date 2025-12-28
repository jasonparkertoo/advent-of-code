package com.adventofcode.input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Data {

    private static final Path RESOURCE_DIR = Path.of("src", "test", "resources");

    private final List<String> data;

    public Data(DataSet dataSet, Year year, Day day) {
        final var file = Path.of(RESOURCE_DIR.toString(), year.getYear(), dataSet.directoryName(), day.getDay());
        try (Stream<String> stream = Files.lines(file)) {
            this.data = stream
                    .filter(l -> !l.isBlank())
                    .toList();
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
        return this.data.get(n);
    }
    
    public <T> T transform(Function<List<String>, T> fn) {
        return fn.apply(this.data);
    }
}