package com.mobiquity.packer;

import com.mobiquity.infrastructure.PackageReader;
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

  @Test
  void fetchPackagesFromFile_Test() {
    String fileAbsolutePath = "D:\\WorkSpace\\Projects\\Mobiquity\\example_input.txt";
    PackageReader packageReader = new PackageReader();
    List<Package> packages = packageReader.fetchPackagesFromFile(fileAbsolutePath);
    List<Package> expectedPackages = new ArrayList<>();
    String inputP1 = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9)\n" +
            "(6,46.34,€48)";
    String inputP2 = "8 : (1,15.3,€34)";
    Package p1 = new Package().parse(inputP1);
    Package p2 = new Package().parse(inputP2);
    expectedPackages.add(p1);
    expectedPackages.add(p2);
    assertEquals(expectedPackages, packages);
  }
}
