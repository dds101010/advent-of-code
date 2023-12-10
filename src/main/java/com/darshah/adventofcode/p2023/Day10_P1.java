package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Day10_P1 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_10_input_1.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var lines = linesStream.toList();
            var start = getStartingLocation(lines);
            var queue = new LinkedList<>(getFirstIteration(lines, start));
            var visited = new HashSet<Location>();
            visited.add(start);

            System.out.printf("First iteration size: (should be 2): %d%n", queue.size());
            var ans = 0L;

            while (!queue.isEmpty()) {
                // System.out.printf("> %s%n", queue);
                var len = queue.size();
                for (int i = 0; i < len; i++) {
                    var current = queue.removeFirst();
                    visited.add(current);
                    var currentChar = lines.get(current.y).charAt(current.x);
                    if (currentChar == '-') {
                        queueIfNotVisited(Location.getInstance(current.x - 1, current.y), queue, visited);
                        queueIfNotVisited(Location.getInstance(current.x + 1, current.y), queue, visited);
                    } else if (currentChar == '|') {
                        queueIfNotVisited(Location.getInstance(current.x, current.y + 1), queue, visited);
                        queueIfNotVisited(Location.getInstance(current.x, current.y - 1), queue, visited);
                    } else if (currentChar == '7') {
                        queueIfNotVisited(Location.getInstance(current.x - 1, current.y), queue, visited);
                        queueIfNotVisited(Location.getInstance(current.x, current.y + 1), queue, visited);
                    } else if (currentChar == 'J') {
                        queueIfNotVisited(Location.getInstance(current.x, current.y - 1), queue, visited);
                        queueIfNotVisited(Location.getInstance(current.x - 1, current.y), queue, visited);
                    } else if (currentChar == 'F') {
                        queueIfNotVisited(Location.getInstance(current.x + 1, current.y), queue, visited);
                        queueIfNotVisited(Location.getInstance(current.x, current.y + 1), queue, visited);
                    } else if (currentChar == 'L') {
                        queueIfNotVisited(Location.getInstance(current.x, current.y - 1), queue, visited);
                        queueIfNotVisited(Location.getInstance(current.x + 1, current.y), queue, visited);
                    }
                }
                ans++;
            }

            System.out.println("Result: ");
            System.out.println(ans);

            for (int j = 0; j < lines.size(); j++) {
                var line = lines.get(j).toCharArray();
                for (int i = 0; i < line.length; i++) {
                    if ("|-LJF7".indexOf(line[i]) != -1 && !visited.contains(new Location(i, j))) {
                        line[i] = '.';
                    }
                }
                System.out.println(line);
            }
        }
    }

    public static void queueIfNotVisited(Location location, LinkedList<Location> queue, HashSet<Location> visited) {
        if (location != null && !visited.contains(location)) {
            queue.offerLast(location);
        }
    }

    public static List<Location> getFirstIteration(List<String> input, Location start) {
        var firstIteration = new LinkedList<Location>();

        var east = getCharAt(input, start.x + 1, start.y);
        if (east == '-' || east == '7' || east == 'J') {
            firstIteration.offer(new Location(start.x + 1, start.y));
        }

        var south = getCharAt(input, start.x, start.y + 1);
        if (south == 'J' || south == '|' || south == 'L') {
            firstIteration.offer(new Location(start.x, start.y + 1));
        }

        var west = getCharAt(input, start.x - 1, start.y);
        if (west == '-' || west == 'L' || west == 'F') {
            firstIteration.offer(new Location(start.x - 1, start.y));
        }

        var north = getCharAt(input, start.x, start.y - 1);
        if (north == '|' || north == 'F' || north == '7') {
            firstIteration.offer(new Location(start.x, start.y - 1));
        }

        return firstIteration;
    }


    public static char getCharAt(List<String> input, int x, int y) {
        if (x < 0 || y < 0) {
            return '.';
        } else if (y >= input.size()) {
            return '.';
        } else if (x >= input.get(y).length()) {
            return '.';
        } else {
            return input.get(y).charAt(x);
        }
    }

    public static Location getStartingLocation(List<String> lines) {

        for (int i = 0; i < lines.size(); i++) {
            var j = lines.get(i).indexOf("S");
            if (j != -1) {
                return new Location(j, i);
            }
        }
        throw new RuntimeException("Could not find location of S");
    }

    public static record Location(int x, int y) {

        public static Location getInstance(int x, int y) {
            if (x < 0 || y < 0) {
                return null;
            }
            return new Location(x, y);
        }
    }
}
