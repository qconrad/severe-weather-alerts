package com.severeweatheralerts.TextUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExMatcher {
  public static ArrayList<String> match(String regex, String input) {
    Pattern pattern = Pattern.compile(regex);
    return getMatches(input, pattern);
  }

  public static ArrayList<String> multiLineMatch(String regex, String input) {
    Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    return getMatches(input, pattern);
  }

  protected static ArrayList<String> getMatches(String input, Pattern pattern) {
    ArrayList<String> matches = new ArrayList<>();
    if (input == null) return matches;
    Matcher matcher = pattern.matcher(input);
    while (matcher.find()) matches.add(matcher.group());
    return matches;
  }
}
