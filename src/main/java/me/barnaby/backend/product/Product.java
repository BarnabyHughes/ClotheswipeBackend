package me.barnaby.backend.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.barnaby.backend.product.productArgs.Price;
import me.barnaby.backend.product.productArgs.Seller;

import java.net.URL;

@AllArgsConstructor @Getter
public class Product {


    private long productId;
    private String name;
    private URL url;
    private URL imageUrl;
    private int likes;
    private String brand;
    private String size;
    private Price price;
    private Seller seller;

    //public Product (long productId, String name, URL url, URL imageUrl, int likes, String brand, Size size, Price price, Seller seller) {


  //  }






}
