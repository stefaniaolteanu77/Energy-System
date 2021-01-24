package action;

import input.DistributorData;
import input.ProducerData;
import observer.EnergyChange;
import output.MonthlyStats;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProducersActions {
    private ProducersActions() {

    }

    public static void setProducerMonthlyStat(final List<ProducerData> producers, int month) {
        for (ProducerData producer : producers) {
            List<Integer> distributors = new ArrayList<>(producer.getDistributors());
            List<Integer> sortedDistributors = distributors.stream().sorted().collect(Collectors.toList());
            MonthlyStats monthlyStats = new MonthlyStats(month, sortedDistributors);
            producer.getMonthlyStats().add(monthlyStats);
        }

    }
    public static Map<Integer, Integer> setListOfChanges(final List<ProducerData> producers) {
        Map<Integer, Integer> listOfChanges = new LinkedHashMap<>();
        for (ProducerData producer : producers) {
            listOfChanges.put(producer.getId(), producer.getEnergyPerDistributor());
        }
        return listOfChanges;
    }
    public static void updateProducers(final List<DistributorData> distributors,
                                       final List<ProducerData> producers,
                                       Map<Integer, Integer> listOfChanges) {
        EnergyChange energyChange = new EnergyChange();
        for (DistributorData distributor : distributors) {
            energyChange.addObserver(distributor);
        }
        energyChange.set(listOfChanges, producers);

        for (DistributorData distributor : distributors) {
            energyChange.removeObserver(distributor);
        }
    }

}
