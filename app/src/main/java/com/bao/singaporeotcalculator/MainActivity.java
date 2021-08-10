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

    private Double hourlyRate, hourlyRateLimit, hourlyRateNormal = 0.0;
    private Double otPay15, otPay20 , otPay30;
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
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateHourlyBasicRate();
                setHourlyBasicRate();
                setOtPay15();
                setOtPay20();
                setOtPay30();
                setOtPayTotal();
                setTotalSalary();

            }
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
        Spinner salarySpinner = (Spinner)  findViewById(R.id.spinnerSalaryType);

        ArrayAdapter<String> salaryAdapter =new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.salaryType));
        salaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        salarySpinner.setAdapter(salaryAdapter);

        salarySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { salaryType = position; }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void getMaxClaimType() {
        Spinner claimSpinner = (Spinner)  findViewById(R.id.spinnerMaxClaimType);
        ArrayAdapter<String> claimAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.salaryType));
        claimAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        claimSpinner.setAdapter(claimAdapter);

        claimSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { claimType = position; }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    //Calculate Hourly Basic Rate
    private void calculateHourlyBasicRate()
    {
        EditText eBasicSalary = findViewById(R.id.inputBasicSalary);
        EditText eMaxClaimWage = findViewById(R.id.inputMaxClaimSalary);
        Double dMaxClaimWage;

        if(!(eMaxClaimWage.getText().toString().isEmpty())){
            dMaxClaimWage = Double.parseDouble(eMaxClaimWage.getText().toString());
        } else {
            dMaxClaimWage = Double.MAX_VALUE;
        }

        if(!(eBasicSalary.getText().toString().isEmpty())) {
            Double dBasicSalary = Double.parseDouble(eBasicSalary.getText().toString());

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
            if(hourlyRateLimit <= hourlyRateNormal)
                hourlyRate =hourlyRateLimit ;
            else
                hourlyRate = hourlyRateNormal;
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
        EditText eOt15 = findViewById(R.id.inputOt1);
        EditText eRate1 = findViewById(R.id.editRate1);
        TextView viewRate1 = findViewById(R.id.textOtRate1);


        if(eOt15.getText().toString().isEmpty() || hourlyRate == 0.0 || eRate1.getText().toString().isEmpty()) return 0.0;
        viewRate1.setText(eRate1.getText().toString());
        otPay15 = hourlyRate * Double.parseDouble(eRate1.getText().toString());
        Double dOt15 = Double.parseDouble(eOt15.getText().toString());
        return otPay15 * dOt15;
    }

    private void setOtPay15()
    {
        if(hourlyRate == 0.0) return;

        TextView tOtPay15 = findViewById(R.id.showOtPay1);
        tOtPay15.setText(df.format(calculateOtPay15()));
        TextView otPay15hrs = findViewById(R.id.otPay15hrs);
        String text = getString(R.string.per_hours, df.format(otPay15));
        otPay15hrs.setText(text);
    }


    private double calculateOtPay20()
    {
        EditText eOt20 = findViewById(R.id.inputOt2);
        EditText eRate2 = findViewById(R.id.editRate2);
        TextView viewRate2 = findViewById(R.id.textOtRate2);



        if(eOt20.getText().toString().isEmpty() || hourlyRate == 0.0 || eRate2.getText().toString().isEmpty()) return 0.0;
        viewRate2.setText(eRate2.getText().toString());
        otPay20 = hourlyRate * Double.parseDouble(eRate2.getText().toString());
        Double dOt20 = Double.parseDouble(eOt20.getText().toString());
        return otPay20 * dOt20;
    }

    private void setOtPay20()
    {
        if(hourlyRate == 0.0) return;

        TextView tOtPay20 = findViewById(R.id.showOtPay2);
        tOtPay20.setText(df.format(calculateOtPay20()));
        TextView otPay20hrs = findViewById(R.id.otPay20hrs);
        String text = getString(R.string.per_hours, df.format(otPay20));
        otPay20hrs.setText(text);

    }

    private double calculateOtPay30()
    {
        EditText eOt30 = findViewById(R.id.inputOt3);
        EditText eRate3 = findViewById(R.id.editRate3);
        TextView viewRate3 = findViewById(R.id.textOtRate3);


        if(eOt30.getText().toString().isEmpty() || hourlyRate == 0.0 || eRate3.getText().toString().isEmpty()) return 0.0;
        viewRate3.setText(eRate3.getText().toString());
        otPay30 = hourlyRate * Double.parseDouble(eRate3.getText().toString());
        Double dOt30 = Double.parseDouble(eOt30.getText().toString());
        return otPay30 * dOt30;
    }

    private void setOtPay30()
    {
        if(hourlyRate == 0.0) return;

        TextView tOtPay30 = findViewById(R.id.showOtPay3);
        tOtPay30.setText(df.format(calculateOtPay30()));
        TextView otPay30hrs = findViewById(R.id.otPay30hrs);
        String text = getString(R.string.per_hours, df.format(otPay30));
        otPay30hrs.setText(text);
    }

    private double calculateOtPayTotal()
    {
        return calculateOtPay15() + calculateOtPay20() + calculateOtPay30();
    }

    private void setOtPayTotal()
    {
        TextView tOtPayTotal = findViewById(R.id.showOtPayTotal);
        tOtPayTotal.setText(df.format(calculateOtPayTotal()));
    }

    private Double getOtherSalary(){
        EditText eOther = findViewById(R.id.inputOther);
        if(eOther.getText().toString().isEmpty()){
            return 0.0;
        }else{
            Double dOther = Double.parseDouble(eOther.getText().toString());
            return dOther;
        }
    }

    private void setTotalSalary() {
        TextView tTotalSalary = findViewById(R.id.showTotalSalary);
        EditText eBasicSalary = findViewById(R.id.inputBasicSalary);

        if(eBasicSalary.getText().toString().isEmpty()) {
            tTotalSalary.setText("0");
        }else{
            Double dBasicSalary = Double.parseDouble(eBasicSalary.getText().toString());
            tTotalSalary.setText(df.format(dBasicSalary + calculateOtPayTotal() + getOtherSalary()));
        }
    }

    //Set up HyperLink
    private void setupHyperLink(){
        TextView linkTextView = findViewById(R.id.mom_website);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}