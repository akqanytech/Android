package com.akqa.ny.tech.share;

import com.akqa.ny.tech.share.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static String TAG = "com.akqa";
	private class CfgChangeReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context c, Intent i) {
			Log.i(TAG, "Cfg change broadcast received!");
			int orientation = getBaseContext().getResources().getConfiguration().orientation;
			if (orientation == Configuration.ORIENTATION_PORTRAIT)
				Toast.makeText(getBaseContext(), "Portrait", Toast.LENGTH_SHORT).show();
			else { 
				if (orientation == Configuration.ORIENTATION_LANDSCAPE)
					Toast.makeText(getBaseContext(), "Landscape.", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getBaseContext(), "WTF?", Toast.LENGTH_SHORT).show();
			}
		}
	}
	private CfgChangeReceiver observerA;
	private class RFChangeReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context c, Intent i) {
			Log.i(TAG, "RF field broadcast received!");
		}
	}
	private RFChangeReceiver observerB;
	private IntentFilter filterA, filterB;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observerA = new CfgChangeReceiver();
    	filterA = new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED);
    	observerB = new RFChangeReceiver();
        filterB = new IntentFilter("com.android.nfc_extras.action.RF_FIELD_ON_DETECTED");
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onStart() {
    	super.onResume();
        Log.i(TAG, "Registering receivers!");
    	registerReceiver(observerA, filterA);
    	registerReceiver(observerB, filterB);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
        //Log.i(TAG, "Unregistering receivers!");
    	//unregisterReceiver(observerA);
    	//unregisterReceiver(observerB);
    }
}
