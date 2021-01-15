package updates;

import input.ConsumerData;
import updates.DistributorChanges;
import updates.ProducerChanges;

import java.util.List;

public final class UpdateData {
  private List<ConsumerData> newConsumers;
  private List<DistributorChanges> updatedDistributors;
  private List<ProducerChanges> updatedProducers;

  public UpdateData(List<ConsumerData> newConsumers, List<DistributorChanges> updatedDistributors, List<ProducerChanges> updatedProducers) {
    this.newConsumers = newConsumers;
    this.updatedDistributors = updatedDistributors;
    this.updatedProducers = updatedProducers;
  }

  public List<ConsumerData> getNewConsumers() {
    return newConsumers;
  }

  public void setNewConsumers(final List<ConsumerData> newConsumers) {
    this.newConsumers = newConsumers;
  }

  public List<DistributorChanges> getUpdatedDistributors() {
    return updatedDistributors;
  }

  public void setUpdatedDistributors(final List<DistributorChanges> updatedDistributors) {
    this.updatedDistributors = updatedDistributors;
  }

  public List<ProducerChanges> getUpdatedProducers() {
    return updatedProducers;
  }

  public void setUpdatedProducers(List<ProducerChanges> updatedProducers) {
    this.updatedProducers = updatedProducers;
  }

  @Override
  public String toString() {
    return "UpdateData{" +
            "newConsumers=" + newConsumers +
            ", updatedDistributors=" + updatedDistributors +
            ", updatedProducers=" + updatedProducers +
            '}';
  }
}
