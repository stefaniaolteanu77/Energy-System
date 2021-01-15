package input;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import output.ContractData;

@JsonPropertyOrder({"id", "isBankrupt", "budget"})
public final class ConsumerData implements Entity {
  private int id;
  private int budget;
  @JsonIgnore private int monthlyIncome;

  private boolean isBankrupt;
  @JsonIgnore private boolean owesDistributor;
  @JsonIgnore private int owedPrice;
  @JsonIgnore private ContractData contract;

  public ConsumerData(final int id, final int budget,
                      final int monthlyIncome) {
    this.id = id;
    this.budget = budget;
    this.monthlyIncome = monthlyIncome;
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

  public int getBudget() {
    return budget;
  }

  public void setBudget(final int budget) {
    this.budget = budget;
  }

  public int getMonthlyIncome() {
    return monthlyIncome;
  }

  public void setMonthlyIncome(final int monthlyIncome) {
    this.monthlyIncome = monthlyIncome;
  }

  public ContractData getContract() {
    return contract;
  }

  public void setContract(ContractData contract) {
    this.contract = contract;
  }

  /**
   *
   * @return whether the consumer owes the distributor or not
   */
  public boolean owesDistributor() {
    return owesDistributor;
  }

  public void setOwesDistributor(final boolean owesDistributor) {
    this.owesDistributor = owesDistributor;
  }

  public int getOwedPrice() {
    return owedPrice;
  }

  public void setOwedPrice(final int owedPrice) {
    this.owedPrice = owedPrice;
  }

  @Override
  public String toString() {
    return "ConsumerData{"
        + "id="
        + id
        + ", budget="
        + budget
        + ", monthlyIncome="
        + monthlyIncome
        + ", isBankrupt="
        + isBankrupt
        + ", owesDistributor="
        + owesDistributor
        + ", contract="
        + contract
        + '}';
  }
}
