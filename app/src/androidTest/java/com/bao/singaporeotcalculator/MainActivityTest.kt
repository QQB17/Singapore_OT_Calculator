package com.bao.singaporeotcalculator

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @JvmField
    @Rule
    var mActivity: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private fun checkHourlyBasicRate(text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.hourlyBasicRate))
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }

    private fun checkShowOtPay1(text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.showOtPay1))
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }

    private fun checkShowOtPay2(text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.showOtPay2))
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }

    private fun checkShowOtPay3(text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.showOtPay3))
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }

    private fun checkOtPay15hrs(text: String) {
        if (text.isEmpty()) {
            Espresso.onView(ViewMatchers.withId(R.id.otPay15hrs))
                .check(ViewAssertions.matches(ViewMatchers.withText("")))
        } else {
            Espresso.onView(ViewMatchers.withId(R.id.otPay15hrs)).check(
                ViewAssertions.matches(
                    ViewMatchers.withText(
                        "$$text/hrs"
                    )
                )
            )
        }
    }

    private fun checkOtPay20hrs(text: String) {
        if (text.isEmpty()) {
            Espresso.onView(ViewMatchers.withId(R.id.otPay20hrs))
                .check(ViewAssertions.matches(ViewMatchers.withText("")))
        } else {
            Espresso.onView(ViewMatchers.withId(R.id.otPay20hrs)).check(
                ViewAssertions.matches(
                    ViewMatchers.withText(
                        "$$text/hrs"
                    )
                )
            )
        }
    }

    private fun checkOtPay30hrs(text: String) {
        if (text.isEmpty()) {
            Espresso.onView(ViewMatchers.withId(R.id.otPay30hrs))
                .check(ViewAssertions.matches(ViewMatchers.withText("")))
        } else {
            Espresso.onView(ViewMatchers.withId(R.id.otPay30hrs)).check(
                ViewAssertions.matches(
                    ViewMatchers.withText(
                        "$$text/hrs"
                    )
                )
            )
        }
    }

    private fun checkShowOtPayTotal(text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.showOtPayTotal))
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }

    private fun checkShowTotalSalary(text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.showTotalSalary))
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }

    @Test
    fun inputMonthlyBasicSalary() {
        Espresso.onView(ViewMatchers.withId(R.id.inputBasicSalary))
            .perform(ViewActions.typeText("1500"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.buttonCalculate)).perform(ViewActions.click())
        checkHourlyBasicRate("7.87")
        checkShowOtPay1("0")
        checkShowOtPay2("0")
        checkShowOtPay3("0")
        checkOtPay15hrs("11.8")
        checkOtPay20hrs("15.73")
        checkOtPay30hrs("23.6")
        checkShowOtPayTotal("0")
        checkShowTotalSalary("1,500")
    }

    @Test
    fun inputHourlyBasicSalary() {
        Espresso.onView(ViewMatchers.withId(R.id.spinnerSalaryType)).perform(ViewActions.click())
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`("Hourly")
            )
        ).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spinnerSalaryType)).check(
            ViewAssertions.matches(
                ViewMatchers.withSpinnerText(
                    Matchers.containsString("Hourly")
                )
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.inputBasicSalary))
            .perform(ViewActions.typeText("8"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.buttonCalculate)).perform(ViewActions.click())
        checkHourlyBasicRate("8")
        checkShowOtPay1("0")
        checkShowOtPay2("0")
        checkShowOtPay3("0")
        checkOtPay15hrs("12")
        checkOtPay20hrs("16")
        checkOtPay30hrs("24")
        checkShowOtPayTotal("0")
        checkShowTotalSalary("8")
    }

    @Test
    fun inputMonthlyMaxClaim() {
        Espresso.onView(ViewMatchers.withId(R.id.inputMaxClaimSalary))
            .perform(ViewActions.typeText("1200"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.buttonCalculate)).perform(ViewActions.click())
        checkHourlyBasicRate("0")
        checkShowOtPay1("0")
        checkShowOtPay2("0")
        checkShowOtPay3("0")
        checkOtPay15hrs("")
        checkOtPay20hrs("")
        checkOtPay30hrs("")
        checkShowOtPayTotal("0")
        checkShowTotalSalary("0")
    }

    @Test
    fun inputHourlyMaxClaim() {
        Espresso.onView(ViewMatchers.withId(R.id.spinnerMaxClaimType)).perform(ViewActions.click())
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`("Hourly")
            )
        ).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spinnerMaxClaimType)).check(
            ViewAssertions.matches(
                ViewMatchers.withSpinnerText(
                    Matchers.containsString("Hourly")
                )
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.inputMaxClaimSalary))
            .perform(ViewActions.typeText("1200"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.buttonCalculate)).perform(ViewActions.click())
        checkHourlyBasicRate("0")
        checkShowOtPay1("0")
        checkShowOtPay2("0")
        checkShowOtPay3("0")
        checkOtPay15hrs("")
        checkOtPay20hrs("")
        checkOtPay30hrs("")
        checkShowOtPayTotal("0")
        checkShowTotalSalary("0")
    }

    @Test
    fun inputMonthlyBasicSalaryAndMonthlyMaxClaim() {
        Espresso.onView(ViewMatchers.withId(R.id.inputBasicSalary))
            .perform(ViewActions.typeText("1500"))
        Espresso.onView(ViewMatchers.withId(R.id.inputMaxClaimSalary))
            .perform(ViewActions.typeText("1200"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.buttonCalculate)).perform(ViewActions.click())
        checkHourlyBasicRate("7.87") // Maintain basic salary rate
        checkShowOtPay1("0")
        checkShowOtPay2("0")
        checkShowOtPay3("0")
        checkOtPay15hrs("9.44")
        checkOtPay20hrs("12.59")
        checkOtPay30hrs("18.88")
        checkShowOtPayTotal("0")
        checkShowTotalSalary("1,500")
    }

    @Test
    fun inputMonthlyBasicSalaryAndHourlyMaxClaim() {
        Espresso.onView(ViewMatchers.withId(R.id.spinnerMaxClaimType)).perform(ViewActions.click())
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`("Hourly")
            )
        ).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spinnerMaxClaimType)).check(
            ViewAssertions.matches(
                ViewMatchers.withSpinnerText(
                    Matchers.containsString("Hourly")
                )
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.inputBasicSalary))
            .perform(ViewActions.typeText("1500"))
        Espresso.onView(ViewMatchers.withId(R.id.inputMaxClaimSalary))
            .perform(ViewActions.typeText("6"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.buttonCalculate)).perform(ViewActions.click())
        checkHourlyBasicRate("7.87") // Maintain basic salary rate
        checkShowOtPay1("0")
        checkShowOtPay2("0")
        checkShowOtPay3("0")
        checkOtPay15hrs("9")
        checkOtPay20hrs("12")
        checkOtPay30hrs("18")
        checkShowOtPayTotal("0")
        checkShowTotalSalary("1,500")
    }

    @Test
    fun inputOtRateAndOtherWithBasicSalary() {
        Espresso.onView(ViewMatchers.withId(R.id.inputBasicSalary))
            .perform(ViewActions.typeText("1500"))
        Espresso.onView(ViewMatchers.withId(R.id.inputOt1)).perform(ViewActions.typeText("43"))
        Espresso.onView(ViewMatchers.withId(R.id.inputOt2)).perform(ViewActions.typeText("12.5"))
        Espresso.onView(ViewMatchers.withId(R.id.inputOt3)).perform(ViewActions.typeText("8.25"))
        Espresso.onView(ViewMatchers.withId(R.id.inputOther)).perform(ViewActions.typeText("200"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.buttonCalculate)).perform(ViewActions.click())
        checkHourlyBasicRate("7.87") // Maintain basic salary rate
        checkShowOtPay1("507.43")
        checkShowOtPay2("196.68")
        checkShowOtPay3("194.71")
        checkOtPay15hrs("11.8")
        checkOtPay20hrs("15.73")
        checkOtPay30hrs("23.6")
        checkShowOtPayTotal("898.82")
        checkShowTotalSalary("2,598.82")
    }

    @Test
    fun changeEditRateWithBasicSalary() {
        Espresso.onView(ViewMatchers.withId(R.id.inputBasicSalary))
            .perform(ViewActions.typeText("1500"))
        Espresso.onView(ViewMatchers.withId(R.id.editRate1)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText("1.25"))
        Espresso.onView(ViewMatchers.withId(R.id.editRate2)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText("1.8"))
        Espresso.onView(ViewMatchers.withId(R.id.editRate3)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText("10"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.buttonCalculate)).perform(ViewActions.click())
        checkHourlyBasicRate("7.87") // Maintain basic salary rate
        checkShowOtPay1("0")
        checkShowOtPay2("0")
        checkShowOtPay3("0")
        checkOtPay15hrs("9.83")
        checkOtPay20hrs("14.16")
        checkOtPay30hrs("78.67")
        checkShowOtPayTotal("0")
        checkShowTotalSalary("1,500")

        Espresso.onView(ViewMatchers.withId(R.id.textOtRate1))
            .check(ViewAssertions.matches(ViewMatchers.withText("1.25: $")))
        Espresso.onView(ViewMatchers.withId(R.id.textOtRate2))
            .check(ViewAssertions.matches(ViewMatchers.withText("1.8: $")))
        Espresso.onView(ViewMatchers.withId(R.id.textOtRate3))
            .check(ViewAssertions.matches(ViewMatchers.withText("10: $")))
    }

    @Test
    fun overall() {
        Espresso.onView(ViewMatchers.withId(R.id.inputBasicSalary))
            .perform(ViewActions.typeText("2000"))
        Espresso.onView(ViewMatchers.withId(R.id.inputMaxClaimSalary))
            .perform(ViewActions.typeText("1500"))
        Espresso.onView(ViewMatchers.withId(R.id.editRate1)).perform(ViewActions.clearText())
            .perform(ViewActions.typeText("1.25"))
        Espresso.onView(ViewMatchers.withId(R.id.inputOt1)).perform(ViewActions.typeText("43"))
        Espresso.onView(ViewMatchers.withId(R.id.inputOt2)).perform(ViewActions.typeText("12.5"))
        Espresso.onView(ViewMatchers.withId(R.id.inputOt3)).perform(ViewActions.typeText("8.25"))
        Espresso.onView(ViewMatchers.withId(R.id.inputOther))
            .perform(ViewActions.typeText("133.55"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.buttonCalculate)).perform(ViewActions.click())
        checkHourlyBasicRate("10.49") // Maintain basic salary rate
        checkShowOtPay1("422.86")
        checkShowOtPay2("196.68")
        checkShowOtPay3("194.71")
        checkOtPay15hrs("9.83")
        checkOtPay20hrs("15.73")
        checkOtPay30hrs("23.6")

        checkShowOtPayTotal("814.25")
        checkShowTotalSalary("2,947.8")

        Espresso.onView(ViewMatchers.withId(R.id.textOtRate1))
            .check(ViewAssertions.matches(ViewMatchers.withText("1.25: $")))
        Espresso.onView(ViewMatchers.withId(R.id.textOtRate2))
            .check(ViewAssertions.matches(ViewMatchers.withText("2.0: $")))
        Espresso.onView(ViewMatchers.withId(R.id.textOtRate3))
            .check(ViewAssertions.matches(ViewMatchers.withText("3.0: $")))
    }

    @Test
    @Ignore
    fun changeLanguage() {
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
        Espresso.onView(ViewMatchers.withText("Language")).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("中文")).perform(ViewActions.click())
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Espresso.onView(ViewMatchers.withId(R.id.spinnerMaxClaimType)).perform(ViewActions.click())
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(
                    Matchers.instanceOf<Any>(
                        String::class.java
                    )
                ), Matchers.`is`("每小时")
            )
        ).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spinnerMaxClaimType)).check(
            ViewAssertions.matches(
                ViewMatchers.withSpinnerText(
                    Matchers.containsString("每小时")
                )
            )
        )

        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
        Espresso.onView(ViewMatchers.withText("语言")).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("English")).perform(ViewActions.click())
    }
}