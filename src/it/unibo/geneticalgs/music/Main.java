package it.unibo.geneticalgs.music;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		int targetGens = 500;
		
		// Chords (C major scale, three voices harmony)
		ArrayList<Integer> c = new ArrayList<Integer>();
		c.add(1); c.add(3); c.add(5);
		
		ArrayList<Integer> dm = new ArrayList<Integer>();
		dm.add(2); dm.add(4); dm.add(6);
		
		ArrayList<Integer> em = new ArrayList<Integer>();
		em.add(3); em.add(5); em.add(7);
		
		ArrayList<Integer> f = new ArrayList<Integer>();
		f.add(4); f.add(6); f.add(1);
		
		ArrayList<Integer> g = new ArrayList<Integer>();
		g.add(5); g.add(7); g.add(2);
		
		ArrayList<Integer> am = new ArrayList<Integer>();
		am.add(6); am.add(1); am.add(3);
		
		ArrayList<Integer> bdim = new ArrayList<Integer>();
		bdim.add(7); bdim.add(2); bdim.add(4);
		
		Chord C = new Chord("C", c);
		Chord Dm = new Chord("Dm", dm);
		Chord Em = new Chord("Em", em);
		Chord F = new Chord("F", f);
		Chord G = new Chord("G", g);
		Chord Am = new Chord("Am", am);
		Chord Bdim = new Chord("Bdim", bdim);
		
		// Choose chords
		Chord[] arrayseq = {Dm, F, Am, G};
		NaiveChordSequence seq = new NaiveChordSequence(arrayseq);
		
		// Weights parsing
		int interv_w = 2;
		int rest_w = 5;
		if (args.length > 0) {
			interv_w = isInt(args[0]) ? getInt(args[0]) : 2;
			rest_w = isInt(args[1]) ? getInt(args[1]) : 5;
		}
				
		// Input weights: [ 1,2,3 = small\irrelevant\big intervals, 1...9 = less...more rests]
		int[] weights = {interv_w, rest_w};
		
		// Initialize and start the program
		IGACore core = new GACoreMusic(seq, weights);
		
		core.init();
		core.start(targetGens);
	}
	
	public static int getInt(String string) {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException nFE) {
			return 0;
		}

	}

	public static boolean isInt(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException nFE) {
			return false;
		}
		return true;
	}

}
