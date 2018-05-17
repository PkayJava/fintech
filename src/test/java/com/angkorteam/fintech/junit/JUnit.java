package com.angkorteam.fintech.junit;

import java.io.File;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.apache.tomcat.util.net.SSLHostConfigCertificate.Type;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.springframework.web.context.ContextLoaderListener;

import com.angkorteam.fintech.Constants;
import com.angkorteam.framework.spring.JdbcTemplate;

public class JUnit extends RunListener {

    private static JUnitApplication application;

    private static JUnitMockServletContext servletContext;

    private static File location;

    private static JUnitWicketTester wicket;

    public void testRunStarted(Description description) throws Exception {
        location = new File("");
        application = new JUnitApplication();
        servletContext = new JUnitMockServletContext(application, null);
        servletContext.addInitParameter("contextConfigLocation", "/WEB-INF/api-context.xml");
        ContextLoaderListener listener = new ContextLoaderListener();
        listener.initWebApplicationContext(servletContext);
        wicket = new JUnitWicketTester();
        JdbcTemplate jdbcTemplate = wicket.getJdbcTemplate();
        jdbcTemplate.update("update m_appuser set username = ?, email = ? where id = ?", Constants.UID, "pkayjava@gmail.com", "1");
        jdbcTemplate.update("update m_appuser set email = ? where id = ?", "system@angkorteam.com", "2");
    }

    public void testRunFinished(Result result) throws Exception {
    }

    public static JUnitApplication getApplication() {
        return application;
    }

    public static File getLocation() {
        return location;
    }

    public static JUnitMockServletContext getServletContext() {
        return servletContext;
    }

    public static JUnitWicketTester getWicket() {
        return wicket;
    }

    public Connector http(int port) {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(port);
        connector.setURIEncoding("UTF-8");

        connector.addUpgradeProtocol(new org.apache.coyote.http2.Http2Protocol());
        return connector;
    }

    public Connector https(int port) {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(port);
        connector.setURIEncoding("UTF-8");

        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        protocol.setSSLEnabled(true);

        SSLHostConfig sslHostConfig = new SSLHostConfig();
        sslHostConfig.setProtocols("TLSv1.2");

        SSLHostConfigCertificate certificate = new SSLHostConfigCertificate(sslHostConfig, Type.RSA);
        certificate.setCertificateFile("/opt/openssl/localhost.crt");
        certificate.setCertificateKeyFile("/opt/openssl/localhost.pem");

        sslHostConfig.addCertificate(certificate);

        connector.addUpgradeProtocol(new org.apache.coyote.http2.Http2Protocol());
        connector.addSslHostConfig(sslHostConfig);
        return connector;
    }

}
