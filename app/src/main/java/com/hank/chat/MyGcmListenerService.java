package com.hank.chat;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Administrator on 2016/7/22.
 */
public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG = "GCM";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        Log.d(TAG, "onMessageReceived: from:"+from);
        Log.d(TAG, "onMessageReceived: message:"+
            data.getString("message"));
    }
}
