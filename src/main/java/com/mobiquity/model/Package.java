package com.mobiquity.model;

import com.mobiquity.exception.APIException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Package {
  private final int MAX_CAPACITY = 100;
  private final int MAX_ITEMS_NUMBER = 15;
  private List<Item> items;
  private int capacity;

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

  public Package parse(String packageTxt) throws APIException {
    capacity = Integer.parseInt(packageTxt.substring(0, packageTxt.indexOf(":")).trim());
    items = new ArrayList<>();
    String parenthesesGroupRegex = "\\(([^)]+)\\)";
    Matcher m = Pattern.compile(parenthesesGroupRegex).matcher(packageTxt);
    while (m.find()) {
      Item item = new Item().parse(m.group(1));
      items.add(item);
    }
    validateConstraints();
    return this;
  }

  private void validateConstraints() throws APIException {
    if (capacity > MAX_CAPACITY)
      throw new APIException("Input package capacity shouldn't exceed: " + MAX_CAPACITY);
    if (items != null && items.size() > MAX_ITEMS_NUMBER)
      throw new APIException("Input number of items shouldn't exceed: " + MAX_ITEMS_NUMBER);
  }

  public void sortItemsByLowestWeight() {
    this.items.sort(Comparator.comparing(Item::getWeight));
  }

  public void sortItemsByIndex() {
    this.items.sort(Comparator.comparing(Item::getIndex));
  }

  public String getItemsIndexToString() {
    String packedItems =
        items.stream()
            .map(item -> String.valueOf(item.getIndex()))
            .collect(Collectors.joining(","));
    return packedItems.length() > 0 ? packedItems : "-";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Package aPackage = (Package) o;
    return capacity == aPackage.capacity && Objects.equals(items, aPackage.items);
  }
}
