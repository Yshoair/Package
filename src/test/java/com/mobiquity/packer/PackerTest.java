package com.mobiquity.packer;

import com.mobiquity.model.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PackerTest {
  @Test
  void parseItem_test() {
    String itemTxt = "1,53.38,€45";
    Item parsedItem = new Item().parse(itemTxt);
    Item item = new Item(1, 53.38, 45);
    assertEquals(item, parsedItem);
  }
}
