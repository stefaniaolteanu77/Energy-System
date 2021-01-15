package input;

import updates.UpdateData;

import java.util.List;

public final class Input {
  private final int numberOfTurns;
  private final List<ConsumerData> consumers;
  private final List<DistributorData> distributors;
  private final List<ProducerData> producers;
  private final List<UpdateData> monthlyChanges;

  public Input(int numberOfTurns, List<ConsumerData> consumers,
               List<DistributorData> distributors,
               List<ProducerData> producers,
               List<UpdateData> monthlyChanges) {
    this.numberOfTurns = numberOfTurns;
    this.consumers = consumers;
    this.distributors = distributors;
    this.producers = producers;
    this.monthlyChanges = monthlyChanges;
  }

  public List<ConsumerData> getConsumers() {
    return consumers;
  }

  public List<DistributorData> getDistributors() {
    return distributors;
  }

  public int getNumberOfTurns() {
    return numberOfTurns;
  }

  public List<ProducerData> getProducers() {
    return producers;
  }

  public List<UpdateData> getMonthlyChanges() {
    return monthlyChanges;
  }

  @Override
  public String toString() {
    return "Input{" +
            "numberOfTurns=" + numberOfTurns +
            ", consumers=" + consumers +
            ", distributors=" + distributors +
            ", producers=" + producers +
            ", monthlyChanges=" + monthlyChanges +
            '}';
  }
}
