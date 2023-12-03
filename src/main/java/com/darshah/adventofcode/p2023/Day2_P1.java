package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

public class Day2_P1 {

    public static void main(String[] args) throws IOException {
        var map = new HashMap<String, Integer>();
        map.put("red", 12);
        map.put("green", 13);
        map.put("blue", 14);

        var inputPath = Path.of("txt");
        // var inputPath = Path.of("2023/day_1_input_1.txt");
        try (Stream<String> lines = Files.lines(inputPath)) {
            var sum = lines
                // .parallel()
                // .peek(System.out::println)
                .map(line -> {
                    var rolls = line.split(":")[1];
                    var inputs = rolls.split(";");
                    for (var input : inputs) {
                        var cubes = input.split(",");

                        for (var cube : cubes) {
                            var temp = cube.trim().split(" ");
                            var num = Integer.parseInt(temp[0].trim());
                            var color = temp[1].trim();

                            if (map.get(color) < num) {
                                return null;
                            }
                        }
                    }

                    return Integer.parseInt(line.split(":")[0].split(" ")[1]);
                }).filter(Objects::nonNull).reduce(0, Integer::sum);

            System.out.println("Result: ");
            System.out.println(sum);
        }
    }
}
