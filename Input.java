import java.util.*;

public class Input implements Comparable<Input>{
    double fitness = 0;
    double[] sol = new double[inputSize];
    
    static int inputSize = 20;

    public int compareTo(Input other) {
        if(this.getFitness() > other.getFitness())
            return 1;
        else if (this.getFitness() == other.getFitness())
            return 0 ;
        return -1 ;
    }
    
    // Create a random input
    public void generateInput() {
        Random r = new Random();
        for(int j=0; j<inputSize;j++){
           sol[j] = (r.nextDouble() *(10)) - 5;
        } 
        fitness = Assess.getTest1(sol);
    }
    
    public double getFitness() {
        return this.fitness;
    }
    
    public int getSize() {
        return this.inputSize;
    }
}
