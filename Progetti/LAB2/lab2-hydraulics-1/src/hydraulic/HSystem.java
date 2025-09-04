package hydraulic;

import java.util.Arrays;

public class HSystem {

// R1
	
	public static final int MAX_ELEMENT = 100;

	Element[] elements = new Element[MAX_ELEMENT];
	int nextElement = 0;

	public void addElement(Element elem){
		elements[nextElement] = elem;
		nextElement++;
	}
	
	
	public Element[] getElements(){
		return Arrays.copyOf(elements, MAX_ELEMENT);
	}

// R4
	/**
	 * starts the simulation of the system
	 * 
	 * The notification about the simulations are sent
	 * to an observer object
	 * 
	 * Before starting simulation the parameters of the
	 * elements of the system must be defined
	 * 
	 * @param observer the observer receiving notifications
	 */
	public void simulate(SimulationObserver observer){
		//TODO: to be implemented
	}

// R6
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		//TODO: to be implemented
		return null;
	}

// R7
	/**
	 * Deletes a previously added element 
	 * with the given name from the system
	 */
	public boolean deleteElement(String name) {
		//TODO: to be implemented
		return false;
	}

// R8
	/**
	 * starts the simulation of the system; if {@code enableMaxFlowCheck} is {@code true},
	 * checks also the elements maximum flows against the input flow
	 * 
	 * If {@code enableMaxFlowCheck} is {@code false}  a normals simulation as
	 * the method {@link #simulate(SimulationObserver)} is performed
	 * 
	 * Before performing a checked simulation the max flows of the elements in thes
	 * system must be defined.
	 */
	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {
		//TODO: to be implemented
	}

// R9
	/**
	 * creates a new builder that can be used to create a 
	 * hydraulic system through a fluent API 
	 * 
	 * @return the builder object
	 */
    public static HBuilder build() {
		return new HBuilder();
    }
}
