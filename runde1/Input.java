package runde1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Input {
  /**
   * read a file and convert it to a string array
   * @param path path to the input file
   * @return a string array where one string represents one line
   */
  public static String[] getLines(String path){
    try {
      File file = new File(path);
      Scanner reader = new Scanner(file);
      ArrayList<String> content = new ArrayList<>();
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

  /**
   * read an input file
   * @param path path to the input file
   * @param lowerCase if true content of file is converted to lower case
   * @return the whole content of the file as a string also containing line breaks
   */
  public static String getText(String path, boolean lowerCase){
     try {
      File file = new File(path);
      Scanner reader = new Scanner(file);
      String content = "";
      while(reader.hasNextLine()){
        content += reader.nextLine() + "\n";
      }
      reader.close();

      if(lowerCase){
        content = content.toLowerCase();
      }
      return content;
      
    } catch(FileNotFoundException e){
      e.printStackTrace();
    }
    
    return null;
  }

  /**
   * reads the first line of a file
   * @param path path to the input file
   * @return the first line of the file as a String
   */
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