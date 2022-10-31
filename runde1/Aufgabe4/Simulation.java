package runde1.Aufgabe4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.OptionalDouble;

public class Simulation {
  ArrayList<Task> tasks;
  ArrayList<Task> taskQueue = new ArrayList<>();
  Task currentTask = null;
  final int startTime = 540; // 9h
  final int endTime = 1020; // 17h
  int variant;
  int time = 0;
  int relativeTime = 0;

  ArrayList<Integer> waitTime = new ArrayList<>();

  public Simulation(ArrayList<Task> tasks, int variant){
    this.tasks = tasks;
    this.variant = variant;
  }

  /**
   *
   * @return maximum waiting time, average waiting time
   */
  public double[] getWaitTimes(){
    int maxWaitTime = Collections.max(waitTime);
    OptionalDouble optionalDouble = waitTime.stream().mapToDouble(x -> x).average();
    double averageWaitTime = optionalDouble.isPresent() ? optionalDouble.getAsDouble() : 0;
    return new double[]{maxWaitTime, averageWaitTime};
  }

  public void run(){
    time = 0;
    while(tasksNotDone()){
      runDay();
    }
  }

  public void runDay(){
    relativeTime = startTime;

    while(relativeTime <  endTime && tasksNotDone()) {
      if(currentTask != null) {
        workOnCurrentTask();
      }else{
        setCurrentTask();
      }
      addTasksToQueue();
    }
  }

  public boolean tasksNotDone(){
    return tasks.size() != 0 || taskQueue.size() != 0 || currentTask != null;
  }

  public void workOnCurrentTask(){
    // look if task can be done before the end
    int delta_time = endTime - relativeTime; // time left to work
    if(delta_time < currentTask.duration){
      // works on current task until the work day is over
      currentTask.duration -= delta_time;
      time += delta_time;
      relativeTime = endTime;
    }else{
      // finishes current task
      time += currentTask.duration;
      relativeTime += currentTask.duration;
      waitTime.add(time - currentTask.time);
      currentTask = null;
    }
  }

  public void setCurrentTask(){
    if(taskQueue.size() != 0) {
      if (variant == 0) {
        // select the tasks order
        currentTask = taskQueue.get(0);
        taskQueue.remove(0);
      } else if (variant == 1) {
        // select always the shortest task
        taskQueue.sort(Comparator.comparingInt(o -> o.duration));
        currentTask = taskQueue.get(0);
        taskQueue.remove(0);
      }
    }
  }

  public void addTasksToQueue(){
    int addedTasks = 0;
    for(Task task: tasks){
      // task hasn't been received yet
      if(task.time > time) break;

      taskQueue.add(task);
      addedTasks++;
    }
    // remove all added tasks
    if(addedTasks > 0){
      tasks.subList(0, addedTasks).clear();
    }

    // add task to queue if there is no current task and the queue is empty
    if(currentTask == null && taskQueue.size() == 0 && tasks.size() != 0){
      time = tasks.get(0).time;
      taskQueue.add(tasks.get(0));
      tasks.remove(0);
    }
  }
}
