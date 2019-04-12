package com.example.keerthanaa.kioskapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.common2.payments.PayIntent;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.Intents;
import com.clover.sdk.v3.order.LineItem;
import com.clover.sdk.v3.payments.DataEntryLocation;
import com.clover.sdk.v3.payments.Payment;
import com.clover.sdk.v3.payments.TransactionSettings;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends Activity {
  List<LineItem> menuOrderList;
  Double subtotal = 0.0;
  Double total = 0.0;
  String orderId;
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
    Intent orderIntent = getIntent();
    orderId = orderIntent.getStringExtra("orderId");
    Log.d(TAG, "ORDER ID Received " + orderId);
    Double tax;

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

    Button backButton = (Button) findViewById(R.id.back_button);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    Button payButton = (Button) findViewById(R.id.pay);
    payButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pay();
      }
    });

  }

  private void pay() {
    final Intent intent = new Intent();
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... voids) {

        try {
          TransactionSettings txSettings = new TransactionSettings();
          txSettings.setAllowOfflinePayment(true);
          txSettings.setApproveOfflinePaymentWithoutPrompt(true);
          txSettings.setAutoAcceptPaymentConfirmations(true);
          txSettings.setAutoAcceptSignature(true);
          txSettings.setCloverShouldHandleReceipts(false);
          txSettings.setDisableDuplicateCheck(true);
          txSettings.setDisableCashBack(true);
          txSettings.setDisableReceiptSelection(true);
          txSettings.setDisableRestartTransactionOnFailure(true);
          txSettings.setSignatureEntryLocation(DataEntryLocation.NONE);
          txSettings.setCardEntryMethods(Intents.CARD_ENTRY_METHOD_ALL);

          PayIntent pi = new PayIntent.Builder()
              .amount((new Double(total)).longValue())
              .orderId(orderId)
              .transactionSettings(txSettings)
              .action("com.clover.remote.protocol.dualdisplay.action.START_REMOTE_PROTOCOL_PAY")
              .build();
          pi.addTo(intent);
          startActivityForResult(intent, 100);
        } catch (Exception e) {
          e.printStackTrace();
        }

        return null;
      }
    }.execute();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.d(TAG, "onActivityResult: " + resultCode + " for " + requestCode);
    if (requestCode == 100 && resultCode == RESULT_OK) {
      Payment payment = data.getParcelableExtra(Intents.EXTRA_PAYMENT);
      String paymentId = data.getStringExtra(Intents.EXTRA_PAYMENT_ID);
      String orderId = data.getStringExtra(Intents.EXTRA_ORDER_ID);

      Toast.makeText(this, "Payment: " + payment.getJSONObject().toString(), Toast.LENGTH_LONG).show();
    }
    super.onActivityResult(requestCode, resultCode, data);
  }
}
