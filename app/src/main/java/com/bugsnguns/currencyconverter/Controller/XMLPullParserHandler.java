package com.bugsnguns.currencyconverter.Controller;

import android.util.Log;
import com.bugsnguns.currencyconverter.Model.Currency;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 28.06.2017.
 */

//Парсер xml-файла с данными у курсах валют
//Позволяет получить все данные о валютах, предоставляемых ЦБ в xml-файле
public class XMLPullParserHandler {

    List<Currency> currencies;

    private Currency currency;
    private String text;
    public Currency rubCurrency;
    private final String XMLURI = "currency.xml";
    private final String XMLURL = "http://www.cbr.ru/scripts/XML_daily.asp";
    private InputStream stream;
    public boolean connectionSuccessfull = false;
    public String TAG = "TEST";


    public String getXMLURI() {
        return XMLURI;
    }

    public String getXMLURL() {
        return XMLURL;
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream (InputStream inputStream) {
        stream = inputStream;
    }

    public XMLPullParserHandler() {
        currencies = new ArrayList<Currency>();
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    //парсим XML. Создаваемые объекты класса Currency (валюта) сохраняются в List
    public List<Currency> parse(InputStream is) {

        //Костыль для рубля, т.к. xml-файл от ЦБ не содержит информации о нем
        rubCurrency = new Currency();
        rubCurrency.setNumCode(1);
        rubCurrency.setCharCode("RUB");
        rubCurrency.setNominal(1);
        rubCurrency.setName("Российский рубль");
        rubCurrency.setValue("1");

        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            parser = factory.newPullParser();
            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:

                        if(tagname.equalsIgnoreCase("Valute")) {
                            currency = new Currency();
                        }
                        break;

                    case XmlPullParser.TEXT:

                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if(tagname.equalsIgnoreCase("Valute")) {
                            currencies.add(currency);
                        }else if(tagname.equalsIgnoreCase("NumCode")) {
                            currency.setNumCode(Integer.parseInt(text));
                        }else if(tagname.equalsIgnoreCase("CharCode")) {
                            currency.setCharCode(text);
                        }else if(tagname.equalsIgnoreCase("Nominal")) {
                            currency.setNominal(Integer.parseInt(text));
                        }else if(tagname.equalsIgnoreCase("Name")) {
                            currency.setName(text);
                        }else if(tagname.equalsIgnoreCase("Value")) {
                            currency.setValue(text);
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        currencies.add(rubCurrency);
        return currencies;
    }

    //получаем XML по URL
    public void fetchXML() {
        Log.v(TAG, "fetchXML() is called");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(getXMLURL());
                    HttpURLConnection connect = (HttpURLConnection)url.openConnection();
                    connect.setReadTimeout(10000);
                    connect.setConnectTimeout(15000);
                    connect.setRequestMethod("GET");
                    connect.setDoInput(true);
                    connect.connect();

                    InputStream stream = connect.getInputStream();
                    Log.v(TAG, "InputStream Stream is created");
                    setStream(stream);
                    connectionSuccessfull = true;
                    Log.v(TAG, "connectedSuccessfull is true");

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        thread.run();
    }
}
