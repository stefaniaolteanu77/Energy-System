package strategies;

public final class StrategyFactory {
  private static StrategyFactory instance = null;

  private StrategyFactory() {

  }

  /**
   * Singleton instantiation
   *
   * @return an object of this class
   */
  public static StrategyFactory getInstance() {
    if (instance == null) {
      instance = new StrategyFactory();
    }
    return instance;
  }

  public ProducerStrategy getStrategy(String strategyType) {
    if (strategyType == null) {
      return null;
    }
    if (strategyType.equalsIgnoreCase("GREEN")) {
      return new GreenStrategy();
    } else if (strategyType.equalsIgnoreCase("PRICE")) {
      return new PriceStrategy();
    } else if (strategyType.equalsIgnoreCase("QUANTITY")) {
      return new QuantityStrategy();
    }
    return null;
  }
}
