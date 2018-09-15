package androidmasterminds.com.getmessage.Adapters

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidmasterminds.com.getmessage.Model.SmsModel
import androidmasterminds.com.getmessage.R
import androidmasterminds.com.getmessage.Utilities.SmsDiffCallback
import kotlinx.android.synthetic.main.header_layout.view.*
import kotlinx.android.synthetic.main.message_list.view.*

/**
 * Created by Suraj on 14/9/18.
 */


/*
*
* Paging adapter for recyler view
* */
class MessagesAdapter(val context: Context) : PagedListAdapter<SmsModel, RecyclerView.ViewHolder>(SmsDiffCallback()) {


    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    // Gets the number of animals in the list

    override fun getItemViewType(position: Int): Int {

        if (isPositionHeader(position)) {
            return TYPE_HEADER;

        } else {
            return TYPE_ITEM;
        }

    }

    private fun isPositionHeader(position: Int): Boolean {
        return getItem(position)?.isheader!!
    }


    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType === TYPE_ITEM) {
            val view = LayoutInflater.from(context).inflate(R.layout.message_list, parent, false)

            return ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.header_layout, parent, false)
            return HeaderViewHolder(view)

        }



        throw RuntimeException("there is no type that matches the type $viewType + make sure your using types correctly")


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var mSmsData = getItem(position)


        if (holder is HeaderViewHolder) {
            if (mSmsData?.isheader!!) {

                holder.mTitle.text =mSmsData.numberof_days
            } else {
                if(position==0) {
             //       holder.mTitle.text = "" + mSmsData.numberof_days + "" + mSmsData.hours
                }

            }
        }
            else
            {

            if (holder is ItemViewHolder) {

                //set the Value from List to corresponding UI component as shown below.
                holder.mMessagetitle_txt.text = "" + mSmsData?.number
                holder.mMessagebody_txt.text = "" + mSmsData?.body

                holder.mMessage_time.text=""+mSmsData?.date

                //similarly bind other UI components or perform operations
            }
        }

    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val mMessagetitle_txt = view.txt_number;
        val mMessagebody_txt = view.txt_mesagebody;
        val mMessage_time=view.txt_date;


    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val mTitle = view.txt_title;


    }
}