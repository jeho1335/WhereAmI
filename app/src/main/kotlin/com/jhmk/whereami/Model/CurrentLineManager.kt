package com.jhmk.whereami.Model

import android.content.Context
import android.util.Log
import com.jhmk.whereami.R

class CurrentLineManager(val context: Context) {
    val TAG = this.javaClass.simpleName
    val lineInfoList: MutableList<LineInfo> = ArrayList()

    init {
        lineInfoList.add(LineInfo(R.color.color_line_1, context.resources.getString(R.string.line_1)))
        lineInfoList.add(LineInfo(R.color.color_line_2, context.resources.getString(R.string.line_2)))
        lineInfoList.add(LineInfo(R.color.color_line_3, context.resources.getString(R.string.line_3)))
        lineInfoList.add(LineInfo(R.color.color_line_4, context.resources.getString(R.string.line_4)))
        lineInfoList.add(LineInfo(R.color.color_line_5, context.resources.getString(R.string.line_5)))
        lineInfoList.add(LineInfo(R.color.color_line_6, context.resources.getString(R.string.line_6)))
        lineInfoList.add(LineInfo(R.color.color_line_7, context.resources.getString(R.string.line_7)))
        lineInfoList.add(LineInfo(R.color.color_line_8, context.resources.getString(R.string.line_8)))
        lineInfoList.add(LineInfo(R.color.color_line_9, context.resources.getString(R.string.line_9)))
        lineInfoList.add(LineInfo(R.color.color_line_10, context.resources.getString(R.string.line_10)))
        lineInfoList.add(LineInfo(R.color.color_line_11, context.resources.getString(R.string.line_11)))
        lineInfoList.add(LineInfo(R.color.color_line_12, context.resources.getString(R.string.line_12)))
        lineInfoList.add(LineInfo(R.color.color_line_13, context.resources.getString(R.string.line_13)))
        lineInfoList.add(LineInfo(R.color.color_line_14, context.resources.getString(R.string.line_14)))
        lineInfoList.add(LineInfo(R.color.color_line_15, context.resources.getString(R.string.line_15)))
        lineInfoList.add(LineInfo(R.color.color_line_16, context.resources.getString(R.string.line_16)))
        lineInfoList.add(LineInfo(R.color.color_line_17, context.resources.getString(R.string.line_17)))
        lineInfoList.add(LineInfo(R.color.color_line_18, context.resources.getString(R.string.line_18)))
        lineInfoList.add(LineInfo(R.color.color_line_19, context.resources.getString(R.string.line_19)))
        lineInfoList.add(LineInfo(R.color.color_line_20, context.resources.getString(R.string.line_20)))
        lineInfoList.add(LineInfo(R.color.color_line_21, context.resources.getString(R.string.line_21)))
        lineInfoList.add(LineInfo(R.color.color_line_22, context.resources.getString(R.string.line_22)))
        lineInfoList.add(LineInfo(R.color.color_line_23, context.resources.getString(R.string.line_23)))
        lineInfoList.add(LineInfo(R.color.color_line_unknown, context.resources.getString(R.string.line_unknown)))
    }

    fun getLineFilteredList(line : String?){
        Log.d(TAG, "##### getLineFilteredList #####")
    }
}