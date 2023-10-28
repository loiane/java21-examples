package com.loiane;

public class SwitchPatternMatching {

    sealed interface Shape permits Circle, Rectangle, Square {
        double area();
    }
    record Circle(double radius) implements Shape {
        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }
    record Rectangle(double length, double width) implements Shape {
        @Override
        public double area() {
            return length * width;
        }
    }
    record Square(double side) implements Shape {
        @Override
        public double area() {
            return side * side;
        }
    }

    static double calcArea(Shape shape) {
        return switch (shape) {
            case Circle c -> c.area();
            case Rectangle r -> r.area();
            case Square s -> s.area();
        };
    }

    static double calcAreaBeforeJDK21(Shape shape) {
        if (shape instanceof Circle c) {
            return c.area();
        } else if (shape instanceof Rectangle r) {
            return r.area();
        } else if (shape instanceof Square s) {
            return s.area();
        }
        return 0;
    }

    // case dominance - does not compile
    static String checkShapeType(Object object) {
        return switch (object) {
            // case Shape s -> s.getClass().getSimpleName();
            case Circle c -> "Circle";
            case Rectangle r -> "Rectangle";
            case Square s -> "Square";
            case Shape s -> s.getClass().getSimpleName();
            default -> "Unknown";
        };
    }

    static boolean transformToBoolean(String input) {
        return switch (input) {
            case null -> throw new IllegalArgumentException("Input cannot be null");
            case "y", "Y" -> true;
            case "n", "N" -> false;
            case String s when s.equalsIgnoreCase("YES") ->  true;
            case String s when s.equalsIgnoreCase("NO") -> false;
            case String s -> throw new IllegalArgumentException(STR."Input has invalid value: \{s}");
        };

    }

    public static void main(String[] args) {

        System.out.println(STR."Area of Circle: \{calcArea(new Circle(2))}"); // 12.566370614359172
        System.out.println(STR."Area of Rectangle: \{calcArea(new Rectangle(2, 3))}"); // 6.0
        System.out.println(STR."Area of Square: \{calcArea(new Square(2))}"); // 4.0


        // Enhanced type checking and Dominance of case labels
        //System.out.println(STR."Transform to boolean: \{transformToBoolean(null)}"); // IllegalArgumentException: Input cannot be null
        System.out.println(STR."Transform to boolean: \{transformToBoolean("y")}"); // true
        System.out.println(STR."Transform to boolean: \{transformToBoolean("N")}"); // false
        System.out.println(STR."Transform to boolean: \{transformToBoolean("YES")}"); // true
        System.out.println(STR."Transform to boolean: \{transformToBoolean("NO")}"); // false
        System.out.println(STR."Transform to boolean: \{transformToBoolean("Maybe")}"); // IllegalArgumentException: Input has invalid value: Maybe

    }

    // Prior to Java 21
    // motivation
    static String formatter(Object obj) {
        String formatted = "unknown";
        if (obj instanceof Integer i) {
            formatted = String.format("int %d", i);
        } else if (obj instanceof Long l) {
            formatted = String.format("long %d", l);
        } else if (obj instanceof Double d) {
            formatted = String.format("double %f", d);
        } else if (obj instanceof String s) {
            formatted = String.format("String %s", s);
        }
        return formatted;
    }

    // As of Java 21
    static String formatterPatternSwitch(Object obj) {
        return switch (obj) {
            case Integer i -> String.format("int %d", i);
            case Long l    -> String.format("long %d", l);
            case Double d  -> String.format("double %f", d);
            case String s  -> String.format("String %s", s);
            default        -> obj.toString();
        };
    }

    // null testing
    // Prior to Java 21
    static void testFooBarOld(String s) {
        if (s == null) {
            System.out.println("Oops!");
            return;
        }
        switch (s) {
            case "Foo", "Bar" -> System.out.println("Great");
            default           -> System.out.println("Ok");
        }
    }

    // As of Java 21
    static void testFooBarNew(String s) {
        switch (s) {
            case null         -> System.out.println("Oops");
            case "Foo", "Bar" -> System.out.println("Great");
            default           -> System.out.println("Ok");
        }
    }

    // Case refinement
    // As of Java 21
    static void testStringOld(String response) {
        switch (response) {
            case null -> { }
            case String s -> {
                if (s.equalsIgnoreCase("YES"))
                    System.out.println("You got it");
                else if (s.equalsIgnoreCase("NO"))
                    System.out.println("Shame");
                else
                    System.out.println("Sorry?");
            }
        }
    }

    // As of Java 21
    static void testStringNew(String response) {
        switch (response) {
            case null -> { }
            case String s
                    when s.equalsIgnoreCase("YES") -> {
                System.out.println("You got it");
            }
            case String s
                    when s.equalsIgnoreCase("NO") -> {
                System.out.println("Shame");
            }
            case String s -> {
                System.out.println("Sorry?");
            }
        }
    }

    //enhanced
    // As of Java 21
    static void testStringEnhanced(String response) {
        switch (response) {
            case null -> { }
            case "y", "Y" -> {
                System.out.println("You got it");
            }
            case "n", "N" -> {
                System.out.println("Shame");
            }
            case String s
                    when s.equalsIgnoreCase("YES") -> {
                System.out.println("You got it");
            }
            case String s
                    when s.equalsIgnoreCase("NO") -> {
                System.out.println("Shame");
            }
            case String s -> {
                System.out.println("Sorry?");
            }
        }
    }
}
