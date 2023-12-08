package com.darshah.adventofcode.p2023;

import com.darshah.adventofcode.p2023.Day8_P1.Node;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day8_P2 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_8_input_1.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var lines = linesStream.toList();

            var pattern = Pattern.compile("(?<FROM>\\w+)\\s=\\s\\((?<LEFT>\\w+),\\s(?<RIGHT>\\w+)\\)");
            var nodeMap = new HashMap<String, Node>();
            var directions = lines.get(0);

            var currentNodes = new LinkedList<Node>();
            var totalEndsWithZ = 0L;

            for (int i = 2; i < lines.size(); i++) {
                var matcher = pattern.matcher(lines.get(i).trim());
                if (!matcher.find()) {
                    throw new IllegalArgumentException("Could not parse line " + lines.get(i));
                }
                var fromLocation = matcher.group("FROM");
                var leftLocation = matcher.group("LEFT");
                var rightLocation = matcher.group("RIGHT");

                var leftNode = nodeMap.computeIfAbsent(leftLocation, Node::new);
                var rightNode = nodeMap.computeIfAbsent(rightLocation, Node::new);
                var fromNode = nodeMap.computeIfAbsent(fromLocation, Node::new);
                fromNode.left = leftNode;
                fromNode.right = rightNode;

                if (fromLocation.endsWith("A")) {
                    currentNodes.add(fromNode);
                } else if (fromLocation.endsWith("Z")) {
                    totalEndsWithZ++;
                }
            }

            var oscillationList = new LinkedList<Long>();

            for (var node : currentNodes) {
                var iterations = getOscillations(node, directions);
                oscillationList.add(iterations);
            }

            System.out.println("Result: ");
            System.out.println(oscillationList);
            System.out.println("Calculate LCM from https://www.calculatorsoup.com/calculators/math/lcm.php");
        }
    }

    private static long getOscillations(Node node, String directions) {
        var head = node;
        long iterations = 0;
        while (!head.location.endsWith("Z")) {
            for (char next : directions.toCharArray()) {
                // System.out.printf("Current: %s", head.location);
                if (next == 'L') {
                    head = head.left;
                } else {
                    head = head.right;
                }
                // System.out.printf(" > Next %c: %s%n", next, head.location);
                iterations++;

                if (head.location.endsWith("Z")) {
                    break;
                }
            }
        }
        return iterations;
    }
}
