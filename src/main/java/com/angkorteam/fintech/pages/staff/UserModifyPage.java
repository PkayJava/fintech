package com.angkorteam.fintech.pages.staff;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualInputValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.request.AppUserBuilder;
import com.angkorteam.fintech.helper.AppUserHelper;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionMapper;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionMultipleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class UserModifyPage extends Page {

    private String userId;

    private String firstNameValue;
    private Label firstNameField;

    private String emailValue;
    private Label emailField;

    private String lastNameValue;
    private Label lastNameField;

    private String loginValue;
    private Label loginField;

    private OptionSingleChoiceProvider officeProvider;
    private Option officeValue;
    private Select2SingleChoice<Option> officeField;
    private TextFeedbackPanel officeFeedback;

    private OptionSingleChoiceProvider staffProvider;
    private Option staffValue;
    private Select2SingleChoice<Option> staffField;
    private TextFeedbackPanel staffFeedback;

    private OptionMultipleChoiceProvider permissionProvider;
    private List<Option> permissionValue;
    private Select2MultipleChoice<Option> permissionField;
    private TextFeedbackPanel permissionFeedback;

    private Boolean overridePasswordExpiryPolicyValue;
    private CheckBox overridePasswordExpiryPolicyField;
    private TextFeedbackPanel overridePasswordExpiryPolicyFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private String passwordValue;
    private String repeatPasswordValue;

    @Override
    protected void onInitialize() {
	super.onInitialize();

	PageParameters parameters = getPageParameters();
	this.userId = parameters.get("userId").toString("");

	JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
	Map<String, Object> userObject = jdbcTemplate.queryForMap("", this.userId);

	initForm(userObject);

	initPasswordForm();
    }

    protected void initPasswordForm() {
	Form<Void> passwordForm = new Form<>("passwordForm");
	add(passwordForm);

	Button updateButton = new Button("updateButton");
	updateButton.setOnSubmit(this::updateButtonSubmit);
	passwordForm.add(updateButton);

	BookmarkablePageLink<Void> closeLink = new BookmarkablePageLink<>("closeLink", UserBrowsePage.class);
	passwordForm.add(closeLink);

	PasswordTextField passwordField = new PasswordTextField("passwordField",
		new PropertyModel<>(this, "passwordValue"));
	passwordField.setRequired(false);
	passwordField.setResetPassword(false);
	passwordField.add(new OnChangeAjaxBehavior());
	passwordForm.add(passwordField);
	TextFeedbackPanel passwordFeedback = new TextFeedbackPanel("passwordFeedback", passwordField);
	passwordForm.add(passwordFeedback);

	PasswordTextField repeatPasswordField = new PasswordTextField("repeatPasswordField",
		new PropertyModel<>(this, "repeatPasswordValue"));
	repeatPasswordField.setRequired(false);
	repeatPasswordField.setResetPassword(false);
	repeatPasswordField.add(new OnChangeAjaxBehavior());
	passwordForm.add(repeatPasswordField);
	TextFeedbackPanel repeatPasswordFeedback = new TextFeedbackPanel("repeatPasswordFeedback", repeatPasswordField);
	passwordForm.add(repeatPasswordFeedback);

	passwordForm.add(new EqualInputValidator(passwordField, repeatPasswordField));
    }

    protected void initForm(Map<String, Object> userObject) {
	JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

	this.form = new Form<>("form");
	add(this.form);

	this.saveButton = new Button("saveButton");
	this.saveButton.setOnSubmit(this::saveButtonSubmit);
	this.form.add(this.saveButton);

	this.closeLink = new BookmarkablePageLink<>("closeLink", StaffBrowsePage.class);
	this.form.add(this.closeLink);

	this.firstNameValue = (String) userObject.get("firstname");
	this.firstNameField = new Label("firstNameField", new PropertyModel<>(this, "firstNameValue"));
	this.form.add(this.firstNameField);

	this.emailValue = (String) userObject.get("email");
	this.emailField = new Label("emailField", new PropertyModel<>(this, "emailValue"));
	this.form.add(this.emailField);

	this.lastNameValue = (String) userObject.get("lastname");
	this.lastNameField = new Label("lastNameField", new PropertyModel<>(this, "lastNameValue"));
	this.form.add(this.lastNameField);

	this.officeValue = jdbcTemplate.queryForObject("select id, name text from m_office where id = ?",
		new OptionMapper(), userObject.get("office_id"));
	this.officeProvider = new OptionSingleChoiceProvider("m_office", "id", "name");
	this.officeField = new Select2SingleChoice<>("officeField", 0, new PropertyModel<>(this, "officeValue"),
		this.officeProvider);
	this.officeField.add(new OnChangeAjaxBehavior(this::officeFieldUpdate));
	this.officeField.setRequired(true);
	this.form.add(this.officeField);
	this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
	this.form.add(this.officeFeedback);

	this.permissionValue = jdbcTemplate.query(
		"select m_role.id, m_role.name text from m_appuser_role inner join m_role on m_appuser_role.role_id = m_role.id where m_appuser_role.id = ?",
		new OptionMapper(), userObject.get("id"));
	this.permissionProvider = new OptionMultipleChoiceProvider("m_role", "id", "name");
	this.permissionField = new Select2MultipleChoice<>("permissionField", 0,
		new PropertyModel<>(this, "permissionValue"), this.permissionProvider);
	this.permissionField.add(new OnChangeAjaxBehavior());
	this.permissionField.setRequired(true);
	this.form.add(this.permissionField);
	this.permissionFeedback = new TextFeedbackPanel("permissionFeedback", this.permissionField);
	this.form.add(this.permissionFeedback);

	this.officeValue = jdbcTemplate.queryForObject("select id, display_name text from m_staff where id = ?",
		new OptionMapper(), userObject.get("staff_id"));
	this.staffProvider = new OptionSingleChoiceProvider("m_staff", "id", "display_name");
	this.staffField = new Select2SingleChoice<>("staffField", 0, new PropertyModel<>(this, "staffValue"),
		this.staffProvider);
	this.staffField.add(new OnChangeAjaxBehavior());
	this.form.add(this.staffField);
	this.staffFeedback = new TextFeedbackPanel("staffFeedback", this.staffField);
	this.form.add(this.staffFeedback);

	this.loginValue = (String) userObject.get("username");
	this.loginField = new Label("loginField", new PropertyModel<>(this, "loginValue"));
	this.form.add(this.loginField);

	this.overridePasswordExpiryPolicyField = new CheckBox("overridePasswordExpiryPolicyField",
		new PropertyModel<>(this, "overridePasswordExpiryPolicyValue"));
	this.overridePasswordExpiryPolicyField.setRequired(true);
	this.overridePasswordExpiryPolicyField.add(new OnChangeAjaxBehavior());
	this.form.add(this.overridePasswordExpiryPolicyField);
	this.overridePasswordExpiryPolicyFeedback = new TextFeedbackPanel("overridePasswordExpiryPolicyFeedback",
		this.overridePasswordExpiryPolicyField);
	this.form.add(this.overridePasswordExpiryPolicyFeedback);

	this.staffProvider.applyWhere("office", "office_id = " + this.officeValue.getId());
    }

    private void officeFieldUpdate(AjaxRequestTarget target) {
	this.staffValue = null;
	this.staffProvider.setDisabled(false);
	this.staffProvider.applyWhere("office", "office_id = " + this.officeValue.getId());
	target.add(this.form);
    }

    private void updateButtonSubmit(Button button) {
	AppUserBuilder builder = new AppUserBuilder();
	builder.withId(this.userId);
	builder.withPassword(this.passwordValue);
	builder.withRepeatPassword(this.repeatPasswordValue);

	JsonNode node = null;
	try {
	    node = AppUserHelper.update(builder.build());
	} catch (UnirestException e) {
	    error(e.getMessage());
	    return;
	}
	if (reportError(node)) {
	    return;
	}
	setResponsePage(UserBrowsePage.class);
    }

    private void saveButtonSubmit(Button button) {
	AppUserBuilder builder = new AppUserBuilder();
	builder.withId(this.userId);
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
	    node = AppUserHelper.update(builder.build());
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
