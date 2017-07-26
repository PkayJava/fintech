package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.MifosDataSourceManager;
import com.angkorteam.framework.SpringBean;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class LoginHelper {

    public static JsonNode authenticate(String identifier, String username, String password) throws UnirestException {
        MifosDataSourceManager mifos = SpringBean.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl() + "/api/v1/authentication?username=" + username + "&password=" + password;
        return Unirest.post(mifosUrl).header("Fineract-Platform-TenantId", identifier)
                .header("Content-Type", "application/json").asJson().getBody();
    }

}
