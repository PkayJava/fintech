package com.angkorteam.fintech.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class JavaMailSenderFactoryBean implements FactoryBean<JavaMailSender> {

    private String host;

    private String username;

    private String password;

    private int port;

    private String protocol;

    private boolean ssl;

    private boolean debug;

    @Override
    public JavaMailSender getObject() throws Exception {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(this.host);
        sender.setPassword(this.password);
        sender.setUsername(this.username);
        sender.setPort(this.port);
        sender.setProtocol(this.protocol);
        Properties properties = new Properties();
        if (this.username != null || this.password != null) {
            properties.put("mail.smtp.auth", true);
        }
        if (this.ssl) {
            properties.put("mail.smtp.ssl.enable", true);
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }
        if (this.debug) {
            properties.put("mail.debug", true);
        }
        sender.setJavaMailProperties(properties);
        return sender;
    }

    @Override
    public Class<?> getObjectType() {
        return JavaMailSender.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

}
