package com.angkorteam.fintech.junit;

import java.io.File;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.springframework.web.context.ContextLoaderListener;

import com.angkorteam.fintech.Constants;

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
        wicket.getJdbcTemplate().update("update m_appuser set username = ? where id = ?", Constants.UID, "1");
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

}
