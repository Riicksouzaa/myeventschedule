package com.rsdev.myeventschedule.view.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.rsdev.myeventschedule.databinding.DialogLoadingBinding

class LoadingDialog(context: Context, private val message: String) : Dialog(context) {
    private lateinit var binding: DialogLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loadDialogText.text = message

        this.setCancelable(false)
        this.window?.setBackgroundDrawable(null)
    }
}