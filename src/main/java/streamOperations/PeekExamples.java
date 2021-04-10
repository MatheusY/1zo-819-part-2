package streamOperations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import streamOperations.element.Student;

public class PeekExamples {

    public static void main(String[] args) {
        PeekExamples p = new PeekExamples();

        // Create a studentList of 5 students
        List<Student> firstList = Stream.generate(Student::new)
                .limit(5).collect(Collectors.toList());

        System.out.println("--------- Before Peek ---------");
        firstList.forEach(System.out::println);
        System.out.println("---- Peeking during stream operations ----");
        // Create a second studentList using first and looking
        // at elements on the Stream using peek
        List<Student> secondList = firstList.stream()
                .peek(System.out::print)
                .collect(Collectors.toList());

        System.out.println("\n--------- After Peek ---------");
        firstList.forEach(System.out::println);

        System.out.println("Lists contain same elements? " +
                firstList.equals(secondList));
        
        List<Student> thirdList =
                firstList.stream()
                        // What happens if we call a method with side-effect?
                        .peek(PeekExamples::changeStudent)
                        // Terminal operation returns a List
                        .collect(Collectors.toList());

        System.out.println("firstList = " + firstList);
        System.out.println("thirdList = " + thirdList);

    }
    
    // A method that has side-effects, name of student changes
    private static void changeStudent(Student p) {
        p.setName(p.getName() + " Doe");
    }

}
