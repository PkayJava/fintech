package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.AccountType;
import com.angkorteam.fintech.dto.AccountUsage;
import com.angkorteam.fintech.dto.request.GLAccountBuilder;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.provider.AccountTypeProvider;
import com.angkorteam.fintech.provider.AccountUsageProvider;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionMapper;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Map;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class AccountModifyPage extends Page {

    private String accountId;

    private AccountTypeProvider accountTypeProvider;
    private Option accountTypeValue;
    private Select2SingleChoice<Option> accountTypeField;
    private TextFeedbackPanel accountTypeFeedback;

    private OptionSingleChoiceProvider parentProvider;
    private Option parentValue;
    private Select2SingleChoice<Option> parentField;
    private TextFeedbackPanel parentFeedback;

    private String glCodeValue;
    private TextField<String> glCodeField;
    private TextFeedbackPanel glCodeFeedback;

    private String accountNameValue;
    private TextField<String> accountNameField;
    private TextFeedbackPanel accountNameFeedback;

    private AccountUsageProvider accountUsageProvider;
    private Option accountUsageValue;
    private Select2SingleChoice<Option> accountUsageField;
    private TextFeedbackPanel accountUsageFeedback;

    private OptionSingleChoiceProvider tagProvider;
    private Option tagValue;
    private Select2SingleChoice<Option> tagField;
    private TextFeedbackPanel tagFeedback;

    private Boolean manualAllowValue;
    private CheckBox manualAllowField;
    private TextFeedbackPanel manualAllowFeedback;

    private String descriptionValue;
    private TextArea<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

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

        for (AccountType a : AccountType.values()) {
            if (a.getLiteral().equals(String.valueOf(account.get("classification_enum")))) {
                this.accountTypeValue = new Option(a.name(), a.getDescription());
                break;
            }
        }
        this.accountTypeProvider = new AccountTypeProvider();
        this.accountTypeField = new Select2SingleChoice<>("accountTypeField", 0, new PropertyModel<>(this, "accountTypeValue"), this.accountTypeProvider);
        this.accountTypeField.setRequired(true);
        this.accountTypeField.add(new OnChangeAjaxBehavior(this::accountTypeFieldUpdate, this::accountTypeFieldError));
        this.form.add(this.accountTypeField);
        this.accountTypeFeedback = new TextFeedbackPanel("accountTypeFeedback", this.accountTypeField);
        this.form.add(this.accountTypeFeedback);

        this.parentValue = jdbcTemplate.queryForObject("select id, name text from acc_gl_account where id = ?", new OptionMapper(), account.get("parent_id"));
        this.parentProvider = new OptionSingleChoiceProvider("acc_gl_account", "id", "name");
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

        for (AccountUsage a : AccountUsage.values()) {
            if (a.getLiteral().equals(account.get("account_usage"))) {
                this.accountUsageValue = new Option(a.name(), a.getDescription());
                break;
            }
        }
        this.accountUsageProvider = new AccountUsageProvider();
        this.accountUsageField = new Select2SingleChoice<>("accountUsageField", 0, new PropertyModel<>(this, "accountUsageValue"), this.accountUsageProvider);
        this.form.add(this.accountUsageField);
        this.accountUsageFeedback = new TextFeedbackPanel("accountUsageFeedback", this.accountUsageField);
        this.form.add(this.accountUsageFeedback);

        this.tagValue = jdbcTemplate.queryForObject("select id, code_value text from m_code_value where id = ?", new OptionMapper(), account.get("tag_id"));
        this.tagProvider = new OptionSingleChoiceProvider("m_code_value", "id", "code_value");
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

    private void accountTypeFieldUpdate(AjaxRequestTarget target) {
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
    }

    private void accountTypeFieldError(AjaxRequestTarget target, RuntimeException e) {
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
        target.appendJavaScript(Select2SingleChoice.REMOVE_POPUP_UP_SCRIPT);
    }

    private void saveButtonSubmit(Button button) {
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
            node = GLAccountHelper.update(builder.build());
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
