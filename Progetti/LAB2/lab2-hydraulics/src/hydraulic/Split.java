package hydraulic;

import java.util.Arrays;

public class Split extends Element {

	

	public Split(String name) {
		super(name, 2);
	}

	public Split(String name, int numOutput){
		super(name, numOutput);
	}
	
	public Element[] getOutputs(){
		return Arrays.copyOf(outputs, 2);
	}

	public void connect(Element elem, int noutput){
		outputs[noutput] = elem;
		elem.input = this;
	}

	@Override
	public void simulate(double flow, SimulationObserver observer){
		double outflow = flow/2;
		observer.notifyFlow("Split", getName(), flow, outflow, outflow);
		outputs[0].simulate(outflow, observer);
		outputs[1].simulate(outflow, observer);
	} 

	@Override
	public void layout(StringBuffer buffer){
		buffer.append("[" + getName() + "]" + this.getClass().getSimpleName() + " ");
		int bufferLength = buffer.length();

		for(int i = 0; i < outputs.length; i++){
			if(outputs[i] != null){
				if(i > 0){
					buffer.append("\n");
					buffer.append(" ".repeat(bufferLength));
					buffer.append("|\n");
					buffer.append(" ".repeat(bufferLength));
				}
			}
			if(e != null){
				buffer.append("+->");
				e.layout(buffer);
			}
		}
	}

	
}
