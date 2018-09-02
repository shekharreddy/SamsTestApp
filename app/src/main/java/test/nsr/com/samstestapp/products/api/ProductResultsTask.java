package test.nsr.com.samstestapp.products.api;

import test.nsr.com.samstestapp.network.APICommand;
import test.nsr.com.samstestapp.network.APICommandListener;
import test.nsr.com.samstestapp.network.NetworkResponseListener;
import test.nsr.com.samstestapp.network.NetworkTask;
import test.nsr.com.samstestapp.parser.ParserListener;
import test.nsr.com.samstestapp.parser.ProductsJSONParserTask;
import test.nsr.com.samstestapp.products.ProductsDataManager;
import test.nsr.com.samstestapp.products.datamodels.ProductResultsData;

/**
 * @author shekharreddy
 * Task to Manage products Data Request and Data Parsing.
 */
public class ProductResultsTask extends APICommand implements ParserListener {

    private ProductResultsData productResultsData;
    private int pageNumber;
    private static Exception genericException = new Exception("Error");

    public ProductResultsTask(APICommandListener listener, int pageNumber){
        super(listener);
        this.pageNumber = pageNumber;
    }

    public void execute(){
        new NetworkTask(mNetworkResponseListener, pageNumber).execute();

    }

    NetworkResponseListener mNetworkResponseListener = new NetworkResponseListener() {
        @Override
        public void onSuccess(String response) {
            if(response == null){
                notifyError(genericException);
            } else {
                new ProductsJSONParserTask(ProductResultsTask.this, response).execute();
            }
        }

        @Override
        public void onFailure() {
            notifyError(new Exception("Error"));
        }
    };


    @Override
    public void onParseSuccess(ProductResultsData data) {
        if(data == null || data.getProducts() == null ) {
            notifyError(genericException);
        } else {
            productResultsData = data;
            ProductsDataManager.getSingleTonInstance().setTotalNumberOfProducts(productResultsData.getTotalProducts());
            ProductsDataManager.getSingleTonInstance().setProductsList(productResultsData.getProducts());
            notifySuccess();
        }

    }
    public ProductResultsData getSearchResults(){
        return productResultsData;
    }

    @Override
    public void onParseFailure() {
        notifyError(genericException);
    }

}
