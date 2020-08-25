package com.angkorteam.fintech.client.dto;

import java.util.HashMap;
import java.util.Map;

public class DataTable {

    private String registeredTableName;

    private Map<String, String> data = new HashMap<>();

    public String getRegisteredTableName() {
        return registeredTableName;
    }

    public void setRegisteredTableName(String registeredTableName) {
        this.registeredTableName = registeredTableName;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

}