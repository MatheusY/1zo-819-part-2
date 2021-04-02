package interfaces;

/*
The Learn Programming Academy
Java SE 11 Developer 1Z0-819 OCP Course - Part 2
Section 1: Java Fundamentals 
Topic:  Nested Class, Extras
*/

class SuperClass {
	public void doSomething() {
		System.out.println("SuperClass: doSomething");
	}
}

interface LessSpecificInterface {
	default void doSomething() {
		System.out.println("LessSpecific: doSomething");
	}
}

interface MoreSpecificInterface  {
	default void doSomething() {
		System.out.println("MoreSpecific: doSomething");
	}
}

public class MethodSelection implements LessSpecificInterface, MoreSpecificInterface {
	public static void main(String[] args) {
		MethodSelection methodSelection = new MethodSelection();
		methodSelection.callDoSomething();
	}

	public void callDoSomething() {
		doSomething();
	}

	@Override
	public void doSomething() {
		
	}
}