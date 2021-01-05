package com.severeweatheralerts.TextUtils;

import static com.severeweatheralerts.TextUtils.RegExMatcher.match;

public class TextBeautifier {
  public static String beautify(String text) {
    if (text == null) return null;
    String[] lineSplit = text.split("\n");
    StringBuilder newText = new StringBuilder();
    for (int curLine = 0; curLine < lineSplit.length; curLine++) {
      if (shouldAppendCurrentLine(lineSplit, curLine)) newText.append(lineSplit[curLine]);
      else if (shouldAppendTwoNewLines(lineSplit[curLine])) newText.append("\n\n");
      else if (shouldAppendCurrentLineWithNewLine(lineSplit, curLine)) newText.append(lineSplit[curLine]).append("\n");
      else newText.append(lineSplit[curLine]).append(" "); // append line with space
    }
    return newText.toString().replaceAll(" +\n", "\n").replaceAll("^\\.(?=[^.])", "").replaceAll("\n\\.(?=[^.])", "\n");
  }

  private static boolean shouldAppendCurrentLineWithNewLine(String[] lineSplit, int i) {
    String nextLine = (i < lineSplit.length - 1) ? lineSplit[i + 1] : "";
    String curLine = lineSplit[i];
    String previousLine = (i > 0) ? lineSplit[i - 1] : "";
    return !isTwoNewLines(nextLine) && isListOrSectionHeading(nextLine, curLine, previousLine);
  }

  private static boolean isListOrSectionHeading(String nextLine, String curLine, String previousLine) {
    return endsWith3Periods(curLine) || isList(nextLine, curLine, previousLine);
  }

  private static boolean isList(String nextLine, String curLine, String previousLine) {
    return isUnbulletedList(curLine, previousLine, nextLine) || isBulletedList(nextLine, curLine);
  }

  private static boolean isBulletedList(String nextLine, String curLine) {
    return endsWithPeriod(curLine) && startsWithBullet(nextLine);
  }

  private static boolean isUnbulletedList(String curLine, String previousLine, String nextLine) {
    return startsWithUpperCaseChar(curLine) && endsWithPeriod(curLine) && (hasNeighboringLineWithPeriodAtEnd(previousLine, nextLine));
  }

  private static boolean hasNeighboringLineWithPeriodAtEnd(String previousLine, String nextLine) {
    return endsWithPeriod(previousLine) || endsWithPeriod(nextLine);
  }

  private static boolean startsWithBullet(String line) {
    if (line.length() < 1) return false;
    return line.charAt(0) == '*';
  }

  private static boolean startsWithUpperCaseChar(String line) {
    return Character.isUpperCase(line.charAt(0));
  }

  private static boolean endsWith3Periods(String line) {
    return match("\\.\\.\\.$", line).size() > 0;
  }

  private static boolean shouldAppendTwoNewLines(String curLine) {
    return isTwoNewLines(curLine);
  }

  private static boolean shouldAppendCurrentLine(String[] lineSplit, int i) {
    return isLastLine(i, lineSplit.length);
  }

  private static boolean endsWithPeriod(String s) {
    if (s.length() < 1) return false;
    return s.toCharArray()[s.length()-1] == '.';
  }

  private static boolean isLastLine(int i, int length) {
    return i == length - 1;
  }

  private static boolean isTwoNewLines(String s) {
    return s.equals("");
  }
}
