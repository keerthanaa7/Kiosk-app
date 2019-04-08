package com.example.keerthanaa.kioskapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleMenuActivity extends Activity {

  private String TAG = SingleMenuActivity.class.getSimpleName();
  //private int menuQuantity = 1;
 // int minMenuQuantity = 1;
 // double totalPrice = 0;
  private String menuName, menuId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
        // Set the content to appear under the system bars so that the
        // content doesn't resize when the system bars hide and show.
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        // Hide the nav bar and status bar
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    Log.d(TAG, "On create single menu activity");
    setContentView(R.layout.activity_single_menu);

    Intent menuIntent = getIntent();
    Bundle extras = menuIntent.getExtras();
    menuName = extras.getString("Name");
    menuId = extras.getString("menuId");
    Double menuPrice = extras.getDouble("Price");
    int imageId = extras.getInt("imageId");
    Log.d(TAG, menuName + menuPrice);

    ImageView menuImage = (ImageView) findViewById(R.id.single_menu_image);
    menuImage.setImageResource(imageId);

    TextView menuNameView = (TextView) findViewById(R.id.single_menu_name);
    menuNameView.setText(menuName);

    TextView menuPriceView = (TextView) findViewById(R.id.single_menu_price);
    menuPriceView.setText(getResources().getString(R.string.single_menu_price, menuPrice));

   // ImageButton incrementButton = (ImageButton) findViewById(R.id.increment);
  //  ImageButton decrementButton = (ImageButton) findViewById(R.id.decrement);
  //  TextView menuQuantityView = (TextView) findViewById(R.id.menu_quantity);
  //  menuQuantityView.setText(String.valueOf(menuQuantity));
   // menuQuantity = Integer.parseInt(menuQuantityView.getText().toString());

   // Button addToCartView = (Button) findViewById(R.id.add_cart_text);
  //  totalPrice = menuQuantity * menuPrice;
  //  addToCartView.setText(getResources().getString(R.string.add_items_cart, menuQuantity, totalPrice));

    Button checkout = (Button) findViewById(R.id.checkout);
    Button extraMenu = (Button) findViewById(R.id.choose_extra_menu);
    extraMenu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });


   /* incrementButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        menuQuantity = menuQuantity + 1;
        menuQuantityView.setText(String.valueOf(menuQuantity));
        if (addToCartView.getVisibility() == View.GONE) {
          addToCartView.setVisibility(View.VISIBLE);
        }
        totalPrice = menuQuantity * menuPrice;
        addToCartView.setText(getResources().getString(R.string.add_items_cart, menuQuantity, totalPrice));
      }
    });

    decrementButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (menuQuantity > minMenuQuantity) {
          menuQuantity = menuQuantity - 1;
          menuQuantityView.setText(String.valueOf(menuQuantity));
          if (addToCartView.getVisibility() == View.GONE) {
            addToCartView.setVisibility(View.VISIBLE);
          }
          totalPrice = menuQuantity * menuPrice;
          addToCartView.setText(getResources().getString(R.string.add_items_cart, menuQuantity, totalPrice));
        }
      }
    });*/

  /*  addToCartView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();

      }
    });*/

    checkout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent orderIntent = new Intent(SingleMenuActivity.this, OrderActivity.class);
        Bundle extras = new Bundle();
        orderIntent.putExtras(extras);
        startActivity(orderIntent);
      }
    });
  }

}
