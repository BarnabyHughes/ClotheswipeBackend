package me.barnaby.backend.apis.apis;

import me.barnaby.backend.apis.API;
import me.barnaby.backend.product.Product;
import me.barnaby.backend.product.productArgs.Price;
import me.barnaby.backend.product.productArgs.Seller;
import me.barnaby.backend.services.ProductService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Vinted implements API {

    @Override
    public String getData() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://vinted3.p.rapidapi.com/getSearch?country=us&page=1&order=newest_first"))
                .header("X-RapidAPI-Key", "59b399baaemsh621cea3eb6e5985p12530ajsndc5af0efaf10")
                .header("X-RapidAPI-Host", "vinted3.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addProducts(ProductService productService) {
        productService.addProducts(convertJsonToProductList(getData()));
    }

    private List<Product> convertJsonToProductList(String jsonString) {
        List<Product> productList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonProduct = jsonArray.getJSONObject(i);

                long productId = jsonProduct.optLong("productId", -1);
                String title = jsonProduct.optString("title", "Unknown");
                URL url = parseURL(jsonProduct.optString("url", ""));
                URL imageUrl = parseURL(jsonProduct.optString("image", ""));
                int likes = jsonProduct.optInt("favourites", -1);
                String brand = jsonProduct.optString("brand", "Unknown");

                String size = jsonProduct.optString("size", "Unknown");
                Price price = parsePrice(jsonProduct.getJSONObject("price"));
                Seller seller = parseSeller(jsonProduct.getJSONObject("seller"));

                // Create a Product object
                Product product = new Product(productId, title, url, imageUrl, likes, brand, size, price, seller);
                productList.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }



    private Price parsePrice(JSONObject jsonPrice) {
        double amount = jsonPrice.optDouble("amount", -1);
        String currency = jsonPrice.optString("currency", "?");
        double fees = jsonPrice.optDouble("fees");

        return new Price(amount, currency, fees);
    }

    private Seller parseSeller(JSONObject jsonSeller) {
        String username = jsonSeller.optString("username", "");
        URL profilePic = parseURL(jsonSeller.optString("profilePicture", ""));
        URL profile = parseURL(jsonSeller.optString("profile", ""));

        return new Seller(username, profilePic, profile);
    }

    private URL parseURL(String urlString) {
        try {
            return urlString.isEmpty() ? null : new URL(urlString);
        } catch (MalformedURLException e) {
            // Handle the case where the URL is not valid
            e.printStackTrace();
            return null;
        }
    }


}
