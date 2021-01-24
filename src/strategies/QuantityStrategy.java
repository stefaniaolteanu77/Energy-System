package strategies;

import input.ProducerData;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class QuantityStrategy implements ProducerStrategy {
  @Override
  public List<ProducerData> chooseProducers(List<ProducerData> producers) {
    return producers.stream()
        .sorted(
            Comparator.comparing(ProducerData::getEnergyPerDistributor)
                .reversed()
                .thenComparing(ProducerData::getId))
        .collect(Collectors.toList());
  }
}
