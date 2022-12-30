package com.severeweatheralerts.Graphics.Tools;

import com.severeweatheralerts.Alerts.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RangeGenerator {
  private final Alert alert;
  private final double predictionAmount;
  private double fallbackMargin = 1;

  /** Overrides the default fallback margin of 1 */
  public void setFallbackMargin(double fallbackMargin) {
    this.fallbackMargin = fallbackMargin;
  }

  private enum Unit { INCHES, FEET }

  public RangeGenerator(Alert alert, double predictionAmount) {
    this.alert = alert;
    this.predictionAmount = predictionAmount;
  }

  public String getRange() {
    if (predictionAmount < 0.1)
      return "little to none";

    String description = convertFeetToInches(alert.getDescription());

    // Get the bounds
    List<Integer> range = getClosestRange(getRanges(description), predictionAmount);
    double margin = getMargin(range);
    int lowerBound = (int) Math.round(predictionAmount - margin);
    int upperBound = (int) Math.round(predictionAmount + margin);

    // Determine the unit
    Unit units = Unit.INCHES;
    if (shouldUseFeet(lowerBound, upperBound)) {
      // Convert inches to feet
      lowerBound = toFeet(lowerBound);
      upperBound = toFeet(upperBound);
      units = Unit.FEET;
    }

    return getFinalText(units, lowerBound, upperBound);
  }

  /** Criteria for using feet:
   * 1. Up to a value greater than a foot
   * 2. Lower bound greater than a foot
   */
  private boolean shouldUseFeet(int lowerBound, int upperBound) {
    return (shouldUseUpToFormat(lowerBound) && upperBound >= 12) || lowerBound >= 12;
  }

  /** Converts inches to feet */
  private int toFeet(int inches) {
    return (int) Math.round(inches / 12.0);
  }

  /** Determines the format to use for the final text */
  private String getFinalText(Unit units, int lowerBound, int upperBound) {
    if (shouldUseUpToFormat(lowerBound)) return useUpToFormat(units, upperBound);
    return useRangeFormat(units, lowerBound, upperBound);
  }

  private boolean shouldUseUpToFormat(int lowerBound) {
    return lowerBound <= 0;
  }

  /** Returns text in the range format
   *  Example: 3 to 6 inches
   */
  private String useRangeFormat(Unit units, int lowerBound, int upperBound) {
    return lowerBound + " to " + upperBound + getUnits(units, upperBound);
  }

  /** Returns text in the up to format
   *  Example: up to 3 inches, up to 2 feet
   */
  private String useUpToFormat(Unit units, int upperBound) {
    return "up to " + upperBound + getUnits(units, upperBound);
  }

  /** Returns the units for a value. If the value is 1, returns the singular unit. Otherwise, returns the plural unit. */
  private String getUnits(Unit units, int value) {
    if (units == Unit.FEET) {
      if (value == 1) return " foot";
      return " feet";
    }
    if (value == 1) return " inch";
    return " inches";
  }

  /** Gets margin from a range. Uses fallback margin if range is not found. */
  private double getMargin(List<Integer> range) {
    if (range.size() < 2) return fallbackMargin;
    return getMarginFromBounds(range.get(0), range.get(1));
  }

  /** Replaces feet with inches in the description */
  private String convertFeetToInches(String description) {
    String regex = "(\\d+) to (\\d+) feet|up to (\\d+) (feet|foot)";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(description);
    if (matcher.find()) {
      if (matcher.group(1) != null) {
        int lower = Integer.parseInt(matcher.group(1)) * 12;
        int upper = Integer.parseInt(matcher.group(2)) * 12;
        return description.replace(matcher.group(0), lower + " to " + upper + " inches");
      }
      int upper = Integer.parseInt(matcher.group(3)) * 12;
      return description.replace(matcher.group(0), "up to " + upper + " inches");
    }
    return description;
  }

  /** Gets the closest range to the prediction amount */
  private List<Integer> getClosestRange(List<Integer> ranges, double predictionAmount) {
    List<Integer> bestRange = new ArrayList<>();
    double bestRangeDistance = Double.MAX_VALUE;
    for (int i = 0; i < ranges.size(); i += 2) {
      double rangeDistance = Math.abs(predictionAmount - getRangeCenter(ranges.get(i), ranges.get(i + 1)));
      if (rangeDistance < bestRangeDistance) {
        bestRangeDistance = rangeDistance;
        bestRange.clear();
        bestRange.add(ranges.get(i));
        bestRange.add(ranges.get(i + 1));
      }
    }
    return bestRange;
  }

  /** Gets the center of a range
   * @param lowerBound Lower bound of range
   * @param upperBound Upper bound of range
   * @return Center of range
   */
  private double getRangeCenter(int lowerBound, int upperBound) {
    return (lowerBound + upperBound) / 2.0;
  }

  /** Gets the +- margin given a lower and upper bound
   * Example: 1 to 3 inches -> 1
   * Example: 1 to 6 inches -> 2.5
   */
  private double getMarginFromBounds(int lowerBound, int upperBound) {
    return (upperBound - lowerBound) / 2.0;
  }

  /** Returns a list of ranges in the format [lowerBound, upperBound, lowerBound, upperBound, ...] */
  public List<Integer> getRanges(String description) {
    String rangeRegex = "(\\d+) (to|and) (\\d+) inches";
    String upToRegex = "up to (\\d+) inch";
    Pattern rangePattern = Pattern.compile(rangeRegex);
    Pattern upToPattern = Pattern.compile(upToRegex);
    Matcher rangeMatcher = rangePattern.matcher(description);
    Matcher upToMatcher = upToPattern.matcher(description);
    List<Integer> ranges = new ArrayList<>();
    while (rangeMatcher.find()) {
      int lowerBound = Integer.parseInt(rangeMatcher.group(1));
      int upperBound = Integer.parseInt(rangeMatcher.group(3));
      ranges.add(lowerBound);
      ranges.add(upperBound);
    }
    if (ranges.size() == 0) {
      while (upToMatcher.find()) {
        int upperBound = Integer.parseInt(upToMatcher.group(1));
        ranges.add(0);
        ranges.add(upperBound);
      }
    }
    return ranges;
  }
}
