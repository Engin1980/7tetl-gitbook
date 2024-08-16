package cz.osu.prf.kip;

public class Utils {

  public static void doSleep(int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException ex) {
      // intentionally blank
    }
  }

}
