package com.mobiquity.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Package {
  List<Item> items;
  int capacity;

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public Package parse(String packageTxt) {
    capacity = Integer.parseInt(packageTxt.substring(0, packageTxt.indexOf(":")).trim());
    items = new ArrayList<>();
    String parenthesesGroupRegex = "\\(([^)]+)\\)";
    Matcher m = Pattern.compile(parenthesesGroupRegex).matcher(packageTxt);
    while (m.find()) {
      Item item = new Item().parse(m.group(1));
      items.add(item);
    }
    return this;
  }

  public void sortItemsByLowestWeight() {
    this.items.sort(Comparator.comparing(Item::getWeight));
  }

  public void sortItemsByIndex() {
    this.items.sort(Comparator.comparing(Item::getIndex));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Package aPackage = (Package) o;
    return capacity == aPackage.capacity && Objects.equals(items, aPackage.items);
  }

}
