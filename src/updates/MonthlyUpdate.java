package updates;

import input.ConsumerData;
import input.DistributorData;
import input.ProducerData;

import java.util.List;

public final class MonthlyUpdate {
  private MonthlyUpdate() {

  }

  /**
   * Adds the new consumers to the game
   *
   * @param consumers initial consumers
   * @param newConsumers consumer that need to be added
   */
  public static void addNewConsumers(
      final List<ConsumerData> consumers, final List<ConsumerData> newConsumers) {
    consumers.addAll(newConsumers);
  }

  /**
   * Updates the distributors costs
   *
   * @param distributors initial distributors
   * @param changes the changes to the distributors costs
   */
  public static void changeCostDistributor(
      final List<DistributorData> distributors, final List<DistributorChanges> changes) {
    for (DistributorChanges change : changes) {
      for (DistributorData distributor : distributors) {
        if (change.getId() == distributor.getId()) {
          distributor.setInfrastructureCost(change.getInfrastructureCost());
        }
      }
    }
  }

  public static void changeEnergyProducer(
      final List<ProducerData> producers, final List<ProducerChanges> changes) {
    for (ProducerChanges change : changes) {
      for (ProducerData producer : producers) {
        if (change.getId() == producer.getId()) {
          producer.setEnergyPerDistributor(change.getEnergyPerDistributor());
        }
      }
    }
  }
}
