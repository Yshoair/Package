package com.mobiquity.model;

public class Item {
  private int index;
  private double weight;
  private int cost;

  public Item() {}

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

  public Item(int index, double weight, int cost) {
    this.index = index;
    this.weight = weight;
    this.cost = cost;
  }

  public Item parse(String txt) {
    String[] params = txt.split(",");
    index = Integer.parseInt(params[0].trim());
    weight = Double.parseDouble(params[1].trim());
    cost = Integer.parseInt(params[2].substring(1).trim());
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item item = (Item) o;
    return index == item.index && Double.compare(item.weight, weight) == 0 && cost == item.cost;
  }
}
