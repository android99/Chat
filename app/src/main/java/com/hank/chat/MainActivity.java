package com.hank.chat;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = new EditText(this);
        if (name==null){
            new AlertDialog.Builder(this)
                    .setTitle("Name")
                    .setView(editText)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            name = editText.getText().toString();
                            getSharedPreferences("chat", MODE_PRIVATE)
                                    .edit()
                                    .putString("NAME", name)
                                    .commit();
                            processToken();
                        }
                    }).show();
        }
    }

    private void processToken() {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getExtras().getString("message");
            Log.d("onReceive", msg);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(Message.ACTION_MESSAGE);
        registerReceiver(receiver, filter);
    }
}
