package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Stream;

public class Day12_P1 {


    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_12.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {

            var ans = linesStream
                .parallel()
                .mapToLong(Day12_P1::solveLine)
                .sum();

            System.out.println("Result: ");
            System.out.println(ans);
        }
    }

    public static long solveLine(String line) {
        var split = line.split("\\s+");
        var springs = split[0].toCharArray();
        var groups = Arrays.stream(split[1].split(",")).mapToInt(Integer::parseInt).toArray();

        // System.out.println(Arrays.toString(springs));
        // System.out.println(Arrays.toString(groups));

        return backtrack(springs, 0, groups);
    }

    public static long backtrack(char[] springs, int current, int[] groups) {

        if (current == springs.length) {
            return isValid(springs, groups) ? 1 : 0;
        } else if (springs[current] == '?') {
            springs[current] = '#';
            var result = backtrack(springs, current + 1, groups);
            springs[current] = '.';
            result += backtrack(springs, current + 1, groups);
            springs[current] = '?';
            return result;
        } else {
            return backtrack(springs, current + 1, groups);
        }
    }

    public static boolean isValid(char[] springs, int[] groups) {
        var actualGroups = new LinkedList<Integer>();
        int current = 0;
        for (var spring : springs) {
            if (spring == '#') {
                current++;
            } else if (current != 0) {
                actualGroups.addLast(current);
                current = 0;
            }
        }

        if (current != 0) {
            actualGroups.addLast(current);
        }

        if (actualGroups.size() != groups.length) {
            return false;
        }

        for (int i = 0; i < groups.length; i++) {
            if (groups[i] != actualGroups.get(i)) {
                return false;
            }
        }

        return true;
    }
}
