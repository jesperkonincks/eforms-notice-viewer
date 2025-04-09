package eu.europa.ted.eforms.viewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.europa.ted.eforms.viewer.util.LoggingHelper;

/**
 * Entry point.
 */
@SpringBootApplication
public class Application {
  private static final Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(final String... args) {
    LoggingHelper.installJulToSlf4jBridge();
    SpringApplication.run(Application.class, args);
  }
}
