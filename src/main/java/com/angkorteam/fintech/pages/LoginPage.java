package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.boot.AppProperties;
import com.angkorteam.fintech.data.MifosSingleChoiceProvider;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.meta.infrastructure.Tenant;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.webui.frmk.wicket.markup.html.panel.ComponentFeedbackPanel;
import com.angkorteam.webui.frmk.wicket.resource.AdminLteMinJS;
import com.angkorteam.webui.frmk.wicket.resource.ICheckBootstrapMinCSS;
import org.apache.metamodel.DataContext;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.springframework.context.ApplicationContext;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class LoginPage extends WebPage {

    protected Label appName;

    protected MifosSingleChoiceProvider identifierProvider;
    protected Option identifierValue;
    protected Select2SingleChoice identifierField;
    protected ComponentFeedbackPanel identifierFeedback;

    protected String loginValue;
    protected TextField<String> loginField;
    protected ComponentFeedbackPanel loginFeedback;

    protected String passwordValue;
    protected TextField<String> passwordField;
    protected ComponentFeedbackPanel passwordFeedback;

    protected Form<Void> form;
    protected Button loginButton;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        ApplicationContext context = WicketFactory.getApplicationContext();
        DataContext delegateDataContext = (DataContext) context.getBean("delegateDataContext");
        Tenant tenant = Tenant.staticInitialize(delegateDataContext);
        AppProperties properties = context.getBean(AppProperties.class);

        this.appName = new Label("appName", properties.getName());
        add(this.appName);

        this.form = new Form<>("form");
        this.add(this.form);

        this.identifierProvider = new MifosSingleChoiceProvider(tenant.getName(), tenant.IDENTIFIER.getName(), tenant.NAME.getName());
        this.identifierField = new Select2SingleChoice("identifierField", new PropertyModel<>(this, "identifierValue"), this.identifierProvider);
        this.identifierField.setRequired(true);
        this.form.add(this.identifierField);
        this.identifierFeedback = new ComponentFeedbackPanel("identifierFeedback", this.identifierField);
        this.form.add(this.identifierFeedback);


        this.loginField = new TextField<>("loginField", new PropertyModel<>(this, "loginValue"));
        this.loginField.setRequired(true);
        this.form.add(this.loginField);
        this.loginFeedback = new ComponentFeedbackPanel("loginFeedback", this.loginField);
        this.form.add(this.loginFeedback);

        this.passwordField = new PasswordTextField("passwordField", new PropertyModel<>(this, "passwordValue"));
        this.passwordField.setRequired(true);
        this.form.add(this.passwordField);
        this.passwordFeedback = new ComponentFeedbackPanel("passwordFeedback", this.passwordField);
        this.form.add(this.passwordFeedback);

        this.loginButton = new Button("loginButton") {
            @Override
            public void onSubmit() {
                loginButtonClick();
            }
        };
        this.form.add(this.loginButton);
    }

    protected void loginButtonClick() {
        getSession().setAttribute(WebSession.IDENTIFIER, this.identifierValue.getId());
        WebSession webSession = (WebSession) getSession();
        boolean valid = webSession.signIn(this.loginValue, this.passwordValue);
        if (!valid) {
            this.loginField.error("invalid");
            this.passwordField.error("invalid");
        } else {
            setResponsePage(getApplication().getHomePage());
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        // <!-- icheck bootstrap -->
        response.render(CssHeaderItem.forReference(ICheckBootstrapMinCSS.INSTANCE));

        // <!-- AdminLTE App -->
        response.render(JavaScriptHeaderItem.forReference(AdminLteMinJS.INSTANCE));
        response.render(OnDomReadyHeaderItem.forScript("document.getElementById(\"" + this.loginField.getMarkupId(true) + "\").focus();"));
    }

}
