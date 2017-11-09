package com.angkorteam.fintech.pages.account;

import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.DeprecatedPage;
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
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
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
public class RuleModifyPage extends DeprecatedPage {

    protected String ruleId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock ruleNameBlock;
    protected WebMarkupContainer ruleNameIContainer;
    protected String ruleNameValue;
    protected TextField<String> ruleNameField;
    protected TextFeedbackPanel ruleNameFeedback;

    protected WebMarkupBlock officeBlock;
    protected WebMarkupContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;
    protected TextFeedbackPanel officeFeedback;

    protected WebMarkupBlock descriptionBlock;
    protected WebMarkupContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextArea<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;

    protected WebMarkupBlock debitAccountBlock;
    protected WebMarkupContainer debitAccountIContainer;
    protected SingleChoiceProvider debitAccountProvider;
    protected Option debitAccountValue;
    protected Select2SingleChoice<Option> debitAccountField;
    protected TextFeedbackPanel debitAccountFeedback;

    protected WebMarkupBlock debitTagBlock;
    protected WebMarkupContainer debitTagIContainer;
    protected MultipleChoiceProvider debitTagProvider;
    protected List<Option> debitTagValue;
    protected Select2MultipleChoice<Option> debitTagField;
    protected TextFeedbackPanel debitTagFeedback;

    protected WebMarkupBlock multipleDebitBlock;
    protected WebMarkupContainer multipleDebitIContainer;
    protected Boolean multipleDebitValue;
    protected CheckBox multipleDebitField;
    protected TextFeedbackPanel multipleDebitFeedback;

    protected WebMarkupBlock creditAccountBlock;
    protected WebMarkupContainer creditAccountIContainer;
    protected SingleChoiceProvider creditAccountProvider;
    protected Option creditAccountValue;
    protected Select2SingleChoice<Option> creditAccountField;
    protected TextFeedbackPanel creditAccountFeedback;

    protected WebMarkupBlock creditTagBlock;
    protected WebMarkupContainer creditTagIContainer;
    protected MultipleChoiceProvider creditTagProvider;
    protected List<Option> creditTagValue;
    protected Select2MultipleChoice<Option> creditTagField;
    protected TextFeedbackPanel creditTagFeedback;

    protected WebMarkupBlock multipleCreditBlock;
    protected WebMarkupContainer multipleCreditIContainer;
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
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.ruleId = parameters.get("ruleId").toString("");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        Map<String, Object> ruleObject = jdbcTemplate.queryForMap("select * from acc_accounting_rule where id = ?", this.ruleId);

        this.ruleNameValue = (String) ruleObject.get("name");
        this.descriptionValue = (String) ruleObject.get("description");
        this.officeValue = jdbcTemplate.queryForObject("select id, name text from m_office where id = ?", Option.MAPPER, ruleObject.get("office_id"));
        this.debitAccountValue = jdbcTemplate.queryForObject("select id, name text from acc_gl_account where id = ?", Option.MAPPER, ruleObject.get("debit_account_id"));
        this.debitTagValue = jdbcTemplate.query("SELECT m_code_value.id, m_code_value.code_value text FROM acc_rule_tags INNER JOIN m_code_value ON acc_rule_tags.tag_id = m_code_value.id WHERE acc_type_enum = " + RuleBrowsePage.DEBIT + " and acc_rule_tags.acc_rule_id = ?", Option.MAPPER, ruleObject.get("id"));
        this.multipleDebitValue = (Boolean) ruleObject.get("allow_multiple_debits");
        this.creditAccountValue = jdbcTemplate.queryForObject("select id, name text from acc_gl_account where id = ?", Option.MAPPER, ruleObject.get("credit_account_id"));
        this.creditTagValue = jdbcTemplate.query("SELECT m_code_value.id, m_code_value.code_value text FROM acc_rule_tags INNER JOIN m_code_value ON acc_rule_tags.tag_id = m_code_value.id WHERE acc_type_enum = " + RuleBrowsePage.CREDIT + " and acc_rule_tags.acc_rule_id = ?", Option.MAPPER, ruleObject.get("id"));
        this.multipleCreditValue = (Boolean) ruleObject.get("allow_multiple_credits");
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", RuleBrowsePage.class);
        this.form.add(this.closeLink);

        initRuleNameBlock();

        initOfficeBlock();

        initDescriptionBlock();

        initDebitAccountBlock();

        initDebitTagBlock();

        initMultipleDebitBlock();

        initCreditAccountBlock();

        initCreditTagBlock();

        initMultipleCreditBlock();

    }

    protected void initMultipleCreditBlock() {
        this.multipleCreditBlock = new WebMarkupBlock("multipleCreditBlock", Size.Four_4);
        this.form.add(this.multipleCreditBlock);
        this.multipleCreditIContainer = new WebMarkupContainer("multipleCreditIContainer");
        this.multipleCreditBlock.add(this.multipleCreditIContainer);
        this.multipleCreditField = new CheckBox("multipleCreditField", new PropertyModel<>(this, "multipleCreditValue"));
        this.multipleCreditField.setRequired(true);
        this.multipleCreditIContainer.add(this.multipleCreditField);
        this.multipleCreditFeedback = new TextFeedbackPanel("multipleCreditFeedback", this.multipleCreditField);
        this.multipleCreditIContainer.add(this.multipleCreditFeedback);
    }

    protected void initCreditTagBlock() {
        this.creditTagBlock = new WebMarkupBlock("creditTagBlock", Size.Four_4);
        this.form.add(this.creditTagBlock);
        this.creditTagIContainer = new WebMarkupContainer("creditTagIContainer");
        this.creditTagBlock.add(this.creditTagIContainer);
        this.creditTagProvider = new MultipleChoiceProvider("m_code_value", "id", "code_value");
        this.creditTagProvider.applyWhere("code_id", "code_id in (7,8,9,10,11)");
        this.creditTagField = new Select2MultipleChoice<>("creditTagField", 0, new PropertyModel<>(this, "creditTagValue"), this.creditTagProvider);
        this.creditTagIContainer.add(this.creditTagField);
        this.creditTagFeedback = new TextFeedbackPanel("creditTagFeedback", this.creditTagField);
        this.creditTagIContainer.add(this.creditTagFeedback);
    }

    protected void initCreditAccountBlock() {
        this.creditAccountBlock = new WebMarkupBlock("creditAccountBlock", Size.Four_4);
        this.form.add(this.creditAccountBlock);
        this.creditAccountIContainer = new WebMarkupContainer("creditAccountIContainer");
        this.creditAccountBlock.add(this.creditAccountIContainer);
        this.creditAccountProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.creditAccountProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.creditAccountField = new Select2SingleChoice<>("creditAccountField", 0, new PropertyModel<>(this, "creditAccountValue"), this.creditAccountProvider);
        this.creditAccountIContainer.add(this.creditAccountField);
        this.creditAccountFeedback = new TextFeedbackPanel("creditAccountFeedback", this.creditAccountField);
        this.creditAccountIContainer.add(this.creditAccountFeedback);
    }

    protected void initMultipleDebitBlock() {
        this.multipleDebitBlock = new WebMarkupBlock("multipleDebitBlock", Size.Four_4);
        this.form.add(this.multipleDebitBlock);
        this.multipleDebitIContainer = new WebMarkupContainer("multipleDebitIContainer");
        this.multipleDebitBlock.add(this.multipleDebitIContainer);
        this.multipleDebitField = new CheckBox("multipleDebitField", new PropertyModel<>(this, "multipleDebitValue"));
        this.multipleDebitField.setRequired(true);
        this.multipleDebitIContainer.add(this.multipleDebitField);
        this.multipleDebitFeedback = new TextFeedbackPanel("multipleDebitFeedback", this.multipleDebitField);
        this.multipleDebitIContainer.add(this.multipleDebitFeedback);
    }

    protected void initDebitTagBlock() {
        this.debitTagBlock = new WebMarkupBlock("debitTagBlock", Size.Four_4);
        this.form.add(this.debitTagBlock);
        this.debitTagIContainer = new WebMarkupContainer("debitTagIContainer");
        this.debitTagBlock.add(this.debitTagIContainer);
        this.debitTagProvider = new MultipleChoiceProvider("m_code_value", "id", "code_value");
        this.debitTagProvider.applyWhere("code_id", "code_id in (7,8,9,10,11)");
        this.debitTagField = new Select2MultipleChoice<>("debitTagField", 0, new PropertyModel<>(this, "debitTagValue"), this.debitTagProvider);
        this.debitTagIContainer.add(this.debitTagField);
        this.debitTagFeedback = new TextFeedbackPanel("debitTagFeedback", this.debitTagField);
        this.debitTagIContainer.add(this.debitTagFeedback);
    }

    protected void initDebitAccountBlock() {
        this.debitAccountBlock = new WebMarkupBlock("debitAccountBlock", Size.Four_4);
        this.form.add(this.debitAccountBlock);
        this.debitAccountIContainer = new WebMarkupContainer("debitAccountIContainer");
        this.debitAccountBlock.add(this.debitAccountIContainer);
        this.debitAccountProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.debitAccountProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Detail.getLiteral());
        this.debitAccountField = new Select2SingleChoice<>("debitAccountField", 0, new PropertyModel<>(this, "debitAccountValue"), this.debitAccountProvider);
        this.debitAccountIContainer.add(this.debitAccountField);
        this.debitAccountFeedback = new TextFeedbackPanel("debitAccountFeedback", this.debitAccountField);
        this.debitAccountIContainer.add(this.debitAccountFeedback);
    }

    protected void initDescriptionBlock() {
        this.descriptionBlock = new WebMarkupBlock("descriptionBlock", Size.Twelve_12);
        this.form.add(this.descriptionBlock);
        this.descriptionIContainer = new WebMarkupContainer("descriptionIContainer");
        this.descriptionBlock.add(this.descriptionIContainer);
        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(true);
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.descriptionIContainer.add(this.descriptionFeedback);
    }

    protected void initOfficeBlock() {
        this.officeBlock = new WebMarkupBlock("officeBlock", Size.Six_6);
        this.form.add(this.officeBlock);
        this.officeIContainer = new WebMarkupContainer("officeIContainer");
        this.officeBlock.add(this.officeIContainer);
        this.officeProvider = new SingleChoiceProvider("m_office", "id", "name");
        this.officeField = new Select2SingleChoice<>("officeField", 0, new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeField.setRequired(true);
        this.officeIContainer.add(this.officeField);
        this.officeFeedback = new TextFeedbackPanel("officeFeedback", this.officeField);
        this.officeIContainer.add(this.officeFeedback);
    }

    protected void initRuleNameBlock() {
        this.ruleNameBlock = new WebMarkupBlock("ruleNameBlock", Size.Six_6);
        this.form.add(this.ruleNameBlock);
        this.ruleNameIContainer = new WebMarkupContainer("ruleNameIContainer");
        this.ruleNameBlock.add(this.ruleNameIContainer);
        this.ruleNameField = new TextField<>("ruleNameField", new PropertyModel<>(this, "ruleNameValue"));
        this.ruleNameField.setRequired(true);
        this.ruleNameIContainer.add(this.ruleNameField);
        this.ruleNameFeedback = new TextFeedbackPanel("ruleNameFeedback", this.ruleNameField);
        this.ruleNameIContainer.add(this.ruleNameFeedback);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
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
