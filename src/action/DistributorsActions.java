package action;

import input.ConsumerData;
import input.DistributorData;
import input.ProducerData;
import observer.EnergyChange;
import output.Constants;
import output.ContractData;
import output.MonthlyStats;
import strategies.ProducerStrategy;
import strategies.StrategyFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                                     final List<ProducerData> producers,
                                     boolean firstTurn) {
    StrategyFactory factory = StrategyFactory.getInstance();
    for (DistributorData distributor : distributors) {
        if (firstTurn) {

          ProducerStrategy strategy = factory.getStrategy(distributor.getProducerStrategy());
          List<ProducerData> sortedProducers = strategy.chooseProducers(producers);

          int i = 0;
          List<Integer> producersOfDistributors = distributor.getProducers();
          int neededEnergy = distributor.getEnergyNeededKW();

          while (neededEnergy > 0) {
            int distributorEnergy = sortedProducers.get(i).getEnergyPerDistributor();

            ProducerData producer = sortedProducers.get(i);

            if (producer.getMaxDistributors() == producer.getDistributors().size()) {
              i++;
              continue;
            }

            producersOfDistributors.add(producer.getId());
            distributor.setProducers(producersOfDistributors);

            List<Integer> distributorsOfProducer = producer.getDistributors();
            distributorsOfProducer.add(distributor.getId());
            producer.setDistributors(distributorsOfProducer);

            neededEnergy -= distributorEnergy;
            i++;
          }
        }
      }
  }

  public static void chooseProducers(final List<DistributorData> distributors,
                                     final List<ProducerData> producers) {
    StrategyFactory factory = StrategyFactory.getInstance();
    for (DistributorData distributor : distributors) {
      if (distributor.isChangeProducer()) {

        List<Integer> producersOfDistributors = distributor.getProducers();
        for(Integer producerOfDistributor : producersOfDistributors) {
          for (ProducerData producer : producers) {
            if (producer.getId() == producerOfDistributor) {
              producer.getDistributors().remove(Integer.valueOf(distributor.getId()));
            }
          }
        }
        producersOfDistributors.clear();

        ProducerStrategy strategy = factory.getStrategy(distributor.getProducerStrategy());
        List<ProducerData> sortedProducers = strategy.chooseProducers(producers);

        int i = 0;
        int neededEnergy = distributor.getEnergyNeededKW();

        while (neededEnergy > 0) {
          int distributorEnergy = sortedProducers.get(i).getEnergyPerDistributor();
          ProducerData producer = sortedProducers.get(i);
          if (producer.getMaxDistributors() == producer.getDistributors().size()) {
            i++;
            continue;
          }
          producersOfDistributors.add(producer.getId());

          List<Integer> distributorsOfProducer = producer.getDistributors();
          distributorsOfProducer.add(distributor.getId());

          neededEnergy -= distributorEnergy;
          i++;
        }

      }
    }
  }

  public static void setProducerMonthlyStat(final List<ProducerData> producers, int month) {
    for (ProducerData producer : producers) {
      List<Integer> distributors = new ArrayList<>(producer.getDistributors());
      List<Integer> sortedDistributors = distributors.stream().sorted().collect(Collectors.toList());
      MonthlyStats monthlyStats = new MonthlyStats(month, sortedDistributors);
      producer.getMonthlyStats().add(monthlyStats);
    }

  }
  public static Map<Integer, Integer> setListOfChanges(final List<ProducerData> producers) {
    Map<Integer, Integer> listOfChanges = new LinkedHashMap<>();
    for (ProducerData producer : producers) {
      listOfChanges.put(producer.getId(), producer.getEnergyPerDistributor());
    }
    return listOfChanges;
  }
  public static void updateProducers(final List<DistributorData> distributors,
                                             final List<ProducerData> producers,
                                     Map<Integer, Integer> listOfChanges) {
    EnergyChange energyChange = new EnergyChange();
    for (DistributorData distributor : distributors) {
      energyChange.addObserver(distributor);
    }
    energyChange.set(listOfChanges, producers);
  }

  public static void calculateProductionCost(final List<DistributorData> distributors,
                                             final List<ProducerData> producers) {
    for (DistributorData distributor : distributors) {
      int cost = 0;
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
