package com.example.keerthanaa.kioskapp;

import android.app.Activity;
import android.content.ClipData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.ResultStatus;
import com.clover.sdk.v1.ServiceCallback;
import com.clover.sdk.v1.ServiceConnector;
import com.clover.sdk.v3.inventory.Category;
import com.clover.sdk.v3.inventory.InventoryConnector;
import com.clover.sdk.v3.inventory.InventoryContract;
import com.clover.sdk.v3.inventory.Item;
import com.clover.sdk.v3.inventory.TaxRate;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class InventoryItemsActivity extends Activity {
  private String TAG = InventoryItemsActivity.class.getSimpleName();

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
    setContentView(R.layout.activity_menu);


    // Create an ArrayList of customMenu objects
    ArrayList<CustomMenu> customMenus = new ArrayList<CustomMenu>();
    for (Item item : MainActivity.getMenuItemsList() ){
      Log.v(TAG, "item :  " + dumpItem(item));
      customMenus.add(new CustomMenu(item.getName(), item.getPrice(), R.drawable.bbq_bacon_burger));
    }


    // Create an {@link AndroidFlavorAdapter}, whose data source is a list of
    // {@link AndroidFlavor}s. The adapter knows how to create list item views for each item
    // in the list.
    CustomMenuAdapter menuAdapter = new CustomMenuAdapter(this, customMenus);

    // Get a reference to the ListView, and attach the adapter to the listView.
    GridView gridView = (GridView) findViewById(R.id.gridview_menu);
    gridView.setAdapter(menuAdapter);
  }

  private String dumpItem(Item item) {
    return item != null ? String.format("%s{id=%s, name=%s, price=%d}", Item.class.getSimpleName(), item.getId(), item.getName(), item.getPrice()) : null;
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "resume");
    }
}
