package it.unibo.geneticalgs.music;

public interface IGACore {
	void init();
	void start(int targetGens);
	void selection();
	void crossover();
	void mutation();
}
