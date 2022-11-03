package runde1.Aufgabe4;

public class Task {
  /**
   * receiving time
   */
  public int time;
  public int duration;

  public Task(int time, int duration){
    this.time = time;
    this.duration = duration;
  }

  public Task(String time, String duration){
    this.time = Integer.parseInt(time);
    this.duration = Integer.parseInt(duration);
  }
}
