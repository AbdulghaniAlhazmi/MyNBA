package com.example.mynba

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val notificationID = 1
const val ChanelId = "chanelid"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class Notification : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notification = context?.let {
            NotificationCompat.Builder(it, ChanelId)
                .setSmallIcon(R.drawable.ic_baseline_comment_24)
                .setContentTitle(intent?.getStringExtra(titleExtra))
                .setContentText(intent?.getStringExtra(messageExtra))
                .build()
        }

        val manger = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manger.notify(notificationID,notification)
    }
}