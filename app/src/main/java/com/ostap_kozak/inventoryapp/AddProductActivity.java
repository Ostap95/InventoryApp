package com.ostap_kozak.inventoryapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ostapkozak on 27/06/2017.
 */

public class AddProductActivity extends AppCompatActivity{

    private static int RESULT_LOAD_IMAGE = 1;

    private EditText editTextProductName, editTextProductQuantity, editTextProductPrice, editTextProductSupplier;
    private ImageView imageViewProductPicture;
    private ImageButton buttonAddPicture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Views initialization
        editTextProductName = (EditText) findViewById(R.id.edittext_product_name);
        editTextProductQuantity = (EditText) findViewById(R.id.edittext_product_quantity);
        editTextProductPrice = (EditText) findViewById(R.id.edittext_product_price);
        editTextProductSupplier = (EditText) findViewById(R.id.edittext_supplier_name);
        imageViewProductPicture = (ImageView) findViewById(R.id.imageview_prodcut);
        buttonAddPicture = (ImageButton) findViewById(R.id.button_add_picture);

        buttonAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            imageViewProductPicture.setImageURI(imageUri);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_product_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                // TODO Handle save action
                saveProduct();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveProduct() {
        String productName = editTextProductName.getText().toString().trim();
        int productQuantity = Integer.parseInt(editTextProductQuantity.getText().toString().trim());
        double productPrice = Double.parseDouble(editTextProductPrice.getText().toString().trim());
        String productSupplier = editTextProductSupplier.getText().toString().trim();
    }
}
