package observer;

import input.ProducerData;

import java.util.List;
import java.util.Map;

public interface Change {
    void update(Map<Integer, Integer> listOfEnergy,
                List<ProducerData> producers);
}
