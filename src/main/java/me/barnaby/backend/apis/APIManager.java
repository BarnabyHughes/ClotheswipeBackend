package me.barnaby.backend.apis;

import lombok.AllArgsConstructor;
import me.barnaby.backend.BackendApplication;

@AllArgsConstructor
public class APIManager {

    BackendApplication application;
    public String getStringData(APIType apiType) {
        return apiType.getApi().getData();
    }

    public void addProducts(APIType apiType) {
        apiType.getApi().addProducts(application.getProductService());
    }

    public void initializeAPIs() {
        addProducts(APIType.VINTED);
        addProducts(APIType.DEPOP);

    }

}
