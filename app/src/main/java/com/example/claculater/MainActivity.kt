package com.example.claculater

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.claculater.databinding.ActivityMain2Binding
import com.example.claculater.databinding.ActivityMainBinding
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    private var lastNumber: Double = 0.0
    private var result :Double = 0.0
    private var currentOperation:Operation? = null
    private val operationMap = mapOf(Operation.PLUS to "+",Operation.MINUS to "-", Operation.MULTIPLICATION to "×", Operation.DIVISION to "÷", Operation.MODULES to "%")

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addCallBacks()
    }


    fun addCallBacks(){

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
                intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }
            currentOperation?.let {
                if (lastNumber !=0.0 && binding.resultTextView .text.toString().toDouble() != 0.0){
                    result = doCurrentOperation()
                    binding.resultTextView.text = result.toString()
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
        val secondNumber = binding.resultTextView.text.toString().toDouble()
        return  when(currentOperation){
            Operation.PLUS -> lastNumber + secondNumber
            Operation.MINUS -> lastNumber - secondNumber
            Operation.MULTIPLICATION -> lastNumber * secondNumber
            Operation.DIVISION -> lastNumber / secondNumber
            Operation.MODULES -> lastNumber % secondNumber
            null -> 0.0
        }
    }


    fun onClickNumber(v:View){
        if (binding.resultTextView.text.toString().matches(Regex("^-?\\d+\$")) || binding.resultTextView.text == "" ||  binding.resultTextView.text == "-" || binding.resultTextView.text == "0."){
                val newDigit = (v as Button).text.toString()
                val oldNumber = binding.resultTextView.text.toString()
                val newTextNumber = oldNumber + newDigit
                binding.resultTextView.text =newTextNumber
                prepareOperationText(newDigit)
            }
    }

    fun clearInput(){
        binding.resultTextView.text =""
    }
    fun prepareOperation(operation: Operation){
        if(binding.resultTextView.text != ""){

            val temp1  = binding.resultTextView.text.toString().toDoubleOrNull()
            lastNumber = temp1!!
            val temp2 = lastNumber.toString() + operationMap.get(operation).toString()
            binding.operationTextView.text = temp2

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

        else binding.resultTextView.text = newTextNumber

    }

    fun prepareOperationText(newTextNumber:String){
        if(currentOperation!=null && newTextNumber !="0."){
            binding.operationTextView.text = binding.operationTextView.text.toString() + newTextNumber
            result = doCurrentOperation()
            binding.resultTextView.text = result.toString()
            currentOperation = null
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
        else if (binding.resultTextView.text.toString() == "") {
            prepareOperationText("0.")
            binding.resultTextView.text = "0."

        } else {
            val negDot = "."
            val newTextNumber = oldNumber + negDot
            prepareOperationText(negDot)
            binding.resultTextView.text = newTextNumber
        }
    }

    fun checkPassword(){
        if(binding.resultTextView.text.toString() == ""){
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }


}