package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Day10_P2 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_10_p1_done.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {

            var magnifiedLines = linesStream.flatMap(Day10_P2::magnify)/*.peek(System.out::println)*/.toList();

            char[][] grid = new char[magnifiedLines.size()][magnifiedLines.size()];
            for (int i = 0; i < magnifiedLines.size(); i++) {
                var line = magnifiedLines.get(i);
                for (int j = 0; j < line.length(); j++) {
                    grid[i][j] = line.charAt(j);
                }
            }

            floodFill(grid, 0, 0);

            int ans = 0;

            for (char[] value : grid) {
                for (char c : value) {
                    if (c == '.') {
                        ans++;
                    }
                }
            }

            System.out.println("Result: ");
            System.out.println(ans);

            for (char[] chars : grid) {
                System.out.println(String.valueOf(chars));
            }
        }
    }

    private static void floodFill(char[][] grid, int x, int y) {
        if (x < 0 || y < 0 || x >= grid.length || y >= grid.length) {
            return;
        }
        if (grid[x][y] == 'O' || grid[x][y] == '|' || grid[x][y] == '-' || grid[x][y] == 'J' || grid[x][y] == 'L'
            || grid[x][y] == 'F' || grid[x][y] == '7') {
            return;
        }
        grid[x][y] = 'O';
        floodFill(grid, x + 1, y);
        floodFill(grid, x + 1, y + 1);
        floodFill(grid, x, y + 1);
        floodFill(grid, x - 1, y + 1);
        floodFill(grid, x - 1, y);
        floodFill(grid, x - 1, y - 1);
        floodFill(grid, x, y - 1);
        floodFill(grid, x + 1, y - 1);
    }

    private static Stream<StringBuilder> magnify(String str) {
        var b1 = new StringBuilder();
        var b2 = new StringBuilder();
        var b3 = new StringBuilder();

        for (char c : str.toCharArray()) {
            switch (c) {
                case '.': {
                    b1.append("   ");
                    b2.append(" . ");
                    b3.append("   ");
                    break;
                }
                case '|': {
                    b1.append(" | ");
                    b2.append(" | ");
                    b3.append(" | ");
                    break;
                }
                case '-': {
                    b1.append("   ");
                    b2.append("---");
                    b3.append("   ");
                    break;
                }
                case 'F': {
                    b1.append("   ");
                    b2.append(" F-");
                    b3.append(" | ");
                    break;
                }
                case '7': {
                    b1.append("   ");
                    b2.append("-7 ");
                    b3.append(" | ");
                    break;
                }
                case 'J': {
                    b1.append(" | ");
                    b2.append("-J ");
                    b3.append("   ");
                    break;
                }
                case 'L': {
                    b1.append(" | ");
                    b2.append(" L-");
                    b3.append("   ");
                    break;
                }
                default: {
                    b1.append("   ");
                    b2.append("   ");
                    b3.append("   ");
                }
            }
        }

        return Stream.of(b1, b2, b3);
    }

    private static char getReplacement(char c) {
        if (c == '|') {
            return '║';
        } else if (c == '-') {
            return '═';
        } else if (c == '7') {
            return '╗';
        } else if (c == 'F') {
            return '╔';
        } else if (c == 'L') {
            return '╚';
        } else if (c == 'J') {
            return '╝';
        } else {
            return 'X';
        }
    }
}
