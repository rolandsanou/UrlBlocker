package com.roland.urlblocker.services;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ExampleService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
//    public class NetworkConnection extends BroadcastReceiver {
//        private Context _context;
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
//            this._context =context;
//            if(!noConnectivity) {
//                onConnectionFound();
//            } else {
//                onConnectionLost();
//            }
//        }
//
//        public void onConnectionLost() {
//            PreferenceHelper.getInstance(_context).setIsInternetConnection(false);
//        }
//
//        public void onConnectionFound() {
//            PreferenceHelper.getInstance(_context).setIsInternetConnection(true);
//        }
//    }

    private void TimeSettingReceiverStart(){
        UrlScreen screen = new UrlScreen();
        IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screen, screenStateFilter);
    }
}
