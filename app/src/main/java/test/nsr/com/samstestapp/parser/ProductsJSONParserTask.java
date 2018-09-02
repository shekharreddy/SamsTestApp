package test.nsr.com.samstestapp.parser;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import test.nsr.com.samstestapp.products.datamodels.Product;
import test.nsr.com.samstestapp.products.datamodels.ProductResultsData;

/**
 *  JSON parsing Task for Product List.
 */
public class ProductsJSONParserTask extends AsyncTask<Void, Void, ProductResultsData> {
    private String networkResponse;
    private ParserListener mParserListener;

    public ProductsJSONParserTask(ParserListener mParserListener, String networkResponse){
        this.mParserListener = mParserListener;
        this.networkResponse = networkResponse;
    }

    //Sample Product JSON
//    "productId": "003e3e6a-3f84-43ac-8ef3-a5ae2db0f80e",
//            "productName": "Ellerton TV Console",
//            "shortDescription": "<p><span style=\"color:#FF0000;\"><b>White Glove Delivery Included</b></span></p>\n\n<ul>\n\t<li>Excellent for the gamer, movie enthusiest, or interior decorator in your home</li>\n\t<li>Built-in power strip for electronics</li>\n\t<li>Hardwood solids and cherry veneers</li>\n</ul>\n",
//            "longDescription": "<p>The Ellerton media console is well-suited for today&rsquo;s casual lifestyle. Its elegant style and expert construction will make it a centerpiece in any home. Soundly constructed, the Ellerton uses hardwood solids &amp; cherry veneers elegantly finished in a rich dark cherry finish. &nbsp;With ample storage for electronics &amp; media, it also cleverly allows for customization with three choices of interchangeable door panels.</p>\n",
//            "price": "$949.00",
//            "productImage": "/images/image2.jpeg",
//            "reviewRating": 2,
//            "reviewCount": 1,
//            "inStock": true

    @Override
    protected ProductResultsData doInBackground(Void ... x) {
        return parseJSONResponse(networkResponse);
    }

    protected void onPostExecute(ProductResultsData result) {
        mParserListener.onParseSuccess(result);
    }


    private ProductResultsData parseJSONResponse(String networkResponse){
        ArrayList<Product> productsList = new ArrayList<>();
        ProductResultsData mSearchResultsData = new ProductResultsData();

        try {
            JSONObject jsonObj = new JSONObject(networkResponse);
            int statusCode = jsonObj.getInt("statusCode");
            if(statusCode != 200){
                mParserListener.onParseFailure();
            }
            mSearchResultsData.setStatusCode(statusCode);
            mSearchResultsData.setPageNumber(jsonObj.getInt("pageNumber"));
            mSearchResultsData.setTotalProducts(jsonObj.getInt("totalProducts"));
            mSearchResultsData.setPageSize(jsonObj.getInt("pageSize"));

            // Getting JSON Array node
            JSONArray productsJSON = jsonObj.getJSONArray("products");

            // looping through All products
            for (int i = 0; i < productsJSON.length(); i++) {
                Product product = new Product();
                JSONObject c = productsJSON.getJSONObject(i);
                product.setProductId(c.optString("productId"));
                product.setProductImage(c.optString("productImage"));
                product.setProductName(c.optString("productName"));
                product.setLongDescription(c.optString("longDescription"));
                product.setShortDescription(c.optString("shortDescription"));
                product.setPrice(c.optString("price"));
                product.setReviewRating(c.getInt("reviewRating"));
                product.setReviewCount(c.getInt("reviewCount"));
                product.setInStock(c.getBoolean("inStock"));
                // adding product to product list
                productsList.add(product);
            }
            mSearchResultsData.setProducts(productsList);
        } catch (JSONException e){
            mParserListener.onParseFailure();
        } catch (Exception e){
            mParserListener.onParseFailure();
        }
        return mSearchResultsData;
    }
}
