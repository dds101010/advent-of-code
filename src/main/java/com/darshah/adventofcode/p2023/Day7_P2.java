package com.darshah.adventofcode.p2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Day7_P2 {

    public static Map<Character, Integer> cardsPriority = new HashMap<>();

    static {
        cardsPriority.put('J', 1);
        cardsPriority.put('2', 2);
        cardsPriority.put('3', 3);
        cardsPriority.put('4', 4);
        cardsPriority.put('5', 5);
        cardsPriority.put('6', 6);
        cardsPriority.put('7', 7);
        cardsPriority.put('8', 8);
        cardsPriority.put('9', 9);
        cardsPriority.put('T', 10);
        cardsPriority.put('Q', 12);
        cardsPriority.put('K', 13);
        cardsPriority.put('A', 14);
    }

    public static void main(String[] args) throws IOException {
        var inputPath = Path.of("2023/day_7_input_1.txt");
        try (Stream<String> linesStream = Files.lines(inputPath)) {
            var lines = linesStream.toList();
            var bids = new TreeSet<Bid>();

            for (var line : lines) {
                var split = line.trim().split("\\s+");
                var bid = new Bid(Hand.getInstance(split[0]), Long.parseLong(split[1]));
                bids.add(bid);
            }

            int rank = 1;
            long ans = 0;
            for (var bid : bids) {
                System.out.printf("> rank %d, Bid %s%n", rank, bid);
                ans += (bid.amount * rank);
                rank++;
            }

            System.out.println("Result: ");
            System.out.println(ans);
        }
    }

    record Bid(Hand hand, long amount) implements Comparable<Bid> {

        @Override
        public int compareTo(Bid that) {
            return this.hand.compareTo(that.hand);
        }
    }

    record Hand(String cards, HandType handType) implements Comparable<Hand> {

        public static Hand getInstance(String cards) {
            var map = new HashMap<Character, Integer>();
            int jokerCount = 0;

            for (Character card : cards.toCharArray()) {
                if (card == 'J') {
                    jokerCount++;
                    continue;
                }
                var newValue = map.getOrDefault(card, 0) + 1;
                map.put(card, newValue);
            }

            if (map.isEmpty()) {
                // all jokers
                return new Hand(cards, HandType.FIVE_OF_A_KIND);
            }

            int max = map.values().stream().max(Integer::compareTo).orElseThrow();
            long threeCardsPair = map.values().stream().filter(i -> i == 3).count();
            long twoCardsPair = map.values().stream().filter(i -> i == 2).count();

            if (max == 5) {
                return new Hand(cards, HandType.FIVE_OF_A_KIND);
            } else if (max == 4) {
                if (jokerCount == 1) {
                    return new Hand(cards, HandType.FIVE_OF_A_KIND);
                } else {
                    return new Hand(cards, HandType.FOUR_OF_A_KIND);
                }
            } else if (twoCardsPair == 1 && threeCardsPair == 1) {
                return new Hand(cards, HandType.FULL_HOUSE);
            } else if (threeCardsPair == 1) {
                if (jokerCount == 2) {
                    return new Hand(cards, HandType.FIVE_OF_A_KIND);
                } else if (jokerCount == 1) {
                    return new Hand(cards, HandType.FOUR_OF_A_KIND);
                } else {
                    return new Hand(cards, HandType.THREE_OF_A_KIND);
                }
            } else if (twoCardsPair == 2) {
                if (jokerCount == 1) {
                    return new Hand(cards, HandType.FULL_HOUSE);
                } else {
                    return new Hand(cards, HandType.TWO_PAIR);
                }
            } else if (twoCardsPair == 1) {
                if (jokerCount == 3) {
                    return new Hand(cards, HandType.FIVE_OF_A_KIND);
                } else if (jokerCount == 2) {
                    return new Hand(cards, HandType.FOUR_OF_A_KIND);
                } else if (jokerCount == 1) {
                    return new Hand(cards, HandType.THREE_OF_A_KIND);
                } else {
                    return new Hand(cards, HandType.ONE_PAIR);
                }
            } else {
                if (jokerCount == 4) {
                    return new Hand(cards, HandType.FIVE_OF_A_KIND);
                } else if (jokerCount == 3) {
                    return new Hand(cards, HandType.FOUR_OF_A_KIND);
                } else if (jokerCount == 2) {
                    return new Hand(cards, HandType.THREE_OF_A_KIND);
                } else if (jokerCount == 1) {
                    return new Hand(cards, HandType.ONE_PAIR);
                }
                return new Hand(cards, HandType.HIGH_CARD);
            }
        }

        @Override
        public int compareTo(Hand that) {
            if (this.handType != that.handType) {
                return Integer.compare(this.handType.ordinal(), that.handType.ordinal());
            }

            for (int i = 0; i < this.cards.length(); i++) {
                int thisCardPriority = cardsPriority.get(this.cards.charAt(i));
                int thatCardPriority = cardsPriority.get(that.cards.charAt(i));

                if (thisCardPriority == thatCardPriority) {
                    continue;
                }
                return Integer.compare(thisCardPriority, thatCardPriority);
            }
            System.out.println("Cards are same! " + this.cards + ", " + that.cards);
            return 0;
        }
    }

    enum HandType {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND
    }
}
