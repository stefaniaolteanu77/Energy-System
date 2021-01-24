package strategies;

import input.ProducerData;

import java.util.List;

public interface ProducerStrategy {
  /**
   * chooses producer
   * @param producers list of producers
   * @return
   */
  List<ProducerData> chooseProducers(List<ProducerData> producers);
}
