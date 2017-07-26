package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.AccountType;
import com.angkorteam.fintech.dto.AccountUsage;
import com.angkorteam.fintech.dto.FinancialActivityType;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.FinancialActivityBuilder;
import com.angkorteam.fintech.helper.FinancialActivityHelper;
import com.angkorteam.fintech.provider.FinancialActivityProvider;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionMapper;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Map;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FinancialActivityModifyPage extends Page {

    private String financialActivityId;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private FinancialActivityProvider financialActivityProvider;
    private Option financialActivityValue;
    private Select2SingleChoice<Option> financialActivityField;
    private TextFeedbackPanel financialActivityFeedback;

    private SingleChoiceProvider accountProvider;
    private Option accountValue;
    private Select2SingleChoice<Option> accountField;
    private TextFeedbackPanel accountFeedback;

    @Override
    protected void onInitialize() {
	super.onInitialize();

	PageParameters parameters = getPageParameters();
	this.financialActivityId = parameters.get("financialActivityId").toString("");

	JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
	Map<String, Object> financialActivityObject = jdbcTemplate
		.queryForMap("select * from acc_gl_financial_activity_account where id = ?", financialActivityId);

	this.form = new Form<>("form");
	add(this.form);

	this.saveButton = new Button("saveButton");
	this.saveButton.setOnSubmit(this::saveButtonSubmit);
	this.form.add(this.saveButton);

	this.closeLink = new BookmarkablePageLink<>("closeLink", FinancialActivityBrowsePage.class);
	this.form.add(this.closeLink);

	this.financialActivityProvider = new FinancialActivityProvider();
	this.financialActivityField = new Select2SingleChoice<>("financialActivityField", 0,
		new PropertyModel<>(this, "financialActivityValue"), this.financialActivityProvider);
	this.form.add(this.financialActivityField);
	this.financialActivityFeedback = new TextFeedbackPanel("financialActivityFeedback",
		this.financialActivityField);
	this.form.add(this.financialActivityFeedback);
	if (financialActivityObject.get("financial_activity_type") != null) {
	    String type = String.valueOf(financialActivityObject.get("financial_activity_type"));
	    for (FinancialActivityType p : FinancialActivityType.values()) {
		if (p.getLiteral().equals(type)) {
		    this.financialActivityValue = new Option(p.name(), p.getDescription());
		    break;
		}
	    }
	}
	this.financialActivityField
		.add(new OnChangeAjaxBehavior(this::financialActivityFieldUpdate, this::financialActivityFieldError));

	if (financialActivityObject.get("gl_account_id") != null) {
	    this.accountValue = jdbcTemplate.queryForObject("select id, name text from acc_gl_account where id = ?",
		    new OptionMapper(), financialActivityObject.get("gl_account_id"));
	}
	this.accountProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
	this.accountProvider.applyWhere("usage", AccountUsage.Detail.getLiteral());
	this.accountProvider.setDisabled(true);
	this.accountField = new Select2SingleChoice<>("accountField", 0, new PropertyModel<>(this, "accountValue"),
		this.accountProvider);
	this.form.add(this.accountField);
	this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
	this.form.add(this.accountFeedback);

	if (this.financialActivityValue == null) {
	    this.accountValue = null;
	    this.accountProvider.setDisabled(true);
	} else {
	    AccountType classification_enum = null;
	    for (FinancialActivityType a : FinancialActivityType.values()) {
		if (this.financialActivityValue.getId().equals(a.name())) {
		    classification_enum = a.getAccountType();
		    break;
		}
	    }
	    this.accountProvider.setDisabled(false);
	    this.accountProvider.applyWhere("classification_enum",
		    "classification_enum = " + classification_enum.getLiteral());
	}
    }

    private void financialActivityFieldUpdate(AjaxRequestTarget target) {
	if (this.financialActivityValue == null) {
	    this.accountValue = null;
	    this.accountProvider.setDisabled(true);
	} else {
	    AccountType classification_enum = null;
	    for (FinancialActivityType a : FinancialActivityType.values()) {
		if (this.financialActivityValue.getId().equals(a.name())) {
		    classification_enum = a.getAccountType();
		    break;
		}
	    }
	    this.accountValue = null;
	    this.accountProvider.setDisabled(false);
	    this.accountProvider.applyWhere("classification_enum",
		    "classification_enum = " + classification_enum.getLiteral());
	}
	target.add(this.form);
    }

    private void financialActivityFieldError(AjaxRequestTarget target, RuntimeException e) {
	if (e != null) {
	    throw e;
	}
	target.add(this.form);
	target.appendJavaScript(Select2SingleChoice.REMOVE_POPUP_UP_SCRIPT);
    }

    private void saveButtonSubmit(Button button) {
	FinancialActivityBuilder builder = new FinancialActivityBuilder();
	builder.withId(this.financialActivityId);
	if (this.financialActivityValue != null) {
	    builder.withFinancialActivity(
		    FinancialActivityType.valueOf(this.financialActivityValue.getId()).getLiteral());
	}
	if (this.accountValue != null) {
	    builder.withGlAccountId(this.accountValue.getId());
	}
	JsonNode node = null;
	try {
	    node = FinancialActivityHelper.update((Session) getSession(), builder.build());
	} catch (UnirestException e) {
	    error(e.getMessage());
	    return;
	}
	if (reportError(node)) {
	    return;
	}
	setResponsePage(FinancialActivityBrowsePage.class);
    }

}
