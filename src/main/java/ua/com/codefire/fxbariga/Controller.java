package ua.com.codefire.fxbariga;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.io.IOUtils;
import org.json.simple.DeserializationException;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static final String API_URL = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

    @FXML
    private Button btnBuy;
    @FXML
    private Button btnSale;
    @FXML
    private TextField resultField;
    @FXML
    private TextField amountField;
    @FXML
    private TextField margField;
    @FXML
    private ComboBox comboBox;

    private List<Currency> currencies;
    @FXML
    private void initialize() {
        currencies = new ArrayList<>();

        try {
            String resp_json = IOUtils.toString(new URL(API_URL), "UTF-8");
            JsonArray data = (JsonArray) Jsoner.deserialize(resp_json);

            for (Object obj : data) {
                JsonObject currency = (JsonObject) obj;

                // Get data
                String currencyName = currency.getString("ccy");
                double buy = currency.getDouble("buy");
                double sale = currency.getDouble("sale");

                // Create currency object
                Currency curr = new Currency(currencyName, buy, sale);

                // Add currency object
                currencies.add(curr);

            }

        } catch (IOException | DeserializationException e) {
            e.printStackTrace();
        }

        // Create currency list
        ObservableList<String> currencyItems = FXCollections.observableArrayList();

        for (Currency c : currencies) {
            currencyItems.add(c.getName());
        }
        comboBox.setItems(currencyItems);
    }

    public void onBuyClick() {
        double amount = Double.parseDouble(amountField.getText());
        double result = .0;
        double margin = .0;
        Currency currency = currencies.get(comboBox.getSelectionModel().getSelectedIndex());
        double buy = currency.getBuy();
        result = amount * buy * 1.03;
        margin = result * 0.03;
        resultField.setText(Double.toString(result));
        margField.setText(Double.toString(margin));
    }

    public void onSaleClick() {
        double amount = Double.parseDouble(amountField.getText());
        double margin = .0;
        double result = .0;
        Currency currency = currencies.get(comboBox.getSelectionModel().getSelectedIndex());
        double sale = currency.getSale();
        result = amount * sale * 1.03;
        margin = result * 0.03;
        resultField.setText(Double.toString(result));
        margField.setText(Double.toString(margin));
    }
}

