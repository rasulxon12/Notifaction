package uz.akra.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Image Click", Toast.LENGTH_SHORT).show()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.cancel(1)
    }
}