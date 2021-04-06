package collections;

/*
The Learn Programming Academy
Java SE 11 Developer 1Z0-819 OCP Course - Part 2
Section 4: Generics and Collections
Topic:  Generics & Wildcards
*/

import java.util.ArrayList;
import java.util.List;

public class GenericContainer<T> {

	// Container wraps an ArrayList
	List<? super T> wrappedList;

	// Constructor
	GenericContainer(List<? super T> wrappedList) {
		this.wrappedList = wrappedList;
	}

	// Wrap get method with custom one
	public T myGet(int index) {
		return (T) wrappedList.get(index);
	}

	// Wrap add method with custom one
	public void myAdd(T o) {
		wrappedList.add(o);
	}

	// Wrap ArrayList.toString()
	public String toString() {
		return wrappedList.toString();
	}

	// Main method declares and constructs an instance
	public static void main(String[] args) {
		List<CharSequence> l = new ArrayList<>(List.of(new StringBuilder("bye")));
		GenericContainer<String> g = new GenericContainer<>(l);

		// Add elements to wrapped List
		g.myAdd("hi");

		// Get first element from wrapped List
		String o = g.myGet(0);
	}

}
