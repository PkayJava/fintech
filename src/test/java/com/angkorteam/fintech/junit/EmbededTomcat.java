package com.angkorteam.fintech.junit;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.catalina.LifecycleState;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.JreMemoryLeakPreventionListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.ThreadLocalLeakPreventionListener;
import org.apache.catalina.mbeans.GlobalResourcesLifecycleListener;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.VersionLoggerListener;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.apache.tomcat.util.net.SSLHostConfigCertificate.Type;
import org.apache.wicket.WicketRuntimeException;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import com.angkorteam.fintech.MifosDataSourceManager;
import com.angkorteam.framework.HttpServletRequestDataSource;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.spring.JdbcTemplate;

public class EmbededTomcat extends RunListener {

    private Tomcat tomcat;

    private static final int PORT = 7080;

    private static EmbededTomcat instance;

    private MifosDataSourceManager dataSourceManager;

    private Map<String, DataSource> dataSource = null;

    public void testRunStarted(Description description) throws Exception {
        this.tomcat = new Tomcat();

        this.dataSource = new HashMap<>();

        this.tomcat.getServer().addLifecycleListener(new VersionLoggerListener());
        this.tomcat.getServer().addLifecycleListener(new JreMemoryLeakPreventionListener());
        this.tomcat.getServer().addLifecycleListener(new GlobalResourcesLifecycleListener());
        this.tomcat.getServer().addLifecycleListener(new ThreadLocalLeakPreventionListener());

        this.tomcat.getService().addConnector(http(PORT));

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

        instance = this;

        this.dataSourceManager = SpringBean.getBean(MifosDataSourceManager.class);

        // "/opt/openssl/localhost.crt"
        // "/opt/openssl/localhost.pem"
    }

    public JdbcTemplate getJdbcTemplate(String identifier) {
        if (this.dataSource.containsKey(identifier)) {
            return new JdbcTemplate(this.dataSource.get(identifier));
        } else {
            DataSource dataSource = this.dataSourceManager.getDataSource(identifier);
            HttpServletRequestDataSource ds = new HttpServletRequestDataSource(dataSource);
            try {
                ds.getConnection().setAutoCommit(true);
                this.dataSource.put(identifier, ds);
                return new JdbcTemplate(ds);
            } catch (SQLException e) {
                throw new WicketRuntimeException(e);
            }
        }
    }

    public JdbcNamed getJdbcNamed(String identifier) {
        if (this.dataSource.containsKey(identifier)) {
            return new JdbcNamed(this.dataSource.get(identifier));
        } else {
            DataSource dataSource = this.dataSourceManager.getDataSource(identifier);
            HttpServletRequestDataSource ds = new HttpServletRequestDataSource(dataSource);
            try {
                ds.getConnection().setAutoCommit(true);
                this.dataSource.put(identifier, ds);
                return new JdbcNamed(ds);
            } catch (SQLException e) {
                throw new WicketRuntimeException(e);
            }
        }
    }

    public static EmbededTomcat getInstance() {
        return instance;
    }

    public String getAddress() {
        return "http://localhost:" + PORT;
    }

    public void testRunFinished(Result result) throws Exception {
        this.tomcat.stop();
    }

    public Connector http(int port) {
        Connector connector = new Connector(Http11Nio2Protocol.class.getName());
        connector.setPort(port);
        connector.setURIEncoding("UTF-8");

        connector.addUpgradeProtocol(new org.apache.coyote.http2.Http2Protocol());
        return connector;
    }

    public Connector oneWaySSL(File certificateFile, File certificateKeyFile, int port) {
        Connector connector = new Connector(Http11Nio2Protocol.class.getName());
        connector.setPort(port);
        connector.setURIEncoding("UTF-8");

        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        protocol.setSSLEnabled(true);

        SSLHostConfig config = new SSLHostConfig();
        config.setProtocols("TLSv1.2");

        SSLHostConfigCertificate certificate = new SSLHostConfigCertificate(config, Type.RSA);
        certificate.setCertificateFile(certificateFile.getAbsolutePath());
        certificate.setCertificateKeyFile(certificateKeyFile.getAbsolutePath());
        config.addCertificate(certificate);

        connector.addUpgradeProtocol(new org.apache.coyote.http2.Http2Protocol());
        connector.addSslHostConfig(config);
        return connector;
    }

    public Connector twoWaySSL(File caCertificateFile, File certificateFile, File certificateKeyFile, int port) {
        Connector connector = new Connector(Http11Nio2Protocol.class.getName());
        connector.setPort(port);
        connector.setURIEncoding("UTF-8");

        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        protocol.setSSLEnabled(true);

        SSLHostConfig config = new SSLHostConfig();
        config.setCaCertificateFile(caCertificateFile.getAbsolutePath());
        config.setCertificateVerification("required");
        config.setProtocols("TLSv1.2");

        SSLHostConfigCertificate certificate = new SSLHostConfigCertificate(config, Type.RSA);
        certificate.setCertificateFile(certificateFile.getAbsolutePath());
        certificate.setCertificateKeyFile(certificateKeyFile.getAbsolutePath());
        config.addCertificate(certificate);

        connector.addUpgradeProtocol(new org.apache.coyote.http2.Http2Protocol());
        connector.addSslHostConfig(config);
        return connector;
    }

}
