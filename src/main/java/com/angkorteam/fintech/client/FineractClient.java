package com.angkorteam.fintech.client;

import com.angkorteam.fintech.client.dto.*;
import com.angkorteam.fintech.client.function.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FineractClient implements Closeable,
        OrganisationApi,
        AdministrationApi,
        InfrastructureApi,
        PortfolioApi,
        AccountingApi {

    private static final String DATE_FORMAT_KEY = "dateFormat";
    private static final String DATE_FORMAT_VALUE = "yyyy-MM-dd";
    private static final String LOCALE_KEY = "locale";
    private static final String LOCALE_VALUE = "en";

    private final String baseUrl;

    private final Gson gson;

    private final CloseableHttpClient client;

    public FineractClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = HttpClientBuilder.create().build();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

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
    public FineractResponse glClosureCreate(String tenant, String token, PostGLClosureRequest requestBody) {
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
    public FineractResponse glClosureUpdate(String tenant, String token, long glClosureId, PutGLClosureRequest requestBody) throws AppException, SysException {
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
    public FineractResponse passwordPreferenceUpdate(String tenant, String token, PutPasswordPreferencesTemplateRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/passwordpreferences");
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
    public FineractResponse permissionUpdate(String tenant, String token, PutPermissionsRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/permissions");
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
    public FineractResponse roleCreate(String tenant, String token, PostRolesRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/roles");
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
    public FineractResponse roleCommand(String tenant, String token, long roleId, String command) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/roles/" + roleId + "?command=" + command);
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
    public FineractResponse workingDayUpdate(String tenant, String token, PutWorkingDaysRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put("recurrence", requestBody.getRecurrence());
        internalRequestBody.put("extendTermForDailyRepayments", requestBody.isExtendTermForDailyRepayments());
        internalRequestBody.put("extendTermForRepaymentsOnHolidays", requestBody.isExtendTermForRepaymentsOnHolidays());
        if (requestBody.getRepaymentRescheduleType() != null) {
            internalRequestBody.put("repaymentRescheduleType", Long.valueOf(requestBody.getRepaymentRescheduleType().getLiteral()));
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
        requestBuilder.setUri(this.baseUrl + "/workingdays");
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
    public FineractResponse fundCreate(String tenant, String token, PostFundsRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/funds");
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
    public FineractResponse fundUpdate(String tenant, String token, long fundId, PutFundsFundIdRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/funds/" + fundId);
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
    public FineractResponse codeValueCreate(String tenant, String token, long codeId, PostCodeValuesDataRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/codes/" + codeId + "/codevalues");
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
    public FineractResponse codeValueUpdate(String tenant, String token, long codeId, long codeValueId, PutCodeValuesDataRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/codes/" + codeId + "/codevalues/" + codeValueId);
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
    public FineractResponse codeValueDelete(String tenant, String token, long codeId, long codeValueId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/codes/" + codeId + "/codevalues/" + codeValueId);
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
    public FineractResponse currencyUpdate(String tenant, String token, PutCurrenciesRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/currencies");
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
    public FineractResponse tellerCreate(String tenant, String token, PostTellersRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        internalRequestBody.put("officeId", requestBody.getOfficeId());
        internalRequestBody.put("description", requestBody.getDescription());
        internalRequestBody.put("name", requestBody.getName());
        if (requestBody.getStartDate() != null) {
            internalRequestBody.put("startDate", DateFormatUtils.format(requestBody.getStartDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("startDate", null);
        }
        if (requestBody.getEndDate() != null) {
            internalRequestBody.put("endDate", DateFormatUtils.format(requestBody.getEndDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("endDate", null);
        }
        if (requestBody.getStatus() != null) {
            internalRequestBody.put("status", requestBody.getStatus().getEnumType());
        } else {
            internalRequestBody.put("status", null);
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
        requestBuilder.setUri(this.baseUrl + "/tellers");
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
    public FineractResponse paymentTypeCreate(String tenant, String token, PostPaymentTypesRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/paymenttypes");
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
    public FineractResponse paymentTypeUpdate(String tenant, String token, long paymentTypeId, PutPaymentTypesPaymentTypeIdRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/paymenttypes/" + paymentTypeId);
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
    public FineractResponse paymentTypeDelete(String tenant, String token, long paymentTypeId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/paymenttypes/" + paymentTypeId);
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
    public FineractResponse holidayCreate(String tenant, String token, PostHolidaysRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        internalRequestBody.put("name", requestBody.getName());
        internalRequestBody.put("description", requestBody.getDescription());
        if (requestBody.getReschedulingType() != null) {
            internalRequestBody.put("reschedulingType", requestBody.getReschedulingType().getLiteral());
        } else {
            internalRequestBody.put("reschedulingType", null);
        }
        if (requestBody.getFromDate() != null) {
            internalRequestBody.put("fromDate", DateFormatUtils.format(requestBody.getFromDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("fromDate", null);
        }
        if (requestBody.getToDate() != null) {
            internalRequestBody.put("toDate", DateFormatUtils.format(requestBody.getToDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("toDate", null);
        }
        if (requestBody.getRepaymentsRescheduledTo() != null) {
            internalRequestBody.put("repaymentsRescheduledTo", DateFormatUtils.format(requestBody.getRepaymentsRescheduledTo(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("repaymentsRescheduledTo", null);
        }
        if (requestBody.getOffices() != null && !requestBody.getOffices().isEmpty()) {
            internalRequestBody.put("offices", requestBody.getOffices());
        } else {
            internalRequestBody.put("offices", null);
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
        requestBuilder.setUri(this.baseUrl + "/holidays");
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
    public FineractResponse staffCreate(String tenant, String token, PostStaffRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        if (requestBody.getJoiningDate() != null) {
            internalRequestBody.put("joiningDate", DateFormatUtils.format(requestBody.getJoiningDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("joiningDate", null);
        }
        internalRequestBody.put("externalId", requestBody.getExternalId());
        internalRequestBody.put("firstname", requestBody.getFirstName());
        internalRequestBody.put("lastname", requestBody.getLastName());
        internalRequestBody.put("mobileNo", requestBody.getMobileNo());
        internalRequestBody.put("officeId", requestBody.getOfficeId());
        internalRequestBody.put("isActive", requestBody.isActive());
        internalRequestBody.put("isLoanOfficer", requestBody.isLoanOfficer());

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/staff");
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, FineractResponse.class);
            } else {
                System.out.println(responseText);
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
    public FineractResponse glAccountCreate(String tenant, String token, PostGLAccountsRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put("description", requestBody.getDescription());
        internalRequestBody.put("name", requestBody.getName());
        internalRequestBody.put("glCode", requestBody.getGlCode());
        internalRequestBody.put("manualEntriesAllowed", requestBody.isManualEntriesAllowed());
        if (requestBody.getType() != null) {
            internalRequestBody.put("type", Long.valueOf(requestBody.getType().getLiteral()));
        } else {
            internalRequestBody.put("type", null);
        }
        if (requestBody.getTagId() > 0) {
            internalRequestBody.put("tagId", requestBody.getTagId());
        } else {
            internalRequestBody.put("tagId", null);
        }
        if (requestBody.getParentId() > 0) {
            internalRequestBody.put("parentId", requestBody.getParentId());
        } else {
            internalRequestBody.put("parentId", null);
        }
        if (requestBody.getUsage() != null) {
            internalRequestBody.put("usage", Long.valueOf(requestBody.getUsage().getLiteral()));
        } else {
            internalRequestBody.put("usage", null);
        }
        internalRequestBody.put("disabled", requestBody.isDisabled());

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/glaccounts");
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, FineractResponse.class);
            } else {
                System.out.println(responseText);
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
    public FineractResponse accountingRuleCreate(String tenant, String token, PostAccountingRulesRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put("name", requestBody.getName());
        internalRequestBody.put("description", requestBody.getDescription());
        internalRequestBody.put("accountToCredit", requestBody.getAccountToCredit());
        internalRequestBody.put("accountToDebit", requestBody.getAccountToDebit());
        internalRequestBody.put("officeId", requestBody.getOfficeId());
        internalRequestBody.put("allowMultipleCreditEntries", requestBody.isAllowMultipleCreditEntries());
        internalRequestBody.put("allowMultipleDebitEntries", requestBody.isAllowMultipleDebitEntries());
        if (requestBody.getCreditTags() != null && !requestBody.getCreditTags().isEmpty()) {
            internalRequestBody.put("creditTags", requestBody.getCreditTags());
        } else {
            internalRequestBody.put("creditTags", null);
        }
        if (requestBody.getDebitTags() != null && !requestBody.getDebitTags().isEmpty()) {
            internalRequestBody.put("debitTags", requestBody.getDebitTags());
        } else {
            internalRequestBody.put("debitTags", null);
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
        requestBuilder.setUri(this.baseUrl + "/accountingrules");
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, FineractResponse.class);
            } else {
                System.out.println(responseText);
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
    public FineractResponse taxComponentCreate(String tenant, String token, PostTaxesComponentsRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        internalRequestBody.put("name", requestBody.getName());
        internalRequestBody.put("percentage", requestBody.getPercentage());
        if (requestBody.getStartDate() != null) {
            internalRequestBody.put("startDate", DateFormatUtils.format(requestBody.getStartDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("startDate", null);
        }
        if (requestBody.getCreditAccountType() != null) {
            internalRequestBody.put("creditAccountType", Integer.valueOf(requestBody.getCreditAccountType().getLiteral()));
        } else {
            internalRequestBody.put("creditAccountType", null);
        }
        if (requestBody.getCreditAccountId() > 0) {
            internalRequestBody.put("creditAcountId", requestBody.getCreditAccountId());
        } else {
            internalRequestBody.put("creditAcountId", null);
        }
        if (requestBody.getDebitAccountType() != null) {
            internalRequestBody.put("debitAccountType", Integer.valueOf(requestBody.getDebitAccountType().getLiteral()));
        } else {
            internalRequestBody.put("debitAccountType", null);
        }
        if (requestBody.getDebitAccountId() > 0) {
            internalRequestBody.put("debitAcountId", requestBody.getDebitAccountId());
        } else {
            internalRequestBody.put("debitAcountId", null);
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
        requestBuilder.setUri(this.baseUrl + "/taxes/component");
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, FineractResponse.class);
            } else {
                System.out.println(responseText);
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
    public FineractResponse taxGroupCreate(String tenant, String token, PostTaxesGroupRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put("name", requestBody.getName());
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        if (requestBody.getTaxComponents() != null && !requestBody.getTaxComponents().isEmpty()) {
            List<Map<String, Object>> taxComponents = new ArrayList<>();
            internalRequestBody.put("taxComponents", taxComponents);
            for (PostTaxesGroupRequest.TaxComponent o : requestBody.getTaxComponents()) {
                Map<String, Object> taxComponent = new HashMap<>();
                if (o.getId() > 0) {
                    taxComponent.put("id", o.getId());
                } else {
                    taxComponent.put("id", null);
                }
                if (o.getStartDate() != null) {
                    taxComponent.put("startDate", DateFormatUtils.format(o.getStartDate(), DATE_FORMAT_VALUE));
                } else {
                    taxComponent.put("startDate", null);
                }
                if (o.getEndDate() != null) {
                    taxComponent.put("endDate", DateFormatUtils.format(o.getEndDate(), DATE_FORMAT_VALUE));
                } else {
                    taxComponent.put("endDate", null);
                }
                if (o.getTaxComponentId() > 0) {
                    taxComponent.put("taxComponentId", o.getTaxComponentId());
                } else {
                    taxComponent.put("taxComponentId", null);
                }
                taxComponents.add(taxComponent);
            }
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
        requestBuilder.setUri(this.baseUrl + "/taxes/group");
        HttpUriRequest request = requestBuilder.build();
        try (CloseableHttpResponse response = this.client.execute(request)) {
            String responseText = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() <= 299) {
                return this.gson.fromJson(responseText, FineractResponse.class);
            } else {
                System.out.println(responseText);
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