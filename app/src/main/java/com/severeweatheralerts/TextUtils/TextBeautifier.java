package com.severeweatheralerts.TextUtils;

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
    String curLine = lineSplit[i];
    String nextLine = lineSplit[i + 1];

    return endsWithPeriod(curLine) &&
           !isLastLine(i, lineSplit.length) &&
           !isTwoNewLines(nextLine);
  }

  private static boolean shouldAppendTwoNewLines(String curLine) {
    return isTwoNewLines(curLine);
  }

  private static boolean shouldAppendCurrentLine(String[] lineSplit, int i) {
    return isLastLine(i, lineSplit.length);
  }

  private static boolean endsWithPeriod(String s) {
    return s.toCharArray()[s.length()-1] == '.';
  }

  private static boolean isLastLine(int i, int length) {
    return i == length - 1;
  }

  private static boolean isTwoNewLines(String s) {
    return s.equals("");
  }
}
