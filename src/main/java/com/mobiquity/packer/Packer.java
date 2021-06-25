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

  private static synchronized void getPackerInstance() {
    if (packer == null) packer = new Packer();
  }

  public static String pack(String filePath) throws APIException {
    getPackerInstance();
    List<Package> packages = packer.packageReader.fetchPackagesFromFile(filePath);
    packer.packContext.setPackStrategy(new DynamicProgrammingBottomUpPackStrategy());
    packages.forEach(p -> packer.packContext.executePackStrategy(p));
    return packages.stream().map(Package::itemsIndexToString).collect(Collectors.joining("\n"));
  }
}
