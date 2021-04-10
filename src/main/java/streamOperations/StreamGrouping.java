package streamOperations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import streamOperations.element.Pet;

public class StreamGrouping {
    public static void main(String[] args) {

        // Print one Pet object
        System.out.println(new Pet());

        // Create a randomly generated list of 5000 pets.
        List<Pet> petPopulation = Stream.generate(Pet::new)
                .limit(5000)
                .collect(Collectors.toList());

        // Count the number of Dogs
        System.out.println("Number of Dogs = " +
                petPopulation.stream()
                        .filter((s) -> s.getType() == "Dog")
                        .count());
        
     // Count Pet Population by each Pet Type
        Map<String, Long> petTypeCounts = petPopulation.stream()
                // Collects to Map<String,Long> where key=pet type
                .collect(Collectors.groupingBy(Pet::getType,
                        Collectors.counting()));

        System.out.println("--- Counts by Pet Type: ---");
        // Create a stream from Map.entrySet()
        petTypeCounts.entrySet().stream()
                // Sort stream by pet type (key for Map)
                .sorted((s, t) -> s.getKey().compareTo(t.getKey()))
                // Print element: a key/value pair
                .forEach(System.out::println);
        
        System.out.println("--- Counts by Pet Type: ---");
        petPopulation.stream()
                // Collects to Map<String,Long> where key=pet type
                .collect(Collectors.groupingBy(Pet::getType,
                        Collectors.counting()))
                // Create a stream from Map.entrySet()
                .entrySet().stream()
                // Sort stream by pet type (key for Map)
                .sorted((s, t) -> s.getKey().compareTo(t.getKey()))
                // Print element: a key/value pair
                .forEach(System.out::println);
        
        System.out.println("--- Average Age by State, PetType: ---");
        Map<List, Double> petMaps = petPopulation.stream()
                // Collect data into a Map<List,Double>
                .collect(
                        Collectors.groupingBy(
                                // Grouping by list of attributes
                                p -> Arrays.asList(
                                        p.getState(), p.getType()),
                                // Get average age of pet
                                Collectors.averagingInt(Pet::getAge)));

        // Can get very specific about requesting information from Map
        System.out.println(String.format(
                "Average age of a dog in CO = %.2f ",
                petMaps.get(Arrays.asList("CO", "Dog"))));
        
        System.out.println("--- Counts by State, PetType: ---");
        petPopulation.stream()
                // Collect data into a Map<List,Long>
                .collect(
                        Collectors.groupingBy(
                                // Grouping by list of attributes
                                p -> Arrays.asList(p.getState(), p.getType()),
                                // Count pets by state, type
                                Collectors.counting()))
                // Create a stream from Map.entrySet()
                .entrySet().stream()
                // Sort first by state, then type, each part of key which is List
                .sorted((s, t) -> (s.getKey().get(0) + s.getKey().get(1))
                        .compareTo(t.getKey().get(0) + t.getKey().get(1)))
                // Limit data to just Cat and Dog
                .filter((s) -> (s.getKey().get(1).equals("Cat") ||
                        s.getKey().get(1).equals("Dog")))
                // Print each mapped entry
                .forEach(System.out::println);

	     // Using .groupingBy with 3 arguments, second argument is Supplier
	     // Count Pet Population by each Pet Type
	     Map<String, Long> typeCounts = petPopulation.stream()
	             // Collects to Map<String,Long> where key=pet type
	             .collect(Collectors.groupingBy(Pet::getType,
	                     // Want a treeMap so map is ordered
	                     TreeMap::new,
	                     Collectors.counting()));
	     System.out.println("----- Ordered Mapping now ----- ");
	     System.out.println(typeCounts);
	     
	     System.out.println("----- Creating Nested Collections ----- ");
	     Map<String, Map<String, Long>> TwoDMap = petPopulation.stream()
	             // Collect data into a Map<List,Double>
	             .collect(Collectors.groupingBy(
	                     // Grouping by State first
	                     Pet::getState,
	                     // Specify Sorted Map
	                     TreeMap::new,
	                     // Get average age of pet
	                     Collectors.groupingBy(
	                             // Grouping by Pet Type next
	                             Pet::getType,
	                             // Specify Sorted Map
	                             TreeMap::new,
	                             Collectors.counting()
	                     )));

	     TwoDMap.entrySet().forEach(System.out::println);
	     
	   //Creates bifurcated population
	     Map<Boolean, List<Pet>> dogsAndNotDogs =
	             petPopulation.stream().collect(
	                     // Map<Boolean,List<Pet>
	                     Collectors.partitioningBy(s -> s.getType().equals("Dog"))
	             );

	     System.out.println("Print first five dogs");
	     dogsAndNotDogs.entrySet().stream()
	             // Only want Dogs, so key is true
	             .filter((s) -> s.getKey())
	             // Flatten list of Pets to a Stream
	             .flatMap(s -> s.getValue().stream())
	             .limit(5)
	             .forEach(System.out::println);
	     
	     System.out.println("Just print dog population by veterinary type");
	     petPopulation.stream().collect(
	             // Map<Boolean, Map<String,Long>
	             Collectors.partitioningBy(s -> s.getType().equals("Dog"),
	                     Collectors.groupingBy(
	                             // Grouping by Veterinary Practice
	                             Pet::getVet,
	                             // Specify Sorted Map
	                             TreeMap::new,
	                             Collectors.counting()))
	     )
	             .entrySet().stream()
	             .filter((s) -> s.getKey())
	             .forEach(System.out::println);
    }
}
