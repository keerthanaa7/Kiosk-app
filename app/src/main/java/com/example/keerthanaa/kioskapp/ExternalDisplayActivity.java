package com.example.keerthanaa.kioskapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.clover.common2.payments.PayIntent;
import com.clover.sdk.v1.Intents;
import static com.clover.sdk.v1.Intents.ACTION_SECURE_PAY;

public class ExternalDisplayActivity extends Activity {
  static final String ACTION_START_SECURE_PAYMENT = "clover.intent.action.START_SECURE_PAYMENT_EXTERNAL_DISPLAY";
  static final String ACTION_SECURE_PAYMENT_FINISH = "clover.intent.action.SECURE_PAYMENT_EXTERNAL_DISPLAY_FINISH";
  private String TAG = ExternalDisplayActivity.class.getSimpleName();
  Double total = 0.0;
  String orderId;

  private class ExtDisplayReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      if (intent.getAction() == null) {
        return;
      }
      Log.d(TAG, "Got action " + intent.getAction());
      if (ACTION_START_SECURE_PAYMENT.equals(intent.getAction())) {
        total = intent.getDoubleExtra("total",0.0);
        orderId = intent.getStringExtra("orderId");
        securePay();
      }
    }
  }

  private BroadcastReceiver extDisplayReceiver;

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

    setContentView(R.layout.activity_external_display);
  }


  @Override
  protected void onStart() {
    super.onStart();
    if (extDisplayReceiver == null) {
      extDisplayReceiver = new ExtDisplayReceiver();
      IntentFilter filter = new IntentFilter();
      filter.addAction(ACTION_START_SECURE_PAYMENT);
      registerReceiver(extDisplayReceiver, filter);
    }
  }

  @Override
  protected void onStop() {
    if (extDisplayReceiver != null) {
      unregisterReceiver(extDisplayReceiver);
      extDisplayReceiver = null;
    }
    super.onStop();
  }

  private void securePay() {
    Intent intent = new Intent(ACTION_SECURE_PAY);
    PayIntent payIntent = new PayIntent.Builder()
        .amount((new Double(total * 100)).longValue())
        .orderId(orderId)
        .action(Intents.ACTION_SECURE_CARD_DATA)
        .cardEntryMethods(Intents.CARD_ENTRY_METHOD_ALL)
        .build();
    payIntent.addTo(intent);
    startActivityForResult(intent, 100);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 100 && resultCode == RESULT_OK) {
      Intent payFinishIntent  = new Intent(ACTION_SECURE_PAYMENT_FINISH);
      sendBroadcast(payFinishIntent);
    }
  }
}
