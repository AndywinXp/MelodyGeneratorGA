package it.unibo.geneticalgs.music;

public interface IPopulation {
	public void init();
	public void computeFitness(Object additionalData);
	public void updatePopulation();
	public IIndividual getFirstParent();
	public IIndividual getSecondParent();
	public IIndividual getLeastFit();
}
