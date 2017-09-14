package com.angkorteam.fintech.junit;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.core.request.handler.BookmarkableListenerRequestHandler;
import org.apache.wicket.core.request.handler.ListenerRequestHandler;
import org.apache.wicket.core.request.handler.PageAndComponentProvider;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.INamedParameters;
import org.apache.wicket.request.mapper.parameter.INamedParameters.NamedPair;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;

import com.angkorteam.fintech.Application;
import com.angkorteam.fintech.Constants;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.pages.LoginPage;

public class JUnitWicketTester extends WicketTester {

    public JUnitWicketTester() {
        super(JUnit.getApplication(), JUnit.getServletContext());
    }

    @Override
    public Session getSession() {
        return (Session) super.getSession();
    }

    @Override
    public Application getApplication() {
        return (Application) super.getApplication();
    }

    public void login() {
        this.startPage(LoginPage.class);

        FormTester form = this.newFormTester("form");

        form.setValue("identifierField", Constants.AID);
        form.setValue("loginField", Constants.UID);
        form.setValue("passwordField", Constants.PWD);
        form.submit("loginButton");

    }

    public void executeListener(final Component component, PageParameters parameters) {
        Args.notNull(component, "component");

        // there are two ways to do this. RequestCycle could be forced to call the
        // handler
        // directly but constructing and parsing the URL increases the chance of
        // triggering bugs
        Page page = component.getPage();
        PageAndComponentProvider pageAndComponentProvider = new PageAndComponentProvider(page, component);

        IRequestHandler handler = null;
        if (page.isPageStateless() || (page.isBookmarkable() && page.wasCreatedBookmarkable())) {
            handler = new BookmarkableListenerRequestHandler(pageAndComponentProvider);
        } else {
            handler = new ListenerRequestHandler(pageAndComponentProvider);
        }

        Url url = urlFor(handler);
        if (parameters != null) {
            for (NamedPair namedPair : parameters.getAllNamed()) {
                if (namedPair.getType() == INamedParameters.Type.QUERY_STRING) {
                    url.addQueryParameter(namedPair.getKey(), namedPair.getValue());
                }
            }
        }
        getRequest().setUrl(url);

        // Process the request
        processRequest(getRequest(), null);
    }

}
