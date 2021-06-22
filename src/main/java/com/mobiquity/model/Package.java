package com.mobiquity.model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Package {
  Map<Integer, Item> indexToItem;
  double capacity;

  public Map<Integer, Item> getIndexToItem() {
    return indexToItem;
  }

  public void setIndexToItem(Map<Integer, Item> indexToItem) {
    this.indexToItem = indexToItem;
  }

  public double getCapacity() {
    return capacity;
  }

  public void setCapacity(double capacity) {
    this.capacity = capacity;
  }

  public Package parse(String packageTxt) {
    capacity = Double.parseDouble(packageTxt.substring(0, packageTxt.indexOf(":")).trim());
    indexToItem = new HashMap<>();
    String parenthesesGroupRegex = "\\(([^)]+)\\)";
    Matcher m = Pattern.compile(parenthesesGroupRegex).matcher(packageTxt);
    while (m.find()) {
      Item item = new Item().parse(m.group(1));
      indexToItem.put(item.getIndex(), item);
    }
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Package aPackage = (Package) o;
    return Double.compare(aPackage.capacity, capacity) == 0
        && indexToItem.equals(aPackage.indexToItem);
  }
}
