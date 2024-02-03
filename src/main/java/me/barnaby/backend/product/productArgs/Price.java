package me.barnaby.backend.product.productArgs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor @Getter
public class Price implements Serializable {

    double price;
    String currency;
    double fees;
}
