package com.example.calculatoractivity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var tvOperation: TextView
    private lateinit var tvResult: TextView
    private lateinit var btnEqual: Button
    private lateinit var btnClear: Button

    private var canAddOperator:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator_layout)
        initComponents()
        initListeners()
    }
    private fun initComponents(){
        tvOperation = findViewById(R.id.tvOperation)
        tvResult = findViewById(R.id.tvResult)
        btnEqual = findViewById(R.id.btnEqual)
        btnClear = findViewById(R.id.btnClear)
    }

    private fun initListeners(){
        btnEqual.setOnClickListener { equalCalculate() }
        btnClear.setOnClickListener { clearEverything() }
    }

    fun pressNumber(btn:View){
        setResult()
        if ( btn is Button && btn.text == "0" && tvOperation.text == "" ){ return }
        if ( btn is Button ){
            tvOperation.append(btn.text)
            canAddOperator = true
        }
    }
    fun pressOperator(btn:View){
        if(!canAddOperator){ return }
        if (btn is Button){
            tvOperation.append(btn.text)
            canAddOperator = false
        }
    }


    fun clearEverything() {
        tvOperation.text = ""
        tvResult.text = ""
    }

    private fun setResult() {
        if (tvOperation.text.contains("X") || tvOperation.text.contains("+") ||
                    tvOperation.text.contains("-") || tvOperation.text.contains("/")) {
            calculate()
        }
        else{ return }
    }

    private fun calculate(){

    }
    private fun equalCalculate(){

    }
}