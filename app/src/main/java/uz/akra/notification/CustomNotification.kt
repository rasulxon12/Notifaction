package uz.akra.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_NO_CREATE
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import uz.akra.notification.databinding.ActivityCustomNotificationBinding

class CustomNotification : AppCompatActivity() {
    private val binding by lazy { ActivityCustomNotificationBinding.inflate(layoutInflater) }
    lateinit var notificationManager: NotificationManagerCompat
    var channelId = "1"

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        notificationManager = NotificationManagerCompat.from(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)


        }


        binding.buttonShow.setOnClickListener {
            notificationManager.notify(1, notifyBuilder())

        }

        binding.buttonHide.setOnClickListener {

            notificationManager.cancel(1)

        }

    }

    private fun notifyBuilder(): Notification {
        val collapsedView = RemoteViews(packageName, R.layout.notification_collapsed)
        val expandedView = RemoteViews(packageName, R.layout.notification_expanded)

        val clickIntent = Intent(this, NotificationReceiver::class.java)
        val clickPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(this, 0, clickIntent, FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(this, 0, clickIntent, FLAG_NO_CREATE or FLAG_IMMUTABLE)
        }

        collapsedView.setTextViewText(R.id.textViewCollapse, "This is Collapse!")

        expandedView.setImageViewResource(R.id.image_view_expanded,
            R.drawable.ic_launcher_background)
        expandedView.setOnClickPendingIntent(R.id.image_view_expanded, clickPendingIntent)

        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground).setCustomContentView(collapsedView)
            .setCustomBigContentView(expandedView)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle()).build()
    }
}