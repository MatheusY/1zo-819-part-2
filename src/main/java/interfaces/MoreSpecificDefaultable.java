package interfaces;

/*
The Learn Programming Academy
Java SE 11 Developer 1Z0-819 OCP Course - Part 2
Section 3: Interfaces
Topic:  Default methods
*/

// Interface extends Defaultable, and overrides the default method
public interface MoreSpecificDefaultable extends Defaultable {
	void defaultNotAbstractMethod();
}

// Class implements both interfaces
class MoreSpecific extends DefaultClass implements
Defaultable, MoreSpecificDefaultable {

	// implements the abstractMethod and calls the default method
	public void abstractMethod() {
		defaultNotAbstractMethod(); // Which default method is used?
	}

	// invokes the abstract method
	public static void main(String[] args) {
		new MoreSpecific().abstractMethod();
	}
	
	public void defaultNotAbstractMethod() {
	    new Defaultable() {
	        public void abstractMethod() {
	        }
	    }.defaultNotAbstractMethod();
	}
}
