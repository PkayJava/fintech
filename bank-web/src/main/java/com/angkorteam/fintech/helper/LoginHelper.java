package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.MifosDataSourceManager;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.google.gson.Gson;
import io.github.openunirest.http.JsonNode;
import io.github.openunirest.http.Unirest;
import io.github.openunirest.http.exceptions.UnirestException;
import org.apache.wicket.WicketRuntimeException;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class LoginHelper {

    public static JsonNode authenticate(String identifier, String username, String password) {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataSourceManager mifos = context.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl();
        return authenticate(mifosUrl, identifier, username, password);
    }

    public static JsonNode authenticate(String mifosUrl, String identifier, String username, String password) {
        ApplicationContext context = WicketFactory.getApplicationContext();
        Gson gson = context.getBean(Gson.class);
        String url = mifosUrl + "/api/v1/authentication";
        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        try {
            return Unirest.post(url).header("Fineract-Platform-TenantId", identifier).header("Content-Type", "application/json").body(gson.toJson(body)).asJson().getBody();
        } catch (UnirestException e) {
            throw new WicketRuntimeException(e);
        }
    }

}
