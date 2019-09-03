package it.unibo.geneticalgs.music;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.concurrent.ThreadLocalRandom;

public class IndividualMusic implements IIndividual {

	private int fitness;
	private int ID;
	private final int geneLength = 16;
	private int[] genes = new int[geneLength];
	private Hashtable<Integer, String> notesTable;
	private int[] weights;
	
	// Gene values:
	// 0  REST
	// 1  C3
	// 2  D3
	// 3  E3
	// 4  F3
	// 5  G3
	// 6  A3
	// 7  B3
	// 8  C4
	// 9  D4
	// 10 E4
	// 11 F4
	// 12 G4
	// 13 A4
	// 14 B4
	// 15 C5
	
	public IndividualMusic(int ID, int[] weights) {
		this.ID = ID;
		fitness = 0;
		this.weights = weights;
		for (int i = 0; i < getGeneLength(); i++) {
			if (ThreadLocalRandom.current().nextInt(0, 100) <= 35) { // 35% chance of getting a REST note
				setGeneAtIndex(i, 0);
			} else {
				setGeneAtIndex(i, ThreadLocalRandom.current().nextInt(1, 16));
			}
		}
		
		notesTable = new Hashtable<Integer, String>();
		notesTable.put(0, "REST"); notesTable.put(1, "C3"); 
		notesTable.put(2, "D3"); notesTable.put(3, "E3"); notesTable.put(4, "F3"); 
		notesTable.put(5, "G3"); notesTable.put(6, "A3"); notesTable.put(7, "B3"); 
		notesTable.put(8, "C4"); notesTable.put(9, "D4"); notesTable.put(10, "E4"); 
		notesTable.put(11, "F4"); notesTable.put(12, "G4"); notesTable.put(13, "A4"); 
		notesTable.put(14, "B4"); notesTable.put(15, "C5"); 
	}
	
	@Override
	public void fitnessFunction(Object chords) {
		
		this.fitness = 0;
		
		NaiveChordSequence seq = (NaiveChordSequence) chords;
		
		// INTERVAL POINTS (depends on user defined weight)
		if (weights[0] != 2) {
			ArrayList<Integer> templist = new ArrayList<Integer>();
			ArrayList<Integer> difflist = new ArrayList<Integer>();
			ArrayList<Double> normdifflist = new ArrayList<Double>();
			
			// Remove all REST values
			for (int a : genes)
			    templist.add(a);
			templist.removeAll(Collections.singleton(0));
			
			// Create a list of differences
			for (int i = 1; i < templist.size(); i++)
			    difflist.add(Math.abs(getGeneAtIndex(i)-getGeneAtIndex(i-1)));
			
			// Normalize the values to -1 and 1
			int minVal = templist.get(templist.indexOf(Collections.min(templist)));
			int maxVal = templist.get(templist.indexOf(Collections.max(templist)));
			
			for (Integer a : difflist) {
				double value = (a.intValue() - (maxVal+minVal)/2)/((maxVal-minVal)/2);
				normdifflist.add(value);
			}
			
			int raiseAmount = 0;
			if (weights[0] == 1) { // Prefer smaller differences (values closer to -1)
				for (int i = 0; i < normdifflist.size(); i++) {
					raiseAmount -= normdifflist.get(i)*100;
				}
			} else if (weights[0] == 3) { // Prefer bigger differences
				for (int i = 0; i < normdifflist.size(); i++) {
					raiseAmount += normdifflist.get(i)*100;
				}
			}
			raiseFitness(raiseAmount);
		}
		
		
		// NOTES/RESTS RATIO (depends on user defined weight)
		// If the weight is 5, then this is irrelevant
		if (weights[1] != 5) {
			int rests = 0;
			for (int i = 0; i < getGeneLength(); i++) {
				if (getGeneAtIndex(i) == 0)
					rests++;
			}
			
			double ratio = (double) rests/getGeneLength();
			int raiseAmount = 0;
			if (weights[1] < 5) {
				raiseAmount = - (int) ((weights[1]/4.0)*1200*ratio);
			} else {
				raiseAmount = (int) (Math.pow(2, weights[1])*ratio);
			}
			raiseFitness(raiseAmount);
		}
		
		
		// SUCCESSIVE NON HARMONIC TONES
		int nonHarmonicCount = 0;
		
		for (int i = 0; i < getGeneLength(); i++) {
			Chord temp = null;
			if (getGeneAtIndex(i) > 0) {
				temp = seq.getChordGivenPosition(i);
				if (!temp.isComponentInChord(getGeneAtIndex(i))) {
					nonHarmonicCount++;
				} else {
					nonHarmonicCount = 0;
				}
			}
		}
		
		// We penalize the score based on how many successive non-harmonic tones there are 
		raiseFitness(- (int) Math.round((nonHarmonicCount*25)));
		
		// LAST TONE POINTS
		int lastToneIndex = 0;
		for (int i = 0; i < getGeneLength(); i++) {
			if (getGeneAtIndex(i) > 0) {
				lastToneIndex = i;
			}
		}
		Chord lastToneChord = seq.getChordGivenPosition(lastToneIndex);
		
		if (lastToneChord.isComponentRoot(getGeneAtIndex(lastToneIndex))) {
			raiseFitness(100);
		} else if (lastToneChord.isComponentInChord(getGeneAtIndex(lastToneIndex))) {
			raiseFitness(50);
		} else {
			raiseFitness(-50);
		}
		
		for (int i = 0; i < getGeneLength(); i++) {
			
			raiseFitness(10);
		}
		
		
		
	}

	public int getFitness() {
		return this.fitness;
	}

	public void raiseFitness(int by) {
		this.fitness += by;
	}
	
	public int getGeneLength() {
		return this.geneLength;
	}
	
	public int[] getGenes() {
		return this.genes;
	}
	
	public int getGeneAtIndex(int i) {
		if (i < getGeneLength())
			return this.genes[i];
		else
			System.out.println("Error in Individual " + i + ": get genes array out of bounds.");
			return -1;
	}
	
	public void setGeneAtIndex(int i, int value) {
		if (i < getGeneLength())
			this.genes[i] = value;
		else
			System.out.println("Error in Individual " + i + ": set genes array out of bounds.");
			return;
	}

	public int getID() {
		return ID;
	}
	
	@Override
	public String toString() {
		String res = "[ ";
		
		for (int i = 0; i < this.getGeneLength(); i++) {
			res += notesTable.get(getGeneAtIndex(i)) + " ";
		}
		
		res += "]";
		
		return res;
	}

}
