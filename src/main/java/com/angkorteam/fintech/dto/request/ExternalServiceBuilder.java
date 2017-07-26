package com.angkorteam.fintech.dto.request;

import com.angkorteam.fintech.dto.ServiceType;
import com.mashape.unirest.http.JsonNode;

import java.io.Serializable;

public class ExternalServiceBuilder implements Serializable {

    private ServiceType type;

    // S3
    private String s3AccessKey;
    private boolean hasS3AccessKey;

    private String s3BucketName;
    private boolean hasS3BucketName;

    private String s3SecretKey;
    private boolean hasS3SecretKey;

    // SMT & SMTP
    private String host;
    private boolean hasHost;

    private Integer port;
    private boolean hasPort;

    // SMTP
    private String username;
    private boolean hasUsername;

    private String password;
    private boolean hasPassword;

    private boolean useTls;
    private boolean hasUseTls;

    // SMS
    private String endpoint;
    private boolean hasEndpoint;

    private String tenantAppKey;
    private boolean hasTenantAppKey;

    public ExternalServiceBuilder(ServiceType type) {
        this.type = type;
    }

    public ExternalServiceBuilder withTenantAppKey(String tenantAppKey) {
        this.tenantAppKey = tenantAppKey;
        this.hasTenantAppKey = true;
        return this;
    }

    public ExternalServiceBuilder withEndpoint(String endpoint) {
        this.endpoint = endpoint;
        this.hasEndpoint = true;
        return this;
    }

    public ExternalServiceBuilder withUseTls(boolean useTls) {
        this.useTls = useTls;
        this.hasUseTls = true;
        return this;
    }

    public ExternalServiceBuilder withPort(Integer port) {
        this.port = port;
        this.hasPort = true;
        return this;
    }

    public ExternalServiceBuilder withHost(String host) {
        this.host = host;
        this.hasHost = true;
        return this;
    }

    public ExternalServiceBuilder withPassword(String password) {
        this.password = password;
        this.hasPassword = true;
        return this;
    }

    public ExternalServiceBuilder withUsername(String username) {
        this.username = username;
        this.hasUsername = true;
        return this;
    }

    public ExternalServiceBuilder withS3BucketName(String s3BucketName) {
        this.s3BucketName = s3BucketName;
        this.hasS3BucketName = true;
        return this;
    }

    public ExternalServiceBuilder withS3AccessKey(String s3AccessKey) {
        this.s3AccessKey = s3AccessKey;
        this.hasS3AccessKey = true;
        return this;
    }

    public ExternalServiceBuilder withS3SecretKey(String s3SecretKey) {
        this.s3SecretKey = s3SecretKey;
        this.hasS3SecretKey = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        object.getObject().put("type", this.type.name());
        if (this.hasS3AccessKey) {
            object.getObject().put("s3_access_key", this.s3AccessKey);
        }
        if (this.hasS3BucketName) {
            object.getObject().put("s3_bucket_name", this.s3BucketName);
        }
        if (this.hasS3SecretKey) {
            object.getObject().put("s3_secret_key", this.s3SecretKey);
        }
        if (this.hasUsername) {
            object.getObject().put("username", this.username);
        }
        if (this.hasPassword) {
            object.getObject().put("password", this.password);
        }
        if (this.hasHost) {
            if (this.type == ServiceType.SMTP) {
                object.getObject().put("host", this.host);
            } else if (this.type == ServiceType.SMS) {
                object.getObject().put("host_name", this.host);
            }
        }
        if (this.hasPort) {
            if (this.type == ServiceType.SMTP) {
                object.getObject().put("port", this.port);
            } else if (this.type == ServiceType.SMS) {
                object.getObject().put("port_number", this.port);
            }
        }
        if (this.hasTenantAppKey) {
            object.getObject().put("tenant_app_key", this.tenantAppKey);
        }
        if (this.hasUseTls) {
            object.getObject().put("useTLS", this.useTls);
        }
        return object;
    }

}
