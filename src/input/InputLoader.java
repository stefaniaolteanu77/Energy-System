package input;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import output.Constants;
import output.Parsing;
import updates.DistributorChanges;
import updates.ProducerChanges;
import updates.UpdateData;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class InputLoader implements Parsing {
  private final String inputPath;

  public InputLoader(final String inputPath) {
    this.inputPath = inputPath;
  }

  /**
   * Reads from input file
   *
   * @return an input which contains the number of turns, the consumers, the distributors and list
   *     of monthly updates
   */
  public Input readInput() {
    JSONParser jsonParser = new JSONParser();
    int numberOfTurns = 0;
    EntityFactory entityFactory = EntityFactory.getInstance();
    @SuppressWarnings("unchecked")
    List<ConsumerData> consumers = (List<ConsumerData>) entityFactory.getEntity("CONSUMERS");
    @SuppressWarnings("unchecked")
    List<DistributorData> distributors =
        (List<DistributorData>) entityFactory.getEntity("DISTRIBUTORS");
    List<ProducerData> producers = (List<ProducerData>) entityFactory.getEntity("PRODUCERS");
    List<UpdateData> monthlyChanges = new ArrayList<>();

    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(inputPath));
      int jsonNumberOfTurns = ((Long) (jsonObject).get(Constants.NUMBER_OF_TURNS)).intValue();
      JSONObject initialData = (JSONObject) jsonObject.get(Constants.INITIAL_DATA);
      JSONArray jsonConsumers = (JSONArray) initialData.get(Constants.CONSUMERS);
      JSONArray jsonDistributors = (JSONArray) initialData.get(Constants.DISTRIBUTORS);
      JSONArray jsonProducers = (JSONArray) initialData.get(Constants.PRODUCERS);
      JSONArray jsonUpdates = (JSONArray) jsonObject.get(Constants.MONTHLY_UPDATES);

      numberOfTurns = jsonNumberOfTurns;

      if (jsonConsumers != null) {
        for (Object jsonConsumer : jsonConsumers) {
          consumers.add(
              new ConsumerData(
                  ((Long) ((JSONObject) jsonConsumer).get(Constants.ID)).intValue(),
                  ((Long) ((JSONObject) jsonConsumer).get(Constants.INITIAL_BUDGET)).intValue(),
                  ((Long) ((JSONObject) jsonConsumer).get(Constants.MONTHLY_INCOME)).intValue()));
        }
      } else {
        System.out.println("THERE ARE NO CONSUMERS");
      }

      if (jsonDistributors != null) {
        for (Object jsonDistributor : jsonDistributors) {
          distributors.add(
              new DistributorData(
                  ((Long) ((JSONObject) jsonDistributor).get(Constants.ID)).intValue(),
                  ((Long) ((JSONObject) jsonDistributor).get(Constants.CONTRACT_LENGTH)).intValue(),
                  ((Long) ((JSONObject) jsonDistributor).get(Constants.INITIAL_BUDGET)).intValue(),
                  ((Long) ((JSONObject) jsonDistributor).get(Constants.INITIAL_INF_COST))
                      .intValue(),
                  ((Long) ((JSONObject) jsonDistributor).get(Constants.ENERGY_NEEDED)).intValue(),
                  (String) ((JSONObject) jsonDistributor).get(Constants.PRODUCER_STRATEGY)));
        }
      } else {
        System.out.println("THERE ARE NO DISTRIBUTORS");
      }

      if (jsonProducers != null) {
        for (Object jsonProducer : jsonProducers) {
          producers.add(
              new ProducerData(
                  ((Long) ((JSONObject) jsonProducer).get(Constants.ID)).intValue(),
                  (String) ((JSONObject) jsonProducer).get(Constants.ENERGY_TYPE),
                  ((Long) ((JSONObject) jsonProducer).get(Constants.MAX_DIST)).intValue(),
                  ((Number) ((JSONObject) jsonProducer).get(Constants.PRICE_KW)).doubleValue(),
                  ((Long) ((JSONObject) jsonProducer).get(Constants.ENERGY_PER_DIST)).intValue()));
        }
      } else {
        System.out.println("THERE ARE NO PRODUCERS");
      }

      if (jsonUpdates != null) {
        for (Object jsonUpdate : jsonUpdates) {
          @SuppressWarnings("unchecked")
          List<ConsumerData> newConsumers =
              (List<ConsumerData>) entityFactory.getEntity("CONSUMERS");
          List<DistributorChanges> updatedDistributors = new ArrayList<>();
          List<ProducerChanges> updatedProducers = new ArrayList<>();
          JSONArray jsonNewConsumers =
              (JSONArray) ((JSONObject) jsonUpdate).get(Constants.NEW_CONSUMERS);
          JSONArray jsonUpdatedDistributors =
              (JSONArray) ((JSONObject) jsonUpdate).get(Constants.DIST_CHANGES);
          JSONArray jsonUpdatedProducers =
              (JSONArray) ((JSONObject) jsonUpdate).get(Constants.PROD_CHANGES);
          if (jsonNewConsumers != null) {
            for (Object jsonNewConsumer : jsonNewConsumers) {
              newConsumers.add(
                  new ConsumerData(
                      ((Long) ((JSONObject) jsonNewConsumer).get(Constants.ID)).intValue(),
                      ((Long) ((JSONObject) jsonNewConsumer).get(Constants.INITIAL_BUDGET))
                          .intValue(),
                      ((Long) ((JSONObject) jsonNewConsumer).get(Constants.MONTHLY_INCOME))
                          .intValue()));
            }
          }
          if (jsonUpdatedDistributors != null) {
            for (Object jsonUpdatedDistributor : jsonUpdatedDistributors) {
              updatedDistributors.add(
                  new DistributorChanges(
                      ((Long) ((JSONObject) jsonUpdatedDistributor).get(Constants.ID)).intValue(),
                      ((Long) ((JSONObject) jsonUpdatedDistributor).get(Constants.INF_COST))
                          .intValue()));
            }
          }
          if (jsonUpdatedProducers != null) {
            for (Object jsonUpdatedProducer : jsonUpdatedProducers) {
              updatedProducers.add(
                  new ProducerChanges(
                      ((Long) ((JSONObject) jsonUpdatedProducer).get(Constants.ID)).intValue(),
                      ((Long) ((JSONObject) jsonUpdatedProducer).get(Constants.ENERGY_PER_DIST))
                          .intValue()));
            }
          }
          monthlyChanges.add(new UpdateData(newConsumers, updatedDistributors, updatedProducers));
        }
      }

    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }

    return new Input(numberOfTurns, consumers, distributors, producers, monthlyChanges);
  }
}
