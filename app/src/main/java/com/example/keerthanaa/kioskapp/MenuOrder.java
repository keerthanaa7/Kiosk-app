package com.example.keerthanaa.kioskapp;

public class MenuOrder {

  private String mOrderName;

  private double mOrderPrice;

  /**
   * @param mName  name of the menu ordered
   * @param mPrice price of the menu ordered
   */
  public MenuOrder(String mName, double mPrice) {
    mOrderName = mName;
    mOrderPrice = mPrice;
  }

  /**
   * Get the name of the menu order
   */
  public String getOrderName() {
    return mOrderName;
  }

  /**
   * Get price of the menu order
   */
  public double getOrderPrice() {
    return mOrderPrice;
  }

}
