package androidmasterminds.com.getmessage

import android.Manifest
import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import androidmasterminds.com.getmessage.BackgroundSync.DataBaseFeed
import androidmasterminds.com.getmessage.Utilities.LogUtils
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkStatus
import com.tbruyelle.rxpermissions2.RxPermissions

class SplashActivity : AppCompatActivity() {
    private lateinit var rxPermissions: RxPermissions
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mContext = this@SplashActivity
        rxPermissions = RxPermissions(this); // where this is an Activity or Fragment instance
        rxPermissions
                .request(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
                .subscribe { granted ->
                    if (granted) {
                        val compressionWork = OneTimeWorkRequest.Builder(DataBaseFeed::class.java)
                                         .build()
                        val compressionWorkId = compressionWork.getId()
                        WorkManager.getInstance().enqueue(compressionWork)
                        val status: LiveData<WorkStatus> = WorkManager.getInstance().getStatusById(compressionWorkId)


                    } else {
                        LogUtils.showToast((mContext as SplashActivity)!!, getString(R.string.alert_sms))
                    }
                }
        getHadler()
    }

    private fun getHadler() {

        Handler().postDelayed({



                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
                finish()


        }, 3000)


    }
}
