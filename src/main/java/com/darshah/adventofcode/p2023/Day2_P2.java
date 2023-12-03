package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Day2_P2 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("txt");
        // var inputPath = Path.of("2023/day_1_input_1.txt");
        try (Stream<String> lines = Files.lines(inputPath)) {
            var sum = lines
                // .parallel()
                // .peek(System.out::println)
                .map(line -> {
                    var red = Integer.MIN_VALUE;
                    var green = Integer.MIN_VALUE;
                    var blue = Integer.MIN_VALUE;

                    var rolls = line.split(":")[1];
                    var inputs = rolls.split(";");
                    for (var input : inputs) {
                        var cubes = input.split(",");

                        for (var cube : cubes) {
                            var temp = cube.trim().split(" ");
                            var num = Integer.parseInt(temp[0].trim());
                            var color = temp[1].trim();

                            switch (color) {
                                case "red" -> red = Math.max(num, red);
                                case "green" -> green = Math.max(num, green);
                                case "blue" -> blue = Math.max(num, blue);
                            }
                        }
                    }

                    System.out.printf("red: %d, green: %d, blue: %d%n", red, green, blue);

                    return red * green * blue;
                }).reduce(0, Integer::sum);

            System.out.println("Result: ");
            System.out.println(sum);
        }
    }
}
