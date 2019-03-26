package com.example.keerthanaa.kioskapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.ServiceConnector;
import com.clover.sdk.v3.inventory.InventoryConnector;
import com.clover.sdk.v3.inventory.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();

    private enum ConnectionMethod {
        SERVICE_CONNECTOR, SERVICE_CONNECTOR_USING_CALLBACKS,
    }

    // this applies only to service wrapper
    private InventoryConnector inventoryConnector;

    // this is used for all connection methods
    private boolean serviceIsBound = false;
    private static List<Item> menuItemsList= new ArrayList<Item>();

    // Change this variable if you want to use a different connection method
    private ConnectionMethod connectionMethod = ConnectionMethod.SERVICE_CONNECTOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
            // Set the content to appear under the system bars so that the
            // content doesn't resize when the system bars hide and show.
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            // Hide the nav bar and status bar
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Button orderButton = (Button)findViewById(R.id.order_button);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InventoryItemsActivity.class));

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        connectToServiceConnector();
    }

    private void connectToServiceConnector() {
        inventoryConnector = new InventoryConnector(this, CloverAccount.getAccount(this), new ServiceConnector.OnServiceConnectedListener() {
            @Override
            public void onServiceConnected(ServiceConnector connector) {
                serviceIsBound = true;
                Log.v(TAG, "Connected to service via wrapper");
                fetchObjectsFromServiceConnector();
            }

            @Override
            public void onServiceDisconnected(ServiceConnector connector) {
                Log.v(TAG, "Disconnected from service via wrapper");
                serviceIsBound = false;
            }
        });
        inventoryConnector.connect();
    }


    private void fetchObjectsFromServiceConnector() {
        if (serviceIsBound && inventoryConnector != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
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
                                menuItemsList.add(item);
                                 Log.v(TAG, "item = " + dumpItem(item));
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error ", e);
                    }
                    return null;
                }

            }.execute();
        }
    }

    public static List<Item> getMenuItemsList(){
        return menuItemsList;
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
