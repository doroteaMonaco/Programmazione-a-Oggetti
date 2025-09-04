package hydraulic;


public class Sink extends Element {

	private String nameSink;

	public Sink(String name) {
		super(name);
		this.nameSink = name;
	}

	public String getSink(){
		return nameSink;
	}
}
