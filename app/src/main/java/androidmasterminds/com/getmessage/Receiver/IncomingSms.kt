package androidmasterminds.com.getmessage.Receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import androidmasterminds.com.getmessage.R
import androidmasterminds.com.getmessage.SplashActivity
import androidmasterminds.com.getmessage.Utilities.LogUtils



/*
*
* Broadcast receiver for pushing the notification when new message reads
*
* */

class IncomingSms : BroadcastReceiver() {
    // Get the object of SmsManager
    internal val sms = SmsManager.getDefault()

    override fun onReceive(context: Context, intent: Intent) {

        val bundle = intent.extras

        try {

            if (bundle != null) {

                val pdusObj = bundle.get("pdus") as Array<Any>

                for (i in pdusObj.indices) {

                    val currentMessage = SmsMessage.createFromPdu(pdusObj[i] as ByteArray)


                    val phoneNumber = currentMessage.displayOriginatingAddress
                    val message = currentMessage.displayMessageBody
                    LogUtils.makeLogD("SmsReceiver", "senderNum: $phoneNumber; message: $message")


                    GenerateNotification(phoneNumber, message, context)
                    // Show alert
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(context, "senderNum: $phoneNumber, message: $message", duration)
                    toast.show()

                }

            } // bundle is null

        } catch (e: Exception) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e)
        }

    }

    private fun GenerateNotification(senderNum: String, message: String, context: Context) {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context, SplashActivity::class.java)
        val pIntent = PendingIntent.getActivity(context, System.currentTimeMillis().toInt(), intent, 0)
        val notificationId = 1
        val channelId = "channel-01"
        val channelName = "Channel Name"
        val importance = NotificationManager.IMPORTANCE_HIGH

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                    channelId, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

        val mBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_app)
                .setContentTitle("" + senderNum)
                .setContentText("" + message).setSmallIcon(R.mipmap.ic_app)

        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntent(intent)
        val resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        mBuilder.setContentIntent(resultPendingIntent)

        notificationManager.notify(notificationId, mBuilder.build())


    }
}
