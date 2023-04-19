package com.Trx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.Trx.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMI_Activity : AppCompatActivity() {

    private var binding : ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBMI)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI"
        }

        binding?.toolbarBMI?.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            if(validateMetricUnits()){
                val HeightValue : Float = binding?.EtMetricHeight.toString().toFloat()/100
                val WeightValue : Float = binding?.EtMetricHeight.toString().toFloat()
                val BMI = WeightValue/(HeightValue*HeightValue)
                displayBMIResult(BMI)
            }
            else
                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
        }

    }

    private fun displayBMIResult(bmi: Float){

        val bmiLabel : String
        val bmiDescription : String

        when (bmi) {
            in 0f..18.5f -> {
                bmiLabel = "Underweight"
                bmiDescription = "You have to get some Gains so Eat!!!!"
            }
            in 18.5f .. 25f -> {
                bmiLabel = "Normal"
                bmiDescription = "You have a normal BMI :)"
            }
            else -> {
                bmiLabel = "Overweight"
                bmiDescription = "You should maintain a healthy diet"
            }
        }

        val BmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDiplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text =BmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }

    private fun validateMetricUnits():Boolean {
        var isValid = true
        if(binding?.EtMetricWeight?.text.toString().isEmpty())
            isValid = false
        else if(binding?.EtMetricHeight?.text.toString().isEmpty())
            isValid = false
        return isValid
    }

}