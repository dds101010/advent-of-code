package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day4_P2 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_4_input_1.txt");

        try (Stream<String> lineStream = Files.lines(inputPath)) {
            var lines = lineStream.toList();
            int[] mem = new int[lines.size()];
            Arrays.fill(mem, 1);

            for (int i = 0; i < mem.length; i++) {
                var matchingNumbers = Day4_P1.getMatchingNumbers(lines.get(i));
                for (int j = 1; j <= matchingNumbers; j++) {
                    mem[i + j] += mem[i];
                }
            }

            var sum = IntStream.of(mem).sum();

            System.out.println("Result: ");
            System.out.println(sum);

        }
    }
}
