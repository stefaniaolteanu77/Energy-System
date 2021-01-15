package action;

import input.ConsumerData;
import input.DistributorData;
import input.ProducerData;
import strategies.ProducerStrategy;
import strategies.StrategyFactory;
import updates.UpdateData;
import updates.MonthlyUpdate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class Action {
  private final List<ConsumerData> consumers;
  private final List<DistributorData> distributors;
  private final List<ProducerData> producers;
  private final List<UpdateData> updates;
  private final List<ConsumerData> bankruptConsumers;
  private final List<DistributorData> bankruptDistributors;

  public Action(final List<ConsumerData> consumers,
                final List<DistributorData> distributors,
                final List<ProducerData> producers, final List<UpdateData> updates) {
    this.consumers = consumers;
    this.distributors = distributors;
    this.producers = producers;
    this.updates = updates;
    bankruptConsumers = new ArrayList<>();
    bankruptDistributors = new ArrayList<>();
  }

  /**
   * This is the method that calls all the actions the distributors and consumers
   * do each turn in the right order
   * @param numberOfTurns the game's number of turns
   */
  public void executeTurns(final int numberOfTurns) {
    for (int i = 0; i <= numberOfTurns; i++) {
      // for the rounds of the game from 1 to numberOfTurns which have updates
      if (i != 0) {
        MonthlyUpdate.addNewConsumers(consumers, updates.get(i - 1).getNewConsumers());
        MonthlyUpdate.changeCostDistributor(
            distributors, updates.get(i - 1).getUpdatedDistributors());
      }

      DistributorsActions.chooseProducers(distributors, producers);

      DistributorsActions.calculateProductionCost(distributors, producers);

      DistributorsActions.calculateProfit(distributors);

      DistributorsActions.calculateContractPrice(distributors);

      DistributorsActions.removeFinishedContracts(distributors, consumers);

      ConsumersActions.chooseDistributor(consumers, distributors);

      ConsumersActions.calculateBudget(consumers);

      ConsumersActions.monthPass(consumers, distributors);

      DistributorsActions.calculateExpenses(distributors);

      DistributorsActions.calculateBudget(distributors, consumers);

      DistributorsActions.removeBankruptContracts(distributors, consumers);

      DistributorsActions.removeBankruptDistributors(distributors, bankruptDistributors);

      DistributorsActions.removeContractsOfBankruptDistributors(bankruptDistributors, consumers);

      ConsumersActions.removeBankruptConsumers(consumers, bankruptConsumers);


    }
  }

  /**
   * Combines the consumers still in the game and the bankrupt ones and sorts them by Id
   * @return list of Consumers needed at output
   */
  public List<ConsumerData> getOutputConsumers() {
    List<ConsumerData> outputConsumers = new ArrayList<>();
    outputConsumers.addAll(bankruptConsumers);
    outputConsumers.addAll(consumers);
    outputConsumers =
        outputConsumers.stream()
            .sorted(Comparator.comparingInt(ConsumerData::getId))
            .collect(Collectors.toList());
    return outputConsumers;
  }

  /**
   * Combines the distributors still in the game and the bankrupt ones and sorts them by Id
   * @return list of Distributors needed at output
   */
  public List<DistributorData> getOutputDistributors() {
    List<DistributorData> outputDistributors = new ArrayList<>();
    outputDistributors.addAll(bankruptDistributors);
    outputDistributors.addAll(distributors);
    outputDistributors =
        outputDistributors.stream()
            .sorted(Comparator.comparingInt(DistributorData::getId))
            .collect(Collectors.toList());
    return outputDistributors;
  }
}
