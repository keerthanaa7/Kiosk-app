package com.example.keerthanaa.kioskapp;

public class CustomMenu {

  private String mMenuName;

  private double mMenuPrice;

  // Drawable resource ID
  private int mImageResourceId;

  private String mMenuId;

  /*
   * Create a new AndroidFlavor object.
   *
   * @param mName is the name of the menu
   * @param mPrice is the price of the menu
   * @param image is drawable reference ID that corresponds to the menu
   * */
  public CustomMenu(String mName, double mPrice, int imageResourceId, String mId)
  {
    mMenuName = mName;
    mMenuPrice = mPrice;
    mImageResourceId = imageResourceId;
    mMenuId = mId;
  }

  /**
   * Get the name of the mrnu
   */
  public String getMenuName() {
    return mMenuName;
  }

  /**
   * Get the Android version number
   */
  public double getmMenuPrice() {
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
   * @return string variable containing the id
   */
  public String getMenuId() {
    return mMenuId;
  }
}
