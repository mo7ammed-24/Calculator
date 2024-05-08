package com.example.claculater

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    lateinit var buttonPlus : Button
    lateinit var buttonMinus: Button
    lateinit var buttonMultiplication :Button
    lateinit var buttonDiv : Button
    lateinit var buttonModules : Button
    lateinit var buttonResult :Button
    lateinit var buttonNegative:Button
    lateinit var textNumber : TextView
    lateinit var buttonClear : Button
    var lastNumber: Double = 0.0
    var result :Double = 0.0
    var currentOperation:Operation? = null
    lateinit var textOperation :TextView
    val operationMap = mapOf(Operation.PLUS to "+",Operation.MINUS to "-", Operation.MULTIPLICATION to "×", Operation.DIVISION to "÷", Operation.MODULES to "%")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        addCallBacks()
    }


    fun addCallBacks(){
        buttonPlus.setOnClickListener {
            prepareOperation(Operation.PLUS)
        }
        buttonMinus.setOnClickListener {
            prepareOperation(Operation.MINUS)
        }
        buttonMultiplication.setOnClickListener {
            prepareOperation(Operation.MULTIPLICATION)
        }
        buttonDiv.setOnClickListener {
            prepareOperation(Operation.DIVISION)
        }
        buttonModules.setOnClickListener {
            prepareOperation(Operation.MODULES)
        }
        buttonResult.setOnClickListener {
            currentOperation?.let {
                if (lastNumber !=0.0 && textNumber.text.toString().toDouble() != 0.0){
                    result = doCurrentOperation()
                    textNumber.text = result.toString()
                }

                lastNumber = 0.0
            }
        }


        buttonClear.setOnClickListener {
            clearInput()
            textOperation.text = ""
        }
    }

    private fun doCurrentOperation():Double{
        val secondNumber = textNumber.text.toString().toDouble()
        return  when(currentOperation){
            Operation.PLUS -> lastNumber + secondNumber
            Operation.MINUS -> lastNumber - secondNumber
            Operation.MULTIPLICATION -> lastNumber * secondNumber
            Operation.DIVISION -> lastNumber / secondNumber
            Operation.MODULES -> lastNumber % secondNumber
            null -> 0.0
        }
    }

    fun initView(){
        buttonPlus = findViewById(R.id.plusButton)
        buttonMinus = findViewById(R.id.minusButton)
        buttonMultiplication = findViewById(R.id.muliplyButton)
        buttonDiv = findViewById(R.id.divButton)
        textNumber = findViewById(R.id.resultTextView)
        buttonModules = findViewById(R.id.modulesButton)
        buttonResult = findViewById(R.id.resultButton)
        buttonClear = findViewById(R.id.deleteButton)
        buttonNegative = findViewById(R.id.negativeButton)
        textOperation = findViewById(R.id.operationTextView)
    }


    fun onClickNumber(v:View){
        var newDigit = (v as Button).text.toString()
        val oldNumber = textNumber.text.toString()
        val newTextNumber = oldNumber + newDigit
        textNumber.text =newTextNumber
        prepareOperationText(newDigit)
    }
    fun clearInput(){
        textNumber.text =""
    }
    fun prepareOperation(operation: Operation){
        if(textNumber.text != ""){

            val temp1  = textNumber.text.toString().toDoubleOrNull()
            lastNumber = temp1!!
            val temp2 = lastNumber.toString() + operationMap.get(operation).toString()
            textOperation.text = temp2

        }
        clearInput()
        currentOperation = operation

    }

    fun onClickNegative(v: View){
        val negSign = "-"
        val oldNumber = textNumber.text.toString()
        val newTextNumber = negSign + oldNumber
        textNumber.text =newTextNumber
    }

    fun prepareOperationText(newTextNumber:String){
        if(currentOperation!=null){
            textOperation.text = textOperation.text.toString() + newTextNumber
            result = doCurrentOperation()
            textNumber.text = result.toString()
            currentOperation = null
        }
        else {
            val temp = textOperation.text.toString()
            val temp2 = temp + newTextNumber
            textOperation.text = temp2
        }
    }

    fun onClickDot(v: View) {
        val oldNumber = textNumber.text.toString()
        if (textNumber.text.toString().find { it == '.' } == '.')
            textNumber.text = oldNumber
        else if (textNumber.text.toString() == "") {
            prepareOperationText("0.")
            textNumber.text = "0."

        } else {
            val negDot = "."
            val newTextNumber = oldNumber + negDot
            prepareOperationText(negDot)
            textNumber.text = newTextNumber
        }
    }

    fun checkPassword(){
        if(textNumber.text.toString() == ""){
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }


}