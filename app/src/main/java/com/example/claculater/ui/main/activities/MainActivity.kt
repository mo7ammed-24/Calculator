package com.example.claculater.ui.main.activities

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.example.claculater.base.BaseActivity
import com.example.claculater.data.AppInfo
import com.example.claculater.util.Operation
import com.example.claculater.databinding.ActivityMainBinding
import com.example.claculater.ui.main.brodcastreceiver.MyBrodcastReciver
import com.example.claculater.ui.main.service.AppLockService

class MainActivity: BaseActivity<ActivityMainBinding>() {
    private var lastNumber: Double? = null
    private var result :Double = 0.0
    private var currentOperation: Operation? = null
    private val operationMap = mapOf(
        Operation.PLUS to "+",
        Operation.MINUS to "-", Operation.MULTIPLICATION to "×", Operation.DIVISION to "÷", Operation.MODULES to "%")
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate
    var password =0

    override fun onCreate(savedInstanceState: Bundle?) {
        val myBrodcast = MyBrodcastReciver()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_BOOT_COMPLETED)
        filter.addAction(Intent.ACTION_SCREEN_ON)
        registerReceiver(myBrodcast, filter)
        if (!isAccessGranted()) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }
        val intent2 = Intent(this, AppLockService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForegroundService(intent2)
        else
            startService(intent2)

        val sharedPrefs = this.getSharedPreferences("Password_Sharing", MODE_PRIVATE)
        password = sharedPrefs.getInt("PASSWORD", 0)
        if (password==0){
            intent = Intent(this, PasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
        super.onCreate(savedInstanceState)
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("www", "this on restart")
    }


    override fun initialize() {
        // this is initialize function
    }

    override fun callBacks() {

        binding.plusButton .setOnClickListener {
            prepareOperation(Operation.PLUS)
        }
        binding.minusButton.setOnClickListener {
            prepareOperation(Operation.MINUS)
        }
        binding.muliplyButton.setOnClickListener {
            prepareOperation(Operation.MULTIPLICATION)
        }
        binding.divButton.setOnClickListener {
            prepareOperation(Operation.DIVISION)
        }
        binding.modulesButton.setOnClickListener {
            prepareOperation(Operation.MODULES)
        }
        binding.resultButton.setOnClickListener {
            if(binding.resultTextView.text.toString().toInt() == password){
                intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                binding.operationTextView.text = ""
                binding.resultTextView.text = ""
            }
            currentOperation?.let {
                if (lastNumber !=0.0 && binding.resultTextView .text.toString().toDouble() != 0.0){
                    result = doCurrentOperation()
                    binding.finalResult.text = result.toString().toIntOrDouble()
                }

                lastNumber = 0.0
            }
        }

        binding.deleteButton.setOnClickListener {
            clearInput()
            binding.operationTextView.text = ""
        }
    }

    private fun doCurrentOperation():Double{
        val secondNumber = binding.resultTextView.text.toString().toDoubleOrNull()
        Log.i("KKKKK","this is second : $secondNumber")

        return  when(currentOperation){
            Operation.PLUS -> lastNumber!! + secondNumber!!
            Operation.MINUS -> lastNumber!! - secondNumber!!
            Operation.MULTIPLICATION -> lastNumber!! * secondNumber!!
            Operation.DIVISION -> lastNumber!! / secondNumber!!
            Operation.MODULES -> lastNumber!! % secondNumber!!
            null -> 0.0
        }
    }


    fun onClickNumber(v:View){
        if (binding.resultTextView.text.toString().matches(Regex("^-?\\d+(\\.\\d+)?\$|^-?\\d+\\.\$")) || binding.resultTextView.text == "" ||  binding.resultTextView.text == "-" || binding.resultTextView.text == "0."){
                val newDigit = (v as Button).text.toString()
                val oldNumber = binding.resultTextView.text.toString()
                val newTextNumber = oldNumber + newDigit
                binding.resultTextView.text =newTextNumber
                prepareOperationText(newDigit)
            }
    }

    fun clearInput(){
        binding.resultTextView.text =""
        binding.finalResult.text = ""
        currentOperation = null
    }
    fun prepareOperation(operation: Operation){
        if(binding.resultTextView.text != "" && binding.resultTextView.text!="-"){
            if(binding.finalResult.text!=""){
                val temp1  = binding.finalResult.text.toString().toDoubleOrNull()
                lastNumber = temp1!!
                val temp2 = lastNumber.toString()+ operationMap.get(operation).toString()
                binding.operationTextView.text = temp2
            }
            else{
                val temp1  = binding.resultTextView.text.toString().toDoubleOrNull()
                lastNumber = temp1!!
                val temp2 = lastNumber.toString().toIntOrDouble() + operationMap.get(operation).toString()
                binding.operationTextView.text = temp2
            }

        }
        clearInput()
        if (lastNumber!=null)
            currentOperation = operation

    }

    fun onClickNegative(v: View){
        val negSign = "-"
        val oldNumber = binding.resultTextView.text.toString()
        val newTextNumber = negSign + oldNumber
        if(binding.resultTextView.text.toString().find {it=='-'} == '-')
            binding.resultTextView.text =oldNumber

        else if (binding.resultTextView.text.toString().matches(Regex("^-?\\d+(\\.\\d+)?\$|^-?\\d+\\.\$")) || binding.resultTextView.text == ""  || binding.resultTextView.text == "0."){
                binding.resultTextView.text =newTextNumber
                Log.i("KKKKK","this is : $newTextNumber")
                prepareOperationText(newTextNumber)
            }
            else { binding.resultTextView.text = newTextNumber
            binding.operationTextView.text = newTextNumber}
        }


    fun prepareOperationText(newTextNumber:String){
        if(currentOperation!=null && newTextNumber !="0."){
            if (newTextNumber=="-"){
               binding.operationTextView.text = binding.operationTextView.text.toString() + "($newTextNumber"
            }
            else if(binding.resultTextView.text.toString()!="" && newTextNumber.get(0) == '-'){
                binding.operationTextView.text=binding.operationTextView.text.slice(0..binding.operationTextView.text.toString().indexOfLast{it=='+' || it=='-'|| it=='÷' ||it == '×'||it=='%'})
                binding.operationTextView.text=binding.operationTextView.text.toString()+"($newTextNumber"
                result = doCurrentOperation()
                binding.finalResult.text = result.toString().toIntOrDouble()
            }
            else {
                binding.operationTextView.text = binding.operationTextView.text.toString() + newTextNumber
                result = doCurrentOperation()
                binding.finalResult.text = result.toString().toIntOrDouble()
            }

        }
        else {
            val temp = binding.operationTextView.text.toString()
            val temp2 = temp + newTextNumber
            binding.operationTextView.text = temp2
        }
    }

    fun onClickDot(v: View) {
        val oldNumber = binding.resultTextView.text.toString()
        if (binding.resultTextView.text.toString().find { it == '.' } == '.')
            binding.resultTextView.text = oldNumber
        else if (binding.resultTextView.text.toString() =="" || binding.resultTextView.text.toString() =="-") {
                prepareOperationText("0.")
            binding.resultTextView.text = "0."
            println(" this is ${binding.resultTextView.text}")

        }else {
            val negDot = "."
            val newTextNumber = oldNumber + negDot
            prepareOperationText(negDot)
            binding.resultTextView.text = newTextNumber
        }



    }

fun String.toIntOrDouble()=if(this.contains(".") && this.substringAfter(".").toInt() == 0) this.substringBefore(".").toInt().toString()  else this.toDouble().toString()

    fun isAccessGranted(): Boolean {
        return try {
            val packageManager = packageManager
            val applicationInfo: ApplicationInfo = packageManager.getApplicationInfo(packageName, 0)
            val appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode = if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
                appOpsManager.checkOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    applicationInfo.uid,
                    applicationInfo.packageName
                )
            } else {
                0
            }
            mode == AppOpsManager.MODE_ALLOWED
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
    fun getAllApps(context: Context): List<AppInfo> {
        val packageManager = context.packageManager
        val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val appList = mutableListOf<AppInfo>()

        for (appInfo in installedApps) {
            val appName = appInfo.loadLabel(packageManager).toString()
            val packageName = appInfo.packageName
            val icon = appInfo.loadIcon(packageManager)
            appList.add(AppInfo(appName, packageName, icon))
        }

        return appList
    }
}