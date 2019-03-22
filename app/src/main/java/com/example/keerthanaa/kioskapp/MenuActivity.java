package com.example.keerthanaa.kioskapp;

import android.app.Activity;
import android.content.ClipData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

public class MenuActivity extends Activity {

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
  }

 /* @Override
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
        if (connectionMethod == ConnectionMethod.SERVICE_CONNECTOR_USING_CALLBACKS) {
          fetchItemsFromServiceConnectorUsingCallbacks();
        } else {
          fetchObjectsFromServiceConnector();
        }
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
      new AsyncTask<Void, Void, ClipData.Item>() {
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
  }*/
}
