package test.nsr.com.samstestapp.parser;

import test.nsr.com.samstestapp.products.datamodels.ProductResultsData;

/**
 * @author shekharreddy
 * Callbacks for data parsing success/failure
 */
public interface ParserListener {
    void onParseSuccess(ProductResultsData data);
    void onParseFailure();
}
