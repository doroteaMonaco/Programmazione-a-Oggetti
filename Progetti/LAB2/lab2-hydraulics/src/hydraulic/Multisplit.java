package hydraulic;



public class Multisplit extends Split {

	private double[] proportions;
	
	public Multisplit(String name, int numOutput) {
		super(name, numOutput);
	}
	
	@Override
	public Element[] getOutputs(){
		return super.getOutputs();
	}

	@Override
	public void connect(Element elem, int noutput) {
		outputs[noutput] = elem;
		elem.input = this;
	}
	
	public void setProportions(double... proportions) {
		this.proportions = proportions;
	}

	@Override
	public void simulate(double flow, SimulationObserver observer){
		double[] outflows = new double[outputs.length];
		for(int i = 0; i < outputs.length; i++){
			outflows[i] = flow * proportions[i];
		}
		observer.notifyFlow("Multisplit", getName(), flow, outflows);
		for(int i = 0; i < outputs.length; i++){
			outputs[i].simulate(outflows[i], observer);
		}
	} 
	
}
