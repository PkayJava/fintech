package com.angkorteam.fintech;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import org.apache.commons.configuration.XMLPropertiesConfiguration;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.core.request.mapper.CryptoMapper;
import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.angkorteam.fintech.pages.LoginPage;
import com.angkorteam.fintech.pages.SimulatorPage;
import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
import com.angkorteam.framework.ReferenceUtilities;
import com.angkorteam.framework.ResourceScope;
import com.angkorteam.framework.SpringBean;
import com.google.common.collect.Maps;

/**
 * Created by socheatkhauv on 6/11/17.
 */
public class Application extends AuthenticatedWebApplication {

    public static Map<String, NumberFormat> FORMATS;

    static {
        FORMATS = Maps.newHashMap();
        FORMATS.put("#,###.00", new DecimalFormat("#,###.00"));
        FORMATS.put("#.00", new DecimalFormat("#.00"));
        FORMATS.put("#,###,##0.00", new DecimalFormat("#,###,##0.00"));
    }

    @Override
    protected void init() {
        super.init();
        XMLPropertiesConfiguration configuration = SpringBean.getBean(XMLPropertiesConfiguration.class);

        IPackageResourceGuard packageResourceGuard = this.getResourceSettings().getPackageResourceGuard();
        if (packageResourceGuard instanceof SecurePackageResourceGuard) {
            SecurePackageResourceGuard guard = (SecurePackageResourceGuard) packageResourceGuard;
            guard.addPattern("+*.ogg");
            guard.addPattern("+*.mp3");
        }
        getJavaScriptLibrarySettings().setJQueryReference(new PackageResourceReference(ResourceScope.class, ReferenceUtilities.J_QUERY_JS));
        mountPage("/simulator", SimulatorPage.class);

        RuntimeConfigurationType configurationType = RuntimeConfigurationType.valueOf(configuration.getString("wicket"));

        if (configurationType == RuntimeConfigurationType.DEPLOYMENT) {
            IRequestMapper cryptoMapper = new CryptoMapper(getRootRequestMapper(), this);
            setRootRequestMapper(cryptoMapper);
        }

        getMarkupSettings().setStripWicketTags(true);
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return ClientBrowsePage.class;
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
