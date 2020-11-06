import java.lang.Math;
import java.util.*;

class Example
{
    public static void main(String[] args){
        //Start time in milliseconds
       long startT=System.currentTimeMillis();

       String name="Lauren Little";
       String login = "lml22";

       // For assessment 1
       int evolutionCount = 1;
       int evolutionLimit = 500000;
       double bestFitness = 999;
       int solSize = 20;
       final int popSize = 10;
       
       //For assessment 2
       long iterEndT = 0;
       long iterStartT = 0;
       double seconds = 0;
       final int iterations = 500000;
       final int popKnapsackSize = 100;
       int iterCount = 1; 
       int bestUtilityUnder500kgIndex = 0;
       int countUnder = 0;
       double bestUtilityUnder500kg = 0;
       
       double[] sol1 = new double[solSize];
       
       //Assessment 1
       //Initialise population
       startT=System.currentTimeMillis();
       Population popDials = new Population(popSize, true);

       while (evolutionCount <= evolutionLimit && seconds <= 29) {
          if (popDials.inputs[0].fitness < bestFitness){
             bestFitness = popDials.inputs[0].fitness;
             sol1 = popDials.inputs[0].sol;
          }
          popDials = Evolution.evolvePopulation(popDials);
          evolutionCount ++;
          iterEndT= System.currentTimeMillis();
          seconds = (iterEndT - startT) / 1000.0;
       }
       double fit = Assess.getTest1(sol1);
       System.out.println("The fitness of your example Solution is: "+ fit);

       
       System.out.println(" ");
       System.out.println(" ");
       System.out.println("Now let us turn to the second problem:");
       System.out.println("A sample solution in this case is a boolean array of size 100.");
       System.out.println("I now create a random sample solution and get the weight and utility:");

       //Assessment 2
        //creating an initial population 
        seconds = 0;
        iterStartT=System.currentTimeMillis();
        PopKnap popKnapsack = new PopKnap(popKnapsackSize, true);
        boolean[] sol2 = new boolean[100];

        bestUtilityUnder500kgIndex = checkBest(popKnapsack,bestUtilityUnder500kg);
        bestUtilityUnder500kg = popKnapsack.sampleSolutions[bestUtilityUnder500kgIndex].utility;
        countUnder = checkUnder(popKnapsack, 500);
                    
        while( iterCount < iterations && seconds <= 29){ 
            //create next population (with selection / crossover / mutation)          
            popKnapsack = nextGen.setNextGen(popKnapsack, countUnder); 
            bestUtilityUnder500kgIndex = checkBest(popKnapsack,bestUtilityUnder500kg);
            sol2 = popKnapsack.sampleSolutions[bestUtilityUnder500kgIndex].solKS;
            countUnder = checkUnder(popKnapsack, 500);
 
            iterCount +=1;
            
            //set time for time limit
            iterEndT= System.currentTimeMillis();
            seconds = (iterEndT - iterStartT) / 1000.0;
        } 
        double[] tmp = (Assess.getTest2(sol2));

        //The index 0 of tmp gives the weight. Index 1 gives the utility
        System.out.println("The weight is: " + tmp[0]);
        System.out.println("The utility is: " + tmp[1]);

        Assess.checkIn(name,login,sol1,sol2);

       long endT=System.currentTimeMillis();
       System.out.println("Total execution time was: " +  ((endT - startT)/1000.0) + " seconds");
    }
    
    public static int checkBest(PopKnap popKnapsack, double bestUtilityUnder500kg){
        int Index = 0;
        Arrays.sort(popKnapsack.sampleSolutions, Comparator.comparing(sampleSolution::getWeight));
        for(int i = 0; i < popKnapsack.popSizeKS(); i++){ 
           if (popKnapsack.sampleSolutions[i].weight > 500){
               //break;
           }           
           if ((bestUtilityUnder500kg < popKnapsack.sampleSolutions[i].utility) 
                   && popKnapsack.sampleSolutions[i].weight <= 500){
              bestUtilityUnder500kg = popKnapsack.sampleSolutions[i].utility;
              Index = i;
           }
        }   
        return Index;
    }  
    
    public static int checkUnder(PopKnap popKnapsack, double limit){
        int underCount = 0;
        for(int i = 0; i < popKnapsack.popSizeKS(); i++){
           if (popKnapsack.sampleSolutions[i].weight < limit){
              underCount += 1;
           }
           else{
              break;
           }
        }
        return underCount;
    } 
    
    public static double[] getFitness(boolean[] solution2){
       return(Assess.getTest2(solution2));
    }
}      
    

