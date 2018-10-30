package com.jhmk.whereami.Module.Home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.jhmk.whereami.Module.Custom.Dialog.SelectLineDialog.SelectLineDialog
import com.jhmk.whereami.Module.Custom.ProgressDialog
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

    private fun bmListener(pos: Int) {
        Log.d(TAG, "##### bmListener ##### pos : $pos")

    }

    private fun handleOnClick(v: View) {
        Log.d(TAG, "##### handleOnClick #####")
        when (v) {
            fb_body -> toggleFab()
            fb_nearme -> {
                toggleFab()
                if (txt_current_line.text.toString().isNotEmpty()) {
                    showNearByStation(txt_current_line.text.toString())
                } else {
                    showSelectLineDialog()
                }
            }
            fb_select_line -> {
                toggleFab()
                showSelectLineDialog()
            }
        }
    }

    override fun onResultCurrentLocation(isSuccess: Boolean) {
        Log.d(TAG, "##### handleOnClick ##### isSuccess : $isSuccess")
        if(!isSuccess){
            activity?.toast(resources.getString(R.string.string_request_gps_failed))
        }
    }

    override fun onResultNearbyStation(isSuccess: Boolean, stName: String?, stLine: String?) {
        Log.d(TAG, "##### onResultNearbyStation $isSuccess $stName $stLine#####")
        val openAnim = AnimationUtils.loadAnimation(activity, R.anim.txt_open)
        mProgress.dismissAllowingStateLoss()
        when (isSuccess) {
            true -> {
                txt_current_line.text = stLine
                txt_nearby_station.text = stName
                activity?.toast(resources.getString(R.string.string_request_gps_success))
            }
            else -> {
                activity?.toast(resources.getString(R.string.string_request_network_failed))
            }
        }
        Handler().postDelayed({ txt_nearby_station.startAnimation(openAnim) }, 1000)
    }

    override fun onResultPrevNextStation(isSuccess: Boolean, prevName: String?, nextName: String?) {
        Log.d(TAG, "##### onResultPrevNextStation $isSuccess $prevName $nextName#####")
        val openAnim = AnimationUtils.loadAnimation(activity, R.anim.txt_open)
        txt_prestation.text = prevName
        txt_nextstation.text = nextName
        txt_prestation.startAnimation(openAnim)
        txt_nextstation.startAnimation(openAnim)
    }

    private val lineSelectListener = object : SelectLineDialog.SelectedLine {
        override fun onSelectedLine(result: String) {
            Log.d(TAG, "##### onSelectedLine ##### result : $result")
            showNearByStation(result)
        }
    }

    private fun showSelectLineDialog() {
        SelectLineDialog()
            .setTitle("어떤 노선에 타고 계세요?")
            .setChipClickListener(lineSelectListener)
            .show(activity?.fragmentManager, this.javaClass.simpleName)
    }

    private fun showNearByStation(stLine: String) {
        val closeAnim = AnimationUtils.loadAnimation(activity, R.anim.fab_close)
        mPresenter.requestNearbyStation(activity as Context, stLine)
        txt_nearby_station.startAnimation(closeAnim)
        txt_prestation.startAnimation(closeAnim)
        txt_nextstation.startAnimation(closeAnim)
        mProgress
            .setTitle(resources.getString(R.string.string_request_gps_progress_title))
            .show(activity?.fragmentManager, this.javaClass.simpleName)
    }

    private fun toggleFab() {
        Log.d(TAG, "##### toggleFab #####")
        val openAnim = AnimationUtils.loadAnimation(activity, R.anim.fab_open)
        val closeAnim = AnimationUtils.loadAnimation(activity, R.anim.fab_close)
        if (fb_nearme.isClickable) {
            fb_nearme.startAnimation(closeAnim)
            fb_select_line.startAnimation(closeAnim)
            fb_nearme.isClickable = false
            fb_select_line.isClickable = false
        } else {
            fb_nearme.startAnimation(openAnim)
            fb_select_line.startAnimation(openAnim)
            fb_nearme.isClickable = true
            fb_select_line.isClickable = true
        }

    }

}