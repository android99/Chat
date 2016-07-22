package com.hank.chat;

import android.content.Intent;
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
        String msg = data.getString("message");
        Log.d(TAG, "onMessageReceived: from:"+from);
        Log.d(TAG, "onMessageReceived: message:"+ msg
            );
        Bundle bundle = new Bundle();
        bundle.putString("message", msg);
        Intent intent = new Intent(Message.ACTION_MESSAGE);

        intent.putExtras(bundle);
        sendBroadcast(intent);
    }
}
