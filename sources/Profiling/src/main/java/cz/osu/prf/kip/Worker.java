package cz.osu.prf.kip;

public abstract class Worker {
  protected boolean abortFlag = false;

  protected abstract void run();

  public void start(){
    Runnable r = () -> run();
    Thread t = new Thread(r);
    t.setName(this.getClass().getSimpleName());
    t.start();
  }

  public void abort() {
    this.abortFlag = true;
  }
}
