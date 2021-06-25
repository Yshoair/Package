package com.mobiquity.model;

import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PackageTest {
  @Test
  void parsePackage_test() throws APIException {
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
  void parsePackageViolatesCapacityConstraintThrowsApiException_test() {
    String inputPackage = "101 : (1,53.38,€45) (2,88.62,€98)";
    assertThrows(APIException.class, () -> new Package().parse(inputPackage));
  }
}
