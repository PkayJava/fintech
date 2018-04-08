package com.angkorteam.fintech.layout;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;

import com.angkorteam.fintech.widget.TextFeedbackPanel;

public class UIContainer extends MarkupContainer {

    private static final long serialVersionUID = 1L;

    /**
     * @see Component#Component(String)
     */
    protected UIContainer(final String id) {
        super(id);
    }

    /**
     * A convenience method to return the WebPage. Same as getPage().
     * 
     * @return WebPage
     */
    public final WebPage getWebPage() {
        return (WebPage) getPage();
    }

    /**
     * A convenience method to return the current WebRequest. Same as
     * {@link org.apache.wicket.Component#getRequest()}.
     *
     * @return the current WebRequest
     */
    public final WebRequest getWebRequest() {
        return (WebRequest) getRequest();
    }

    /**
     * A convenience method to return the current WebResponse. Same as
     * {@link org.apache.wicket.Component#getResponse()}.
     *
     * @return the current WebResponse
     */
    public final WebResponse getWebResponse() {
        return (WebResponse) getResponse();
    }

    /**
     * A convenience method to return the WebSession. Same as
     * {@link org.apache.wicket.Component#getSession()} .
     *
     * @return the current WebSession
     */
    public final WebSession getWebSession() {
        return WebSession.get();
    }

    /**
     * A convenience method to return the WebApplication. Same as
     * {@link WebApplication#get()}.
     *
     * @return the current WebApplication
     */
    public final WebApplication getWebApplication() {
        return WebApplication.get();
    }

    public TextFeedbackPanel newFeedback(String id, FormComponent<?> formComponent) {
        TextFeedbackPanel feedback = new TextFeedbackPanel(id, formComponent);
        add(feedback);
        return feedback;
    }
}