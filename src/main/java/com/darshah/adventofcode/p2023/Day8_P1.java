package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day8_P1 {

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_8_input_1.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var lines = linesStream.toList();

            var pattern = Pattern.compile("(?<FROM>\\w+)\\s=\\s\\((?<LEFT>\\w+),\\s(?<RIGHT>\\w+)\\)");
            var nodeMap = new HashMap<String, Node>();

            var directions = lines.get(0);
            Node head = null;
            Node tail = null;

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

                if ("AAA".equals(fromLocation)) {
                    head = fromNode;
                } else if ("ZZZ".equals(fromLocation)) {
                    tail = fromNode;
                }
            }

            System.out.println(head);
            System.out.println(tail);
            long ans = 0;

            Objects.requireNonNull(head);
            Objects.requireNonNull(tail);

            while (head != tail) {
                for (char next : directions.toCharArray()) {
                    // System.out.printf("Current: %s", head.location);
                    if (next == 'L') {
                        head = head.left;
                    } else {
                        head = head.right;
                    }
                    // System.out.printf(" > Next %c: %s%n", next, head.location);
                    ans++;

                    if (head == tail) {
                        break;
                    }
                }
                // System.out.println("One iteration completed...");
            }

            System.out.println("Result: ");
            System.out.println(ans);
        }
    }

    public static class Node {

        public String location;
        public Node left;
        public Node right;

        public Node(String location) {
            this.location = location;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("location='").append(location).append('\'');
            sb.append(", left=").append(left == null ? "null" : left.location);
            sb.append(", right=").append(right == null ? "null" : right.location);
            sb.append('}');
            return sb.toString();
        }
    }
}
