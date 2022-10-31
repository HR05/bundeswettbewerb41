package runde1.Aufgabe4;

import runde1.Input;

import java.util.ArrayList;

public class Main {
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

  public static ArrayList<Task> cloneTasks(ArrayList<Task> tasks){
    ArrayList<Task> newTasks = new ArrayList<>();
    for(Task task: tasks){
      newTasks.add(new Task(task.time, task.duration));
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
    }
  }

  public static void printWaitTimes(int i, int variant, double maxWaitTime, double averageWaitTime){
    System.out.printf("Input: fahrradwerkstatt%d.txt | Variant: %d\n", i, variant);
    System.out.printf("  maximum waiting time: %.0f min\n", maxWaitTime);
    System.out.printf("  average waiting time: %.2f min\n", averageWaitTime);
    System.out.println("----------------------------------------------------");
  }
}
