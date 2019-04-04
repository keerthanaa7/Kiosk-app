package com.example.keerthanaa.kioskapp;

public class CustomMenu {

  private String mMenuName;

  private double mMenuPrice;

  // Drawable resource ID
  private int mImageResourceId;

  private String mMenuId;

  /*
   * Create a new Custom Menu object.
   *
   * @param mName is the name of the menu
   * @param mPrice is the price of the menu
   * @param imageResourceId is drawable reference ID that corresponds to the menu
   * @param mId is the item identifier obtained from inventory connector
   * */
  public CustomMenu(String mName, double mPrice, int imageResourceId, String mId) {
    mMenuName = mName;
    mMenuPrice = mPrice;
    mImageResourceId = imageResourceId;
    mMenuId = mId;
  }

  /**
   * Get the name of the menu
   */
  public String getMenuName() {
    return mMenuName;
  }

  /**
   * Get the price of the menu order
   */
  public double getMenuPrice() {
    return mMenuPrice;
  }

  /**
   * Get the image resource ID
   */
  public int getImageResourceId() {
    return mImageResourceId;
  }

  /**
   * get the item id
   *
   * @return string variable containing the id
   */
  public String getMenuId() {
    return mMenuId;
  }
}
