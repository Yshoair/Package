package com.mobiquity.model;

import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
  @Test
  void parseItem_test() throws APIException {
    String itemTxt = "1,53.38,€45";
    Item parsedItem = new Item().parse(itemTxt);
    Item expectedItem = new Item(1, 53.38, 45);
    assertEquals(expectedItem, parsedItem);
  }

  @Test
  void parseItemViolatesIndexConstraintThrowsApiException_test() {
    String itemTxt = "20,53.38,€45";
    assertThrows(APIException.class, () -> new Item().parse(itemTxt));
  }

  @Test
  void parseItemViolatesWeightConstraintThrowsApiException_test() {
    String itemTxt = "1,101,€45";
    assertThrows(APIException.class, () -> new Item().parse(itemTxt));
  }

  @Test
  void parseItemViolatesCostConstraintThrowsApiException_test() {
    String itemTxt = "20,53.38,€102";
    assertThrows(APIException.class, () -> new Item().parse(itemTxt));
  }
}
