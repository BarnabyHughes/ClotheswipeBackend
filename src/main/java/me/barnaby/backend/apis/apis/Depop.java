package me.barnaby.backend.apis.apis;

import me.barnaby.backend.apis.API;
import me.barnaby.backend.product.Product;
import me.barnaby.backend.product.productArgs.Price;
import me.barnaby.backend.product.productArgs.Seller;
import me.barnaby.backend.services.ProductService;
import org.json.JSONArray;
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

public class Depop implements API {
    @Override
    public String getData() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://depop-thrift.p.rapidapi.com/getSearch?page=1&countryCode=gb&sortBy=newlyListed"))
                .header("X-RapidAPI-Key", "59b399baaemsh621cea3eb6e5985p12530ajsndc5af0efaf10")
                .header("X-RapidAPI-Host", "depop-thrift.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
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

                long productId = jsonProduct.optLong("id", 0);
                String title = jsonProduct.optString("slug", "Unknown");
                URL url = parseURL(jsonProduct.optString("url", ""));

                JSONArray imagesArray = jsonProduct.optJSONArray("images");
                URL imageUrl = parseURL((imagesArray != null && imagesArray.length() > 0) ? imagesArray.getString(0) : "");

                String brand = jsonProduct.optString("brand", "");

                JSONArray sizesArray = jsonProduct.optJSONArray("sizes");
                String size = (sizesArray != null && sizesArray.length() > 0) ? sizesArray.getString(0) : "Unknown";

                JSONObject jsonPrice = jsonProduct.optJSONObject("price");
                Price price = parsePrice(jsonPrice);

                JSONObject jsonSeller = jsonProduct.optJSONObject("seller");
                Seller seller = parseSeller(jsonSeller);

                // Create a Product object only if productId is valid
                if (productId != 0) {
                    Product product = new Product(productId, title, url, imageUrl, -1, brand, size, price, seller);
                    productList.add(product);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }




    private Seller parseSeller(JSONObject jsonSeller) {
        String username = jsonSeller.optString("username", "Unknown");
        String profilePicString = "https://www.depop.com/" + username;
        String profileString = jsonSeller.optString("profile", "");

        URL profilePic = parseURL(profilePicString);
        URL profile = parseURL(profileString);

        return new Seller(username, profilePic, profile);
    }

    private Price parsePrice(JSONObject jsonPrice) {
        try {
            double amount = jsonPrice.optDouble("amount", 0.0);
            String currency = jsonPrice.optString("currency", "");
            double fees = jsonPrice.optDouble("fees", 0.0);

            return new Price(amount, currency, fees);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
