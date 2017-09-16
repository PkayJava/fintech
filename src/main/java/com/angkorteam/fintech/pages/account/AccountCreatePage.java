package com.angkorteam.fintech.pages.account;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.AccountType;
import com.angkorteam.fintech.dto.AccountUsage;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.request.GLAccountBuilder;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.AccountTypeProvider;
import com.angkorteam.fintech.provider.AccountUsageProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class AccountCreatePage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private AccountTypeProvider accountTypeProvider;
    private Option accountTypeValue;
    private Select2SingleChoice<Option> accountTypeField;
    private TextFeedbackPanel accountTypeFeedback;

    private SingleChoiceProvider parentProvider;
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

    private SingleChoiceProvider tagProvider;
    private Option tagValue;
    private Select2SingleChoice<Option> tagField;
    private TextFeedbackPanel tagFeedback;

    private Boolean manualAllowValue;
    private CheckBox manualAllowField;
    private TextFeedbackPanel manualAllowFeedback;

    private String descriptionValue;
    private TextArea<String> descriptionField;
    private TextFeedbackPanel descriptionFeedback;

    private static final List<PageBreadcrumb> BREADCRUMB;

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
            breadcrumb.setLabel("Accounts Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

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
        this.accountTypeField.setLabel(Model.of("Account Type"));
        this.accountTypeField.setRequired(true);
        this.accountTypeField.add(new OnChangeAjaxBehavior(this::accountTypeFieldUpdate));
        this.form.add(this.accountTypeField);
        this.accountTypeFeedback = new TextFeedbackPanel("accountTypeFeedback", this.accountTypeField);
        this.form.add(this.accountTypeFeedback);

        this.parentProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.parentProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Header.getLiteral());
        this.parentProvider.setDisabled(true);
        this.parentField = new Select2SingleChoice<>("parentField", 0, new PropertyModel<>(this, "parentValue"), this.parentProvider);
        this.parentField.setLabel(Model.of("Parent Account"));
        this.form.add(this.parentField);
        this.parentFeedback = new TextFeedbackPanel("parentFeedback", this.parentField);
        this.form.add(this.parentFeedback);

        this.glCodeField = new TextField<>("glCodeField", new PropertyModel<>(this, "glCodeValue"));
        this.glCodeField.setRequired(true);
        this.glCodeField.setLabel(Model.of("GL Code"));
        this.form.add(this.glCodeField);
        this.glCodeFeedback = new TextFeedbackPanel("glCodeFeedback", this.glCodeField);
        this.form.add(this.glCodeFeedback);

        this.accountNameField = new TextField<>("accountNameField", new PropertyModel<>(this, "accountNameValue"));
        this.accountNameField.setRequired(true);
        this.accountNameField.setLabel(Model.of("Account Name"));
        this.form.add(this.accountNameField);
        this.accountNameFeedback = new TextFeedbackPanel("accountNameFeedback", this.accountNameField);
        this.form.add(this.accountNameFeedback);

        this.accountUsageProvider = new AccountUsageProvider();
        this.accountUsageField = new Select2SingleChoice<>("accountUsageField", 0, new PropertyModel<>(this, "accountUsageValue"), this.accountUsageProvider);
        this.accountUsageField.setRequired(true);
        this.accountUsageField.setLabel(Model.of("Account Usage"));
        this.form.add(this.accountUsageField);
        this.accountUsageFeedback = new TextFeedbackPanel("accountUsageFeedback", this.accountUsageField);
        this.form.add(this.accountUsageFeedback);

        this.tagProvider = new SingleChoiceProvider("m_code_value", "id", "code_value");
        this.tagProvider.setDisabled(true);
        this.tagField = new Select2SingleChoice<>("tagField", 0, new PropertyModel<>(this, "tagValue"), this.tagProvider);
        this.tagField.setLabel(Model.of("Tag"));
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
        this.descriptionField.setLabel(Model.of("Description"));
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
            AccountType accountType = AccountType.valueOf(this.accountTypeValue.getId());
            this.tagValue = null;
            this.parentValue = null;
            this.tagProvider.applyWhere("code", "code_id in (select id from m_code where code_name = '" + accountType.getTag() + "')");
            this.tagProvider.setDisabled(false);
            this.parentProvider.setDisabled(false);
            this.parentProvider.applyWhere("classification_enum", "classification_enum = " + accountType.getLiteral());
        }
        if (target != null) {
            target.add(this.form);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
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
            builder.withType(AccountType.valueOf(this.accountTypeValue.getId()));
        }
        if (this.accountUsageValue != null) {
            builder.withUsage(AccountUsage.valueOf(this.accountUsageValue.getId()));
        }

        JsonNode node = null;
        try {
            node = GLAccountHelper.create((Session) getSession(), builder.build());
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
