package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	
	public Sink(String name) {
		super(name);
	}

	@Override
	public void connect(Element elem){
		
	}

	@Override
	public void simulate(double flow, SimulationObserver observer){
		observer.notifyFlow("Sink", getName(), flow, flow, SimulationObserver.NO_FLOW);
		
	} 

	@Override
	public void layout(StringBuffer buffer){
		buffer.append("[" + getName() + "]Sink");
	}
}
