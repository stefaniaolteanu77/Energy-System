package action;

import input.ConsumerData;
import input.DistributorData;
import output.Constants;
import output.ContractData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class ConsumersActions {
  private ConsumersActions() {

  }

  /**
   * When the month passes updates the contracts of the distributors and consumers
   *
   * @param consumers
   * @param distributors
   */
  public static void monthPass(
      final List<ConsumerData> consumers, final List<DistributorData> distributors) {
    for (ConsumerData consumer : consumers) {
      if (consumer.getContract() != null) {
        // the contract is finished
        if (consumer.getContract().getRemainedContractMonths() == 0) {
          consumer.setContract(null);
        } else {
          // update contract for consumer
          ContractData contract = consumer.getContract();
          contract.setRemainedContractMonths(
              consumer.getContract().getRemainedContractMonths() - 1);
          consumer.setContract(contract);
          // update list of contracts of distributors
          for (DistributorData distributor : distributors) {
            if (contract.getId() == distributor.getId()) {
              for (ContractData contractData : distributor.getContracts()) {
                if (contractData.getId() == consumer.getId()) {
                  contractData.setRemainedContractMonths(contract.getRemainedContractMonths());
                }
              }
            }
          }
        }
      }
    }
  }

  /**
   * The consumers choose the contract of the distributors with lowest price
   *
   * @param consumers
   * @param distributors
   */
  public static void chooseDistributor(
      final List<ConsumerData> consumers, final List<DistributorData> distributors) {
    for (ConsumerData consumer : consumers) {
      if (consumer.getContract() == null) {
        // get the distributor with the lowest contract
        DistributorData distributor =
            Collections.min(distributors, Comparator.comparing(DistributorData::getContractPrice));
        // add the consumer to the contract list of the distributor
        ContractData distributorContract =
            new ContractData(
                consumer.getId(), distributor.getContractPrice(), distributor.getContractLength());
        distributor.getContracts().add(distributorContract);
        // create the contract for the consumer
        ContractData contract =
            new ContractData(
                distributor.getId(),
                distributor.getContractPrice(),
                distributor.getContractLength());
        consumer.setContract(contract);
      }
    }
  }

  /**
   * Calculate budget for all consumers
   *
   * @param consumers
   */
  public static void calculateBudget(final List<ConsumerData> consumers) {
    for (ConsumerData consumer : consumers) {
      if (consumer.getContract() != null) {
        if (consumer.owesDistributor()) {
          int owedValue =
              (int) Math.round(Math.floor(Constants.OWED_VALUE_CONSTANT * consumer.getOwedPrice()))
                  + consumer.getContract().getPrice();
          // the consumer owes the distributor a second time so he is bankrupt
          if (consumer.getBudget() + consumer.getMonthlyIncome() < owedValue) {
            consumer.setBudget(consumer.getBudget() + consumer.getMonthlyIncome());
            consumer.setBankrupt(true);
            // the consumer pays his owed value
          } else {
            consumer.setBudget(consumer.getBudget() + consumer.getMonthlyIncome() - owedValue);
            consumer.setOwesDistributor(false);
          }
          // the consumer can't pay this month
        } else if (consumer.getBudget() + consumer.getMonthlyIncome()
            < consumer.getContract().getPrice()) {
          consumer.setBudget(consumer.getBudget() + consumer.getMonthlyIncome());
          consumer.setOwesDistributor(true);
          consumer.setOwedPrice(consumer.getContract().getPrice());
          // the consumer is able to pay this month
        } else {
          consumer.setBudget(
              consumer.getBudget()
                  + consumer.getMonthlyIncome()
                  - consumer.getContract().getPrice());
        }
      }
    }
  }

  /**
   * Remove all bankrupt consumers and add them to a list of bankrupts to preserve their information
   *
   * @param consumers
   * @param consumersToRemove
   */
  public static void removeBankruptConsumers(
      final List<ConsumerData> consumers, final List<ConsumerData> consumersToRemove) {
    for (ConsumerData consumer : consumers) {
      if (consumer.isBankrupt()) {
        consumersToRemove.add(consumer);
      }
    }
    consumers.removeAll(consumersToRemove);
  }
}
