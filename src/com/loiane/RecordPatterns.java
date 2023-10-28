package com.loiane;

public class RecordPatterns {

    public static void main(String[] args) {
        String obj = "Hello";

        // Prior to Java 16
        if (obj instanceof String) {
            String s = (String)obj;
            //... use s ...
        }

        // As of Java 16
        if (obj instanceof String s) {
            //... use s ...
        }

        //failCase();

        Point p = new Point(42, 42);
        printSum2(p);
    }

    // As of Java 16
    record Point(int x, int y) {}

    static void printSum(Object obj) {
        if (obj instanceof Point p) {
            int x = p.x();
            int y = p.y();
            System.out.println(x+y);
        }
    }

    // As of Java 21
    static void printSum2(Object obj) {
        if (obj instanceof Point(int x, int y)) {
            System.out.println(x+y);
        }
    }

    // Nested record patterns
    record Rectangle(Point upperLeft, Point lowerRight) {}
    record Circle(Point center, int radius) {}

    static double calcArea(Object obj) {
        if (obj instanceof Rectangle(Point(int x1, int y1), Point(int x2, int y2))) {
            return (x2 - x1) * (y2 - y1);
        } else if (obj instanceof Circle(Point center, int radius)) {
            return Math.PI * radius * radius;
        }
        return 0.0;
    }

    // type inference
    static double calcAreaTypeInference(Object obj) {
        if (obj instanceof Rectangle(Point(var x1, var y1), Point(var x2, var y2))) {
            return (x2 - x1) * (y2 - y1);
        } else if (obj instanceof Circle(var center, var radius)) {
            return Math.PI * radius * radius;
        }
        return 0.0;
    }



    static void failCase() {
        // As of Java 21
        record Pair(Object x, Object y) {}

        Pair p = new Pair(42, 42);

        if (p instanceof Pair(String s, String t)) {
            System.out.println(s + ", " + t);
        } else {
            System.out.println("Not a pair of strings");
        }
    }

    class SwitchRecordPattern {
        record Point(int x, int y) {}
        sealed interface Figure permits Rectangle, Circle {}
        record Rectangle(Point upperLeft, Point lowerRight) implements Figure{}
        record Circle(Point center, int radius) implements Figure{}

        double calcArea(Figure obj) {
            return switch (obj) {
                case null -> 0.0;
                case Rectangle(Point(var x1, var y1), Point(var x2, var y2)) -> (x2 - x1) * (y2 - y1);
                case Circle(var center, var radius) -> Math.PI * radius * radius;
            };
        }

    }

}
