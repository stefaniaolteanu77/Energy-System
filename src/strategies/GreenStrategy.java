package strategies;

import entities.EnergyType;
import input.ProducerData;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class GreenStrategy implements ProducerStrategy {

  @Override
  public List<ProducerData> chooseProducers(List<ProducerData> producers) {
    return producers.stream()
        .sorted(
            ((Comparator<ProducerData>)
                    (p1, p2) -> {
                      if (EnergyType.valueOf(p1.getEnergyType()).isRenewable()
                          == EnergyType.valueOf(p2.getEnergyType()).isRenewable()) {
                        return 0;
                      }
                      return EnergyType.valueOf(p1.getEnergyType()).isRenewable() ? -1 : 1;
                    })
                .thenComparingDouble(ProducerData::getPriceKW)
                .thenComparing(ProducerData::getEnergyPerDistributor, Comparator.reverseOrder())
                .thenComparing(ProducerData::getId))
        .collect(Collectors.toList());
  }
}
