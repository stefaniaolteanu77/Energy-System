package output;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import input.ConsumerData;
import input.DistributorData;
import input.ProducerData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Writer implements Parsing {
  private final FileWriter file;

  public Writer(final String path) throws IOException {
    this.file = new FileWriter(path);
  }

  /**
   * Write to output file
   * @param consumers list of consumers to be written to output file
   * @param distributors list of distributors to be written to output file
   */
  public void writeFile(final List<ConsumerData> consumers,
                        final List<DistributorData> distributors,
                        final List<ProducerData> producers) {
    try {
      Map<String, Object> map = new LinkedHashMap<>();
      map.put(Constants.CONSUMERS, consumers);
      map.put(Constants.DISTRIBUTORS, distributors);
      map.put(Constants.ENERGY_PRODUCERS, producers);
      ObjectMapper mapper = new ObjectMapper();
      ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
      writer.writeValue(file, map);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
