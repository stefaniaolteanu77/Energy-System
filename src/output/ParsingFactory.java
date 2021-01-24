package output;

import input.InputLoader;

import java.io.IOException;

public final class ParsingFactory {

  private final String inputPath;
  private final String outputPath;

  public ParsingFactory(final String inputPath, final String outputPath) {
    this.inputPath = inputPath;
    this.outputPath = outputPath;
  }

  /**
   * @param type string that says what we instantiate
   * @return either a writer or an inputLoader based on the type string
   * @throws IOException
   */
  public Parsing getInputOutput(final String type) throws IOException {
    if (type == null) {
      return null;
    }
    if (type.equalsIgnoreCase("INPUT")) {
      return new InputLoader(inputPath);

    } else if (type.equalsIgnoreCase("OUTPUT")) {
      return new Writer(outputPath);
    }
    return null;
  }
}
