package com.yunzhu.module.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

import com.yunzhu.module.ui.R
import kotlinx.android.synthetic.main.frame_empty_view.view.*


/**
 * @author:lisc
 * @date:2019-10-17
 */

class LoadingLayout : FrameLayout {

    private lateinit var contentView: View
    private var mReloadListener:(()->Unit)? = null
    private var mStatus:Status = Status.LOADING

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    fun init(context: Context) {
        contentView = View.inflate(context, R.layout.frame_empty_view, this)
        contentView.setOnClickListener {
            if(mStatus == Status.LOAD_FAILED || mStatus == Status.NETWORK_UNAVAILABLE){
                setState(Status.LOADING)
                mReloadListener?.invoke()
            }
        }
    }

    fun setState(status: Status) {
        when (status) {
            Status.DISMISS -> {
                contentView.visibility = View.VISIBLE
                emptyViewLoading!!.visibility = View.GONE
                emptyViewNoData!!.visibility = View.GONE
                emptyViewLoadFailed!!.visibility = View.GONE
                emptyViewNetworkUnavailable!!.visibility = View.GONE
            }
            Status.LOADING -> {
                contentView.visibility = View.GONE
                emptyViewLoading!!.visibility = View.VISIBLE
                emptyViewNoData!!.visibility = View.GONE
                emptyViewLoadFailed!!.visibility = View.GONE
                emptyViewNetworkUnavailable!!.visibility = View.GONE
            }
            Status.NO_DATA -> {
                contentView.visibility = View.GONE
                emptyViewLoading!!.visibility = View.GONE
                emptyViewNoData!!.visibility = View.VISIBLE
                emptyViewLoadFailed!!.visibility = View.GONE
                emptyViewNetworkUnavailable!!.visibility = View.GONE
            }
            Status.LOAD_FAILED -> {
                contentView.visibility = View.GONE
                emptyViewLoading!!.visibility = View.GONE
                emptyViewNoData!!.visibility = View.GONE
                emptyViewLoadFailed!!.visibility = View.VISIBLE
                emptyViewNetworkUnavailable!!.visibility = View.GONE
            }
            Status.NETWORK_UNAVAILABLE -> {
                contentView.visibility = View.GONE
                emptyViewLoading!!.visibility = View.GONE
                emptyViewNoData!!.visibility = View.GONE
                emptyViewLoadFailed.visibility = View.GONE
                emptyViewNetworkUnavailable!!.visibility = View.VISIBLE
            }
        }
        mStatus = status
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val childCount = childCount
        check(childCount <= 5) { "EmptyView can only have one child view" }
        if (childCount == 5) {
            contentView = getChildAt(4)
        }
        setState(Status.LOADING)
    }

    enum class Status {
        DISMISS, LOADING, NO_DATA, LOAD_FAILED, NETWORK_UNAVAILABLE
    }

    fun setOnReloadListener(listener:()->Unit){
        this.mReloadListener = listener
    }
}
