package com.mobiquity.service;

import com.mobiquity.contract.IPackStrategy;
import com.mobiquity.model.Item;
import com.mobiquity.model.Package;

import java.util.ArrayList;
import java.util.List;

/** Packing Strategy Based on dynamic programming bottom-up algorithm */
public class DynamicProgrammingBottomUpPackStrategy implements IPackStrategy {

  /**
   * 2D array to store different Cost states where the rows represent the item and the column
   * represent the capacity
   */
  private int[][] states;

  /**
   * Wrapper function sorts the items by weight ascending to insure lighter weights are picked by
   * the algorithm in case of equal values and calls builder and backtrack methods.
   *
   * @param p Package to be organised with highest cost lowest weight items
   * @return Package with highest cost items
   */
  @Override
  public Package pack(Package p) {
    p.sortItemsByLowestWeight();
    buildStatesBottomUp(p);
    backtrackSelectedItems(p);
    p.sortItemsByIndex();
    return p;
  }

  /**
   * Builds the states array in a bottom-up manner starting from the base cases and building up to
   * the highest possible value according to this formula f[n,c] = max{f[n-1,c], f[n-1,c-Wn] + Vn}
   * where n is the item index, c is the total capacity W is the weight of the item and V is the
   * value of the item
   *
   * @param p Package to be organised with highest cost lowest weight items
   */
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

  /**
   * Backtracks the built 2D array to fetch the selected items by navigating the array from the last
   * index point with the highest value and capacity to the lowest point
   * compares total values with row before it if they don't match that means the current item
   * will be taken so we fetch it and subtract its value and capacity moving to the next cell
   * till we hit a base case
   * @param p Package to be organised with highest cost lowest weight items
   */
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
