package it.unibo.geneticalgs.music;
import java.util.ArrayList;

public class Chord {
	private String name;
	private ArrayList<Integer> components = new ArrayList<Integer>();

	public Chord(String name, ArrayList<Integer> components) {
		this.name = name;
		this.components = components;
	}
	
	public String getChordName() {
		return name;
	}

	public ArrayList<Integer> getComponents() {
		return this.components;
	}
	
	public boolean isComponentInChord(int component) {
		if (component < 1 || component > 15) {
			System.out.println("isComponentInChord error: " + component + " is an invalid value.");
		} else {
			int normalizedComponent = (component > 7) ? component%7 : component;
			normalizedComponent += (normalizedComponent == 0) ? 7 : 0;
			
			return components.contains(normalizedComponent);
		}
		
		return false;
	}
	
	public boolean isComponentRoot(int component) {
		if (component < 1 || component > 15) {
			System.out.println("isComponentInChord error: " + component + " is an invalid value.");
		} else {
			int normalizedComponent = (component > 7) ? component%7 : component;
			normalizedComponent += (normalizedComponent == 0) ? 7 : 0;
			
			return normalizedComponent == components.get(0).intValue();
		}
		
		return false;
	}
	
}
