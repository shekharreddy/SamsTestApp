package test.nsr.com.samstestapp.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import test.nsr.com.samstestapp.R;
import test.nsr.com.samstestapp.ui.utils.UIUtils;
import test.nsr.com.samstestapp.network.APICommand;
import test.nsr.com.samstestapp.network.APICommandListener;
import test.nsr.com.samstestapp.products.ProductsDataManager;
import test.nsr.com.samstestapp.products.api.ProductResultsTask;
import test.nsr.com.samstestapp.products.datamodels.Product;
import test.nsr.com.samstestapp.ui.utils.RecyclerViewItemSpacingDecoration;
import test.nsr.com.samstestapp.ui.utils.RecyclerViewScrollListener;

/**
 * @author shekharreddy
 * A fragment representing a list of Products.
 */
public class ProductListFragment extends Fragment implements APICommandListener {
    private static final String TAG = "ProductListFragment";
    private RecyclerView recyclerView;
    private TextView errorTextView;
    private ProgressBar paginationProgressBar;
    private ProgressBar loadingProgressBar;

    private ProductsRecyclerViewAdapter mMyProductRecyclerViewAdapter;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private int currentPage = PAGE_START;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        initializeUI(view);
        loadData(PAGE_START);
        return view;
    }

    private RecyclerViewScrollListener mRecyclerViewScrollListener = new RecyclerViewScrollListener(getContext()) {
        @Override
        protected void loadMoreItems() {
            isLoading = true;
            currentPage += 1;
            if(currentPage <= getTotalPageCount()) {
                Log.i(TAG, "Load data for Page No " +currentPage);
                loadData(currentPage);
            }
        }

        @Override
        public int getTotalPageCount() {
            return TOTAL_PAGES;
        }

        @Override
        public boolean isLastPage() {
            return isLastPage;
        }

        @Override
        public boolean isLoading() {
            return isLoading;
        }
    };

    private void initializeUI(View view){
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);
        paginationProgressBar = view.findViewById(R.id.paginationProgressBar);
        loadingProgressBar = view.findViewById(R.id.progressBar);
        errorTextView = view.findViewById(R.id.errorDetails);

        if(UIUtils.isXLargeDevice(getContext())){
            recyclerView.setLayoutManager(new GridLayoutManager(context, UIUtils.XLARGE_SCREEN__LIST_COLUMN_COUNT));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        RecyclerView.ItemDecoration dividerItemDecoration = new RecyclerViewItemSpacingDecoration(getContext());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.addOnScrollListener(mRecyclerViewScrollListener);
    }

    private void loadData(int pageNumber){
        if(pageNumber > PAGE_START){
            showHidePaginationProgressbar(true);
        }
        new ProductResultsTask(ProductListFragment.this, pageNumber).execute();
    }

    private void hideShowLoadingProgressbar(boolean show){
        loadingProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCommandSuccess(APICommand c) {
        if(isVisible()) {
            if (currentPage == PAGE_START) {
                int totalProducts = ProductsDataManager.getSingleTonInstance().getTotalNumberOfProducts();
                TOTAL_PAGES = (int) Math.ceil((double) totalProducts / (double) UIUtils.PRODUCT_LIST_PAGE_SIZE);
            }
            updateUI(((ProductResultsTask) c).getSearchResults().getProducts());
            hideShowLoadingProgressbar(false);
        }
    }

    @Override
    public void onCommandError(APICommand c, Exception e) {
        Log.e(TAG, "Error in Getting Data for Page No " +currentPage);

        //TODO API specific error codes handling pending.
        // currently showing Generic Error for all types of Errors.
        if(isVisible()) {
            if (currentPage == PAGE_START) {
                showErrorUI();
                hideShowLoadingProgressbar(false);
            }
        }
    }

    // Show Error UI when API fails for first page
    private void showErrorUI(){
        errorTextView.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    // Show/hide Pagination progressBar on page bottom.
    private void showHidePaginationProgressbar(boolean show){
        paginationProgressBar.setVisibility(show ? View.VISIBLE: View.GONE);
    }

    private void updateUI(ArrayList<Product> resultsData){
        if(currentPage == PAGE_START) {
            mMyProductRecyclerViewAdapter = new ProductsRecyclerViewAdapter(resultsData);
            recyclerView.setAdapter(mMyProductRecyclerViewAdapter);
        } else {
            isLoading = false;
            mMyProductRecyclerViewAdapter.updateData(resultsData);
            showHidePaginationProgressbar(false);
        }
    }
}
