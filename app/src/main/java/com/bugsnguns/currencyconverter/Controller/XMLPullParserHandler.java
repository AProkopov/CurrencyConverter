package com.bugsnguns.currencyconverter.Controller;

import com.bugsnguns.currencyconverter.Model.Currency;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
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

    public String getXMLURI() {
        return XMLURI;
    }

    public XMLPullParserHandler() {
        currencies = new ArrayList<Currency>();
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

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
}
