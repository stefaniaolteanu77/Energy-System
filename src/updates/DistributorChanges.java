package updates;

public final class DistributorChanges {
  private final int id;
  private final int infrastructureCost;

  public DistributorChanges(final int id, final int infrastructureCost) {
    this.id = id;
    this.infrastructureCost = infrastructureCost;
  }

  public int getId() {
    return id;
  }

  public int getInfrastructureCost() {
    return infrastructureCost;
  }

  @Override
  public String toString() {
    return "CostChangeInputData{" + "id=" + id + ", infrastructureCost=" + infrastructureCost + '}';
  }
}
