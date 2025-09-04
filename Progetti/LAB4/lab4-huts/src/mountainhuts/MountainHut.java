package mountainhuts;

import java.util.Optional;


public class MountainHut {

	private String name, category;
	private int numPostiLetto;
	private Municipality m;
	private Optional<Integer> altitude;

	public MountainHut(String name, String category, int postiLetto, Municipality m, int altitude) {
		this.name = name;
		this.category = category;
		this.numPostiLetto = postiLetto;
		this.m = m;
		this.altitude = Optional.ofNullable(altitude);
	}

	public String getName(){
		return name;
	}

	public String getCategory(){
		return category;
	}

	public int getPostiLetto(){
		return numPostiLetto;
	}

	public Municipality getMunicipality(){
		return m;
	}

	public Optional<Integer> getOptionalAltitude(){
		return altitude;
	}
	
}
