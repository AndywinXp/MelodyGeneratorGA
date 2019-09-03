package it.unibo.geneticalgs.music;

public class NaiveChordSequence {
	// Class for a basic 4 chords sequence
	
	private Chord[] sequence;
	
	public NaiveChordSequence(Chord[] sequence) {
		this.sequence = sequence;
	}
	
	public Chord getChordGivenPosition(int eightnote) {
		Chord temp = null;
		if (eightnote < 0 || eightnote > 15) {
			System.out.println("Error getChordGivenPosition: bad argument " + eightnote);
		} else {
			temp = sequence[eightnote/4];
		}
		return temp;
	}
	
	@Override
	public String toString() {
		String res = "[ ";
		for(Chord c : sequence) {
			res += c.getChordName() + " ";
		}
		res += "]";
		return res;
	}
}
