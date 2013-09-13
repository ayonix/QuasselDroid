package com.iskrembilen.quasseldroid.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.iskrembilen.quasseldroid.R;
import com.iskrembilen.quasseldroid.events.ConnectionChangedEvent;
import com.iskrembilen.quasseldroid.gui.LoginActivity;
import com.iskrembilen.quasseldroid.io.QuasselDbHelper;
import com.iskrembilen.quasseldroid.service.CoreConnService;
import com.squareup.otto.Subscribe;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private boolean connected;
    private static final String TAG = NetworkChangeReceiver.class.getSimpleName();

    public NetworkChangeReceiver() {
        super();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Resources res = context.getResources();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        boolean preferenceReconnect = preferences.getBoolean(res.getString(R.string.preference_reconnect), false);
        boolean preferenceReconnectOnWifi = preferences.getBoolean(res.getString(R.string.preference_reconnect_on_wifi), false);

        final ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi =
                connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile =
                connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        Log.d(TAG, "Received connection activity");

        if (LoginActivity.connectIntent != null) {
            if (wifi.isConnected() && preferenceReconnect && !connected) {
                Log.d(TAG, "Reconnecting on Wifi");
                context.startService(LoginActivity.connectIntent);
            } else if (mobile.isConnected() && preferenceReconnect && !preferenceReconnectOnWifi && !connected) {
                Log.d(TAG, "Reconnecting (not Wifi)");
                context.startService(LoginActivity.connectIntent);
            }
        }
    }

    @Subscribe
    public void onConnectionChanged(ConnectionChangedEvent event) {
        if(event.status == ConnectionChangedEvent.Status.Disconnected) {
            connected = false;
        } else {
            connected = true;
        }
    }
}
