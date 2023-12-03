package com.darshah.adventofcode.p2023;

import static java.lang.Character.isDigit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day3_P1 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_3_input_1.txt");
        // var inputPath = Path.of("2023/day_1_input_1.txt");

        var input = Files.readAllLines(inputPath);

        int sum = 0;
        var buffer = new StringBuilder();
        var foundSymbol = false;

        for (int y = 0; y < input.size(); y++) {
            var line = input.get(y);
            buffer.setLength(0);
            foundSymbol = false;
            for (int x = 0; x < line.length(); x++) {
                var curr = line.charAt(x);

                if (isDigit(curr)) {
                    if (foundSymbol || isAdjacentCellSymbol(input, x, y)) {
                        foundSymbol = true;
                    }
                    buffer.append(curr);
                } else {
                    if (foundSymbol) {
                        // System.out.println("found number: " + buffer);
                        sum += Integer.parseInt(buffer.toString());
                    }
                    buffer.setLength(0);
                    foundSymbol = false;
                }
            }

            if (foundSymbol && !buffer.isEmpty()) {
                sum += Integer.parseInt(buffer.toString());
            }
        }

        System.out.println("Result: ");
        System.out.println(sum);
    }

    private static boolean isAdjacentCellSymbol(List<String> input, int x, int y) {
        return isCharAtSymbol(input, x + 1, y) ||
            isCharAtSymbol(input, x + 1, y + 1) ||
            isCharAtSymbol(input, x, y + 1) ||
            isCharAtSymbol(input, x - 1, y + 1) ||
            isCharAtSymbol(input, x - 1, y) ||
            isCharAtSymbol(input, x - 1, y - 1) ||
            isCharAtSymbol(input, x, y - 1) ||
            isCharAtSymbol(input, x + 1, y - 1);
    }

    private static boolean isCharAtSymbol(List<String> input, int x, int y) {
        var value = charAtSafe(input, x, y);
        return !isDigit(value) && value != '.';
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
