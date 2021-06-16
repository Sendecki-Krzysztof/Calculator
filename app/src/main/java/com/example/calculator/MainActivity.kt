package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var lastNum : Boolean =false // variable to check if the last Button pressed was a number.

    // Given onCreate function. changed so that Binding is used for the ContentView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    // Handler for Number Buttons. Simply appends the clicked button to the textView and then sets
    // lastNum to true.
    fun numPressed(view: View) {

        val button = view as Button
        binding.tvInput.append("${button.text}")
        lastNum = true
    }

    // Handler for the operation buttons. It checks if containsOperation is false and that lastNum
    // is true, then it appends the pressed operation to the textView
    fun operationPressed(view: View) {
        val button = view as Button

        if(lastNum && !containsOperation(binding.tvInput.text.toString())) {
            binding.tvInput.append(" ${button.text} ")
            lastNum = false
        }
    }

    // Private Helper function to operationPressed. This function checks to see if the current screen
    // already has a operation symbol in it. if it does it returns true and prevents the user from
    // adding another one.
    private fun containsOperation(text: String) : Boolean {
        return if (text.startsWith("-")) {
            false
        } else {
            (text.contains("/")
                    || text.contains("*")
                    || text.contains("-")
                    || text.contains("+"))
        }
    }


    // Handler for the clear(CLR) button. It clears the textview and resets all values back to their
    // starting position.
    fun clearPressed(view: View) {
        binding.tvInput.text = ""
        lastNum = false
    }


    // Handler for the equals button. It takes the text and splits it at the operation symbol then
    // preforms the corresponding operation on the 2 halves of the string. It also catches a
    // ArithmeticException in case anything goes wrong with the calculating
    fun equalsPressed(view: View) {
        val button = view as Button
        if(lastNum) {
            var value = binding.tvInput.text.toString()
            var prefix = ""

            try{
                if(binding.tvInput.text.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1)
                }

                subtract(prefix, value)
                add(prefix,value)
                multiply(prefix, value)
                divide(prefix, value)

            }catch (error:ArithmeticException) {
                error.printStackTrace()
            }
        }
    }

    // Helper Functions for equalsPressed. decide what happens given a equals pressed based on the
    // Operation button used. They check if the prefix of the string is a "-" and then print the
    // value onto the textview given after they complete their corresponding operations
    private fun subtract(prefix :String, value : String) {
        if(value.contains("-")) {
            val split = value.split(" - ")
            val two = split[1]
            var one = split[0]

            if(prefix.isNotEmpty()) {
                one = prefix + split[0]
            }

            binding.tvInput.text = removeZero((one.toDouble() - two.toDouble()).toString())
        }
    }
    private fun add(prefix : String, value : String) {
        if(value.contains("+")) {
            val split = value.split(" + ")
            val two = split[1]
            var one = split[0]

            if(prefix.isNotEmpty()) {
                one = prefix + split[0]
            }

            binding.tvInput.text = removeZero((one.toDouble() + two.toDouble()).toString())
        }
    }
    private fun multiply(prefix : String, value : String) {
        if(value.contains("*")) {
            val split = value.split(" * ")
            val two = split[1]
            var one = split[0]


            if(prefix.isNotEmpty()) {
                one = prefix + split[0]
            }

            binding.tvInput.text = removeZero((one.toDouble() * two.toDouble()).toString())
        }
    }
    private fun divide(prefix : String, value : String) {
        if(value.contains("/")) {
            val split = value.split(" / ")
            val two = split[1]
            var one = split[0]

            if(prefix.isNotEmpty()) {
                one = prefix + split[0]
            }

            binding.tvInput.text = removeZero((one.toDouble() / two.toDouble()).toString())
        }
    }

    // Function to remove the unneeded zeros at the send of a string
    private fun removeZero(value : String) : String{
        var result = value
        if(value.contains(".0")) {
            result = result.substring(0, value.length - 2)
        }
        return result
    }
}