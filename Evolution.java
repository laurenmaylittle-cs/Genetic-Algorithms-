import java.util.*;

public class Evolution {

    private static final double mutationRate = 0.95;
    private static final int eliteCount = 2;
    private static final int crossoverCount = 2;
    private static int i = 0;
    
    // Evolution
    public static Population evolvePopulation(Population pop) {
        //Create a new random population
        Population evolvedPop = new Population(pop.popSize(), true);
        
        // Keep x best inputs
        if (eliteCount > 0) {
           for (i = 0; i < eliteCount; i++) {  
               evolvedPop.inputs[i] = pop.inputs[i];
            }  
        }
        // Crossover on the next best x items
        for (i = eliteCount; i < eliteCount + crossoverCount; i++) { 
           Random r1 = new Random();
           double crossValue = 0;
           int crossStart = r1.nextInt(evolvedPop.inputs[0].getSize());   
           for (int c = crossStart; c < evolvedPop.inputs[0].getSize(); c++) {
              crossValue = evolvedPop.inputs[i].sol[c];
              evolvedPop.inputs[i].sol[c] = evolvedPop.inputs[i].sol[c];
              evolvedPop.inputs[i].sol[c] = crossValue;    
           }  
        }
        
        //Mutate all items except Elite?  or exclude crossover too?
        Random m1 = new Random();
        Random m2 = new Random();
        double origValue = 0;
        double oValue = 0;
        double newFitness = 0;
        double maxValue = 5;
        double minValue = -5;
        
        for (int i = eliteCount + crossoverCount; i < evolvedPop.popSize(); i++) { 
            
           for (int m = 0; m < evolvedPop.inputs[0].getSize(); m++) {             
              if (m1.nextDouble() <= mutationRate){
                 origValue = evolvedPop.inputs[i].fitness;
                 oValue = evolvedPop.inputs[i].sol[m];
                 //Try random number above first
                 evolvedPop.inputs[i].sol[m] = (m2.nextDouble() * (maxValue - oValue)) - oValue;
                 newFitness = Assess.getTest1(evolvedPop.inputs[i].sol);
                 // Fitness was worse, so try random number below
                 if (newFitness > origValue){
                     evolvedPop.inputs[i].sol[m] = (m2.nextDouble() * (oValue - minValue)) + minValue;
                     newFitness = Assess.getTest1(evolvedPop.inputs[i].sol);
                     if (newFitness > origValue) {
                        evolvedPop.inputs[i].sol[m] = oValue;
                        evolvedPop.inputs[i].fitness = origValue;
                     }
                 } 
                 evolvedPop.inputs[i].fitness = newFitness;
              } 
           }
        }
    
        Arrays.sort(evolvedPop.inputs, Comparator.comparing(Input::getFitness));
        return evolvedPop;
    }
}