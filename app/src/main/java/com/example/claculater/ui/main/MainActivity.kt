package com.example.claculater.ui.main

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.example.claculater.base.BaseActivity
import com.example.claculater.util.Operation
import com.example.claculater.databinding.ActivityMainBinding

class MainActivity: BaseActivity<ActivityMainBinding>() {

    private var lastNumber: Double = 0.0
    private var result :Double = 0.0
    private var currentOperation: Operation? = null
    private val operationMap = mapOf(
        Operation.PLUS to "+",
        Operation.MINUS to "-", Operation.MULTIPLICATION to "×", Operation.DIVISION to "÷", Operation.MODULES to "%")
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate


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
            if(binding.resultTextView.text =="2244"){
                intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
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
            Operation.PLUS -> lastNumber + secondNumber!!
            Operation.MINUS -> lastNumber - secondNumber!!
            Operation.MULTIPLICATION -> lastNumber * secondNumber!!
            Operation.DIVISION -> lastNumber / secondNumber!!
            Operation.MODULES -> lastNumber % secondNumber!!
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

}