package test.nsr.com.samstestapp.ui.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 *
 * @author shekharreddy
 * Handle Pagination for Recycler view
 *
 */
public abstract class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private int VISIBLE_THRESHOLD;

    public RecyclerViewScrollListener(Context context){
        VISIBLE_THRESHOLD = UIUtils.getVisibleThreadHoldToLoadMore(context);
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int lastVisibleItemPosition = 0;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        }
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        }

        if (!isLoading() && !isLastPage()) {
            if(totalItemCount <= lastVisibleItemPosition + VISIBLE_THRESHOLD){
                loadMoreItems();
            }
        }

    }

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}
