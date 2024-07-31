package cz.osu.prf.kip.workers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Consumer extends Worker {

  private final Logger logger;

  public Consumer() {
    super();
    this.logger = LogManager.getLogger(this.getClass()); // + "." + super.id);
  }

  @Override
  public void run() {

    logger.info("Work started");

    while (!Worker.abortFlag) {

      int value = super.getFromStore();
      logger.info("Got {} from store, processing", value);

      try {
        this.processValue(value);
      } catch (Exception ex) {
        logger.error("Error when processing.", ex);
      }
    }

    logger.info("Work completed");
  }

  private void processValue(int value) throws Exception {
    logger.trace("Entering random sleep");
    super.DoRandomSleep();
    logger.trace("Exiting random sleep");

    if (value < 0)
      throw new Exception("Unable to process value " + value + ".");
  }
}
