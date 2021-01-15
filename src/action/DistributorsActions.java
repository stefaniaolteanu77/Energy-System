package action;

import input.ConsumerData;
import input.DistributorData;
import input.ProducerData;
import output.Constants;
import output.ContractData;
import strategies.ProducerStrategy;
import strategies.StrategyFactory;

import java.util.ArrayList;
import java.util.List;

public final class DistributorsActions {
  private DistributorsActions() {

  }

  /**
   * Calculate profit for all distributors
   * @param distributors
   */
  public static void calculateProfit(final List<DistributorData> distributors) {
    for (DistributorData distributor : distributors) {
      distributor.setProfit((int) Math.round(Math.floor(Constants.PROFIT_CONSTANT
              * distributor.getProductionCost())));
    }
  }

  /**
   * Calculate the price of the contract for all distributors
   * @param distributors
   */
  public static void calculateContractPrice(final List<DistributorData> distributors) {
    for (DistributorData distributor : distributors) {
      // the distributor has no contracts
      if (distributor.getContracts().size() == 0) {
        distributor.setContractPrice(
            distributor.getInfrastructureCost()
                + distributor.getProductionCost()
                + distributor.getProfit());
        // the distributor has contracts
      } else {
        float infCost =
            (float) distributor.getInfrastructureCost() / distributor.getContracts().size();
        distributor.setContractPrice(
            (int)
                Math.round(
                    Math.floor(infCost)
                        + distributor.getProductionCost()
                        + distributor.getProfit()));
      }
    }
  }

  /**
   * Calculate the expenses for all distributors
   * @param distributors
   */
  public static void calculateExpenses(final List<DistributorData> distributors) {
    for (DistributorData distributor : distributors) {
      distributor.setExpensesPrice(
          distributor.getInfrastructureCost()
              + distributor.getProductionCost() * distributor.getContracts().size());
    }
  }

  /**
   * Calculate the budget for all distributors
   * @param distributors
   * @param consumers
   */
  public static void calculateBudget(final List<DistributorData> distributors,
                                     final List<ConsumerData> consumers) {
    for (DistributorData distributor : distributors) {
      // the distributor has no contracts
      if (distributor.getContracts().size() == 0) {
        distributor.setBudget(distributor.getBudget() - distributor.getExpensesPrice());
      } else {
        int monthlyRate = 0;
        for (ContractData contract : distributor.getContracts()) {
          for (ConsumerData consumer : consumers) {
            if (contract.getId() == consumer.getId()) {
              if (!consumer.owesDistributor() && !consumer.isBankrupt()) {
                monthlyRate += contract.getPrice();
              }
            }
          }
        }
        distributor.setBudget(
            distributor.getBudget() - distributor.getExpensesPrice() + monthlyRate);
      }
      // the budget is negative so the distributor is bankrupt
      if (distributor.getBudget() < 0) {
        distributor.setBankrupt(true);
      }
    }
  }

  /**
   * If a consumer finished his contract remove his contract and
   * remove the consumer from the contract list of his distributor
   * @param distributors
   * @param consumers
   */
  public static void removeFinishedContracts(final List<DistributorData> distributors,
                                             final List<ConsumerData> consumers) {
    for (DistributorData distributor : distributors) {
      ArrayList<ContractData> contractsToRemove = new ArrayList<>();
      for (ContractData contract : distributor.getContracts()) {
        if (contract.getRemainedContractMonths() == 0) {
          contractsToRemove.add(contract);
          for (ConsumerData consumer : consumers) {
            if (consumer.getId() == contract.getId()) {
              consumer.setContract(null);
            }
          }
        }
      }
      distributor.getContracts().removeAll(contractsToRemove);
    }
  }

  /**
   * Remove all the contracts of bankrupt consumers from a distributor's
   * contract list
   * @param distributors
   * @param consumers
   */
  public static void removeBankruptContracts(final List<DistributorData> distributors,
                                             final List<ConsumerData> consumers) {
    for (DistributorData distributor : distributors) {
      ArrayList<ContractData> contractsToRemove = new ArrayList<>();
      for (ContractData contract : distributor.getContracts()) {
        for (ConsumerData consumer : consumers) {
          if (consumer.getId() == contract.getId() && consumer.isBankrupt()) {
            contractsToRemove.add(contract);
          }
        }
      }
      distributor.getContracts().removeAll(contractsToRemove);
    }
  }

  /**
   * Remove all bankrupt distributors
   * @param distributors
   * @param distributorsToRemove
   */
  public static void removeBankruptDistributors(final List<DistributorData> distributors,
                                                final List<DistributorData> distributorsToRemove) {
    for (DistributorData distributor : distributors) {
      if (distributor.isBankrupt()) {
        distributorsToRemove.add(distributor);
      }
    }
    distributors.removeAll(distributorsToRemove);
  }

  /**
   * For all bankrupt distributors remove all their contracts
   * @param distributors
   * @param consumers
   */
  public static void removeContractsOfBankruptDistributors(final List<DistributorData> distributors,
                                                           final List<ConsumerData> consumers) {
    for (DistributorData distributor : distributors) {
      if (!distributor.getContracts().isEmpty()) {
        for (ContractData contractData : distributor.getContracts()) {
          for (ConsumerData consumer : consumers) {
            if (consumer.getId() == contractData.getId()) {
              consumer.setContract(null);
            }
          }
        }
        distributor.getContracts().clear();
      }
    }
  }
  public static void chooseProducers(final List<DistributorData> distributors,
                                     final List<ProducerData> producers) {
    StrategyFactory factory = StrategyFactory.getInstance();
    for (DistributorData distributor : distributors) {
      ProducerStrategy strategy = factory.getStrategy(distributor.getProducerStrategy());
      List<ProducerData> sortedProducers = strategy.chooseProducers(producers);

//      int i = 0;
//      List <Integer> prod = distributor.getProducers();
//      int neededEnergy = distributor.getEnergyNeededKW();
//      while (neededEnergy > 0) {
//        int distributorEnergy = sortedProducers.get(i).getEnergyPerDistributor();
//        int distributorId = sortedProducers.get(i).getId();
//        prod.add(distributorId);
//        distributor.setProducers(prod);
//        neededEnergy -= distributorEnergy;
     // }
    }
  }

  public static void calculateProductionCost(final List<DistributorData> distributors,
                                             final List<ProducerData> producers) {
    int cost = 0;
    for (DistributorData distributor : distributors) {
      for (Integer contractWithProducer : distributor.getProducers()) {
        for (ProducerData producer : producers) {
          if (contractWithProducer == producer.getId()) {
            cost += producer.getPriceKW() * producer.getEnergyPerDistributor();
          }
        }
      }
      distributor.setProductionCost((int) Math.round(Math.floor(cost / 10)));
    }
  }
}
