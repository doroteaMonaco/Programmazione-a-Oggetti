package hydraulic;

public class HSystemExt extends HSystem{

    public String layout(){
        StringBuffer buffer = new StringBuffer;
		for(Element e : elements){
			if(e instanceof Source){
				e.layout(buffer);
			}
		}
		return null;
	}

// R7
	public boolean deleteElement(String name) {
        Element toDelete;
		for(Element e : elements){
            if(e != null){
                if(e.getName().equals(name)){
                    toDelete = e;
                    break;
                }
            }
        }
        if(toDelete == null){
            return false;
        }
        if(!toDelete.isDeletable()){
            return false;
        }
        if(toDelete.input != null){
            toDelete.input.connect(toDelete.getOutput());
        }
        Element[] newElements = new Element[elements.length]
        int i = 0;
        for(Element e : elements){
            if(e != null){
                if(e != toDelete){
                    newElements[i++] = e;
                }else{
                    break;
                }
            }
        }
        elements = newElements;
        return true;
	}

// R8
	
	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {
		//TODO: to be implemented
	}

// R9
	
    public static HBuilder build() {
		return new HBuilder();
    }
    
}
