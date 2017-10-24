package com.angkorteam.fintech.pages.account;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

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
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.models.PageBreadcrumb;
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
public class AccountCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock accountTypeBlock;
    protected WebMarkupContainer accountTypeIContainer;
    protected AccountTypeProvider accountTypeProvider;
    protected Option accountTypeValue;
    protected Select2SingleChoice<Option> accountTypeField;
    protected TextFeedbackPanel accountTypeFeedback;

    protected WebMarkupBlock parentBlock;
    protected WebMarkupContainer parentIContainer;
    protected SingleChoiceProvider parentProvider;
    protected Option parentValue;
    protected Select2SingleChoice<Option> parentField;
    protected TextFeedbackPanel parentFeedback;

    protected WebMarkupBlock glCodeBlock;
    protected WebMarkupContainer glCodeIContainer;
    protected String glCodeValue;
    protected TextField<String> glCodeField;
    protected TextFeedbackPanel glCodeFeedback;

    protected WebMarkupBlock accountNameBlock;
    protected WebMarkupContainer accountNameIContainer;
    protected String accountNameValue;
    protected TextField<String> accountNameField;
    protected TextFeedbackPanel accountNameFeedback;

    protected WebMarkupBlock accountUsageBlock;
    protected WebMarkupContainer accountUsageIContainer;
    protected AccountUsageProvider accountUsageProvider;
    protected Option accountUsageValue;
    protected Select2SingleChoice<Option> accountUsageField;
    protected TextFeedbackPanel accountUsageFeedback;

    protected WebMarkupBlock tagBlock;
    protected WebMarkupContainer tagIContainer;
    protected SingleChoiceProvider tagProvider;
    protected Option tagValue;
    protected Select2SingleChoice<Option> tagField;
    protected TextFeedbackPanel tagFeedback;

    protected WebMarkupBlock manualAllowBlock;
    protected WebMarkupContainer manualAllowIContainer;
    protected Boolean manualAllowValue;
    protected CheckBox manualAllowField;
    protected TextFeedbackPanel manualAllowFeedback;

    protected WebMarkupBlock descriptionBlock;
    protected WebMarkupContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextArea<String> descriptionField;
    protected TextFeedbackPanel descriptionFeedback;

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
            breadcrumb.setLabel("Accounts Create");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void configureRequiredValidation() {
        this.accountTypeField.setRequired(true);
        this.glCodeField.setRequired(true);
        this.accountNameField.setRequired(true);
        this.descriptionField.setRequired(true);
        this.manualAllowField.setRequired(true);
        this.tagField.setRequired(true);
        this.accountUsageField.setRequired(true);
    }

    @Override
    protected void configureMetaData() {
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
    }

    @Override
    protected void initComponent() {
        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.closeLink = new BookmarkablePageLink<>("closeLink", AccountBrowsePage.class);
        this.form.add(this.closeLink);

        initAccountTypeBlock();

        initParentBlock();

        initGlCodeBlock();

        initAccountNameBlock();

        initAccountUsageBlock();

        initManualAllowBlock();

        initDescriptionBlock();

    }

    protected void initAccountTypeBlock() {
        this.accountTypeBlock = new WebMarkupBlock("accountTypeBlock");
        this.form.add(this.accountTypeBlock);
        this.accountTypeIContainer = new WebMarkupContainer("accountTypeIContainer");
        this.accountTypeBlock.add(this.accountTypeIContainer);
        this.accountTypeProvider = new AccountTypeProvider();
        this.accountTypeField = new Select2SingleChoice<>("accountTypeField", new PropertyModel<>(this, "accountTypeValue"), this.accountTypeProvider);
        this.accountTypeField.setLabel(Model.of("Account Type"));
        this.accountTypeField.add(new OnChangeAjaxBehavior(this::accountTypeFieldUpdate));
        this.accountTypeIContainer.add(this.accountTypeField);
        this.accountTypeFeedback = new TextFeedbackPanel("accountTypeFeedback", this.accountTypeField);
        this.accountTypeIContainer.add(this.accountTypeFeedback);
    }

    protected void initDescriptionBlock() {
        this.manualAllowBlock = new WebMarkupBlock("manualAllowBlock");
        this.form.add(this.manualAllowBlock);
        this.manualAllowIContainer = new WebMarkupContainer("manualAllowIContainer");
        this.manualAllowBlock.add(this.manualAllowIContainer);
        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setLabel(Model.of("Description"));
        this.form.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.form.add(this.descriptionFeedback);
    }

    protected void initManualAllowBlock() {
        this.manualAllowBlock = new WebMarkupBlock("manualAllowBlock");
        this.form.add(this.manualAllowBlock);
        this.manualAllowIContainer = new WebMarkupContainer("manualAllowIContainer");
        this.manualAllowBlock.add(this.manualAllowIContainer);
        this.manualAllowField = new CheckBox("manualAllowField", new PropertyModel<>(this, "manualAllowValue"));
        this.manualAllowIContainer.add(this.manualAllowField);
        this.manualAllowFeedback = new TextFeedbackPanel("manualAllowFeedback", this.manualAllowField);
        this.manualAllowIContainer.add(this.manualAllowFeedback);
    }

    protected void initTagBlock() {
        this.tagBlock = new WebMarkupBlock("tagBlock");
        this.form.add(this.tagBlock);
        this.tagIContainer = new WebMarkupContainer("tagIContainer");
        this.tagBlock.add(this.tagIContainer);
        this.tagProvider = new SingleChoiceProvider("m_code_value", "id", "code_value");
        this.tagProvider.setDisabled(true);
        this.tagField = new Select2SingleChoice<>("tagField", new PropertyModel<>(this, "tagValue"), this.tagProvider);
        this.tagField.setLabel(Model.of("Tag"));
        this.form.add(this.tagField);
        this.tagFeedback = new TextFeedbackPanel("tagFeedback", this.tagField);
        this.form.add(this.tagFeedback);
    }

    protected void initAccountUsageBlock() {
        this.accountUsageBlock = new WebMarkupBlock("accountUsageBlock");
        this.form.add(this.accountUsageBlock);
        this.accountUsageIContainer = new WebMarkupContainer("accountUsageIContainer");
        this.accountUsageBlock.add(this.accountUsageIContainer);
        this.accountUsageProvider = new AccountUsageProvider();
        this.accountUsageField = new Select2SingleChoice<>("accountUsageField", new PropertyModel<>(this, "accountUsageValue"), this.accountUsageProvider);
        this.accountUsageField.setLabel(Model.of("Account Usage"));
        this.form.add(this.accountUsageField);
        this.accountUsageFeedback = new TextFeedbackPanel("accountUsageFeedback", this.accountUsageField);
        this.form.add(this.accountUsageFeedback);
    }

    protected void initAccountNameBlock() {
        this.accountNameBlock = new WebMarkupBlock("accountNameBlock");
        this.form.add(this.accountNameBlock);
        this.accountNameIContainer = new WebMarkupContainer("accountNameIContainer");
        this.accountNameBlock.add(this.accountNameIContainer);
        this.accountNameField = new TextField<>("accountNameField", new PropertyModel<>(this, "accountNameValue"));
        this.accountNameField.setLabel(Model.of("Account Name"));
        this.accountNameIContainer.add(this.accountNameField);
        this.accountNameFeedback = new TextFeedbackPanel("accountNameFeedback", this.accountNameField);
        this.accountNameIContainer.add(this.accountNameFeedback);
    }

    protected void initGlCodeBlock() {
        this.glCodeBlock = new WebMarkupBlock("glCodeBlock");
        this.form.add(this.glCodeBlock);
        this.glCodeIContainer = new WebMarkupContainer("glCodeIContainer");
        this.glCodeBlock.add(this.glCodeIContainer);
        this.glCodeField = new TextField<>("glCodeField", new PropertyModel<>(this, "glCodeValue"));
        this.glCodeField.setLabel(Model.of("GL Code"));
        this.glCodeIContainer.add(this.glCodeField);
        this.glCodeFeedback = new TextFeedbackPanel("glCodeFeedback", this.glCodeField);
        this.glCodeIContainer.add(this.glCodeFeedback);
    }

    protected void initParentBlock() {
        this.parentBlock = new WebMarkupBlock("parentBlock");
        this.form.add(this.parentBlock);
        this.parentIContainer = new WebMarkupContainer("parentIContainer");
        this.parentBlock.add(this.parentIContainer);
        this.parentProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.parentProvider.applyWhere("account_usage", "account_usage = " + AccountUsage.Header.getLiteral());
        this.parentProvider.setDisabled(true);
        this.parentField = new Select2SingleChoice<>("parentField", new PropertyModel<>(this, "parentValue"), this.parentProvider);
        this.parentField.setLabel(Model.of("Parent Account"));
        this.parentIContainer.add(this.parentField);
        this.parentFeedback = new TextFeedbackPanel("parentFeedback", this.parentField);
        this.parentIContainer.add(this.parentFeedback);
    }

    protected boolean accountTypeFieldUpdate(AjaxRequestTarget target) {
        configureMetaData();
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
