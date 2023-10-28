package com.loiane;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UnnamedVariables {

    public static void main(String[] args) {

        record Order(int qty) {}
        List<Order> orders = List.of(new Order(1), new Order(2), new Order(3));
        final int LIMIT = 10;
        int total = 0;

        // Prior to Java 21
        for (Order order : orders) {
            if (total < LIMIT) {
                total++;
            }
        }

        // As of Java 21
        for (var _ : orders) {
            if (total < LIMIT) {
                total++;
            }
        }

        // bug in Java 21.0 https://bugs.openjdk.org/browse/JDK-8313323
        // use >= 21.0.1

        System.out.println(formatDate("2023-01-01"));
    }

    // real use case
    static String formatDate(String unformattedDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(unformattedDate);
            return sdf.format(date);
        } catch (Exception _) {
            // log error without using exception
            System.out.println("Error parsing date");
        }
        return null;
    }
}
