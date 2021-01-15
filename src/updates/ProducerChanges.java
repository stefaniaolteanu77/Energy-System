package updates;

public class ProducerChanges {
    private final int id;
    private final int energyPerDistributor;

    public ProducerChanges(int id, int energyPerDistributor) {
        this.id = id;
        this.energyPerDistributor = energyPerDistributor;
    }

    public int getId() {
        return id;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    @Override
    public String toString() {
        return "ProducerChanges{" +
                "id=" + id +
                ", energyPerDistributor=" + energyPerDistributor +
                '}';
    }
}
