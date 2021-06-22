package com.mobiquity.model;

import java.util.Objects;

public class Item {
  private int index;
  private double weight;
  private int cost;

  public Item() {}

  public Item(int index, double weight, int cost) {
    this.index = index;
    this.weight = weight;
    this.cost = cost;
  }

  public Item parse(String txt) {
    return this;
  }
}
