package com.angkorteam.fintech.pages.account;

import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.AccountRuleBuilder;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.helper.AccountingRuleHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.pages.role.RoleBrowsePage;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 7/3/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RuleModifyPage extends Page {

    protected String ruleId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected String ruleNameValue;
    protected TextField<String> ruleNameField;
    protected TextFeedbackPanel ruleNameFeedback;

    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected String descriptionValue;
    protected TextArea<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;

    protected SingleChoiceProvider debitAccountProvider;
    protected Option debitAccountValue;
    protected Select2SingleChoice<Option> debitAccountField;
    protected TextFeedbackPanel debitAccountFeedback;

    protected MultipleChoiceProvider debitTagProvider;
    protected List<Option> debitTagValue;
    protected Select2MultipleChoice<Option> debitTagField;
    protected TextFeedbackPanel debitTagFeedback;

    protected Boolean multipleDebitValue;
    protected CheckBox multipleDebitField;
    protected TextFeedbackPanel multipleDebitFeedback;

    protected SingleChoiceProvider creditAccountProvider;
    protected Option creditAccountValue;
    protected Select2SingleChoice<Option> creditAccountField;
    protected TextFeedbackPanel creditAccountFeedback;

    protected MultipleChoiceProvider creditTagProvider;
    protected List<Option> creditTagValue;
    protected Select2MultipleChoice<Option> creditTagField;
    protected TextFeedbackPanel creditTagFeedback;

    protected Boolean multipleCreditValue;
    protected CheckBox multipleCreditField;
    protected TextFeedbackPanel multipleCreditFeedback;

    protected static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting");
            breadcrumb.setPage(AccountingPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting Rule");
            breadcrumb.setPage(RoleBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting Rule Modify");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();
        this.ruleId = parameters.get("ruleId").toString("");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> ruleObject = jdbcTemplate.queryForMap("select * from acc_accounting_rule where id = ?", this.ruleId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", RuleBrowsePage.class);
        this.form.add(this.closeLink);

        this.ruleNameValue = (String) ruleObject.get("name");
        this.ruleNameField = new TextField<>("ruleNameField", new PropertyModel<>(this, "ruleNameValue"));
        this.ruleNameField.setRequired(true);
        this.form.add(this.ruleNameField);
        this.ruleNameFeedback = new TextFeedbackPanel("ruleNameFeedback", this.ruleNameField);
        this.form.add(this.ruleNameFeedback);

        this.descriptionValue = (String) ruleObject.get("description");
        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(true);
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);

        this.officeValue = jdbcTemplate.queryForObject("select id, name text from m_office where id = ?", Option.MAPPER, ruleObject.get("office_id"));
        this.officeProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", 0, new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setRequired(true);
        this.form.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.form.add(this.officeFeedback);

        this.debitAccountValue = jdbcTemplate.queryForObject("select id, name text from acc_gl_account where id = ?", Option.MAPPER, ruleObject.get("debit_account_id"));
        this.debitAccountProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.debitAccountProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.debitAccountField = new Select2SingleChoice<>("debitAccountField", 0, new PropertyModel<>(this, "debitAccountValue"), this.debitAccountProvider);
        this.form.add(this.debitAccountField);
        this.debitAccountFeedback = new TextFeedbackPanel("debitAccountFeedback", this.debitAccountField);
        this.form.add(this.debitAccountFeedback);

        this.debitTagValue = jdbcTemplate.query("SELECT m_code_value.id, m_code_value.code_value text FROM acc_rule_tags INNER JOIN m_code_value ON acc_rule_tags.tag_id = m_code_value.id WHERE acc_type_enum = " + RuleBrowsePage.DEBIT + " and acc_rule_tags.acc_rule_id = ?", Option.MAPPER, ruleObject.get("id"));
        this.debitTagProvider = new MultipleChoiceProvider("m_code_value", "id", "code_value");
        this.debitTagProvider.applyWhere("code_id", "code_id in (7,8,9,10,11)");
        this.debitTagField = new Select2MultipleChoice<>("debitTagField", 0, new PropertyModel<>(this, "debitTagValue"), this.debitTagProvider);
        this.form.add(this.debitTagField);
        this.debitTagFeedback = new TextFeedbackPanel("debitTagFeedback", this.debitTagField);
        this.form.add(this.debitTagFeedback);

        this.multipleDebitValue = (Boolean) ruleObject.get("allow_multiple_debits");
        this.multipleDebitField = new CheckBox("multipleDebitField", new PropertyModel<>(this, "multipleDebitValue"));
        this.multipleDebitField.setRequired(true);
        this.form.add(this.multipleDebitField);
        this.multipleDebitFeedback = new TextFeedbackPanel("multipleDebitFeedback", this.multipleDebitField);
        this.form.add(this.multipleDebitFeedback);

        this.creditAccountValue = jdbcTemplate.queryForObject("select id, name text from acc_gl_account where id = ?", Option.MAPPER, ruleObject.get("credit_account_id"));
        this.creditAccountProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.creditAccountProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.creditAccountField = new Select2SingleChoice<>("creditAccountField", 0, new PropertyModel<>(this, "creditAccountValue"), this.creditAccountProvider);
        this.form.add(this.creditAccountField);
        this.creditAccountFeedback = new TextFeedbackPanel("creditAccountFeedback", this.creditAccountField);
        this.form.add(this.creditAccountFeedback);

        this.creditTagValue = jdbcTemplate.query("SELECT m_code_value.id, m_code_value.code_value text FROM acc_rule_tags INNER JOIN m_code_value ON acc_rule_tags.tag_id = m_code_value.id WHERE acc_type_enum = " + RuleBrowsePage.CREDIT + " and acc_rule_tags.acc_rule_id = ?", Option.MAPPER, ruleObject.get("id"));
        this.creditTagProvider = new MultipleChoiceProvider("m_code_value", "id", "code_value");
        this.creditTagProvider.applyWhere("code_id", "code_id in (7,8,9,10,11)");
        this.creditTagField = new Select2MultipleChoice<>("creditTagField", 0, new PropertyModel<>(this, "creditTagValue"), this.creditTagProvider);
        this.form.add(this.creditTagField);
        this.creditTagFeedback = new TextFeedbackPanel("creditTagFeedback", this.creditTagField);
        this.form.add(this.creditTagFeedback);

        this.multipleCreditValue = (Boolean) ruleObject.get("allow_multiple_credits");
        this.multipleCreditField = new CheckBox("multipleCreditField", new PropertyModel<>(this, "multipleCreditValue"));
        this.multipleCreditField.setRequired(true);
        this.form.add(this.multipleCreditField);
        this.multipleCreditFeedback = new TextFeedbackPanel("multipleCreditFeedback", this.multipleCreditField);
        this.form.add(this.multipleCreditFeedback);

    }

    protected void saveButtonSubmit(Button button) {
        AccountRuleBuilder builder = new AccountRuleBuilder();
        builder.withId(this.ruleId);
        builder.withName(this.ruleNameValue);
        builder.withDescription(this.descriptionValue);
        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }
        if (this.debitAccountValue != null) {
            builder.withAccountToDebit(this.debitAccountValue.getId());
        }
        if (this.debitTagValue != null && !this.debitTagValue.isEmpty()) {
            for (Option tag : this.debitTagValue) {
                builder.withDebitTags(tag.getId());
            }
            builder.withAllowMultipleDebitEntries(this.multipleDebitValue);
        }
        if (this.creditAccountValue != null) {
            builder.withAccountToCredit(this.creditAccountValue.getId());
        }
        if (this.creditTagValue != null && !this.creditTagValue.isEmpty()) {
            for (Option tag : this.creditTagValue) {
                builder.withCreditTags(tag.getId());
            }
            builder.withAllowMultipleCreditEntries(this.multipleCreditValue);
        }

        JsonNode node = null;
        try {
            node = AccountingRuleHelper.update((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(RuleBrowsePage.class);

    }

}
