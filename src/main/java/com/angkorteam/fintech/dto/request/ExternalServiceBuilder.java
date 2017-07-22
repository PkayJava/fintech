package com.angkorteam.fintech.dto.request;

import java.io.Serializable;

import com.angkorteam.fintech.dto.ServiceType;
import com.mashape.unirest.http.JsonNode;

public class ExternalServiceBuilder implements Serializable {

    private ServiceType type;

    private String s3AccessKey;
    private boolean hasS3AccessKey;

    private String s3BucketName;
    private boolean hasS3BucketName;

    private String s3SecretKey;
    private boolean hasS3SecretKey;

    private String username;
    private boolean hasUsername;

    private String password;
    private boolean hasPassword;

    private String host;
    private boolean hasHost;

    private String port;
    private boolean hasPort;

    private boolean useTLS;
    private boolean hasUseTLS;

    public ExternalServiceBuilder(ServiceType type) {
        this.type = type;
    }

    public ExternalServiceBuilder withUseTLS(boolean useTLS) {
        this.useTLS = useTLS;
        this.hasUseTLS = true;
        return this;
    }

    public ExternalServiceBuilder withPort(String port) {
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

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
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
            object.getObject().put("host", this.host);
        }
        if (this.hasPort) {
            object.getObject().put("port", this.port);
        }
        if (this.hasUseTLS) {
            object.getObject().put("useTLS", this.useTLS);
        }
        return object;
    }

}
