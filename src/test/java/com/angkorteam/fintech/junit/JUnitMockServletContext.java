package com.angkorteam.fintech.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.mock.MockServletContext;

public class JUnitMockServletContext extends MockServletContext {

    private static Map<String, String> PATHS = new HashMap<>();

    static {
	PATHS.put("/WEB-INF/api-context.xml", "src" + File.separatorChar + "main" + File.separatorChar + "webapp"
		+ File.separatorChar + "WEB-INF" + File.separatorChar + "api-context.xml");
    }

    public JUnitMockServletContext(Application application, String path) {
	super(application, path);
    }

    @Override
    public String getInitParameter(String name) {
	return super.getInitParameter(name);
    }

    @Override
    public String getRealPath(String name) {
	if (PATHS.containsKey(name)) {
	    File file = new File(JUnit.getLocation(), PATHS.get(name));
	    return file.getAbsolutePath();
	}
	return super.getRealPath(name);
    }

    @Override
    public InputStream getResourceAsStream(String name) {
	if (PATHS.containsKey(name)) {
	    try {
		File file = new File(JUnit.getLocation().getAbsolutePath(), PATHS.get(name));
		return new FileInputStream(file);
	    } catch (FileNotFoundException e) {
		return null;
	    }
	}
	return super.getResourceAsStream(name);
    }

}
