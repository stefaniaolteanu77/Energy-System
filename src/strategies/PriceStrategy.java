package strategies;

import input.ProducerData;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class PriceStrategy implements ProducerStrategy {
  @Override
  public List<ProducerData> chooseProducers(List<ProducerData> producers) {
    return producers.stream()
        .sorted(
            Comparator.comparingDouble(ProducerData::getPriceKW)
                .thenComparing(ProducerData::getEnergyPerDistributor, Comparator.reverseOrder())
                .thenComparing(ProducerData::getId))
        .collect(Collectors.toList());
  }
}
