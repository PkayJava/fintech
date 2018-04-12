package com.angkorteam.fintech.dto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class JsonNode extends io.github.openunirest.http.JsonNode {

    private JSONObject jsonObject;

    private JSONArray jsonArray;

    private boolean array;

    public JsonNode() {
        this(new JSONObject());
    }

    public JsonNode(JSONObject jsonObject) {
        super(null);
        this.jsonObject = jsonObject;
        this.array = false;
    }

    public JsonNode(JSONArray jsonArray) {
        super(null);
        this.jsonArray = jsonArray;
        this.array = true;
    }

    public JsonNode(String json) {
        super(json);
        if (json != null && !"".equals(json.trim())) {
            try {
                this.jsonObject = new JSONObject(json);
            } catch (JSONException var5) {
                try {
                    this.jsonArray = new JSONArray(json);
                    this.array = true;
                } catch (JSONException var4) {
                    throw new RuntimeException(var4);
                }
            }
        } else {
            this.jsonObject = new JSONObject();
        }

    }

    public JSONObject getObject() {
        return this.jsonObject;
    }

    public JSONArray getArray() {
        JSONArray result = this.jsonArray;
        if (!this.array) {
            result = new JSONArray();
            result.put(this.jsonObject);
        }

        return result;
    }

    public boolean isArray() {
        return this.array;
    }

    public String toString() {
        return this.isArray() ? (this.jsonArray == null ? null : this.jsonArray.toString()) : (this.jsonObject == null ? null : this.jsonObject.toString());
    }
}
