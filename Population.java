import java.util.*;

public class Population
{
    Input[] inputs;

    // Create a population
    public Population(int populationSize, boolean initialise) {
        inputs = new Input[populationSize];
        // Initialise population
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < inputs.length; i++) {
                Input newInput = new Input();
                newInput.generateInput();
                inputs[i] = newInput;
            }
            Arrays.sort(inputs, Comparator.comparing(Input::getFitness));
            int x = 0;
        }
    }  
   
    // Get population size
    public int popSize() {
        return inputs.length;
    }
}
