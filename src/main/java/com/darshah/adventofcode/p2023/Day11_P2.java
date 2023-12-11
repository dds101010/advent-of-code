package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.stream.Stream;

public class Day11_P2 {


    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_11.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var lines = linesStream.toList();

            var len = lines.get(0).length();

            var galaxies = new LinkedList<Location>();
            boolean[] colsWithNoGalaxies = new boolean[len];
            boolean[] rowsWithNoGalaxies = new boolean[len];

            for (int row = 0; row < len; row++) {
                var line = lines.get(row);
                for (int col = 0; col < len; col++) {
                    if (line.charAt(col) == '#') {
                        colsWithNoGalaxies[col] = true;
                        rowsWithNoGalaxies[row] = true;
                    }
                }
            }

            long[] colAdjustment = new long[len];
            for (int i = 0; i < len - 1; i++) {
                colAdjustment[i + 1] = colAdjustment[i];
                if (!colsWithNoGalaxies[i]) {
                    colAdjustment[i + 1] += (1000000 - 1);
                }
            }
            // System.out.println(Arrays.toString(colsWithNoGalaxies));
            // System.out.println(Arrays.toString(colAdjustment));

            long[] rowAdjustment = new long[len];
            for (int i = 0; i < len - 1; i++) {
                rowAdjustment[i + 1] = rowAdjustment[i];
                if (!rowsWithNoGalaxies[i]) {
                    rowAdjustment[i + 1] += (1000000 - 1);
                }
            }
            // System.out.println(Arrays.toString(rowsWithNoGalaxies));
            // System.out.println(Arrays.toString(rowAdjustment));

            for (int row = 0; row < len; row++) {
                var line = lines.get(row);
                for (int col = 0; col < len; col++) {
                    if (line.charAt(col) == '#') {
                        galaxies.add(new Location(row + rowAdjustment[row], col + colAdjustment[col]));
                    }
                }
            }

            long ans = 0;
            for (int i = 0; i < galaxies.size(); i++) {
                for (int j = i + 1; j < galaxies.size(); j++) {
                    ans += distance(galaxies.get(i), galaxies.get(j));
                }
            }

            System.out.println("Result: ");
            System.out.println(ans);
        }
    }

    public static long distance(Location from, Location to) {
        return Math.abs(from.row - to.row) + Math.abs(from.col - to.col);
    }

    public static record Location(long row, long col) {

    }
}
