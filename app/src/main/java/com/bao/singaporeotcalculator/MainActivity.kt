package com.bao.singaporeotcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Locale;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private double hourlyRate, hourlyRateLimit, hourlyRateNormal = 0.0;
    private double otPay15, otPay20, otPay30;
    private final DecimalFormat df =new DecimalFormat("#,###,###.##");
    private int claimType,salaryType; // Monthly = 0 , Hourly = 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSalaryType();
        getMaxClaimType();
        setupHyperLink();

        //Main
        Button calculateButton = findViewById(R.id.buttonCalculate);
        calculateButton.setOnClickListener((View v) -> {
                calculateHourlyBasicRate();
                setHourlyBasicRate();
                setOtPay15();
                setOtPay20();
                setOtPay30();
                setOtPayTotal();
                setTotalSalary();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.english:
                setAppLocate("en");
                Toast.makeText(MainActivity.this, "English", Toast.LENGTH_SHORT).show();
                break;
            case R.id.chinese:
                setAppLocate("zh");
                Toast.makeText(MainActivity.this, "中文", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Change Language
    private void setAppLocate(String localeCode)
    {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config,dm);
        //Refresh will changing language
        if(Build.VERSION.SDK_INT >=11){
            super.recreate();
        }else {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    private void getSalaryType() {
        Spinner salarySpinner = findViewById(R.id.spinnerSalaryType);

        ArrayAdapter<String> salaryAdapter =new ArrayAdapter<>(this,
                R.layout.spinner_item, getResources().getStringArray(R.array.salaryType));
        salaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        salarySpinner.setAdapter(salaryAdapter);

        salarySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                salaryType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void getMaxClaimType() {
        Spinner claimSpinner =findViewById(R.id.spinnerMaxClaimType);
        ArrayAdapter<String> claimAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, getResources().getStringArray(R.array.salaryType));
        claimAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        claimSpinner.setAdapter(claimAdapter);

        claimSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                claimType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    //Calculate Hourly Basic Rate
    private void calculateHourlyBasicRate()
    {
        String eBasicSalary = ((EditText)findViewById(R.id.inputBasicSalary)).getText().toString();
        String eMaxClaimWage = ((EditText)findViewById(R.id.inputMaxClaimSalary)).getText().toString();

        double dMaxClaimWage = (eMaxClaimWage.isEmpty()) ? Double.MAX_VALUE :Double.parseDouble(eMaxClaimWage);

        if(!(eBasicSalary.isEmpty())) {
            double dBasicSalary = Double.parseDouble(eBasicSalary);

            // Monthly
            if(claimType == 0)
                hourlyRateLimit = 12.0 * dMaxClaimWage / (52 * 44);
            // Hourly
            else if(claimType == 1)
                hourlyRateLimit = dMaxClaimWage;

            // Monthly
            if(salaryType == 0)
                hourlyRateNormal = (12 * dBasicSalary) / (52 * 44);
            // Hourly
            else if(salaryType == 1)
                hourlyRateNormal = dBasicSalary;

            // Limit the calculation
            hourlyRate = (hourlyRateLimit <= hourlyRateNormal) ? hourlyRateLimit : hourlyRateNormal;

        }else{
            hourlyRate = 0.0;
            hourlyRateNormal =0.0;
        }
    }

    private void setHourlyBasicRate()
    {
        TextView tHourlyBasicRate = findViewById(R.id.hourlyBasicRate);
        tHourlyBasicRate.setText(df.format(hourlyRateNormal));
    }

    private double calculateOtPay15()
    {
        String inputHour1 = ((EditText)findViewById(R.id.inputOt1)).getText().toString();
        String rate1 = ((EditText)findViewById(R.id.editRate1)).getText().toString();
        TextView viewRate1 = findViewById(R.id.textOtRate1);

        if( hourlyRate == 0.0 || rate1.isEmpty()) return 0.0;
        String text1 = rate1 +  ": $";
        viewRate1.setText(text1);
        otPay15 = hourlyRate * Double.parseDouble(rate1);

        if(inputHour1.isEmpty()) return 0.0;

        return otPay15 * Double.parseDouble(inputHour1);
    }

    private void setOtPay15()
    {
        TextView tOtPay15 = findViewById(R.id.showOtPay1);
        tOtPay15.setText(df.format(calculateOtPay15()));

        TextView otPay15hrs = findViewById(R.id.otPay15hrs);
        if(hourlyRate == 0.0) {
            otPay15hrs.setText("");
        }else {
            String text = getString(R.string.per_hours, df.format(otPay15));
            otPay15hrs.setText(text);
        }
    }


    private double calculateOtPay20()
    {
        String inputHour2 = ((EditText)findViewById(R.id.inputOt2)).getText().toString();
        String rate2 = ((EditText)findViewById(R.id.editRate2)).getText().toString();
        TextView viewRate2 = findViewById(R.id.textOtRate2);

        if(hourlyRate == 0.0 || rate2.isEmpty()) return 0.0;
        String text2 = rate2 + ": $";
        viewRate2.setText(text2);
        otPay20 = hourlyRate * Double.parseDouble(rate2);

        if(inputHour2.isEmpty()) return 0.0;

        return otPay20 *  Double.parseDouble(inputHour2);
    }

    private void setOtPay20()
    {
        TextView tOtPay20 = findViewById(R.id.showOtPay2);
        tOtPay20.setText(df.format(calculateOtPay20()));

        TextView otPay20hrs = findViewById(R.id.otPay20hrs);
        if(hourlyRate == 0.0) {
            otPay20hrs.setText("");
        }else {
            String text = getString(R.string.per_hours, df.format(otPay20));
            otPay20hrs.setText(text);
        }
    }

    private double calculateOtPay30()
    {
        String inputHour3 = ((EditText)findViewById(R.id.inputOt3)).getText().toString();
        String rate3 = ((EditText)findViewById(R.id.editRate3)).getText().toString();
        TextView viewRate3 = findViewById(R.id.textOtRate3);

        if(hourlyRate == 0.0 || rate3.isEmpty()) return 0.0;
        String text3 = rate3 + ": $";
        viewRate3.setText(text3);
        otPay30 = hourlyRate * Double.parseDouble(rate3);

        if(inputHour3.isEmpty()) return 0.0;

        return otPay30 * Double.parseDouble(inputHour3);
    }

    private void setOtPay30()
    {
        TextView tOtPay30 = findViewById(R.id.showOtPay3);
        tOtPay30.setText(df.format(calculateOtPay30()));
        TextView otPay30hrs = findViewById(R.id.otPay30hrs);

        if(hourlyRate == 0.0) {
            otPay30hrs.setText("");
        }else {
            String text = getString(R.string.per_hours, df.format(otPay30));
            otPay30hrs.setText(text);
        }
    }

    private double calculateOtPayTotal()
    {
        return calculateOtPay15() + calculateOtPay20() + calculateOtPay30();
    }

    private void setOtPayTotal()
    {
        ((TextView)findViewById(R.id.showOtPayTotal)).setText(df.format(calculateOtPayTotal()));
    }

    private Double getOtherSalary(){
        String eOther =((EditText)findViewById(R.id.inputOther)).getText().toString();
        if(eOther.isEmpty()){
            return 0.0;
        }else{
            return Double.parseDouble(eOther);
        }
    }

    private void setTotalSalary() {
        TextView tTotalSalary = findViewById(R.id.showTotalSalary);
        String eBasicSalary =((EditText)findViewById(R.id.inputBasicSalary)).getText().toString();

        if(eBasicSalary.isEmpty()) {
            tTotalSalary.setText("0");
        }else{
            tTotalSalary.setText(df.format(Double.parseDouble(eBasicSalary) + calculateOtPayTotal() + getOtherSalary()));
        }
    }

    //Set up HyperLink
    private void setupHyperLink(){
        TextView linkTextView = findViewById(R.id.mom_website);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}