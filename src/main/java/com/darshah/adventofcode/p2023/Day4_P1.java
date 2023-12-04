package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4_P1 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_4_input_1.txt");
        try (Stream<String> lines = Files.lines(inputPath)) {
            var sum = lines.parallel()
                .map(Day4_P1::getMatchingNumbers)
                .mapToInt(numbers -> numbers == 0 ? numbers : 1 << (numbers - 1))
                .sum();

            System.out.println("Result: ");
            System.out.println(sum);
        }
    }

    public static int getMatchingNumbers(String line) {
        var split = line.split(": ")[1].split("\\|");
        var result = Arrays.stream(split[0].trim().split("\\s+"))
            .map(String::trim)
            .collect(Collectors.toSet());

        return Arrays.stream(split[1].trim().split("\\s+"))
            .map(String::trim)
            .filter(result::contains)
            .toList()
            .size();
    }
}
