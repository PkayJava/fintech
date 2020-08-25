package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.MifosDataSourceManager;
import com.angkorteam.webui.frmk.common.WicketFactory;
import io.github.openunirest.http.HttpResponse;
import io.github.openunirest.http.JsonNode;
import io.github.openunirest.http.Unirest;
import io.github.openunirest.http.exceptions.UnirestException;
import org.apache.wicket.WicketRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.InputStream;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class Helper {

    private static final Logger LOGGER = LoggerFactory.getLogger(Helper.class);

    public static JsonNode performServerPost(IMifos session, String url, JsonNode body) throws WicketRuntimeException {
        return performServerPost(session.getIdentifier(), session.getToken(), url, body);
    }

    public static JsonNode performServerPost(IMifos session, String url, String body) throws WicketRuntimeException {
        return performServerPost(session.getIdentifier(), session.getToken(), url, body);
    }

    public static JsonNode performServerPost(String identifier, String token, String url, JsonNode body) throws WicketRuntimeException {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataSourceManager mifos = context.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl() + url;
        try {
            JsonNode response = Unirest.post(mifosUrl).header("Authorization", "Basic " + token).header("Fineract-Platform-TenantId", identifier).header("Content-Type", "application/json").body(body).asJson().getBody();
            if (hasError(response)) {
                LOGGER.info("RQ {}", body.toString());
                LOGGER.info("RS {}", response.toString());
            }
            return response;
        } catch (UnirestException e) {
            throw new WicketRuntimeException(e);
        }
    }

    public static JsonNode performServerPost(String identifier, String token, String url, String body) throws WicketRuntimeException {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataSourceManager mifos = context.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl() + url;
        try {
            JsonNode response = Unirest.post(mifosUrl).header("Authorization", "Basic " + token).header("Fineract-Platform-TenantId", identifier).header("Content-Type", "application/json").body(body).asJson().getBody();
            if (hasError(response)) {
                LOGGER.info("RQ {}", body.toString());
                LOGGER.info("RS {}", response.toString());
            }
            return response;
        } catch (UnirestException e) {
            throw new WicketRuntimeException(e);
        }
    }

    public static JsonNode performServerPost(IMifos session, String url) throws WicketRuntimeException {
        return performServerPost(session.getIdentifier(), session.getToken(), url);
    }

    public static JsonNode performServerPost(String identifier, String token, String url) throws WicketRuntimeException {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataSourceManager mifos = context.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl() + url;
        try {
            JsonNode response = Unirest.post(mifosUrl).header("Authorization", "Basic " + token).header("Fineract-Platform-TenantId", identifier).header("Content-Type", "application/json").asJson().getBody();
            if (hasError(response)) {
                LOGGER.info("RQ {}", "NULL");
                LOGGER.info("RS {}", response.toString());
            }
            return response;
        } catch (UnirestException e) {
            throw new WicketRuntimeException(e);
        }
    }

    public static HttpResponse<InputStream> performServerGet(IMifos session, String url) throws WicketRuntimeException {
        return performServerGet(session.getIdentifier(), session.getToken(), url);
    }

    public static JsonNode performServerGetJsonNode(IMifos session, String url) throws WicketRuntimeException {
        return performServerGetJsonNode(session.getIdentifier(), session.getToken(), url);
    }

    public static HttpResponse<InputStream> performServerGet(String identifier, String token, String url) throws WicketRuntimeException {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataSourceManager mifos = context.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl() + url;
        try {
            HttpResponse<InputStream> response = Unirest.get(mifosUrl).header("Authorization", "Basic " + token).header("Fineract-Platform-TenantId", identifier).asBinary();
            return response;
        } catch (UnirestException e) {
            throw new WicketRuntimeException(e);
        }
    }

    public static JsonNode performServerGetJsonNode(String identifier, String token, String url) throws WicketRuntimeException {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataSourceManager mifos = context.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl() + url;
        try {
            HttpResponse<JsonNode> response = Unirest.get(mifosUrl).header("Authorization", "Basic " + token).header("Fineract-Platform-TenantId", identifier).asJson();
            return response.getBody();
        } catch (UnirestException e) {
            throw new WicketRuntimeException(e);
        }
    }

    public static JsonNode performServerDelete(IMifos session, String url) throws WicketRuntimeException {
        return performServerDelete(session.getIdentifier(), session.getToken(), url);
    }

    public static JsonNode performServerDelete(String identifier, String token, String url) throws WicketRuntimeException {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataSourceManager mifos = context.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl() + url;
        try {
            JsonNode response = Unirest.delete(mifosUrl).header("Authorization", "Basic " + token).header("Fineract-Platform-TenantId", identifier).header("Content-Type", "application/json").asJson().getBody();
            if (hasError(response)) {
                LOGGER.info("RQ {}", "NULL");
                LOGGER.info("RS {}", response.toString());
            }
            return response;
        } catch (UnirestException e) {
            throw new WicketRuntimeException(e);
        }
    }

    public static JsonNode performServerPut(IMifos session, String url, JsonNode body) throws WicketRuntimeException {
        return performServerPut(session.getIdentifier(), session.getToken(), url, body);
    }

    public static JsonNode performServerPut(String identifier, String token, String url, JsonNode body) throws WicketRuntimeException {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataSourceManager mifos = context.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl() + url;
        try {
            JsonNode response = Unirest.put(mifosUrl).header("Authorization", "Basic " + token).header("Fineract-Platform-TenantId", identifier).header("Content-Type", "application/json").body(body).asJson().getBody();
            if (hasError(response)) {
                LOGGER.info("RQ {}", body.toString());
                LOGGER.info("RS {}", response.toString());
            }
            return response;
        } catch (UnirestException e) {
            throw new WicketRuntimeException(e);
        }
    }

    public static boolean hasError(JsonNode node) {
        if (node.getObject().has("errors")) {
            return true;
        } else {
            if (node.getObject().has("exception") || node.getObject().has("error")) {
                return true;
            }
        }
        return false;
    }

}
