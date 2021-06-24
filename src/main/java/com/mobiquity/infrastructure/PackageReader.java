package com.mobiquity.infrastructure;

import com.mobiquity.model.Package;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PackageReader {

  public PackageReader() {}

  public List<Package> fetchPackagesFromFile(String fileAbsolutePath) {
    FileReader fileReader;
    String result;
    List<Package> packages = new ArrayList<>();
    try {
      fileReader = new FileReader(fileAbsolutePath, Charset.forName(StandardCharsets.UTF_8.name()));
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      while ((result = bufferedReader.readLine()) != null)
        packages.add(new Package().parse(result));
      bufferedReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return packages;
  }
}
