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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator_layout)
        initComponents()
        initUI()
    }

    private fun initUI(){
        setResultTextView()
    }

    private fun setResultTextView(){
        tvResult.text = "0"
    }

    fun pressNumber(view:View){
        if (view is Button){
            tvOperation.text = view.text
        }
    }

    fun pressOperator(view:View){
        if (view is Button && tvOperation.text.isNotEmpty()){
            tvOperation.text = view.text
        }
    }
    private fun initComponents(){
        tvOperation = findViewById(R.id.tvOperation)
        tvResult = findViewById(R.id.tvResult)
        btnEqual = findViewById(R.id.btnEqual)
    }

    private fun initListeners(){
        btnEqual.setOnClickListener { calculate() }
    }

    private fun clearEverything(){
        tvOperation.text = ""
        tvResult.text = ""
    }

    private fun calculate(): String {
        var result = 0

        return result.toString()
    }

}