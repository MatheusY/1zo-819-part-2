package streamOperations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import streamOperations.element.Pet;

public class FlatMapExtras {
    public static void main(String[] args) {

        // Create three different Lists of Pet objects
        List<Pet> petList1 = List.of(new Pet(), new Pet(), new Pet());
        List<Pet> petList2 = List.of(new Pet(), new Pet(), new Pet());
        List<Pet> petList3 = List.of(new Pet(), new Pet(), new Pet());

        // Create List of List of Pets
        List<List<Pet>> listOfPets
                = List.of(petList1, petList2, petList3);

        System.out.println("--- List of lists ---");
        listOfPets.forEach(System.out::println);

        // Create a list (outside of the stream)
        List<Pet> fullList = new ArrayList<Pet>();
        listOfPets.stream()
                // Collect all elements into fullList
                .map(s -> fullList.addAll(s))
                // Non-short-circuiting terminal operation
                .allMatch(p -> p != null);
        System.out.println("--- Full list ---");
        fullList.forEach(System.out::println);
        
        System.out.println("--- Create Full list with flatMap---");
        listOfPets.stream()
                // Turns a List<Pet> to Stream<Pet>
//              .flatMap(s -> s.stream())
//        		.flatMap(Stream::of)
        		.flatMap(Collection::stream)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        System.out.println("--- Putting it all together---");
        listOfPets.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Pet::getType))
                .entrySet()
                .stream()
                .forEach(System.out::println);
    }
}
