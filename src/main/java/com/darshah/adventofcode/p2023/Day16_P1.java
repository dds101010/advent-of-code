package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Day16_P1 {

    static Cell[][] grid;

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_16.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var lines = linesStream.toList();
            var answer = solve(lines, 0, 0, Direction.EAST);

            System.out.println("Result: ");
            System.out.println(answer);
        }
    }

    public static int solve(List<String> lines, int ir, int ic, Direction direction) {
        grid = parse(lines);
        var start = new Light(ir, ic, direction);
        start.visit();
        var queue = new LinkedList<Light>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            var size = queue.size();
            for (int i = 0; i < size; i++) {
                var current = queue.removeFirst();
                var next = current.next();
                if (next[0] != null && next[0].visit()) {
                    queue.addLast(next[0]);
                }
                if (next[1] != null && next[1].visit()) {
                    queue.addLast(next[1]);
                }
            }
        }

        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (!grid[row][col].from.isEmpty()) {
                    count++;
                }
            }
        }
        return count;
    }

    public static Cell[][] parse(List<String> lines) {
        final var rows = lines.size();
        final var cols = lines.get(0).length();
        var grid = new Cell[rows][cols];

        for (int row = 0; row < rows; row++) {
            var line = lines.get(row);
            for (int col = 0; col < cols; col++) {
                grid[row][col] = new Cell(line.charAt(col), new HashSet<>());
            }
            // System.out.println(Arrays.toString(grid[row]));
        }

        return grid;
    }

    public record Light(int row, int col, Direction direction) {

        public Light[] next() {
            return switch (grid[row][col].mirror) {
                case '.' -> new Light[]{go(direction), null};
                case '/' -> new Light[]{
                    switch (direction) {
                        case NORTH -> go(Direction.EAST);
                        case EAST -> go(Direction.NORTH);
                        case SOUTH -> go(Direction.WEST);
                        case WEST -> go(Direction.SOUTH);
                    }, null
                };
                case '\\' -> new Light[]{
                    switch (direction) {
                        case NORTH -> go(Direction.WEST);
                        case EAST -> go(Direction.SOUTH);
                        case SOUTH -> go(Direction.EAST);
                        case WEST -> go(Direction.NORTH);
                    }, null
                };
                case '|' -> {
                    if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                        yield new Light[]{go(direction), null};
                    } else {
                        yield new Light[]{go(Direction.NORTH), go(Direction.SOUTH)};
                    }
                }
                case '-' -> {
                    if (direction == Direction.EAST || direction == Direction.WEST) {
                        yield new Light[]{go(direction), null};
                    } else {
                        yield new Light[]{go(Direction.EAST), go(Direction.WEST)};
                    }
                }
                default -> throw new IllegalArgumentException("Wrong mirror %c".formatted(grid[row][col].mirror));
            };
        }

        public Light go(Direction towards) {
            var nextRow = row;
            var nextCol = col;

            switch (towards) {
                case NORTH -> nextRow--;
                case EAST -> nextCol++;
                case SOUTH -> nextRow++;
                case WEST -> nextCol--;
            }

            if (nextRow < 0 || nextCol < 0 || nextRow == grid.length || nextCol == grid[nextRow].length) {
                return null;
            }

            return new Light(nextRow, nextCol, towards);
        }

        public Cell cell() {
            return grid[row][col];
        }

        public boolean hasVisited() {
            return grid[row][col].from.contains(direction);
        }

        public boolean visit() {
            return grid[row][col].from.add(direction);
        }
    }

    public record Cell(char mirror, HashSet<Direction> from) {

    }

    public enum Direction {
        NORTH,
        EAST,
        WEST,
        SOUTH
    }
}
