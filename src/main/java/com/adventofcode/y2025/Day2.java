package com.adventofcode.y2025;

import java.util.List;

import com.adventofcode.input.Data;

public record Day2(Data data) {
    
    public List<Long> sumInvalidIds() {
        var ids = this.data.getLines().getFirst().split(",");

        var sum = 0L;
        var sum2 = 0L;
        for (var id : ids) {
            var parts = id.split("-");
            var left = Long.parseLong(parts[0]);
            var right = Long.parseLong(parts[1]);

            for (var i = left; i <= right; i++) {
                var results = this.checkInvalid(String.valueOf(i));
                sum += results.getFirst() ? i : 0;
                sum2 += results.getLast() ? i : 0;
            }
        }

        System.out.println(sum + " " + sum2);
        
        return List.of(sum, sum2);
    }

    private List<Boolean> checkInvalid(String num) {
        var isInv = false;
        
        if (num.length() % 2 == 0) {
            var halfLen = num.length() / 2;
            var left = num.substring(0, halfLen);
            var right = num.substring(halfLen);
            if (left.equals(right)) {
                isInv = true;
            }
        }
        
        var isInv2 = false;
        for (var k = 2; k <= num.length(); k++) {
            if (num.length() % k != 0) {
                continue;
            }
            
            var patternLen = num.length() / k;
            var pattern = num.substring(0, patternLen);
            var valid = true;
            for (var i = 1; i < k; i++) {
                var start = i * patternLen;
                if (!num.substring(start, start + patternLen).equals(pattern)) {
                    valid = false;
                    break;
                }
            }
        
            if (valid) {
                isInv2 = true;
                break;
            }
        }
        
        return List.of(isInv, isInv2);
    }
}
