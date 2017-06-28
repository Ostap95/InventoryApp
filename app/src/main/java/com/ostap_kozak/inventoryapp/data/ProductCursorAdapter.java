package com.ostap_kozak.inventoryapp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
    public View newView(final Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.textview_product_name);
        TextView priceTextView = (TextView) view.findViewById(R.id.textview_product_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.textview_product_quantity);
        Button saleButton = (Button) view.findViewById(R.id.button_sale);

        // Find the columns of product attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);

        // Read the product data from cursor
        String productName = cursor.getString(nameColumnIndex);
        double productPrice = cursor.getDouble(priceColumnIndex);
        final int productQuantity = cursor.getInt(quantityColumnIndex);

        // Update TextViews with the corresponding attributes
        nameTextView.setText(productName);
        priceTextView.setText(String.valueOf(productPrice) + " " + context.getResources().getString(R.string.euro_sign));
        quantityTextView.setText(String.valueOf(productQuantity) + " " + context.getResources().getString(R.string.list_view_unit_indicator));

        int productId = cursor.getInt(cursor.getColumnIndex(ProductEntry._ID));
        final Uri currentProductUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, productId);

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentResolver resolver = v.getContext().getContentResolver();
                ContentValues values = new ContentValues();
                int quantity = productQuantity;
                if (productQuantity > 0) {
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, --quantity);
                    resolver.update(currentProductUri,
                            values,
                            null,
                            null
                    );
                    context.getContentResolver().notifyChange(currentProductUri, null);
                } else {
                    Toast.makeText(context, "Item out of stock", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
