package com.angkorteam.fintech.junit;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.http.WebRequest;

import com.angkorteam.fintech.Application;

public class JUnitApplication extends Application {

    public WebRequest newWebRequest(HttpServletRequest servletRequest, final String filterPath) {
	ServletWebRequest request = new ServletWebRequest(servletRequest, filterPath);
	return request;
    }

}
