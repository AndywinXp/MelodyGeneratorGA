package it.unibo.geneticalgs.music;

public class PopulationMusic implements IPopulation {
	int popSize;
	int firstParentIndex;
	int secondParentIndex;
	int leastFitIndex;
	IndividualMusic[] individuals;
	
	public PopulationMusic(int popSize, int[] weights) {
		this.popSize = popSize;
		individuals = new IndividualMusic[this.popSize];
		
		for (int i = 0; i < this.popSize; i++) {
			individuals[i] = new IndividualMusic(i, weights);
		}
		
		firstParentIndex = -1;
		secondParentIndex = -1;
		leastFitIndex = -1;
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void computeFitness(Object chords) {
		for (int i = 0; i < this.popSize; i++) {
			individuals[i].fitnessFunction(chords);
		}
		
		updatePopulation();
	}

	@Override
	public void updatePopulation() {
		// Calculate first parent index
		int mostFitValue = Integer.MIN_VALUE;
		for (int i = 0; i < this.popSize; i++) {
			if (mostFitValue <= individuals[i].getFitness()) {
				mostFitValue = individuals[i].getFitness();
				this.firstParentIndex = i;
			}
		}
		
		// Calculate first and second parent indices
		int mostFitIndex = 0;
		int secondMostFitIndex = 0;
		
		for (int i = 0; i < this.popSize; i++) {
			if (individuals[i].getFitness() > individuals[mostFitIndex].getFitness()) {
				secondMostFitIndex = mostFitIndex;
				mostFitIndex = i;
			} else if (individuals[i].getFitness() > individuals[secondMostFitIndex].getFitness()) {
				secondMostFitIndex = i;
			}
		}
		
		//this.firstParentIndex = mostFitIndex;
		this.secondParentIndex = secondMostFitIndex;
		
		// Calculate least fit index
		int leastFitValue = Integer.MAX_VALUE;
		for (int i = 0; i < this.popSize; i++) {
			if (leastFitValue >= individuals[i].getFitness()) {
				leastFitValue = individuals[i].getFitness();
				this.leastFitIndex = i;
			}
		}
		
	}

	@Override
	public IIndividual getFirstParent() {
		return this.individuals[this.firstParentIndex];
	}

	@Override
	public IIndividual getSecondParent() {
		// TODO Auto-generated method stub
		return this.individuals[this.secondParentIndex];
	}

	@Override
	public IIndividual getLeastFit() {
		// TODO Auto-generated method stub
		return this.individuals[this.leastFitIndex];
	}

}
