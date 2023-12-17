package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class Day17_P1 {

    private static int[][] grid;

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_17.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var lines = linesStream.toList();
            grid = parse(lines);
            var maxRow = grid.length - 1;
            var maxCol = grid[maxRow].length - 1;
            var start = new Point(0, 0, -1, Direction.EAST, 0);
            // var pq = new PriorityQueue<LinkedList<Point>>(Comparator.comparing(LinkedList::getLast));
            var pq = new PriorityQueue<Point>();
            var visited = new HashSet<Point>();
            // var startList = new LinkedList<Point>();
            // startList.add(start);
            pq.offer(start);
            long answer = 0;

            while (!pq.isEmpty()) {
                // var currentList = pq.poll();
                var current = pq.poll(); // currentList.getLast();
                // visited.add(current);
                // System.out.println(current);
                if (current.row == maxRow && current.col == maxCol) {
                    answer = current.heat;
                    // System.out.println(currentList);
                    break;
                }
                Stream.of(current.straight(), current.left(), current.right())
                    .filter(Objects::nonNull)
                    .filter(next -> !visited.contains(next))
                    .peek(visited::add)
                    /*.map(next -> {
                        var newList = new LinkedList<Point>(currentList);
                        newList.addLast(next);
                        return newList;
                    })*/.forEach(pq::offer);
            }

            System.out.println("Result: ");
            System.out.println(answer);
        }
    }

    public static int[][] parse(List<String> lines) {
        final var rows = lines.size();
        final var cols = lines.get(0).length();
        var grid = new int[rows][cols];

        for (int row = 0; row < rows; row++) {
            var line = lines.get(row);
            for (int col = 0; col < cols; col++) {
                grid[row][col] = line.charAt(col) - '0';
            }
        }

        return grid;
    }

    public enum Direction {
        NORTH, EAST, WEST, SOUTH
    }

    public static record Point(int row, int col, int len, Direction dir, long heat) implements Comparable<Point> {

        public static Point get(int row, int col, int len, Direction dir, long previousHeat) {
            if (row < 0 || col < 0 || row == grid.length || col == grid[row].length) {
                return null;
            }
            return new Point(row, col, len, dir, previousHeat + grid[row][col]);
        }

        public Point straight() {
            if (len == 2) {
                return null;
            }
            return switch (dir) {
                case NORTH -> Point.get(row - 1, col, len + 1, dir, heat);
                case EAST -> Point.get(row, col + 1, len + 1, dir, heat);
                case WEST -> Point.get(row, col - 1, len + 1, dir, heat);
                case SOUTH -> Point.get(row + 1, col, len + 1, dir, heat);
            };
        }

        public Point left() {
            return switch (dir) {
                case NORTH -> Point.get(row, col - 1, 0, Direction.WEST, heat);
                case EAST -> Point.get(row - 1, col, 0, Direction.NORTH, heat);
                case WEST -> Point.get(row + 1, col, 0, Direction.SOUTH, heat);
                case SOUTH -> Point.get(row, col + 1, 0, Direction.EAST, heat);
            };
        }

        public Point right() {
            return switch (dir) {
                case NORTH -> Point.get(row, col + 1, 0, Direction.EAST, heat);
                case EAST -> Point.get(row + 1, col, 0, Direction.SOUTH, heat);
                case WEST -> Point.get(row - 1, col, 0, Direction.NORTH, heat);
                case SOUTH -> Point.get(row, col - 1, 0, Direction.WEST, heat);
            };
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return row == point.row && col == point.col && len == point.len /*&& dir == point.dir*/;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col, len, dir);
        }

        @Override
        public int compareTo(Point that) {
            return Long.compare(this.heat, that.heat);
        }
    }
}
