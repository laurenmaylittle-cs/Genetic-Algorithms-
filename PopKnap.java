import java.util.*;

public class PopKnap
{
    sampleSolution[] sampleSolutions;
    
    public PopKnap(int popKnapSize, boolean init)
    {
        sampleSolutions = new sampleSolution[popKnapSize];
        
        if (init){
            for(int i=0;i< sampleSolutions.length; i++){                   
               sampleSolution newSampleSolution = new sampleSolution();
               newSampleSolution.generateSolution();
               sampleSolutions[i] = newSampleSolution;
            }
            Arrays.sort(sampleSolutions, Comparator.comparing(sampleSolution::getUtility));
        }
    }

    // Get population size
    public int popSizeKS() {
        return sampleSolutions.length;
    }
}