package com.example.murtaza.walkietalkie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mActivity;

    public WifiDirectBroadcastReceiver(WifiP2pManager mManager, WifiP2pManager.Channel mChannel, MainActivity mActivity) {
        Log.i("class","test1");
        this.mManager = mManager;
        this.mChannel = mChannel;
        this.mActivity = mActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //Broadcast intent action to indicate whether Wi-Fi p2p is enabled or disabled.
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            Log.i("class","test2");
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Log.i("class","test3");
                Toast.makeText(context, "WIFI is On", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "WIFI is OFF", Toast.LENGTH_SHORT).show();
            }
            //Broadcast intent action indicating that the available peer list has changed.
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if (mManager != null) {
                Log.i("class","test4");
                mManager.requestPeers(mChannel, mActivity.peerListListener);
                Log.e("DEVICE_NAME", "WIFI P2P peers changed called");
            }//Broadcast intent action indicating that the state of Wi-Fi p2p connectivity has changed.
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            if (mManager == null) {
                return;
            }

            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            if (networkInfo != null && networkInfo.isConnected()) {
                Log.i("class","test5");
                mManager.requestConnectionInfo(mChannel, mActivity.connectionInfoListener);
            } else {
                Log.i("class","test6");
                mActivity.connectionStatus.setText("Device Disconnected");
                mActivity.clear_all_device_icons();
                mActivity.rippleBackground.stopRippleAnimation();
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            Log.i("class","test7");

        }
    }
}
