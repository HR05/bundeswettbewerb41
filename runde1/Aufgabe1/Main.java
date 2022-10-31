package runde1.Aufgabe1;

import runde1.Input;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
  public static String text = Input.getText("runde1/Aufgabe1/Input/Alice_im_Wunderland.txt", true);

  public static void main(String[] args){
    for(int i = 0; i <= 5; i++){
      String searchPhrase = Input.getLine("./runde1/Aufgabe1/Input/stoerung" + i + ".txt");
      String phrase = search(searchPhrase);
      System.out.println(phrase);
    }
  }

  public static String search(String searchPhrase){
    Pattern pattern = Pattern.compile("_");
    Matcher matcher = pattern.matcher(searchPhrase);
    Pattern searchPattern = Pattern.compile(matcher.replaceAll("\\\\S*"));
    Matcher searchMatcher = searchPattern.matcher(text);
    if(searchMatcher.find()){
      return searchMatcher.group();
    }
    return null;
  }
}