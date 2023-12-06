package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day6_P1 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_6_input_1.txt");
        try (Stream<String> lines = Files.lines(inputPath)) {
            final List<Long> times = new ArrayList<>();
            final List<Long> distances = new ArrayList<>();

            lines.forEach(line -> {
                if (line.startsWith("Time:")) {
                    Arrays.stream(line.split(":\\s+")[1].split("\\s+"))
                        .map(Long::parseLong)
                        .forEach(times::add);
                } else if (line.startsWith("Distance:")) {
                    Arrays.stream(line.split(":\\s+")[1].split("\\s+"))
                        .map(Long::parseLong)
                        .forEach(distances::add);
                }
            });

            long ans = findWaysToBeat(times, distances);
            System.out.println("Result: ");
            System.out.println(ans);
        }
    }

    public static long findWaysToBeat(List<Long> times, List<Long> distances) {
        return IntStream.range(0, times.size())
            .parallel()
            .mapToObj(index -> {
                var time = times.get(index);
                var distance = distances.get(index);
                long minSeconds = findMin(time, distance);
                long maxSeconds = findMax(time, distance);
                // System.out.printf("time [%d], distance [%d] : Min [%d], Max [%d]%n",
                //    time, distance, minSeconds, maxSeconds);
                return Map.entry(minSeconds, maxSeconds);
            }).mapToLong(entry -> entry.getValue() - entry.getKey() + 1)
            .reduce(1, (a, b) -> a * b);
    }

    public static long findMin(long seconds, long maxDistance) {
        long L = 0, R = seconds - 1;
        long min = Integer.MAX_VALUE;
        long M = 0;

        while (L <= R) {
            M = L + (R - L) / 2;
            if ((M * (seconds - M)) > maxDistance) {
                min = Math.min(min, M);
                R = M - 1;
            } else {
                L = M + 1;
            }
        }

        return min;
    }

    public static long findMax(long seconds, long maxDistance) {
        long L = 0, R = seconds - 1;
        long max = Integer.MIN_VALUE;
        long M = 0;

        while (L <= R) {
            M = L + (R - L) / 2;
            if ((M * (seconds - M)) > maxDistance) {
                max = Math.max(max, M);
                L = M + 1;
            } else {
                R = M - 1;
            }
        }

        return max;
    }
}
