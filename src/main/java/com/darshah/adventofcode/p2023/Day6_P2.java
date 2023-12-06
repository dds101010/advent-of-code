package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day6_P2 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_6_input_1.txt");
        try (Stream<String> lines = Files.lines(inputPath)) {
            final List<Long> times = new ArrayList<>();
            final List<Long> distances = new ArrayList<>();

            lines.forEach(line -> {
                if (line.startsWith("Time:")) {
                    var number = line.split(":\\s+")[1].replaceAll("\\s+", "");
                    System.out.println("time: " + number);
                    times.add(Long.parseLong(number));
                } else if (line.startsWith("Distance:")) {
                    var number = line.split(":\\s+")[1].replaceAll("\\s+", "");
                    System.out.println("distance: " + number);
                    distances.add(Long.parseLong(number));
                }
            });

            long ans = Day6_P1.findWaysToBeat(times, distances);
            System.out.println("Result: ");
            System.out.println(ans);
        }
    }
}
