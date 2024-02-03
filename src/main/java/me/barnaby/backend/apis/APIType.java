package me.barnaby.backend.apis;

import me.barnaby.backend.apis.apis.Depop;
import me.barnaby.backend.apis.apis.Vinted;

public enum APIType {
    VINTED(new Vinted()),
    DEPOP(new Depop());
    //API2(new Api2());
    // Add more API enums as needed

    private final API api;

    APIType(API api) {
        this.api = api;
    }

    public API getApi() {
        return api;
    }
}

