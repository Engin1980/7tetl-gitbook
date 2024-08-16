package cz.osu.prf.kip;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Sleeper extends Worker {
  private final Logger logger = LogManager.getLogger();

  @Override
  protected void run() {
    logger.info("Started");

    while (!super.abortFlag) {
      int interval = (int) (Math.random() * 1000);
      Utils.doSleep(interval);
      logger.info("Awake.... and sleeping");
    }

    logger.info("Finished");
  }
}
