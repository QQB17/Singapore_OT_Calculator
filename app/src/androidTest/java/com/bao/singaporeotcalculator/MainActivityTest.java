package com.bao.singaporeotcalculator;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.util.Locale;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<>(MainActivity.class);

    private void checkHourlyBasicRate(String text) {
        onView(withId(R.id.hourlyBasicRate)).check(matches(withText(text)));
    }

    private void checkShowOtPay1(String text) {
        onView(withId(R.id.showOtPay1)).check(matches(withText(text)));
    }

    private void checkShowOtPay2(String text) {
        onView(withId(R.id.showOtPay2)).check(matches(withText(text)));
    }
    private void checkShowOtPay3(String text) {
        onView(withId(R.id.showOtPay3)).check(matches(withText(text)));
    }

    private void checkOtPay15hrs(String text){
        if(text.isEmpty()){
            onView(withId(R.id.otPay15hrs)).check(matches(withText("")));
        } else {
            onView(withId(R.id.otPay15hrs)).check(matches(withText("$"+text+"/hrs")));
        }

    }

    private void checkOtPay20hrs(String text){
        if(text.isEmpty()){
            onView(withId(R.id.otPay20hrs)).check(matches(withText("")));
        }else {
            onView(withId(R.id.otPay20hrs)).check(matches(withText("$"+text+"/hrs")));
        }

    }

    private void checkOtPay30hrs(String text){
        if(text.isEmpty()){
            onView(withId(R.id.otPay30hrs)).check(matches(withText("")));
        }else{
            onView(withId(R.id.otPay30hrs)).check(matches(withText("$"+text+"/hrs")));
        }

    }

    private void checkShowOtPayTotal(String text){
        onView(withId(R.id.showOtPayTotal)).check(matches(withText(text)));
    }

    private void checkShowTotalSalary(String text){
        onView(withId(R.id.showTotalSalary)).check(matches(withText(text)));
    }

    @Test
    public void inputMonthlyBasicSalary() {
        onView(withId(R.id.inputBasicSalary)).perform(typeText("1500"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.buttonCalculate)).perform(click());
        checkHourlyBasicRate("7.87");
        checkShowOtPay1("0");
        checkShowOtPay2("0");
        checkShowOtPay3("0");
        checkOtPay15hrs("11.8");
        checkOtPay20hrs("15.73");
        checkOtPay30hrs("23.6");
        checkShowOtPayTotal("0");
        checkShowTotalSalary("1,500");
    }

    @Test
    public void inputHourlyBasicSalary() {
        onView(withId(R.id.spinnerSalaryType)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Hourly"))).perform(click());
        onView(withId(R.id.spinnerSalaryType)).check(matches(withSpinnerText(containsString("Hourly"))));

        onView(withId(R.id.inputBasicSalary)).perform(typeText("8"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.buttonCalculate)).perform(click());
        checkHourlyBasicRate("8");
        checkShowOtPay1("0");
        checkShowOtPay2("0");
        checkShowOtPay3("0");
        checkOtPay15hrs("12");
        checkOtPay20hrs("16");
        checkOtPay30hrs("24");
        checkShowOtPayTotal("0");
        checkShowTotalSalary("8");
    }

    @Test
    public void inputMonthlyMaxClaim() {
        onView(withId(R.id.inputMaxClaimSalary)).perform(typeText("1200"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.buttonCalculate)).perform(click());
        checkHourlyBasicRate("0");
        checkShowOtPay1("0");
        checkShowOtPay2("0");
        checkShowOtPay3("0");
        checkOtPay15hrs("");
        checkOtPay20hrs("");
        checkOtPay30hrs("");
        checkShowOtPayTotal("0");
        checkShowTotalSalary("0");
    }

    @Test
    public void inputHourlyMaxClaim() {
        onView(withId(R.id.spinnerMaxClaimType)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Hourly"))).perform(click());
        onView(withId(R.id.spinnerMaxClaimType)).check(matches(withSpinnerText(containsString("Hourly"))));

        onView(withId(R.id.inputMaxClaimSalary)).perform(typeText("1200"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.buttonCalculate)).perform(click());
        checkHourlyBasicRate("0");
        checkShowOtPay1("0");
        checkShowOtPay2("0");
        checkShowOtPay3("0");
        checkOtPay15hrs("");
        checkOtPay20hrs("");
        checkOtPay30hrs("");
        checkShowOtPayTotal("0");
        checkShowTotalSalary("0");
    }

    @Test
    public void inputMonthlyBasicSalaryAndMonthlyMaxClaim(){
        onView(withId(R.id.inputBasicSalary)).perform(typeText("1500"));
        onView(withId(R.id.inputMaxClaimSalary)).perform(typeText("1200"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.buttonCalculate)).perform(click());
        checkHourlyBasicRate("7.87"); // Maintain basic salary rate
        checkShowOtPay1("0");
        checkShowOtPay2("0");
        checkShowOtPay3("0");
        checkOtPay15hrs("9.44");
        checkOtPay20hrs("12.59");
        checkOtPay30hrs("18.88");
        checkShowOtPayTotal("0");
        checkShowTotalSalary("1,500");
    }

    @Test
    public void  inputMonthlyBasicSalaryAndHourlyMaxClaim() {
        onView(withId(R.id.spinnerMaxClaimType)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Hourly"))).perform(click());
        onView(withId(R.id.spinnerMaxClaimType)).check(matches(withSpinnerText(containsString("Hourly"))));

        onView(withId(R.id.inputBasicSalary)).perform(typeText("1500"));
        onView(withId(R.id.inputMaxClaimSalary)).perform(typeText("6"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.buttonCalculate)).perform(click());
        checkHourlyBasicRate("7.87"); // Maintain basic salary rate
        checkShowOtPay1("0");
        checkShowOtPay2("0");
        checkShowOtPay3("0");
        checkOtPay15hrs("9");
        checkOtPay20hrs("12");
        checkOtPay30hrs("18");
        checkShowOtPayTotal("0");
        checkShowTotalSalary("1,500");
    }

    @Test
    public void inputOtRateAndOtherWithBasicSalary() {
        onView(withId(R.id.inputBasicSalary)).perform(typeText("1500"));
        onView(withId(R.id.inputOt1)).perform(typeText("43"));
        onView(withId(R.id.inputOt2)).perform(typeText("12.5"));
        onView(withId(R.id.inputOt3)).perform(typeText("8.25"));
        onView(withId(R.id.inputOther)).perform(typeText("200"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.buttonCalculate)).perform(click());
        checkHourlyBasicRate("7.87"); // Maintain basic salary rate
        checkShowOtPay1("507.43");
        checkShowOtPay2("196.68");
        checkShowOtPay3("194.71");
        checkOtPay15hrs("11.8");
        checkOtPay20hrs("15.73");
        checkOtPay30hrs("23.6");
        checkShowOtPayTotal("898.82");
        checkShowTotalSalary("2,598.82");
    }

    @Test
    public void changeEditRateWithBasicSalary(){
        onView(withId(R.id.inputBasicSalary)).perform(typeText("1500"));
        onView(withId(R.id.editRate1)).perform(clearText()).perform(typeText("1.25"));
        onView(withId(R.id.editRate2)).perform(clearText()).perform(typeText("1.8"));
        onView(withId(R.id.editRate3)).perform(clearText()).perform(typeText("10"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.buttonCalculate)).perform(click());
        checkHourlyBasicRate("7.87"); // Maintain basic salary rate
        checkShowOtPay1("0");
        checkShowOtPay2("0");
        checkShowOtPay3("0");
        checkOtPay15hrs("9.83");
        checkOtPay20hrs("14.16");
        checkOtPay30hrs("78.67");
        checkShowOtPayTotal("0");
        checkShowTotalSalary("1,500");

        onView(withId(R.id.textOtRate1)).check(matches(withText("1.25: $")));
        onView(withId(R.id.textOtRate2)).check(matches(withText("1.8: $")));
        onView(withId(R.id.textOtRate3)).check(matches(withText("10: $")));
    }

    @Test
    public void overall() {
        onView(withId(R.id.inputBasicSalary)).perform(typeText("2000"));
        onView(withId(R.id.inputMaxClaimSalary)).perform(typeText("1500"));
        onView(withId(R.id.editRate1)).perform(clearText()).perform(typeText("1.25"));
        onView(withId(R.id.inputOt1)).perform(typeText("43"));
        onView(withId(R.id.inputOt2)).perform(typeText("12.5"));
        onView(withId(R.id.inputOt3)).perform(typeText("8.25"));
        onView(withId(R.id.inputOther)).perform(typeText("133.55"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.buttonCalculate)).perform(click());
        checkHourlyBasicRate("10.49"); // Maintain basic salary rate
        checkShowOtPay1("422.86");
        checkShowOtPay2("196.68");
        checkShowOtPay3("194.71");
        checkOtPay15hrs("9.83");
        checkOtPay20hrs("15.73");
        checkOtPay30hrs("23.6");;
        checkShowOtPayTotal("814.25");
        checkShowTotalSalary("2,947.8");

        onView(withId(R.id.textOtRate1)).check(matches(withText("1.25: $")));
        onView(withId(R.id.textOtRate2)).check(matches(withText("2.0: $")));
        onView(withId(R.id.textOtRate3)).check(matches(withText("3.0: $")));

    }

    @Test
    @Ignore
    public void changeLanguage(){
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText("Language")).perform(click());
        onView(withText("中文")).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.spinnerMaxClaimType)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("每小时"))).perform(click());
        onView(withId(R.id.spinnerMaxClaimType)).check(matches(withSpinnerText(containsString("每小时"))));

        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText("语言")).perform(click());
        onView(withText("English")).perform(click());
    }
}