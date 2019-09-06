package it.unibo.geneticalgs.music;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GACoreMusic implements IGACore {
	
	Random rn;
	PopulationMusic pop;
	int genCount;
	IndividualMusic parent1;
	IndividualMusic parent2;
	NaiveChordSequence seq;
	final int popLength = 100;
	boolean flagFitness;
	
	public GACoreMusic(NaiveChordSequence seq, int[] weights) {
		genCount = 0;
		this.seq = seq;
		pop = new PopulationMusic(popLength, weights);	
	}
	
	@Override
	public void init() {
		pop.init();
		pop.computeFitness(seq);
		rn = new Random();
		flagFitness = false;

		System.out.println("Generation: " + 
		+ genCount + " Fittest ID: " + ((IndividualMusic) pop.getFirstParent()).getID() + 
		 " with genes " + ((IndividualMusic) pop.getFirstParent()).toString() +
		 " and fitness " + ((IndividualMusic) pop.getFirstParent()).getFitness() + 
		 " over chords " + seq.toString());
	}
	
	@Override
	public void selection() {
		parent1 = (IndividualMusic) pop.getFirstParent();
		parent2 = (IndividualMusic) pop.getSecondParent();
	}

	@Override
	public void crossover() {
		int crossoverPoint = rn.nextInt(parent1.getGeneLength());
		
		for (int i = 0; i < crossoverPoint; i++) {
			int temp = parent1.getGeneAtIndex(i);
			parent1.setGeneAtIndex(i, parent2.getGeneAtIndex(i));
			parent2.setGeneAtIndex(i, temp);
		}
	}

	@Override
	public void mutation() {        
        // 5% chance of mutation
        if (rn.nextInt(1000) <= 50) {
        	int mutationPointA = rn.nextInt(pop.individuals[0].getGeneLength());
        	int mutationPointB = rn.nextInt(pop.individuals[0].getGeneLength());
        	
        	 // Randomly change values at the mutation point
        	for (int i = Math.min(mutationPointA, mutationPointB); 
        			i <= Math.max(mutationPointA, mutationPointB); i++) {
        		parent1.setGeneAtIndex(i, ThreadLocalRandom.current().nextInt(0, 16));
        	}

        	mutationPointA = rn.nextInt(pop.individuals[0].getGeneLength());
        	mutationPointB = rn.nextInt(pop.individuals[0].getGeneLength());
        	
        	for (int i = Math.min(mutationPointA, mutationPointB); 
        			i <= Math.max(mutationPointA, mutationPointB); i++) {
        		parent2.setGeneAtIndex(i, ThreadLocalRandom.current().nextInt(0, 16));
        	}
        }
	}

	@Override
	public void start(int targetGens, int desiredMinFitness) {
		boolean canStop = false;
		while (!canStop) {
			genCount++;
			selection();
			crossover();
			mutation();
			
			// Replacing individuals with fittest ones
			
			pop.computeFitness(seq);
			
		/*	System.out.println("Generation: " + 
					+ genCount + " Fittest ID: " + ((IndividualMusic) pop.getFirstParent()).getID() + 
					 " with genes " + java.util.Arrays.toString(((IndividualMusic) pop.getFirstParent()).getGenes()) +
					 " and fitness " + ((IndividualMusic) pop.getFirstParent()).getFitness());*/
			
			System.out.println("Generation: " + 
					+ genCount + " Fittest ID: " + ((IndividualMusic) pop.getFirstParent()).getID() + 
					 " with genes " + ((IndividualMusic) pop.getFirstParent()).toString() +
					 " and fitness " + ((IndividualMusic) pop.getFirstParent()).getFitness() + 
					 " over chords " + seq.toString());		
			
			
			
			canStop = isGoalReached(targetGens, desiredMinFitness);
		}
		
	}
	
	boolean isGoalReached(int targetGens, int desiredMinFitness) {
		if (getFittestOffspring().getFitness() >= desiredMinFitness && genCount >= targetGens)
			return true;	
		return false;
	}
	
    IndividualMusic getFittestOffspring() {
        if (parent1.getFitness() > parent2.getFitness()) {
            return parent1;
        }
        return parent2;
    }


    void addFittestOffspring() {

        parent1.fitnessFunction(seq);
        parent2.fitnessFunction(seq);

        pop.individuals[pop.leastFitIndex] = getFittestOffspring();
    }

}
