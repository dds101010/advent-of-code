package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Day1_P1 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_1_input_1.txt");
        try (Stream<String> lines = Files.lines(inputPath)) {
            var sum = lines
                .parallel()
                .map(line -> {
                    var digits = line.chars().filter(Character::isDigit).toArray();
                    return "%c%c".formatted((char) digits[0], (char) digits[digits.length - 1]);
                }).mapToInt(Integer::parseInt)
                .sum();

            System.out.println("Result: ");
            System.out.println(sum);
        }
    }
}
