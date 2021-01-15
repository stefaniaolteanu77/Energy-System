package strategies;

import input.DistributorData;
import input.ProducerData;

import java.util.List;

public interface ProducerStrategy {
    List<ProducerData> chooseProducers(List<ProducerData> producers);

}
