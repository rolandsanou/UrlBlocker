package com.roland.urlblocker.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.UUID;

public class UrlScreen extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        if (intentAction != null) {
            if (intentAction.equals("android.intent.action.SCREEN_ON")) {
                Date date = new Date();
                FormatHttpTime formatHttpTime = new FormatHttpTime();
//                ChildUsageActivity childUsageActivity = new ChildUsageActivity();
//                childUsageActivity.setId(null);
//                childUsageActivity.setChildDeviceId(UUID.fromString(preferenceHelper.getChildDeviceId()));
//                childUsageActivity.setStartTime(formatHttpTime.FormatHttpTime(date));
//                childUsageActivity.setStartTimeTick(date.getTime());
//                try {
//                    ChomarParentalLocalDatabase.getInstance(context).ChomarDatabaseDao().insertChildUsageActivity(childUsageActivity);
//                    context.sendBroadcast(new Intent(PreferenceHelper.getInstance(context).getStrTimeSettingReceiverStart()));
//                    Log.i(TAG, "ChildId: "+childUsageActivity.getStartTime()+" startTick:"+childUsageActivity.getStartTimeTick());
//                } catch (Exception ex) {
//                    Log.d(TAG, "onReceive: " + ex.getMessage());
//                }
//
//                preferenceHelper.setScreenTurnOnStartTime(childUsageActivity.getStartTimeTick());
            } else {
//                ChildUsageActivity childUsageActivity = ChomarParentalLocalDatabase.getInstance(context).ChomarDatabaseDao().getChildUsageActivity(preferenceHelper.getScreenTurnOnStartTime());
//                if (childUsageActivity != null) {
//                    Date date = new Date();
//                    FormatHttpTime formatHttpTime = new FormatHttpTime();
//                    childUsageActivity.setEndTime(formatHttpTime.FormatHttpTime(date));
//                    childUsageActivity.setEndTimeTick(date.getTime());
//                    try {
//                        ChomarParentalLocalDatabase.getInstance(context).ChomarDatabaseDao().updateChildUsageActivity(childUsageActivity);
//                        Log.i(TAG, "ChildId: "+childUsageActivity.getEndTime()+" endTick:"+childUsageActivity.getEndTimeTick());
//                    } catch (Exception ex) {
//                        Log.d(TAG, "onReceive: " + ex.getMessage());
//                    }
//
//                    if (PreferenceHelper.getInstance(context).isInternetConnection()) {
//                        ChildUsageActivityPostTask(context, childUsageActivity);
//                    }
//                }
            }
        }
    }
}
