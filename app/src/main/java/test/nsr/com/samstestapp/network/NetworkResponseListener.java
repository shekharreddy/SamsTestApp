package test.nsr.com.samstestapp.network;

/**
 * @author shekharreddy
 * API Network response listener with Success/failure
 */
public interface NetworkResponseListener {
    public void onSuccess(String response);
    public void onFailure();
}
