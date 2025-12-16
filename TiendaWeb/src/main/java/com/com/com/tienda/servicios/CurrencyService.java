package com.com.com.tienda.servicios;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyService {

    private static final Dotenv dotenv = Dotenv.load();
   private static final String API_KEY = dotenv.get("EXCHANGE_API_KEY");


    static {
        if (API_KEY == null || API_KEY.isBlank()) {
            System.err.println("❌ API_KEY no cargada");
        } else {
            System.out.println("✅ API_KEY cargada");
        }
    }

    private static final String API_URL =
        "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    public static double convertir(double precioUSD, String moneda) {

        if (moneda == null || moneda.equals("USD")) {
            return precioUSD;
        }

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            JsonObject json = JsonParser
                .parseReader(new InputStreamReader(conn.getInputStream()))
                .getAsJsonObject();

            double tasa = json
                .getAsJsonObject("conversion_rates")
                .get(moneda)
                .getAsDouble();

            return precioUSD * tasa;

        } catch (Exception e) {
            e.printStackTrace();
            return precioUSD;
        }
    }
}
