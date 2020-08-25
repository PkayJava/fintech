package com.angkorteam.fintech.client.dto;

import java.util.ArrayList;
import java.util.List;

public class PutCurrenciesRequest {

    private List<String> currencies = new ArrayList<>();

    public List<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
    }

}
