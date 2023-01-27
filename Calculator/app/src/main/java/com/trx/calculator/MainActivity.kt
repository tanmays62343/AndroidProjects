package com.trx.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private var input : TextView? = null
    private var LastNumeric : Boolean = false
    private var LastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.Result)

    }
    fun OnDigit(view : View){
        input?.append((view as Button).text)
        LastNumeric = true
    }
    fun onCLR(view: View){
        input?.text = ""
    }
    fun OnDecimal(view: View){
        if(LastNumeric && !LastDot){
            input?.append(".")
            LastNumeric = false
        }
    }
    fun onOperator(view: View){
        input?.text?.let {
            if(LastNumeric && !isOperatorAdded(it.toString())){
                input?.append((view as Button).text)
                LastNumeric = false
                LastDot = false
            }
        }
    }
    fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")) {
            false
        }
        else{
            value.contains("+")
                    || value.contains("-")
                    || value.contains("x")
                    || value.contains("/")
        }
    }
    fun OnEqual(view: View){
        if(LastNumeric){
            var InputValue = input?.text.toString()
            var prefix = ""

            try{
                if(InputValue.startsWith("-")){
                    prefix = "-"
                    InputValue = InputValue.substring(1)
                }
                if(InputValue.contains("-")){
                    val splitValue = InputValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isEmpty()){
                        one += prefix
                    }
                    input?.text = removeDecimal((one.toDouble() - two.toDouble()).toString())

                }else if(InputValue.contains("+")){
                    val splitValue = InputValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isEmpty()){
                        one += prefix
                    }
                    input?.text = removeDecimal((one.toDouble() + two.toDouble()).toString())

                }else if(InputValue.contains("x")){
                    val splitValue = InputValue.split("x")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isEmpty()){
                        one += prefix
                    }
                    input?.text = removeDecimal((one.toDouble() * two.toDouble()).toString())

                }else if(InputValue.contains("/")){
                    val splitValue = InputValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isEmpty()){
                        one += prefix
                    }
                    input?.text = removeDecimal((one.toDouble() / two.toDouble()).toString())
                }
            }catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeDecimal(result: String) :String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0,result.length-2)
        return value
    }
}