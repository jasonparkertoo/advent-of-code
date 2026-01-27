package com.adventofcode.y2025;

import com.adventofcode.input.Data;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

record Day10(Data data) {
    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\(([^)]*)\\)");
    private static final char LIGHT_ON = '#';
    private static final char LIGHT_OFF = '.';
    private static final int BRUTE_FORCE_THRESHOLD = 20;

    public int fewestButtonPresses() {
        return data.getLines().stream().map(this::parseLightMachine).mapToInt(this::findMinimumPresses).sum();
    }

    private LightMachine parseLightMachine(final String line) {
        final int diagramStart = line.indexOf('[');
        final int diagramEnd = line.indexOf(']', diagramStart + 1);

        validateBrackets(line, diagramStart, diagramEnd);

        final String diagramSection = line.substring(diagramStart + 1, diagramEnd);
        final String buttonSection = line.substring(diagramEnd + 1);

        final BigInteger targetState = parseLightDiagram(diagramSection);
        final List<BigInteger> buttonMasks = parseButtonMasks(buttonSection);

        return new LightMachine(targetState, buttonMasks);
    }

    private void validateBrackets(final String line, final int start, final int end) {
        if (start == -1) {
            throw new IllegalArgumentException("Missing opening '[' in: " + line);
        }
        if (end == -1) {
            throw new IllegalArgumentException("Missing closing ']' in: " + line);
        }
    }

    private BigInteger parseLightDiagram(final String diagram) {
        return IntStream.range(0, diagram.length())
            .mapToObj(i -> {
                final char ch = diagram.charAt(i);
                if (ch == LIGHT_ON) {
                    return BigInteger.ONE.shiftLeft(i);
                } else if (ch != LIGHT_OFF) {
                    throw new IllegalArgumentException("Invalid character '" + ch + "' in diagram");
                }
                return BigInteger.ZERO;
            })
            .reduce(BigInteger.ZERO, BigInteger::add);
    }

    private List<BigInteger> parseButtonMasks(final String buttonSpec) {
        return BUTTON_PATTERN.matcher(buttonSpec)
            .results()
            .map(matchResult -> matchResult.group(1).trim())
            .map(this::createButtonMask)
            .collect(Collectors.toList());
    }

    private BigInteger createButtonMask(final String commaSeparatedIndices) {
        if (commaSeparatedIndices.isEmpty()) {
            return BigInteger.ZERO;
        }

        return Arrays.stream(commaSeparatedIndices.split(","))
            .map(String::trim)
            .map(indexStr -> {
                final int index = Integer.parseInt(indexStr);
                if (index < 0) {
                    throw new IllegalArgumentException("Negative button index: " + index);
                }
                return index;
            })
            .reduce(BigInteger.ZERO, BigInteger::setBit, BigInteger::or);
    }

    private int findMinimumPresses(final LightMachine machine) {
        if (machine.buttonMasks.isEmpty()) {
            return machine.targetState.equals(BigInteger.ZERO) ? 0 : -1;
        }

        if (machine.targetState.equals(BigInteger.ZERO)) {
            return 0;
        }

        final int buttonCount = machine.buttonMasks.size();
        return buttonCount <= BRUTE_FORCE_THRESHOLD
                ? searchBruteForce(machine)
                : searchMeetInMiddle(machine);
    }

    private int searchBruteForce(final LightMachine machine) {
        final int totalCombinations = 1 << machine.buttonMasks.size();
        final int minPresses = IntStream.range(0, totalCombinations)
            .filter(combo -> {
                final int pressCount = Integer.bitCount(combo);
                return pressCount < Integer.MAX_VALUE; // Always true, but matches original logic
            })
            .map(combo -> {
                final int pressCount = Integer.bitCount(combo);
                final BigInteger resultState = computeStateFromCombo(machine.buttonMasks, combo);
                return resultState.equals(machine.targetState) ? pressCount : Integer.MAX_VALUE;
            })
            .filter(value -> value != Integer.MAX_VALUE)
            .min()
            .orElseThrow(() -> new IllegalArgumentException("No solution found"));

        return minPresses;
    }

    private int searchMeetInMiddle(final LightMachine machine) {
        final int halfIndex = machine.buttonMasks.size() / 2;

        final List<StateWithPresses> firstHalfStates = generateAllStates(machine.buttonMasks, 0, halfIndex);
        final List<StateWithPresses> secondHalfStates = generateAllStates(
            machine.buttonMasks,
            halfIndex,
            machine.buttonMasks.size()
        );

        final Map<BigInteger, Integer> complementMap = secondHalfStates.stream()
            .collect(HashMap::new, (map, info) -> {
                // What do we need from first half? first XOR second = target
                // So: first = second XOR target
                final BigInteger neededFromFirst = info.state.xor(machine.targetState);
                map.merge(neededFromFirst, info.presses, Math::min);
            }, HashMap::putAll);

        return firstHalfStates.stream()
            .filter(firstHalf -> complementMap.containsKey(firstHalf.state))
            .mapToInt(firstHalf -> firstHalf.presses + complementMap.get(firstHalf.state))
            .min()
            .orElseThrow(() -> new IllegalArgumentException("No solution found"));
    }

    private List<StateWithPresses> generateAllStates(final List<BigInteger> buttons, final int startIdx, final int endIdx) {
        final int rangeSize = endIdx - startIdx;
        final int totalCombinations = 1 << rangeSize;

        return IntStream.range(0, totalCombinations)
            .mapToObj(combo -> {
                BigInteger state = BigInteger.ZERO;
                int presses = 0;

                for (int i = 0; i < rangeSize; i++) {
                    if ((combo & (1 << i)) != 0) {
                        state = state.xor(buttons.get(startIdx + i));
                        presses++;
                    }
                }

                return new StateWithPresses(state, presses);
            })
            .collect(Collectors.toList());
    }

    private BigInteger computeStateFromCombo(final List<BigInteger> buttons, int combo) {
        return IntStream.range(0, buttons.size())
            .filter(i -> (combo & (1 << i)) != 0)
            .mapToObj(buttons::get)
            .reduce(BigInteger.ZERO, BigInteger::xor);
    }

    public int calculateScore() {
        return data.getLines().stream()
                .mapToInt(this::scoreConfiguration)
                .sum();
    }

    private int scoreConfiguration(final String config) {
        return Arrays.stream(config.split("\n"))
            .map(line -> {
                final String[] parts = line.split("\\s+");
                final List<Integer> goal = parseIntegerList(parts[parts.length - 1]);
                final List<List<Integer>> coefficients = parseCoefficients(parts, goal.size());
                final Map<ParityPattern, Map<List<Integer>, Integer>> patternCosts = buildPatternCostLookup(coefficients);
                return solveRecursively(goal, patternCosts, new HashMap<>());
            })
            .mapToInt(Integer::intValue)
            .sum();
    }

    private List<List<Integer>> parseCoefficients(final String[] parts, final int goalSize) {
        return IntStream.range(1, parts.length - 1)
            .mapToObj(i -> parseIntegerList(parts[i]))
            .map(coefficient -> toBinaryPresenceVector(coefficient, goalSize))
            .collect(Collectors.toList());
    }

    private List<Integer> toBinaryPresenceVector(final List<Integer> indices, final int size) {
        final List<Integer> vector = new ArrayList<>(Collections.nCopies(size, 0));
        indices.stream()
            .filter(index -> index < size)
            .forEach(index -> vector.set(index, 1));
        return vector;
    }

    private int solveRecursively(final List<Integer> goal, final Map<ParityPattern, Map<List<Integer>, Integer>> patternCosts, final Map<List<Integer>, Integer> memo) {
        if (memo.containsKey(goal)) {
            return memo.get(goal);
        }

        if (isAllZeros(goal)) {
            return 0;
        }

        final ParityPattern parity = new ParityPattern(
            goal
                .stream()
                .map(i -> i % 2)
                .collect(Collectors.toList())
        );

        final Map<List<Integer>, Integer> validPatterns = patternCosts.get(parity);

        final int minCost = validPatterns.entrySet().stream()
            .filter(entry -> canApplyPattern(entry.getKey(), goal))
            .mapToInt(entry -> {
                final List<Integer> pattern = entry.getKey();
                final int patternCost = entry.getValue();
                final List<Integer> reducedGoal = reduceGoal(goal, pattern);
                return patternCost + 2 * solveRecursively(reducedGoal, patternCosts, memo);
            })
            .min()
            .orElse(1_000_000);

        memo.put(goal, minCost);
        return minCost;
    }

    private boolean isAllZeros(final List<Integer> list) {
        return list.stream().allMatch(i -> i == 0);
    }

    private boolean canApplyPattern(final List<Integer> pattern, final List<Integer> goal) {
        return IntStream.range(0, pattern.size())
            .allMatch(i -> pattern.get(i) <= goal.get(i));
    }

    private List<Integer> reduceGoal(final List<Integer> goal, final List<Integer> pattern) {
        return IntStream.range(0, goal.size())
            .mapToObj(i -> (goal.get(i) - pattern.get(i)) / 2)
            .collect(Collectors.toList());
    }

    private Map<ParityPattern, Map<List<Integer>, Integer>> buildPatternCostLookup(final List<List<Integer>> coefficients) {
        final int numButtons = coefficients.size();
        final int numVariables = coefficients.get(0).size();

        final Map<ParityPattern, Map<List<Integer>, Integer>> lookup = new HashMap<>();

        for (final List<Integer> parityList : generateAllParityPatterns(numVariables)) {
            lookup.put(new ParityPattern(parityList), new HashMap<>());
        }

        for (int k = 0; k <= numButtons; k++) {
            for (final List<Integer> buttonIndices : combinations(numButtons, k)) {
                final List<Integer> pattern = sumCoefficients(coefficients, buttonIndices, numVariables);
                final ParityPattern parity = new ParityPattern(
                    pattern
                        .stream()
                        .map(i -> i % 2)
                        .collect(Collectors.toList())
                );

                lookup.get(parity).putIfAbsent(pattern, k);
            }
        }

        return lookup;
    }

    private List<Integer> sumCoefficients(final List<List<Integer>> coefficients, final List<Integer> buttonIndices, final int numVariables) {
        final List<Integer> sum = new ArrayList<>(Collections.nCopies(numVariables, 0));

        buttonIndices.stream()
            .map(coefficients::get)
            .forEach(coefficient -> {
                for (int i = 0; i < numVariables; i++) {
                    sum.set(i, sum.get(i) + coefficient.get(i));
                }
            });

        return sum;
    }

    private List<List<Integer>> generateAllParityPatterns(final int numBits) {
        final int total = 1 << numBits;
        return IntStream.range(0, total)
            .mapToObj(bitmask -> IntStream.range(0, numBits)
                .map(bit -> (bitmask >> bit) & 1)
                .boxed()
                .collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

    private List<List<Integer>> combinations(final int n, final int k) {
        final List<List<Integer>> result = new ArrayList<>();
        generateCombinations(0, n, k, new ArrayList<>(), result);
        return result;
    }

    private void generateCombinations(final int start, final int n, final int k, final List<Integer> current, final List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        IntStream.range(start, n)
            .forEach(i -> {
                current.add(i);
                generateCombinations(i + 1, n, k, current, result);
                current.removeLast();
            });
    }

    private List<Integer> parseIntegerList(final String s) {
        final String content = s.substring(1, s.length() - 1); // Remove brackets
        return Arrays.stream(content.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private record LightMachine(BigInteger targetState, List<BigInteger> buttonMasks) {}

    private record StateWithPresses(BigInteger state, int presses) {}

    private record ParityPattern(List<Integer> pattern) {}
}
  
        
        
        
    
        
        
        
    
                
        
    
                        
        
        
        
    
        
        
        
        
        
    
