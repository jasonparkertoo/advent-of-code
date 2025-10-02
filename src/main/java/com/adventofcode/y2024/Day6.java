package com.adventofcode.y2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Maze(List<List<String>> layout) {
    static final String GUARD = "^";
    static final String OBSTRUCTION = "#";

    enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    record GuardState(int x, int y, Direction direction) {
        public Position pos() {
            return new Position(x, y);
        }
    }

    record Position(int x, int y) {
    }

    // Maze generation
    static Maze generate(Path p) {
        try (Stream<String> stream = Files.lines(p)) {
            var layout = stream
                    .map(l -> Arrays.stream(l.trim().split("")).toList())
                    .toList();
            return new Maze(layout);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    Position findGuard() {
        return IntStream.range(0, layout.size())
                .boxed()
                .flatMap(y -> IntStream.range(0, layout.get(y).size())
                        .filter(x -> GUARD.equals(layout.get(y).get(x)))
                        .mapToObj(x -> new Position(x, y)))
                .findFirst()
                .orElseThrow();
    }

    // Unified move method
    GuardState move(GuardState state) {
        return move(state, null);
    }

    GuardState move(GuardState state, Position obstruction) {
        int width = layout.getFirst().size();
        int height = layout.size();
        int nx = state.x, ny = state.y;
        Direction dir = state.direction;

        switch (dir) {
            case NORTH -> ny--;
            case EAST -> nx++;
            case SOUTH -> ny++;
            case WEST -> nx--;
        }

        if (nx < 0 || nx >= width || ny < 0 || ny >= height)
            return new GuardState(-1, -1, null);

        boolean blocked = OBSTRUCTION.equals(layout.get(ny).get(nx)) || (obstruction != null && obstruction.x == nx && obstruction.y == ny);

        if (blocked) {
            dir = switch (dir) {
                case NORTH -> Direction.EAST;
                case EAST -> Direction.SOUTH;
                case SOUTH -> Direction.WEST;
                case WEST -> Direction.NORTH;
            };
            return new GuardState(state.x, state.y, dir);
        }

        return new GuardState(nx, ny, dir);
    }

    Set<Position> explore() {
        Position guardPos = findGuard();
        GuardState start = new GuardState(guardPos.x, guardPos.y, Direction.NORTH);

        return Stream.iterate(start, s -> s.direction != null, this::move)
                .map(GuardState::pos)
                .collect(Collectors.toSet());
    }

    boolean causesLoop(Position currentPosition) {
        Position guardStart = findGuard();
        if (OBSTRUCTION.equals(layout.get(currentPosition.y).get(currentPosition.x)) || currentPosition.equals(guardStart)) {
            return false;
        }

        Set<GuardState> visited = new HashSet<>();
        GuardState current = new GuardState(guardStart.x, guardStart.y, Direction.NORTH);

        while (current.direction != null) {
            if (!visited.add(current))
                return true;
            current = move(current, currentPosition);
        }
        return false;
    }

    int countLoopPositions() {
        Position guardPos = findGuard();
        return explore().stream()
                .filter(p -> !p.equals(guardPos))
                .mapToInt(p -> causesLoop(p) ? 1 : 0)
                .sum();
    }
}
