package hydraulic;

import java.util.Arrays;

public abstract class Element {
	
	private String name;
	protected Element[] outputs;
	protected Element input;

	public Element(String name){
		this(name, 1);
	}

	public Element(String name,int numOutputs){
		this.name = name;
		outputs = new Element[numOutputs];
	}

	public String getName() {
		return name;
	}
	
	
	public void connect(Element elem) {
		outputs[0] = elem;
		elem.input = this;
	}
	
	
	public void connect(Element elem, int index){
		// does nothing by default
	}
	
	
	public Element getOutput(){
		for(Element e : outputs){
			if(e != null){
				return e;
			}
		}
		return null;
	}

	protected abstract void simulate(double flow, SimulationObserver observer);
	protected abstract void layout(StringBuffer buffer);
	
	public boolean isDeletable(){
		if(outputs.length == 1){
			return true;
		}
		int countOutputs = 0;
		for(Element e : outputs){
			if(e != null){
				countOutputs++;
			}
		}
		if(countOutputs <= 1){
			return true;
		}

		return false;
	}

	public Element[] getOutputs(){
		return Arrays.copyOf(outputs, outputs.length);
	}
	
	
	public void setMaxFlow(double maxFlow) {
		// does nothing by default
	}
}
