package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Day5_P1 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_5_input_1.txt");
        try (Stream<String> lines = Files.lines(inputPath)) {

            var input = new LinkedList<TreeSet<Range>>();
            List<Long> seeds = null;

            for (String line : lines.toList()) {
                if (line.startsWith("seeds:")) {
                    seeds = Arrays.stream(line.split(": ")[1].split("\\s+"))
                        .map(Long::parseLong)
                        .toList();
                } else if (line.contains("map:")) {
                    input.addLast(new TreeSet<>());
                } else if (!line.isBlank()) {
                    input.getLast().add(Range.parse(line));
                }
            }

            // System.out.println(input);

            var ans = Objects.requireNonNull(seeds)
                .stream()
                .map(seed -> traverseFromSrcToDest(seed, input))
                .min(Comparator.comparing(Long::longValue));

            System.out.println("Result: ");
            System.out.println(ans);
        }
    }

    public static Long traverseFromSrcToDest(Long seed, LinkedList<TreeSet<Range>> input) {
        long result = seed;

        for (TreeSet<Range> ranges : input) {
            for (Range range : ranges) {
                var position = range.positionInRange(result);

                if (position < 0) {
                    break;
                } else if (position == 0) {
                    result = range.mapToDestination(result);
                    break;
                }
            }
        }

        return result;
    }


    public record Range(long sourceStart, long destStart, long length) implements Comparable<Range> {

        public static Range parse(String rangeStr) {
            var lineSplit = rangeStr.split("\\s+");
            if (lineSplit.length != 3) {
                throw new IllegalArgumentException("Must have three values: %s".formatted(rangeStr));
            }
            return new Range(
                Long.parseLong(lineSplit[1]),
                Long.parseLong(lineSplit[0]),
                Long.parseLong(lineSplit[2])
            );
        }

        public long mapToDestination(long source) {
            return source + (destStart - sourceStart);
        }

        public int positionInRange(long num) {
            if (num < sourceStart) {
                return -1;
            } else if (/* num >= sourceStart && */ num < sourceStart + length) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Range range = (Range) o;
            return sourceStart == range.sourceStart && destStart == range.destStart && length == range.length;
        }

        @Override
        public int compareTo(Range that) {
            return Long.compare(this.sourceStart, that.sourceStart);
        }
    }
}
