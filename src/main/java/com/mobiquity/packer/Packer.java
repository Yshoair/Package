package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.infrastructure.PackageReader;
import com.mobiquity.model.Package;
import com.mobiquity.service.DynamicProgrammingBottomUpPackStrategy;
import com.mobiquity.service.PackContext;

import java.util.List;
import java.util.stream.Collectors;

public class Packer {

  private static Packer packer;
  private final PackageReader packageReader;
  private final PackContext packContext;

  private Packer() {
    packageReader = new PackageReader();
    packContext = new PackContext();
  }

  /**
   * Insures Single instance of packer whenever pack is called synchronized for multi-thread safety
   */
  private static synchronized void setPackerInstance() {
    if (packer == null) packer = new Packer();
  }

  /**
   * fetches packages from file and sets the packing strategy executes it and returns the packed
   * items
   *
   * @param filePath Absolute path to fetch package data
   * @return String representing the selected data with highest cost lowest weight to be packed
   * @throws APIException
   */
  public static String pack(String filePath) throws APIException {
    setPackerInstance();
    List<Package> packages = packer.packageReader.fetchPackagesFromFile(filePath);
    packer.packContext.setPackStrategy(new DynamicProgrammingBottomUpPackStrategy());
    packages.forEach(p -> packer.packContext.executePackStrategy(p));
    return packages.stream().map(Package::itemsIndexToString).collect(Collectors.joining("\n"));
  }
}
