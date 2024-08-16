package cz.osu.prf.kip;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class MemoryGlutton extends Worker {
  private static final int BLOCK_SIZE = 10_000_000;
  private final List<Double> data = new LinkedList<>();
  private final Logger logger = LogManager.getLogger();
  @Override
  protected void run() {
    logger.info("Started");

    while (!super.abortFlag) {
      int interval = (int) (Math.random() * 1000);
      Utils.doSleep(interval);

      logger.info("Occupying memory (" + data.size() + ")");
      for (int i = 0; i < BLOCK_SIZE; i++) {
        data.add(Math.random());
      }
    }

    logger.info("Finished");
  }
}
