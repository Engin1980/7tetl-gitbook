package cz.osu.prf.kip.workers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Producer extends Worker {
  private final Logger logger;

  public Producer() {
    super();
    this.logger = LogManager.getLogger(this.getClass());
  }

  @Override
  public void run() {
    logger.info("Work started");

    while (!Worker.abortFlag) {
      int value = produceValue();
      logger.info("Produced " + value + ", putting it to the store");
      super.putToStore(value);
    }

    logger.info("Work completed");
  }

  private int produceValue() {
    logger.trace("Entering random sleep");
    super.DoRandomSleep();
    logger.trace("Exiting random sleep");

    int ret = (int) (Math.random() * 1000);
    return ret;
  }
}
