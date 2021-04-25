package annotations;

/*
The Learn Programming Academy
Java SE 11 Developer 1Z0-819 OCP Course - Part 2
Section 17 -Annotations
Topic:  Using @SafeArgs
*/

import java.util.Arrays;

// Create a simple generic class
class MyClass<T> {
    T name;

    MyClass(T name) {
        this.name = name;
    }
}

public class SafeArgsExample {
	
//	@SuppressWarnings("unchecked")
    public static void main(String[] args) {

        // Generic Array creation is not allowed
        MyClass[] myArray = {
                new MyClass<>("jane"),
                new MyClass<>("joe")
        };

        // Yet you can do this...
        doSomething(new MyClass<>("jane"), new MyClass<>("joe"));
    }

    // Method with parameterized typed MyClass varargs
//    @SuppressWarnings("unchecked")
//	@SuppressWarnings("varargs")
    @SafeVarargs
    public static void doSomething(MyClass<String>... myStuff) {
        Arrays.stream(myStuff)
                .forEach(System.out::println);
    }
}
