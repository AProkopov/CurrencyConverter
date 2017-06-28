package com.bugsnguns.currencyconverter.Model;

/**
 * Created by Antonio on 25.06.2017.
 */

//Валюта (для заполнения данными из xml-файла ЦБ)
public class Currency {

    private int NumCode;

    private String charCode;

    private int nominal;

    private String name;

    private String value;

    public int getNumCode() {
        return NumCode;
    }

    public void setNumCode(int numCode) {
        NumCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
