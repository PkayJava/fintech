package com.angkorteam.fintech;

import javax.servlet.ServletContext;

import org.apache.commons.configuration.XMLPropertiesConfiguration;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.angkorteam.fintech.pages.LoginPage;
import com.angkorteam.fintech.pages.product.FixedDepositCreatePage;
import com.angkorteam.framework.ResourceScope;

/**
 * Created by socheatkhauv on 6/11/17.
 */
public class Application extends AuthenticatedWebApplication {

    @Override
    protected void init() {
        super.init();
        getJavaScriptLibrarySettings().setJQueryReference(new PackageResourceReference(ResourceScope.class, "AdminLTE/plugins/jQuery/jquery-3.1.1.min.js"));
    }

    @Override
    public Class<? extends Page> getHomePage() {
        // return UserBrowsePage.class;
        return FixedDepositCreatePage.class;
    }

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        XMLPropertiesConfiguration configuration = applicationContext.getBean(XMLPropertiesConfiguration.class);
        return RuntimeConfigurationType.valueOf(configuration.getString("wicket"));
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return Session.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }

}
