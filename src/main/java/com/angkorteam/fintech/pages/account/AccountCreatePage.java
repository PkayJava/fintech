package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.request.GLAccountBuilder;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.provider.AccountTypeProvider;
import com.angkorteam.fintech.provider.AccountUsageProvider;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class AccountCreatePage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

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

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", AccountBrowsePage.class);
        this.form.add(this.closeLink);

        this.accountTypeProvider = new AccountTypeProvider();
        this.accountTypeField = new Select2SingleChoice<>("accountTypeField", 0, new PropertyModel<>(this, "accountTypeValue"), this.accountTypeProvider);
        this.accountTypeField.setRequired(true);
        this.accountTypeField.add(new OnChangeAjaxBehavior() {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                if (accountTypeValue == null) {
                    tagValue = null;
                    parentValue = null;
                    tagProvider.removeWhere("code");
                } else {
                    String tag = "";
                    if (GLAccountBuilder.Account.Asset.getLiteral().equals(accountTypeValue.getId())) {
                        tag = "AssetAccountTags";
                    } else if (GLAccountBuilder.Account.Equity.getLiteral().equals(accountTypeValue.getId())) {
                        tag = "EquityAccountTags";
                    } else if (GLAccountBuilder.Account.Expense.getLiteral().equals(accountTypeValue.getId())) {
                        tag = "ExpenseAccountTags";
                    } else if (GLAccountBuilder.Account.Income.getLiteral().equals(accountTypeValue.getId())) {
                        tag = "IncomeAccountTags";
                    } else if (GLAccountBuilder.Account.Liability.getLiteral().equals(accountTypeValue.getId())) {
                        tag = "LiabilityAccountTags";
                    }
                    tagValue = null;
                    parentValue = null;
                    tagProvider.applyWhere("code", "code_id in (select id from m_code where code_name = '" + tag + "')");
                    tagProvider.setDisabled(false);
                    parentProvider.setDisabled(false);
                    parentProvider.applyWhere("classification_enum", "classification_enum = " + accountTypeValue.getId());
                }
                target.add(form);
            }

            @Override
            protected void onError(AjaxRequestTarget target, RuntimeException e) {
                if (e != null) {
                    throw e;
                }
                tagValue = null;
                parentValue = null;
                tagProvider.removeWhere("code");
                tagProvider.setDisabled(true);
                parentProvider.removeWhere("classification_enum");
                parentProvider.setDisabled(true);
                target.add(form);
                target.appendJavaScript(Select2SingleChoice.REMOVE_POPUP_UP_SCRIPT);
            }
        });
        this.form.add(this.accountTypeField);
        this.accountTypeFeedback = new TextFeedbackPanel("accountTypeFeedback", this.accountTypeField);
        this.form.add(this.accountTypeFeedback);

        this.parentProvider = new OptionSingleChoiceProvider("acc_gl_account", "id", "name");
        this.parentProvider.applyWhere("account_usage", "account_usage = " + GLAccountBuilder.Usage.Header.getLiteral());
        this.parentProvider.setDisabled(true);
        this.parentField = new Select2SingleChoice<>("parentField", 0, new PropertyModel<>(this, "parentValue"), this.parentProvider);
        this.form.add(this.parentField);
        this.parentFeedback = new TextFeedbackPanel("parentFeedback", this.parentField);
        this.form.add(this.parentFeedback);

        this.glCodeField = new TextField<>("glCodeField", new PropertyModel<>(this, "glCodeValue"));
        this.glCodeField.setRequired(true);
        this.form.add(this.glCodeField);
        this.glCodeFeedback = new TextFeedbackPanel("glCodeFeedback", this.glCodeField);
        this.form.add(this.glCodeFeedback);

        this.accountNameField = new TextField<>("accountNameField", new PropertyModel<>(this, "accountNameValue"));
        this.accountNameField.setRequired(true);
        this.form.add(this.accountNameField);
        this.accountNameFeedback = new TextFeedbackPanel("accountNameFeedback", this.accountNameField);
        this.form.add(this.accountNameFeedback);

        this.accountUsageProvider = new AccountUsageProvider();
        this.accountUsageField = new Select2SingleChoice<>("accountUsageField", 0, new PropertyModel<>(this, "accountUsageValue"), this.accountUsageProvider);
        this.accountUsageField.setRequired(true);
        this.form.add(this.accountUsageField);
        this.accountUsageFeedback = new TextFeedbackPanel("accountUsageFeedback", this.accountUsageField);
        this.form.add(this.accountUsageFeedback);

        this.tagProvider = new OptionSingleChoiceProvider("m_code_value", "id", "code_value");
        this.tagProvider.setDisabled(true);
        this.tagField = new Select2SingleChoice<>("tagField", 0, new PropertyModel<>(this, "tagValue"), this.tagProvider);
        this.tagField.setRequired(true);
        this.form.add(this.tagField);
        this.tagFeedback = new TextFeedbackPanel("tagFeedback", this.tagField);
        this.form.add(this.tagFeedback);

        this.manualAllowField = new CheckBox("manualAllowField", new PropertyModel<>(this, "manualAllowValue"));
        this.manualAllowField.setRequired(true);
        this.form.add(this.manualAllowField);
        this.manualAllowFeedback = new TextFeedbackPanel("manualAllowFeedback", this.manualAllowField);
        this.form.add(this.manualAllowFeedback);

        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setRequired(true);
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);
    }

    private void saveButtonSubmit(Button button) {
        GLAccountBuilder builder = new GLAccountBuilder();
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
            builder.withType(GLAccountBuilder.Account.valueOf(this.accountTypeValue.getText()));
        }
        if (this.accountUsageValue != null) {
            builder.withUsage(GLAccountBuilder.Usage.valueOf(this.accountUsageValue.getText()));
        }

        JsonNode node = null;
        try {
            node = GLAccountHelper.createGLAccount(builder.build());
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
