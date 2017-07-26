package com.angkorteam.fintech.pages;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Page;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.framework.Option;
import com.angkorteam.framework.ReferenceUtilities;
import com.angkorteam.framework.models.Login;
import com.angkorteam.framework.wicket.WicketConsumer;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class LoginPage extends WebPage {

    public static final MetaDataKey<WicketConsumer<Login>> LOGIN = new MetaDataKey<WicketConsumer<Login>>() {
    };

    public static final MetaDataKey<Class<Page>> RESET_PASSWORD = new MetaDataKey<Class<Page>>() {
    };

    public static final MetaDataKey<PageParameters> RESET_PASSWORD_PARAMETER = new MetaDataKey<PageParameters>() {
    };

    public static final MetaDataKey<Class<Page>> REGISTER = new MetaDataKey<Class<Page>>() {
    };

    public static final MetaDataKey<PageParameters> REGISTER_PARAMETER = new MetaDataKey<PageParameters>() {
    };

    private TextField<String> loginField;
    private String loginValue;

    private TextField<String> passwordField;
    private String passwordValue;

    private CheckBox rememberField;
    private boolean rememberValue;

    private Form<Void> form;
    private Button loginButton;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.form = new Form<>("form");
        this.add(this.form);

        this.loginField = new TextField<>("loginField", new PropertyModel<>(this, "loginValue"));
        this.loginField.setRequired(true);
        this.form.add(this.loginField);

        this.passwordField = new PasswordTextField("passwordField", new PropertyModel<>(this, "passwordValue"));
        this.passwordField.setRequired(true);
        this.form.add(this.passwordField);

        this.rememberField = new CheckBox("rememberField", new PropertyModel<>(this, "rememberValue"));
        this.form.add(rememberField);

        this.loginButton = new Button("loginButton");
        this.form.add(this.loginButton);
        this.loginButton.setOnSubmit(this::loginButtonClick);

        Class<Page> reset = getApplication().getMetaData(RESET_PASSWORD);
        PageParameters resetParameter = getApplication().getMetaData(RESET_PASSWORD_PARAMETER);

        Class<Page> register = getApplication().getMetaData(REGISTER);
        PageParameters registerParameter = getApplication().getMetaData(REGISTER_PARAMETER);

        WebMarkupContainer extraLink = new WebMarkupContainer("extraLink");
        this.add(extraLink);

        if (reset == null && register == null) {
            extraLink.setVisible(false);
            BookmarkablePageLink<Void> resetLink = new BookmarkablePageLink<>("resetLink", LoginPage.class);
            BookmarkablePageLink<Void> registerLink = new BookmarkablePageLink<>("registerLink", LoginPage.class);
            extraLink.add(resetLink);
            extraLink.add(registerLink);
        } else {
            if (reset == null) {
                BookmarkablePageLink<Void> resetLink = new BookmarkablePageLink<>("resetLink", LoginPage.class);
                resetLink.setVisible(false);
                extraLink.add(resetLink);
            } else {
                BookmarkablePageLink<Void> resetLink = new BookmarkablePageLink<>("resetLink", reset, resetParameter);
                extraLink.add(resetLink);
            }
            if (reset == null) {
                BookmarkablePageLink<Void> registerLink = new BookmarkablePageLink<>("registerLink", LoginPage.class);
                registerLink.setVisible(false);
                extraLink.add(registerLink);
            } else {
                BookmarkablePageLink<Void> registerLink = new BookmarkablePageLink<>("registerLink", register,
                        registerParameter);
                extraLink.add(registerLink);
            }
        }

    }

    protected void loginButtonClick(Button button) {
        WicketConsumer<Login> login = getApplication().getMetaData(LOGIN);
        if (login != null) {
            Login value = new Login(this.loginValue, this.passwordValue, this.rememberValue);
            login.accept(value);
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        Option option = new Option();
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
