package input;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import observer.Change;
import output.ContractData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder({
  "id",
  "energyNeededKW",
  "contractPrice",
  "budget",
  "producerStrategy",
  "isBankrupt",
  "contracts"
})
public final class DistributorData implements Entity, Change {
  private int id;
  @JsonIgnore private int contractLength;
  private int budget;
  @JsonIgnore private int infrastructureCost;
  private int energyNeededKW;
  private String producerStrategy;

  private boolean isBankrupt;
  @JsonIgnore private boolean changeProducer;
  @JsonIgnore private int productionCost;
  @JsonIgnore private int profit;
  private int contractPrice;
  @JsonIgnore private int expensesPrice;
  private List<ContractData> contracts;
  @JsonIgnore private List<Integer> producers;

  public DistributorData(
      int id,
      int contractLength,
      int budget,
      int infrastructureCost,
      int energyNeededKW,
      String producerStrategy) {
    this.id = id;
    this.contractLength = contractLength;
    this.budget = budget;
    this.infrastructureCost = infrastructureCost;
    this.energyNeededKW = energyNeededKW;
    this.producerStrategy = producerStrategy;
    contracts = new ArrayList<>();
    producers = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  @JsonGetter("isBankrupt")
  public boolean isBankrupt() {
    return isBankrupt;
  }

  public void setBankrupt(final boolean bankrupt) {
    isBankrupt = bankrupt;
  }

  public int getContractLength() {
    return contractLength;
  }

  public void setContractLength(final int contractLength) {
    this.contractLength = contractLength;
  }

  public int getBudget() {
    return budget;
  }

  public void setBudget(final int budget) {
    this.budget = budget;
  }

  public int getInfrastructureCost() {
    return infrastructureCost;
  }

  public void setInfrastructureCost(final int infrastructureCost) {
    this.infrastructureCost = infrastructureCost;
  }

  public int getEnergyNeededKW() {
    return energyNeededKW;
  }

  public void setEnergyNeededKW(int energyNeededKW) {
    this.energyNeededKW = energyNeededKW;
  }

  public String getProducerStrategy() {
    return producerStrategy;
  }

  public void setProducerStrategy(String producerStrategy) {
    this.producerStrategy = producerStrategy;
  }

  public int getProductionCost() {
    return productionCost;
  }

  public void setProductionCost(int productionCost) {
    this.productionCost = productionCost;
  }

  public List<ContractData> getContracts() {
    return contracts;
  }

  public void setContracts(final List<ContractData> contracts) {
    this.contracts = contracts;
  }

  @JsonProperty("contractCost")
  public int getContractPrice() {
    return contractPrice;
  }

  public void setContractPrice(final int contractPrice) {
    this.contractPrice = contractPrice;
  }

  public int getProfit() {
    return profit;
  }

  public void setProfit(final int profit) {
    this.profit = profit;
  }

  public int getExpensesPrice() {
    return expensesPrice;
  }

  public void setExpensesPrice(final int expensesPrice) {
    this.expensesPrice = expensesPrice;
  }

  public boolean isChangeProducer() {
    return changeProducer;
  }

  public void setChangeProducer(boolean changeProducer) {
    this.changeProducer = changeProducer;
  }

  public List<Integer> getProducers() {
    return producers;
  }

  public void setProducers(List<Integer> producers) {
    this.producers = producers;
  }

  @Override
  public String toString() {
    return "DistributorData{"
        + "id="
        + id
        + ", contractLength="
        + contractLength
        + ", budget="
        + budget
        + ", infrastructureCost="
        + infrastructureCost
        + ", energyNeededKW="
        + energyNeededKW
        + ", producerStrategy='"
        + producerStrategy
        + '\''
        + ", isBankrupt="
        + isBankrupt
        + ", changeProducer="
        + changeProducer
        + ", productionCost="
        + productionCost
        + ", profit="
        + profit
        + ", contractPrice="
        + contractPrice
        + ", expensesPrice="
        + expensesPrice
        + ", contracts="
        + contracts
        + ", producers="
        + producers
        + '}';
  }

  @Override
  public void update(Map<Integer, Integer> listOfEnergy, List<ProducerData> inputProducers) {
    for (Map.Entry<Integer, Integer> entry : listOfEnergy.entrySet()) {
      for (Integer producerId : producers) {
        if (entry.getKey() == producerId) {
          for (ProducerData producer : inputProducers) {
            if (producerId == producer.getId()
                && entry.getValue() != producer.getEnergyPerDistributor()) {
              changeProducer = true;
              break;
            }
          }
        }
      }
    }
  }
}
