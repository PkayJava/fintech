package com.angkorteam.fintech;

import com.angkorteam.fintech.pages.account.RuleCreatePage;
import com.angkorteam.fintech.pages.rate.FloatingRateCreatePage;
import com.angkorteam.framework.ResourceScope;
import org.apache.commons.configuration.XMLPropertiesConfiguration;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Created by socheatkhauv on 6/11/17.
 */
public class Application extends WebApplication {

    public static final String MIFOS = "http://172.16.1.6:8080";
    //    public static final String MIFOS = "http://172.16.1.3:8080";
    public static final String TENANT_IDENTIFIER = "tenantIdentifier=default";

    @Override
    protected void init() {
        super.init();
        getJavaScriptLibrarySettings().setJQueryReference(new PackageResourceReference(ResourceScope.class, "AdminLTE/plugins/jQuery/jquery-3.1.1.min.js"));
    }

    @Override
    public Class<? extends Page> getHomePage() {
//        return StarterPage.class;
        return FloatingRateCreatePage.class;
    }

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        XMLPropertiesConfiguration configuration = applicationContext.getBean(XMLPropertiesConfiguration.class);
        return RuntimeConfigurationType.valueOf(configuration.getString("wicket"));
    }
}
