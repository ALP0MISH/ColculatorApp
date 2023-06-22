package com.example.colculatorapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.colculatorapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val calculator: Calculator by lazy {
        Calculator()
    }

    private val isNightMode: Boolean by lazy(LazyThreadSafetyMode.NONE) {
        resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK ==
                Configuration.UI_MODE_NIGHT_YES
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
        observeData()
    }

    private fun observeData() {
        calculator.calculateFlow.onEach { data: CalculatorModel ->
            val structure = "${data.numberFirst} ${data.operation} ${data.numberSecond}"
            binding.resultTextView.text = structure
        }.launchIn(lifecycleScope)
    }


    private fun setOnClickListener() {
        with(binding) {
            themeIcon.setOnClickListener {
                if (isNightMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            firstButton.setOnClickListener {
                calculator.enterNumber(number = "1")
            }

            secondButton.setOnClickListener {
                calculator.enterNumber(number = "2")
            }

            thirdButton.setOnClickListener {
                calculator.enterNumber(number = "3")
            }

            fourthButton.setOnClickListener {
                calculator.enterNumber(number = "4")

            }
            fifthButton.setOnClickListener {
                calculator.enterNumber(number = "5")
            }

            sixthButton.setOnClickListener {
                calculator.enterNumber(number = "6")
            }

            seventhButton.setOnClickListener {
                calculator.enterNumber(number = "7")
            }

            eightButton.setOnClickListener {
                calculator.enterNumber(number = "8")
            }

            ninthButton.setOnClickListener {
                calculator.enterNumber(number = "9")
            }

            ziroButton.setOnClickListener {
                calculator.enterNumber(number = "0")
            }

            pluseButton.setOnClickListener {
                calculator.enterOperation(operation = "+")
            }

            minuseButton.setOnClickListener {
                calculator.enterOperation(operation = "-")
            }

            divideButton.setOnClickListener {
                calculator.enterOperation(operation = "/")
            }
            xButton.setOnClickListener {
                calculator.enterOperation(operation = "x")
            }

            equalButton.setOnClickListener {
                calculator.enterCalculate()
            }
            acButton.setOnClickListener {
                calculator.enterClear()
            }
            pointButton.setOnClickListener {
                calculator.enterDecimal()
            }
            deleteButton.setOnClickListener {
                calculator.enterDelete()
            }
        }
    }


}