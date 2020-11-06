import java.util.*;

public class sampleSolution implements Comparable<sampleSolution>
{
    static int solutionSize = 100;
    
    double utility = 0;
    double weight = 0;    
    boolean[] solKS = new boolean[solutionSize];
    double[] testResult;
    
    public int compareTo(sampleSolution other) {
    if(this.getWeight() > other.getWeight())
        return 1;
    else if (this.getWeight() == other.getWeight())
        return 0 ;
    return -1 ;
    }
    
    public int comparedTo(sampleSolution other) {
    if(this.getUtility() > other.getUtility())
        return 1;
    else if (this.getUtility() == other.getUtility())
        return 0 ;
    return -1 ;
    }
    // Create a random input
    public void generateSolution() {
        //Random r = new Random();
        for(int j = 0; j < solutionSize; j++){
           solKS[j] = (Math.random()>0.90);
        } 
        testResult = Assess.getTest2(solKS);
        utility = testResult[1];
        weight = testResult[0];        
    }    
    
    public double getUtility() {
        return this.utility;
    }
    
    public double getWeight() {
        return this.weight;
    }
    
    public int getSize() {
        return this.solutionSize;
    }
}
