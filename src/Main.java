import action.Action;
import input.ConsumerData;
import input.DistributorData;
import input.Input;
import input.InputLoader;
import output.ParsingFactory;
import output.Writer;

import java.util.List;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {

        ParsingFactory parsing = new ParsingFactory(args[0], args[1]);
        //ParsingFactory parsing = new ParsingFactory("checker/resources/in/complex_5.json", "checker/resources/out/complex_5.json");
        InputLoader inputLoader = (InputLoader) parsing.getInputOutput("INPUT");

        Input input = inputLoader.readInput();
        Action action = new Action(input.getConsumers(),
                input.getDistributors(), input.getProducers(), input.getMonthlyChanges());
        action.executeTurns(input.getNumberOfTurns());

        List<ConsumerData> outputConsumers = action.getOutputConsumers();

        List<DistributorData> outputDistributors = action.getOutputDistributors();

        Writer writer = (Writer) parsing.getInputOutput("OUTPUT");
        writer.writeFile(outputConsumers, outputDistributors, input.getProducers());
    }
}
