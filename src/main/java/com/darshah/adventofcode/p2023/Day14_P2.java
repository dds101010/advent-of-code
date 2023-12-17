package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

/**
 * INCOMPLETE ::: DOES NOT WORK
 */
public class Day14_P2 {


    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_14.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var lines = linesStream.toList();
            var grid = Day14_P1.grid(lines);
            var sum = 0L;
            var map = new LinkedHashMap<Long, Long>(); // 1000000000
            for (int i = 0; i < 1000000000; i++) {
                if (i % 10000000 == 0) {
                    System.out.printf("%s - checkpoint - %direction%n", LocalDateTime.now(), i);
                }
                sum = cycle(grid);
                map.compute(sum, (__, val) -> val == null ? 0L : val + 1);
                System.out.println(map);
                // set.add(sum);
                // System.out.println(i + ": " + set);
            }
            System.out.println("Result: ");
            System.out.println(sum);
        }
    }

    public static long slideNorth(char[][] grid) {
        final var len = grid[0].length;
        long sum = 0;
        for (int col = 0; col < len; col++) {
            for (int row = 0; row < grid.length; row++) {
                if (grid[row][col] == 'O') {
                    sum += (grid.length - row);
                } else if (grid[row][col] == '.') {
                    for (int nextRow = row + 1; nextRow < grid.length; nextRow++) {
                        if (grid[nextRow][col] == '#') {
                            break;
                        } else if (grid[nextRow][col] == 'O') {
                            sum += (grid.length - row);
                            grid[row][col] = 'O';
                            grid[nextRow][col] = '.';
                            break;
                        }
                    }
                }
            }
        }
        return sum;
    }

    public static long cycle(char[][] grid) {
        slideNorth(grid);
        slideWest(grid);
        slideSouth(grid);
        return slideEast(grid);
    }

    public static long slideWest(char[][] grid) {
        final var len = grid.length;
        long sum = 0;
        for (int row = 0; row < len; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 'O') {
                    sum += (grid.length - row);
                } else if (grid[row][col] == '.') {
                    for (int nextCol = col + 1; nextCol < grid.length; nextCol++) {
                        if (grid[row][nextCol] == '#') {
                            break;
                        } else if (grid[row][nextCol] == 'O') {
                            sum += (grid.length - row);
                            grid[row][col] = 'O';
                            grid[row][nextCol] = '.';
                            break;
                        }
                    }
                }
            }
        }
        return sum;
    }

    public static long slideSouth(char[][] grid) {
        final var len = grid[0].length;
        long sum = 0;
        for (int col = 0; col < len; col++) {
            for (int row = grid.length - 1; row >= 0; row--) {
                if (grid[row][col] == 'O') {
                    sum += (grid.length - row);
                } else if (grid[row][col] == '.') {
                    for (int nextRow = row - 1; nextRow >= 0; nextRow--) {
                        if (grid[nextRow][col] == '#') {
                            break;
                        } else if (grid[nextRow][col] == 'O') {
                            sum += (grid.length - row);
                            grid[row][col] = 'O';
                            grid[nextRow][col] = '.';
                            break;
                        }
                    }
                }
            }
        }
        return sum;
    }

    public static long slideEast(char[][] grid) {
        final var len = grid.length;
        long sum = 0;
        for (int row = 0; row < len; row++) {
            for (int col = grid[row].length - 1; col >= 0; col--) {
                if (grid[row][col] == 'O') {
                    sum += (grid.length - row);
                } else if (grid[row][col] == '.') {
                    for (int nextCol = col - 1; nextCol >= 0; nextCol--) {
                        if (grid[row][nextCol] == '#') {
                            break;
                        } else if (grid[row][nextCol] == 'O') {
                            sum += (grid.length - row);
                            grid[row][col] = 'O';
                            grid[row][nextCol] = '.';
                            break;
                        }
                    }
                }
            }
        }
        return sum;
    }
}
