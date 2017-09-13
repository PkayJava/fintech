package com.angkorteam.fintech.pages.staff;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.AppUserBuilder;
import com.angkorteam.fintech.helper.AppUserHelper;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualInputValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.List;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class UserCreatePage extends Page {

    private String firstNameValue;
    private TextField<String> firstNameField;
    private TextFeedbackPanel firstNameFeedback;

    private String emailValue;
    private TextField<String> emailField;
    private TextFeedbackPanel emailFeedback;

    private String lastNameValue;
    private TextField<String> lastNameField;
    private TextFeedbackPanel lastNameFeedback;

    private String loginValue;
    private TextField<String> loginField;
    private TextFeedbackPanel loginFeedback;

    private String passwordValue;
    private PasswordTextField passwordField;
    private TextFeedbackPanel passwordFeedback;

    private String repeatPasswordValue;
    private PasswordTextField repeatPasswordField;
    private TextFeedbackPanel repeatPasswordFeedback;

    private SingleChoiceProvider officeProvider;
    private Option officeValue;
    private Select2SingleChoice<Option> officeField;
    private TextFeedbackPanel officeFeedback;

    private SingleChoiceProvider staffProvider;
    private Option staffValue;
    private Select2SingleChoice<Option> staffField;
    private TextFeedbackPanel staffFeedback;

    private MultipleChoiceProvider permissionProvider;
    private List<Option> permissionValue;
    private Select2MultipleChoice<Option> permissionField;
    private TextFeedbackPanel permissionFeedback;

    private Boolean overridePasswordExpiryPolicyValue;
    private CheckBox overridePasswordExpiryPolicyField;
    private TextFeedbackPanel overridePasswordExpiryPolicyFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;
    
    private static final List<PageBreadcrumb> BREADCRUMB;
    
    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("User");
            breadcrumb.setPage(UserBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("User Create");
            BREADCRUMB.add(breadcrumb);
        }
    }
    
    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", UserBrowsePage.class);
        this.form.add(this.closeLink);

        this.firstNameField = new TextField<>("firstNameField", new PropertyModel<>(this, "firstNameValue"));
        this.firstNameField.setRequired(true);
        this.firstNameField.add(new OnChangeAjaxBehavior());
        this.form.add(this.firstNameField);
        this.firstNameFeedback = new TextFeedbackPanel("firstNameFeedback", this.firstNameField);
        this.form.add(this.firstNameFeedback);

        this.passwordField = new PasswordTextField("passwordField", new PropertyModel<>(this, "passwordValue"));
        this.passwordField.setRequired(false);
        this.passwordField.setResetPassword(false);
        this.passwordField.add(new OnChangeAjaxBehavior());
        this.form.add(this.passwordField);
        this.passwordFeedback = new TextFeedbackPanel("passwordFeedback", this.passwordField);
        this.form.add(this.passwordFeedback);

        this.repeatPasswordField = new PasswordTextField("repeatPasswordField",
                new PropertyModel<>(this, "repeatPasswordValue"));
        this.repeatPasswordField.setRequired(false);
        this.repeatPasswordField.setResetPassword(false);
        this.repeatPasswordField.add(new OnChangeAjaxBehavior());
        this.form.add(this.repeatPasswordField);
        this.repeatPasswordFeedback = new TextFeedbackPanel("repeatPasswordFeedback", this.repeatPasswordField);
        this.form.add(this.repeatPasswordFeedback);

        this.emailField = new TextField<>("emailField", new PropertyModel<>(this, "emailValue"));
        this.emailField.setRequired(true);
        this.emailField.add(new OnChangeAjaxBehavior());
        this.form.add(this.emailField);
        this.emailFeedback = new TextFeedbackPanel("emailFeedback", this.emailField);
        this.form.add(this.emailFeedback);

        this.lastNameField = new TextField<>("lastNameField", new PropertyModel<>(this, "lastNameValue"));
        this.lastNameField.setRequired(true);
        this.lastNameField.add(new OnChangeAjaxBehavior());
        this.form.add(this.lastNameField);
        this.lastNameFeedback = new TextFeedbackPanel("lastNameFeedback", this.lastNameField);
        this.form.add(this.lastNameFeedback);

        this.officeProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", 0, new PropertyModel<>(this, "officeValue"),
                this.officeProvider);
        this.officeField.add(new OnChangeAjaxBehavior(this::officeFieldUpdate));
        this.officeField.setRequired(true);
        this.form.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.form.add(this.officeFeedback);

        this.permissionProvider = new MultipleChoiceProvider("m_role", "id", "name");
        this.permissionField = new Select2MultipleChoice<>("permissionField", 0,
                new PropertyModel<>(this, "permissionValue"), this.permissionProvider);
        this.permissionField.add(new OnChangeAjaxBehavior());
        this.permissionField.setRequired(true);
        this.form.add(this.permissionField);
        this.permissionFeedback = new TextFeedbackPanel("permissionFeedback", this.permissionField);
        this.form.add(this.permissionFeedback);

        this.staffProvider = new SingleChoiceProvider("m_staff", "id", "display_name");
        this.staffProvider.setDisabled(true);
        this.staffField = new Select2SingleChoice<>("staffField", 0, new PropertyModel<>(this, "staffValue"),
                this.staffProvider);
        this.staffField.add(new OnChangeAjaxBehavior());
        this.form.add(this.staffField);
        this.staffFeedback = new TextFeedbackPanel("staffFeedback", this.staffField);
        this.form.add(this.staffFeedback);

        this.loginField = new TextField<>("loginField", new PropertyModel<>(this, "loginValue"));
        this.loginField.add(new OnChangeAjaxBehavior());
        this.loginField.setRequired(true);
        this.form.add(this.loginField);
        this.loginFeedback = new TextFeedbackPanel("loginFeedback", this.loginField);
        this.form.add(this.loginFeedback);

        this.overridePasswordExpiryPolicyField = new CheckBox("overridePasswordExpiryPolicyField",
                new PropertyModel<>(this, "overridePasswordExpiryPolicyValue"));
        this.overridePasswordExpiryPolicyField.setRequired(true);
        this.overridePasswordExpiryPolicyField.add(new OnChangeAjaxBehavior());
        this.form.add(this.overridePasswordExpiryPolicyField);
        this.overridePasswordExpiryPolicyFeedback = new TextFeedbackPanel("overridePasswordExpiryPolicyFeedback",
                this.overridePasswordExpiryPolicyField);
        this.form.add(this.overridePasswordExpiryPolicyFeedback);

        this.form.add(new EqualInputValidator(this.passwordField, this.repeatPasswordField));
    }

    protected boolean officeFieldUpdate(AjaxRequestTarget target) {
        this.staffValue = null;
        this.staffProvider.setDisabled(false);
        this.staffProvider.applyWhere("office", "office_id = " + this.officeValue.getId());
        target.add(this.form);
        return false;
    }

    private void saveButtonSubmit(Button button) {
        AppUserBuilder builder = new AppUserBuilder();
        builder.withFirstname(this.firstNameValue);
        builder.withLastname(this.lastNameValue);
        builder.withEmail(this.emailValue);
        builder.withUsername(this.loginValue);
        if (this.passwordValue != null && !"".equals(this.passwordValue)) {
            builder.withSendPasswordToEmail(false);
            builder.withPassword(this.passwordValue);
            builder.withRepeatPassword(this.repeatPasswordValue);
        } else {
            builder.withSendPasswordToEmail(true);
        }
        builder.withPasswordNeverExpires(this.overridePasswordExpiryPolicyValue);
        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }
        if (this.permissionValue != null) {
            for (Option role : this.permissionValue) {
                builder.withRole(role.getId());
            }
        }
        if (this.staffValue != null) {
            builder.withStaffId(this.staffValue.getId());
        }

        JsonNode node = null;
        try {
            node = AppUserHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(UserBrowsePage.class);
    }

}
