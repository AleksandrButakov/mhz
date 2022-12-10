package ru.anbn.mhz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("111111111111111111111111");
        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
    }
}
