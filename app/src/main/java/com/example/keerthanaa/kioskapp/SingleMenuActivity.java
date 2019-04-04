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
  int menuQuantity = 1;
  int minMenuQuantity = 1;
  double totalPrice = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "On create single menu activity");
    setContentView(R.layout.activity_single_menu);

    Intent menuIntent = getIntent();
    Bundle extras = menuIntent.getExtras();
    String menuName = extras.getString("Name");
    Double menuPrice = extras.getDouble("Price")/100;
    int imageId = extras.getInt("imageId");
    Log.d(TAG, menuName + menuPrice);

    ImageView menuImage = (ImageView) findViewById(R.id.single_menu_image);
    menuImage.setImageResource(imageId);

    TextView menuNameView = (TextView) findViewById(R.id.single_menu_name);
    menuNameView.setText(menuName);

    TextView menuPriceView = (TextView) findViewById(R.id.single_menu_price);
    menuPriceView.setText(getResources().getString(R.string.single_menu_price, menuPrice));

    ImageButton incrementButton = (ImageButton) findViewById(R.id.increment);
    ImageButton decrementButton = (ImageButton) findViewById(R.id.decrement);
    TextView menuQuantityView = (TextView) findViewById(R.id.menu_quantity);
    menuQuantityView.setText(String.valueOf(menuQuantity));
    menuQuantity = Integer.parseInt(menuQuantityView.getText().toString());

    Button addToCartView = (Button) findViewById(R.id.add_cart_text);
    totalPrice = menuQuantity * menuPrice;
    addToCartView.setText(getResources().getString(R.string.add_items_cart, menuQuantity, totalPrice));

    incrementButton.setOnClickListener(new View.OnClickListener() {
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
    });

    addToCartView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(SingleMenuActivity.this, OrderActivity.class));
      }
    });

  }
}
