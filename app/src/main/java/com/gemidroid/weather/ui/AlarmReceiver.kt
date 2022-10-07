package com.gemidroid.weather.ui

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.gemidroid.weather.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED)
            context?.let {
                sendNotification(context, context.getString(R.string.channelId))
            }
    }

    private fun sendNotification(context: Context, channelId: String) {
        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            Notification.Builder(context, channelId)
        else Notification.Builder(context)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notification
            .setContentText("Alarm")
            .setContentTitle("New Weather")
            .setSmallIcon(R.drawable.ic_weather)
        notificationManager.notify(10002, notification.build())
    }
}
