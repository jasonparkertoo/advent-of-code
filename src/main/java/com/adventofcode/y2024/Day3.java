package com.adventofcode.y2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.Integer.parseInt;

class StopResumeSpliterator<T> implements Spliterator<T> {
    private final Spliterator<T> source;
    private final T stopToken;
    private final T resumeToken;
    private boolean processing = true;

    StopResumeSpliterator(Spliterator<T> source, T stopToken, T resumeToken) {
        this.source = source;
        this.stopToken = stopToken;
        this.resumeToken = resumeToken;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return source.tryAdvance(elem -> {
            if (Objects.equals(elem, stopToken)) {
                processing = false;
            } else if (Objects.equals(elem, resumeToken)) {
                processing = true;
            } else if (processing) {
                action.accept(elem);
            }
        });
    }

    @Override
    public Spliterator<T> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return source.estimateSize();
    }

    @Override
    public int characteristics() {
        return source.characteristics() & ~Spliterator.SIZED;
    }

    static <T> Stream<T> stream(Collection<T> data, T stop, T resume) {
        var spliterator = new StopResumeSpliterator<>(data.spliterator(), stop, resume);
        return StreamSupport.stream(spliterator, false);
    }
}

record Instruction(String op) {
    String prefix() {
        return this.op.substring(0, op.indexOf("("));
    }

    List<Integer> intValues() {
        var numbers = this.op.replace(prefix(), "")
                .replace(")", "")
                .split(",");
        return Arrays.stream(numbers)
                .map(Integer::parseInt)
                .toList();
    }

    Integer left() {
        var tokens = op.split(",");
        return Integer.parseInt(tokens[0].strip().replace(prefix(), ""));
    }

    Integer right() {
        var tokens = op.split(",");
        return Integer.parseInt(tokens[1].strip().replace(")", ""));
    }
}

enum ScanLevel {
    FULL, MUL
}

record Memory(List<String> sections) {
    private static final String START_TOKEN = "do()";
    private static final String STOP_TOKEN = "don't()";

    static Memory from(final Path p) {
        try (Stream<String> s = Files.lines(p)) {
            return new Memory(s.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Integer processMultiplyInstruction(String instruction) {
        final var INSTRUCTION_PREFIX_REGEX = "mul\\(";
        final var pair = instruction
                .replaceFirst(INSTRUCTION_PREFIX_REGEX, "")
                .replace(")", "")
                .split(",");
        return parseInt(pair[0]) * parseInt(pair[1]);
    }

    int product(ScanLevel level) {
        final var instPattern = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)|don't\\(\\)|do\\(\\)");
        final var instructions = this.sections().stream()
                .flatMap(s -> instPattern.matcher(s).results()).map(MatchResult::group)
                .toList();

        final var stream = switch (level) {
            case FULL -> StopResumeSpliterator.stream(instructions, STOP_TOKEN, START_TOKEN);
            case MUL -> instructions.stream().filter(inst -> !inst.equals(STOP_TOKEN) && !inst.equals(START_TOKEN));
        };

        return stream
                .mapToInt(this::processMultiplyInstruction)
                .sum();
    }
}
