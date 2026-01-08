package com.adventofcode.y2025;

import com.adventofcode.input.Data;

import java.util.List;
import java.util.Stack;

record Day7(Data d) {
    private static final String START_CHARACTER = "S";
    private static final String SPLITTER_CHARACTER = "^";
    
    private record Pos(int row, int column) {}
    
    private Pos findStart(final List<List<String>> grid) {
        for (var row = 0; row < grid.size(); row++) {
            for (var col = 0; col < grid.get(row).size(); col++) {
                var cell = grid.get(row).get(col);
                if (START_CHARACTER.equals(cell)) {
                    return new Pos(row, col);
                }
            }
        }
        return new Pos(-1, -1);
    }

    private boolean inBounds(final List<List<String>> grid, final Pos pos) {
        if (pos.row() < 0 || pos.row() >= grid.size()) {
            return true;
        }
        if (pos.column() < 0 || pos.column() >= grid.get(pos.row).size()) {
            return true;
        }
        return false;
    }

    private List<Pos> neighbors(final List<List<String>> grid, final Pos pos) {
        if (inBounds(grid, pos)) {
            return List.of();
        }
        var cell = grid.get(pos.row()).get(pos.column());
        if (SPLITTER_CHARACTER.equals(cell)) {
            Pos leftNeighbor = new Pos(pos.row() + 1, pos.column() - 1);
            Pos rightNeighbor = new Pos(pos.row() + 1, pos.column() + 1);
            return List.of(leftNeighbor, rightNeighbor);
        }
        return List.of(new Pos(pos.row()+1, pos.column()));
    }
    
    private int countFromStart(final List<List<String>> grid, final Pos start) {
        var stack = new Stack<Pos>();
        stack.push(start);
        var visited = new Stack<Pos>();
        var splitCount = 0;
        
        while (!stack.isEmpty()) {
            var p = stack.pop();
            
            if (inBounds(grid, p) || visited.contains(p)) {
                continue;
            }
            visited.push(p);
            
            var cell = grid.get(p.row()).get(p.column());
            if (SPLITTER_CHARACTER.equals(cell)) {
                splitCount++;
            }
            
            for (var pos : neighbors(grid, p)) {
                stack.push(pos);
            }
        }
        
        return splitCount;
    }
    
    int countBeamSplits() {
        var grid = this.d.asGrid();
        
        var startPos = this.findStart(grid);
        if (startPos.row() == -1 && startPos.column() == -1) {
            return 0;
        }
        
        return countFromStart(grid, startPos);
    }
}
