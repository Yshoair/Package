package com.mobiquity.infrastructure;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Package;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PackageReaderTest {
  @Test
  void fetchPackagesFromFile_Test() throws APIException {
    String fileAbsolutePath =
        "D:\\WorkSpace\\Projects\\Mobiquity\\item-packer\\src\\test\\java\\resources\\example_input";
    PackageReader packageReader = new PackageReader();
    List<Package> packages = packageReader.fetchPackagesFromFile(fileAbsolutePath);
    List<Package> expectedPackages = new ArrayList<>();
    String inputP1 =
        "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) "
            + "(6,46.34,€48)";
    String inputP2 = "8 : (1,15.3,€34)";
    String inputP3 =
        "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) "
            + "(5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)";
    String inputP4 =
        "56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) "
            + "(6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)";
    Package p1 = new Package().parse(inputP1);
    Package p2 = new Package().parse(inputP2);
    Package p3 = new Package().parse(inputP3);
    Package p4 = new Package().parse(inputP4);
    expectedPackages.add(p1);
    expectedPackages.add(p2);
    expectedPackages.add(p3);
    expectedPackages.add(p4);
    assertEquals(expectedPackages, packages);
  }

  @Test
  void whenInvalidInputFileThenThrowAPIException() {
    String fileAbsolutePath =
        "D:\\WorkSpace\\Projects\\Mobiquity\\item-packer\\src\\test\\java\\resources\\example";
    PackageReader packageReader = new PackageReader();
    assertThrows(APIException.class, () -> packageReader.fetchPackagesFromFile(fileAbsolutePath));
  }

  @Test
  void whenInvalidDataInFileThenThrowAPIException() {
    String fileAbsolutePath =
        "D:\\WorkSpace\\Projects\\Mobiquity\\item-packer\\src\\test\\java\\resources\\example_invalid_input";
    PackageReader packageReader = new PackageReader();
    assertThrows(APIException.class, () -> packageReader.fetchPackagesFromFile(fileAbsolutePath));
  }
}
