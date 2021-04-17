package parallelStream;

/*
The Learn Programming Academy
Java SE 11 Developer 1Z0-819 OCP Course - Part 2
Section 12:  Parallel Stream
Topic:  Collect and Reduce
*/

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ParallelExtras {
    public static void main(String[] args) {

        Set<String> set = new TreeSet<>(Set.of("b", "c", "d"));

        // Using reduce with String
//        String firstResult = set
//                .stream()
////                .reduce(String::concat)
//                // reduce returns Optional,
//                // need to use get() to get value
////                .get();
//                .reduce("", String::concat, String::concat);
        
     // Using reduce with StringBuilder
        StringBuilder firstResult = set
                .parallelStream()
                .map(s -> new StringBuilder(s))
                // Requires Identity(StringBuilder), BiFunction, BinaryOperator
                .reduce(new StringBuilder("a"),
                        (a, b) -> b.append(a),
                        (a, b) -> a.append(b));

        System.out.println("firstResult = " + firstResult);

        // Using collect with String
//        String secondResult = set
//                .stream()
//                // collect requires Collector argument
////                .collect(Collectors.joining());
//                .collect(String::new, String::concat, String::concat);
        
     // Using collect with StringBuilder
        StringBuilder secondResult = set
                .parallelStream()
                .map(s -> new StringBuilder(s))
                // collect requires Supplier, BiConsumer, BiConsumer
                .collect(/*StringBuilder::new*/ () -> new StringBuilder("a"),
                        (a, b) -> b.append(a),
                        (a, b) -> a.append(b));

        System.out.println("secondResult = " + secondResult);
    }
}
