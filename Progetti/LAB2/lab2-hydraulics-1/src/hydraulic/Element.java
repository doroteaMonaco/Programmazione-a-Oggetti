package hydraulic;



public abstract class Element {
	
	private String name;
	protected Element[] outputs;
	protected Element input;

	public Element(String name){
		this.name = name;
	}

	public Element(String name, int numOutputs){
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
	}
	
	
	public Element getOutput(){
		for(Element e : outputs){
			if(e != null){
				return e;
			}
		}
		return null;
	}
	
	public Element[] getOutputs(){
		return null;
	}
	
	/**
	 * Defines the maximum input flow acceptable for this element
	 * 
	 * @param maxFlow maximum allowed input flow
	 */
	public void setMaxFlow(double maxFlow) {
		// does nothing by default
	}
}
