package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day1_P2 {

    private static final Map<Character, Node> tree;

    static {
        tree = new HashMap<>();
        Map.of(
            "one", '1',
            "two", '2',
            "three", '3',
            "four", '4',
            "five", '5',
            "six", '6',
            "seven", '7',
            "eight", '8',
            "nine", '9'
        ).forEach((number, charRep) -> {
            Node head = null;
            for (char cur : number.toCharArray()) {
                if (head == null) {
                    head = tree.computeIfAbsent(cur, (__) -> new Node());
                } else {
                    head = head.next.computeIfAbsent(cur, (__) -> new Node());
                }
            }
            head.value = charRep;
        });
        // System.out.println(tree);
    }

    public static void main(String[] args) throws IOException {
        // var inputPath = Path.of("2023/day_1_input_2_sample.txt");
        var inputPath = Path.of("2023/day_1_input_1.txt");
        try (Stream<String> lines = Files.lines(inputPath)) {
            var sum = lines
                .parallel()
                .map(Day1_P2::mapToDigits)
                // .peek(System.out::println)
                .map(line -> {
                    var digits = line.chars().filter(Character::isDigit).toArray();
                    var result = "%c%c".formatted((char) digits[0], (char) digits[digits.length - 1]);
                    // System.out.printf("%s -> %s\n", line, result);
                    // System.out.println(result);
                    return result;
                })
                .mapToInt(Integer::parseInt)
                .sum();

            System.out.println("Result: ");
            System.out.println(sum);
        }
    }

    private static String mapToDigits(String input) {
        // mkeightwooneseven2
        // thone2
        // xyzninine -> xyzni9
        // 012345678
        Node node = null;
        StringBuilder result = new StringBuilder();
        StringBuilder buffer = new StringBuilder();
        var chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            var cur = chars[i];
            if (node == null) {
                if (tree.containsKey(cur)) {
                    node = tree.get(cur);
                    buffer.append(cur);
                } else {
                    result.append(cur);
                }
            } else {
                if (node.next.containsKey(cur)) {
                    node = node.next.get(cur);
                    buffer.append(cur);
                } else {
                    result.append(buffer.charAt(0));
                    i -= buffer.length();
                    buffer = new StringBuilder();
                    node = null;
                }
            }

            if (node != null && node.value != null) {
                result.append(node.value);
                buffer = new StringBuilder();
                node = null;
                i--;
            }
        }

        return result.append(buffer).toString();
    }

    static class Node {

        Character value;
        Map<Character, Node> next = new HashMap<>();

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("value=").append(value);
            sb.append(", next=").append(next);
            sb.append('}');
            return sb.toString();
        }
    }
}
