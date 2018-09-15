package androidmasterminds.com.getmessage.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import androidmasterminds.com.getmessage.Model.SmsModel
import androidmasterminds.com.getmessage.RoomDatabase.AppDatabase

/**
 * Created by Suraj on 15/9/18.
 */

class SmsViemodel(application: Application) : AndroidViewModel(application) {
    private var SmsLiveData: LiveData<PagedList<SmsModel>>


    init {
        val factory: DataSource.Factory<Int, SmsModel> =
                AppDatabase.getInstance(getApplication()).SmsDao().getAllPaged()

        val pagedListBuilder: LivePagedListBuilder<Int, SmsModel> = LivePagedListBuilder<Int, SmsModel>(factory,
                20)
        SmsLiveData = pagedListBuilder.build()
    }

    fun getSmsLiveData() = SmsLiveData


}
