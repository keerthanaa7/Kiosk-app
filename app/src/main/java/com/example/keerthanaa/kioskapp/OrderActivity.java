package com.example.keerthanaa.kioskapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.clover.sdk.v3.order.LineItem;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends Activity {
  List<LineItem> menuOrderList;
  Double subtotal = 0.0;
  private String TAG = OrderActivity.class.getSimpleName();

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
    setContentView(R.layout.activity_order);
    Double total, tax;

    // Create an {@link MenuOrderAdapter}, whose data source is a list of
    // {@link MenuOrder}s. The adapter knows how to create list item views for each item
    // in the list.

    ArrayList<MenuOrder> menuOrders = new ArrayList<MenuOrder>();
    menuOrderList = InventoryItemsActivity.getLineItemsList();
    if (menuOrderList != null) {
      for (int i = 0; i < menuOrderList.size(); i++) {
        menuOrders.add(new MenuOrder(menuOrderList.get(i).getName(), (menuOrderList.get(i).getPrice()) * (menuOrderList.get(i).getUnitQty()), menuOrderList.get(i).getUnitQty()));
        subtotal = subtotal + (menuOrderList.get(i).getPrice()) * (menuOrderList.get(i).getUnitQty());
      }
    }

    subtotal = subtotal / 100;
    tax = subtotal * .15;
    total = subtotal + tax;
    MenuOrderAdapter orderAdapter = new MenuOrderAdapter(this, menuOrders);

    // Get a reference to the ListView, and attach the adapter to the listView.
    ListView orderListView = (ListView) findViewById(R.id.listview_order);
    orderListView.setAdapter(orderAdapter);

    TextView subtotalView = (TextView) findViewById(R.id.subtotal);
    subtotalView.setText(getResources().getString(R.string.order_price, subtotal));

    TextView taxView = (TextView) findViewById(R.id.tax);
    taxView.setText(getResources().getString(R.string.tax, tax));

    TextView totalView = (TextView) findViewById(R.id.total);
    totalView.setText(getResources().getString(R.string.total, total));

  }
}
