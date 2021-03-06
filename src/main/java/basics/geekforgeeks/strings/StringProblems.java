package basics.geekforgeeks.strings;

import basics.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class StringProblems {
    public static void main(String[] args) {
        System.out.println(RepeatingCharacters.LeftMostNonRepeatingCharacter.indexOfLeftMostNonRepeatingCharacter("geekforgeeks"));
    }
}

class RepeatingCharacters {

    static class LeftMostRepeatingCharacter {
        static int indexOfLeftMostRepeatingCharacter(String s) {
            if (s == null || s.length() <= 1) return -1;
            return usingAsciiAsCounter(s);
        }

        private static int usingBruteForceDoubleIteration(String s) {
            int index = -1;

            for (int i = 0; i < s.length(); i++) {
                for (int j = i + 1; j < s.length(); j++) {
                    if (s.charAt(i) == s.charAt(j)) return i;
                }
            }

            return index;
        }

        private static int usingMapCounter(String s) {
            Map<Character, Integer> counterMap = new LinkedHashMap<Character, Integer>();
            for (Character c : s.toCharArray()) {
                Integer count = counterMap.getOrDefault(c, 0);
                counterMap.put(c, ++count);
            }

            int res = -1;
            System.out.println(counterMap);
            for (Map.Entry entry : counterMap.entrySet()) {
                res++;
                if (entry.getValue() != null && (Integer) entry.getValue() > 1) return res;
            }
            return res;
        }

        private static int usingAsciiAsCounter(String s) {
            int[] counts = new int[256];

            for (int i = 0; i < s.length(); i++) {
                counts[s.charAt(i)]++;
            }

            for (int i = 0; i < s.length(); i++) {
                if (counts[s.charAt(i)] > 1) return i;
            }
            return -1;
        }
    }

    static class LeftMostNonRepeatingCharacter {
        static int indexOfLeftMostNonRepeatingCharacter(String s) {
            if (s == null) return -1;
            if (s.length() == 1) return 0;
            return usingEfficientCounterOneIteration(s);
        }

        private static int usingBruteForceDoubleIteration(String s) {
            int length = s.length();
            for (int i = 0; i < length; i++) {
                boolean didRepeat = false;
                for (int j = 0; j < length; j++) {
                    if (i != j && s.charAt(i) == s.charAt(j)) {
                        didRepeat = true;
                        break;
                    }
                }
                if (!didRepeat) return i;

            }
            return -1;
        }

        private static int usingEfficientCounterTwoIterations(String s) {
            int[] counts = new int[256];
            int length = s.length();
            for (int i = 0; i < length; i++) {
                counts[s.charAt(i)]++;
            }

            for (int i = 0; i < length; i++) {
                if (counts[s.charAt(i)] == 1) return i;
            }
            return -1;
        }

        private static int usingEfficientCounterOneIteration(String s) {
            int[] indexes = new int[256];
            Arrays.fill(indexes, -1);
            int length = s.length();
            for (int i = 0; i < length; i++) {
                if (indexes[s.charAt(i)] == -1) {
                    indexes[s.charAt(i)] = i;
                } else {
                    indexes[s.charAt(i)] = Integer.MIN_VALUE;
                }
            }

            int result = Integer.MAX_VALUE;
            for (int i = 0; i < indexes.length; i++) {
                if (indexes[i] >= 0) result = Math.min(result, indexes[i]);
            }

            return result == Integer.MAX_VALUE ? -1 : result;
        }
    }
}

class StringReversal {
    static String reverse(String s) {
        if (s == null || s.length() == 1) return s;

        // TODO
        return "";
    }
}

class PrintStrings {
    static void printFrequency(String str) {
        int[] counts = getCounts(str);

        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 0) continue;
            String print = (char) (i + 'a') + "=" + counts[i];
            System.out.println(print);
        }
    }

    static void printSorted(String str) {
        int[] counts = getCounts(str);

        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 0) continue;

            for (int j = 0; j < counts[i]; j++) {
                System.out.print((char) (i + 'a'));
            }
        }
    }

    @NotNull
    private static int[] getCounts(String str) {
        int[] counts = new int[26];
        int length = str.length();
        for (int i = 0; i < length; i++) {
            int charInt = str.charAt(i);
            int indexCalculation = charInt - 'a';
            counts[indexCalculation]++;
        }
        return counts;
    }
}

class StringSubsequence {
    static boolean checkStringSubsequenceIterative(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        int i = 0, j = 0;
        while (i < s1.length() && j < s2.length()) {
            if (s1.charAt(i) == s2.charAt(j)) {
                i++;
                j++;
            } else {
                i++;
            }
        }

        if (j == s2.length()) return true;
        else return false;
    }

    static boolean checkStringSubsequenceRecursive(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() > s1.length()) return false;
        return checkStringSubsequenceRecursive(s1, s2, 0, 0);
    }

    private static boolean checkStringSubsequenceRecursive(String s1, String s2, int i, int j) {
        if (j == s2.length()) return true;
        if (i == s1.length()) return false;

        if (s1.charAt(i) == s2.charAt(j)) {
            return checkStringSubsequenceRecursive(s1, s2, ++i, ++j);
        } else {
            return checkStringSubsequenceRecursive(s1, s2, ++i, j);
        }
    }

}

class Anagram {
    static boolean isAnagram(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) return false;
        return isAnagramEfficientByCounting(s1, s2);
    }

    private static boolean isAnagramUsingFrequency(String s1, String s2) {
        Map<Character, Integer> s1Map = Utils.frequencyMap(s1);
        Map<Character, Integer> s2Map = Utils.frequencyMap(s2);

        for (Map.Entry e : s1Map.entrySet()) {
            if (e.getValue() != s2Map.get(e.getKey())) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAnagramBySorting(String s1, String s2) {
        return Utils.sortString(s1).equals(Utils.sortString(s2));
    }

    private static boolean isAnagramEfficientByCounting(String s1, String s2) {
        int[] counts = new int[256];
        for (int i = 0; i < s1.length(); i++) {
            counts[s1.charAt(i)]++;
            counts[s2.charAt(i)]--;
        }
        return Arrays.stream(counts).allMatch(i -> i == 0);
    }
}

class Line {
    private Set<Point> pointsOnLine;

    Line(Set<Point> points) {
        if (points == null || points.size() < 2) throw new IllegalArgumentException("Line contains at least 2 points");
        if (!valid()) {
            throw new RuntimeException("Points are not collinear");
        }
        this.pointsOnLine = points;
    }

    void addPoint(Point p) {
        pointsOnLine.add(p);
        if (!valid()) {
            pointsOnLine.remove(p);
            throw new RuntimeException("New Point is not collinear");
        }
    }

    double slope() {
        Point p1 = null, p2 = null;
        for (Point p : pointsOnLine) {
            if (p1 == null) {
                p1 = p;
                continue;
            }

            if (p2 == null) {
                p2 = p;
                continue;
            }

            if (p1 != null && p2 != null) break;
        }

        return Math.abs((p1.getY() - p2.getY()) / (p1.getX() - p2.getX()));
    }

    private boolean valid() {
        if (pointsOnLine.size() == 2) return true;
        Point[] ps = new Point[pointsOnLine.size()];
        ps = pointsOnLine.toArray(ps);
        for (Point p : ps) {

        }
        return false;
    }

    @Override
    public String toString() {
        return "Line{" +
                "pointsOnLine=" + pointsOnLine +
                '}';
    }
}

