package com.angkorteam.fintech.pages.staff;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualInputValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MAppUser;
import com.angkorteam.fintech.ddl.MAppUserRole;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.ddl.MRole;
import com.angkorteam.fintech.ddl.MStaff;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.AppUserBuilder;
import com.angkorteam.fintech.helper.AppUserHelper;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class UserModifyPage extends Page {

    protected String userId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock firstNameBlock;
    protected WebMarkupContainer firstNameVContainer;
    protected String firstNameValue;
    protected ReadOnlyView firstNameView;

    protected WebMarkupBlock emailBlock;
    protected WebMarkupContainer emailVContainer;
    protected String emailValue;
    protected ReadOnlyView emailView;

    protected WebMarkupBlock lastNameBlock;
    protected WebMarkupContainer lastNameVContainer;
    protected String lastNameValue;
    protected ReadOnlyView lastNameView;

    protected WebMarkupBlock loginBlock;
    protected WebMarkupContainer loginVContainer;
    protected String loginValue;
    protected ReadOnlyView loginView;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected WebMarkupBlock staffBlock;
    protected WebMarkupContainer staffIContainer;
    protected SingleChoiceProvider staffProvider;
    protected Option staffValue;
    protected Select2SingleChoice<Option> staffField;
    protected TextFeedbackPanel staffFeedback;

    protected WebMarkupBlock permissionBlock;
    protected WebMarkupContainer permissionIContainer;
    protected MultipleChoiceProvider permissionProvider;
    protected List<Option> permissionValue;
    protected Select2MultipleChoice<Option> permissionField;
    protected TextFeedbackPanel permissionFeedback;

    protected WebMarkupBlock overridePasswordExpiryPolicyBlock;
    protected WebMarkupContainer overridePasswordExpiryPolicyIContainer;
    protected Boolean overridePasswordExpiryPolicyValue;
    protected CheckBox overridePasswordExpiryPolicyField;
    protected TextFeedbackPanel overridePasswordExpiryPolicyFeedback;

    protected Form<Void> passwordForm;
    protected Button updateButton;
    protected BookmarkablePageLink<Void> passwordFormCloseLink;

    protected WebMarkupBlock passwordBlock;
    protected WebMarkupContainer passwordIContainer;
    protected String passwordValue;
    protected PasswordTextField passwordField;
    protected TextFeedbackPanel passwordFeedback;

    protected WebMarkupBlock repeatPasswordBlock;
    protected WebMarkupContainer repeatPasswordIContainer;
    protected String repeatPasswordValue;
    protected PasswordTextField repeatPasswordField;
    protected TextFeedbackPanel repeatPasswordFeedback;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
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
            breadcrumb.setLabel("User Modify");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.userId = parameters.get("userId").toString("");

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MAppUser.NAME);
        selectQuery.addWhere(MAppUser.Field.ID + " = :" + MAppUser.Field.ID, this.userId);
        selectQuery.addField(MAppUser.Field.ID);
        selectQuery.addField(MAppUser.Field.STAFF_ID);
        selectQuery.addField(MAppUser.Field.OFFICE_ID);
        selectQuery.addField(MAppUser.Field.FIRST_NAME);
        selectQuery.addField(MAppUser.Field.EMAIL);
        selectQuery.addField(MAppUser.Field.LAST_NAME);
        selectQuery.addField(MAppUser.Field.USERNAME);
        selectQuery.addField(MAppUser.Field.PASSWORD_NEVER_EXPIRES);
        Map<String, Object> userObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.firstNameValue = (String) userObject.get(MAppUser.Field.FIRST_NAME);
        this.emailValue = (String) userObject.get(MAppUser.Field.EMAIL);
        this.lastNameValue = (String) userObject.get(MAppUser.Field.LAST_NAME);
        Long overridePasswordExpiryPolicyValue = (Long) userObject.get(MAppUser.Field.PASSWORD_NEVER_EXPIRES);
        this.overridePasswordExpiryPolicyValue = overridePasswordExpiryPolicyValue == null ? false : overridePasswordExpiryPolicyValue == 1;

        selectQuery = new SelectQuery(MOffice.NAME);
        selectQuery.addWhere(MOffice.Field.ID + " = :" + MOffice.Field.ID, userObject.get(MAppUser.Field.OFFICE_ID));
        selectQuery.addField(MOffice.Field.ID);
        selectQuery.addField(MOffice.Field.NAME + " as text");
        this.officeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        selectQuery = new SelectQuery(MAppUserRole.NAME);
        selectQuery.addJoin("INNER JOIN " + MRole.NAME + " ON " + MAppUserRole.NAME + "." + MAppUserRole.Field.ROLE_ID + " = " + MRole.NAME + "." + MRole.Field.ID);
        selectQuery.addField(MRole.NAME + "." + MRole.Field.ID);
        selectQuery.addField(MRole.NAME + "." + MRole.Field.NAME + " as text");
        selectQuery.addWhere(MAppUserRole.NAME + "." + MAppUserRole.Field.APP_USER_ID + " = :" + MAppUserRole.Field.APP_USER_ID, userObject.get(MAppUser.Field.ID));
        this.permissionValue = named.query(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        selectQuery = new SelectQuery(MStaff.NAME);
        selectQuery.addField(MStaff.Field.ID);
        selectQuery.addField(MStaff.Field.DISPLAY_NAME + " as text");
        selectQuery.addWhere(MStaff.Field.ID + " = :" + MStaff.Field.ID, userObject.get(MAppUser.Field.STAFF_ID));
        this.staffValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        this.loginValue = (String) userObject.get(MAppUser.Field.USERNAME);
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", UserBrowsePage.class);
        this.form.add(this.closeLink);

        this.passwordForm = new Form<>("passwordForm");
        add(this.passwordForm);

        this.updateButton = new Button("updateButton");
        this.updateButton.setOnSubmit(this::updateButtonSubmit);
        this.passwordForm.add(this.updateButton);

        this.passwordFormCloseLink = new BookmarkablePageLink<>("passwordFormCloseLink", UserBrowsePage.class);
        this.passwordForm.add(this.passwordFormCloseLink);

        initFirstNameBlock();

        initEmailBlock();

        initLastNameBlock();

        initOfficeBlock();

        initPermissionBlock();

        initStaffBlock();

        initLoginBlock();

        initOverridePasswordExpiryPolicyBlock();

        initPasswordBlock();

        initRepeatPasswordBlock();

        this.passwordForm.add(new EqualInputValidator(passwordField, repeatPasswordField));
    }

    protected void initPasswordBlock() {
        this.passwordBlock = new WebMarkupBlock("passwordBlock", Size.Twelve_12);
        this.passwordForm.add(this.passwordBlock);
        this.passwordIContainer = new WebMarkupContainer("passwordIContainer");
        this.passwordBlock.add(this.passwordIContainer);
        this.passwordField = new PasswordTextField("passwordField", new PropertyModel<>(this, "passwordValue"));
        this.passwordField.setRequired(false);
        this.passwordField.setResetPassword(false);
        this.passwordField.add(new OnChangeAjaxBehavior());
        this.passwordIContainer.add(this.passwordField);
        this.passwordFeedback = new TextFeedbackPanel("passwordFeedback", this.passwordField);
        this.passwordIContainer.add(this.passwordFeedback);
    }

    protected void initRepeatPasswordBlock() {
        this.repeatPasswordBlock = new WebMarkupBlock("repeatPasswordBlock", Size.Twelve_12);
        this.passwordForm.add(this.repeatPasswordBlock);
        this.repeatPasswordIContainer = new WebMarkupContainer("repeatPasswordIContainer");
        this.repeatPasswordBlock.add(this.repeatPasswordIContainer);
        this.repeatPasswordField = new PasswordTextField("repeatPasswordField", new PropertyModel<>(this, "repeatPasswordValue"));
        this.repeatPasswordField.setRequired(false);
        this.repeatPasswordField.setResetPassword(false);
        this.repeatPasswordField.add(new OnChangeAjaxBehavior());
        this.repeatPasswordIContainer.add(this.repeatPasswordField);
        this.repeatPasswordFeedback = new TextFeedbackPanel("repeatPasswordFeedback", this.repeatPasswordField);
        this.repeatPasswordIContainer.add(this.repeatPasswordFeedback);
    }

    protected void initEmailBlock() {
        this.emailBlock = new WebMarkupBlock("emailBlock", Size.Twelve_12);
        this.form.add(this.emailBlock);
        this.emailVContainer = new WebMarkupContainer("emailVContainer");
        this.emailBlock.add(this.emailVContainer);
        this.emailView = new ReadOnlyView("emailView", new PropertyModel<>(this, "emailValue"));
        this.emailVContainer.add(this.emailView);
    }

    protected void initLastNameBlock() {
        this.lastNameBlock = new WebMarkupBlock("lastNameBlock", Size.Six_6);
        this.form.add(this.lastNameBlock);
        this.lastNameVContainer = new WebMarkupContainer("lastNameVContainer");
        this.lastNameBlock.add(this.lastNameVContainer);
        this.lastNameView = new ReadOnlyView("lastNameView", new PropertyModel<>(this, "lastNameValue"));
        this.lastNameVContainer.add(this.lastNameView);
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Six_6);
        this.form.add(this.officeBlock);
        this.officeIContainer = new WebMarkupContainer("officeIContainer");
        this.officeBlock.add(this.officeIContainer);
        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.add(new OnChangeAjaxBehavior(this::officeFieldUpdate));
        this.officeField.setRequired(true);
        this.officeIContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeIContainer.add(this.officeFeedback);
    }

    protected void initPermissionBlock() {
        this.permissionBlock = new WebMarkupBlock("permissionBlock", Size.Twelve_12);
        this.form.add(this.permissionBlock);
        this.permissionIContainer = new WebMarkupContainer("permissionIContainer");
        this.permissionBlock.add(this.permissionIContainer);
        this.permissionProvider = new MultipleChoiceProvider(MRole.NAME, MRole.Field.ID, MRole.Field.NAME);
        this.permissionField = new Select2MultipleChoice<>("permissionField", new PropertyModel<>(this, "permissionValue"), this.permissionProvider);
        this.permissionField.add(new OnChangeAjaxBehavior());
        this.permissionField.setRequired(true);
        this.permissionIContainer.add(this.permissionField);
        this.permissionFeedback = new TextFeedbackPanel("permissionFeedback", this.permissionField);
        this.permissionIContainer.add(this.permissionFeedback);
    }

    protected void initStaffBlock() {
        this.staffBlock = new WebMarkupBlock("staffBlock", Size.Six_6);
        this.form.add(this.staffBlock);
        this.staffIContainer = new WebMarkupContainer("staffIContainer");
        this.staffBlock.add(this.staffIContainer);
        this.staffProvider = new SingleChoiceProvider(MStaff.NAME, MStaff.Field.ID, MStaff.Field.DISPLAY_NAME);
        this.staffField = new Select2SingleChoice<>("staffField", new PropertyModel<>(this, "staffValue"), this.staffProvider);
        this.staffField.add(new OnChangeAjaxBehavior());
        this.staffIContainer.add(this.staffField);
        this.staffFeedback = new TextFeedbackPanel("staffFeedback", this.staffField);
        this.staffIContainer.add(this.staffFeedback);
    }

    protected void initLoginBlock() {
        this.loginBlock = new WebMarkupBlock("loginBlock", Size.Twelve_12);
        this.form.add(this.loginBlock);
        this.loginVContainer = new WebMarkupContainer("loginVContainer");
        this.loginBlock.add(this.loginVContainer);
        this.loginView = new ReadOnlyView("loginView", new PropertyModel<>(this, "loginValue"));
        this.loginVContainer.add(this.loginView);
    }

    protected void initOverridePasswordExpiryPolicyBlock() {
        this.overridePasswordExpiryPolicyBlock = new WebMarkupBlock("overridePasswordExpiryPolicyBlock", Size.Twelve_12);
        this.form.add(this.overridePasswordExpiryPolicyBlock);
        this.overridePasswordExpiryPolicyIContainer = new WebMarkupContainer("overridePasswordExpiryPolicyIContainer");
        this.overridePasswordExpiryPolicyBlock.add(this.overridePasswordExpiryPolicyIContainer);
        this.overridePasswordExpiryPolicyField = new CheckBox("overridePasswordExpiryPolicyField", new PropertyModel<>(this, "overridePasswordExpiryPolicyValue"));
        this.overridePasswordExpiryPolicyField.setRequired(true);
        this.overridePasswordExpiryPolicyField.add(new OnChangeAjaxBehavior());
        this.overridePasswordExpiryPolicyIContainer.add(this.overridePasswordExpiryPolicyField);
        this.overridePasswordExpiryPolicyFeedback = new TextFeedbackPanel("overridePasswordExpiryPolicyFeedback", this.overridePasswordExpiryPolicyField);
        this.overridePasswordExpiryPolicyIContainer.add(this.overridePasswordExpiryPolicyFeedback);
    }

    protected void initFirstNameBlock() {
        this.firstNameBlock = new WebMarkupBlock("firstNameBlock", Size.Six_6);
        this.form.add(this.firstNameBlock);
        this.firstNameVContainer = new WebMarkupContainer("firstNameVContainer");
        this.firstNameBlock.add(this.firstNameVContainer);
        this.firstNameView = new ReadOnlyView("firstNameView", new PropertyModel<>(this, "firstNameValue"));
        this.firstNameVContainer.add(this.firstNameView);

    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
        this.staffProvider.applyWhere("office", MStaff.Field.OFFICE_ID + " = " + this.officeValue.getId());
    }

    protected boolean officeFieldUpdate(AjaxRequestTarget target) {
        this.staffValue = null;
        this.staffProvider.setDisabled(false);
        this.staffProvider.applyWhere("office", MStaff.Field.OFFICE_ID + " = " + this.officeValue.getId());
        target.add(this.form);
        return false;
    }

    protected void updateButtonSubmit(Button button) {
        AppUserBuilder builder = new AppUserBuilder();
        builder.withId(this.userId);
        builder.withPassword(this.passwordValue);
        builder.withRepeatPassword(this.repeatPasswordValue);

        JsonNode node = AppUserHelper.update((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(UserBrowsePage.class);
    }

    protected void saveButtonSubmit(Button button) {
        AppUserBuilder builder = new AppUserBuilder();
        builder.withId(this.userId);
        builder.withPasswordNeverExpires(this.overridePasswordExpiryPolicyValue);
        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        } else {
            builder.withOfficeId(null);
        }
        if (this.permissionValue != null) {
            for (Option role : this.permissionValue) {
                builder.withRole(role.getId());
            }
        } else {
            builder.withRole(null);
        }
        if (this.staffValue != null) {
            builder.withStaffId(this.staffValue.getId());
        } else {
            builder.withStaffId(null);
        }

        JsonNode node = AppUserHelper.update((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(UserBrowsePage.class);
    }

}
