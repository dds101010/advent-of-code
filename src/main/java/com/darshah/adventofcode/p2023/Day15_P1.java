package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day15_P1 {


    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_15.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var lines = linesStream.toList();
            if (lines.size() != 1) {
                throw new IllegalArgumentException("there should be only one line");
            }
            var line = lines.get(0);

            var sum = Arrays.stream(line.split(","))
                .mapToInt(Day15_P1::solve)
                .sum();
            System.out.println("Result: ");
            System.out.println(sum);
        }
    }

    public static int solve(String input) {
        var sum = 0;
        for (char c : input.toCharArray()) {
            sum += (int) c;
            sum *= 17;
            sum %= 256;
            // System.out.println(sum);
        }
        return sum;
    }
}
