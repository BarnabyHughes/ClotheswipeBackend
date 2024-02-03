package me.barnaby.backend.apis;

import me.barnaby.backend.apis.apis.Depop;
import me.barnaby.backend.apis.apis.Vinted;

public enum APIType {
    VINTED(new Vinted()),
    DEPOP(new Depop());

    private final API api;

    APIType(API api) {
        this.api = api;
    }

    public API getApi() {
        return api;
    }
}

