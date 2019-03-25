package com.example.keerthanaa.kioskapp;

import android.app.Activity;
import android.content.ClipData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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

import java.util.List;

import static android.content.ContentValues.TAG;

public class MenuActivity extends Activity {

  private enum ConnectionMethod {
    SERVICE_CONNECTOR, SERVICE_CONNECTOR_USING_CALLBACKS,
  }

  // this applies only to service wrapper
  private InventoryConnector inventoryConnector;

  // this is used for all connection methods
  private boolean serviceIsBound = false;
  private TextView resultText;
  private TextView statusText;

  // Change this variable if you want to use a different connection method
  private ConnectionMethod connectionMethod = ConnectionMethod.SERVICE_CONNECTOR;

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

    resultText = (TextView) findViewById(R.id.inventoryResult);
    statusText = (TextView) findViewById(R.id.inventoryStatus);
  }

  @Override
  protected void onResume() {
    super.onResume();
    connectToServiceConnector();
  }

  private void connectToServiceConnector() {
    inventoryConnector = new InventoryConnector(this, CloverAccount.getAccount(this), new ServiceConnector.OnServiceConnectedListener() {
      @Override
      public void onServiceConnected(ServiceConnector connector) {
        serviceIsBound = true;
        statusText.setText("Connected to service via service wrapper");
        fetchObjectsFromServiceConnector();
      }

      @Override
      public void onServiceDisconnected(ServiceConnector connector) {
        statusText.setText("Disconnected from service via wrapper");
        serviceIsBound = false;
      }
    });
    inventoryConnector.connect();
  }

  private void fetchObjectsFromServiceConnector() {
    if (serviceIsBound && inventoryConnector != null) {
      new AsyncTask<Void, Void, Item>() {
        @Override
        protected Item doInBackground(Void... params) {
          Item item = null;
          try {
            List<String> items = inventoryConnector.getItemIds();
            if (items != null) {
              for (int i = 0; i < items.size(); i++) {
                String itemId = items.get(i);
                // just print out the first few to the console
                if (i > 10) {
                  break;
                }
                item = inventoryConnector.getItem(itemId);
                Log.v(TAG, "item = " + dumpItem(item));
              }

            }
          } catch (Exception e) {
            Log.e(TAG, "Error ", e);
          }
          return item;
        }

        @Override
        protected void onPostExecute(Item result) {
          displayItem(result);
        }
      }.execute();
    }
  }


  private void fetchItemsFromServiceConnectorUsingCallbacks() {
    if (serviceIsBound && inventoryConnector != null) {
      inventoryConnector.getItems(new ServiceCallback<List<Item>>() {

        @Override
        public void onServiceSuccess(List<Item> items, ResultStatus status) {
          if (items != null && items.size() > 0) {
            String itemId = items.get(0).getId();
            inventoryConnector.getItem(itemId, new ServiceCallback<Item>() {
              @Override
              public void onServiceSuccess(Item result, ResultStatus resultStatus) {
                displayItem(result);
              }

              @Override
              public void onServiceFailure(ResultStatus resultStatus) {
                Log.e(TAG, "Error calling getItem()");
              }

              @Override
              public void onServiceConnectionFailure() {
                Log.e(TAG, "Error calling getItem() - couldn't connect to service");
              }
            });
          }
        }

        @Override
        public void onServiceFailure(ResultStatus status) {
          Log.e(TAG, "Error calling getItems()");
        }

        @Override
        public void onServiceConnectionFailure() {
          Log.e(TAG, "Error binding to inventory service");
        }
      });
    }
  }

  private void displayItem(Item item) {
    if (item != null) {
      String textViewContents = "";
      CharSequence text = resultText.getText();
      if (text != null) {
        textViewContents = text.toString();
      }
      resultText.setText(textViewContents + "\nitem = " + dumpItem(item));
    }
  }


  private String dumpItem(Item item) {
    return item != null ? String.format("%s{id=%s, name=%s, price=%d}", Item.class.getSimpleName(), item.getId(), item.getName(), item.getPrice()) : null;
  }

  @Override
  protected void onStop() {
    super.onStop();
  }

  @Override
  protected void onPause() {
    switch (connectionMethod) {
      case SERVICE_CONNECTOR:
      case SERVICE_CONNECTOR_USING_CALLBACKS:
        if (inventoryConnector != null) {
          inventoryConnector.disconnect();
          inventoryConnector = null;
        }
        break;
    }

    if (serviceIsBound) {
      serviceIsBound = false;
    }

    super.onPause();
  }
}
