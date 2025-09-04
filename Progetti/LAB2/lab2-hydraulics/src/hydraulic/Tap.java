package hydraulic;



public class Tap extends Element {

	private boolean open;
	
	public Tap(String name) {
		super(name);
	}

	
	public void setOpen(boolean open){
		this.open = open;
	}

	@Override
	public void simulate(double flow, SimulationObserver observer){
		if(open){
			observer.notifyFlow("Tap", getName(), flow, flow);
			getOutput().simulate(flow, observer);
		} else{
			observer.notifyFlow("Tap", getName(), flow, 0.0);
			getOutput().simulate(0.0, observer);
		}
	}

	@Override
	public void layout(StringBuffer buffer){
		buffer.append("[" + getName() + "]Tap -> ");
		if(getOutput() != null){
			buffer.append("->");
			getOutput().layout(buffer);
		} else{
			buffer.append("*");
		}
	}
	
}
