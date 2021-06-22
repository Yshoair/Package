package com.mobiquity.model;

import java.util.Map;

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
    return null;
  }
}
