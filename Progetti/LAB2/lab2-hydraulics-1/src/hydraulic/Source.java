package hydraulic;


public class Source extends Element {

	private String nameSource;

	public Source(String name) {
		super(name);
		this.nameSource = name;
	}

	public String getSource(){
		return nameSource;
	}

	/**
	 * Define the flow of the source to be used during the simulation
	 *
	 * @param flow flow of the source (in cubic meters per hour)
	 */
	public void setFlow(double flow){
		// TODO: to be implemented
	}

}
