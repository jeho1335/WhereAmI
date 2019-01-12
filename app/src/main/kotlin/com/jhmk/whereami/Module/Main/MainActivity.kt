package com.jhmk.whereami.Module.Main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.jhmk.whereami.Model.ConstVariables
import com.jhmk.whereami.Module.Base.BaseActivity
import com.jhmk.whereami.Module.Home.HomeFragment
import com.jhmk.whereami.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), Main.view {
    val TAG = this.javaClass.simpleName
    private lateinit var mPresenter: MainPresenter
    private lateinit var mContetView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "##### onCreate #####")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUi()
    }

    override fun onDestroy() {
        Log.d(TAG, "##### onDestroy #####")
        super.onDestroy()
    }

    override fun onResultRequestCheckPermission(msg: String) {
        Log.d(TAG, "##### onResultRequestCheckPermission #####")
    }

    private fun initializeUi() {
        Log.d(TAG, "##### initializeUi #####")
        mPresenter = MainPresenter(this)
        mContetView = view_content_main as View
        handleFragment(ConstVariables.FRAGMENT_STATE_HOME)
        setNetworkThreadPolicy()
        setRuntimePermission()
    }

    private fun setNetworkThreadPolicy(){
        Log.d(TAG, "##### setNetworkThreadPolicy #####")
        mPresenter.checkNetworkThreadPolicy()
    }

    private fun setRuntimePermission(){
        Log.d(TAG, "##### setRuntimePermission ####")
        mPresenter.requestCheckPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @SuppressLint("CommitTransaction")
    private fun handleFragment(state: Int) {
        Log.d(TAG, "##### handleFragment #####")
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        var fr = Fragment()

        when (state) {
            ConstVariables.FRAGMENT_STATE_HOME -> {
                fr = HomeFragment()
            }
        }
        if (fr.isAdded) {
            ft.show(fr)
        } else {
            ft.replace(mContetView.id, fr, fr.javaClass.simpleName)
        }
        ft.commit()
    }

}