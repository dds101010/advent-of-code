package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Day14_P1 {


    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_14.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var lines = linesStream.toList();
            var grid = grid(lines);
            var sum = slideAllNorth(grid);
            System.out.println("Result: ");
            System.out.println(sum);
        }
    }

    public static long slideAllNorth(char[][] grid) {
        final var len = grid[0].length;
        long sum = 0;
        for (int col = 0; col < len; col++) {
            sum += slideColToNorth(grid, col);
        }

        return sum;
    }

    public static long slideColToNorth(char[][] grid, int col) {
        long sum = 0;
        for (int empty = 0; empty < grid.length; empty++) {
            if (grid[empty][col] == 'O') {
                sum += (grid.length - empty);
            } else if (grid[empty][col] == '.') {
                for (int rock = empty + 1; rock < grid.length; rock++) {
                    if (grid[rock][col] == '#') {
                        break;
                    } else if (grid[rock][col] == 'O') {
                        sum += (grid.length - empty);
                        grid[empty][col] = 'O';
                        grid[rock][col] = '.';
                        break;
                    }
                }
            }
        }
        return sum;
    }

    public static char[][] grid(List<String> input) {
        var grid = new char[input.size()][input.get(0).length()];

        for (int i = 0; i < grid.length; i++) {
            var str = input.get(i);
            for (int j = 0; j < str.length(); j++) {
                grid[i][j] = str.charAt(j);
            }
        }

        return grid;
    }
}
