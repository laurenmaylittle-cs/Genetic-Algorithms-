import java.util.*;

public class nextGen
{
    private static final double mutationRateKS = 0.94;
    //private static final double mutationRateKSTrue = 0.90;
    private static int eliteCountKS = 20;
    private static int countUnder = 0;
    private static double[] testResult ;

    public static PopKnap setNextGen(PopKnap popKS, int popCount) {
       //Set up new population, then overwrite values with elite / crossover / mutation
        PopKnap nextGenPop = new PopKnap(popKS.popSizeKS(), true); 
       
       // ELITE Selection*********************************************** 
       // Store the best "x" items that haven't exceeded the weight
       if (eliteCountKS > 0){
          if (popCount < eliteCountKS) {
              eliteCountKS = popCount;
          }
          PopKnap nextGenPopTop = new PopKnap(popCount, false);
          for (int i = 0; i < popCount; i++) {  
             nextGenPopTop.sampleSolutions[i] = popKS.sampleSolutions[i];
          }
          Arrays.sort(nextGenPopTop.sampleSolutions, 
             Comparator.comparing(sampleSolution::getUtility).reversed()); 
          for (int i = 0; i < eliteCountKS; i++) {  
             nextGenPop.sampleSolutions[i] = nextGenPopTop.sampleSolutions[i];
          }                  
       }
       
       //CROSSOVER
       // Get number of records under weight of 600 (go a bit over, as crossover might bring it back under)
       // Create parents and perform crossover on each of these
       countUnder = Example.checkUnder(nextGenPop, 600);

       for(int i = eliteCountKS; i < countUnder; i++){
          if (countUnder-1 == i) {
              break;
          } 
           Random r1 = new Random();
           int coStart = r1.nextInt(nextGenPop.sampleSolutions[0].getSize());
           boolean coValue;
           for (int c = coStart; c < nextGenPop.sampleSolutions[0].getSize(); c++) {
               coValue = nextGenPop.sampleSolutions[i].solKS[c];
               nextGenPop.sampleSolutions[i].solKS[c] = nextGenPop.sampleSolutions[i+1].solKS[c];
               nextGenPop.sampleSolutions[i+1].solKS[c] = coValue;    
           }
           testResult = Example.getFitness(nextGenPop.sampleSolutions[i].solKS);
           nextGenPop.sampleSolutions[i].utility = testResult[1];
           nextGenPop.sampleSolutions[i].weight = testResult[0];
           i++;
           testResult = Example.getFitness(nextGenPop.sampleSolutions[i].solKS);
           nextGenPop.sampleSolutions[i].utility = testResult[1];
           nextGenPop.sampleSolutions[i].weight = testResult[0];                  
       }                    

       // Mutate every element (except elite?) - but with different rules?
       // 1) Flip items according to mutation rate
       // 2) Cleverer - cycle through, check prob, check bit and do something
       // different - If 0, then have a probability 75%, if 1 always do it
     
       for(int i = eliteCountKS; i < popKS.popSizeKS(); i++){

           for (int m = 1; m < nextGenPop.sampleSolutions[0].getSize(); m++) {
              if (Math.random() > mutationRateKS){ 
                 nextGenPop.sampleSolutions[i].solKS[m] = !nextGenPop.sampleSolutions[i].solKS[m];
              }  
           }
           testResult = Example.getFitness(nextGenPop.sampleSolutions[i].solKS);
           nextGenPop.sampleSolutions[i].utility = testResult[1];
           nextGenPop.sampleSolutions[i].weight = testResult[0];
       } 
       Arrays.sort(nextGenPop.sampleSolutions, Comparator.comparing(sampleSolution::getWeight));        
       return nextGenPop;               
    }
}          
       
