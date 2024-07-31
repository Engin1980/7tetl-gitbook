package cz.osu.prf.kip.workers;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Worker extends Thread {
  private static final int FAILED_DATA = -1;
  private static final int NO_DATA = -2;
  public static boolean abortFlag = false;
  private final LinkedBlockingQueue<Integer> store = new LinkedBlockingQueue<>();

  public void waitToFinish() {
    try {
      this.join();
    } catch (InterruptedException ex) {
      // intentionally blank
    }
  }

  protected int getFromStore() {
    int ret = 0;
    try {
      Integer tmp = store.poll(3000, TimeUnit.MILLISECONDS);
      ret = tmp == null ? NO_DATA : tmp;
    } catch (InterruptedException e) {
      ret = FAILED_DATA;
    }
    return ret;
  }

  protected void putToStore(int value) {
    try {
      store.put(value);
    } catch (InterruptedException e) {
      // intentionally blank
    }
  }

  protected void DoRandomSleep() {
    int delay = (int) (Math.random() * 5000);
    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      // intentionally blank
    }
  }
}
