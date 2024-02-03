package me.barnaby.backend.services;

import me.barnaby.backend.product.Product;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> productList = new ArrayList<>();

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void addProducts(List<Product> products) {
        productList.addAll(products);
    }


    public void initializeProducts(List<Product> products) {
        this.productList.addAll(products);
        System.out.println("ProductService initialized with " + productList.size() + " products");
    }

    public List<Product> getPaginatedProducts(int page, int size) {
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, productList.size());

        if (startIndex >= productList.size()) {
            // Handle the case where the requested page is beyond the available data
            return List.of();
        }

        return productList.subList(startIndex, endIndex);
    }
}
