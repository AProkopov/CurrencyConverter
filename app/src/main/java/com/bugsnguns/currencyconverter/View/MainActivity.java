package com.bugsnguns.currencyconverter.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.bugsnguns.currencyconverter.Model.Currency;
import com.bugsnguns.currencyconverter.R;
import com.bugsnguns.currencyconverter.Controller.XMLPullParserHandler;
import java.math.BigDecimal;
import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public String [] currencyNames;
    public Spinner currencySpinnerFrom;
    public Spinner currencySpinnerTo;
    public List<Currency> currencies;
    public Currency currencyFrom;
    public Currency currencyTo;
    public ImageView imageFrom;
    public ImageView imageTo;
    public TextView textViewFrom;
    public TextView textViewTo;
    public EditText editText;
    public TextView resultView;
    public XMLPullParserHandler parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerBuilder();

        //создаем элементы для работы с их содержимым во время работы с MainActivity
        imageFrom = (ImageView) findViewById(R.id.imageFrom);
        imageTo = (ImageView) findViewById(R.id.imageTo);
        textViewFrom = (TextView) findViewById(R.id.textViewFrom);
        textViewTo = (TextView) findViewById(R.id.textViewTo);
        editText = (EditText) findViewById(R.id.editText);
        resultView = (TextView) findViewById(R.id.resultView);

        //парсим xml-файл, содержащий информацию о курсах вылют
        try {
            parser = new XMLPullParserHandler();
            parser.fetchXML();

            if (parser.connectionSuccessfull) {
                currencies.clear();
                currencies = parser.parse(parser.getStream());
            }

            else {
                currencies = parser.parse(getAssets().open(parser.getXMLURI()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //обработка нажатия кнопки Convert
    public void onConvertClick (View view) throws Exception{

        //конвертация и вывод результата
        try {
            double valueFrom = Double.parseDouble(currencyFrom.getValue().replace(",", "."));
            double valueTo = Double.parseDouble(currencyTo.getValue().replace(",", "."));
            double amount = Double.parseDouble(editText.getText().toString());
            BigDecimal result = (BigDecimal.valueOf((valueFrom / currencyFrom.getNominal() /
                    (valueTo / currencyTo.getNominal())) * amount)).setScale(2, BigDecimal.ROUND_UP);
            resultView.setText(result.toString());

            //проверяем, был ли получен InputStream stream по URL. Если нет, пробуем получить.
            if (!parser.connectionSuccessfull) {
                parser.fetchXML();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

        //обработчик выбора элемента Spinner (From)
        currencySpinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosenStr = currencySpinnerFrom.getSelectedItem().toString();

                switch (chosenStr) {
                    case "AUD":
                        currencyFrom = currencies.get(0);
                        imageFrom.setImageResource(R.mipmap.aud);
                        break;
                    case "AZN":
                        currencyFrom = currencies.get(1);
                        imageFrom.setImageResource(R.mipmap.azn);
                        break;
                    case "GBP":
                        currencyFrom = currencies.get(2);
                        imageFrom.setImageResource(R.mipmap.gbp);
                        break;
                    case "AMD":
                        currencyFrom = currencies.get(3);
                        imageFrom.setImageResource(R.mipmap.amd);
                        break;
                    case "BYN":
                        currencyFrom = currencies.get(4);
                        imageFrom.setImageResource(R.mipmap.byn);
                        break;
                    case "BGN":
                        currencyFrom = currencies.get(5);
                        imageFrom.setImageResource(R.mipmap.bgn);
                        break;
                    case "BRL":
                        currencyFrom = currencies.get(6);
                        imageFrom.setImageResource(R.mipmap.brl);
                        break;
                    case "HUF":
                        currencyFrom = currencies.get(7);
                        imageFrom.setImageResource(R.mipmap.huf);
                        break;
                    case "HKD":
                        currencyFrom = currencies.get(8);
                        imageFrom.setImageResource(R.mipmap.hkd);
                        break;
                    case "DKK":
                        currencyFrom = currencies.get(9);
                        imageFrom.setImageResource(R.mipmap.dkk);
                        break;
                    case "USD":
                        currencyFrom = currencies.get(10);
                        imageFrom.setImageResource(R.mipmap.usd);
                        break;
                    case "EUR":
                        currencyFrom = currencies.get(11);
                        imageFrom.setImageResource(R.mipmap.eur);
                        break;
                    case "INR":
                        currencyFrom = currencies.get(12);
                        imageFrom.setImageResource(R.mipmap.inr);
                        break;
                    case "KZT":
                        currencyFrom = currencies.get(13);
                        imageFrom.setImageResource(R.mipmap.kzt);
                        break;
                    case "CAD":
                        currencyFrom = currencies.get(14);
                        imageFrom.setImageResource(R.mipmap.cad);
                        break;
                    case "KGS":
                        currencyFrom = currencies.get(15);
                        imageFrom.setImageResource(R.mipmap.kgs);
                        break;
                    case "CNY":
                        currencyFrom = currencies.get(16);
                        imageFrom.setImageResource(R.mipmap.cny);
                        break;
                    case "MDL":
                        currencyFrom = currencies.get(17);
                        imageFrom.setImageResource(R.mipmap.mdl);
                        break;
                    case "NOK":
                        currencyFrom = currencies.get(18);
                        imageFrom.setImageResource(R.mipmap.nok);
                        break;
                    case "PLN":
                        currencyFrom = currencies.get(19);
                        imageFrom.setImageResource(R.mipmap.pln);
                        break;
                    case "RON":
                        currencyFrom = currencies.get(20);
                        imageFrom.setImageResource(R.mipmap.ron);
                        break;
                    case "XDR":
                        currencyFrom = currencies.get(21);
                        imageFrom.setImageDrawable(null);
                        break;
                    case "SGD":
                        currencyFrom = currencies.get(22);
                        imageFrom.setImageResource(R.mipmap.sgd);
                        break;
                    case "TJS":
                        currencyFrom = currencies.get(23);
                        imageFrom.setImageResource(R.mipmap.tjs);
                        break;
                    case "TRY":
                        currencyFrom = currencies.get(24);
                        imageFrom.setImageResource(R.mipmap.tur);
                        break;
                    case "TMT":
                        currencyFrom = currencies.get(25);
                        imageFrom.setImageResource(R.mipmap.tmt);
                        break;
                    case "UZS":
                        currencyFrom = currencies.get(26);
                        imageFrom.setImageResource(R.mipmap.uzs);
                        break;
                    case "UAH":
                        currencyFrom = currencies.get(27);
                        imageFrom.setImageResource(R.mipmap.uah);
                        break;
                    case "CZK":
                        currencyFrom = currencies.get(28);
                        imageFrom.setImageResource(R.mipmap.czk);
                        break;
                    case "SEK":
                        currencyFrom = currencies.get(29);
                        imageFrom.setImageResource(R.mipmap.sek);
                        break;
                    case "CHF":
                        currencyFrom = currencies.get(30);
                        imageFrom.setImageResource(R.mipmap.chf);
                        break;
                    case "ZAR":
                        currencyFrom = currencies.get(31);
                        imageFrom.setImageResource(R.mipmap.zar);
                        break;
                    case "KRW":
                        currencyFrom = currencies.get(32);
                        imageFrom.setImageResource(R.mipmap.krw);
                        break;
                    case "JPY":
                        currencyFrom = currencies.get(33);
                        imageFrom.setImageResource(R.mipmap.jpy);
                        break;
                    case "RUB":
                        currencyFrom = currencies.get(34);
                        imageFrom.setImageResource(R.mipmap.rus);
                        break;
                    }

                textViewFrom.setText(currencyFrom.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //обработчик выбора элемента Spinner (To)
        currencySpinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosenStr = currencySpinnerTo.getSelectedItem().toString();

                switch (chosenStr) {
                    case "AUD":
                        currencyTo = currencies.get(0);
                        imageTo.setImageResource(R.mipmap.aud);
                        break;
                    case "AZN":
                        currencyTo = currencies.get(1);
                        imageTo.setImageResource(R.mipmap.azn);
                        break;
                    case "GBP":
                        currencyTo = currencies.get(2);
                        imageTo.setImageResource(R.mipmap.gbp);
                        break;
                    case "AMD":
                        currencyTo = currencies.get(3);
                        imageTo.setImageResource(R.mipmap.amd);
                        break;
                    case "BYN":
                        currencyTo = currencies.get(4);
                        imageTo.setImageResource(R.mipmap.byn);
                        break;
                    case "BGN":
                        currencyTo = currencies.get(5);
                        imageTo.setImageResource(R.mipmap.bgn);
                        break;
                    case "BRL":
                        currencyTo = currencies.get(6);
                        imageTo.setImageResource(R.mipmap.brl);
                        break;
                    case "HUF":
                        currencyTo = currencies.get(7);
                        imageTo.setImageResource(R.mipmap.huf);
                        break;
                    case "HKD":
                        currencyTo = currencies.get(8);
                        imageTo.setImageResource(R.mipmap.hkd);
                        break;
                    case "DKK":
                        currencyTo = currencies.get(9);
                        imageTo.setImageResource(R.mipmap.dkk);
                        break;
                    case "USD":
                        currencyTo = currencies.get(10);
                        imageTo.setImageResource(R.mipmap.usd);
                        break;
                    case "EUR":
                        currencyTo = currencies.get(11);
                        imageTo.setImageResource(R.mipmap.eur);
                        break;
                    case "INR":
                        currencyTo = currencies.get(12);
                        imageTo.setImageResource(R.mipmap.inr);
                        break;
                    case "KZT":
                        currencyTo = currencies.get(13);
                        imageTo.setImageResource(R.mipmap.kzt);
                        break;
                    case "CAD":
                        currencyTo = currencies.get(14);
                        imageTo.setImageResource(R.mipmap.cad);
                        break;
                    case "KGS":
                        currencyTo = currencies.get(15);
                        imageTo.setImageResource(R.mipmap.kgs);
                        break;
                    case "CNY":
                        currencyTo = currencies.get(16);
                        imageTo.setImageResource(R.mipmap.cny);
                        break;
                    case "MDL":
                        currencyTo = currencies.get(17);
                        imageTo.setImageResource(R.mipmap.mdl);
                        break;
                    case "NOK":
                        currencyTo = currencies.get(18);
                        imageTo.setImageResource(R.mipmap.nok);
                        break;
                    case "PLN":
                        currencyTo = currencies.get(19);
                        imageTo.setImageResource(R.mipmap.pln);
                        break;
                    case "RON":
                        currencyTo = currencies.get(20);
                        imageTo.setImageResource(R.mipmap.ron);
                        break;
                    case "XDR":
                        currencyTo = currencies.get(21);
                        imageTo.setImageDrawable(null);
                        break;
                    case "SGD":
                        currencyTo = currencies.get(22);
                        imageTo.setImageResource(R.mipmap.sgd);
                        break;
                    case "TJS":
                        currencyTo = currencies.get(23);
                        imageTo.setImageResource(R.mipmap.tjs);
                        break;
                    case "TRY":
                        currencyTo = currencies.get(24);
                        imageTo.setImageResource(R.mipmap.tur);
                        break;
                    case "TMT":
                        currencyTo = currencies.get(25);
                        imageTo.setImageResource(R.mipmap.tmt);
                        break;
                    case "UZS":
                        currencyTo = currencies.get(26);
                        imageTo.setImageResource(R.mipmap.uzs);
                        break;
                    case "UAH":
                        currencyTo = currencies.get(27);
                        imageTo.setImageResource(R.mipmap.uah);
                        break;
                    case "CZK":
                        currencyTo = currencies.get(28);
                        imageTo.setImageResource(R.mipmap.czk);
                        break;
                    case "SEK":
                        currencyTo = currencies.get(29);
                        imageTo.setImageResource(R.mipmap.sek);
                        break;
                    case "CHF":
                        currencyTo = currencies.get(30);
                        imageTo.setImageResource(R.mipmap.chf);
                        break;
                    case "ZAR":
                        currencyTo = currencies.get(31);
                        imageTo.setImageResource(R.mipmap.zar);
                        break;
                    case "KRW":
                        currencyTo = currencies.get(32);
                        imageTo.setImageResource(R.mipmap.krw);
                        break;
                    case "JPY":
                        currencyTo = currencies.get(33);
                        imageTo.setImageResource(R.mipmap.jpy);
                        break;
                    case "RUB":
                        currencyTo = currencies.get(34);
                        imageTo.setImageResource(R.mipmap.rus);
                        break;
                }

                textViewTo.setText(currencyTo.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}


