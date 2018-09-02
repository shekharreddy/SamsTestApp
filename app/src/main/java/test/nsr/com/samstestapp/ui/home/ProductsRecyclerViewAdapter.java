package test.nsr.com.samstestapp.ui.home;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import test.nsr.com.samstestapp.R;
import test.nsr.com.samstestapp.ui.utils.UIUtils;
import test.nsr.com.samstestapp.imageloader.ImageDownloader;
import test.nsr.com.samstestapp.network.APIUtils;
import test.nsr.com.samstestapp.products.datamodels.Product;
import test.nsr.com.samstestapp.ui.productdetails.ProductInfoActivity;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Product}
 */
public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "MyProductRecyclerViewAdapter";
    private final ArrayList<Product> mValues;
    private boolean isLoadingAdded = false;
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    public ProductsRecyclerViewAdapter(ArrayList<Product> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(view);
    }

    public void updateData(ArrayList<Product> nextPageData){
        int previousSize = mValues.size();
        mValues.addAll(nextPageData);
        notifyItemRangeChanged(previousSize, mValues.size()-1);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.productName.setText(holder.mItem.getProductName());
        float rating = holder.mItem.getReviewRating();
        if(rating > 0) {
            holder.productRating.setVisibility(View.VISIBLE);
            holder.productRating.setRating(holder.mItem.getReviewRating());
        } else {
            holder.productRating.setVisibility(View.INVISIBLE);
        }
        holder.productPrice.setText(holder.mItem.getPrice());
        holder.stockStatus.setText(holder.mItem.getInStock() ? R.string.in_stock : R.string.out_of_stock);
        String imageURL = String.format(APIUtils.PRODUCT_IMAGE_API, holder.mItem.getProductImage());
        ImageDownloader.downloadImage(imageURL, holder.productImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductInfoActivity.class);
                intent.putExtra(UIUtils.BUNDLE_PRODUCT_KEY, position);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mValues.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView productName;
        public final TextView stockStatus;
        public final TextView productPrice;
        public final RatingBar productRating;

        public Product mItem;
        public final ImageView productImage;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            productName = view.findViewById(R.id.product_name);
            productPrice = view.findViewById(R.id.product_price);
            productImage = view.findViewById(R.id.product_image);
            productRating = view.findViewById(R.id.product_rating);
            stockStatus = view.findViewById(R.id.stock_status);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + productPrice.getText() + "'";
        }
    }
}
