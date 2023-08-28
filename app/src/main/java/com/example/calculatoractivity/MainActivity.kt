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
    private var decimalNumber: String = ""


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
            decimalNumber += btn.text
            canAddOperator = true
        }
        setResult()
    }

    fun pressOperator(btn: View) {
        if (btn is Button && btn.text == "," && decimalNumber.isNotEmpty() && !decimalNumber.contains(
                ","
            )
        ) {
            println(decimalNumber)
            tvOperation.append(btn.text)
            decimalNumber += btn.text
        }
        if (!canAddOperator) {
            return
        }
        if (btn is Button && btn.text != ",") {
            tvOperation.append(btn.text)
            canAddOperator = false
            decimalNumber = ""
        }
    }


    private fun clearEverything() {
        tvOperation.text = ""
        tvResult.text = ""
    }

    private fun setResult() {
        val result: String
        if (tvOperation.text.contains("X") || tvOperation.text.contains("+") ||
            tvOperation.text.contains("-") || tvOperation.text.contains("/")
        ) {
            result = calculate()
            tvResult.text = result
        }
    }

    private fun calculate(): String {
        val operations: MutableList<String> = mutableListOf()
        var currentOperator = ""

        for (idx in 0..tvOperation.text.length) {
            val chr: Char = tvOperation.text[idx]
            if (idx == tvOperation.text.length - 1) {
                currentOperator += chr.toString()
                operations.add(currentOperator)
                break
            }
            if (!chr.isDigit() && chr != ',') {
                operations.add(currentOperator)
                operations.add(chr.toString())
                currentOperator = ""
            }
            if (chr.isDigit() || chr == ',') {
                currentOperator += chr.toString()
            }
        }
        return makeMultDiv(operations)
    }

    private fun equalCalculate() {
        if (tvOperation.text[tvOperation.text.length - 1].isDigit() && (tvOperation.text.contains("X") || tvOperation.text.contains("+") ||
                    tvOperation.text.contains("-") || tvOperation.text.contains("/"))) {
            val result:String = calculate()
            tvOperation.text = result
            tvResult.text = ""
        }
    }

    private fun backspace() {
        if (tvOperation.text.isNotEmpty()) {
            tvOperation.text = tvOperation.text.substring(0, tvOperation.text.length - 1)
        }
    }

    private fun makeMultDiv(operation: MutableList<String>): String {
        val newList: MutableList<String> = mutableListOf()
        var prevDigit: Int = operation[0].toInt()
        var nextDigit: Int = operation[2].toInt()

        for (idx in 1 until operation.size step 2) {
            val chr = operation[idx]

            if (chr == "X" || chr == "/") {
                val result: Int = when (chr) {
                    "X" -> {
                        prevDigit * nextDigit
                    }

                    else -> {
                        prevDigit / nextDigit
                    }
                }

                if (newList.size == 0) {
                    newList.add(result.toString())
                }
                if (newList.size > 0) {
                    newList[newList.size - 1] = result.toString()
                }

                prevDigit = result

                if (operation.size - 2 == idx) {
                    break
                } else {
                    nextDigit = operation[idx + 3].toInt()
                }
            }

            if (chr == "+" || chr == "-") {
                if (idx == 1) {
                    newList.add(prevDigit.toString())
                }
                newList.add(chr)
                newList.add(nextDigit.toString())

                if (operation.size - 2 == idx) {
                    break
                } else {
                    prevDigit = nextDigit
                    nextDigit = operation[idx + 3].toInt()
                }
            }
        }
        return makeSumSub(newList)
    }

    private fun makeSumSub(newList: MutableList<String>): String {
        var finalResult: Int = newList[0].toInt()
        for (idx in 1 until newList.size step 2) {

            when (newList[idx]) {
                "+" -> {
                    finalResult += newList[idx + 1].toInt()
                }

                else -> {
                    finalResult -= newList[idx + 1].toInt()
                }
            }
        }
        return finalResult.toString()
    }
}

