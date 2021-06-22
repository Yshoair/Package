package com.mobiquity.packer;

import com.mobiquity.model.Item;
import com.mobiquity.model.Package;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PackerTest {
  @Test
  void parseItem_test() {
    String itemTxt = "1,53.38,€45";
    Item parsedItem = new Item().parse(itemTxt);
    Item expectedItem = new Item(1, 53.38, 45);
    assertEquals(expectedItem, parsedItem);
  }

  @Test
  void parsePackage_test() {
    String packageTxt = "81 : (1,53.38,€45) (2,88.62,€98)";
    Package parsedPackage = new Package().parse(packageTxt);
    Package expectedPackage = new Package();
    expectedPackage.setCapacity(81);
    Map<Integer, Item> indexToItem = new HashMap<>();
    Item item1 = new Item().parse("1,53.38,€45");
    Item item2 = new Item().parse("2,88.62,€98");
    indexToItem.put(item1.getIndex(), item1);
    indexToItem.put(item2.getIndex(), item2);
    expectedPackage.setIndexToItem(indexToItem);
    assertEquals(expectedPackage, parsedPackage);
  }
}
