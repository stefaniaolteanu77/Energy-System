package observer;

import input.ProducerData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnergyChange {
    private final List<Change> changes = new ArrayList<>();

    public void addObserver(final Change change) {
        this.changes.add(change);
    }

    public void removeObserver(final Change change) {
        this.changes.remove(change);
    }

    public void set(final Map<Integer, Integer> listOfEnergy,
                    List<ProducerData> producers) {
        for (Change change : changes) {
            change.update(listOfEnergy, producers);
        }
    }
}
