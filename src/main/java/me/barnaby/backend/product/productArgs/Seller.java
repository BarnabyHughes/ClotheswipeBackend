package me.barnaby.backend.product.productArgs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.net.URL;

@AllArgsConstructor @Getter
public class Seller implements Serializable {

    String username;
    URL profilePic;
    URL profile;

}
