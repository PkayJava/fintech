package com.angkorteam.fintech;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.VersionLoggerListener;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.apache.tomcat.util.net.SSLHostConfigCertificate.Type;

/**
 * Separate startup class for people that want to run the examples directly. Use
 * parameter -Dcom.sun.management.jmxremote to startup JMX (and e.g. connect
 * with jconsole).
 */
public class Start {

    /**
     * Main function, starts the jetty server.
     *
     * @param args
     * @throws ServletException
     * @throws LifecycleException
     * @throws Exception
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     */
    public static void main(String[] args) throws ServletException, LifecycleException {

        Tomcat tomcat = new Tomcat();

        tomcat.getServer().addLifecycleListener(new VersionLoggerListener());
        tomcat.getServer().addLifecycleListener(new org.apache.catalina.core.JreMemoryLeakPreventionListener());
        tomcat.getServer().addLifecycleListener(new org.apache.catalina.mbeans.GlobalResourcesLifecycleListener());
        tomcat.getServer().addLifecycleListener(new org.apache.catalina.core.ThreadLocalLeakPreventionListener());

        tomcat.getService().addConnector(http(9080));

        String webappDirLocation = "src/main/webapp/";

        StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();

    }

    public static Connector http(int port) {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(port);
        connector.setURIEncoding("UTF-8");

        connector.addUpgradeProtocol(new org.apache.coyote.http2.Http2Protocol());
        return connector;
    }

    public static Connector https(int port) {
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
