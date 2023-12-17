package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

public class Day15_P2 {


    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_15.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var lines = linesStream.toList();
            if (lines.size() != 1) {
                throw new IllegalArgumentException("there should be only one line");
            }
            var line = lines.get(0);

            var sum = 0;
            var box = new HashMap<Integer, LinkedHashMap<String, Integer>>();
            Arrays.stream(line.split(","))
                .map(Day15_P2::getLens)
                .forEach(lens -> {
                    if (lens.focalLength == -1) {
                        if (box.containsKey(lens.box) && box.get(lens.box).containsKey(lens.label)) {
                            box.get(lens.box).remove(lens.label);
                        }
                    } else {
                        box.putIfAbsent(lens.box, new LinkedHashMap<>());
                        box.get(lens.box).put(lens.label, lens.focalLength);
                    }
                });

            System.out.println(box);
            for (var entry : box.entrySet()) {
                if (entry.getValue().isEmpty()) {
                    continue;
                }
                var boxNumber = entry.getKey() + 1;
                var focalLengths = entry.getValue().values();
                int slot = 1;
                for (var focalLength : focalLengths) {
                    sum += (boxNumber * focalLength * slot);
                    slot++;
                }
            }
            System.out.println("Result: ");
            System.out.println(sum);
        }
    }

    public static Lens getLens(String input) {
        var sum = 0;
        var focalLength = 0;
        String label = null;
        for (char c : input.toCharArray()) {
            if (c == '=') {
                focalLength = Integer.parseInt(input.split("=")[1]);
                label = input.split("=")[0];
                break;
            } else if (c == '-') {
                focalLength = -1;
                label = input.split("-")[0];
                break;
            } else {
                sum += (int) c;
                sum *= 17;
                sum %= 256;
            }
            // System.out.println(sum);
        }
        return new Lens(label, focalLength, sum);
    }

    public static record Lens(String label, Integer focalLength, Integer box) {

    }
}
