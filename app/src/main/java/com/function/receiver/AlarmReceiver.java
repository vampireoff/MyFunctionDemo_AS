package com.function.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * ÄÖÖÓ´¥·¢µÄ¹ã²¥½ÓÊÕÆ÷
 * @author Administrator
 *
 */
public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals("stop")) {
			context.unregisterReceiver(this);
		}else {
			context.sendBroadcast(new Intent("refresh"));
		}
	}

}
