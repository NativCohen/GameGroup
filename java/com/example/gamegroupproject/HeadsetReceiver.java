package com.example.gamegroupproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class HeadsetReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        int state = intent.getIntExtra("state", -1); //0 - unplugged 1- plugged
        if (state==0){
            Toast.makeText(context, "Disconnect Headset ", Toast.LENGTH_SHORT).show();
        }
        else if (state==1){
            Toast.makeText(context, "Connected Headset" , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Unknown Mode Headset ", Toast.LENGTH_SHORT).show();

        }



    }
}