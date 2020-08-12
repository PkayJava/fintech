package com.angkorteam.fintech.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = true)
public class AppProperties {

    private String name;

    private int retryLimit;

    private String mailSenderHost;
    private int mailSenderPort;
    private String mailSenderProtocol;
    private boolean mailSenderSsl;
    private String mailSenderUsername;
    private String mailSenderPassword;
    private String mailSenderFrom;
    private String mailSenderName;

    private String jdbcDriverClassName;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;

    private String gsonDateFormat;

    private String secret;

    private String mifosUrl;

    private File cacert;

    private int validityPeriod;

    private boolean debug;

    private String timezoneId;

    private File workspace;

    private File certificate;

    private File openssl;

    private File keytool;

    public File getOpenssl() {
        return openssl;
    }

    public void setOpenssl(File openssl) {
        this.openssl = openssl;
    }

    public File getKeytool() {
        return keytool;
    }

    public void setKeytool(File keytool) {
        this.keytool = keytool;
    }

    public File getCertificate() {
        return certificate;
    }

    public void setCertificate(File certificate) {
        this.certificate = certificate;
    }

    public File getWorkspace() {
        return workspace;
    }

    public void setWorkspace(File workspace) {
        this.workspace = workspace;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(String timezoneId) {
        this.timezoneId = timezoneId;
    }

    public String getMailSenderHost() {
        return mailSenderHost;
    }

    public void setMailSenderHost(String mailSenderHost) {
        this.mailSenderHost = mailSenderHost;
    }

    public int getMailSenderPort() {
        return mailSenderPort;
    }

    public void setMailSenderPort(int mailSenderPort) {
        this.mailSenderPort = mailSenderPort;
    }

    public String getMailSenderProtocol() {
        return mailSenderProtocol;
    }

    public void setMailSenderProtocol(String mailSenderProtocol) {
        this.mailSenderProtocol = mailSenderProtocol;
    }

    public boolean isMailSenderSsl() {
        return mailSenderSsl;
    }

    public void setMailSenderSsl(boolean mailSenderSsl) {
        this.mailSenderSsl = mailSenderSsl;
    }

    public String getMailSenderUsername() {
        return mailSenderUsername;
    }

    public void setMailSenderUsername(String mailSenderUsername) {
        this.mailSenderUsername = mailSenderUsername;
    }

    public String getMailSenderPassword() {
        return mailSenderPassword;
    }

    public void setMailSenderPassword(String mailSenderPassword) {
        this.mailSenderPassword = mailSenderPassword;
    }

    public String getMailSenderFrom() {
        return mailSenderFrom;
    }

    public void setMailSenderFrom(String mailSenderFrom) {
        this.mailSenderFrom = mailSenderFrom;
    }

    public String getMailSenderName() {
        return mailSenderName;
    }

    public void setMailSenderName(String mailSenderName) {
        this.mailSenderName = mailSenderName;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(int validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getGsonDateFormat() {
        return gsonDateFormat;
    }

    public void setGsonDateFormat(String gsonDateFormat) {
        this.gsonDateFormat = gsonDateFormat;
    }

    public String getJdbcDriverClassName() {
        return jdbcDriverClassName;
    }

    public void setJdbcDriverClassName(String jdbcDriverClassName) {
        this.jdbcDriverClassName = jdbcDriverClassName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public int getRetryLimit() {
        return retryLimit;
    }

    public void setRetryLimit(int retryLimit) {
        this.retryLimit = retryLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMifosUrl() {
        return mifosUrl;
    }

    public void setMifosUrl(String mifosUrl) {
        this.mifosUrl = mifosUrl;
    }

    public File getCacert() {
        return cacert;
    }

    public void setCacert(File cacert) {
        this.cacert = cacert;
    }
}
