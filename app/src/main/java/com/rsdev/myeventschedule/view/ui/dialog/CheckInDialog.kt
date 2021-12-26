package com.rsdev.myeventschedule.view.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.rsdev.myeventschedule.R
import com.rsdev.myeventschedule.databinding.DialogCheckInBinding
import com.rsdev.myeventschedule.utils.highorder.setupError

class CheckInDialog(context: Context, val callback: (name: String, email: String) -> Unit) :
    Dialog(context) {

    private lateinit var binding: DialogCheckInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCheckInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = binding.checkInName
        val email = binding.checkInEmail
        binding.btnCheckInSubmit.setOnClickListener {
            when {
                name.text.isNullOrEmpty() -> name.setupError(context.getString(R.string.error_empty_name))
                email.text.isNullOrEmpty() -> email.setupError(context.getString(R.string.error_empty_email))
                else -> {
                    callback(name.text.toString(), email.text.toString())
                }
            }
        }
    }
}