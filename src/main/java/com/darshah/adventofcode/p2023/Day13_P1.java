package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Day13_P1 {


    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_13.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var lines = linesStream.toList();
            var list = new LinkedList<String>();
            long ans = 0;

            for (var line : lines) {
                if (line.isEmpty()) {
                    ans += solve(list);
                    list = new LinkedList<>();
                } else {
                    list.addLast(line);
                }
            }

            ans += solve(list);

            System.out.println("Result: ");
            System.out.println(ans);
        }
    }

    public static long solve(List<String> input) {
        // System.out.println(input);
        var horizontal = getHorizontalReflection(input);
        if (horizontal != -1) {
            return 100 * horizontal;
        }

        var vertical = getVerticalReflection(input);
        if (vertical != -1) {
            return vertical;
        }
        throw new RuntimeException("Could not solve \n" + String.join("\n", input));
    }

    public static long getVerticalReflection(List<String> input) {
        var transposed = new LinkedList<String>();
        for (int i = 0; i < input.get(0).length(); i++) {
            var sb = new StringBuilder();
            for (var str : input) {
                sb.append(str.charAt(i));
            }
            transposed.addLast(sb.toString());
        }
        // transposed.forEach(System.out::println);
        return getHorizontalReflection(transposed);
    }

    public static long getHorizontalReflection(List<String> input) {
        var map = new HashMap<String, TreeSet<Integer>>();
        var lastIndex = input.size() - 1;
        var first = input.get(0);
        var last = input.get(lastIndex);

        for (int i = 0; i < input.size(); i++) {
            map.computeIfAbsent(input.get(i), __ -> new TreeSet<>()).add(i);
        }
        // System.out.println(map);

        var queue = new LinkedList<>(map.get(first));
        var from = queue.removeFirst();
        while (!queue.isEmpty()) {
            long midPoint = -1;
            if ((midPoint = getMidPoint(input, from, queue.removeLast())) != -1) {
                return midPoint;
            }
        }

        queue = new LinkedList<>(map.get(last));
        var to = queue.removeLast();
        while (!queue.isEmpty()) {
            long midPoint = -1;
            if ((midPoint = getMidPoint(input, queue.removeFirst(), to)) != -1) {
                return midPoint;
            }
        }

        return -1;
    }

    //    private static int getPreviousIndexOf(List<String> list, int lookup) {
    //        return 0;
    //    }
    //
    //    private static int getNextIndexOf(List<String> list, int lookup) {
    //        var lookupStr = list.get(lookup);
    //        for (int i = lookup + 1; i < list.size(); i++) {
    //            if (lookupStr.equals(list.get(i))) {
    //                return i;
    //            }
    //        }
    //
    //        return -1;
    //    }

    public static long getMidPoint(List<String> input, int top, int bottom) {
        while (top < bottom) {
            if (!input.get(top).equals(input.get(bottom))) {
                return -1;
            }
            top++;
            bottom--;
        }
        return top == bottom ? -1 : top;
    }
}
