package com.severeweatheralerts.TextUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExMatcher {
  public static ArrayList<String> match(String regex, String input) {
    ArrayList<String> matches = new ArrayList<>();
    if (input == null) return matches;
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(input);
    while (matcher.find()) matches.add(matcher.group());
    return matches;
  }
}
