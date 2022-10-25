import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Input {
  
  public static void main(String[] args){
    String test = getText("Aufgabe1/Alice_im_Wunderland.txt");
    System.out.println(test);

  }

  public static String[] getLines(String path){
    try {
      File file = new File(path);
      Scanner reader = new Scanner(file);
      ArrayList<String> content = new ArrayList<String>();
      while(reader.hasNextLine()){
        content.add(reader.nextLine());
      }
      reader.close();
      
      String[] returnContent = new String[content.size()];
      content.toArray(returnContent);
      return returnContent;
      
    } catch(FileNotFoundException e){
      e.printStackTrace();
    }
    
    return null;
  }
  
  public static String getText(String path){
     try {
      File file = new File(path);
      Scanner reader = new Scanner(file);
      String content = "";
      while(reader.hasNextLine()){
        content += reader.nextLine() + "\n";
      }
      reader.close();
      return content;
      
    } catch(FileNotFoundException e){
      e.printStackTrace();
    }
    
    return null;
  }

  
  public static String getLine(String path){
    try {
      File file = new File(path);
      Scanner reader = new Scanner(file);
      String line = reader.nextLine();
      reader.close();
      return line;
    } catch(FileNotFoundException e){
      e.printStackTrace();
    }
    
    return null;
  }
}