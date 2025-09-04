package mountainhuts;

import java.util.ArrayList;
import java.util.List;

public class Municipality {

	private String name;
	private String province;
	private Integer altitude;
	private List<String> comuniInProvincia;
	private List<String> rifugiInComune;

	public Municipality(String name, String province, Integer altitude) {
		this.name = name;
		this.province = province;
		this.altitude = altitude;
		this.comuniInProvincia = new ArrayList<>();
		this.rifugiInComune = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public String getProvince() {
		return province;
	}

	public Integer getAltitude() {
		return altitude;
	}

	public void addComune(String comune){
		if(!this.comuniInProvincia.contains(comune)){
			this.comuniInProvincia.add(comune);
		}
	}

	public List<String> getComune(){
		return this.comuniInProvincia;
	}

	public void addRifugio(MountainHut m){
		if(!this.rifugiInComune.contains(m.getName())){
			this.rifugiInComune.add(m.getName());
		}
	}

	public List<String> getRifugi(){
		return this.rifugiInComune;
	}

}
