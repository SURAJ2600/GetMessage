package androidmasterminds.com.getmessage.Utilities

import android.support.v7.util.DiffUtil
import androidmasterminds.com.getmessage.Model.SmsModel

/**
 * Created by Suraj on 15/9/18.
 */

class SmsDiffCallback : DiffUtil.ItemCallback<SmsModel>() {

    override fun areItemsTheSame(oldItem: SmsModel, newItem: SmsModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SmsModel?, newItem: SmsModel?): Boolean {
        return oldItem == newItem
    }
}
