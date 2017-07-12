package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.AccountUsage;
import com.angkorteam.fintech.dto.request.AccountRuleBuilder;
import com.angkorteam.fintech.helper.AccountingRuleHelper;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.*;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 7/3/17.
 */
public class RuleModifyPage extends Page {

    private String ruleId;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private String ruleNameValue;
    private TextField<String> ruleNameField;
    private TextFeedbackPanel ruleNameFeedback;

    private OptionSingleChoiceProvider officeProvider;
    private Option officeValue;
    private Select2SingleChoice<Option> officeField;
    private TextFeedbackPanel officeFeedback;

    private String descriptionValue;
    private TextArea<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private OptionSingleChoiceProvider debitAccountProvider;
    private Option debitAccountValue;
    private Select2SingleChoice<Option> debitAccountField;
    private TextFeedbackPanel debitAccountFeedback;

    private OptionMultipleChoiceProvider debitTagProvider;
    private List<Option> debitTagValue;
    private Select2MultipleChoice<Option> debitTagField;
    private TextFeedbackPanel debitTagFeedback;

    private Boolean multipleDebitValue;
    private CheckBox multipleDebitField;
    private TextFeedbackPanel multipleDebitFeedback;

    private OptionSingleChoiceProvider creditAccountProvider;
    private Option creditAccountValue;
    private Select2SingleChoice<Option> creditAccountField;
    private TextFeedbackPanel creditAccountFeedback;

    private OptionMultipleChoiceProvider creditTagProvider;
    private List<Option> creditTagValue;
    private Select2MultipleChoice<Option> creditTagField;
    private TextFeedbackPanel creditTagFeedback;

    private Boolean multipleCreditValue;
    private CheckBox multipleCreditField;
    private TextFeedbackPanel multipleCreditFeedback;

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

        this.officeValue = jdbcTemplate.queryForObject("select id, name text from m_office where id = ?", new OptionMapper(), ruleObject.get("office_id"));
        this.officeProvider = new OptionSingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", 0, new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setRequired(true);
        this.form.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.form.add(this.officeFeedback);

        this.debitAccountValue = jdbcTemplate.queryForObject("select id, name text from acc_gl_account where id = ?", new OptionMapper(), ruleObject.get("debit_account_id"));
        this.debitAccountProvider = new OptionSingleChoiceProvider("acc_gl_account", "id", "name");
        this.debitAccountProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.debitAccountField = new Select2SingleChoice<>("debitAccountField", 0, new PropertyModel<>(this, "debitAccountValue"), this.debitAccountProvider);
        this.form.add(this.debitAccountField);
        this.debitAccountFeedback = new TextFeedbackPanel("debitAccountFeedback", this.debitAccountField);
        this.form.add(this.debitAccountFeedback);

        this.debitTagValue = jdbcTemplate.query("SELECT m_code_value.id, m_code_value.code_value text FROM acc_rule_tags INNER JOIN m_code_value ON acc_rule_tags.tag_id = m_code_value.id WHERE acc_type_enum = " + RuleBrowsePage.DEBIT + " and acc_rule_tags.acc_rule_id = ?", new OptionMapper(), ruleObject.get("id"));
        this.debitTagProvider = new OptionMultipleChoiceProvider("m_code_value", "id", "code_value");
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

        this.creditAccountValue = jdbcTemplate.queryForObject("select id, name text from acc_gl_account where id = ?", new OptionMapper(), ruleObject.get("credit_account_id"));
        this.creditAccountProvider = new OptionSingleChoiceProvider("acc_gl_account", "id", "name");
        this.creditAccountProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.creditAccountField = new Select2SingleChoice<>("creditAccountField", 0, new PropertyModel<>(this, "creditAccountValue"), this.creditAccountProvider);
        this.form.add(this.creditAccountField);
        this.creditAccountFeedback = new TextFeedbackPanel("creditAccountFeedback", this.creditAccountField);
        this.form.add(this.creditAccountFeedback);

        this.creditTagValue = jdbcTemplate.query("SELECT m_code_value.id, m_code_value.code_value text FROM acc_rule_tags INNER JOIN m_code_value ON acc_rule_tags.tag_id = m_code_value.id WHERE acc_type_enum = " + RuleBrowsePage.CREDIT + " and acc_rule_tags.acc_rule_id = ?", new OptionMapper(), ruleObject.get("id"));
        this.creditTagProvider = new OptionMultipleChoiceProvider("m_code_value", "id", "code_value");
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

    private void saveButtonSubmit(Button button) {
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
            node = AccountingRuleHelper.updateRule(builder.build());
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
