package output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "price", "remainedContractMonths"})
public final class ContractData {
  private int id;
  private int price;
  private int remainedContractMonths;

  public ContractData(int id, int price, int remainedContractMonths) {
    this.id = id;
    this.price = price;
    this.remainedContractMonths = remainedContractMonths;
  }

  @JsonProperty("consumerId")
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getRemainedContractMonths() {
    return remainedContractMonths;
  }

  public void setRemainedContractMonths(int remainedContractMonths) {
    this.remainedContractMonths = remainedContractMonths;
  }

  @Override
  public String toString() {
    return "ContractData{" +
            "id=" + id +
            ", price=" + price +
            ", remainedContractMonths=" + remainedContractMonths +
            '}';
  }
}
