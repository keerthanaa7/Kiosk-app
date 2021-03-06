package com.example.keerthanaa.kioskapp;

import android.accounts.Account;
import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clover.common2.orders.v3.OrderUtils;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.printer.job.PrintJob;
import com.clover.sdk.v1.printer.job.StaticOrderPrintJob;
import com.clover.sdk.v1.printer.job.TestOrderPrintJob;
import com.clover.sdk.v3.inventory.Item;
import com.clover.sdk.v3.order.LineItem;
import com.clover.sdk.v3.order.Order;
import com.clover.sdk.v3.order.OrderConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class InventoryItemsActivity extends Activity {
  private String TAG = InventoryItemsActivity.class.getSimpleName();
  private LineItem lineItem = null;
  private OrderConnector orderConnector;
  private Account account;
  Order order;
  private static List<LineItem> lineItemList;
  String orderId;
  private int menuQuantity = 1;
  int minMenuQuantity = 1;
  double totalPrice = 0;
  Double menuPrice = 0.0;
  String menuName, menuId;
  int menuImageId;

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
    List<Item> menuList = MainActivity.getMenuItemsList();
    customMenus.add(new CustomMenu(menuList.get(0).getName(), menuList.get(0).getPrice(), R.drawable.bacon_crispy_chicken_burger, (menuList.get(0).getId())));
    customMenus.add(new CustomMenu(menuList.get(1).getName(), menuList.get(1).getPrice(), R.drawable.bbq_bacon_burger, (menuList.get(1).getId())));
    customMenus.add(new CustomMenu(menuList.get(2).getName(), menuList.get(2).getPrice(), R.drawable.cheeseburger, (menuList.get(2).getId())));
    customMenus.add(new CustomMenu(menuList.get(3).getName(), menuList.get(3).getPrice(), R.drawable.coke, (menuList.get(3).getId())));
    customMenus.add(new CustomMenu(menuList.get(4).getName(), menuList.get(4).getPrice(), R.drawable.crispy_chicken_burger, (menuList.get(4).getId())));
    customMenus.add(new CustomMenu(menuList.get(5).getName(), menuList.get(5).getPrice(), R.drawable.double_mushroom_burger, (menuList.get(5).getId())));
    customMenus.add(new CustomMenu(menuList.get(6).getName(), menuList.get(6).getPrice(), R.drawable.fanta, (menuList.get(6).getId())));
    customMenus.add(new CustomMenu(menuList.get(7).getName(), menuList.get(7).getPrice(), R.drawable.frosted_coke, (menuList.get(7).getId())));
    customMenus.add(new CustomMenu(menuList.get(8).getName(), menuList.get(8).getPrice(), R.drawable.grilled_chicken_burger, (menuList.get(8).getId())));
    customMenus.add(new CustomMenu(menuList.get(9).getName(), menuList.get(9).getPrice(), R.drawable.hamburger, (menuList.get(9).getId())));
    customMenus.add(new CustomMenu(menuList.get(10).getName(), menuList.get(10).getPrice(), R.drawable.mushroom_burger, (menuList.get(10).getId())));
    customMenus.add(new CustomMenu(menuList.get(11).getName(), menuList.get(11).getPrice(), R.drawable.vanilla_coffe, (menuList.get(11).getId())));


    // Create an {@link CustomMenuAdapter}, whose data source is a list of
    // {@link customMenus}s. The adapter knows how to create list item views for each item
    // in the list.
    CustomMenuAdapter menuAdapter = new CustomMenuAdapter(this, customMenus);

    // Get a reference to the ListView, and attach the adapter to the listView.
    GridView gridView = (GridView) findViewById(R.id.gridview_menu);
    gridView.setAdapter(menuAdapter);

    account = CloverAccount.getAccount(this);
    orderConnector = new OrderConnector(this, account, null);
    orderConnector.connect();


    ImageButton incrementButton = (ImageButton) findViewById(R.id.increment);
    ImageButton decrementButton = (ImageButton) findViewById(R.id.decrement);
    TextView menuQuantityView = (TextView) findViewById(R.id.menu_quantity);
    Button addToCartView = (Button) findViewById(R.id.add_cart_text);
    Button proceedView = (Button) findViewById(R.id.proceed);
    LinearLayout menuQuantityLayout = (LinearLayout) findViewById(R.id.menu_quantity_layout);


    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CustomMenu menu = customMenus.get(position);
        Log.d(TAG, menu.getMenuName() + menu.getMenuPrice());
        menuQuantity = 1;
        menuQuantityView.setText(String.valueOf(menuQuantity));
        menuPrice = (menu.getMenuPrice()) / 100;
        totalPrice = menuQuantity * menuPrice;
        addToCartView.setVisibility(View.VISIBLE);
        if (menuQuantityLayout.getVisibility() == View.GONE) {
          menuQuantityLayout.setVisibility(View.VISIBLE);
        }
        proceedView.setVisibility(View.VISIBLE);
        addToCartView.setText(getResources().getString(R.string.add_items_cart, menuQuantity, totalPrice));
        menuName = menu.getMenuName();
        menuImageId = menu.getImageResourceId();
        menuId = menu.getMenuId();
      }
    });

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
        addLineItemsToOrder(menuName, menuId);
        Intent menuIntent = new Intent(InventoryItemsActivity.this, SingleMenuActivity.class);
        Bundle extras = new Bundle();
        extras.putString("Name", menuName);
        extras.putDouble("Price", menuPrice);
        extras.putInt("imageId", menuImageId);
        extras.putString("orderId", orderId);

        menuIntent.putExtras(extras);
        startActivity(menuIntent);
      }
    });

    proceedView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent orderIntent = new Intent(InventoryItemsActivity.this, OrderActivity.class);
        orderIntent.putExtra("orderId", orderId);
        startActivity(orderIntent);
      }
    });

    Button kitchenDisplayOrder = (Button) findViewById(R.id.kitchen_display_order);
    kitchenDisplayOrder.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new AsyncTask<Void, Void, Void>() {
          @Override
          protected Void doInBackground(Void... voids) {
            try {
              sendOrderToPrinter(orderConnector.getOrder(orderId));
              orderConnector.fire2(orderId, false);
            } catch (Exception e) {
              Log.w(TAG, "send order to printer failed", e);
            }
            return null;
          }
        }.execute();

      }
    });

    createOrder();
  }

  private void createOrder() {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... voids) {
        try {
          if (order == null) {
            order = orderConnector.createOrder(new Order());
            // orderId = order.getId();
          }
        } catch (Exception e) {
          Log.w(TAG, "create order failed", e);
        }
        return null;
      }
    }.execute();

  }

  private void addLineItemsToOrder(String name, String id) {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... voids) {
        try {
          lineItem = orderConnector.addFixedPriceLineItem(order.getId(), id, name, null);
            lineItem.setUnitQty(menuQuantity);
          if (order.hasLineItems()) {
            lineItemList = new ArrayList<LineItem>(order.getLineItems());
          } else {
            lineItemList = new ArrayList<LineItem>();
          }

          if (lineItem != null) {
            lineItemList.add(lineItem);
            String lineItemId = lineItem.getId();

            if (menuQuantity > 1) {
              Log.d(TAG, "menu quantity > 1 : " + menuQuantity);
              List<String> lineItemIds = new ArrayList<String>();

              for (int j = 0; j < menuQuantity - 1; j++) {
                lineItemIds.add(lineItemId);
              }
              Map<String, List<LineItem>> newLineItems = orderConnector.createLineItemsFrom(order.getId(), order.getId(), lineItemIds);
              lineItemList.addAll(newLineItems.get(lineItemId));
            }
          }

          order = orderConnector.getOrder(order.getId());
          order.setLineItems(lineItemList);
          orderId = order.getId();
        } catch (Exception e) {
          Log.w(TAG, "create order failed", e);
        }
        return null;
      }
    }.execute();
  }

  private String dumpItem(Item item) {
    return item != null ? String.format("%s{id=%s, name=%s, price=%d}", Item.class.getSimpleName(), item.getId(), item.getName(), item.getPrice()) : null;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (orderConnector != null) {
      orderConnector.disconnect();
      orderConnector = null;
    }
    if (lineItemList != null) {
      lineItemList.clear();
    }

  }

  public static List<LineItem> getLineItemsList() {
    return lineItemList;
  }

  private void sendOrderToPrinter(final Order order) {
    int printerFlag = OrderUtils.isAllItemsPrinted(order, null) ? PrintJob.FLAG_REPRINT : PrintJob.FLAG_NONE;
    PrintJob pj = new StaticOrderPrintJob.Builder().markPrinted(true).order(order).flag( PrintJob.FLAG_REPRINT ).build();
    print(pj);
  }

  public void print(PrintJob printJob) {
    printJob.print(this, CloverAccount.getAccount(this));
  }

}



