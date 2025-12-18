package com.adventofcode.y2025;

import java.util.List;

import com.adventofcode.util.IntegerResults;

record Day1(List<String> data) {
    private static final String LEFT = "L";

    private static final int POS_INDEX = 0;
    private static final int LANDED_INDEX = 1;
    private static final int VISITED_INDEX = 2;

    public IntegerResults solve() {
        var vals = new int[3];
        vals[POS_INDEX] = 50;

        for (var entry : data) {
            var direction = entry.substring(0, 1);
            var distance = entry.substring(1);

            for(int i = 0; i < Integer.parseInt(distance); i++) {
                if (direction.equals(LEFT)) {
                    vals[POS_INDEX] = (vals[POS_INDEX] - 1 + 100) % 100;
                } else {
                    vals[POS_INDEX] = (vals[POS_INDEX] + 1) % 100;
                }
                if (0 == vals[POS_INDEX]) {
                    vals[VISITED_INDEX]++;
                }
            }
            
            if (0 == vals[POS_INDEX]) {
                vals[LANDED_INDEX]++;
            }
        }

        return new IntegerResults(vals[LANDED_INDEX], vals[VISITED_INDEX]);
    }
}
