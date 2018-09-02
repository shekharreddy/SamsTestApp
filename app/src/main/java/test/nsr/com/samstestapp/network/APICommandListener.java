package test.nsr.com.samstestapp.network;

/**
 * @author shekharreddy
 * Defines an interface for a APICommandListener to implement.
 */
public interface APICommandListener {

    // Call back for API command success response
    void onCommandSuccess(APICommand c);

    // Call back for API command failure with exception
    void onCommandError(APICommand c, Exception e);
}
