package com.darshah.adventofcode.p2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class P1 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2022/input_p1.txt");
        try (var lines = Files.lines(inputPath)) {
            var max = lines.reduce(
                    new LinkedList<List<String>>(),
                    (acc, current) -> {
                        if (acc.isEmpty()) {
                            acc.addFirst(new ArrayList<>());
                        }
                        if (current.trim().isBlank()) {
                            acc.addFirst(new ArrayList<>());
                        } else {
                            acc.getFirst().add(current);
                        }
                        return acc;
                    },
                    (l1, l2) -> {
                        throw new RuntimeException("Don't execute in parallel stream!");
                    }
                )
                .stream()
                .parallel()
                .map(list -> list.stream().mapToInt(Integer::parseInt).sum())
                .sorted(Collections.reverseOrder())
                .limit(3)
                .mapToInt(Integer::intValue)
                .toArray();

            var top3Sum = Arrays.stream(max).sum();
            System.out.println(Arrays.toString(max));
            System.out.println(top3Sum);
        }
    }

}
