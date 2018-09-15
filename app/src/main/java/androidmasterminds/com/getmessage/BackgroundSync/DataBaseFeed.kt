package androidmasterminds.com.getmessage.BackgroundSync

import android.net.Uri
import android.util.Log
import androidmasterminds.com.getmessage.Model.SmsModel
import androidmasterminds.com.getmessage.RoomDatabase.AppDatabase
import androidmasterminds.com.getmessage.Utilities.LogUtils
import androidx.work.Worker
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Suraj on 15/9/18.
 */


/*
*
* Worker manager class for updating the databse in background
* */

class DataBaseFeed : Worker() {
    private lateinit var rxPermissions: RxPermissions
    private val TAG = DataBaseFeed::class.java.simpleName
    private val TYPE_INCOMING_MESSAGE = 1
    private var isHeader: Boolean = false
    var formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    var formatters = SimpleDateFormat("dd/MM/yyyy");

    var app_database: AppDatabase? = null

    override fun doWork(): WorkerResult {

        /*
        *
        * Worker class for fetching data from background when the user open the application,
        *
        *
        *
        *
        *
        */


        try {


            app_database = AppDatabase.getInstance(applicationContext)
            FecthMessage();

        } catch (e: Exception) {
            e.printStackTrace()
            return WorkerResult.FAILURE
        }


        return WorkerResult.SUCCESS
    }


    /*
    *
    * Fetch the message using rxjava for async
    * */
    private fun FecthMessage() {
        Observable.fromCallable<ArrayList<SmsModel>> {

            fetchInboxSms(TYPE_INCOMING_MESSAGE)
                                                   }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe { result ->
                    if (result != null) {
                        app_database?.SmsDao()?.deletall()
                        if(result.size!=0) {
                            for (i in 0..result.size - 1) {
                                app_database?.SmsDao()?.insertall(result)
                                LogUtils.makeLogD(TAG, ">>>>" + result.get(i))
                            }
                        }
                    }


                }

    }


    /*
    *
    * Getting data from content provider
    * */

    fun fetchInboxSms(type: Int): ArrayList<SmsModel> {
        val smsInbox = ArrayList<SmsModel>()

        val uriSms = Uri.parse("content://sms")

        val cursor = applicationContext.contentResolver
                .query(uriSms,
                        arrayOf("_id", "address", "date", "body"), null, null,
                        "date" + " DESC" + " LIMIT 50")
        //" LIMIT 2"

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                if (cursor.count > 0) {
                    var text = ""

                    for (i in 0..cursor.count - 1) {

                        if(cursor.getString(cursor
                                .getColumnIndex("address"))==null)
                        {

                        }
                        else if(cursor.getString(cursor
                                .getColumnIndex("body"))==null)
                        {

                        }
                        else {


                            var text_temp = ""
                            var date = cursor.getString(2);
                            var calendar = Calendar.getInstance();
                            calendar.setTimeInMillis((date).toLong());
                            var finalDateString = formatter.format(calendar.getTime());
                            Log.e("test date format ", "" + finalDateString);

                            /*Compare the date from current and get the time difference and set the header type*/
                            val c = Calendar.getInstance().time
                            println("Current time => " + c)
                            val formattedDate = formatter.format(c)
                            var date1 = formatter.parse(formattedDate);
                            var date2 = formatter.parse(finalDateString)
                            var different = date1.getTime() - date2.getTime()
                            val secondsInMilli: Long = 1000
                            val minutesInMilli = secondsInMilli * 60
                            val hoursInMilli = minutesInMilli * 60
                            val daysInMilli = hoursInMilli * 24
                            var message_date = formatters.format(calendar.getTime())
                            val elapsedDays = different / daysInMilli
                            different = different % daysInMilli
                            val elapsedHours = different / hoursInMilli
                            different = different % hoursInMilli

                            LogUtils.makeLogD("daya" + elapsedDays, "hoours" + elapsedHours)


                            if (elapsedDays != 0.toLong()) {
                                text_temp = "" + elapsedDays + " days ago"
                            } else if (elapsedHours != 0.toLong()) {
                                text_temp = "" + elapsedHours + " hours ago"
                            } else {
                                text_temp = "few minutes ago"
                            }


                            if (!text.equals(text_temp)) {
                                text = text_temp
                                isHeader = true

                                val message = SmsModel(0, cursor.getString(cursor
                                        .getColumnIndex("address")), cursor.getString(cursor
                                        .getColumnIndex("body")), message_date, "" + text, isHeader);
                                smsInbox.add(message)
                                val message1 = SmsModel(0, cursor.getString(cursor
                                        .getColumnIndex("address")), cursor.getString(cursor
                                        .getColumnIndex("body")), message_date, text, false);
                                smsInbox.add(message1)
                            } else {
                                isHeader = false
                                val message = SmsModel(0, cursor.getString(cursor
                                        .getColumnIndex("address")), cursor.getString(cursor
                                        .getColumnIndex("body")), message_date, text, isHeader);
                                smsInbox.add(message)
                            }


                            cursor.moveToNext();
                        }
                    }
                }

            }
        }

        return smsInbox

    }

}