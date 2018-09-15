package androidmasterminds.com.getmessage

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout.VERTICAL
import androidmasterminds.com.getmessage.Adapters.MessagesAdapter
import androidmasterminds.com.getmessage.ViewModel.SmsViemodel
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

 private lateinit var mAdapter: MessagesAdapter
    private lateinit var viewModel: SmsViemodel

    private lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContext = this@MainActivity


        /*
        * Calling adapter constructor
        *
        * */
        mAdapter = MessagesAdapter(mContext)


        /*
        *
        * Constrcuting the viewmodel and obeser on that to update data
        * */

        viewModel = ViewModelProviders.of(this).get(SmsViemodel::class.java)



        // Creates a vertical Layout Manager using recyler view fo vertical list


        message_list.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        message_list.adapter = mAdapter
       // val resId = R.anim.layout_animfalldown
        //val animation = AnimationUtils.loadLayoutAnimation(mContext, resId)
        //message_list.layoutAnimation=animation
        subscribeUi(mAdapter)


        /*
        *
        * item divider for each item in list
        * */
        val itemDecor = DividerItemDecoration(mContext, VERTICAL)
        message_list.addItemDecoration(itemDecor)


        // Access the RecyclerView Adapter and load the data into it

    }

    private fun subscribeUi(mAdapter: MessagesAdapter) {

        viewModel.getSmsLiveData().observe(this, Observer { smslist ->
            if (smslist != null) {
                if (smslist.size != 0) {
                    mAdapter.submitList(smslist)
                    mAdapter.notifyDataSetChanged()

                }
            else {
                nodatafound_txt.visibility = View.VISIBLE
            }
        }



        })
    }


}
