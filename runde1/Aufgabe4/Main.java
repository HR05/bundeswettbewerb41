package runde1.Aufgabe4;

import runde1.Input;

import java.util.ArrayList;

public class Main {
  /**
   * get the tasks from a given input file
   * @param path path to the input file
   * @return returns an ArrayList of the tasks
   */
  public static ArrayList<Task> getTasks(String path){
    String[] lines = Input.getLines(path);
    ArrayList<Task> tasks = new ArrayList<>();
    assert lines != null;
    for(String line: lines){
      String[] parts = line.split(" ");
      if(parts.length != 2) continue;
      Task task = new Task(parts[0], parts[1]);
      tasks.add(task);
    }
    return tasks;
  }

  /**
   * clone an ArrayList of tasks
   * every task inside this list is cloned
   * @param tasks an ArrayList which should be cloned
   * @return complete new ArrayList which is an exact copy of the @param tasks
   */
  public static ArrayList<Task> cloneTasks(ArrayList<Task> tasks){
    ArrayList<Task> newTasks = new ArrayList<>();
    for(Task task: tasks){
      newTasks.add(new Task(task.getTime(), task.getDuration()));
    }
    return newTasks;
  }

  public static void main(String[] args) {
    for(int i = 0; i <= 4; i++) {
      ArrayList<Task> tasks = getTasks("./runde1/Aufgabe4/Input/fahrradwerkstatt" + i + ".txt");
      Simulation sim0 = new Simulation(cloneTasks(tasks), 0);
      sim0.run();
      double[] waitTimes0 = sim0.getWaitTimes();
      printWaitTimes(i, 0, waitTimes0[0], waitTimes0[1]);

      Simulation sim1 = new Simulation(cloneTasks(tasks), 1);
      sim1.run();
      double[] waitTimes1 = sim1.getWaitTimes();
      printWaitTimes(i, 1, waitTimes1[0], waitTimes1[1]);

      Simulation sim2 = new Simulation(cloneTasks(tasks), 2);
      sim2.run();
      double[] waitTimes2 = sim2.getWaitTimes();
      printWaitTimes(i, 2, waitTimes2[0], waitTimes2[1]);
    }
  }

  /**
   * prints the waiting times in a readable format
   * @param i index of the input file
   * @param variant variant which is used for the simulation
   * @param maxWaitTime maximum waiting time
   * @param averageWaitTime average waiting time
   */
  public static void printWaitTimes(int i, int variant, double maxWaitTime, double averageWaitTime){
    System.out.printf("Input: fahrradwerkstatt%d.txt | Variant: %d\n", i, variant);
    System.out.printf("  maximum waiting time: %.0f min / %.2f hours / %.2f days\n", maxWaitTime, maxWaitTime / 60, maxWaitTime / (60 * 24));
    System.out.printf("  average waiting time: %.2f min / %.2f hours / %.2f days\n", averageWaitTime, averageWaitTime / 60, averageWaitTime / (60 * 24));
    System.out.println("----------------------------------------------------");
  }
}
