package com.example.mycalculator

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mycalculator.databinding.ActivityMainBinding
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var lastDot=false
    var lastNumber=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                     window.statusBarColor = resources.getColor(R.color.bgColor)
                 }

        val view = binding.root
        setContentView(view)


    }

    fun onDigit(btn:View) {
        lastNumber=true
        binding.InputTv.append((btn as? Button)?.text)
    }

    fun onClear(btn: View){
        binding.InputTv.text=""
        lastNumber=false
        lastDot=false
    }

    fun onDecimal(view: View)
    {
        if(lastNumber and !lastDot){
            binding.InputTv.append((view as Button).text)
            lastDot=true
            lastNumber=false
        }
    }

    fun onOperator(view: View)
    {
        val str=binding.InputTv.text.toString()
        val txt=(view as Button).text.toString()

        if(lastNumber and !isOperatorAdded(str))
        {
            binding.InputTv.append(txt)


        }
        else if(!isOperatorAdded(str) and str.isEmpty() and (txt == "-")){
            binding.InputTv.append(txt)
        }

        lastDot=false
        lastNumber=false

    }

    private fun isOperatorAdded(str:String):Boolean{
        return if(str.startsWith("-")){
            false
        }
        else{
            str.contains('+') or str.contains('-') or str.contains('/') or str.contains('x')

        }
    }

    fun onEqual(view: View){
        var InputString=binding.InputTv.text.toString()
        var prefix=""

        try{

            if(InputString.startsWith("-"))
            {
                prefix="-"
                InputString=InputString.substring(1)
            }


             if(InputString.contains('-'))
            {
                val InputValue=InputString.split('-')
                var operand1=InputValue[0]
                val operand2=InputValue[1]

                if(prefix=="-")
                    operand1=prefix+operand1


                binding.InputTv.text=(operand1.toDouble()-operand2.toDouble()).toString()

            }

            else if(InputString.contains('x'))
            {
                val InputValue=InputString.split('x')
                var operand1=InputValue[0]
                val operand2=InputValue[1]

                if(prefix=="-")
                    operand1=prefix+operand1


                binding.InputTv.text=(operand1.toDouble()*operand2.toDouble()).toString()

            }

            else if(InputString.contains('+'))
            {
                val InputValue=InputString.split('+')
                var operand1=InputValue[0]
                val operand2=InputValue[1]

                if(prefix=="-")
                    operand1=prefix+operand1


                binding.InputTv.text=(operand1.toDouble()+operand2.toDouble()).toString()

            }

            else if(InputString.contains('/'))
            {
                val InputValue=InputString.split('/')
                var operand1=InputValue[0]
                val operand2=InputValue[1]

                if(prefix=="-")
                    operand1=prefix+operand1

                if(operand2=="0")
                    binding.InputTv.text="Infinity"
                else{
                    binding.InputTv.text=(operand1.toDouble()/operand2.toDouble()).toString()
                }

            }

        }catch (e:ArithmeticException){
            println("error")
        }
    }


}