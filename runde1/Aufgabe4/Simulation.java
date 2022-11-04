package runde1.Aufgabe4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.OptionalDouble;

public class Simulation {
  /**
   * a list of all tasks which are going to be received
   */
  ArrayList<Task> tasks;
  /**
   * list of task which where received
   */
  ArrayList<Task> taskQueue = new ArrayList<>();
  /**
   * task which is currently worked on
   */
  Task currentTask = null;
  /**
   * 9h converted to minutes
   * start of work day
   */
  final int startTime = 540;
  /**
   * 17h converted to minutes
   * end of work day
   */
  final int endTime = 1020;
  final int day = 1440;
  /**
   * variant of the simulation
   * variant = 0: work on the tasks in order
   * variant = 1: work always on shortest task which in the taskQueue
   */
  int variant;
  /**
   * absolute time in minutes
   */
  int time = 0;
  /**
   * relative time in minutes
   * maximum 1440 minutes (24h)
   */
  int relativeTime = 0;
  /**
   * a list of all waiting times
   * the waiting time is the difference between the receiving time and the finishing time of the task
   */
  ArrayList<Integer> waitTime = new ArrayList<>();

  public Simulation(ArrayList<Task> tasks, int variant){
    this.tasks = tasks;
    this.variant = variant;
  }

  /**
   * get the maximum waiting time and compute the average waiting time
   * @return maximum waiting time, average waiting time
   */
  public double[] getWaitTimes(){
    int maxWaitTime = Collections.max(waitTime);
    OptionalDouble optionalDouble = waitTime.stream().mapToDouble(x -> x).average();
    double averageWaitTime = optionalDouble.isPresent() ? optionalDouble.getAsDouble() : 0;
    return new double[]{maxWaitTime, averageWaitTime};
  }

  /**
   * run the simulation until all tasks are finished
   */
  public void run(){
    time = 0;
    while(tasksNotDone()){
      runDay();
    }
  }

  /**
   * one work day
   * starts at 9h
   * ends at 17h or when all tasks are done
   */
  private void runDay(){
    relativeTime = startTime;
    time += startTime;

    while(relativeTime <  endTime && tasksNotDone()) {
      if(currentTask != null) {
        workOnCurrentTask();
      }else{
        setCurrentTask();
      }
      addTasksToQueue();
    }

    time += day - endTime;
  }

  /**
   * looks if all tasks are finished
   * @return tasks are not done -> true, tasks are done -> false
   */
  private boolean tasksNotDone(){
    return tasks.size() != 0 || taskQueue.size() != 0 || currentTask != null;
  }

  private void workOnCurrentTask(){
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

  private void setCurrentTask(){
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

  private void addTasksToQueue(){
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
      relativeTime = time % day;
      taskQueue.add(tasks.get(0));
      tasks.remove(0);
    }
  }
}
