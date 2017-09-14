package com.angkorteam.fintech.pages;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.provider.MifosSingleChoiceProvider;
import com.angkorteam.framework.ReferenceUtilities;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class LoginPage extends WebPage {

    private MifosSingleChoiceProvider identifierProvider;
    private Option identifierValue;
    private Select2SingleChoice<Option> identifierField;

    private String loginValue;
    private TextField<String> loginField;

    private String passwordValue;
    private TextField<String> passwordField;

    private Form<Void> form;
    private Button loginButton;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.form = new Form<>("form");
        this.add(this.form);

        this.identifierProvider = new MifosSingleChoiceProvider("tenants", "identifier", "name");
        this.identifierField = new Select2SingleChoice<>("identifierField", 0, new PropertyModel<>(this, "identifierValue"), this.identifierProvider);
        this.identifierField.add(new OnChangeAjaxBehavior());
        this.identifierField.setRequired(true);
        this.form.add(this.identifierField);

        this.loginField = new TextField<>("loginField", new PropertyModel<>(this, "loginValue"));
        this.loginField.setRequired(true);
        this.form.add(this.loginField);

        this.passwordField = new PasswordTextField("passwordField", new PropertyModel<>(this, "passwordValue"));
        this.passwordField.setRequired(true);
        this.form.add(this.passwordField);

        this.loginButton = new Button("loginButton");
        this.form.add(this.loginButton);
        this.loginButton.setOnSubmit(this::loginButtonClick);

    }

    protected void loginButtonClick(Button button) {
        Session session = (Session) getSession();
        HttpServletRequest request = (HttpServletRequest) getRequest().getContainerRequest();
        boolean valid = session.signIn(request.getSession(), this.identifierValue.getId(), this.loginValue, this.passwordValue);
        if (valid) {
            setResponsePage(getApplication().getHomePage());
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        com.angkorteam.framework.Option option = new com.angkorteam.framework.Option();
        option.Bootstrap = true;
        option.FontAwesome = true;
        option.Ionicons = true;
        option.AdminLTE = true;
        option.ICheck = true;
        option.HTML5ShimAndRespondJS = true;
        option.GoogleFont = true;
        option.JQuery = true;
        ReferenceUtilities.render(option, response);
    }
}
