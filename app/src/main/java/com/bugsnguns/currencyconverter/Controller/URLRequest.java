package com.bugsnguns.currencyconverter.Controller;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Antonio on 28.06.2017.
 */

//Класс для получения данных (xml) по URL
public class URLRequest {

    private final String LINK = "http://www.cbr.ru/scripts/XML_daily.asp";
    private boolean successfullConnection = false;

    public String getLINK() {
        return LINK;
    }

    public InputStream URLInputStream(String link) {

        InputStream inputStream = null;

        try {
            inputStream = new URL(link).openStream();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public void setConnectionResult() {
        successfullConnection = true;
    }
}
