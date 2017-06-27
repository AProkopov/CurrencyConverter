package com.bugsnguns.currencyconverter;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {

    public String [] currencyNames;
    public Spinner currencySpinnerFrom;
    public Spinner currencySpinnerTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerBuilder();
    }

    //метод spinnerBuilder() заполняет раскрывающиеся списки значениями
    protected void spinnerBuilder(){

        //заполнение Spinner названиями валют
        currencyNames = getResources().getStringArray(R.array.currency_names);
        currencySpinnerFrom = (Spinner)findViewById(R.id.currencySpinnerFrom);
        currencySpinnerTo = (Spinner)findViewById(R.id.currencySpinnerTo);

        // Настраиваем адаптеры
        ArrayAdapter<?> adapterFrom =
                ArrayAdapter.createFromResource(this, R.array.currency_names, android.R.layout.simple_spinner_item);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<?> adapterTo =
                ArrayAdapter.createFromResource(this, R.array.currency_names, android.R.layout.simple_spinner_item);
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Вызываем адаптеры
        currencySpinnerFrom.setAdapter(adapterFrom);
        currencySpinnerTo.setAdapter(adapterTo);
    }
}


