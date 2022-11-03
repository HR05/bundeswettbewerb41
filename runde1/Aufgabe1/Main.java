package runde1.Aufgabe1;

import runde1.Input;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
  public static void main(String[] args){
    String text = Input.getText("runde1/Aufgabe1/Input/Alice_im_Wunderland.txt", true);
    for(int i = 0; i <= 5; i++){
      String searchPhrase = Input.getLine("./runde1/Aufgabe1/Input/stoerung" + i + ".txt");
      String phrase = search(text, searchPhrase);
      System.out.println(phrase);
    }
  }

  /**
   * search a phrase in a text, but you don't know every word of the phrase, unknown words are replaced with a "_"
   * @param text a string where the phrases are being searched
   * @param searchPhrase a string which contains "_" where the word is unknown
   * @return if a phrase was found it will return a string where all unknown words are replaced with the right words, if not null
   */
  public static String search(String text, String searchPhrase){
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