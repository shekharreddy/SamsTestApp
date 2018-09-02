package test.nsr.com.samstestapp.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author shekharreddy
 * Class to define Network related Util functions.
 */
public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    // Convert Input Stream to String.
    public static String convertInputStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return sb.toString();
    }
}
