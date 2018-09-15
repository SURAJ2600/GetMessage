package androidmasterminds.com.getmessage.RoomDatabase

import android.arch.lifecycle.LiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import androidmasterminds.com.getmessage.BackgroundSync.DataBaseFeed
import androidmasterminds.com.getmessage.Model.SmsModel
import androidmasterminds.com.getmessage.Utilities.Util.Companion.DATBASE_NAME

import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkStatus

/**
 * Created by suraj on 30/7/18.
 */
@Database(entities = arrayOf(SmsModel::class), version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {
    abstract fun SmsDao(): SmsInterface

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                        ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATBASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            /*
                            * Woker manager
                            *
                            * */
                            val compressionWork = OneTimeWorkRequest.Builder(DataBaseFeed::class.java)
                                    .build()
                            val compressionWorkId = compressionWork.getId()
                            WorkManager.getInstance().enqueue(compressionWork)
                            val status: LiveData<WorkStatus> = WorkManager.getInstance().getStatusById(compressionWorkId)



                        }
                    })
                    .build()
        }
    }
}