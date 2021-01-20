package com.severeweatheralerts;

import com.severeweatheralerts.TextUtils.StringArrayJoiner;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringArrayJoinerTests {
  @Test
  public void join_emptyArray_EmptyStringReturn() {
    String[] testArr = {};
    assertEquals("", new StringArrayJoiner(testArr).join());
  }

  @Test
  public void join_OneItemInArray_ThatItemReturn() {
    String[] testArr = {"test"};
    assertEquals("test", new StringArrayJoiner(testArr).join());
  }

  @Test
  public void join_TwoItemsInArray_ThoseItemsReturnInOneString() {
    String[] testArr = {"test", "test2"};
    assertEquals("test test2", new StringArrayJoiner(testArr).join());
  }

  @Test
  public void join_DifferentItem_CorrectStringReturn() {
    String[] testArr = {"newString"};
    assertEquals("newString", new StringArrayJoiner(testArr).join());
  }

  @Test
  public void join_DifferentTwoItems_CorrectItemsReturned() {
    String[] testArr = {"newString", "newString2"};
    assertEquals("newString newString2", new StringArrayJoiner(testArr).join());
  }

  @Test
  public void join_ThreeItemsProvided_AllInString() {
    String[] testArr = {"newString", "newString2", "newString3"};
    assertEquals("newString newString2 newString3", new StringArrayJoiner(testArr).join());
  }
}
