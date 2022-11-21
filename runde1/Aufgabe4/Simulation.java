package runde1.Aufgabe4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.OptionalDouble;

public class Simulation {
  /**
   * a list of all tasks which are going to be received
   */
  private ArrayList<Task> tasks;
  /**
   * list of task which where received
   */
  private ArrayList<Task> taskQueue = new ArrayList<>();
  /**
   * task which is currently worked on
   */
  private Task currentTask = null;
  /**
   * variant of the simulation
   * variant = 0: work on the tasks in order
   * variant = 1: work always on shortest task which in the taskQueue
   */
  int variant;
  Time time;
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

  public void addWaitingTimes(int time){
    for(Task task: taskQueue){
      task.addWaitingTime(time);
    }
    if(currentTask != null){
      currentTask.addWaitingTime(time);
    }
  }

  /**
   * run the simulation until all tasks are finished
   */
  public void run(){
    time = new Time(540, 1020); // 9h, 17h
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
    time.startDay();
    addWaitingTimes(540); // 9h

    while(time.isWorkTime() && tasksNotDone()) {
      if(currentTask != null) {
        workOnCurrentTask();
      }else{
        setCurrentTask();
      }
      addTasksToQueue();
    }

    time.endDay();
    addWaitingTimes(420); // 7h
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
    if(currentTask.canBeFinished(time.workTimeLeft())){
      int workTime = currentTask.finish();
      addWaitingTimes(workTime);
      time.add(workTime);
      waitTime.add(currentTask.getWaitingTime());
      currentTask = null;
    }else{
      // works on current task until the work day is over
      int workTime = time.workTimeLeft();
      currentTask.work(workTime);
      addWaitingTimes(workTime);
      time.add(workTime);
    }
  }

  private void setCurrentTask(){
    if(taskQueue.size() != 0) {
      if (variant == 0) {
        // select the tasks in order
        currentTask = taskQueue.get(0);
        taskQueue.remove(0);
      } else if (variant == 1) {
        // select always the shortest task
        taskQueue.sort(Comparator.comparingInt(Task::getDuration));
        currentTask = taskQueue.get(0);
        taskQueue.remove(0);
      } else if (variant == 2) {
        taskQueue.sort(Comparator.comparingDouble(o -> o.getDuration() - (double) o.getWaitingTime() / 10));
        currentTask = taskQueue.get(0);
        taskQueue.remove(0);
      }
    }
  }

  private void addTasksToQueue(){
    int addedTasks = 0;
    for(Task task: tasks){
      // task hasn't been received yet
      if(task.hasNotBeenReceived(time.getAbsolute())) break;

      task.addWaitingTime(time.getAbsolute() - task.getTime());
      taskQueue.add(task);
      addedTasks++;
    }
    // remove all added tasks
    if(addedTasks > 0){
      tasks.subList(0, addedTasks).clear();
    }

    // add task to queue if there is no current task and the queue is empty
    if(currentTask == null && taskQueue.size() == 0 && tasks.size() != 0){
      Task task = tasks.get(0);
      if(time.isInWorkTime(task.getTime())){
        time.set(task.getTime());
        taskQueue.add(task);
        tasks.remove(0);
      }else{
        time.endWorkDay();
      }
    }
  }
}
