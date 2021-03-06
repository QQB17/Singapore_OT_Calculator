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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Locale;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Double hourlyRate, hourlyRateLimit, hourlyRateNormal = 0.0, jobTypeLimit = 0.0;
    private Double otPay15, otPay20 ;
    private final Double workerLimit = 4500.0, clerkLimit = 2600.0;
    private DecimalFormat df =new DecimalFormat("#,###,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupHyperLink();

        //Select Job Type to change to OTPay Limit
        RadioGroup radioJobType = findViewById(R.id.radioJobTypes);
        radioJobType.clearCheck();
        radioJobType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton select = group.findViewById(checkedId);
                if(null != select && checkedId != -1){
                   switch(select.getId()){
                       case R.id.selectedWorker:
                           jobTypeLimit = workerLimit; break;
                       case R.id.selectedClerk:
                           jobTypeLimit = clerkLimit; break;
                   }
                }
            }
        });

        //Main
        Button calculateButton = findViewById(R.id.buttonCalculate);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jobTypeLimit != 0.0) {
                    calculateHourlyBasicRate();
                    setHourlyBasicRate();
                    setOtPay15();
                    setOtPay20();
                    setOtPayTotal();
                    setTotalSalary();
                }else{
                    Toast.makeText(getApplicationContext(), getString(R.string.please_select_you_job_type), Toast.LENGTH_SHORT).show();
                }
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

    //Calculate Hourly Basic Rate
    private void calculateHourlyBasicRate()
    {
        EditText eBasicSalary = findViewById(R.id.inputBasicSalary);

        if(!(eBasicSalary.getText().toString().isEmpty())) {
            Double dBasicSalary = Double.parseDouble(eBasicSalary.getText().toString());
            hourlyRateLimit = (12 * jobTypeLimit) / (52 * 44);
            hourlyRateNormal = (12 * dBasicSalary) / (52 * 44);
            if(hourlyRateLimit <= hourlyRateNormal) hourlyRate =hourlyRateLimit ;
                    else hourlyRate = hourlyRateNormal;
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
        EditText eOt15 = findViewById(R.id.inputOt15);
        otPay15 = hourlyRate * 1.5;
        if(eOt15.getText().toString().isEmpty() || hourlyRate == 0.0) return 0.0;
        Double dOt15 = Double.parseDouble(eOt15.getText().toString());
        return otPay15 * dOt15;
    }

    private void setOtPay15()
    {
        TextView tOtPay15 = findViewById(R.id.showOtPay15);
        tOtPay15.setText(df.format(calculateOtPay15()));
        TextView otPay15hrs = findViewById(R.id.otPay15hrs);
        String text = getString(R.string.per_hours, df.format(otPay15));
        otPay15hrs.setText(text);

        if(hourlyRate == 0.0){
            otPay15hrs.setText("");
        }
    }


    private double calculateOtPay20()
    {
        EditText eOt20 = findViewById(R.id.inputOt20);
        otPay20 = hourlyRate * 2.0;
        if(eOt20.getText().toString().isEmpty() || hourlyRate == 0.0) return 0.0;
        Double dOt20 = Double.parseDouble(eOt20.getText().toString());
        return otPay20 * dOt20;
    }

    private void setOtPay20()
    {
        TextView tOtPay20 = findViewById(R.id.showOtPay20);
        tOtPay20.setText(df.format(calculateOtPay20()));
        TextView otPay20hrs = findViewById(R.id.otPay20hrs);
        String text = getString(R.string.per_hours, df.format(otPay20));
        otPay20hrs.setText(text);

        if(hourlyRate == 0.0){
            otPay20hrs.setText("");
        }
    }

    private double calculateOtPayTotal()
    {
        return calculateOtPay15() + calculateOtPay20();
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