package com.ostap_kozak.inventoryapp.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.ostap_kozak.inventoryapp.data.ProductContract.ProductEntry;
import com.ostap_kozak.inventoryapp.R;

/**
 * Created by ostapkozak on 27/06/2017.
 */

public class ProductCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link ProductCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.textview_product_name);
        TextView priceTextView = (TextView) view.findViewById(R.id.textview_product_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.textview_product_quantity);

        // Find the columns of product attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);

        // Read the product data from cursor
        String productName = cursor.getString(nameColumnIndex);
        double productPrice = cursor.getDouble(priceColumnIndex);
        int productQuantity = cursor.getInt(quantityColumnIndex);

        // Update TextViews with the corresponding attributes
        nameTextView.setText(productName);
        priceTextView.setText(String.valueOf(productPrice));
        quantityTextView.setText(String.valueOf(productQuantity));

    }
}
