package com.angkorteam.fintech.client;

import com.angkorteam.fintech.client.dto.*;
import com.angkorteam.fintech.client.function.AccountingApi;
import com.angkorteam.fintech.client.function.InfrastructureApi;
import com.angkorteam.fintech.client.function.OrganisationApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.wicket.WicketRuntimeException;

import javax.net.ssl.SSLContext;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FineractClient implements Closeable,
        OrganisationApi,
        InfrastructureApi,
        AccountingApi {

    private static final String DATE_FORMAT_KEY = "dateFormat";
    private static final String DATE_FORMAT_VALUE = "yyyy-MM-dd";
    private static final String LOCALE_KEY = "locale";
    private static final String LOCALE_VALUE = "en";

    private final String baseUrl;

    private final Gson gson;

    private final CloseableHttpClient client;

    public FineractClient(SSLContext ssl, String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = HttpClientBuilder.create().setSSLContext(ssl).build();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public PostAuthenticationResponse authentication(String tenant, AuthenticateRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/authentication");
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, PostAuthenticationResponse.class);
            } else {
                if (responseText.contains("developerMessage")) {
                    throw new AppException(responseText, this.gson.fromJson(responseText, AppError.class));
                } else {
                    throw new SysException(responseText, this.gson.fromJson(responseText, SysError.class));
                }
            }
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public FineractResponse officeCreate(String tenant, String token, PostOfficeRequest requestBody) throws AppException, SysException {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        internalRequestBody.put("externalId", requestBody.getExternalId());
        internalRequestBody.put("name", requestBody.getName());
        internalRequestBody.put("parentId", requestBody.getParentId());
        if (requestBody.getOpeningDate() != null) {
            internalRequestBody.put("openingDate", DateFormatUtils.format(requestBody.getOpeningDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("openingDate", null);
        }
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/offices");
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, FineractResponse.class);
            } else {
                if (responseText.contains("developerMessage")) {
                    throw new AppException(responseText, this.gson.fromJson(responseText, AppError.class));
                } else {
                    throw new SysException(responseText, this.gson.fromJson(responseText, SysError.class));
                }
            }
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public FineractResponse officeUpdate(String tenant, String token, long officeId, PutOfficeRequest requestBody) throws AppException, SysException {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        internalRequestBody.put("externalId", requestBody.getExternalId());
        internalRequestBody.put("name", requestBody.getName());
        internalRequestBody.put("parentId", requestBody.getParentId());
        if (requestBody.getOpeningDate() != null) {
            internalRequestBody.put("openingDate", DateFormatUtils.format(requestBody.getOpeningDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("openingDate", null);
        }
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/offices/" + officeId);
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, FineractResponse.class);
            } else {
                if (responseText.contains("developerMessage")) {
                    throw new AppException(responseText, this.gson.fromJson(responseText, AppError.class));
                } else {
                    throw new SysException(responseText, this.gson.fromJson(responseText, SysError.class));
                }
            }
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public FineractResponse officeTransactionCreate(String tenant, String token, PostOfficeTransactionRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        internalRequestBody.put("fromOfficeId", requestBody.getFromOfficeId());
        internalRequestBody.put("toOfficeId", requestBody.getToOfficeId());
        internalRequestBody.put("currencyCode", requestBody.getCurrencyCode());
        internalRequestBody.put("transactionAmount", requestBody.getTransactionAmount());
        internalRequestBody.put("description", requestBody.getDescription());
        if (requestBody.getTransactionDate() != null) {
            internalRequestBody.put("transactionDate", DateFormatUtils.format(requestBody.getTransactionDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("transactionDate", null);
        }

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/officetransactions");
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, FineractResponse.class);
            } else {
                if (responseText.contains("developerMessage")) {
                    throw new AppException(responseText, this.gson.fromJson(responseText, AppError.class));
                } else {
                    throw new SysException(responseText, this.gson.fromJson(responseText, SysError.class));
                }
            }
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public FineractResponse officeTransactionDelete(String tenant, String token, long transactionId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/officetransactions/" + transactionId);
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, FineractResponse.class);
            } else {
                if (responseText.contains("developerMessage")) {
                    throw new AppException(responseText, this.gson.fromJson(responseText, AppError.class));
                } else {
                    throw new SysException(responseText, this.gson.fromJson(responseText, SysError.class));
                }
            }
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public FineractResponse runAccrualCreate(String tenant, String token, PostRunAccrualRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        if (requestBody.getTillDate() != null) {
            internalRequestBody.put("tillDate", DateFormatUtils.format(requestBody.getTillDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("tillDate", null);
        }

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/runaccruals");
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, FineractResponse.class);
            } else {
                if (responseText.contains("developerMessage")) {
                    throw new AppException(responseText, this.gson.fromJson(responseText, AppError.class));
                } else {
                    throw new SysException(responseText, this.gson.fromJson(responseText, SysError.class));
                }
            }
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public FineractResponse glClosureCreate(String tenant, String token, PostGlClosureRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        internalRequestBody.put("officeId", requestBody.getOfficeId());
        internalRequestBody.put("comments", requestBody.getComments());
        if (requestBody.getClosingDate() != null) {
            internalRequestBody.put("closingDate", DateFormatUtils.format(requestBody.getClosingDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("closingDate", null);
        }

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/glclosures");
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, FineractResponse.class);
            } else {
                if (responseText.contains("developerMessage")) {
                    throw new AppException(responseText, this.gson.fromJson(responseText, AppError.class));
                } else {
                    throw new SysException(responseText, this.gson.fromJson(responseText, SysError.class));
                }
            }
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public FineractResponse glClosureUpdate(String tenant, String token, long glClosureId, PutGlClosureRequest requestBody) throws AppException, SysException {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/glclosures/" + glClosureId);
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, FineractResponse.class);
            } else {
                if (responseText.contains("developerMessage")) {
                    throw new AppException(responseText, this.gson.fromJson(responseText, AppError.class));
                } else {
                    throw new SysException(responseText, this.gson.fromJson(responseText, SysError.class));
                }
            }
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public FineractResponse glClosureDelete(String tenant, String token, long glClosureId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/glclosures/" + glClosureId);
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, FineractResponse.class);
            } else {
                if (responseText.contains("developerMessage")) {
                    throw new AppException(responseText, this.gson.fromJson(responseText, AppError.class));
                } else {
                    throw new SysException(responseText, this.gson.fromJson(responseText, SysError.class));
                }
            }
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        if (this.client != null) {
            this.client.close();
        }
    }
}