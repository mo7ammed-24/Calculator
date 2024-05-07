package com.example.claculater

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
    var currentOperation:Operation? = null
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

            if (lastNumber !=0.0 && textNumber.text.toString().toDouble() != 0.0){
                val result = doCurrentOperation()
                textNumber.text = result.toString()
            }

            lastNumber = 0.0
        }


        buttonClear.setOnClickListener {
            clearInput()
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
        buttonModules = findViewById(R.id.modulesButton)
        buttonResult = findViewById(R.id.resultButton)
        textNumber = findViewById(R.id.resultTextView)
        buttonClear = findViewById(R.id.deleteButton)
        buttonNegative = findViewById(R.id.negativeButton)
    }


    fun onClickNumber(v:View){
        var newDigit = (v as Button).text.toString()
        val oldNumber = textNumber.text.toString()
        val newTextNumber = oldNumber + newDigit
        textNumber.text =newTextNumber
    }
    fun clearInput(){
        textNumber.text =""
    }

    fun prepareOperation(operation: Operation){
        if(textNumber.text != ""){
            lastNumber = textNumber.text.toString().toDouble()
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

}