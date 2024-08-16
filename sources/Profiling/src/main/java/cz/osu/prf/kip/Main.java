package cz.osu.prf.kip;

public class Main {
  public static void main(String[] args) {

    Worker worker;

    //worker = new Sleeper();
    worker = new MemoryGlutton();
    //worker = new ThreadEmitter();

    worker.start();
    Utils.doSleep(30000);
    worker.abort();
  }
}
