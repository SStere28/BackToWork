package com.SSI.Mms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class Animatie {

	public void start(Context context) {

		AlarmManager alarmMgr = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent serviceIntent = new Intent(context, Report.class);
		PendingIntent pi = PendingIntent.getService(context, 0, serviceIntent,
				0);
		alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				SystemClock.elapsedRealtime(), 1000, pi);

	}

}
