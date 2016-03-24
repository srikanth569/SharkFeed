package com.srikanth.sharkfeed.bus;

/**
 * Created by srikanth on 3/23/16.
 */
public class DownloadComplete {

    boolean success;

    public DownloadComplete(boolean _success) {
        success = _success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
