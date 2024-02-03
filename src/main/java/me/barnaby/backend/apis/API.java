package me.barnaby.backend.apis;

import me.barnaby.backend.services.ProductService;

public interface API {
    String getData();

    void addProducts(ProductService productService);
}