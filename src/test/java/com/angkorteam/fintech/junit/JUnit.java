package com.angkorteam.fintech.junit;

import java.io.File;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.springframework.web.context.ContextLoaderListener;

public class JUnit extends RunListener {

    private static JUnitApplication application;

    private static JUnitMockServletContext servletContext;

    private static File location;

    public void testRunStarted(Description description) throws Exception {
	location = new File("");
	application = new JUnitApplication();
	servletContext = new JUnitMockServletContext(application, null);
	servletContext.addInitParameter("contextConfigLocation", "/WEB-INF/api-context.xml");
	ContextLoaderListener listener = new ContextLoaderListener();
	listener.initWebApplicationContext(servletContext);
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

}
