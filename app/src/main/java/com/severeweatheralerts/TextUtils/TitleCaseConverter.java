package com.severeweatheralerts.TextUtils;

public class TitleCaseConverter {
  public static String toTitleCase(String input) {
    if (isNotAllCaps(input)) return input;
    String[] words = convertToWordArray(input);
    for (int i = 0; i < words.length; i++) {
      if (shouldBeAllLowerCase(words[i], i))
        words[i] = words[i].toLowerCase();
      else if (shouldBeAllUpperCase(words[i]))
        words[i] = words[i].toUpperCase();
      else
        words[i] = capitalizeFirstLetter(words[i]);
    }
    return new StringArrayJoiner(words).join();
  }

  private static boolean isNotAllCaps(String input) {
    return !input.toUpperCase().equals(input);
  }

  private static String[] convertToWordArray(String input) {
    return input.toLowerCase().split(" ");
  }

  private static String capitalizeFirstLetter(String word) {
    if (word.equals("")) return word;
    char[] chars = word.toCharArray();
    chars[0] = Character.toUpperCase(chars[0]);
    return String.valueOf(chars);
  }

  private static boolean shouldBeAllUpperCase(String word) {
    return isStateAbbreviation(word);
  }

  private static boolean isStateAbbreviation(String word) {
    return word.equals("al") ||
           word.equals("ak") ||
           word.equals("az") ||
           word.equals("ar") ||
           word.equals("ca") ||
           word.equals("co") ||
           word.equals("ct") ||
           word.equals("de") ||
           word.equals("fl") ||
           word.equals("ga") ||
           word.equals("hi") ||
           word.equals("id") ||
           word.equals("il") ||
           word.equals("in") ||
           word.equals("ia") ||
           word.equals("ks") ||
           word.equals("ky") ||
           word.equals("la") ||
           word.equals("me") ||
           word.equals("md") ||
           word.equals("ma") ||
           word.equals("mi") ||
           word.equals("mn") ||
           word.equals("ms") ||
           word.equals("mo") ||
           word.equals("mt") ||
           word.equals("ne") ||
           word.equals("nv") ||
           word.equals("nh") ||
           word.equals("nj") ||
           word.equals("nm") ||
           word.equals("ny") ||
           word.equals("nc") ||
           word.equals("nd") ||
           word.equals("oh") ||
           word.equals("ok") ||
           word.equals("or") ||
           word.equals("pa") ||
           word.equals("ri") ||
           word.equals("sc") ||
           word.equals("sd") ||
           word.equals("tn") ||
           word.equals("tx") ||
           word.equals("ut") ||
           word.equals("vt") ||
           word.equals("va") ||
           word.equals("wa") ||
           word.equals("wv") ||
           word.equals("wi") ||
           word.equals("wy");
  }

  private static boolean shouldBeAllLowerCase(String word, int wordIndex) {
    return notFirstWord(wordIndex) &&
           (word.equals("to") ||
            word.equals("and") ||
            word.equals("at") ||
            word.equals("the") ||
            word.equals("for") ||
            word.equals("in") ||
            word.equals("of"));
  }

  private static boolean notFirstWord(int wordIndex) {
    return wordIndex != 0;
  }
}
