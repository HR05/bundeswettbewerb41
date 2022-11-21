package runde1.Aufgabe4;

public class Time {
  private int absolute = 0;
  private int relative = 0;
  private final int day = 1440;
  private final int startTime;
  private final int endTime;

  public Time(int startTime, int endTime){
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public int getAbsolute(){
    return absolute;
  }

  public void set(int time){
    absolute = time;
    relative = time % day;
  }

  public void add(int time){
    absolute += time;
    addToRelative(time);
  }

  private void addToRelative(int time){
    relative += time;
    relative %= day;
  }

  public void startDay(){
    add(startTime - relative);
  }

  public void endWorkDay(){
    add(workTimeLeft());
  }

  public void endDay(){
    if(relative != 0) {
      add(day - relative);
    }
  }

  public boolean isWorkTime(){
    return startTime <= relative && relative < endTime;
  }

  public int workTimeLeft(){
    return endTime - relative;
  }

  public boolean isInWorkTime(int time){
    return absolute - relative + endTime > time;
  }
}
