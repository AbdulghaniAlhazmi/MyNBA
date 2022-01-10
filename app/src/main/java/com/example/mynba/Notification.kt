package com.example.mynba

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val NOTIFICATION_ID = 1
const val CHANNEL_ID = "CHANNELID"
const val TITLE_KEY = "titleExtra"
const val MESSAGE_KEY = "messageExtra"

class Notification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(intent.getStringExtra(TITLE_KEY))
            .setContentText(intent.getStringExtra(MESSAGE_KEY))
            .build()

        val manger = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manger.notify(NOTIFICATION_ID,notification)
    }
}
