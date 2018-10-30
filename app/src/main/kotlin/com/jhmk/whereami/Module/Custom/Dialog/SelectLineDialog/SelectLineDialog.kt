package com.jhmk.whereami.Module.Custom.Dialog.SelectLineDialog

import android.app.DialogFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.jhmk.whereami.R
import kotlinx.android.synthetic.main.layout_dialog_select_line.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SelectLineDialog : SelectLine.view, DialogFragment() {
    private val TAG = this.javaClass.simpleName
    private lateinit var mPresenter : SelectLine.presenter
    private lateinit var mListener: SelectedLine
    private var mProgressTitle = ""

    interface SelectedLine {
        fun onSelectedLine(result: String)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d(TAG, "##### onCreateView #####")
        dialog.window.attributes.windowAnimations = R.style.SelectDialogAnimation
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        dialog.setCanceledOnTouchOutside(true)
        return inflater.inflate(R.layout.layout_dialog_select_line, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "##### onActivityCreated #####")
        super.onActivityCreated(savedInstanceState)
        mPresenter = SelectLinePresenter(this)
        initializeUi()
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onResume() {
        Log.d(TAG, "##### onResume #####")
        super.onResume()
        val params = dialog.window.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.window.attributes = params as android.view.WindowManager.LayoutParams
    }

    override fun onDestroyOptionsMenu() {
        Log.d(TAG, "onDestroyOptionsMenu #####")
        mPresenter.onDestroy()
    }

    override fun onResultSelectLine(result: String) {
        Log.d(TAG, "onResultSelectLine ##### result : $result")
        mListener.onSelectedLine(result)
        dismissAllowingStateLoss()
    }

    private fun initializeUi() {
        Log.d(TAG, "##### initializeUi #####")
        txt_title_progress.text = mProgressTitle
        chip_group_select_line.isSingleSelection = true
        chip_group_select_line.setOnCheckedChangeListener(this::handleClickChip)
    }

    private fun handleClickChip(group: ChipGroup, checkedId: Int) {
        Log.d(TAG, "##### handleClickChip ##### checkedId : $checkedId")
        val mySelection = group.findViewById<Chip>(checkedId)
        mPresenter.requestSelectLine(mySelection)
    }

    fun setTitle(title: String): SelectLineDialog {
        Log.d(TAG, "##### setTitle #####")
        mProgressTitle = title
        return this
    }

    fun setChipClickListener(listener: SelectedLine): SelectLineDialog {
        Log.d(TAG, "##### setChipClickListener #####")
        this.mListener = listener
        return this
    }

}