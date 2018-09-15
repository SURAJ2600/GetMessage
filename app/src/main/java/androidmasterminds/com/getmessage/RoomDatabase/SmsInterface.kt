package androidmasterminds.com.getmessage.RoomDatabase

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import androidmasterminds.com.getmessage.Model.SmsModel

/**
 * Created by Suraj on 15/9/18.
 */
@Dao
interface SmsInterface {

    @Query("select * from SmsData")
    fun get(): LiveData<List<SmsModel>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeller(task: SmsModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertall(order: ArrayList<SmsModel>)

    @Query("SELECT * FROM SmsData")
    fun getAllPaged(): DataSource.Factory<Int, SmsModel>

    @Query("delete from SmsData")
    fun deletall()



}
