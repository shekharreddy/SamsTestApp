package test.nsr.com.samstestapp.ui.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import test.nsr.com.samstestapp.R;
import test.nsr.com.samstestapp.products.ProductsDataManager;

/**
 * @author shekharreddy
 * Activity to display list of products.
 */
public class ProductsListActivity extends AppCompatActivity {
    private static final String TAG = "ProductsListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "OnCreate called");
        setContentView(R.layout.activity_products_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ProductsDataManager.getSingleTonInstance().clearData();
    }
}
