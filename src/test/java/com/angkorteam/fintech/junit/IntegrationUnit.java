package com.angkorteam.fintech.junit;

import java.io.File;

import org.apache.catalina.LifecycleState;
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
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

public class IntegrationUnit extends RunListener {

    private Tomcat tomcat;

    public void testRunStarted(Description description) throws Exception {
        this.tomcat = new Tomcat();

        this.tomcat.getServer().addLifecycleListener(new VersionLoggerListener());
        this.tomcat.getServer().addLifecycleListener(new org.apache.catalina.core.JreMemoryLeakPreventionListener());
        this.tomcat.getServer().addLifecycleListener(new org.apache.catalina.mbeans.GlobalResourcesLifecycleListener());
        this.tomcat.getServer().addLifecycleListener(new org.apache.catalina.core.ThreadLocalLeakPreventionListener());

        this.tomcat.getService().addConnector(http(9080));

        String webappDirLocation = "src/main/webapp/";

        StandardContext ctx = (StandardContext) this.tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        this.tomcat.start();
        while (this.tomcat.getServer().getState() != LifecycleState.STARTED) {
            Thread.sleep(1000);
        }
    }

    public void testRunFinished(Result result) throws Exception {
        this.tomcat.stop();
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
