package input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import output.MonthlyStats;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
  "id",
  "maxDistributors",
  "priceKW",
  "energyType",
  "energyPerDistributor",
  "monthlyStats"
})
public final class ProducerData implements Entity {
  private int id;
  private String energyType;
  private int maxDistributors;
  private double priceKW;
  private int energyPerDistributor;

  @JsonIgnore private List<Integer> distributors;
  private List<MonthlyStats> monthlyStats;

  public ProducerData(
      int id, String energyType, int maxDistributors, double priceKW, int energyPerDistributor) {
    this.id = id;
    this.energyType = energyType;
    this.maxDistributors = maxDistributors;
    this.priceKW = priceKW;
    this.energyPerDistributor = energyPerDistributor;
    distributors = new ArrayList<>();
    monthlyStats = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEnergyType() {
    return energyType;
  }

  public void setEnergyType(String energyType) {
    this.energyType = energyType;
  }

  public int getMaxDistributors() {
    return maxDistributors;
  }

  public void setMaxDistributors(int maxDistributors) {
    this.maxDistributors = maxDistributors;
  }

  public double getPriceKW() {
    return priceKW;
  }

  public void setPriceKW(double priceKW) {
    this.priceKW = priceKW;
  }

  public int getEnergyPerDistributor() {
    return energyPerDistributor;
  }

  public void setEnergyPerDistributor(int energyPerDistributor) {
    this.energyPerDistributor = energyPerDistributor;
  }

  public List<MonthlyStats> getMonthlyStats() {
    return monthlyStats;
  }

  public void setMonthlyStats(List<MonthlyStats> monthlyStats) {
    this.monthlyStats = monthlyStats;
  }

  public List<Integer> getDistributors() {
    return distributors;
  }

  public void setDistributors(List<Integer> distributors) {
    this.distributors = distributors;
  }

  @Override
  public String toString() {
    return "ProducerData{"
        + "id="
        + id
        + ", energyType='"
        + energyType
        + '\''
        + ", maxDistributors="
        + maxDistributors
        + ", priceKW="
        + priceKW
        + ", energyPerDistributor="
        + energyPerDistributor
        + ", distributors="
        + distributors
        + ", monthlyStats="
        + monthlyStats
        + '}';
  }
}
