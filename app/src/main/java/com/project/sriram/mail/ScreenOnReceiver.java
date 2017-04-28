package com.project.sriram.mail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class ScreenOnReceiver extends BroadcastReceiver {

	public static final String PASS = "PassFile";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent tmpInt = new Intent(context, LockScreenActivity.class);
		tmpInt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		SharedPreferences sp = context.getSharedPreferences(PASS, 0);
		sp.getString("c1", "null");
		if(!sp.getString("c1", "null").equals("null"))
			context.startActivity(tmpInt);
	}

}
