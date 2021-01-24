package input;

import java.util.ArrayList;

public final class EntityFactory {
  private static EntityFactory instance = null;

  private EntityFactory() {

  }

  /**
   * Singleton instantiation
   *
   * @return an object of this class
   */
  public static EntityFactory getInstance() {
    if (instance == null) {
      instance = new EntityFactory();
    }
    return instance;
  }

  /**
   * Factory method
   *
   * @param entityType
   * @return an array list of entities based on the type given as parameters
   */
  public ArrayList<? extends Entity> getEntity(final String entityType) {
    if (entityType == null) {
      return null;
    }
    if (entityType.equalsIgnoreCase("CONSUMERS")) {
      return new ArrayList<ConsumerData>();
    } else if (entityType.equalsIgnoreCase("DISTRIBUTORS")) {
      return new ArrayList<DistributorData>();
    } else if (entityType.equalsIgnoreCase("PRODUCERS")) {
      return new ArrayList<ProducerData>();
    }
    return null;
  }
}
