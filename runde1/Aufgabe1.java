import java.util.regex.Pattern;
import java.util.regex.Matcher;
import ;

public class Aufgabe1 {
  public static String text = "";

  public static void main(String[] args){
    String phrase = search("das _ mir _ _ _ vor");
    System.out.println(phrase);
  }

  public static String search(String searchPhrase){
    Pattern pattern = Pattern.compile("_");
    Matcher matcher = pattern.matcher(searchPhrase);
    Pattern searchPattern = Pattern.compile(matcher.replaceAll("\\w*"));
    Matcher searchMatcher = searchPattern.matcher(text);
    return searchMatcher.toString();
  }
}
