package com.bugsnguns.currencyconverter;

import android.util.Log;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Antonio on 25.06.2017.
 */

@Root(name="Valute")
public class Currency {

    @Element (name = "CharCode")
    public String charCode;

    @Element (name = "Nominal")
    public int nominal;

    @Element (name = "Name")
    public String name;

    @Element (name = "Value")
    public double value;

    //конструктор
    public Currency (String charCode, int nominal, String name, double value){
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        Log.v("currency created", name);
    }


}
