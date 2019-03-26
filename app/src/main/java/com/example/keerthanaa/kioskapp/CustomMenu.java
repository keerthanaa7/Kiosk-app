package com.example.keerthanaa.kioskapp;

public class CustomMenu {

  private String mMenuName;

  private Long mMenuPrice;

  // Drawable resource ID
  private int mImageResourceId;

  /*
   * Create a new AndroidFlavor object.
   *
   * @param mName is the name of the menu
   * @param mPrice is the price of the menu
   * @param image is drawable reference ID that corresponds to the menu
   * */
  public CustomMenu(String mName, Long mPrice, int imageResourceId)
  {
    mMenuName = mName;
    mMenuPrice = mPrice;
    mImageResourceId = imageResourceId;
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
  public Long getmMenuPrice() {
    return mMenuPrice;
  }

  /**
   * Get the image resource ID
   */
  public int getImageResourceId() {
    return mImageResourceId;
  }
}
