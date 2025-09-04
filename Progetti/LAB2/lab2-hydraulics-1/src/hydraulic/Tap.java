package hydraulic;



public class Tap extends Element {

	private String nameTap;
	public Tap(String name) {
		super(name);
		this.nameTap = name;
	}

	public String getTap(){
		return nameTap;
	}

	/**
	 * Set whether the tap is open or not. The status is used during the simulation.
	 *
	 * @param open opening status of the tap
	 */
	public void setOpen(boolean open){
		// TODO: to be implemented
	}
	
}
