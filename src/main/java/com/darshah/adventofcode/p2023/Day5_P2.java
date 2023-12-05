package com.darshah.adventofcode.p2023;

import com.darshah.adventofcode.p2023.Day5_P1.Range;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Day5_P2 {

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

            var ans = Long.MAX_VALUE;
            var memo = new HashMap<Integer, HashMap<Long, Long>>();

            Objects.requireNonNull(seeds, "seeds cannot be null. Something wrong with parsing!");

            for (int i = 0; i < seeds.size(); i += 2) {
                var from = seeds.get(i);
                var to = from + seeds.get(i + 1);
                System.out.printf("Computing from %d to %d%n", from, to);
                for (long seed = from; seed < to; seed++) {
                    // var location = traverseFromSrcToDest(seed, input, memo);
                    var location = Day5_P1.traverseFromSrcToDest(seed, input);
                    ans = Math.min(ans, location);
                }
            }

            System.out.println("Result: ");
            System.out.println(ans);
        }
    }

    // This does not work. goes OOM :')
    public static Long traverseFromSrcToDest(
        Long seed,
        LinkedList<TreeSet<Range>> input,
        HashMap<Integer, HashMap<Long, Long>> memo
    ) {
        long result = seed;
        int index = 0;

        for (TreeSet<Range> ranges : input) {
            if (memo.containsKey(index) && memo.get(index).containsKey(result)) {
                result = memo.get(index).get(result);
            } else {
                var fromResult = result;
                for (Range range : ranges) {
                    var position = range.positionInRange(result);

                    if (position < 0) {
                        break;
                    } else if (position == 0) {
                        result = range.mapToDestination(result);
                        break;
                    }
                }
                memo.computeIfAbsent(index, __ -> new HashMap<>()).put(fromResult, result);
            }
            index++;
        }

        return result;
    }
}
