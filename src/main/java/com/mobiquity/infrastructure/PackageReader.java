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

/** Utility class to read packages with a fixed format from file location */
public class PackageReader {
  private final String PACKAGE_REGEX =
      "[0-9]+ ?:( ?\\([1-9][0-9]*,[0-9]+([.][0-9]+)?,€[0-9]+\\) ?)*";
  private final String FILE_FORMAT = StandardCharsets.UTF_8.name();

  public PackageReader() {}

  /**
   * Fetches input packages from file line by line and transforms them to a list of parsed model
   * representation
   *
   * @param fileAbsolutePath Absolute path of the file to be read
   * @return List of Parsed Packages from the input file
   * @throws APIException
   */
  public List<Package> fetchPackagesFromFile(String fileAbsolutePath) throws APIException {
    FileReader fileReader;
    String result;
    List<Package> packages = new ArrayList<>();
    try {
      fileReader = new FileReader(fileAbsolutePath, Charset.forName(FILE_FORMAT));
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

  /**
   * Validates inputPackage Text against Fixed REGEX
   *
   * @param inputPackage input package text line fetched from file
   * @throws APIException
   */
  private void validateInputPackage(String inputPackage) throws APIException {
    Matcher m = Pattern.compile(PACKAGE_REGEX).matcher(inputPackage);
    if (!m.matches()) throw new APIException("Invalid input package");
  }
}
