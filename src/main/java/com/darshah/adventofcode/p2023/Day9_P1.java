package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day9_P1 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_9_input_1.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var ans = linesStream.map(Day9_P1::predictNext)
                .mapToLong(Long::longValue)
                .sum();

            System.out.println("Result: ");
            System.out.println(ans);
        }
    }

    public static long predictNext(String line) {
        var current = new ArrayList<Long>();
        var result = 0L;
        Arrays.stream(line.split("\\s+"))
            .map(Long::parseLong)
            .forEach(current::add);

        var allZeros = false;
        while (!allZeros) {
            allZeros = true;
            result += current.get(current.size() - 1);
            var next = new ArrayList<Long>();
            var nextVal = 0L;
            for (int i = 1; i < current.size(); i++) {
                nextVal = current.get(i) - current.get(i - 1);
                allZeros = allZeros && nextVal == 0;
                next.add(nextVal);
            }
            current = next;
        }

        return result;
    }
}
