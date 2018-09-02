package test.nsr.com.samstestapp.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import test.nsr.com.samstestapp.ui.utils.UIUtils;

/**
 * @author shekharreddy
 * AysncTask to handle API requests
 * //TODO Error Handling Pending
 */
public class NetworkTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = "NetworkTask";

    private NetworkResponseListener mNetworkResponseListener;
    private int pageNumber;
    public NetworkTask(NetworkResponseListener mNetworkResponseListener, int pageNumber){
        this.pageNumber = pageNumber;
       this.mNetworkResponseListener = mNetworkResponseListener;
    }
    @Override
    protected String doInBackground(Void... voids) {
        HttpsURLConnection urlConnection = null;
        try {
            URL url = new URL(String.format(APIUtils.PRODUCT_API, pageNumber, UIUtils.PRODUCT_LIST_PAGE_SIZE));
            Log.d(TAG, url.toString());
            urlConnection = (HttpsURLConnection) url.openConnection();
            int statusCode = urlConnection.getResponseCode();
            Log.d(TAG, "response code: " + statusCode);

            if (statusCode == HttpsURLConnection.HTTP_OK){
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                return NetworkUtils.convertInputStreamToString(inputStream);
            } else {
                Log.e(TAG, "HTTP Error Status Code " + statusCode);
            }

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException " + e.getMessage());
        }
        finally {
            urlConnection.disconnect();
        }
        return null;
    }
    protected void onPostExecute(String result) {
        mNetworkResponseListener.onSuccess(result);
    }


}
