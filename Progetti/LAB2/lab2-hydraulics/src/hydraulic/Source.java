package hydraulic;


public class Source extends Element {

	private double flow;
	public Source(String name) {
		super(name);
	}

	
	public void setFlow(double flow){
		this.flow = flow;
	}

	@Override
	public void simulate(double flow, SimulationObserver observer){
		observer.notifyFlow("Source", getName(), flow, SimulationObserver.NO_FLOW, this.flow);
		getOutput().simulate(this.flow, observer);
	}

	@Override
	public void layout(StringBuffer buffer){
		buffer.append("[" + getName() + "]Source -> ");
		if(getOutput() != null){
			buffer.append("->");
			getOutput().layout(buffer);
		} else{
			buffer.append("*");
		}
	}

}
