package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.Package;
import com.mobiquity.service.DynamicProgrammingBottomUpPackStrategy;
import com.mobiquity.contract.IPackStrategy;
import com.mobiquity.service.PackContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PackerTest {

  @Test
  void packHighestCostItems_test() throws APIException {
    String inputPackage =
        "56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36)\n"
            + " (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)";
    Package p = new Package().parse(inputPackage);
    PackContext packingContext = new PackContext();
    IPackStrategy packStrategy = new DynamicProgrammingBottomUpPackStrategy();
    packingContext.setPackStrategy(packStrategy);
    p = packingContext.executePackStrategy(p);
    StringBuilder packedItems = new StringBuilder();
    for (Item item : p.getItems()) packedItems.append(item.getIndex()).append(",");
    if (packedItems.length() > 0) packedItems.deleteCharAt(packedItems.length() - 1);
    else packedItems.append("-");
    assertEquals("8,9", packedItems.toString());
  }

  @Test
  void packer_Test() throws APIException {
    String fileAbsolutePath =
        "D:\\WorkSpace\\Projects\\Mobiquity\\item-packer\\src\\test\\java\\resources\\example_input";
    String output = Packer.pack(fileAbsolutePath);
    String expected = "4\n" + "-\n" + "2,7\n" + "8,9";
    assertEquals(expected, output);
  }
}
