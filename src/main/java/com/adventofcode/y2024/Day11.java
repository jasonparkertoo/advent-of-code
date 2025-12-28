package com.adventofcode.y2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adventofcode.input.Data;

record Stones(Data data) {

    int numberOfDigits(final long number, final int base) {
        var temp = number;
        if (temp == 0) {
            return 1;
        }
        if (temp < 0) {
            temp = -temp;
        }
        var numberOfDigits = 0;
        while (temp > 0) {
            temp /= base;
            numberOfDigits++;
        }
        return numberOfDigits;
    }

    long powerOfTen(final int number) {
        var half = number / 2;
        var power = 1L;
        while (half > 0) {
            power *= 10;
            half--;
        }
        return power;
    }

    Map.Entry<Long, Long> processNumber(final long number) {
        if (number == 0) {
            return Map.entry(1L, -1L);
        }
        var numDigits = numberOfDigits(number, 10);
        if (numDigits % 2 == 0) {
            var pow = powerOfTen(numDigits);
            return Map.entry(number / pow, number % pow);
        }
        return Map.entry(number * 2024L, -1L);
    }

    long blink(final long number, final long blinks, final Map<List<Long>, Long> memo) {
        var key = List.of(number, blinks);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        if (blinks == 0) {
            return 1;
        }
        var entry = processNumber(number);

        var out = entry.getValue() == -1
                ? blink(entry.getKey(), blinks - 1, memo)
                : blink(entry.getKey(), blinks - 1, memo) + blink(entry.getValue(), blinks - 1, memo);

        memo.put(key, out);
        return out;
    }

    Long numberOfStones(final long numberOfBlinks) {
        final var numbers = new ArrayList<Long>();
        for (var stone : this.data.getLines().getFirst().split(" ")) {
            numbers.add(Long.valueOf(stone));
        }
        final var memo = new HashMap<List<Long>, Long>();
        return numbers.stream()
                .mapToLong(n -> blink(n, numberOfBlinks, memo))
                .sum();
    }
}
