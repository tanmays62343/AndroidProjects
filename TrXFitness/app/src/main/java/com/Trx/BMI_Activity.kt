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
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //set back button
        supportActionBar?.title = "CALCULATE BMI" // Setting a title in the action bar.
        binding?.toolbarBMI?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        // END

        // TODO(Step 3 : Adding a click event to METRIC UNIT Calculate button and after valid input calculating it.)
        // START
        // Button will calculate the input values in Metric Units
        binding?.btnCalculateUnits?.setOnClickListener {

            // The values are validated.
            if (validateMetricUnits()) {

                // The height value is converted to a float value and divided by 100 to convert it to meter.
                val heightValue: Float = binding?.EtMetricHeight?.text.toString().toFloat() / 100

                // The weight value is converted to a float value
                val weightValue: Float = binding?.EtMetricWeight?.text.toString().toFloat()

                // BMI value is calculated in METRIC UNITS using the height and weight value.
                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)
            } else {
                Toast.makeText(this@BMI_Activity, "Please enter valid values.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        // END
    }


    // TODO(Step 2 : Validating the METRIC UNITS CALCULATION input.)
    // START
    /**
     * Function is used to validate the input values for METRIC UNITS.
     */
    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.EtMetricWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.EtMetricHeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }
    // END

    //TODO(Step 4 : Displaying the calculated BMI UI what we have designed earlier.)
    // START
    /**
     * Function is used to display the result of METRIC UNITS.
     */
    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        binding?.llDiplayBMIResult?.visibility = View.VISIBLE
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }
}