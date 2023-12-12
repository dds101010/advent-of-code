package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day12_P2 {


    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_12.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {

            var ans = linesStream
                .parallel()
                .mapToLong(Day12_P2::solveLine)
                .sum();

            System.out.println("Result: ");
            System.out.println(ans);
        }
    }

    public static long solveLine(String line) {
        var split = line.split("\\s+");

        var multiplier = 5;

        var multipliedSprings = IntStream.range(0, multiplier)
            .mapToObj(__ -> split[0])
            .collect(Collectors.joining("?"));

        var multipliedGroups = IntStream.range(0, multiplier)
            .mapToObj(__ -> split[1])
            .collect(Collectors.joining(","));

        // System.out.println(multipliedSprings);
        // System.out.println(multipliedGroups);

        var springs = multipliedSprings.toCharArray();
        var groups = Arrays.stream(multipliedGroups.split(",")).mapToInt(Integer::parseInt).toArray();

        // System.out.println(Arrays.toString(springs));
        // System.out.println(Arrays.toString(groups));

        return backtrack(springs, 0, groups, new int[groups.length], 0, new HashMap<>());
    }

    public static long backtrack(
        char[] springs, int i, int[] groups, int[] current, int j, Map<Integer, Map<Integer, Map<Integer, Long>>> memo
    ) {
        if (memo.containsKey(i) && memo.get(i).containsKey(j) && memo.get(i).get(j).containsKey(current[j])) {
            return memo.get(i).get(j).get(current[j]);
        }
        if (i == springs.length) {
            return (j + 1 != groups.length) ? 0 : (groups[j] == current[j]) ? 1 : 0;
        }
        var nextJ = (current[j] == 0) ? j : (j + 1 == groups.length) ? j : j + 1;
        var value = switch (springs[i]) {
            case '#' -> {
                if (current[j] + 1 <= groups[j]) {
                    current[j] += 1;
                    long result = backtrack(springs, i + 1, groups, current, j, memo);
                    current[j] -= 1;
                    yield result;
                } else {
                    yield 0;
                }
            }
            case '.' -> {
                if (current[j] == 0 || current[j] == groups[j]) {
                    yield backtrack(springs, i + 1, groups, current, nextJ, memo);
                } else {
                    yield 0;
                }
            }
            case '?' -> {
                /*
                 * 0 -> # .
                 * < -> #
                 * = -> .
                 * > -> 0
                 * */

                long result = 0;

                if (current[j] == 0 || current[j] < groups[j]) {
                    // assume #
                    current[j] += 1;
                    result += backtrack(springs, i + 1, groups, current, j, memo);
                    current[j] -= 1;
                }

                if (current[j] == 0 || current[j] == groups[j]) {
                    // assume .
                    result += backtrack(springs, i + 1, groups, current, nextJ, memo);
                }

                yield result;
            }
            default -> {
                throw new IllegalArgumentException("Unsupported char: " + springs[i]);
            }
        };

        memo.putIfAbsent(i, new HashMap<>());
        memo.get(i).putIfAbsent(j, new HashMap<>());
        memo.get(i).get(j).put(current[j], value);

        return value;
    }
}
