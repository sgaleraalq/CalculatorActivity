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
    private lateinit var btnBackspace: Button

    private var canAddOperator: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator_layout)
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        tvOperation = findViewById(R.id.tvOperation)
        tvResult = findViewById(R.id.tvResult)
        btnEqual = findViewById(R.id.btnEqual)
        btnClear = findViewById(R.id.btnClear)
        btnBackspace = findViewById(R.id.btnBackspace)
    }

    private fun initListeners() {
        btnEqual.setOnClickListener { equalCalculate() }
        btnClear.setOnClickListener { clearEverything() }
        btnBackspace.setOnClickListener { backspace() }
    }

    fun pressNumber(btn: View) {
        if (btn is Button && btn.text == "0" && tvOperation.text == "") {
            return
        }
        if (btn is Button) {
            tvOperation.append(btn.text)
            canAddOperator = true
        }
        setResult()
    }

    fun pressOperator(btn: View) {
        if (!canAddOperator) {
            return
        }
        if (btn is Button) {
            tvOperation.append(btn.text)
            canAddOperator = false
        }
    }


    private fun clearEverything() {
        tvOperation.text = ""
        tvResult.text = ""
    }

    private fun setResult() {
        if (tvOperation.text.contains("X") || tvOperation.text.contains("+") ||
            tvOperation.text.contains("-") || tvOperation.text.contains("/")
        ) { calculate() }
    }

    private fun calculate() {
        var operations: MutableList<String> = mutableListOf()
        var currentOperator = ""

        for (chr in tvOperation.text) {
            println(chr)
            if (chr == tvOperation.text.last()){
                println("last")
                currentOperator += chr.toString()
                operations.add(currentOperator)
            }
            if (!chr.isDigit() && chr.toChar() != ',') {
                operations.add(currentOperator)
                operations.add(chr.toString())
                currentOperator = ""
            }
            if (chr.isDigit() || chr.toChar() == ',') {
                currentOperator += chr.toString()
            }
            println(operations)
        }
    }

    private fun equalCalculate() {
        if (tvOperation.text[tvOperation.text.length - 1].isDigit()) {
            setResult()
        }
    }

    private fun backspace() {
        if (tvOperation.text.isNotEmpty()) {
            tvOperation.text = tvOperation.text.substring(0, tvOperation.text.length - 1)
        }
    }
}