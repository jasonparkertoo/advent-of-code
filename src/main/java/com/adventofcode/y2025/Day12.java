package com.adventofcode.y2025;

import com.adventofcode.input.Data;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Day 12 solution – fully refactored to use {@code List<String>} for present shapes
 * instead of raw {@code char[][]} arrays.
 *
 * The overall algorithm (parsing, rotation generation, back‑tracking) remains
 * unchanged; only the data representation has been switched to immutable list
 * structures.
 */
record Day12(Data data) {
    /** Entry point matching the original Go implementation. */
    int countValidRegions() {
        List<String> lines = data.getLines();

        List<Present> presents = parsePresents(lines);
        List<Region> regions = parseRegions(lines);

        // Pre‑compute all unique rotations for each present (each rotation is a List<String>).
        List<List<List<String>>> allRotations = presents
            .stream()
            .map(p -> getRotations(p.shape()))
            .toList();

        // Count how many regions can accommodate the required presents.
        return (int) regions
            .stream()
            .filter(region -> canFitAllPresents(region, presents, allRotations))
            .count();
    }

    /* --------------------------------------------------------------------- */
    /*   Model objects – immutable data holders                               */
    /* --------------------------------------------------------------------- */

    /** One present definition – the shape is stored as a list of rows (String). */
    private static record Present(List<String> shape, int index) {}

    /** Region definition together with a list of required present multiplicities. */
    private static record Region(int width, int height, List<Integer> presentCounts) {}

    /** Helper for back‑tracking: the pre‑computed rotations of a present and its cell count. */
    private static record PresentItem(List<List<String>> rotations, int cellCount) {}

    /* --------------------------------------------------------------------- */
    /*   Parsing helpers                                                    */
    /* --------------------------------------------------------------------- */

    /** Parse all present definitions from the input. */
    private static List<Present> parsePresents(List<String> lines) {
        List<Present> result = new ArrayList<>();
        int i = 0;

        // Skip leading blank lines.
        while (i < lines.size() && lines.get(i).trim().isEmpty()) i++;

        while (i < lines.size()) {
            String line = lines.get(i);
            if (!line.contains(":") || line.contains("x")) {
                i++;
                continue;
            }

            // "<index>:" line
            String[] parts = line.split(":", 2);
            int idx;
            try {
                idx = Integer.parseInt(parts[0].trim());
            } catch (NumberFormatException e) {
                i++;
                continue;
            }

            i++; // move to shape rows
            List<String> rows = new ArrayList<>();
            while (i < lines.size() && !lines.get(i).trim().isEmpty() && !lines.get(i).contains(":")) {
                rows.add(lines.get(i));
                i++;
            }

            if (!rows.isEmpty()) {
                result.add(new Present(rows, idx));
            }
        }
        return result;
    }

    /** Parse region specifications from the input. */
    private static List<Region> parseRegions(List<String> lines) {
        List<Region> result = new ArrayList<>();
        int i = 0;

        // Locate first region line (contains both 'x' and ':').
        while (i < lines.size() && !(lines.get(i).contains("x") && lines.get(i).contains(":"))) {
            i++;
        }

        for (; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            String[] side = line.split(":");
            if (side.length != 2) continue;

            String[] dims = side[0].split("x");
            if (dims.length != 2) continue;

            int width, height;
            try {
                width = Integer.parseInt(dims[0].trim());
                height = Integer.parseInt(dims[1].trim());
            } catch (NumberFormatException e) {
                continue;
            }

            List<Integer> counts = Arrays.stream(side[1].trim().split("\\s+"))
                .map(tok -> {
                    try {
                        return Integer.parseInt(tok);
                    } catch (NumberFormatException ex) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

            if (!counts.isEmpty()) {
                result.add(new Region(width, height, counts));
            }
        }
        return result;
    }

    /* --------------------------------------------------------------------- */
    /*   Core algorithm – back‑tracking with list‑based shapes               */
    /* --------------------------------------------------------------------- */

    private static boolean canFitAllPresents(
        Region region,
        List<Present> presents,
        List<List<List<String>>> allRotations
    ) {
        // Sanity: region cannot request more different presents than we have.
        if (region.presentCounts().size() > presents.size()) return false;

        // Quick area check.
        int neededArea = IntStream.range(0, region.presentCounts().size())
            .map(i -> countShapeCells(presents.get(i).shape()) * region.presentCounts().get(i))
            .sum();
        if (neededArea > region.width() * region.height()) return false;

        // Empty grid.
        char[][] grid = new char[region.height()][region.width()];
        for (char[] row : grid) Arrays.fill(row, '.');

        // Build a flat list of items to place (respecting multiplicities) and sort largest first.
        List<PresentItem> toPlace = IntStream.range(0, region.presentCounts().size())
            .mapToObj(i -> {
                int count = region.presentCounts().get(i);
                int cells = countShapeCells(presents.get(i).shape());
                return IntStream.range(0, count).mapToObj(c -> new PresentItem(allRotations.get(i), cells));
            })
            .flatMap(Function.identity())
            .sorted(Comparator.comparingInt(PresentItem::cellCount).reversed())
            .toList();

        return backtrackPlace(toPlace, grid, 0);
    }

    /** Count how many ‘#’ cells a shape (List<String>) occupies. */
    private static int countShapeCells(List<String> shape) {
        return (int) shape
            .stream()
            .flatMapToInt(row -> row.chars().filter(ch -> ch == '#'))
            .count();
    }

    /** Recursive back‑tracking placement of all present items. */
    private static boolean backtrackPlace(List<PresentItem> items, char[][] grid, int idx) {
        if (idx >= items.size()) return true; // everything placed

        PresentItem item = items.get(idx);
        for (List<String> shape : item.rotations()) {
            if (shape.isEmpty()) continue;

            int shapeHeight = shape.size();
            int shapeWidth = shape.get(0).length();
            int maxY = grid.length - shapeHeight + 1;
            int maxX = grid[0].length - shapeWidth + 1;

            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    if (canPlaceFast(shape, grid, x, y)) {
                        placeShape(shape, grid, x, y);
                        if (backtrackPlace(items, grid, idx + 1)) return true;
                        removeShape(shape, grid, x, y);
                    }
                }
            }
        }
        return false; // dead end
    }

    /** Check whether a shape can be placed at (startX,startY) without overlap. */
    private static boolean canPlaceFast(List<String> shape, char[][] grid, int startX, int startY) {
        int h = shape.size();
        int w = shape.get(0).length();
        for (int y = 0; y < h; y++) {
            String row = shape.get(y);
            for (int x = 0; x < w; x++) {
                if (row.charAt(x) != '#') continue;
                int gx = startX + x;
                int gy = startY + y;
                if (gx >= grid[0].length || gy >= grid.length || grid[gy][gx] != '.') {
                    return false;
                }
            }
        }
        return true;
    }

    /** Write a shape onto the grid. */
    private static void placeShape(List<String> shape, char[][] grid, int startX, int startY) {
        int h = shape.size();
        int w = shape.get(0).length();
        for (int y = 0; y < h; y++) {
            String row = shape.get(y);
            for (int x = 0; x < w; x++) {
                if (row.charAt(x) == '#') {
                    grid[startY + y][startX + x] = '#';
                }
            }
        }
    }

    /** Erase a previously placed shape from the grid. */
    private static void removeShape(List<String> shape, char[][] grid, int startX, int startY) {
        int h = shape.size();
        int w = shape.get(0).length();
        for (int y = 0; y < h; y++) {
            String row = shape.get(y);
            for (int x = 0; x < w; x++) {
                if (row.charAt(x) == '#') {
                    grid[startY + y][startX + x] = '.';
                }
            }
        }
    }

    /* --------------------------------------------------------------------- */
    /*   Rotation / reflection utilities (list‑based)                        */
    /* --------------------------------------------------------------------- */

    /**
     * Generate all unique rotations (0°, 90°, 180°, 270°) and their horizontal
     * flips for a shape represented as a {@code List<String>}.
     * Each rotation is also a {@code List<String>}.
     */
    private static List<List<String>> getRotations(List<String> shape) {
        List<List<String>> rotations = new ArrayList<>();
        Set<String> seen = new HashSet<>();

        // Helper: convert a char[][] to List<String> and store if unique.
        java.util.function.Consumer<char[][]> addUnique = arr -> {
            List<String> asList = Arrays.stream(arr).map(String::new).toList();
            String key = String.join("|", asList);
            if (seen.add(key)) rotations.add(asList);
        };

        // Work with temporary char[][] for the actual geometric transforms.
        char[][] original = shape.stream().map(String::toCharArray).toArray(char[][]::new);
        addUnique.accept(original);

        char[][] r90 = rotate90(original);
        addUnique.accept(r90);

        char[][] r180 = rotate90(r90);
        addUnique.accept(r180);

        char[][] r270 = rotate90(r180);
        addUnique.accept(r270);

        char[][] flipped = flip(original);
        addUnique.accept(flipped);

        char[][] fr90 = rotate90(flipped);
        addUnique.accept(fr90);

        char[][] fr180 = rotate90(fr90);
        addUnique.accept(fr180);

        char[][] fr270 = rotate90(fr180);
        addUnique.accept(fr270);

        return rotations;
    }

    /** 90° clockwise rotation on a {@code char[][]}. */
    private static char[][] rotate90(char[][] shape) {
        int rows = shape.length;
        if (rows == 0) return shape;
        int cols = shape[0].length;
        if (cols == 0) return shape;

        char[][] rot = new char[cols][rows];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                rot[x][rows - 1 - y] = shape[y][x];
            }
        }
        return rot;
    }

    /** Horizontal flip (mirror) on a {@code char[][]}. */
    private static char[][] flip(char[][] shape) {
        int rows = shape.length;
        if (rows == 0) return shape;
        int cols = shape[0].length;
        if (cols == 0) return shape;

        char[][] flipped = new char[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                flipped[y][cols - 1 - x] = shape[y][x];
            }
        }
        return flipped;
    }
}
