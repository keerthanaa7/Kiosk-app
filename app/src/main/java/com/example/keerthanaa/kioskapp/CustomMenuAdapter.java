package com.example.keerthanaa.kioskapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomMenuAdapter extends ArrayAdapter<CustomMenu> {

  private static final String LOG_TAG = CustomMenuAdapter.class.getSimpleName();


  /**
   * This is our own custom constructor (it doesn't mirror a superclass constructor).
   * The context is used to inflate the layout file, and the list is the data we want
   * to populate into the lists.
   *
   * @param context        The current context. Used to inflate the layout file.
   * @param customMenus A List of customMenu objects to display in a grid
   */
  public CustomMenuAdapter(Activity context, ArrayList<CustomMenu> customMenus) {
    // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
    // the second argument is used when the ArrayAdapter is populating a single TextView.
    // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
    // going to use this second argument, so it can be any value. Here, we used 0.
    super(context, 0, customMenus);
  }

  /**
   * Provides a view for an AdapterView (ListView, GridView, etc.)
   *
   * @param position The position in the list of data that should be displayed in the
   *                 list item view.
   * @param convertView The recycled view to populate.
   * @param parent The parent ViewGroup that is used for inflation.
   * @return The View for the position in the AdapterView.
   */
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Check if the existing view is being reused, otherwise inflate the view
    View gridItemView = convertView;
    if(gridItemView == null) {
      gridItemView = LayoutInflater.from(getContext()).inflate(
          R.layout.grid_item, parent, false);
    }

    // Get the {@link CustomMenu} object located at this position in the list
    CustomMenu currentMenu = getItem(position);

    // Find the TextView in the grid_item.xml layout with the ID menu_name
    TextView nameTextView = (TextView) gridItemView.findViewById(R.id.menu_name);
    // Get the menu name from the current CustomMenu object and
    // set this text on the name TextView
    nameTextView.setText(currentMenu.getMenuName());

    // Find the TextView in the grid_item.xml layout with the ID menu_price
    TextView numberTextView = (TextView) gridItemView.findViewById(R.id.menu_price);
    // Get the menu price from the current CustomMenu object and
    // set this text on the number TextView
    numberTextView.setText("$" + currentMenu.getmMenuPrice().toString());

    // Find the ImageView in the list_item.xml layout with the ID list_item_icon
    ImageView iconView = (ImageView) gridItemView.findViewById(R.id.grid_item_icon);
    // Get the image resource ID from the current AndroidFlavor object and
    // set the image to iconView
    iconView.setImageResource(currentMenu.getImageResourceId());

    // Return the whole list item layout (containing 2 TextViews and an ImageView)
    // so that it can be shown in the ListView
    return gridItemView;
  }
}
