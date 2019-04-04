package com.example.keerthanaa.kioskapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuOrderAdapter extends ArrayAdapter<MenuOrder> {

  private static final String LOG_TAG = MenuOrderAdapter.class.getSimpleName();


  /**
   * This is our own custom constructor (it doesn't mirror a superclass constructor).
   * The context is used to inflate the layout file, and the list is the data we want
   * to populate into the lists.
   *
   * @param context    The current context. Used to inflate the layout file.
   * @param menuOrders A List of MenuOrder objects to display in a listview
   */
  public MenuOrderAdapter(Activity context, ArrayList<MenuOrder> menuOrders) {
    // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
    // the second argument is used when the ArrayAdapter is populating a single TextView.
    // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
    // going to use this second argument, so it can be any value. Here, we used 0.
    super(context, 0, menuOrders);
  }

  /**
   * Provides a view for an AdapterView (ListView, GridView, etc.)
   *
   * @param position    The position in the list of data that should be displayed in the
   *                    list item view.
   * @param convertView The recycled view to populate.
   * @param parent      The parent ViewGroup that is used for inflation.
   * @return The View for the position in the AdapterView.
   */
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Check if the existing view is being reused, otherwise inflate the view
    View listItemView = convertView;
    if (listItemView == null) {
      listItemView = LayoutInflater.from(getContext()).inflate(
          R.layout.list_order_item, parent, false);
    }

    // Get the {@link MenuOrder} object located at this position in the list
    MenuOrder currentOrder = getItem(position);

    // Find the TextView in the list_order_item.xml layout with the ID menu_name
    TextView nameTextView = (TextView) listItemView.findViewById(R.id.order_name);
    // Get the order name from the current currentOrder object and
    // set this text on the name TextView
    nameTextView.setText(currentOrder.getOrderName());

    // Find the TextView in the list_order_item.xml layout with the ID menu_price
    TextView numberTextView = (TextView) listItemView.findViewById(R.id.order_price);
    // Get the menu price from the current currentOrder object and
    // set this text on the number TextView
    numberTextView.setText(getContext().getResources().getString(R.string.menu_price, (currentOrder.getOrderPrice())/100));

    // Return the whole list item layout (containing 2 TextViews
    // so that it can be shown in the ListView
    return listItemView;
  }
}
