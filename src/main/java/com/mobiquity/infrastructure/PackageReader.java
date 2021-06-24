package com.mobiquity.infrastructure;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Package;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackageReader {
  private final String PACKAGE_REGEX =
      "[0-9]+ ?:( ?\\([1-9][0-9]*,[0-9]+([.][0-9]+)?,€[0-9]+\\) ?)*";

  public PackageReader() {}

  public List<Package> fetchPackagesFromFile(String fileAbsolutePath) throws APIException {
    FileReader fileReader;
    String result;
    List<Package> packages = new ArrayList<>();
    try {
      fileReader = new FileReader(fileAbsolutePath, Charset.forName(StandardCharsets.UTF_8.name()));
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      while ((result = bufferedReader.readLine()) != null) {
        validateInputPackage(result);
        packages.add(new Package().parse(result));
      }
      bufferedReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw new APIException("File not found: " + fileAbsolutePath);
    } catch (IOException e) {
      e.printStackTrace();
      throw new APIException("Couldn't read input data from file");
    }
    return packages;
  }

  private void validateInputPackage(String line) throws APIException {
    Matcher m = Pattern.compile(PACKAGE_REGEX).matcher(line);
    if (!m.matches()) throw new APIException("Invalid input package");
  }
}
