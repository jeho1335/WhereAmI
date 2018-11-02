package com.jhmk.whereami.Module.Main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jhmk.whereami.Model.ConstVariables
import com.jhmk.whereami.Module.Home.HomeFragment
import com.jhmk.whereami.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
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
        mPresenter.onDestroy()
    }

    private fun initializeUi() {
        Log.d(TAG, "##### initializeUi #####")
        mPresenter = MainPresenter()
        mContetView = view_content_main as View
        handleFragment(ConstVariables.FRAGMENT_STATE_HOME)
    }

    fun String.appendWorld(): String {
        return this + "World!"
    }


    @SuppressLint("CommitTransaction")
    private fun handleFragment(state: Int) {
        Log.d(TAG, "##### handleFragment #####")
        var a = "Hello"
        a.appendWorld()
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