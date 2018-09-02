package test.nsr.com.samstestapp.ui.productdetails;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import test.nsr.com.samstestapp.R;

/**
 * @author shekharreddy
 * Activity to show product details with View Pager.
 */
public class ProductInfoActivity extends AppCompatActivity {

    public static final String TAG = "ProductInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        Log.i(TAG, "OnCreate called");

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ProductsInfoSlidingFragment fragment = new ProductsInfoSlidingFragment();
            transaction.replace(R.id.content_fragment, fragment);
            transaction.commit();
        }
        getSupportActionBar().setTitle(R.string.product_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // back icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
