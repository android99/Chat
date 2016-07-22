package com.hank.chat;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.hank.chat.backend.registration.Registration;

import java.io.IOException;

/**
 * Created by Administrator on 2016/7/22.
 */
public class RegistrationIntentService extends IntentService {
    private static final String TAG = "Registration";

    public RegistrationIntentService(){
        super("RegistrationIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken(getString(R.string.gcm_sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.d(TAG, "Token:"+token);
            sendRegistrationToServer(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRegistrationToServer(final String token) {
        final String name = getSharedPreferences("chat", MODE_PRIVATE)
                .getString("NAME", "John Doe");
        Log.d(TAG, "name:"+name);
        final Registration api = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("http://10.0.2.2:8080/_ah/api")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                        request.setDisableGZipContent(true);
                    }
                }).build();
        new Thread(){
            @Override
            public void run() {
                try {
                    api.register(name, token).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
