package test.nsr.com.samstestapp.network;

/**
 * @author shekharreddy
 * Base class for API commands which is an abstraction for commands that can be executed.
 */

public abstract class APICommand {
    private final String TAG = "APICommand";
    protected APICommandListener mListener;
    protected String mCommandName;

    /**
     * Constructor. This command will supply success or error to it's listener
     *
     * @param listener
     */
    protected APICommand(APICommandListener listener) {
        this.mListener = listener;
        this.mCommandName = getClass().getSimpleName();
    }

    /**
     * Notify the listener (if one was provided) that the Command was run successfully.
     */
    protected void notifySuccess() {
        if (mListener != null) {
            mListener.onCommandSuccess(this);
        }
    }

    /**
     * Notify the listener (if one was provided) that the Command encountered an error.
     *
     * @param e
     */
    protected void notifyError(Exception e) {
        String errorMessage = "";
        if (e != null) {
            errorMessage = e.getMessage();
        }
        if (mListener != null) {
            mListener.onCommandError(this, e);
        }
    }

    /**
     * Execute this command. You must provide a listener if you wish to be notified about the
     * success or failure of the Command.
     */
    public abstract void execute();

}
