package com.example.unit_converter

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.unit_converter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sourceItems = resources.getStringArray(R.array.unit_items)
        val targetItems = resources.getStringArray(R.array.unit_items)

        val sourceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sourceItems)
        sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sourceUnit.adapter = sourceAdapter

        val targetAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, targetItems)
        targetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.targetUnit.adapter = targetAdapter

        val convertButton = binding.convertButton
        convertButton.setOnClickListener {
            performConversion()
        }
    }

    private fun performConversion() {
        val sourceUnit = binding.sourceUnit.selectedItem.toString()
        val targetUnit = binding.targetUnit.selectedItem.toString()
        val userInput = binding.userInputEditText.text.toString()

        if (userInput.isNotEmpty()) {
            val quantity = userInput.toDoubleOrNull()

            if (quantity != null) {
                val result = convert(quantity, sourceUnit, targetUnit)
                binding.conversionResult.text = getString(R.string.conversion_result_label, result)
            } else {
                binding.conversionResult.text = getString(R.string.invalid_input_message)
            }
        } else {
            binding.conversionResult.text = getString(R.string.empty_input_message)
        }
    }

    private fun convert(value: Double, sourceUnit: String, targetUnit: String): Double {
        val unitToGrams = mapOf(
            "Grams" to 1.0,
            "Kilograms" to 1000.0,
            "Milligrams" to 0.001,
            "Centigrams" to 0.01,
            "Decigrams" to 0.1,
            "Tons" to 1_000_000.0,
            "Pounds" to 453.59237,
            "Ounces" to 28.3495,
            "Stones" to 6350.293
        )

        return value * (unitToGrams[sourceUnit] ?: 1.0) / (unitToGrams[targetUnit] ?: 1.0)
    }
}
