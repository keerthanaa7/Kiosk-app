package com.example.keerthanaa.kioskapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleMenuActivity extends Activity {

  private String TAG = SingleMenuActivity.class.getSimpleName();
  private String menuName, orderId;

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
    setContentView(R.layout.activity_single_menu);

    Intent menuIntent = getIntent();
    Bundle extras = menuIntent.getExtras();
    if (extras == null) {

    } else {
      menuName = extras.getString("Name");
      Double menuPrice = extras.getDouble("Price");
      orderId = extras.getString("orderId");
      int imageId = extras.getInt("imageId");

      ImageView menuImage = (ImageView) findViewById(R.id.single_menu_image);
      menuImage.setImageResource(imageId);

      TextView menuNameView = (TextView) findViewById(R.id.single_menu_name);
      menuNameView.setText(menuName);

      TextView menuPriceView = (TextView) findViewById(R.id.single_menu_price);
      menuPriceView.setText(getResources().getString(R.string.single_menu_price, menuPrice));
    }


    Button checkout = (Button) findViewById(R.id.checkout);
    Button extraMenu = (Button) findViewById(R.id.choose_extra_menu);
    extraMenu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    checkout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent orderIntent = new Intent(SingleMenuActivity.this, OrderActivity.class);
        orderIntent.putExtra("orderId", orderId);
        startActivity(orderIntent);
      }
    });

  }

}
