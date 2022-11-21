package runde1.Aufgabe5;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.alg.shortestpath.*;
import runde1.Input;
import java.util.Arrays;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    for(int i = 0; i < 5; i++) {
      Graph<Integer, DefaultEdge> graph = getGraph("./runde1/Aufgabe5/Input/huepfburg" + i + ".txt");
      GraphPath<Integer, DefaultEdge>[] shortestPaths = findShortestPathToFinish(graph);
      if(shortestPaths == null){
        System.out.println("null");
        continue;
      }
      System.out.println(shortestPaths[0]);
    }
  }

  public static Graph<Integer, DefaultEdge> getGraph(String path){
    String[] lines = Input.getLines(path);
    String[] line0 = lines[0].split(" ");
    int vertices = Integer.parseInt(line0[0]);
    int edges = Integer.parseInt(line0[1]);
    lines = Arrays.copyOfRange(lines, 1, lines.length);

    Graph<Integer, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

    for(String line: lines){
      int vertex1 = Integer.parseInt(line.split(" ")[0]);
      int vertex2 = Integer.parseInt(line.split(" ")[1]);

      graph.addVertex(vertex1);
      graph.addVertex(vertex2);
      graph.addEdge(vertex1, vertex2);
    }

    return graph;
  }

  public static GraphPath<Integer, DefaultEdge>[] findShortestPathToFinish(Graph<Integer, DefaultEdge> graph){
    final int start1 = 1;
    final int start2 = 2;
    final int maxPathLength = 20;
    final boolean simplePathOnly = true;

    GraphPath<Integer, DefaultEdge> shortestPath1 = null;
    GraphPath<Integer, DefaultEdge> shortestPath2 = null;

    AllDirectedPaths<Integer, DefaultEdge> pathSearch = new AllDirectedPaths<>(graph);
    int vertices = graph.vertexSet().size();

    for(int i = 1; i <= vertices; i++) {
      List<GraphPath<Integer, DefaultEdge>> paths1 = pathSearch.getAllPaths(start1, i, simplePathOnly, maxPathLength);
      List<GraphPath<Integer, DefaultEdge>> paths2 = pathSearch.getAllPaths(start2, i, simplePathOnly, maxPathLength);
      
      for (GraphPath<Integer, DefaultEdge> path1 : paths1) {
        for (GraphPath<Integer, DefaultEdge> path2 : paths2) {
          if (path1.getLength() == path2.getLength()) {
            if (shortestPath1 == null || path1.getLength() < shortestPath1.getLength()) {
              shortestPath1 = path1;
              shortestPath2 = path2;
            }
          }
        }
      }
    }

    if(shortestPath1 == null) return null;
    return new GraphPath[]{shortestPath1, shortestPath2};
  }
}
