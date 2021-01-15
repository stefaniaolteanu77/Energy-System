package observer;

import java.util.List;
import java.util.Map;

public interface Change {
    void update(Map<Integer, Integer> listOfEnergy);
}
