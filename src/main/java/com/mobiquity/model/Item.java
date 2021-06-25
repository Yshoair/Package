package com.mobiquity.model;

import com.mobiquity.exception.APIException;

public class Item {
  private final int MAX_WEIGHT = 100;
  private final int MAX_COST = 100;
  private final int MAX_INDEX = 15;
  private int index;
  private double weight;
  private int cost;

  public Item() {}

  public Item(int index, double weight, int cost) {
    this.index = index;
    this.weight = weight;
    this.cost = cost;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public Item parse(String InputItem) throws APIException {
    String[] params = InputItem.split(",");
    try {
      index = Integer.parseInt(params[0].trim());
      weight = Double.parseDouble(params[1].trim());
      cost = Integer.parseInt(params[2].substring(1).trim());
    } catch (NumberFormatException | IndexOutOfBoundsException e) {
      e.printStackTrace();
      throw new APIException("Invalid Input item format");
    }
    validateConstraints();
    return this;
  }

  private void validateConstraints() throws APIException {
    if (weight > MAX_WEIGHT)
      throw new APIException("Input item weight shouldn't exceed: " + MAX_WEIGHT);
    if (cost > MAX_COST) throw new APIException("Input item cost shouldn't exceed: " + MAX_COST);
    if (index > MAX_INDEX)
      throw new APIException("Input item index shouldn't exceed: " + MAX_INDEX);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item item = (Item) o;
    return index == item.index && Double.compare(item.weight, weight) == 0 && cost == item.cost;
  }
}
