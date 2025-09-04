package hydraulic;

import java.util.Arrays;

public class HSystem {

// R1
	private static final int MAX_SIZE = 100;
	protected Element[] elements = new Element[100];
	private int nextElement = 0;

	public void addElement(Element elem){
		elements[nextElement++] = elem;
	}
	
	
	public Element[] getElements(){
		return Arrays.copyOf(elements, nextElement);
	}

// R4
	
	public void simulate(SimulationObserver observer){
		for(Element e : elements){
			if(e instanceof Source){
				e.simulate(SimulationObserver.NO_FLOW, observer);
			}
		}
	}

}
