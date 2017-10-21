package com.angkorteam.fintech.pages.account;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
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
import com.angkorteam.fintech.dto.builder.GLAccountBuilder;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.AccountTypeProvider;
import com.angkorteam.fintech.provider.AccountUsageProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class AccountModifyPage extends Page {

    protected String accountId;

    protected AccountTypeProvider accountTypeProvider;
    protected Option accountTypeValue;
    protected Select2SingleChoice<Option> accountTypeField;
    protected TextFeedbackPanel accountTypeFeedback;

    protected SingleChoiceProvider parentProvider;
    protected Option parentValue;
    protected Select2SingleChoice<Option> parentField;
    protected TextFeedbackPanel parentFeedback;

    protected String glCodeValue;
    protected TextField<String> glCodeField;
    protected TextFeedbackPanel glCodeFeedback;

    protected String accountNameValue;
    protected TextField<String> accountNameField;
    protected TextFeedbackPanel accountNameFeedback;

    protected AccountUsageProvider accountUsageProvider;
    protected Option accountUsageValue;
    protected Select2SingleChoice<Option> accountUsageField;
    protected TextFeedbackPanel accountUsageFeedback;

    protected SingleChoiceProvider tagProvider;
    protected Option tagValue;
    protected Select2SingleChoice<Option> tagField;
    protected TextFeedbackPanel tagFeedback;

    protected Boolean manualAllowValue;
    protected CheckBox manualAllowField;
    protected TextFeedbackPanel manualAllowFeedback;

    protected String descriptionValue;
    protected TextArea<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

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
            breadcrumb.setLabel("Chart of Accounts");
            breadcrumb.setPage(AccountBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounts Modify");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();
        this.accountId = parameters.get("accountId").toString("");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> account = jdbcTemplate.queryForMap("select id,name,parent_id,gl_code, manual_journal_entries_allowed, concat(account_usage,'') account_usage, classification_enum, tag_id,description from acc_gl_account where id = ?", accountId);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", AccountBrowsePage.class);
        this.form.add(this.closeLink);

        this.accountTypeValue = AccountType.optionLiteral(String.valueOf(account.get("classification_enum")));
        this.accountTypeProvider = new AccountTypeProvider();
        this.accountTypeField = new Select2SingleChoice<>("accountTypeField", 0, new PropertyModel<>(this, "accountTypeValue"), this.accountTypeProvider);
        this.accountTypeField.setRequired(true);
        this.accountTypeField.add(new OnChangeAjaxBehavior(this::accountTypeFieldUpdate, this::accountTypeFieldError));
        this.form.add(this.accountTypeField);
        this.accountTypeFeedback = new TextFeedbackPanel("accountTypeFeedback", this.accountTypeField);
        this.form.add(this.accountTypeFeedback);

        this.parentValue = jdbcTemplate.queryForObject("select id, name text from acc_gl_account where id = ?", Option.MAPPER, account.get("parent_id"));
        this.parentProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.parentProvider.applyWhere("classification_enum", "classification_enum = " + accountTypeValue.getId());
        this.parentProvider.applyWhere("check", "id != " + this.accountId);
        this.parentProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Header.getLiteral());
        this.parentField = new Select2SingleChoice<>("parentField", 0, new PropertyModel<>(this, "parentValue"), this.parentProvider);
        this.form.add(this.parentField);
        this.parentFeedback = new TextFeedbackPanel("parentFeedback", this.parentField);
        this.form.add(this.parentFeedback);

        this.glCodeValue = (String) account.get("gl_code");
        this.glCodeField = new TextField<>("glCodeField", new PropertyModel<>(this, "glCodeValue"));
        this.glCodeField.setRequired(true);
        this.form.add(this.glCodeField);
        this.glCodeFeedback = new TextFeedbackPanel("glCodeFeedback", this.glCodeField);
        this.form.add(this.glCodeFeedback);

        this.accountNameValue = (String) account.get("name");
        this.accountNameField = new TextField<>("accountNameField", new PropertyModel<>(this, "accountNameValue"));
        this.accountNameField.setRequired(true);
        this.form.add(this.accountNameField);
        this.accountNameFeedback = new TextFeedbackPanel("accountNameFeedback", this.accountNameField);
        this.form.add(this.accountNameFeedback);

        this.accountUsageValue = AccountUsage.optionLiteral(String.valueOf(account.get("account_usage")));
        this.accountUsageProvider = new AccountUsageProvider();
        this.accountUsageField = new Select2SingleChoice<>("accountUsageField", 0, new PropertyModel<>(this, "accountUsageValue"), this.accountUsageProvider);
        this.form.add(this.accountUsageField);
        this.accountUsageFeedback = new TextFeedbackPanel("accountUsageFeedback", this.accountUsageField);
        this.form.add(this.accountUsageFeedback);

        this.tagValue = jdbcTemplate.queryForObject("select id, code_value text from m_code_value where id = ?", Option.MAPPER, account.get("tag_id"));
        this.tagProvider = new SingleChoiceProvider("m_code_value", "id", "code_value");
        AccountType temp = AccountType.valueOf(accountTypeValue.getId());
        this.tagProvider.applyWhere("code", "code_id in (select id from m_code where code_name = '" + temp.getTag() + "')");
        this.tagField = new Select2SingleChoice<>("tagField", 0, new PropertyModel<>(this, "tagValue"), this.tagProvider);
        this.tagField.setRequired(true);
        this.form.add(this.tagField);
        this.tagFeedback = new TextFeedbackPanel("tagFeedback", this.tagField);
        this.form.add(this.tagFeedback);

        this.manualAllowValue = (Boolean) account.get("manual_journal_entries_allowed");
        this.manualAllowField = new CheckBox("manualAllowField", new PropertyModel<>(this, "manualAllowValue"));
        this.manualAllowField.setRequired(true);
        this.form.add(this.manualAllowField);
        this.manualAllowFeedback = new TextFeedbackPanel("manualAllowFeedback", this.manualAllowField);
        this.form.add(this.manualAllowFeedback);

        this.descriptionValue = (String) account.get("description");
        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(true);
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);
    }

    protected boolean accountTypeFieldUpdate(AjaxRequestTarget target) {
        if (this.accountTypeValue == null) {
            this.tagValue = null;
            this.parentValue = null;
            this.tagProvider.removeWhere("code");
        } else {
            AccountType temp = AccountType.valueOf(this.accountTypeValue.getId());
            this.tagValue = null;
            this.parentValue = null;
            this.tagProvider.applyWhere("code", "code_id in (select id from m_code where code_name = '" + temp.getTag() + "')");
            this.tagProvider.setDisabled(false);
            this.parentProvider.setDisabled(false);
            this.parentProvider.applyWhere("classification_enum", "classification_enum = " + this.accountTypeValue.getId());
        }
        target.add(this.form);
        return false;
    }

    protected boolean accountTypeFieldError(AjaxRequestTarget target, RuntimeException e) {
        if (e != null) {
            throw e;
        }
        this.tagValue = null;
        this.parentValue = null;
        this.tagProvider.removeWhere("code");
        this.tagProvider.setDisabled(true);
        this.parentProvider.removeWhere("classification_enum");
        this.parentProvider.setDisabled(true);
        target.add(this.form);
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        GLAccountBuilder builder = new GLAccountBuilder();
        builder.withId(this.accountId);
        builder.withDescription(this.descriptionValue);
        builder.withGlCode(this.glCodeValue);
        builder.withManualEntriesAllowed(this.manualAllowValue);
        builder.withName(this.accountNameValue);
        if (this.parentValue != null) {
            builder.withParentId(this.parentValue.getId());
        }
        if (this.tagValue != null) {
            builder.withTagId(this.tagValue.getId());
        }
        if (this.accountTypeValue != null) {
            builder.withType(AccountType.valueOf(this.accountTypeValue.getText()));
        }
        if (this.accountUsageValue != null) {
            builder.withUsage(AccountUsage.valueOf(this.accountUsageValue.getText()));
        }

        JsonNode node = null;
        try {
            node = GLAccountHelper.update((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(AccountBrowsePage.class);
    }

}
