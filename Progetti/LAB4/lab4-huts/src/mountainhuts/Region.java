package mountainhuts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


public class Region {

	
	private String name;
	public Region(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	List<String> altitudes = new ArrayList<>();
	Map<String, Municipality> municipalities = new TreeMap<>();
	Map<String, MountainHut> mountainHuts = new TreeMap<>();

	public void setAltitudeRanges(String... ranges) {

		for(String s: ranges){
			if(!this.altitudes.contains(s)){
				this.altitudes.add(s);
			}
		}
		
	}

	
	public String getAltitudeRange(Integer altitude) {


		for(String s: this.altitudes){
			String[] parts = s.split("-");
			if(altitude >= Integer.parseInt(parts[0]) && altitude < Integer.parseInt(parts[1])){
				return s;
			}
		}
		return "0-INF";
	}

	
	public Collection<Municipality> getMunicipalities() {

		Collection<Municipality> municipality = new ArrayList<>();

		for(Municipality m : this.municipalities.values()){
			municipality.add(m);
		}
		return municipality;
	}

	
	public Collection<MountainHut> getMountainHuts() {
		Collection<MountainHut> mountains = new ArrayList<>();

		for(MountainHut m : this.mountainHuts.values()){
			mountains.add(m);
		}
		return mountains;
	}


	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {

		Municipality m = new Municipality(name, province, altitude);

		if(!this.municipalities.containsKey(name)){
			this.municipalities.put(name, m);
		}

		return this.municipalities.get(name);
	}

	
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		
		MountainHut m = new MountainHut(name, category, bedsNumber, municipality, 0);

		if(!this.mountainHuts.containsKey(name)){
			this.mountainHuts.put(name, m);
		}

		return this.mountainHuts.get(name);
	}

	
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber, Municipality municipality){
		MountainHut m = new MountainHut(name, category, bedsNumber, municipality, altitude);

		if(!this.mountainHuts.containsKey(name)){
			this.mountainHuts.put(name, m);
		}

		return this.mountainHuts.get(name);
	}

	
	public static Region fromFile(String name, String file) throws Exception {

		Region r = null;
		try{
			if(r.getName().equals(name)){
				for(String line: Region.readData(file)){
					String[] parts = line.trim().split(";");
					String provincia = parts[0];
					String comune = parts[1];
					int altitudeComune = Integer.parseInt(parts[2]);
					String nomeRifugio = parts[3];
					int altitudeRifugio = Integer.parseInt(parts[4]);
					String categoria = parts[5];
					int numeroLetti = Integer.parseInt(parts[6]);

					Municipality mun = r.createOrGetMunicipality(comune, provincia, altitudeComune)
					r.createOrGetMountainHut(name, altitudeRifugio, categoria, numeroLetti, mun);
				}
			}
			
		}
		catch (Exception e){
			throw e;
		}

		return r;
	}

	public static List<String> readData(String file) throws Exception {

		List<String> lines = new ArrayList<>();
		try{
			
			FileReader fr = new FileReader(file);
			BufferedReader bf = new BufferedReader(fr);
			String line;
			line = bf.readLine();
			while((line = bf.readLine()) != null){
				lines.add(line);
				String[] parts = line.trim().split(";");
				String provincia = parts[0];
				String comune = parts[1];
				int altitudeComune = Integer.parseInt(parts[2]);
				String nomeRifugio = parts[3];
				int altitudeRifugio = Integer.parseInt(parts[4]);
				String categoria = parts[5];
				int numeroLetti = Integer.parseInt(parts[6]);

			}
			
		}
		catch (Exception e){
			throw e;
		}
		return lines;
	}


	public Map<String, Long> countMunicipalitiesPerProvince() {
		Map<String, Long> tmp = new TreeMap<>();

		for(Municipality m : this.municipalities.values()){
			tmp.put(m.getProvince(), (long) m.getComune().size());
		}
		return tmp;
	}

	
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {

		Map<String, Map<String, Long>> tmp = new TreeMap<>();

		for(Municipality mun : this.municipalities.values()){
			tmp.put(mun.getProvince(), new TreeMap<>());
			tmp.get(mun.getProvince()).put(mun.getComune(), (long) mun.getRifugi().size());
		}
		return tmp;
	}

	
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		return null;
	}


	public Map<String, Integer> totalBedsNumberPerProvince() {
		return null;

	}

	
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		return null;

	}


	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		return null;

	}

}
