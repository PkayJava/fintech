package com.angkorteam.fintech.factory;

import com.angkorteam.fintech.pages.LoginPage;
import com.angkorteam.fintech.pages.admin.UserBrowsePage;
import com.angkorteam.webui.frmk.common.AuthenticatedWicketFactory;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WicketFilter;

public class WicketFactory extends AuthenticatedWicketFactory {

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return UserBrowsePage.class;
    }

    @Override
    public void destroy(WicketFilter filter) {
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return WebSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }

}
