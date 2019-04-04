package com.example.keerthanaa.kioskapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.clover.sdk.v3.order.LineItem;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order);

    // Create an {@link MenuOrderAdapter}, whose data source is a list of
    // {@link MenuOrder}s. The adapter knows how to create list item views for each item
    // in the list.
    ArrayList<MenuOrder> menuOrders = new ArrayList<MenuOrder>();
    List<LineItem> menuOrderList = InventoryItemsActivity.getLineItemsList();
    for (int i = 0; i < menuOrderList.size(); i++) {
      menuOrders.add(new MenuOrder(menuOrderList.get(i).getName(), menuOrderList.get(i).getPrice()));
    }
    MenuOrderAdapter orderAdapter = new MenuOrderAdapter(this, menuOrders);

    // Get a reference to the ListView, and attach the adapter to the listView.
    ListView orderListView = (ListView) findViewById(R.id.listview_order);
    orderListView.setAdapter(orderAdapter);
  }
}
