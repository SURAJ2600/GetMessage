package androidmasterminds.com.getmessage.Model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Suraj on 15/9/18.
 */
@Entity(tableName = "SmsData")
data  class SmsModel(@ColumnInfo(name = "id")
                     @PrimaryKey(autoGenerate = true)var id:Int,
                     @ColumnInfo(name = "number") var number:String,
                     @ColumnInfo(name = "body")var body:String,
                     @ColumnInfo(name = "date")var date:String,
                     @ColumnInfo(name = "numberof_days")var numberof_days:String,
                     @ColumnInfo(name = "isheader")var isheader:Boolean)
{
constructor():this(0,"","","","",false)
}