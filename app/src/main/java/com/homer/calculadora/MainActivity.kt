package com.homer.calculadora

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var resultado: TextView

    private lateinit var numberButtons: Array<MaterialButton>

    // Function buttons
    private lateinit var btnClear: MaterialButton
    private lateinit var btnMinus: MaterialButton // Rename for consistency
    private lateinit var btnPlus: MaterialButton  // Rename for consistency
    private lateinit var btnMultiply: MaterialButton // Rename for consistency
    private lateinit var btnEquals: MaterialButton  // Rename for consistency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initComponents()
        initListeners()
    }

    private fun initComponents(){
        resultado = findViewById(R.id.resultado)

        // Initialize number buttons array
        numberButtons = arrayOf(
            findViewById(R.id.btn_0),
            findViewById(R.id.btn_1),
            findViewById(R.id.btn_2),
            findViewById(R.id.btn_3),
            findViewById(R.id.btn_4),
            findViewById(R.id.btn_5),
            findViewById(R.id.btn_6),
            findViewById(R.id.btn_7),
            findViewById(R.id.btn_8),
            findViewById(R.id.btn_9)
        )

        // Function buttons
        btnClear = findViewById(R.id.btn_clear)
        btnMinus = findViewById(R.id.btn_menos)
        btnPlus = findViewById(R.id.btn_mas)
        btnMultiply = findViewById(R.id.btn_multiplicacion)
        btnEquals = findViewById(R.id.btn_igual)
    }

    private fun initListeners() {
        // Use a loop to set listeners for number buttons
        for (i in 0..9) {
            numberButtons[i].setOnClickListener {
                resultado.text = resultado.text.toString() + i
            }
        }

        // Function button listeners
        btnClear.setOnClickListener { resultado.text = "" }
        btnMinus.setOnClickListener { resultado.text = resultado.text.toString() + "-" }
        btnPlus.setOnClickListener { resultado.text = resultado.text.toString() + "+" }
        btnMultiply.setOnClickListener { resultado.text = resultado.text.toString() + "*" }
        btnEquals.setOnClickListener {
            try {
                resultado.text = calcular()
            } catch (e: IllegalArgumentException) {
                resultado.text = "Error" // Handle potential errors gracefully
            }
        }
    }

    private fun calcular(): String {
        val partes = resultado.text.split("\\+|\\-|\\*|\\/".toRegex())
        val operador = resultado.text.replace("[0-9]".toRegex(), "")

        if (partes.size != 2) {
            throw IllegalArgumentException("Invalid expression") // Handle invalid input
        }

        val operando1 = partes[0].toDoubleOrNull() ?: throw IllegalArgumentException("Invalid operand 1")
        val operando2 = partes[1].toDoubleOrNull() ?: throw IllegalArgumentException("Invalid operand 2")

        val resultado = when (operador) {
            "+" -> operando1 + operando2
            "-" -> operando1 - operando2
            "*" -> operando1 * operando2
            "/" -> {
                if (operando2 == 0.0) throw IllegalArgumentException("Division by zero")
                operando1 / operando2
            }
            else -> throw IllegalArgumentException("Unknown operator: $operador")
        }

        return String.format("%.0f", resultado) // Format the result to two decimal places
    }
}