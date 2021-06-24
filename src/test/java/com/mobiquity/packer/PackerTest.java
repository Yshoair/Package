package com.mobiquity.packer;

import com.mobiquity.model.Item;
import com.mobiquity.model.Package;
import com.mobiquity.strategy.DynamicProgrammingBottomUpPackStrategy;
import com.mobiquity.strategy.IPackStrategy;
import com.mobiquity.strategy.PackContext;
import org.junit.jupiter.api.Test;

import java.util.*;

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
    List<Item> items = new ArrayList<>();
    Item item1 = new Item().parse("1,53.38,€45");
    Item item2 = new Item().parse("2,88.62,€98");
    items.add(item1);
    items.add(item2);
    expectedPackage.setItems(items);
    assertEquals(expectedPackage, parsedPackage);
  }

  @Test
  void packHighestCostItems_test() {
    String inputPackage =
        "56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36)\n"
            + " (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)";
    Package p = new Package().parse(inputPackage);
    p.getItems().sort(Comparator.comparing(Item::getWeight));
    PackContext packingContext = new PackContext();
    IPackStrategy packStrategy = new DynamicProgrammingBottomUpPackStrategy();
    packingContext.setPackStrategy(packStrategy);
    p =packingContext.executePackStrategy(p);
    p.getItems().sort(Comparator.comparing(Item::getIndex));
    StringBuilder packedItems = new StringBuilder();
    for (Item item : p.getItems()) packedItems.append(item.getIndex()).append(",");
    if (packedItems.length() > 0) packedItems.deleteCharAt(packedItems.length() - 1);
    else packedItems.append("-");
    assertEquals("8,9", packedItems.toString());
  }
}
