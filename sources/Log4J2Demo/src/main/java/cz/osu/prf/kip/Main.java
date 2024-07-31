package cz.osu.prf.kip;

import cz.osu.prf.kip.workers.Consumer;
import cz.osu.prf.kip.workers.Producer;
import cz.osu.prf.kip.workers.Worker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.FormatterClosedException;
import java.util.List;

public class Main {
  private static final Logger logger = LogManager.getLogger();
  private static final int PRODUCER_COUNT = 3;
  private static final int CONSUMER_COUNT = 5;
  private static final int RUN_TIME_SECONDS = 30;
  private static final List<Worker> workers = new ArrayList<>();

  public static void main(String[] args) {

    logger.info("Producer count: " + PRODUCER_COUNT);
    logger.info("Consumer count: " + CONSUMER_COUNT);
    logger.info("Run time: " + RUN_TIME_SECONDS);

    logger.info("Starting");
    for (int i = 0; i < PRODUCER_COUNT; i++) {
      Producer p = new Producer();
      p.start();
      workers.add(p);
    }
    for (int i = 0; i < CONSUMER_COUNT; i++) {
      Consumer c = new Consumer();
      c.start();
      workers.add(c);
    }

    logger.info("Waiting");
    try {
      Thread.sleep(RUN_TIME_SECONDS * 1000);
    } catch (InterruptedException e) {
      // intentionally blank
    }

    logger.info("Shutting down");
    Worker.abortFlag = true;

    logger.debug("Waiting for workers");
    for (Worker w : workers) {
      w.waitToFinish();
    }

    logger.info("Done");
  }
}
