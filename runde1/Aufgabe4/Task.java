package runde1.Aufgabe4;

public class Task {
  /**
   * receiving time
   */
  private final int time;
  private int duration;
  private int waitingTime = 0;

  public Task(int time, int duration){
    this.time = time;
    this.duration = duration;
  }

  public Task(String time, String duration){
    this.time = Integer.parseInt(time);
    this.duration = Integer.parseInt(duration);
  }

  public int getTime(){
    return time;
  }

  public void addWaitingTime(int time){
    waitingTime += time;
  }

  public int getWaitingTime(){
    return waitingTime;
  }

  public int getDuration(){
    return duration;
  }

  public void work(int time){
    duration -= time;
  }

  public int finish(){
    int workTime = duration;
    duration = 0;
    return workTime;
  }

  public boolean canBeFinished(int timeAvailable){
    return duration <= timeAvailable;
  }

  public boolean hasNotBeenReceived(int currentTime){
    return time > currentTime;
  }
}
