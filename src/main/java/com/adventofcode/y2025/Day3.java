package com.adventofcode.y2025;

import java.util.List;

import com.adventofcode.input.Data;

record Day3(Data data) {
    List<Integer> totalOutputVoltage() {
        int sum = 0;
        for (var bank : this.data.getLines()) {
            int left = 0, right = 0;
            for (var i = 0; i < bank.length(); i++) {
                var num = Integer.parseInt(String.valueOf(bank.charAt(i)));
                if (num > left) {
                    if (i == bank.length() - 1) {
                        right = num;
                    } else {
                        left = num;
                        right = 0;
                    }
                } else {
                    if (num > right) {
                        right = num;
                    }
                }
            }
            sum += (left * 10) + right;
        }
        return List.of(sum);
    }
}
