package com.mobiquity.service;

import com.mobiquity.contract.IPackStrategy;
import com.mobiquity.model.Item;
import com.mobiquity.model.Package;

import java.util.ArrayList;
import java.util.List;

public class DynamicProgrammingBottomUpPackStrategy implements IPackStrategy {

  private int[][] states;

  @Override
  public Package pack(Package p) {
    p.sortItemsByLowestWeight();
    buildStatesBottomUp(p);
    backtrackSelectedItems(p);
    p.sortItemsByIndex();
    return p;
  }

  private void buildStatesBottomUp(Package p) {
    int itemIndex, capacity;
    int include, exclude;
    int itemsCount = p.getItems().size();
    int totalCapacity = p.getCapacity();
    states = new int[itemsCount + 1][totalCapacity + 1];
    List<Item> items = p.getItems();
    for (itemIndex = 0; itemIndex <= itemsCount; itemIndex++) {
      for (capacity = 0; capacity <= totalCapacity; capacity++) {
        if (itemIndex == 0 || capacity == 0) states[itemIndex][capacity] = 0;
        else if (items.get(itemIndex - 1).getWeight() > capacity)
          states[itemIndex][capacity] = states[itemIndex - 1][capacity];
        else {
          exclude = states[itemIndex - 1][capacity];
          include =
              states[itemIndex - 1][capacity - (int) Math.round(items.get(itemIndex - 1).getWeight())]
                  + items.get(itemIndex - 1).getCost();
          states[itemIndex][capacity] = Math.max(include, exclude);
        }
      }
    }
  }

  private void backtrackSelectedItems(Package p) {
    int itemsCount = p.getItems().size();
    int totalCapacity = p.getCapacity();
    int totalValues = states[itemsCount][totalCapacity];
    List<Item> items = p.getItems();
    List<Item> includedItems = new ArrayList<>();
    for (int itemIndex = itemsCount; itemIndex > 0 && totalValues > 0; itemIndex--) {
      Item itemRow = items.get(itemIndex - 1);
      if (totalValues != states[itemIndex - 1][totalCapacity]) {
        includedItems.add(itemRow);
        totalValues -= itemRow.getCost();
        totalCapacity -= (int) Math.round(itemRow.getWeight());
      }
    }
    p.setItems(includedItems);
  }
}
