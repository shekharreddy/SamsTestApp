package test.nsr.com.samstestapp.ui.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

// UI Specific Utils
public class UIUtils {
    // Bundle key used in passing Product data.
    public final static String BUNDLE_PRODUCT_KEY = "product";

    //No of columns to show on Recycler View for Smartphones and large screen devices.
    public final static int XLARGE_SCREEN__LIST_COLUMN_COUNT = 2;

    //Page size for product list to load more data,
    // currently it is same for Tablet and mobile, but it can be changed based on screen size.
    public final static int PRODUCT_LIST_PAGE_SIZE = 25;

    public static Spanned getSpannedText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(text);
        }
    }

    // Check whether device has XLarge screen.
    public static boolean isXLargeDevice(Context context) {
        return context != null && (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    public static int getVisibleThreadHoldToLoadMore(Context context){
        if(isXLargeDevice(context)){
            return 10;
        } else {
            return 5;
        }

    }
}
