package com.joel.forum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppBootReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		//uncomment for auto start
		/*
		if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			Intent i = new Intent(context, MainPageActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
*/
	}

}
