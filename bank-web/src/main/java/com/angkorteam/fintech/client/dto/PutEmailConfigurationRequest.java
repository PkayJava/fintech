package com.angkorteam.fintech.client.dto;

import com.google.gson.annotations.SerializedName;

public class PutEmailConfigurationRequest {

    @SerializedName("SMTP_USERNAME")
    private String smtpUsername;

    @SerializedName("SMTP_SERVER")
    private String smtpServer;

    @SerializedName("SMTP_PORT")
    private int smtpPort;

    @SerializedName("SMTP_PASSWORD")
    private String smtpPassword;

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

}
