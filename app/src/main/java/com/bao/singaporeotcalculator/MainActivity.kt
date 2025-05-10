package com.bao.singaporeotcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bao.singaporeotcalculator.presentaition.nav.MainNavHost
import com.bao.singaporeotcalculator.presentaition.ui.theme.SingaporeOtCalculatorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SingaporeOtCalculatorTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    MainNavHost(navController = navController)
                }
            }
        }
    }

    /*
    private val df = DecimalFormat("#,###,###.##")
    private var hourlyRate = 0.0
    private var hourlyRateLimit = 0.0
    private var hourlyRateNormal = 0.0
    private var otPay15 = 0.0
    private var otPay20 = 0.0
    private var otPay30 = 0.0
    private var claimType = 0
    private var salaryType = 0 // Monthly = 0 , Hourly = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSalaryType()
        maxClaimType
        setupHyperLink()

        //Main
        val calculateButton = findViewById<Button>(R.id.buttonCalculate)
        calculateButton.setOnClickListener { v: View? ->
            calculateHourlyBasicRate()
            setHourlyBasicRate()
            setOtPay15()
            setOtPay20()
            setOtPay30()
            setOtPayTotal()
            setTotalSalary()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.english -> {
                setAppLocate("en")
                Toast.makeText(this@MainActivity, "English", Toast.LENGTH_SHORT).show()
            }

            R.id.chinese -> {
                setAppLocate("zh")
                Toast.makeText(this@MainActivity, "中文", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Change Language
    private fun setAppLocate(localeCode: String) {
        val resources = resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(Locale(localeCode.lowercase(Locale.getDefault())))
        } else {
            config.locale = Locale(localeCode.lowercase(Locale.getDefault()))
        }
        resources.updateConfiguration(config, dm)
        //Refresh will changing language
        if (Build.VERSION.SDK_INT >= 11) {
            super.recreate()
        } else {
            val intent = intent
            finish()
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }

    private fun getSalaryType() {
        val salarySpinner = findViewById<Spinner>(R.id.spinnerSalaryType)

        val salaryAdapter = ArrayAdapter(
            this,
            R.layout.spinner_item, resources.getStringArray(R.array.salaryType)
        )
        salaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        salarySpinner.adapter = salaryAdapter

        salarySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                salaryType = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private val maxClaimType: Unit
        get() {
            val claimSpinner = findViewById<Spinner>(R.id.spinnerMaxClaimType)
            val claimAdapter =
                ArrayAdapter(
                    this,
                    R.layout.spinner_item, resources.getStringArray(R.array.salaryType)
                )
            claimAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            claimSpinner.adapter = claimAdapter

            claimSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    claimType = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

    //Calculate Hourly Basic Rate
    private fun calculateHourlyBasicRate() {
        val eBasicSalary = (findViewById<View>(R.id.inputBasicSalary) as EditText).text.toString()
        val eMaxClaimWage =
            (findViewById<View>(R.id.inputMaxClaimSalary) as EditText).text.toString()

        val dMaxClaimWage =
            if ((eMaxClaimWage.isEmpty())) Double.MAX_VALUE else eMaxClaimWage.toDouble()

        if (!(eBasicSalary.isEmpty())) {
            val dBasicSalary = eBasicSalary.toDouble()

            // Monthly
            if (claimType == 0) hourlyRateLimit = 12.0 * dMaxClaimWage / (52 * 44)
            else if (claimType == 1) hourlyRateLimit = dMaxClaimWage

            // Monthly
            if (salaryType == 0) hourlyRateNormal = (12 * dBasicSalary) / (52 * 44)
            else if (salaryType == 1) hourlyRateNormal = dBasicSalary

            // Limit the calculation
            hourlyRate =
                if ((hourlyRateLimit <= hourlyRateNormal)) hourlyRateLimit else hourlyRateNormal
        } else {
            hourlyRate = 0.0
            hourlyRateNormal = 0.0
        }
    }

    private fun setHourlyBasicRate() {
        val tHourlyBasicRate = findViewById<TextView>(R.id.hourlyBasicRate)
        tHourlyBasicRate.text = df.format(hourlyRateNormal)
    }

    private fun calculateOtPay15(): Double {
        val inputHour1 = (findViewById<View>(R.id.inputOt1) as EditText).text.toString()
        val rate1 = (findViewById<View>(R.id.editRate1) as EditText).text.toString()
        val viewRate1 = findViewById<TextView>(R.id.textOtRate1)

        if (hourlyRate == 0.0 || rate1.isEmpty()) return 0.0
        val text1 = "$rate1: $"
        viewRate1.text = text1
        otPay15 = hourlyRate * rate1.toDouble()

        if (inputHour1.isEmpty()) return 0.0

        return otPay15 * inputHour1.toDouble()
    }

    private fun setOtPay15() {
        val tOtPay15 = findViewById<TextView>(R.id.showOtPay1)
        tOtPay15.text = df.format(calculateOtPay15())

        val otPay15hrs = findViewById<TextView>(R.id.otPay15hrs)
        if (hourlyRate == 0.0) {
            otPay15hrs.text = ""
        } else {
            val text = getString(R.string.per_hours, df.format(otPay15))
            otPay15hrs.text = text
        }
    }


    private fun calculateOtPay20(): Double {
        val inputHour2 = (findViewById<View>(R.id.inputOt2) as EditText).text.toString()
        val rate2 = (findViewById<View>(R.id.editRate2) as EditText).text.toString()
        val viewRate2 = findViewById<TextView>(R.id.textOtRate2)

        if (hourlyRate == 0.0 || rate2.isEmpty()) return 0.0
        val text2 = "$rate2: $"
        viewRate2.text = text2
        otPay20 = hourlyRate * rate2.toDouble()

        if (inputHour2.isEmpty()) return 0.0

        return otPay20 * inputHour2.toDouble()
    }

    private fun setOtPay20() {
        val tOtPay20 = findViewById<TextView>(R.id.showOtPay2)
        tOtPay20.text = df.format(calculateOtPay20())

        val otPay20hrs = findViewById<TextView>(R.id.otPay20hrs)
        if (hourlyRate == 0.0) {
            otPay20hrs.text = ""
        } else {
            val text = getString(R.string.per_hours, df.format(otPay20))
            otPay20hrs.text = text
        }
    }

    private fun calculateOtPay30(): Double {
        val inputHour3 = (findViewById<View>(R.id.inputOt3) as EditText).text.toString()
        val rate3 = (findViewById<View>(R.id.editRate3) as EditText).text.toString()
        val viewRate3 = findViewById<TextView>(R.id.textOtRate3)

        if (hourlyRate == 0.0 || rate3.isEmpty()) return 0.0
        val text3 = "$rate3: $"
        viewRate3.text = text3
        otPay30 = hourlyRate * rate3.toDouble()

        if (inputHour3.isEmpty()) return 0.0

        return otPay30 * inputHour3.toDouble()
    }

    private fun setOtPay30() {
        val tOtPay30 = findViewById<TextView>(R.id.showOtPay3)
        tOtPay30.text = df.format(calculateOtPay30())
        val otPay30hrs = findViewById<TextView>(R.id.otPay30hrs)

        if (hourlyRate == 0.0) {
            otPay30hrs.text = ""
        } else {
            val text = getString(R.string.per_hours, df.format(otPay30))
            otPay30hrs.text = text
        }
    }

    private fun calculateOtPayTotal(): Double {
        return calculateOtPay15() + calculateOtPay20() + calculateOtPay30()
    }

    private fun setOtPayTotal() {
        (findViewById<View>(R.id.showOtPayTotal) as TextView).text =
            df.format(calculateOtPayTotal())
    }

    private val otherSalary: Double
        get() {
            val eOther =
                (findViewById<View>(R.id.inputOther) as EditText).text.toString()
            return if (eOther.isEmpty()) {
                0.0
            } else {
                eOther.toDouble()
            }
        }

    private fun setTotalSalary() {
        val tTotalSalary = findViewById<TextView>(R.id.showTotalSalary)
        val eBasicSalary = (findViewById<View>(R.id.inputBasicSalary) as EditText).text.toString()

        if (eBasicSalary.isEmpty()) {
            tTotalSalary.text = "0"
        } else {
            tTotalSalary.text =
                df.format(eBasicSalary.toDouble() + calculateOtPayTotal() + otherSalary)
        }
    }

    //Set up HyperLink
    private fun setupHyperLink() {
        val linkTextView = findViewById<TextView>(R.id.mom_website)
        linkTextView.movementMethod = LinkMovementMethod.getInstance()
    }*/
}