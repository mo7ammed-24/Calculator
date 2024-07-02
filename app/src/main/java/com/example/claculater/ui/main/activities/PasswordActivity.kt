package com.example.claculater.ui.main.activities

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.core.widget.doAfterTextChanged
import com.example.claculater.base.BaseActivity
import com.example.claculater.data.DataManger
import com.example.claculater.databinding.ActivityCreatePasswordBinding

class PasswordActivity:BaseActivity<ActivityCreatePasswordBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityCreatePasswordBinding = ActivityCreatePasswordBinding::inflate
    override fun initialize() {
        val sharedPrefsPattern = this.getSharedPreferences(DataManger.PATTERN_SHARING, MODE_PRIVATE)
        DataManger.patterCreated = sharedPrefsPattern.getBoolean(DataManger.PATTERN_CREATED, false)
        //nothing to do
    }

    override fun callBacks() {
        binding.inputRewritePassword.doAfterTextChanged {
            binding.buttonSubmit.isEnabled = true
        }
        binding.buttonSubmit.setOnClickListener{
            if (binding.inputCreatePassword.text.toString() == binding.inputRewritePassword.text.toString()){

                DataManger.userPassword=binding.inputRewritePassword.text.toString().toInt()
                val sharedPrefs = this.getSharedPreferences("Password_Sharing", Context.MODE_PRIVATE)
                val editor = sharedPrefs.edit()
                editor.putInt("PASSWORD", DataManger.userPassword!!)
                editor.apply()

                if (DataManger.patterCreated){
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                else{
                    val intent = Intent(this, LockActivity::class.java)
                    intent.putExtra("args", 1)
                    startActivity(intent)
                    finish()
                }
            }
            else if(binding.inputCreatePassword.text.toString()!="" && binding.inputRewritePassword.text.toString()!=""){
                binding.textNotes.text = "Password Not Match"
            }

            else {
                binding.textNotes.text = "Please Create Your Password"
            }
        }
    }
}