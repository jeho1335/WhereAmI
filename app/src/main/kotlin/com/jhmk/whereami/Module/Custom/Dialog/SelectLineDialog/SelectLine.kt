package com.jhmk.whereami.Module.Custom.Dialog.SelectLineDialog

import com.google.android.material.chip.Chip
import com.jhmk.whereami.Module.Base.Base

interface SelectLine {
    interface view{
        fun onResultSelectLine(result : String)
    }

    interface presenter : Base{
        fun requestSelectLine(chip : Chip)
    }
}