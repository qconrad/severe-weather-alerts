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
    return finalize(newText.toString());
  }

  private static String finalize(String text) {
    return replaceLinesWithColons(
           removeExcessiveLineBreaks(
           removeDotsAtBeginningOfLines(
           removeSpaceAtEndOfLines(text))));
  }

  private static String removeSpaceAtEndOfLines(String text) {
    return text.replaceAll(" +\n", "\n");
  }

  private static String removeDotsAtBeginningOfLines(String text) {
    return text.replaceAll("\n\\.(?=[^.])", "\n");
  }

  private static String removeExcessiveLineBreaks(String text) {
    return text.replaceAll("\n\n+", "\n\n");
  }

  private static String replaceLinesWithColons(String text) {
    return text.replaceAll("\n-----+", ":");
  }

  private static boolean shouldAppendCurrentLineWithNewLine(String[] lineSplit, int i) {
    String nextLine = (i < lineSplit.length - 1) ? lineSplit[i + 1] : "";
    if (endsWith(lineSplit[i], ':') || startsWith(lineSplit[i+1], '-')) return true;
    return !isTwoNewLines(nextLine) && isListOrSectionHeading(lineSplit, i);
  }

  private static boolean isListOrSectionHeading(String[] lineSplit, int i) {
    return isList(lineSplit, i) || isSectionHeading(lineSplit[i]);
  }

  private static boolean isList(String[] lineSplit, int i) {
    String nextLine = (i < lineSplit.length) ? lineSplit[i + 1] : "";
    return isUnbulletedList(lineSplit, i) || isBulletedList(nextLine, lineSplit[i]);
  }

  private static boolean isBulletedList(String nextLine, String curLine) {
    return endsWith(curLine, '.') && startsWith(nextLine, '*') || startsWith(nextLine, '.');
  }

  private static boolean isUnbulletedList(String[] lineSplit, int i) {
    return startsWithUpperCaseChar(lineSplit[i]) &&  allLinesInParagraphEndWithPeriods(lineSplit, i);
  }

  private static boolean allLinesInParagraphEndWithPeriods(String[] lineSplit, int i) {
    return searchParagraphForwardForPeriods(lineSplit, i);
  }

  private static boolean searchParagraphForwardForPeriods(String[] lineSplit, int i) {
    if (outOfRange(lineSplit, i) || isTwoNewLines(lineSplit[i])) return true;
    if (endsWith(lineSplit[i], '.')) return searchParagraphForwardForPeriods(lineSplit, i+1);
    return false;
  }

  private static boolean outOfRange(String[] lineSplit, int i) {
    return i < 0 || i >= lineSplit.length;
  }

  private static boolean startsWith(String s, char inputChar) {
    if (s.length() < 1) return false;
    return s.charAt(0) == inputChar;
  }

  private static boolean startsWithUpperCaseChar(String line) {
    return Character.isUpperCase(line.charAt(0));
  }

  private static boolean isSectionHeading(String line) {
    return match("\\.\\.\\.$", line).size() > 0;
  }

  private static boolean shouldAppendTwoNewLines(String curLine) {
    return isTwoNewLines(curLine);
  }

  private static boolean shouldAppendCurrentLine(String[] lineSplit, int i) {
    return isLastLine(i, lineSplit.length);
  }

  private static boolean endsWith(String s, char character) {
    if (s.length() < 1) return false;
    return s.toCharArray()[s.length()-1] == character;
  }

  private static boolean isLastLine(int i, int length) {
    return i == length - 1;
  }

  private static boolean isTwoNewLines(String s) {
    return s.equals("");
  }
}
