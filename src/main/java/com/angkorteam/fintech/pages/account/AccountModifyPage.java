package com.angkorteam.fintech.pages.account;

import java.util.List;
import java.util.Map;

import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.ddl.MCode;
import com.angkorteam.fintech.ddl.MCodeValue;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcNamed;
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
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
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

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
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
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        PageParameters parameters = getPageParameters();
        this.accountId = parameters.get("accountId").toString("");

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(AccGLAccount.NAME);
        selectQuery.addWhere(AccGLAccount.Field.ID + " = :", AccGLAccount.Field.ID, this.accountId);
        selectQuery.addField(AccGLAccount.Field.ID);
        selectQuery.addField(AccGLAccount.Field.NAME);
        selectQuery.addField(AccGLAccount.Field.PARENT_ID);
        selectQuery.addField(AccGLAccount.Field.GL_CODE);
        selectQuery.addField(AccGLAccount.Field.MANUAL_JOURNAL_ENTRIES_ALLOWED);
        selectQuery.addField("concat(" + AccGLAccount.Field.ACCOUNT_USAGE + ",'') " + AccGLAccount.Field.ACCOUNT_USAGE);
        selectQuery.addField(AccGLAccount.Field.CLASSIFICATION_ENUM);
        selectQuery.addField(AccGLAccount.Field.TAG_ID);
        selectQuery.addField(AccGLAccount.Field.DESCRIPTION);

        Map<String, Object> account = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.accountTypeValue = AccountType.optionLiteral(String.valueOf(account.get(AccGLAccount.Field.CLASSIFICATION_ENUM)));

        selectQuery = new SelectQuery(AccGLAccount.NAME);
        selectQuery.addField(AccGLAccount.Field.ID);
        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
        selectQuery.addWhere(AccGLAccount.Field.ID + " = :", AccGLAccount.Field.ID, account.get(AccGLAccount.Field.PARENT_ID));
        this.parentValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        this.glCodeValue = (String) account.get(AccGLAccount.Field.GL_CODE);
        this.accountNameValue = (String) account.get(AccGLAccount.Field.NAME);
        this.accountUsageValue = AccountUsage.optionLiteral(String.valueOf(account.get(AccGLAccount.Field.ACCOUNT_USAGE)));

        selectQuery = new SelectQuery(MCodeValue.NAME);
        selectQuery.addField(MCodeValue.Field.ID);
        selectQuery.addField(MCodeValue.Field.CODE_VALUE + " as text");
        selectQuery.addWhere(MCodeValue.Field.ID + " = :" + MCodeValue.Field.ID, account.get(AccGLAccount.Field.TAG_ID));

        this.tagValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
        this.manualAllowValue = (Boolean) account.get(AccGLAccount.Field.MANUAL_JOURNAL_ENTRIES_ALLOWED);
        this.descriptionValue = (String) account.get(AccGLAccount.Field.DESCRIPTION);
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

        initTagBlock();

    }

    protected void initAccountTypeBlock() {
        this.accountTypeBlock = new WebMarkupBlock("accountTypeBlock", Size.Six_6);
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
        this.descriptionBlock = new WebMarkupBlock("descriptionBlock", Size.Twelve_12);
        this.form.add(this.descriptionBlock);
        this.descriptionIContainer = new WebMarkupContainer("descriptionIContainer");
        this.descriptionBlock.add(this.descriptionIContainer);
        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionField.setLabel(Model.of("Description"));
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionFeedback = new TextFeedbackPanel("descriptionFeedback", this.descriptionField);
        this.descriptionIContainer.add(this.descriptionFeedback);
    }

    protected void initManualAllowBlock() {
        this.manualAllowBlock = new WebMarkupBlock("manualAllowBlock", Size.Twelve_12);
        this.form.add(this.manualAllowBlock);
        this.manualAllowIContainer = new WebMarkupContainer("manualAllowIContainer");
        this.manualAllowBlock.add(this.manualAllowIContainer);
        this.manualAllowField = new CheckBox("manualAllowField", new PropertyModel<>(this, "manualAllowValue"));
        this.manualAllowIContainer.add(this.manualAllowField);
        this.manualAllowFeedback = new TextFeedbackPanel("manualAllowFeedback", this.manualAllowField);
        this.manualAllowIContainer.add(this.manualAllowFeedback);
    }

    protected void initTagBlock() {
        this.tagBlock = new WebMarkupBlock("tagBlock", Size.Six_6);
        this.form.add(this.tagBlock);
        this.tagIContainer = new WebMarkupContainer("tagIContainer");
        this.tagBlock.add(this.tagIContainer);
        this.tagProvider = new SingleChoiceProvider(MCodeValue.NAME, MCodeValue.Field.ID, MCodeValue.Field.CODE_VALUE);
        this.tagField = new Select2SingleChoice<>("tagField", new PropertyModel<>(this, "tagValue"), this.tagProvider);
        this.tagField.setLabel(Model.of("Tag"));
        this.tagIContainer.add(this.tagField);
        this.tagFeedback = new TextFeedbackPanel("tagFeedback", this.tagField);
        this.tagIContainer.add(this.tagFeedback);
    }

    protected void initAccountUsageBlock() {
        this.accountUsageBlock = new WebMarkupBlock("accountUsageBlock", Size.Six_6);
        this.form.add(this.accountUsageBlock);
        this.accountUsageIContainer = new WebMarkupContainer("accountUsageIContainer");
        this.accountUsageBlock.add(this.accountUsageIContainer);
        this.accountUsageProvider = new AccountUsageProvider();
        this.accountUsageField = new Select2SingleChoice<>("accountUsageField", new PropertyModel<>(this, "accountUsageValue"), this.accountUsageProvider);
        this.accountUsageField.setLabel(Model.of("Account Usage"));
        this.accountUsageIContainer.add(this.accountUsageField);
        this.accountUsageFeedback = new TextFeedbackPanel("accountUsageFeedback", this.accountUsageField);
        this.accountUsageIContainer.add(this.accountUsageFeedback);
    }

    protected void initAccountNameBlock() {
        this.accountNameBlock = new WebMarkupBlock("accountNameBlock", Size.Six_6);
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
        this.glCodeBlock = new WebMarkupBlock("glCodeBlock", Size.Six_6);
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
        this.parentBlock = new WebMarkupBlock("parentBlock", Size.Six_6);
        this.form.add(this.parentBlock);
        this.parentIContainer = new WebMarkupContainer("parentIContainer");
        this.parentBlock.add(this.parentIContainer);
        this.parentProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.parentProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = '" + AccountUsage.Header.getLiteral() + "'");
        this.parentField = new Select2SingleChoice<>("parentField", new PropertyModel<>(this, "parentValue"), this.parentProvider);
        this.parentField.setLabel(Model.of("Parent Account"));
        this.parentIContainer.add(this.parentField);
        this.parentFeedback = new TextFeedbackPanel("parentFeedback", this.parentField);
        this.parentIContainer.add(this.parentFeedback);
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
            this.tagProvider.removeWhere("code");
        } else {
            AccountType temp = AccountType.valueOf(this.accountTypeValue.getId());
            this.tagProvider.applyWhere("code", MCodeValue.Field.CODE_ID + " in (select " + MCode.Field.ID + " from " + MCode.NAME + " where " + MCode.Field.CODE_NAME + " = '" + temp.getTag() + "')");
            this.parentProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = '" + this.accountTypeValue.getId() + "'");
        }
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
            this.tagProvider.applyWhere("code", MCodeValue.Field.CODE_ID + " in (select " + MCode.Field.ID + " from " + MCode.NAME + " where " + MCode.Field.CODE_NAME + " = '" + temp.getTag() + "')");
            this.tagProvider.setDisabled(false);
            this.parentProvider.setDisabled(false);
            this.parentProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = '" + this.accountTypeValue.getId() + "'");
        }
        if (target != null) {
            target.add(this.tagBlock);
            target.add(this.parentBlock);
        }
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
