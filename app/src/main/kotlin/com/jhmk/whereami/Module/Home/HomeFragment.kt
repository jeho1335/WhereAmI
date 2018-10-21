package com.jhmk.whereami.Module.Home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.jhmk.whereami.Module.Custom.ProgressDialog
import com.jhmk.whereami.Module.Custom.SelectLineDialog
import com.jhmk.whereami.R
import kotlinx.android.synthetic.main.layout_home_fragment.*
import org.jetbrains.anko.toast


class HomeFragment : Fragment(), Home.view {
    private val TAG = this.javaClass.simpleName
    private val mProgress = ProgressDialog()
    private lateinit var mPresenter: HomePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "##### onCreateView #####")
        return inflater.inflate(R.layout.layout_home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "##### onActivityCreated #####")
        super.onActivityCreated(savedInstanceState)
        initializeUi()
    }

    override fun onDestroyView() {
        Log.d(TAG, "##### onDestroyView #####")
        super.onDestroyView()
        mPresenter.onDestroy()
    }

    private fun initializeUi() {
        Log.d(TAG, "##### onActivityCreated #####")
        mPresenter = HomePresenter(this)
        fb_body.setOnClickListener(this::handleOnClick)
        fb_nearme.setOnClickListener(this::handleOnClick)
        fb_select_line.setOnClickListener(this::handleOnClick)
        fb_nearme.isClickable = false
        fb_select_line.isClickable = false
    }

    private fun bmListener(pos : Int){
        Log.d(TAG, "##### bmListener ##### pos : $pos")

    }

    private fun handleOnClick(v: View) {
        Log.d(TAG, "##### handleOnClick #####")
        when (v) {
            fb_body ->toggleFab()
            fb_nearme -> {
                toggleFab()
                mPresenter.requestNearbyStation(activity as Context)
                mProgress
                    .setTitle(resources.getString(R.string.string_request_gps_progress_title))
                    .show(activity?.fragmentManager, this.javaClass.simpleName)
            }
            fb_select_line -> {
                toggleFab()
                SelectLineDialog()
                        .setTitle("노선 선택")
                        .show(activity?.fragmentManager, this.javaClass.simpleName)
            }
        }
    }

    override fun onResultNearbyStation(isSuccess : Boolean, result: String?) {
        Log.d(TAG, "##### handleOnClick #####")
        mProgress.dismissAllowingStateLoss()
        when(isSuccess){
            true ->{
                if(result != null) {
                    txt_hello.text = result
                }
                activity?.toast(resources.getString(R.string.string_request_gps_success))
            }
            else -> {
                activity?.toast(resources.getString(R.string.string_request_gps_failed))
            }
        }
    }

    /*override fun onResultLineSelectButton(list: List<SelectLineButtonInfo>) {
        Log.d(TAG, "##### onResultLineSelectButton #####")
        for((index, value) in list.withIndex()){
            fb_select_line.addBuilder(TextInsideCircleButton.Builder().imageRect(Rect(Util.dp2px(1F), Util.dp2px(1F), Util.dp2px(1F), Util.dp2px(1F))).normalColorRes(value.color).normalText(value.name).textGravity(Gravity.CENTER).textSize(15).listener(this::bmListener))
            fb_select_line.customPiecePlacePositions.add(PointF(value.btnX / 20, value.btnY / 20))
            fb_select_line.customButtonPlacePositions.add(PointF(value.btnX, value.btnY))
        }
    }*/

    private fun toggleFab(){
        Log.d(TAG, "##### toggleFab #####")
        val openAnim = AnimationUtils.loadAnimation(activity, R.anim.fab_open)
        val closeAnim = AnimationUtils.loadAnimation(activity, R.anim.fab_close)
        if(fb_nearme.isClickable){
            fb_nearme.startAnimation(closeAnim)
            fb_select_line.startAnimation(closeAnim)
            fb_nearme.isClickable = false
            fb_select_line.isClickable = false
        }else{
            fb_nearme.startAnimation(openAnim)
            fb_select_line.startAnimation(openAnim)
            fb_nearme.isClickable = true
            fb_select_line.isClickable = true
        }

    }

}