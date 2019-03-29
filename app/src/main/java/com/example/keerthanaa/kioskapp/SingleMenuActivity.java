package com.example.keerthanaa.kioskapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleMenuActivity extends Activity {

  private String TAG = SingleMenuActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_single_menu);

    Intent menuIntent = getIntent();
    Bundle extras = menuIntent.getExtras();
    String menuName = extras.getString("Name");
    Long menuPrice = extras.getLong("Price");
    int imageId = extras.getInt("imageId");
    Log.d(TAG, menuName + menuPrice);

    ImageView menuImage = (ImageView)findViewById(R.id.single_menu_image);
    menuImage.setImageResource(imageId);

    TextView menuNameView = (TextView)findViewById(R.id.single_menu_name);
    menuNameView.setText(menuName);

    TextView menuPriceView = (TextView)findViewById(R.id.single_menu_price);
    menuPriceView.setText("Price : $" + menuPrice.toString());

  }
}
