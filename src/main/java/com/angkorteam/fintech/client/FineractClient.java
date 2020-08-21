package com.angkorteam.fintech.client;

import com.angkorteam.fintech.client.dto.*;
import com.angkorteam.fintech.client.enums.PutUserRequest;
import com.angkorteam.fintech.client.enums.TableTypeEnum;
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
        AdhocApi,
        BatchApi,
        CommandApi,
        NotificationApi,
        InteroperationApi,
        SpmApi,
        AccountingApi {

    private static final String DATE_FORMAT_KEY = "dateFormat";
    private static final String DATE_FORMAT_VALUE = "yyyy-MM-dd";
    private static final String MONTH_DAY_FORMAT_KEY = "monthDayFormat";
    private static final String MONTH_DAY_FORMAT_VALUE = "dd MMMM";

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
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put("validationPolicyId", Integer.valueOf(requestBody.getValidationPolicy().getLiteral()));

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
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
    public FineractResponse makerCheckerPermissionUpdate(String tenant, String token, Map<String, Boolean> requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put("permissions", requestBody);

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
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
    public FineractResponse roleCreate(String tenant, String token, PostRoleRequest requestBody) {
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
    public FineractResponse codeValueCreate(String tenant, String token, long codeId, PostCodeValueRequest requestBody) {
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
    public FineractResponse codeValueUpdate(String tenant, String token, long codeId, long codeValueId, PutCodeValueRequest requestBody) {
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
    public FineractResponse staffUpdate(String tenant, String token, long staffId, PutStaffRequest requestBody) {
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

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/staff/" + staffId);
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
            internalRequestBody.put("type", Integer.valueOf(requestBody.getType().getLiteral()));
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
            internalRequestBody.put("usage", Integer.valueOf(requestBody.getUsage().getLiteral()));
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
    public FineractResponse floatingRateCreate(String tenant, String token, PostFloatingRatesRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put("name", requestBody.getName());
        internalRequestBody.put("isBaseLendingRate", requestBody.isBaseLendingRate());
        internalRequestBody.put("isActive", requestBody.isActive());
        if (requestBody.getRatePeriods() != null && !requestBody.getRatePeriods().isEmpty()) {
            List<Map<String, Object>> ratePeriods = new ArrayList<>();
            internalRequestBody.put("ratePeriods", ratePeriods);
            for (PostFloatingRatesRequest.RatePeriod o : requestBody.getRatePeriods()) {
                Map<String, Object> ratePeriod = new HashMap<>();
                ratePeriod.put(LOCALE_KEY, LOCALE_VALUE);
                ratePeriod.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
                ratePeriod.put("interestRate", o.getInterestRate());
                if (o.getFromDate() != null) {
                    ratePeriod.put("fromDate", DateFormatUtils.format(o.getFromDate(), DATE_FORMAT_VALUE));
                } else {
                    ratePeriod.put("fromDate", null);
                }
                ratePeriod.put("isDifferentialToBaseLendingRate", o.isDifferentialToBaseLendingRate());
                ratePeriods.add(ratePeriod);
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
        requestBuilder.setUri(this.baseUrl + "/floatingrates");
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
    public FineractResponse chargeCreate(String tenant, String token, PostChargesRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(MONTH_DAY_FORMAT_KEY, MONTH_DAY_FORMAT_VALUE);
        if (requestBody.getIncomeAccountId() > 0) {
            internalRequestBody.put("incomeAccountId", requestBody.getIncomeAccountId());
        } else {
            internalRequestBody.put("incomeAccountId", null);
        }
        if (requestBody.getTaxGroupId() > 0) {
            internalRequestBody.put("taxGroupId", requestBody.getTaxGroupId());
        } else {
            internalRequestBody.put("taxGroupId", null);
        }
        internalRequestBody.put("name", requestBody.getName());
        internalRequestBody.put("amount", requestBody.getAmount());
        internalRequestBody.put("currencyCode", requestBody.getCurrencyCode());
        if (requestBody.getChargeAppliesTo() != null) {
            internalRequestBody.put("chargeAppliesTo", Integer.valueOf(requestBody.getChargeAppliesTo().getLiteral()));
        } else {
            internalRequestBody.put("chargeAppliesTo", null);
        }
        if (requestBody.getChargeTimeType() != null) {
            internalRequestBody.put("chargeTimeType", Integer.valueOf(requestBody.getChargeTimeType().getLiteral()));
        } else {
            internalRequestBody.put("chargeTimeType", null);
        }
        if (requestBody.getChargeCalculationType() != null) {
            internalRequestBody.put("chargeCalculationType", Integer.valueOf(requestBody.getChargeCalculationType().getLiteral()));
        } else {
            internalRequestBody.put("chargeCalculationType", null);
        }
        if (requestBody.getChargePaymentMode() != null) {
            internalRequestBody.put("chargePaymentMode", Integer.valueOf(requestBody.getChargePaymentMode().getLiteral()));
        } else {
            internalRequestBody.put("chargePaymentMode", null);
        }
        internalRequestBody.put("penalty", requestBody.isPenalty());
        internalRequestBody.put("active", requestBody.isActive());
        if (requestBody.getFeeOnMonthDay() != null) {
            internalRequestBody.put("feeOnMonthDay", DateFormatUtils.format(requestBody.getFeeOnMonthDay(), MONTH_DAY_FORMAT_VALUE));
        } else {
            internalRequestBody.put("feeOnMonthDay", null);
        }
        if (requestBody.getFeeInterval() > 0) {
            internalRequestBody.put("feeInterval", requestBody.getFeeInterval());
        } else {
            internalRequestBody.put("feeInterval", null);
        }
        if (requestBody.getMinCap() > 0) {
            internalRequestBody.put("minCap", requestBody.getMinCap());
        } else {
            internalRequestBody.put("minCap", null);
        }
        if (requestBody.getMaxCap() > 0) {
            internalRequestBody.put("maxCap", requestBody.getMaxCap());
        } else {
            internalRequestBody.put("maxCap", null);
        }
        if (requestBody.getFeeFrequency() != null) {
            internalRequestBody.put("feeFrequency", Integer.valueOf(requestBody.getFeeFrequency().getLiteral()));
        } else {
            internalRequestBody.put("feeFrequency", null);
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
        requestBuilder.setUri(this.baseUrl + "/charges");
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
    public FineractResponse clientCreate(String tenant, String token, PostClientsRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        if (requestBody.getOfficeId() > 0) {
            internalRequestBody.put("officeId", requestBody.getOfficeId());
        } else {
            internalRequestBody.put("officeId", null);
        }
        internalRequestBody.put("fullname", requestBody.getFullName());
        if (requestBody.getGroupId() > 0) {
            internalRequestBody.put("groupId", requestBody.getGroupId());
        } else {
            internalRequestBody.put("groupId", null);
        }
        internalRequestBody.put("active", requestBody.isActive());
        if (requestBody.getActivationDate() != null) {
            internalRequestBody.put("activationDate", DateFormatUtils.format(requestBody.getActivationDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("activationDate", null);
        }
        internalRequestBody.put("isStaff", requestBody.isStaff());
        if (requestBody.getStaffId() > 0) {
            internalRequestBody.put("staffId", requestBody.getStaffId());
        } else {
            internalRequestBody.put("staffId", null);
        }
        if (requestBody.getGenderId() > 0) {
            internalRequestBody.put("genderId", requestBody.getGenderId());
        } else {
            internalRequestBody.put("genderId", null);
        }
        if (requestBody.getClientTypeId() > 0) {
            internalRequestBody.put("clientTypeId", requestBody.getClientTypeId());
        }
        internalRequestBody.put("accountNo", requestBody.getAccountNo());
        internalRequestBody.put("externalId", requestBody.getExternalId());
        internalRequestBody.put("mobileNo", requestBody.getMobileNo());
        internalRequestBody.put("emailAddress", requestBody.getEmailAddress());
        internalRequestBody.put("firstname", requestBody.getFirstName());
        internalRequestBody.put("middlename", requestBody.getMiddleName());
        internalRequestBody.put("lastname", requestBody.getLastName());
        if (requestBody.getDateOfBirth() != null) {
            internalRequestBody.put("dateOfBirth", DateFormatUtils.format(requestBody.getDateOfBirth(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("dateOfBirth", null);
        }
        if (requestBody.getSubmittedOnDate() != null) {
            internalRequestBody.put("submittedOnDate", DateFormatUtils.format(requestBody.getSubmittedOnDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("submittedOnDate", null);
        }
        if (requestBody.getClientTypeId() > 0) {
            internalRequestBody.put("clientTypeId", requestBody.getClientTypeId());
        } else {
            internalRequestBody.put("clientTypeId", null);
        }
        if (requestBody.getClientClassificationId() > 0) {
            internalRequestBody.put("clientClassificationId", requestBody.getClientClassificationId());
        } else {
            internalRequestBody.put("clientClassificationId", null);
        }
        if (requestBody.getSavingProductId() > 0) {
            internalRequestBody.put("savingsProductId", requestBody.getSavingProductId());
        } else {
            internalRequestBody.put("savingsProductId", null);
        }
        if (requestBody.getLegalForm() != null) {
            internalRequestBody.put("legalFormId", Integer.valueOf(requestBody.getLegalForm().getLiteral()));
        } else {
            internalRequestBody.put("legalFormId", null);
        }
        if (requestBody.getClientNonPersonDetail() != null) {
            Map<String, Object> clientNonPersonDetail = new HashMap<>();
            internalRequestBody.put("clientNonPersonDetails", clientNonPersonDetail);
            clientNonPersonDetail.put(LOCALE_KEY, LOCALE_VALUE);
            clientNonPersonDetail.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
            clientNonPersonDetail.put("incorpNumber", requestBody.getClientNonPersonDetail().getIncorpNumber());
            clientNonPersonDetail.put("remarks", requestBody.getClientNonPersonDetail().getRemark());
            if (requestBody.getClientNonPersonDetail().getConstitutionId() > 0) {
                clientNonPersonDetail.put("constitutionId", requestBody.getClientNonPersonDetail().getConstitutionId());
            } else {
                clientNonPersonDetail.put("constitutionId", null);
            }
            if (requestBody.getClientNonPersonDetail().getMainBusinessLineId() > 0) {
                clientNonPersonDetail.put("mainBusinessLineId", requestBody.getClientNonPersonDetail().getMainBusinessLineId());
            } else {
                clientNonPersonDetail.put("mainBusinessLineId", null);
            }
            if (requestBody.getClientNonPersonDetail().getIncorpValidityTillDate() != null) {
                clientNonPersonDetail.put("incorpValidityTillDate", DateFormatUtils.format(requestBody.getClientNonPersonDetail().getIncorpValidityTillDate(), DATE_FORMAT_VALUE));
            } else {
                clientNonPersonDetail.put("incorpValidityTillDate", null);
            }
        } else {
            internalRequestBody.put("clientNonPersonDetail", null);
        }
        if (requestBody.getAddress() != null && !requestBody.getAddress().isEmpty()) {
            List<Map<String, Object>> addresses = new ArrayList<>();
            internalRequestBody.put("address", addresses);
            for (PostClientsRequest.Address o : requestBody.getAddress()) {
                Map<String, Object> address = new HashMap<>();
                addresses.add(address);
                if (o.getStateProvinceId() > 0) {
                    address.put("stateProvinceId", o.getStateProvinceId());
                } else {
                    address.put("stateProvinceId", null);
                }
                if (o.getCountryId() > 0) {
                    address.put("countryId", o.getCountryId());
                } else {
                    address.put("countryId", null);
                }
                if (o.getAddressTypeId() > 0) {
                    address.put("addressTypeId", o.getAddressTypeId());
                } else {
                    address.put("addressTypeId", null);
                }
                address.put("isActive", o.isActive());
                address.put("street", o.getStreet());
                address.put("addressLine1", o.getAddressLine1());
                address.put("addressLine2", o.getAddressLine2());
                address.put("addressLine3", o.getAddressLine3());
                address.put("townVillage", o.getTownVillage());
                address.put("city", o.getCity());
                address.put("countyDistrict", o.getCountyDistrict());
                address.put("postalCode", o.getPostalCode());
                address.put("latitude", o.getLatitude());
                address.put("longitude", o.getLongitude());
                address.put("createdBy", o.getCreatedBy());
                address.put("updatedBy", o.getUpdatedBy());
                if (o.getCreatedOn() != null) {
                    address.put("createdOn", DateFormatUtils.format(o.getCreatedOn(), "yyyy-MM-dd"));
                } else {
                    address.put("createdOn", null);
                }
                if (o.getUpdatedOn() != null) {
                    address.put("updatedOn", DateFormatUtils.format(o.getUpdatedOn(), "yyyy-MM-dd"));
                } else {
                    address.put("updatedOn", null);
                }
            }
        } else {
            internalRequestBody.put("address", null);
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
        requestBuilder.setUri(this.baseUrl + "/clients");
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
    public FineractResponse groupCreate(String tenant, String token, PostGroupsRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        if (requestBody.getOfficeId() > 0) {
            internalRequestBody.put("officeId", requestBody.getOfficeId());
        } else {
            internalRequestBody.put("officeId", null);
        }
        internalRequestBody.put("name", requestBody.getName());
        internalRequestBody.put("active", requestBody.isActive());
        internalRequestBody.put("accountNo", requestBody.getAccountNo());
        internalRequestBody.put("externalId", requestBody.getExternalId());
        if (requestBody.getActivationDate() != null) {
            internalRequestBody.put("activationDate", DateFormatUtils.format(requestBody.getActivationDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("activationDate", null);
        }
        if (requestBody.getStaffId() > 0) {
            internalRequestBody.put("staffId", requestBody.getStaffId());
        } else {
            internalRequestBody.put("staffId", null);
        }
        if (requestBody.getSubmittedOnDate() != null) {
            internalRequestBody.put("submittedOnDate", DateFormatUtils.format(requestBody.getSubmittedOnDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("submittedOnDate", null);
        }
        if (requestBody.getClientMembers() != null && !requestBody.getClientMembers().isEmpty()) {
            internalRequestBody.put("clientMembers", requestBody.getClientMembers());
        } else {
            internalRequestBody.put("clientMembers", null);
        }
        if (requestBody.getGroupMembers() != null && !requestBody.getGroupMembers().isEmpty()) {
            internalRequestBody.put("groupMembers", requestBody.getGroupMembers());
        } else {
            internalRequestBody.put("groupMembers", null);
        }
        if (requestBody.getDatatables() != null && !requestBody.getDatatables().isEmpty()) {
            internalRequestBody.put("datatables", requestBody.getDatatables());
        } else {
            internalRequestBody.put("datatables", null);
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
        requestBuilder.setUri(this.baseUrl + "/groups");
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
    public FineractResponse centerCreate(String tenant, String token, PostCentersRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        if (requestBody.getOfficeId() > 0) {
            internalRequestBody.put("officeId", requestBody.getOfficeId());
        } else {
            internalRequestBody.put("officeId", null);
        }
        internalRequestBody.put("name", requestBody.getName());
        internalRequestBody.put("active", requestBody.isActive());
        internalRequestBody.put("accountNo", requestBody.getAccountNo());
        internalRequestBody.put("externalId", requestBody.getExternalId());
        if (requestBody.getActivationDate() != null) {
            internalRequestBody.put("activationDate", DateFormatUtils.format(requestBody.getActivationDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("activationDate", null);
        }
        if (requestBody.getStaffId() > 0) {
            internalRequestBody.put("staffId", requestBody.getStaffId());
        } else {
            internalRequestBody.put("staffId", null);
        }
        if (requestBody.getSubmittedOnDate() != null) {
            internalRequestBody.put("submittedOnDate", DateFormatUtils.format(requestBody.getSubmittedOnDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("submittedOnDate", null);
        }
        if (requestBody.getClientMembers() != null && !requestBody.getClientMembers().isEmpty()) {
            internalRequestBody.put("clientMembers", requestBody.getClientMembers());
        } else {
            internalRequestBody.put("clientMembers", null);
        }
        if (requestBody.getGroupMembers() != null && !requestBody.getGroupMembers().isEmpty()) {
            internalRequestBody.put("groupMembers", requestBody.getGroupMembers());
        } else {
            internalRequestBody.put("groupMembers", null);
        }
        if (requestBody.getDatatables() != null && !requestBody.getDatatables().isEmpty()) {
            internalRequestBody.put("datatables", requestBody.getDatatables());
        } else {
            internalRequestBody.put("datatables", null);
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
        requestBuilder.setUri(this.baseUrl + "/centers");
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
    public FineractResponse financialActivityAccountCreate(String tenant, String token, PostFinancialActivityAccountsRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        if (requestBody.getGlAccountId() > 0) {
            internalRequestBody.put("glAccountId", requestBody.getGlAccountId());
        } else {
            internalRequestBody.put("glAccountId", null);
        }
        if (requestBody.getFinancialActivity() != null) {
            internalRequestBody.put("financialActivityId", Integer.valueOf(requestBody.getFinancialActivity().getLiteral()));
        } else {
            internalRequestBody.put("financialActivityId", null);
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
        requestBuilder.setUri(this.baseUrl + "/financialactivityaccounts");
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
    public FineractResponse financialActivityAccountUpdate(String tenant, String token, long mappingId, PutFinancialActivityAccountsRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        if (requestBody.getGlAccountId() > 0) {
            internalRequestBody.put("glAccountId", requestBody.getGlAccountId());
        } else {
            internalRequestBody.put("glAccountId", null);
        }
        if (requestBody.getFinancialActivity() != null) {
            internalRequestBody.put("financialActivityId", Integer.valueOf(requestBody.getFinancialActivity().getLiteral()));
        } else {
            internalRequestBody.put("financialActivityId", null);
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
        requestBuilder.setUri(this.baseUrl + "/financialactivityaccounts/" + mappingId);
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
    public FineractResponse financialActivityAccountDelete(String tenant, String token, long mappingId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/financialactivityaccounts/" + mappingId);
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
    public FineractResponse glAccountUpdate(String tenant, String token, long glAccountId, PutGLAccountsRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put("description", requestBody.getDescription());
        internalRequestBody.put("name", requestBody.getName());
        internalRequestBody.put("glCode", requestBody.getGlCode());
        internalRequestBody.put("manualEntriesAllowed", requestBody.isManualEntriesAllowed());
        if (requestBody.getType() != null) {
            internalRequestBody.put("type", Integer.valueOf(requestBody.getType().getLiteral()));
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
            internalRequestBody.put("usage", Integer.valueOf(requestBody.getUsage().getLiteral()));
        } else {
            internalRequestBody.put("usage", null);
        }
        internalRequestBody.put("disabled", requestBody.isDisabled());

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/glaccounts/" + glAccountId);
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
    public FineractResponse glAccountDelete(String tenant, String token, long glAccountId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/glaccounts/" + glAccountId);
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
    public FineractResponse journalEntryCreate(String tenant, String token, PostJournalEntriesRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        if (requestBody.getOfficeId() > 0) {
            internalRequestBody.put("officeId", requestBody.getOfficeId());
        } else {
            internalRequestBody.put("officeId", null);
        }
        if (requestBody.getAccountingRule() > 0) {
            internalRequestBody.put("accountingRule", requestBody.getAccountingRule());
        } else {
            internalRequestBody.put("accountingRule", null);
        }
        internalRequestBody.put("currencyCode", requestBody.getCurrencyCode());
        if (requestBody.getPaymentTypeId() > 0) {
            internalRequestBody.put("paymentTypeId", requestBody.getPaymentTypeId());
        } else {
            internalRequestBody.put("paymentTypeId", null);
        }
        if (requestBody.getTransactionDate() != null) {
            internalRequestBody.put("transactionDate", DateFormatUtils.format(requestBody.getTransactionDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("transactionDate", null);
        }
        internalRequestBody.put("accountNumber", requestBody.getAccountNumber());
        internalRequestBody.put("checkNumber", requestBody.getCheckNumber());
        internalRequestBody.put("routingCode", requestBody.getRoutingCode());
        internalRequestBody.put("receiptNumber", requestBody.getReceiptNumber());
        internalRequestBody.put("bankNumber", requestBody.getBankNumber());
        internalRequestBody.put("referenceNumber", requestBody.getReferenceNumber());
        internalRequestBody.put("amount", requestBody.getAmount());
        internalRequestBody.put("comments", requestBody.getComment());
        if (requestBody.getDebits() != null && !requestBody.getDebits().isEmpty()) {
            List<Map<String, Object>> debits = new ArrayList<>();
            internalRequestBody.put("debits", debits);
            for (PostJournalEntriesRequest.TransactionEntry v : requestBody.getDebits()) {
                Map<String, Object> entry = new HashMap<>();
                entry.put("amount", v.getAmount());
                if (v.getGlAccountId() > 0) {
                    entry.put("glAccountId", v.getGlAccountId());
                } else {
                    entry.put("glAccountId", null);
                }
                entry.put("comments", v.getComment());
                debits.add(entry);
            }
        } else {
            internalRequestBody.put("debits", null);
        }
        if (requestBody.getCredits() != null && !requestBody.getCredits().isEmpty()) {
            List<Map<String, Object>> credits = new ArrayList<>();
            internalRequestBody.put("credits", credits);
            for (PostJournalEntriesRequest.TransactionEntry v : requestBody.getCredits()) {
                Map<String, Object> entry = new HashMap<>();
                entry.put("amount", v.getAmount());
                if (v.getGlAccountId() > 0) {
                    entry.put("glAccountId", v.getGlAccountId());
                } else {
                    entry.put("glAccountId", null);
                }
                entry.put("comments", v.getComment());
                credits.add(entry);
            }
        } else {
            internalRequestBody.put("credits", null);
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
        requestBuilder.setUri(this.baseUrl + "/journalentries?command=createOpeningBalance");
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
    public FineractResponse journalEntryUpdate(String tenant, String token, PostJournalEntryUpdateRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/journalentries?command=updateRunningBalance");
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
    public FineractResponse journalEntryDefine(String tenant, String token, PostJournalEntriesRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        if (requestBody.getOfficeId() > 0) {
            internalRequestBody.put("officeId", requestBody.getOfficeId());
        } else {
            internalRequestBody.put("officeId", null);
        }
        if (requestBody.getAccountingRule() > 0) {
            internalRequestBody.put("accountingRule", requestBody.getAccountingRule());
        } else {
            internalRequestBody.put("accountingRule", null);
        }
        internalRequestBody.put("currencyCode", requestBody.getCurrencyCode());
        if (requestBody.getPaymentTypeId() > 0) {
            internalRequestBody.put("paymentTypeId", requestBody.getPaymentTypeId());
        } else {
            internalRequestBody.put("paymentTypeId", null);
        }
        if (requestBody.getTransactionDate() != null) {
            internalRequestBody.put("transactionDate", DateFormatUtils.format(requestBody.getTransactionDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("transactionDate", null);
        }
        internalRequestBody.put("accountNumber", requestBody.getAccountNumber());
        internalRequestBody.put("checkNumber", requestBody.getCheckNumber());
        internalRequestBody.put("routingCode", requestBody.getRoutingCode());
        internalRequestBody.put("receiptNumber", requestBody.getReceiptNumber());
        internalRequestBody.put("bankNumber", requestBody.getBankNumber());
        internalRequestBody.put("referenceNumber", requestBody.getReferenceNumber());
        internalRequestBody.put("amount", requestBody.getAmount());
        internalRequestBody.put("comments", requestBody.getComment());
        if (requestBody.getDebits() != null && !requestBody.getDebits().isEmpty()) {
            List<Map<String, Object>> debits = new ArrayList<>();
            internalRequestBody.put("debits", debits);
            for (PostJournalEntriesRequest.TransactionEntry v : requestBody.getDebits()) {
                Map<String, Object> entry = new HashMap<>();
                entry.put("amount", v.getAmount());
                if (v.getGlAccountId() > 0) {
                    entry.put("glAccountId", v.getGlAccountId());
                } else {
                    entry.put("glAccountId", null);
                }
                entry.put("comments", v.getComment());
                debits.add(entry);
            }
        } else {
            internalRequestBody.put("debits", null);
        }
        if (requestBody.getCredits() != null && !requestBody.getCredits().isEmpty()) {
            List<Map<String, Object>> credits = new ArrayList<>();
            internalRequestBody.put("credits", credits);
            for (PostJournalEntriesRequest.TransactionEntry v : requestBody.getCredits()) {
                Map<String, Object> entry = new HashMap<>();
                entry.put("amount", v.getAmount());
                if (v.getGlAccountId() > 0) {
                    entry.put("glAccountId", v.getGlAccountId());
                } else {
                    entry.put("glAccountId", null);
                }
                entry.put("comments", v.getComment());
                credits.add(entry);
            }
        } else {
            internalRequestBody.put("credits", null);
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
        requestBuilder.setUri(this.baseUrl + "/journalentries?command=defineOpeningBalance");
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
    public FineractResponse journalEntryReverse(String tenant, String token, long transactionId, PostJournalEntryReverseRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/journalentries/" + transactionId + "?command=reverse");
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
    public FineractResponse provisioningEntryCreate(String tenant, String token, PostProvisioningEntriesRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        if (requestBody.getDate() != null) {
            internalRequestBody.put("date", DateFormatUtils.format(requestBody.getDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("date", null);
        }
        internalRequestBody.put("createjournalentries", requestBody.isCreateJournalEntry());

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/provisioningentries");
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
    public FineractResponse provisioningJournalEntryCreate(String tenant, String token, long entryId) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/provisioningentries/" + entryId + "?command=createjournalentry");
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
    public FineractResponse provisioningEntryReCreate(String tenant, String token, long entryId) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/provisioningentries/" + entryId + "?command=recreateprovisioningentry");
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
    public FineractResponse accountingRuleUpdate(String tenant, String token, long accountingRuleId, PutAccountingRulesRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        if (requestBody.getOfficeId() > 0) {
            internalRequestBody.put("officeId", requestBody.getOfficeId());
        } else {
            internalRequestBody.put("officeId", null);
        }
        if (requestBody.getAccountToDebit() > 0) {
            internalRequestBody.put("accountToDebit", requestBody.getAccountToDebit());
        } else {
            internalRequestBody.put("accountToDebit", null);
        }
        if (requestBody.getAccountToCredit() > 0) {
            internalRequestBody.put("accountToCredit", requestBody.getAccountToCredit());
        } else {
            internalRequestBody.put("accountToCredit", null);
        }
        if (requestBody.getDebitTags() != null && !requestBody.getDebitTags().isEmpty()) {
            internalRequestBody.put("debitTags", requestBody.getDebitTags());
        } else {
            internalRequestBody.put("debitTags", null);
        }
        if (requestBody.getCreditTags() != null && !requestBody.getCreditTags().isEmpty()) {
            internalRequestBody.put("creditTags", requestBody.getCreditTags());
        } else {
            internalRequestBody.put("creditTags", null);
        }
        internalRequestBody.put("allowMultipleCreditEntries", requestBody.isAllowMultipleCreditEntries());
        internalRequestBody.put("allowMultipleDebitEntries", requestBody.isAllowMultipleDebitEntries());

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/accountingrules/" + accountingRuleId);
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
    public FineractResponse accountingRuleDelete(String tenant, String token, long accountingRuleId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/accountingrules/" + accountingRuleId);
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
    public FineractResponse adhocCreate(String tenant, String token, PostAdhocRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/adhocquery");
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
    public FineractResponse adhocUpdate(String tenant, String token, long adhocId, PutAdhocRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/adhocquery/" + adhocId);
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
    public FineractResponse adhocDelete(String tenant, String token, long adhocId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/adhocquery/" + adhocId);
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
    public FineractResponse batchRequest(String tenant, String token, boolean enclosingTransaction, PostBatchesRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/batches?enclosingTransaction=" + enclosingTransaction);
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
    public FineractResponse makerCheckerApprove(String tenant, String token, long auditId) {
        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/makercheckers/" + auditId + "?command=approve");
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
    public FineractResponse makerCheckerReject(String tenant, String token, long auditId) {
        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/makercheckers/" + auditId + "?command=reject");
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
    public FineractResponse makerCheckerDelete(String tenant, String token, long auditId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/makercheckers/" + auditId);
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
    public FineractResponse accountNumberFormatCreate(String tenant, String token, PostAccountNumberFormatsRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        if (requestBody.getAccountType() != null) {
            internalRequestBody.put("accountType", Integer.valueOf(requestBody.getAccountType().getLiteral()));
        } else {
            internalRequestBody.put("accountType", null);
        }
        if (requestBody.getPrefixType() != null) {
            internalRequestBody.put("prefixType", Integer.valueOf(requestBody.getPrefixType().getLiteral()));
        } else {
            internalRequestBody.put("prefixType", null);
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
        requestBuilder.setUri(this.baseUrl + "/accountnumberformats");
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
    public FineractResponse accountNumberFormatUpdate(String tenant, String token, long accountNumberFormatId, PutAccountNumberFormatsRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        if (requestBody.getPrefixType() != null) {
            internalRequestBody.put("prefixType", Integer.valueOf(requestBody.getPrefixType().getLiteral()));
        } else {
            internalRequestBody.put("prefixType", null);
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
        requestBuilder.setUri(this.baseUrl + "/accountnumberformats/" + accountNumberFormatId);
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
    public FineractResponse accountNumberFormatDelete(String tenant, String token, long accountNumberFormatId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/accountnumberformats/" + accountNumberFormatId);
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
    public FineractResponse cacheSwitch(String tenant, String token, PutCachesRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        if (requestBody.getCacheType() != null) {
            internalRequestBody.put("cacheType", Integer.valueOf(requestBody.getCacheType().getLiteral()));
        } else {
            internalRequestBody.put("cacheType", null);
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
        requestBuilder.setUri(this.baseUrl + "/caches");
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
    public FineractResponse emailCreate(String tenant, String token, PostEmailRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        internalRequestBody.put("name", requestBody.getName());
        internalRequestBody.put("emailRecipients", requestBody.getEmailRecipients());
        if (requestBody.getStretchyReportId() > 0) {
            internalRequestBody.put("stretchyReportId", requestBody.getStretchyReportId());
        } else {
            internalRequestBody.put("stretchyReportId", null);
        }
        if (requestBody.getEmailAttachmentFileFormatId() > 0) {
            internalRequestBody.put("emailAttachmentFileFormatId", requestBody.getEmailAttachmentFileFormatId());
        } else {
            internalRequestBody.put("emailAttachmentFileFormatId", null);
        }
        if (requestBody.getStartDateTime() != null) {
            internalRequestBody.put("startDateTime", DateFormatUtils.format(requestBody.getStartDateTime(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("startDateTime", null);
        }
        if (requestBody.getClientId() > 0) {
            internalRequestBody.put("clientId", requestBody.getClientId());
        } else {
            internalRequestBody.put("clientId", null);
        }
        if (requestBody.getGroupId() > 0) {
            internalRequestBody.put("groupId", requestBody.getGroupId());
        } else {
            internalRequestBody.put("groupId", null);
        }
        if (requestBody.getStaffId() > 0) {
            internalRequestBody.put("staffId", requestBody.getStaffId());
        } else {
            internalRequestBody.put("staffId", null);
        }
        internalRequestBody.put("emailMessage", requestBody.getEmailMessage());
        internalRequestBody.put("emailSubject", requestBody.getEmailSubject());

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/email");
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
    public FineractResponse emailUpdate(String tenant, String token, long resourceId, PutEmailRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/email/" + resourceId);
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
    public FineractResponse emailDelete(String tenant, String token, long resourceId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/email/" + resourceId);
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
    public FineractResponse emailConfigurationUpdate(String tenant, String token, PutEmailConfigurationRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put("SMTP_USERNAME", requestBody.getSmtpUsername());
        internalRequestBody.put("SMTP_SERVER", requestBody.getSmtpServer());
        if (requestBody.getSmtpPort() > 0) {
            internalRequestBody.put("SMTP_PORT", requestBody.getSmtpPort());
        } else {
            internalRequestBody.put("SMTP_PORT", null);
        }
        internalRequestBody.put("SMTP_PASSWORD", requestBody.getSmtpPassword());

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/email/configuration");
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
    public FineractResponse emailCampaignCreate(String tenant, String token, PostEmailCampaignRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        if (requestBody.getBusinessRuleId() > 0) {
            internalRequestBody.put("businessRuleId", requestBody.getBusinessRuleId());
        } else {
            internalRequestBody.put("businessRuleId", null);
        }
        if (requestBody.getStretchyReportId() > 0) {
            internalRequestBody.put("stretchyReportId", requestBody.getStretchyReportId());
        } else {
            internalRequestBody.put("stretchyReportId", null);
        }
        internalRequestBody.put("campaignName", requestBody.getCampaignName());
        if (requestBody.getCampaignType() > 0) {
            internalRequestBody.put("campaignType", requestBody.getCampaignType());
        } else {
            internalRequestBody.put("campaignType", null);
        }
        internalRequestBody.put("paramValue", requestBody.getParamValue());
        internalRequestBody.put("emailSubject", requestBody.getEmailSubject());
        internalRequestBody.put("emailMessage", requestBody.getEmailMessage());
        internalRequestBody.put("stretchyReportParamMap", requestBody.getStretchyReportParamMap());
        if (requestBody.getEmailAttachmentFileFormatId() > 0) {
            internalRequestBody.put("emailAttachmentFileFormatId", requestBody.getEmailAttachmentFileFormatId());
        } else {
            internalRequestBody.put("emailAttachmentFileFormatId", null);
        }
        internalRequestBody.put("recurrence", requestBody.getRecurrence());
        if (requestBody.getSubmittedOnDate() != null) {
            internalRequestBody.put("submittedOnDate", DateFormatUtils.format(requestBody.getSubmittedOnDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("submittedOnDate", null);
        }
        if (requestBody.getRecurrenceStartDate() != null) {
            internalRequestBody.put("recurrenceStartDate", DateFormatUtils.format(requestBody.getRecurrenceStartDate(), DATE_FORMAT_VALUE));
        } else {
            internalRequestBody.put("recurrenceStartDate", null);
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
        requestBuilder.setUri(this.baseUrl + "/email/campaign");
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
    public FineractResponse emailCampaignUpdate(String tenant, String token, long resourceId, PutEmailCampaignRequest requestBody) {
        return null;
    }

    @Override
    public FineractResponse emailCampaignActivate(String tenant, String token, long resourceId, PostEmailCampaignActivationRequest requestBody) {
        return null;
    }

    @Override
    public FineractResponse emailCampaignReactivate(String tenant, String token, long resourceId, PostEmailCampaignActivationRequest requestBody) {
        return null;
    }

    @Override
    public FineractResponse emailCampaignClose(String tenant, String token, long resourceId, PostEmailCampaignCloseRequest requestBody) {
        return null;
    }

    @Override
    public FineractResponse emailCampaignPreview(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse emailCampaignDelete(String tenant, String token, long resourceId) {
        return null;
    }

    @Override
    public FineractResponse smsCampaignCreate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse smsCampaignUpdate(String tenant, String token, long campaignId) {
        return null;
    }

    @Override
    public FineractResponse smsCampaignDelete(String tenant, String token, long campaignId) {
        return null;
    }

    @Override
    public FineractResponse smsCampaignPreview(String tenant, String token, long campaignId) {
        return null;
    }

    @Override
    public FineractResponse smsCampaignReactivate(String tenant, String token, long campaignId) {
        return null;
    }

    @Override
    public FineractResponse smsCampaignActivate(String tenant, String token, long campaignId) {
        return null;
    }

    @Override
    public FineractResponse smsCampaignClose(String tenant, String token, long campaignId) {
        return null;
    }

    @Override
    public FineractResponse codeCreate(String tenant, String token, CodeRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/codes");
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
    public FineractResponse codeUpdate(String tenant, String token, long codeId, CodeRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/codes/" + codeId);
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
    public FineractResponse codeDelete(String tenant, String token, long codeId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/codes/" + codeId);
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
    public FineractResponse externalServiceUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse configurationUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse creditBureauUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse creditBureauMappingUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse creditBureauOrganisationCreate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse creditBureauProductMappingCreate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse datatableCreate(String tenant, String token, PostDataTableRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        if (requestBody.getApptableName() != null) {
            internalRequestBody.put("apptableName", requestBody.getApptableName().getLiteral());
        } else {
            internalRequestBody.put("apptableName", null);
        }
        internalRequestBody.put("multiRow", requestBody.isMultiRow());
        internalRequestBody.put("datatableName", requestBody.getDatatableName());
        if (requestBody.getColumns() != null && !requestBody.getColumns().isEmpty()) {
            List<Map<String, Object>> columns = new ArrayList<>();
            internalRequestBody.put("columns", columns);
            for (PostDataTableRequest.Column v : requestBody.getColumns()) {
                Map<String, Object> column = new HashMap<>();
                String type = v.getType();
                column.put("type", type);
                column.put("name", v.getName());
                column.put("mandatory", v.isMandatory());
                if ("String".equals(type)) {
                    column.put("length", v.getLength());
                } else if ("DropDown".equals(type)) {
                    column.put("code", v.getCode());
                }
                columns.add(column);
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
        requestBuilder.setUri(this.baseUrl + "/datatables");
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
    public FineractResponse datatableUpdate(String tenant, String token, String datatableName, PutDataTableRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        if (requestBody.getApptableName() != null) {
            internalRequestBody.put("apptableName", requestBody.getApptableName().getLiteral());
        } else {
            internalRequestBody.put("apptableName", null);
        }

        if (requestBody.getAddColumns() != null && !requestBody.getAddColumns().isEmpty()) {
            List<Map<String, Object>> columns = new ArrayList<>();
            internalRequestBody.put("addColumns", columns);
            for (PutDataTableRequest.AddColumn v : requestBody.getAddColumns()) {
                Map<String, Object> column = new HashMap<>();
                String type = v.getType();
                column.put("type", type);
                column.put("name", v.getName());
                column.put("mandatory", v.isMandatory());
                if ("String".equals(type)) {
                    column.put("length", v.getLength());
                } else if ("DropDown".equals(type)) {
                    column.put("code", v.getCode());
                }
                columns.add(column);
            }
        }
        if (requestBody.getChangeColumns() != null && !requestBody.getChangeColumns().isEmpty()) {
            List<Map<String, Object>> columns = new ArrayList<>();
            internalRequestBody.put("changeColumns", columns);
            for (PutDataTableRequest.ChangeColumn v : requestBody.getChangeColumns()) {
                Map<String, Object> column = new HashMap<>();
                String type = v.getType();
                column.put("name", v.getName());
                column.put("newName", v.getNewName());
                column.put("mandatory", v.isMandatory());
                if ("String".equals(type)) {
                    column.put("length", v.getLength());
                } else if ("DropDown".equals(type)) {
                    column.put("code", v.getCode());
                    column.put("newCode", v.getNewCode());
                }
                columns.add(column);
            }
        }
        if (requestBody.getDropColumns() != null && !requestBody.getDropColumns().isEmpty()) {
            List<Map<String, Object>> columns = new ArrayList<>();
            internalRequestBody.put("dropColumns", columns);
            for (PutDataTableRequest.DropColumn v : requestBody.getDropColumns()) {
                Map<String, Object> column = new HashMap<>();
                column.put("name", v.getName());
                columns.add(column);
            }
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
        requestBuilder.setUri(this.baseUrl + "/datatables/" + datatableName);
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
    public FineractResponse datatableDelete(String tenant, String token, String datatableName) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/datatables/" + datatableName);
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
    public FineractResponse datatableRegister(String tenant, String token, String datatable, TableTypeEnum apptable, PostDataTableRegisterRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put("category", requestBody.getCategory().getLiteral());
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/datatables/register/" + datatable + "/" + apptable.getLiteral());
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
    public FineractResponse datatableDeregister(String tenant, String token, String datatable) {
        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/datatables/deregister/" + datatable);
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
    public FineractResponse datatableEntryCreate(String tenant, String token, String datatable, long apptableId, Map<String, Object> requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.putAll(requestBody);
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/datatables/" + datatable + "/" + apptableId);
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
    public FineractResponse datatableEntryOne2OneUpdate(String tenant, String token, String datatable, long apptableId, Map<String, Object> requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.putAll(requestBody);
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/datatables/" + datatable + "/" + apptableId);
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
    public FineractResponse datatableEntryOne2ManyUpdate(String tenant, String token, String datatable, long apptableId, long datatableId, Map<String, Object> requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.putAll(requestBody);
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/datatables/" + datatable + "/" + apptableId + "/" + datatableId);
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
    public FineractResponse datatableEntryClear(String tenant, String token, String datatable, long apptableId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/datatables/" + datatable + "/" + apptableId);
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
    public FineractResponse datatableEntryDelete(String tenant, String token, String datatable, long apptableId, long datatableId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/datatables/" + datatable + "/" + apptableId + "/" + datatableId);
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
    public FineractResponse datatableEntityCheckCreate(String tenant, String token, PostDatatableEntityCheckRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put(LOCALE_KEY, LOCALE_VALUE);
        internalRequestBody.put(DATE_FORMAT_KEY, DATE_FORMAT_VALUE);
        internalRequestBody.put("datatableName", requestBody.getDatatableName());
        if (requestBody.getEntity() != null) {
            internalRequestBody.put("entity", requestBody.getEntity().getLiteral());
        }
        if (requestBody.getStatus() != null) {
            internalRequestBody.put("status", Integer.valueOf(requestBody.getStatus().getLiteral()));
        }
        if (requestBody.getProductId() > 0) {
            internalRequestBody.put("productId", requestBody.getProductId());
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
        requestBuilder.setUri(this.baseUrl + "/entityDatatableChecks");
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
    public FineractResponse datatableEntityCheckDelete(String tenant, String token, long entityDatatableCheckId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/entityDatatableChecks/" + entityDatatableCheckId);
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
    public FineractResponse reportCreate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse reportUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse reportDelete(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse documentCreate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse documentUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse documentDelete(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse imageCreate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse imageCreate(String tenant, String token, long abc) {
        return null;
    }

    @Override
    public FineractResponse imageUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse imageUpdate(String tenant, String token, long abc) {
        return null;
    }

    @Override
    public FineractResponse imageDelete(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse entityCreate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse entityUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse entityDelete(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse deviceRegistration(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse deviceUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse deviceDelete(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse hookCreate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse hookUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse hookDelete(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse jobStart(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse jobStop(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse jobExecute(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse jobDetailUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse reportMailingCreate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse reportMailingUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse reportMailingDelete(String tenant, String token) {
        return null;
    }

    @Override
    public PostAuthenticationResponse tokenRequest(String tenant, AuthenticateRequest requestBody) {
        return null;
    }

    @Override
    public PostAuthenticationResponse tokenValidation(String tenant, AuthenticateRequest requestBody) {
        return null;
    }

    @Override
    public PostAuthenticationResponse tokenUpdate(String tenant, AuthenticateRequest requestBody) {
        return null;
    }

    @Override
    public PostAuthenticationResponse twoFactorConfigurationUpdate(String tenant, AuthenticateRequest requestBody) {
        return null;
    }

    @Override
    public FineractResponse smsCreate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse smsUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse smsDelete(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse likelihoodUpdate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse surveyCreate(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse surveyRegister(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse surveyDelete(String tenant, String token) {
        return null;
    }

    @Override
    public FineractResponse interoperationTransfer(String tenant, String token, long staffId, PutStaffRequest requestBody) {
        return null;
    }

    @Override
    public FineractResponse interoperationQuoteCreate(String tenant, String token, long staffId, PutStaffRequest requestBody) {
        return null;
    }

    @Override
    public FineractResponse interoperationTransactionCreate(String tenant, String token, long staffId, PutStaffRequest requestBody) {
        return null;
    }

    @Override
    public FineractResponse accountIdentifierDelete(String tenant, String token, long staffId, PutStaffRequest requestBody) {
        return null;
    }

    @Override
    public FineractResponse accountIdentifierDelete(String tenant, String token, long staffId, PutStaffRequest requestBody, long abc) {
        return null;
    }

    @Override
    public FineractResponse accountIdentifierRegister(String tenant, String token, long staffId, PutStaffRequest requestBody) {
        return null;
    }

    @Override
    public FineractResponse accountIdentifierRegister(String tenant, String token, long staffId, PutStaffRequest requestBody, long abc) {
        return null;
    }

    @Override
    public FineractResponse notificationUpdate(String tenant, String token, long auditId) {
        return null;
    }

    @Override
    public FineractResponse userCreate(String tenant, String token, PostUserRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        if (requestBody.getOfficeId() > 0) {
            internalRequestBody.put("officeId", requestBody.getOfficeId());
        }
        if (requestBody.getStaffId() > 0) {
            internalRequestBody.put("staffId", requestBody.getStaffId());
        }
        if (requestBody.getRoles() != null && !requestBody.getRoles().isEmpty()) {
            internalRequestBody.put("roles", requestBody.getRoles());
        }
        internalRequestBody.put("isSelfServiceUser", requestBody.isSelfServiceUser());
        if (requestBody.getClients() != null && !requestBody.getClients().isEmpty()) {
            internalRequestBody.put("clients", requestBody.getClients());
        }
        internalRequestBody.put("sendPasswordToEmail", requestBody.isSendPasswordToEmail());
        internalRequestBody.put("username", requestBody.getUsername());
        internalRequestBody.put("password", requestBody.getPassword());
        internalRequestBody.put("repeatPassword", requestBody.getPassword());
        internalRequestBody.put("passwordNeverExpires", requestBody.isPasswordNeverExpires());
        internalRequestBody.put("email", requestBody.getEmail());
        internalRequestBody.put("firstname", requestBody.getFirstName());
        internalRequestBody.put("lastname", requestBody.getLastName());

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/users");
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
    public FineractResponse userUpdate(String tenant, String token, long userId, PutUserRequest requestBody) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        if (requestBody.getOfficeId() > 0) {
            internalRequestBody.put("officeId", requestBody.getOfficeId());
        }
        if (requestBody.getStaffId() > 0) {
            internalRequestBody.put("staffId", requestBody.getStaffId());
        }
        if (requestBody.getRoles() != null && !requestBody.getRoles().isEmpty()) {
            internalRequestBody.put("roles", requestBody.getRoles());
        }
        internalRequestBody.put("isSelfServiceUser", requestBody.isSelfServiceUser());
        if (requestBody.getClients() != null && !requestBody.getClients().isEmpty()) {
            internalRequestBody.put("clients", requestBody.getClients());
        }
        internalRequestBody.put("sendPasswordToEmail", requestBody.isSendPasswordToEmail());
        internalRequestBody.put("username", requestBody.getUsername());
        internalRequestBody.put("password", requestBody.getPassword());
        internalRequestBody.put("repeatPassword", requestBody.getPassword());
        internalRequestBody.put("passwordNeverExpires", requestBody.isPasswordNeverExpires());
        internalRequestBody.put("email", requestBody.getEmail());
        internalRequestBody.put("firstname", requestBody.getFirstName());
        internalRequestBody.put("lastname", requestBody.getLastName());

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/users/" + userId);
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
    public FineractResponse userDelete(String tenant, String token, long userId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/users/" + userId);
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
    public FineractResponse roleDelete(String tenant, String token, long roleId) {
        RequestBuilder requestBuilder = RequestBuilder.create("DELETE");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/roles/" + roleId);
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
    public FineractResponse roleDisable(String tenant, String token, long roleId) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/roles/" + roleId + "?command=disable");
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
    public FineractResponse roleEnable(String tenant, String token, long roleId) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/roles/" + roleId + "?command=enable");
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
    public FineractResponse rolePermissionUpdate(String tenant, String token, long roleId, Map<String, Boolean> permissions) {
        Map<String, Object> internalRequestBody = new HashMap<>();
        internalRequestBody.put("permissions", permissions);

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(internalRequestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/roles/" + roleId + "/permissions");
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
    public FineractResponse surveyCreate(String tenant, String token, SurveyRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/surveys");
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
    public FineractResponse surveyUpdate(String tenant, String token, long surveyId, SurveyRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("PUT");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/surveys/" + surveyId);
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
    public FineractResponse surveyDeactivate(String tenant, String token, long surveyId) {
        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/surveys/" + surveyId + "?command=deactivate");
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
    public FineractResponse surveyActivate(String tenant, String token, long surveyId) {
        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setUri(this.baseUrl + "/surveys/" + surveyId + "?command=activate");
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
    public FineractResponse surveyScoreCreate(String tenant, String token, long surveyId, PostScoreRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/surveys/scorecards/" + surveyId);
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
    public FineractResponse surveyLookupTableCreate(String tenant, String token, long surveyId, PostLookupTableRequest requestBody) {
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);
        entityBuilder.setContentEncoding(StandardCharsets.UTF_8.name());
        entityBuilder.setText(this.gson.toJson(requestBody));
        HttpEntity entity = entityBuilder.build();

        RequestBuilder requestBuilder = RequestBuilder.create("POST");
        requestBuilder.addHeader("Fineract-Platform-TenantId", tenant);
        requestBuilder.addHeader("Authorization", "Basic " + token);
        requestBuilder.setEntity(entity);
        requestBuilder.setUri(this.baseUrl + "/surveys/" + surveyId + "/lookuptables");
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