package test.nsr.com.samstestapp.ui.productdetails;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import test.nsr.com.samstestapp.R;
import test.nsr.com.samstestapp.ui.utils.UIUtils;
import test.nsr.com.samstestapp.imageloader.ImageDownloader;
import test.nsr.com.samstestapp.network.APIUtils;
import test.nsr.com.samstestapp.products.ProductsDataManager;
import test.nsr.com.samstestapp.products.datamodels.Product;

/**
 * @author shekharreddy
 *
 * Simple {@link ProductInfoPagerAdapter} to show Product details which gives continuous feedback to the user
 * when scrolling.
 * // TODO Currently handling the data which is loaded on Home screen.
 */

public class ProductInfoPagerAdapter extends PagerAdapter {
    private TextView productName;
    private TextView productDescription;
    private TextView price;
    private RatingBar ratingBar;
    private ImageView productImage;
    private Context mContext;

    private Product product;

    /**
     * @return the number of pages to display
     */
    @Override
    public int getCount() {
        return ProductsDataManager.getSingleTonInstance().getProductsList().size();
    }

    /**
     * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
     * same object as the {@link View} added to the {@link ViewPager}.
     */
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    /**
     * Instantiate the {@link View} which should be displayed at {@code position}. Here we
     * inflate a layout from the apps resources and then change the text view to signify the position.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Inflate a new layout from our resources
        mContext = container.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_details_viewpager_item,
                container, false);
        // Add the newly created View to the ViewPager
        container.addView(view);
        initializeUI(view);
        product = ProductsDataManager.getSingleTonInstance().getProductsList().get(position);
        loadDataOnUI();
        // Return the View
        return view;
    }

    /**
     * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
     * {@link View}.
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    // Retrieve UI widgets from xml
    private void initializeUI(View view){
        productName = view.findViewById(R.id.product_name);
        ratingBar = view.findViewById(R.id.product_rating);
        productDescription = view.findViewById(R.id.product_desc);
        productImage = view.findViewById(R.id.product_image);
        price = view.findViewById(R.id.price);
    }

    //Show product info.
    private void loadDataOnUI(){
        productName.setText(product.getProductName());
        ratingBar.setRating(product.getReviewRating());
        updateProductDescription();
        updateStockStatusAndPrice();
        String imageURL = String.format(APIUtils.PRODUCT_IMAGE_API, product.getProductImage());
        ImageDownloader.downloadImage(imageURL, productImage);
    }

    // Update the Stock Status along with price using spannable text.
    private void updateStockStatusAndPrice(){
        StringBuilder stockAndPrice = new StringBuilder();
        String stockStatus = product.getInStock() ? mContext.getResources().getString(R.string.in_stock) : mContext.getResources().getString(R.string.out_of_stock);
        stockAndPrice.append(stockStatus);
        stockAndPrice.append("  ");
        stockAndPrice.append(product.getPrice());
        SpannableString spannableString = new SpannableString(stockAndPrice.toString());
        spannableString.setSpan(new TextAppearanceSpan(mContext, android.R.style.TextAppearance_Small), 0, stockStatus.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        price.setText(spannableString,TextView.BufferType.SPANNABLE);
    }

    // Update the product description with bold title.
    private void updateProductDescription(){
        StringBuilder desc = new StringBuilder();
        desc.append(mContext.getResources().getString(R.string.product_description));
        desc.append("\n\n");
        desc.append(UIUtils.getSpannedText(product.getShortDescription()));
        desc.append("\n");
        desc.append(UIUtils.getSpannedText(product.getLongDescription()));

        SpannableString spannableString = new SpannableString(desc.toString());
        // spannableString.setSpan(new StyleSpan(Typeface.BOLD),0,20,0);
        spannableString.setSpan(new TextAppearanceSpan(mContext, android.R.style.TextAppearance_Large), 0, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        productDescription.setText(spannableString,TextView.BufferType.SPANNABLE);
    }

}
