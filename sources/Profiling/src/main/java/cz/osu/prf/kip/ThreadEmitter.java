package cz.osu.prf.kip;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ThreadEmitter extends Worker {
  private final Logger logger = LogManager.getLogger();
  private final List<Thread> threads = new ArrayList<>();

  private void threadRun() {
    while (!super.abortFlag) {
      double a = Math.random();
      double b = Math.random();
      double res = Math.atan2(a, b);
    }
  }

  @Override
  protected void run() {
    logger.info("Started");

    while (!super.abortFlag) {
      int interval = (int) (Math.random() * 2500);
      Utils.doSleep(interval);

      logger.info("Starting new thread (" + threads.size() + ")");
      Thread t = new Thread(() -> threadRun());
      t.setName("Emitter " + threads.size());
      threads.add(t);
      t.start();
    }

    logger.info("Finished");
  }
}
