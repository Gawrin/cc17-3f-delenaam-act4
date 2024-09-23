package com.example.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var editTextBillAmount: EditText
    private lateinit var switchEnableTip: Switch
    private lateinit var radioGroupTipOptions: RadioGroup
    private lateinit var switchRoundTip: Switch
    private lateinit var buttonCalculate: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        editTextBillAmount = findViewById(R.id.editTextBillAmount)
        switchEnableTip = findViewById(R.id.switchEnableTip)
        radioGroupTipOptions = findViewById(R.id.radioGroupTipOptions)
        switchRoundTip = findViewById(R.id.switchRoundTip)
        buttonCalculate = findViewById(R.id.buttonCalculate)
        textViewResult = findViewById(R.id.textViewResult)

        // Set initial state
        radioGroupTipOptions.isEnabled = false
        switchRoundTip.isEnabled = false

        // Set listeners
        switchEnableTip.setOnCheckedChangeListener { _, isChecked ->
            radioGroupTipOptions.isEnabled = isChecked
            switchRoundTip.isEnabled = isChecked
        }

        buttonCalculate.setOnClickListener {
            calculateTip()
        }
    }

    private fun calculateTip() {
        val billAmount = editTextBillAmount.text.toString().toDoubleOrNull()
        if (billAmount == null) {
            textViewResult.text = getString(R.string.invalid_bill_amount)
            return
        }

        val tipPercentage = when {
            !switchEnableTip.isChecked -> 0.0
            else -> when (radioGroupTipOptions.checkedRadioButtonId) {
                R.id.radioButtonAmazing -> 0.20
                R.id.radioButtonGood -> 0.18
                R.id.radioButtonOk -> 0.15
                else -> 0.0 // Default to 0% if nothing is selected
            }
        }

        var tipAmount = billAmount * tipPercentage
        if (switchRoundTip.isChecked && switchEnableTip.isChecked) {
            tipAmount = ceil(tipAmount)
        }

        val totalAmount = billAmount + tipAmount

        val resultText = getString(R.string.result_format, tipAmount, totalAmount)
        textViewResult.text = resultText
    }
}