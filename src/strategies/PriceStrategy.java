package strategies;

import input.DistributorData;
import input.ProducerData;
import output.MonthlyStats;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PriceStrategy implements ProducerStrategy{
    @Override
    public List<ProducerData> chooseProducers(List<ProducerData> producers) {
        return producers.stream().sorted(Comparator.comparingDouble(ProducerData::getPriceKW).
                thenComparing(ProducerData::getEnergyPerDistributor, Comparator.reverseOrder()).
                thenComparing(ProducerData::getId)).collect(Collectors.toList());

    }
}
