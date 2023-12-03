package com.darshah.adventofcode.p2023;

import static java.lang.Character.isDigit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Day3_P2 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_3_input_1.txt");
        // var inputPath = Path.of("2023/day_1_input_1.txt");

        var input = Files.readAllLines(inputPath);

        var specialGearMap = new HashMap<String, List<Integer>>();

        var buffer = new StringBuilder();
        var symbolIndexes = new HashSet<String>();

        for (int y = 0; y < input.size(); y++) {
            var line = input.get(y);
            buffer.setLength(0);
            symbolIndexes.clear();
            for (int x = 0; x < line.length(); x++) {
                var curr = line.charAt(x);

                if (isDigit(curr)) {
                    symbolIndexes.addAll(isAdjacentCellSymbol(input, x, y));
                    buffer.append(curr);
                } else {
                    if (!symbolIndexes.isEmpty()) {
                        // System.out.println("found number: " + buffer);
                        var number = Integer.parseInt(buffer.toString());
                        symbolIndexes.forEach(index -> {
                            specialGearMap.computeIfAbsent(index, __ -> new LinkedList<>()).add(number);
                        });
                    }
                    buffer.setLength(0);
                    symbolIndexes.clear();
                }
            }

            if (!symbolIndexes.isEmpty() && !buffer.isEmpty()) {
                var number = Integer.parseInt(buffer.toString());
                symbolIndexes.forEach(index -> {
                    specialGearMap.computeIfAbsent(index, __ -> new LinkedList<>()).add(number);
                });
            }
        }

        var sum = specialGearMap.values()
            .stream()
            .filter(integers -> integers.size() == 2)
            .map(integers -> integers.get(0) * integers.get(1))
            .mapToInt(Integer::intValue)
            .sum();

        System.out.println("Result: ");
        System.out.println(sum);
    }

    private static List<String> isAdjacentCellSymbol(List<String> input, int x, int y) {
        return Stream.of(
                isCharAtSymbol(input, x + 1, y),
                isCharAtSymbol(input, x + 1, y + 1),
                isCharAtSymbol(input, x, y + 1),
                isCharAtSymbol(input, x - 1, y + 1),
                isCharAtSymbol(input, x - 1, y),
                isCharAtSymbol(input, x - 1, y - 1),
                isCharAtSymbol(input, x, y - 1),
                isCharAtSymbol(input, x + 1, y - 1)
            ).filter(Objects::nonNull)
            .toList();
    }

    private static String isCharAtSymbol(List<String> input, int x, int y) {
        var value = charAtSafe(input, x, y);
        if (!isDigit(value) && value == '*') {
            return "%d_%d".formatted(x, y);
        }
        return null;
    }

    private static char charAtSafe(List<String> input, int x, int y) {
        if (x < 0 || y < 0) {
            return '.';
        } else if (y >= input.size()) {
            return '.';
        } else if (x >= input.get(y).length()) {
            return '.';
        } else {
            return input.get(y).charAt(x);
        }
    }
}
