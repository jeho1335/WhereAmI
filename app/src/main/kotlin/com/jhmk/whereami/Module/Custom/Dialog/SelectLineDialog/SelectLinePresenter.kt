package com.jhmk.whereami.Module.Custom.Dialog.SelectLineDialog

import android.util.Log
import com.google.android.material.chip.Chip
import com.jhmk.whereami.Model.ConstVariables

class SelectLinePresenter(view : SelectLine.view) : SelectLine.presenter {
    private val TAG = this.javaClass.simpleName
    private val mView = view

    override fun onDestroy() {
        Log.d(TAG, "##### onDestroy #####")
    }

    override fun requestSelectLine(chip: Chip) {
        Log.d(TAG, "##### requestSelectLine #####")
        if(chip.tag.toString() == ConstVariables.LINE_UNKNOWN){
            mView.onResultSelectLine(ConstVariables.LINE_UNKNOWN)
        }else{
            mView.onResultSelectLine(chip.tag.toString())
        }

    }


}