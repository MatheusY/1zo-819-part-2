package streamOperations;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import streamOperations.element.Pet;

public class StreamExtras {
    public static void main(String[] args) {
    	List<Pet> petPopulation = Stream.generate(Pet::new)
//        		.distinct()
                .limit(5)
                .collect(Collectors.toList());

        petPopulation.forEach(System.out::println);

        System.out.println("--- Group by Pet using groupingBy ---");
	     // Take list of Pet, collect into a Map using groupingBy
	     // chain to stream of Entry and print key,values.
	     petPopulation.stream()
	             .collect(
	                     // groupingBy with a single argument
//	                     Collectors.groupingBy(Pet::getType))
	            		 Collectors.groupingBy(Pet::getType,
	                             TreeMap::new,
	                             Collectors.toList()))
	             .entrySet()
	             .stream()
	             .forEach(System.out::println);
	     System.out.println("-----------------------------");
//	     System.out.println("--- Group by Pet using toMap ---");
//	     // Take list of Pet, collect into a Map using toMap
//	     // chain to stream of Entry and print key,values.
//	     petPopulation.stream()
//	             .collect(
//	                     // toMap requires at least 2 arguments
//	                     Collectors.toMap(Pet::getType, p -> p))
//	             .entrySet()
//	             .stream()
//	             .forEach(System.out::println);
	     petPopulation.stream()
	        .distinct()
	        .collect(
	                // toMap requires at least 2 arguments
//	                Collectors.toMap(
//	                        p -> p.getType() + "_" + p.getName(),
//	                        p -> p))
	        		Collectors.toMap(
	                        p -> p.getType() + "_" + p.getName(),
	                        p -> p,
	                        // merge function ignored if not parallel
	                        (existing, replacement) -> existing,
	                        TreeMap::new))
	        .entrySet()
	        .stream()
	        .forEach(System.out::println);
    }
}
