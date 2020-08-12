//package com.angkorteam.fintech;
//
//import java.io.File;
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//
//import org.apache.catalina.LifecycleException;
//import org.apache.catalina.LifecycleState;
//import org.apache.catalina.WebResourceRoot;
//import org.apache.catalina.connector.Connector;
//import org.apache.catalina.core.StandardContext;
//import org.apache.catalina.startup.Tomcat;
//import org.apache.catalina.startup.VersionLoggerListener;
//import org.apache.catalina.webresources.DirResourceSet;
//import org.apache.catalina.webresources.StandardRoot;
//import org.apache.coyote.http11.Http11Nio2Protocol;
//
///**
// * Separate startup class for people that want to run the examples directly. Use
// * parameter -Dcom.sun.management.jmxremote to startup JMX (and e.g. connect
// * with jconsole).
// */
//public class Start {
//
//    private static int PORT = 7080;
//
//    /**
//     * Main function, starts the jetty server.
//     *
//     * @param args
//     * @throws ServletException
//     * @throws LifecycleException
//     * @throws InterruptedException
//     * @throws IOException
//     */
//    public static void main(String[] args) throws ServletException, LifecycleException, InterruptedException, IOException {
//
//        Tomcat tomcat = new Tomcat();
//
//        tomcat.getServer().addLifecycleListener(new VersionLoggerListener());
//        tomcat.getServer().addLifecycleListener(new org.apache.catalina.core.JreMemoryLeakPreventionListener());
//        tomcat.getServer().addLifecycleListener(new org.apache.catalina.mbeans.GlobalResourcesLifecycleListener());
//        tomcat.getServer().addLifecycleListener(new org.apache.catalina.core.ThreadLocalLeakPreventionListener());
//
//        tomcat.getService().addConnector(http(PORT));
//
//        String webapp = "src/main/webapp/";
//
//        StandardContext context = (StandardContext) tomcat.addWebapp("", new File(webapp).getAbsolutePath());
//        File classes = new File("target/classes");
//        WebResourceRoot root = new StandardRoot(context);
//        root.addPreResources(new DirResourceSet(root, "/WEB-INF/classes", classes.getAbsolutePath(), "/"));
//        context.setResources(root);
//
//        tomcat.start();
//
//        while (tomcat.getServer().getState() != LifecycleState.STARTED) {
//            Thread.sleep(1000);
//        }
//
//        Runtime.getRuntime().exec("/usr/bin/firefox http://localhost:" + PORT);
//        Runtime.getRuntime().exec("/usr/bin/firefox https://localhost:8443/community-app/app/?baseApiUrl=https://localhost:8443&tenantIdentifier=default#/");
//
//        tomcat.getServer().await();
//
//    }
//
//    public static Connector http(int port) {
//        Connector connector = new Connector(Http11Nio2Protocol.class.getName());
//        connector.setPort(port);
//        connector.setURIEncoding("UTF-8");
//
//        connector.addUpgradeProtocol(new org.apache.coyote.http2.Http2Protocol());
//        return connector;
//    }
//
//}
