package com.loiane;

import static java.lang.StringTemplate.RAW;
import static java.util.FormatProcessor.FMT;

public class StringTemplates {

    public static void main(String[] args) {

        int recordId = 10;
        String entityName = "Product";

        String log = entityName + " with ID " + recordId + " saved successfully.";

        StringBuilder sb = new StringBuilder()
                .append(entityName).append(" with ID ").append(recordId).append(" saved successfully.");

        System.out.println(log);
        System.out.println(sb);

        // problem
        final String firstName = "Loiane' OR p.first_name = 'Loiane_";
        String query = STR."SELECT * FROM Person p WHERE p.first_name = '\{firstName}'";
        System.out.println(query);


        // String Processors - Java 21
        // The STR template processor
        String template = STR."\{entityName} with ID \{recordId} save successfully.";

        System.out.println(template);

        final String LANGUAGE = "Java";
        final int VERSION = 21;
        String multiLine = STR."""
                {
                    "language": "\{LANGUAGE}",
                    "version":\s\{VERSION}
                }
                """;

        System.out.println(multiLine);

        // safeguard
        // java: processor missing from string template expression
        //String doesNotCompile = "My name is \{name}";

        // The FMT template processor
        // The format specifiers are the same as those defined in java.util.Formatter
        record CartItem(String name, double price, int quantity) {
            double subTotal(){
                return price * quantity;
            }
        }

        CartItem[] cartItems = new CartItem[] {
          new CartItem("Coffee", 3.45, 3),
          new CartItem("Latte", 5.20, 1),
          new CartItem("Cappuccino", 4.60, 2),
        };

        String receipt = FMT."""
            Item           Price  Qty  Subtotal
            %-10s\{cartItems[0].name()}  %7.2f\{cartItems[0].price()}  %2d\{cartItems[0].quantity()}     %5.2f\{cartItems[0].subTotal()}
            %-10s\{cartItems[1].name()}  %7.2f\{cartItems[1].price()}  %2d\{cartItems[1].quantity()}     %5.2f\{cartItems[1].subTotal()}
            %-10s\{cartItems[2].name()}  %7.2f\{cartItems[2].price()}  %2d\{cartItems[2].quantity()}     %5.2f\{cartItems[2].subTotal()}
            \{" ".repeat(19)} Total %7.2f\{cartItems[0].subTotal() + cartItems[1].subTotal() + cartItems[2].subTotal()}
            """;

        System.out.println(receipt);

        // ensuring safety - RAW
        String name = "Loiane";
        String info = STR."My name is \{name}";

        StringTemplate st = RAW."My name is \{name}";
        String processed = STR.process(st);

        System.out.println(processed);


    }
}
