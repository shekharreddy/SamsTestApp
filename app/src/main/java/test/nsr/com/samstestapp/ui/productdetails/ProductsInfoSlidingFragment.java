package test.nsr.com.samstestapp.ui.productdetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.nsr.com.samstestapp.R;
import test.nsr.com.samstestapp.ui.utils.UIUtils;
import test.nsr.com.samstestapp.ui.utils.ViewPagerTransformer;


/**
 * @author shekharreddy
 * Fragment to show Product details using viewpager adapter.
 */
public class ProductsInfoSlidingFragment extends Fragment {

    static final String TAG = "ProductsInfoSlidingFragment";

    private ViewPager mViewPager;

    /**
     * Inflates the {@link View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        int currentPosition = 0;
        // Inflate the layout for this fragment
        if(getActivity().getIntent() != null) {
            Bundle bundle = getActivity().getIntent().getExtras();
            if (bundle != null) {
                currentPosition = bundle.getInt(UIUtils.BUNDLE_PRODUCT_KEY);
            }
        }
        mViewPager = view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new ProductInfoPagerAdapter());
        mViewPager.setPageTransformer(true, new ViewPagerTransformer());
        // Move the mViewPager position to selected product position.
        mViewPager.setCurrentItem(currentPosition);
        // END setup_viewpager
    }

}
