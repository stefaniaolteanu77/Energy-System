package observer;

import input.ProducerData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class EnergyChange {
  private final List<Change> changes = new ArrayList<>();

  /**
   * adds observer
   * @param change
   */
  public void addObserver(final Change change) {
    this.changes.add(change);
  }

  /**
   * removes observer
   * @param change
   */
  public void removeObserver(final Change change) {
    this.changes.remove(change);
  }

  /**
   * uses update
   * @param listOfEnergy
   * @param producers list of producers
   */
  public void set(final Map<Integer, Integer> listOfEnergy, List<ProducerData> producers) {
    for (Change change : changes) {
      change.update(listOfEnergy, producers);
    }
  }
}
