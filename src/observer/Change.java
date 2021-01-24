package observer;

import input.ProducerData;

import java.util.List;
import java.util.Map;

public interface Change {
  /**
   * Update distributors when producers change
   * @param listOfEnergy map of changes to producers
   * @param producers list of producers
   */
  void update(Map<Integer, Integer> listOfEnergy, List<ProducerData> producers);
}
