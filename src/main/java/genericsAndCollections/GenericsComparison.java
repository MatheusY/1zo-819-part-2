package genericsAndCollections;

/*
The Learn Programming Academy
Java SE 11 Developer 1Z0-819 OCP Course - Part 2
Section 4: Generics and Collections
Topic:  Generic Methods
*/

import java.util.ArrayList;
import java.util.List;

// A generic class with no generic methods
class GenericsClass<T> {

    // instance field set to type of type parameter of the class
    T aGenericField;

    // constructor takes argument (typed to type parameter of the class)
    GenericsClass(T aGenericField) {
        this.aGenericField = aGenericField;
        System.out.println("GenericsClass constructor: " +
                aGenericField.getClass().getName());
    }

    // method returns result typed to type parameter of the class
    public T aGenericClassMethod() {
        return this.aGenericField;
    }
}

// a non-generic class with generic methods
class NonGenericClass {

    // constructor declared with type parameter T.
    // Method's single argument restricted to method type parameter
    <T> NonGenericClass(T aGenericParameter) {
        System.out.println("NonGenericClass: generic constructor: " +
                aGenericParameter.getClass().getName());
    }

    // static method with type parameter
    // Method's single argument restricted to method type parameter
    public static <T> void genericStaticMethod(T aGenericParameter) {
        System.out.println("NonGenericClass: static generic method: " +
                aGenericParameter.getClass().getName());
    }

    // instance method with type parameter
    // Method's single argument restricted to method type parameter
    public <T> void genericInstanceMethod(T aGenericParameter) {
        System.out.println("NonGenericClass: instance generic method: " +
                aGenericParameter.getClass().getName());
    }
    
//    static T aStaticField;
//
//    public static T aGenericClassStaticMethod() {
//        return aStaticField;
//    }
}

public class GenericsComparison {
    public static void main(String[] args) {

        // Create instances of generic class using type arguments
        GenericsClass<Number> a1 =
                new GenericsClass<>(Double.valueOf("10"));
        GenericsClass<ArrayList<String>> a2 =
                new GenericsClass<>(new ArrayList(List.of("A", "B", "C")));

        // Create instances of non-generic class with a generic
        // constructor same as any other non-generic - compiler uses type
        // inference
        NonGenericClass b1 = new NonGenericClass(Double.valueOf("10"));

        //  Alternately, you can specify the type for clarity if desired
        NonGenericClass b2 = new <ArrayList<String>>NonGenericClass(
                new ArrayList(List.of("A", "B", "C")));

        // Invoking static generic method specifying type (optional)
        NonGenericClass.<Exception>genericStaticMethod(
                new RuntimeException("Just Testing"));

        // Invoking instance generic method
        b1.genericInstanceMethod("Hello");    
        
        String s1 = "Anne";
        String s2 = "Bob";

        // Create an Instance of CombinedClass using String and Byte
        CombinedClass<String, Byte> c = new CombinedClass<>(
                Byte.valueOf("1"), 2);

        // Using the generic method on Combined Class with types
        // String and List<String>.  Types are inferred.
        String compareString = c.getComparisonResult(s1, s2,
                List.of("COMES AFTER", "EQUALS", "IS LISTED BEFORE"));

        System.out.println(s1 + " " + compareString + " " + s2);
        
     // Create an Instance of CombinedClass using Integer and String
        CombinedClass<Integer, String> c2 = new CombinedClass<>(
                "A combined Class", Byte.valueOf("5"));
        Integer i1 = 1;
        Integer i2 = 2;
        String compareString2 = c2.getComparisonResult(i1, i2,
                List.of("IS GREATER THAN", "IS EQUAL", "IS LESS THAN"));

        System.out.println(i1 + " " + compareString2 + " " + i2);
    }
}

//Generic class with two type parameters
class CombinedClass<T extends Comparable, U> {

 // Create some instance variables
 U instanceField;
 Number instanceNumberField;

 // Constructor is a generic method.  In this case the method's parameter
 // type is upper bound to Number
 <V extends Number> CombinedClass(U instanceField, V instanceNumber) {
     this.instanceField = instanceField;
     this.instanceNumberField = instanceNumber;
     System.out.println("CombinedClass: instanceField (" +
             instanceField.getClass().getName() + ") : " + instanceField);
     System.out.println("CombinedClass: instanceNumber (" +
             instanceNumber.getClass().getName() + ") : " + instanceNumber);
 }

 // generic method declares two type parameters:
 //  -- X has no restriction but it's type is the return type of this method
 //  -- W is a List made up of X elements.
 // The method has three arguments:
 //  -- t and s which are of type T (class type parameter)
 //  -- definitions argument is a List defined by the W method type parameter.
 public <X, W extends List<X>> X getComparisonResult(
         T t, T s, W definitions) {
     int result = s.compareTo(t);
     if (result != 0) result = result < 0 ? -1 : 1;
     result++;

     return definitions.get(result);
 }
}
