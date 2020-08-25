//package com.angkorteam.fintech.pages.staff;
//
//import java.util.List;
//
//import org.apache.wicket.ajax.AjaxRequestTarget;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import com.angkorteam.fintech.widget.WebMarkupContainer;
//import org.apache.wicket.markup.html.form.CheckBox;
//import org.apache.wicket.markup.html.form.PasswordTextField;
//import org.apache.wicket.markup.html.form.TextField;
//import org.apache.wicket.markup.html.form.validation.EqualInputValidator;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.Session;
//import com.angkorteam.fintech.ddl.MOffice;
//import com.angkorteam.fintech.ddl.MRole;
//import com.angkorteam.fintech.ddl.MStaff;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.builder.AppUserBuilder;
//import com.angkorteam.fintech.helper.AppUserHelper;
//import com.angkorteam.fintech.layout.Size;
//import com.angkorteam.fintech.provider.MultipleChoiceProvider;
//import com.angkorteam.fintech.provider.SingleChoiceProvider;
//import com.angkorteam.fintech.widget.TextFeedbackPanel;
//import com.angkorteam.fintech.widget.WebMarkupBlock;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
//import com.angkorteam.framework.wicket.markup.html.form.Button;
//import com.angkorteam.framework.wicket.markup.html.form.Form;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
//import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
//import com.google.common.collect.Lists;
//import io.github.openunirest.http.JsonNode;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class UserCreatePage extends Page {
//
//    protected Form<Void> form;
//    protected Button saveButton;
//    protected BookmarkablePageLink<Void> closeLink;
//
//    protected WebMarkupBlock firstNameBlock;
//    protected WebMarkupContainer firstNameIContainer;
//    protected String firstNameValue;
//    protected TextField<String> firstNameField;
//    protected TextFeedbackPanel firstNameFeedback;
//
//    protected WebMarkupBlock emailBlock;
//    protected WebMarkupContainer emailIContainer;
//    protected String emailValue;
//    protected TextField<String> emailField;
//    protected TextFeedbackPanel emailFeedback;
//
//    protected WebMarkupBlock lastNameBlock;
//    protected WebMarkupContainer lastNameIContainer;
//    protected String lastNameValue;
//    protected TextField<String> lastNameField;
//    protected TextFeedbackPanel lastNameFeedback;
//
//    protected WebMarkupBlock loginBlock;
//    protected WebMarkupContainer loginIContainer;
//    protected String loginValue;
//    protected TextField<String> loginField;
//    protected TextFeedbackPanel loginFeedback;
//
//    protected WebMarkupBlock autoGeneratePasswordBlock;
//    protected WebMarkupContainer autoGeneratePasswordIContainer;
//    protected Boolean autoGeneratePasswordValue;
//    protected CheckBox autoGeneratePasswordField;
//    protected TextFeedbackPanel autoGeneratePasswordFeedback;
//
//    protected WebMarkupBlock passwordBlock;
//    protected WebMarkupContainer passwordIContainer;
//    protected String passwordValue;
//    protected PasswordTextField passwordField;
//    protected TextFeedbackPanel passwordFeedback;
//
//    protected WebMarkupBlock repeatPasswordBlock;
//    protected WebMarkupContainer repeatPasswordIContainer;
//    protected String repeatPasswordValue;
//    protected PasswordTextField repeatPasswordField;
//    protected TextFeedbackPanel repeatPasswordFeedback;
//
//    protected WebMarkupBlock officeBlock;
//    protected WebMarkupContainer officeIContainer;
//    protected SingleChoiceProvider officeProvider;
//    protected Option officeValue;
//    protected Select2SingleChoice<Option> officeField;
//    protected TextFeedbackPanel officeFeedback;
//
//    protected WebMarkupBlock staffBlock;
//    protected WebMarkupContainer staffIContainer;
//    protected SingleChoiceProvider staffProvider;
//    protected Option staffValue;
//    protected Select2SingleChoice<Option> staffField;
//    protected TextFeedbackPanel staffFeedback;
//
//    protected WebMarkupBlock permissionBlock;
//    protected WebMarkupContainer permissionIContainer;
//    protected MultipleChoiceProvider permissionProvider;
//    protected List<Option> permissionValue;
//    protected Select2MultipleChoice<Option> permissionField;
//    protected TextFeedbackPanel permissionFeedback;
//
//    protected WebMarkupBlock overridePasswordExpiryPolicyBlock;
//    protected WebMarkupContainer overridePasswordExpiryPolicyIContainer;
//    protected Boolean overridePasswordExpiryPolicyValue;
//    protected CheckBox overridePasswordExpiryPolicyField;
//    protected TextFeedbackPanel overridePasswordExpiryPolicyFeedback;
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Admin");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("User");
//            breadcrumb.setPage(UserBrowsePage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("User Create");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initData() {
//    }
//
//    @Override
//    protected void initComponent() {
//        this.form = new Form<>("form");
//        add(this.form);
//
//        this.saveButton = new Button("saveButton");
//        this.saveButton.setOnSubmit(this::saveButtonSubmit);
//        this.form.add(this.saveButton);
//
//        this.closeLink = new BookmarkablePageLink<>("closeLink", UserBrowsePage.class);
//        this.form.add(this.closeLink);
//
//        initFirstNameBlock();
//
//        initPasswordBlock();
//
//        initRepeatPasswordBlock();
//
//        initEmailBlock();
//
//        initLastNameBlock();
//
//        initOfficeBlock();
//
//        initPermissionBlock();
//
//        initStaffBlock();
//
//        initLoginBlock();
//
//        this.autoGeneratePasswordBlock = new WebMarkupBlock("autoGeneratePasswordBlock", Size.Six_6);
//        this.form.add(this.autoGeneratePasswordBlock);
//        this.autoGeneratePasswordIContainer = new WebMarkupContainer("autoGeneratePasswordIContainer");
//        this.autoGeneratePasswordBlock.add(this.autoGeneratePasswordIContainer);
//        this.autoGeneratePasswordField = new CheckBox("autoGeneratePasswordField", new PropertyModel<>(this, "autoGeneratePasswordValue"));
//        this.autoGeneratePasswordField.add(new OnChangeAjaxBehavior(this::autoGeneratePasswordFieldUpdate));
//        this.autoGeneratePasswordIContainer.add(this.autoGeneratePasswordField);
//        this.autoGeneratePasswordFeedback = new TextFeedbackPanel("autoGeneratePasswordFeedback", this.autoGeneratePasswordField);
//        this.autoGeneratePasswordIContainer.add(this.autoGeneratePasswordFeedback);
//
//        initOverridePasswordExpiryPolicyBlock();
//    }
//
//    protected boolean autoGeneratePasswordFieldUpdate(AjaxRequestTarget target) {
//        if (this.autoGeneratePasswordValue != null && this.autoGeneratePasswordValue) {
//            this.passwordIContainer.setVisible(false);
//            this.repeatPasswordIContainer.setVisible(false);
//        } else {
//            this.passwordIContainer.setVisible(true);
//            this.repeatPasswordIContainer.setVisible(true);
//        }
//        if (target != null) {
//            target.add(this.passwordBlock);
//            target.add(this.repeatPasswordBlock);
//        }
//        return false;
//    }
//
//    @Override
//    protected void configureMetaData() {
//        this.overridePasswordExpiryPolicyField.setRequired(true);
//        this.loginField.setRequired(true);
//        this.autoGeneratePasswordField.setRequired(true);
//        this.permissionField.setRequired(true);
//        this.officeField.setRequired(true);
//        this.lastNameField.setRequired(true);
//        this.emailField.setRequired(true);
//        this.firstNameField.setRequired(true);
//        this.passwordField.setRequired(true);
//        this.repeatPasswordField.setRequired(true);
//        this.staffProvider.setDisabled(true);
//        this.form.add(new EqualInputValidator(this.passwordField, this.repeatPasswordField));
//
//        autoGeneratePasswordFieldUpdate(null);
//    }
//
//    protected void initFirstNameBlock() {
//        this.firstNameBlock = new WebMarkupBlock("firstNameBlock", Size.Six_6);
//        this.form.add(this.firstNameBlock);
//        this.firstNameIContainer = new WebMarkupContainer("firstNameIContainer");
//        this.firstNameBlock.add(this.firstNameIContainer);
//        this.firstNameField = new TextField<>("firstNameField", new PropertyModel<>(this, "firstNameValue"));
//        this.firstNameField.add(new OnChangeAjaxBehavior());
//        this.firstNameIContainer.add(this.firstNameField);
//        this.firstNameFeedback = new TextFeedbackPanel("firstNameFeedback", this.firstNameField);
//        this.firstNameIContainer.add(this.firstNameFeedback);
//    }
//
//    protected void initPasswordBlock() {
//        this.passwordBlock = new WebMarkupBlock("passwordBlock", Size.Six_6);
//        this.form.add(this.passwordBlock);
//        this.passwordIContainer = new WebMarkupContainer("passwordIContainer");
//        this.passwordBlock.add(this.passwordIContainer);
//        this.passwordField = new PasswordTextField("passwordField", new PropertyModel<>(this, "passwordValue"));
//        this.passwordField.setResetPassword(false);
//        this.passwordField.add(new OnChangeAjaxBehavior());
//        this.passwordIContainer.add(this.passwordField);
//        this.passwordFeedback = new TextFeedbackPanel("passwordFeedback", this.passwordField);
//        this.passwordIContainer.add(this.passwordFeedback);
//    }
//
//    protected void initRepeatPasswordBlock() {
//        this.repeatPasswordBlock = new WebMarkupBlock("repeatPasswordBlock", Size.Six_6);
//        this.form.add(this.repeatPasswordBlock);
//        this.repeatPasswordIContainer = new WebMarkupContainer("repeatPasswordIContainer");
//        this.repeatPasswordBlock.add(this.repeatPasswordIContainer);
//        this.repeatPasswordField = new PasswordTextField("repeatPasswordField", new PropertyModel<>(this, "repeatPasswordValue"));
//        this.repeatPasswordField.setResetPassword(false);
//        this.repeatPasswordField.add(new OnChangeAjaxBehavior());
//        this.repeatPasswordIContainer.add(this.repeatPasswordField);
//        this.repeatPasswordFeedback = new TextFeedbackPanel("repeatPasswordFeedback", this.repeatPasswordField);
//        this.repeatPasswordIContainer.add(this.repeatPasswordFeedback);
//    }
//
//    protected void initEmailBlock() {
//        this.emailBlock = new WebMarkupBlock("emailBlock", Size.Twelve_12);
//        this.form.add(this.emailBlock);
//        this.emailIContainer = new WebMarkupContainer("emailIContainer");
//        this.emailBlock.add(this.emailIContainer);
//        this.emailField = new TextField<>("emailField", new PropertyModel<>(this, "emailValue"));
//        this.emailField.add(new OnChangeAjaxBehavior());
//        this.emailIContainer.add(this.emailField);
//        this.emailFeedback = new TextFeedbackPanel("emailFeedback", this.emailField);
//        this.emailIContainer.add(this.emailFeedback);
//    }
//
//    protected void initLastNameBlock() {
//        this.lastNameBlock = new WebMarkupBlock("lastNameBlock", Size.Six_6);
//        this.form.add(this.lastNameBlock);
//        this.lastNameIContainer = new WebMarkupContainer("lastNameIContainer");
//        this.lastNameBlock.add(this.lastNameIContainer);
//        this.lastNameField = new TextField<>("lastNameField", new PropertyModel<>(this, "lastNameValue"));
//        this.lastNameField.add(new OnChangeAjaxBehavior());
//        this.lastNameIContainer.add(this.lastNameField);
//        this.lastNameFeedback = new TextFeedbackPanel("lastNameFeedback", this.lastNameField);
//        this.lastNameIContainer.add(this.lastNameFeedback);
//    }
//
//    protected void initOfficeBlock() {
//        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Six_6);
//        this.form.add(this.officeBlock);
//        this.officeIContainer = new WebMarkupContainer("officeIContainer");
//        this.officeBlock.add(this.officeIContainer);
//        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
//        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
//        this.officeField.add(new OnChangeAjaxBehavior(this::officeFieldUpdate));
//        this.officeIContainer.add(this.officeField);
//        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
//        this.officeIContainer.add(this.officeFeedback);
//    }
//
//    protected void initPermissionBlock() {
//        this.permissionBlock = new WebMarkupBlock("permissionBlock", Size.Twelve_12);
//        this.form.add(this.permissionBlock);
//        this.permissionIContainer = new WebMarkupContainer("permissionIContainer");
//        this.permissionBlock.add(this.permissionIContainer);
//        this.permissionProvider = new MultipleChoiceProvider(MRole.NAME, MRole.Field.ID, MRole.Field.NAME);
//        this.permissionField = new Select2MultipleChoice<>("permissionField", new PropertyModel<>(this, "permissionValue"), this.permissionProvider);
//        this.permissionField.add(new OnChangeAjaxBehavior());
//        this.permissionIContainer.add(this.permissionField);
//        this.permissionFeedback = new TextFeedbackPanel("permissionFeedback", this.permissionField);
//        this.permissionIContainer.add(this.permissionFeedback);
//    }
//
//    protected void initStaffBlock() {
//        this.staffBlock = new WebMarkupBlock("staffBlock", Size.Six_6);
//        this.form.add(this.staffBlock);
//        this.staffIContainer = new WebMarkupContainer("staffIContainer");
//        this.staffBlock.add(this.staffIContainer);
//        this.staffProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.Field.ID, MStaff.Field.DISPLAY_NAME);
//        this.staffField = new Select2SingleChoice<>("staffField", new PropertyModel<>(this, "staffValue"), this.staffProvider);
//        this.staffField.add(new OnChangeAjaxBehavior());
//        this.staffIContainer.add(this.staffField);
//        this.staffFeedback = new TextFeedbackPanel("staffFeedback", this.staffField);
//        this.staffIContainer.add(this.staffFeedback);
//    }
//
//    protected void initLoginBlock() {
//        this.loginBlock = new WebMarkupBlock("loginBlock", Size.Twelve_12);
//        this.form.add(this.loginBlock);
//        this.loginIContainer = new WebMarkupContainer("loginIContainer");
//        this.loginBlock.add(this.loginIContainer);
//        this.loginField = new TextField<>("loginField", new PropertyModel<>(this, "loginValue"));
//        this.loginField.add(new OnChangeAjaxBehavior());
//        this.loginIContainer.add(this.loginField);
//        this.loginFeedback = new TextFeedbackPanel("loginFeedback", this.loginField);
//        this.loginIContainer.add(this.loginFeedback);
//    }
//
//    protected void initOverridePasswordExpiryPolicyBlock() {
//        this.overridePasswordExpiryPolicyBlock = new WebMarkupBlock("overridePasswordExpiryPolicyBlock", Size.Six_6);
//        this.form.add(this.overridePasswordExpiryPolicyBlock);
//        this.overridePasswordExpiryPolicyIContainer = new WebMarkupContainer("overridePasswordExpiryPolicyIContainer");
//        this.overridePasswordExpiryPolicyBlock.add(this.overridePasswordExpiryPolicyIContainer);
//        this.overridePasswordExpiryPolicyField = new CheckBox("overridePasswordExpiryPolicyField", new PropertyModel<>(this, "overridePasswordExpiryPolicyValue"));
//        this.overridePasswordExpiryPolicyField.add(new OnChangeAjaxBehavior());
//        this.overridePasswordExpiryPolicyIContainer.add(this.overridePasswordExpiryPolicyField);
//        this.overridePasswordExpiryPolicyFeedback = new TextFeedbackPanel("overridePasswordExpiryPolicyFeedback", this.overridePasswordExpiryPolicyField);
//        this.overridePasswordExpiryPolicyIContainer.add(this.overridePasswordExpiryPolicyFeedback);
//    }
//
//    protected boolean officeFieldUpdate(AjaxRequestTarget target) {
//        this.staffValue = null;
//        this.staffProvider.setDisabled(false);
//        this.staffProvider.applyWhere("office", MStaff.Field.OFFICE_ID + " = " + this.officeValue.getId());
//        if (target != null) {
//            target.add(this.staffBlock);
//        }
//        return false;
//    }
//
//    protected void saveButtonSubmit(Button button) {
//        AppUserBuilder builder = new AppUserBuilder();
//        builder.withFirstname(this.firstNameValue);
//        builder.withLastname(this.lastNameValue);
//        builder.withEmail(this.emailValue);
//        builder.withUsername(this.loginValue);
//        if (this.autoGeneratePasswordValue != null && this.autoGeneratePasswordValue) {
//            builder.withSendPasswordToEmail(true);
//        } else {
//            builder.withSendPasswordToEmail(false);
//            builder.withPassword(this.passwordValue);
//            builder.withRepeatPassword(this.repeatPasswordValue);
//        }
//        builder.withPasswordNeverExpires(this.overridePasswordExpiryPolicyValue);
//        if (this.officeValue != null) {
//            builder.withOfficeId(this.officeValue.getId());
//        }
//        if (this.permissionValue != null) {
//            for (Option role : this.permissionValue) {
//                builder.withRole(role.getId());
//            }
//        }
//        if (this.staffValue != null) {
//            builder.withStaffId(this.staffValue.getId());
//        }
//
//        JsonNode node = AppUserHelper.create((Session) getSession(), builder.build());
//
//        if (reportError(node)) {
//            return;
//        }
//        setResponsePage(UserBrowsePage.class);
//    }
//
//}
