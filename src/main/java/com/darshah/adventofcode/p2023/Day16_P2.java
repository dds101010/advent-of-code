package com.darshah.adventofcode.p2023;

import com.darshah.adventofcode.p2023.Day16_P1.Direction;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Day16_P2 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_16.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var lines = linesStream.toList();
            var max = Integer.MIN_VALUE;

            var maxRow = lines.size();
            var maxCol = lines.get(0).length();

            for (int row = 0; row < maxRow; row++) {
                for (int col = 0; col < maxCol; col++) {
                    if (row == 0) {
                        max = Math.max(max, Day16_P1.solve(lines, row, col, Direction.SOUTH));
                    }
                    if (col == 0) {
                        max = Math.max(max, Day16_P1.solve(lines, row, col, Direction.EAST));
                    }
                    if (row == maxRow - 1) {
                        max = Math.max(max, Day16_P1.solve(lines, row, col, Direction.NORTH));
                    }
                    if (col == maxCol - 1) {
                        max = Math.max(max, Day16_P1.solve(lines, row, col, Direction.WEST));
                    }
                }
            }

            System.out.println("Result: ");
            System.out.println(max);
        }
    }
}
