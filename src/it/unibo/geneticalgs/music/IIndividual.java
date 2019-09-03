package it.unibo.geneticalgs.music;

public interface IIndividual {
	public void fitnessFunction(Object additionalData);
	public int getID();
	public Object getGenes();
}
