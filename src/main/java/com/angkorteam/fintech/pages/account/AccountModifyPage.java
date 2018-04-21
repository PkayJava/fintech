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
import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.ddl.MCode;
import com.angkorteam.fintech.ddl.MCodeValue;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.GLAccountBuilder;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.helper.GLAccountHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.AccountTypeProvider;
import com.angkorteam.fintech.provider.AccountUsageProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class AccountModifyPage extends Page {

    protected String accountId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock accountTypeBlock;
    protected UIContainer accountTypeIContainer;
    protected AccountTypeProvider accountTypeProvider;
    protected Option accountTypeValue;
    protected Select2SingleChoice<Option> accountTypeField;

    protected UIBlock parentBlock;
    protected UIContainer parentIContainer;
    protected SingleChoiceProvider parentProvider;
    protected Option parentValue;
    protected Select2SingleChoice<Option> parentField;

    protected UIRow row2;

    protected UIBlock glCodeBlock;
    protected UIContainer glCodeIContainer;
    protected String glCodeValue;
    protected TextField<String> glCodeField;

    protected UIBlock accountNameBlock;
    protected UIContainer accountNameIContainer;
    protected String accountNameValue;
    protected TextField<String> accountNameField;

    protected UIRow row3;

    protected UIBlock accountUsageBlock;
    protected UIContainer accountUsageIContainer;
    protected AccountUsageProvider accountUsageProvider;
    protected Option accountUsageValue;
    protected Select2SingleChoice<Option> accountUsageField;

    protected UIBlock tagBlock;
    protected UIContainer tagIContainer;
    protected SingleChoiceProvider tagProvider;
    protected Option tagValue;
    protected Select2SingleChoice<Option> tagField;

    protected UIRow row4;

    protected UIBlock manualAllowBlock;
    protected UIContainer manualAllowIContainer;
    protected Boolean manualAllowValue;
    protected CheckBox manualAllowField;

    protected UIRow row5;

    protected UIBlock descriptionBlock;
    protected UIContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextArea<String> descriptionField;

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
        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, this.accountId);
        selectQuery.addField(AccGLAccount.Field.ID);
        selectQuery.addField(AccGLAccount.Field.NAME);
        selectQuery.addField(AccGLAccount.Field.PARENT_ID);
        selectQuery.addField(AccGLAccount.Field.GL_CODE);
        selectQuery.addField(AccGLAccount.Field.MANUAL_JOURNAL_ENTRIES_ALLOWED);
        selectQuery.addField("CONCAT(" + AccGLAccount.Field.ACCOUNT_USAGE + ",'') " + AccGLAccount.Field.ACCOUNT_USAGE);
        selectQuery.addField(AccGLAccount.Field.CLASSIFICATION_ENUM);
        selectQuery.addField(AccGLAccount.Field.TAG_ID);
        selectQuery.addField(AccGLAccount.Field.DESCRIPTION);

        Map<String, Object> account = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.accountTypeValue = AccountType.optionLiteral(String.valueOf(account.get(AccGLAccount.Field.CLASSIFICATION_ENUM)));

        selectQuery = new SelectQuery(AccGLAccount.NAME);
        selectQuery.addField(AccGLAccount.Field.ID);
        selectQuery.addField(AccGLAccount.Field.NAME + " AS text");
        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, account.get(AccGLAccount.Field.PARENT_ID));
        this.parentValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        this.glCodeValue = (String) account.get(AccGLAccount.Field.GL_CODE);
        this.accountNameValue = (String) account.get(AccGLAccount.Field.NAME);
        this.accountUsageValue = AccountUsage.optionLiteral(String.valueOf(account.get(AccGLAccount.Field.ACCOUNT_USAGE)));

        selectQuery = new SelectQuery(MCodeValue.NAME);
        selectQuery.addField(MCodeValue.Field.ID);
        selectQuery.addField(MCodeValue.Field.CODE_VALUE + " AS text");
        selectQuery.addWhere(MCodeValue.Field.ID + " = :" + MCodeValue.Field.ID, account.get(AccGLAccount.Field.TAG_ID));

        this.tagValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);
        this.manualAllowValue = (Boolean) account.get(AccGLAccount.Field.MANUAL_JOURNAL_ENTRIES_ALLOWED);
        this.descriptionValue = (String) account.get(AccGLAccount.Field.DESCRIPTION);

        this.accountTypeProvider = new AccountTypeProvider();

        this.parentProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.parentProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = '" + AccountUsage.Header.getLiteral() + "'");

        this.accountUsageProvider = new AccountUsageProvider();

        this.tagProvider = new SingleChoiceProvider(MCodeValue.NAME, MCodeValue.Field.ID, MCodeValue.Field.CODE_VALUE);
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.accountTypeBlock = this.row1.newUIBlock("accountTypeBlock", Size.Six_6);
        this.accountTypeIContainer = this.accountTypeBlock.newUIContainer("accountTypeIContainer");
        this.accountTypeField = new Select2SingleChoice<>("accountTypeField", new PropertyModel<>(this, "accountTypeValue"), this.accountTypeProvider);
        this.accountTypeIContainer.add(this.accountTypeField);
        this.accountTypeIContainer.newFeedback("accountTypeFeedback", this.accountTypeField);

        this.parentBlock = this.row1.newUIBlock("parentBlock", Size.Six_6);
        this.parentIContainer = this.parentBlock.newUIContainer("parentIContainer");
        this.parentField = new Select2SingleChoice<>("parentField", new PropertyModel<>(this, "parentValue"), this.parentProvider);
        this.parentIContainer.add(this.parentField);
        this.parentIContainer.newFeedback("parentFeedback", this.parentField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.glCodeBlock = this.row2.newUIBlock("glCodeBlock", Size.Six_6);
        this.glCodeIContainer = this.glCodeBlock.newUIContainer("glCodeIContainer");
        this.glCodeField = new TextField<>("glCodeField", new PropertyModel<>(this, "glCodeValue"));
        this.glCodeIContainer.add(this.glCodeField);
        this.glCodeIContainer.newFeedback("glCodeFeedback", this.glCodeField);

        this.accountNameBlock = this.row2.newUIBlock("accountNameBlock", Size.Six_6);
        this.accountNameIContainer = this.accountNameBlock.newUIContainer("accountNameIContainer");
        this.accountNameField = new TextField<>("accountNameField", new PropertyModel<>(this, "accountNameValue"));
        this.accountNameIContainer.add(this.accountNameField);
        this.accountNameIContainer.newFeedback("accountNameFeedback", this.accountNameField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.accountUsageBlock = this.row3.newUIBlock("accountUsageBlock", Size.Six_6);
        this.accountUsageIContainer = this.accountUsageBlock.newUIContainer("accountUsageIContainer");
        this.accountUsageField = new Select2SingleChoice<>("accountUsageField", new PropertyModel<>(this, "accountUsageValue"), this.accountUsageProvider);
        this.accountUsageIContainer.add(this.accountUsageField);
        this.accountUsageIContainer.newFeedback("accountUsageFeedback", this.accountUsageField);

        this.tagBlock = this.row3.newUIBlock("tagBlock", Size.Six_6);
        this.tagIContainer = this.tagBlock.newUIContainer("tagIContainer");
        this.tagField = new Select2SingleChoice<>("tagField", new PropertyModel<>(this, "tagValue"), this.tagProvider);
        this.tagIContainer.add(this.tagField);
        this.tagIContainer.newFeedback("tagFeedback", this.tagField);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.manualAllowBlock = this.row4.newUIBlock("manualAllowBlock", Size.Twelve_12);
        this.manualAllowIContainer = this.manualAllowBlock.newUIContainer("manualAllowIContainer");
        this.manualAllowField = new CheckBox("manualAllowField", new PropertyModel<>(this, "manualAllowValue"));
        this.manualAllowIContainer.add(this.manualAllowField);
        this.manualAllowIContainer.newFeedback("manualAllowFeedback", this.manualAllowField);

        this.row5 = UIRow.newUIRow("row5", this.form);

        this.descriptionBlock = this.row5.newUIBlock("descriptionBlock", Size.Twelve_12);
        this.descriptionIContainer = this.descriptionBlock.newUIContainer("descriptionIContainer");
        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionIContainer.newFeedback("descriptionFeedback", this.descriptionField);
    }

    @Override
    protected void configureMetaData() {
        this.accountTypeField.setRequired(true);
        this.glCodeField.setRequired(true);
        this.accountNameField.setRequired(true);
        this.descriptionField.setRequired(true);
        this.manualAllowField.setRequired(true);
        this.accountUsageField.setRequired(true);

        if (this.accountTypeValue == null) {
            this.tagProvider.removeWhere("code");
        } else {
            AccountType temp = AccountType.valueOf(this.accountTypeValue.getId());
            this.tagProvider.applyWhere("code", MCodeValue.Field.CODE_ID + " in (select " + MCode.Field.ID + " from " + MCode.NAME + " where " + MCode.Field.CODE_NAME + " = '" + temp.getTag() + "')");
            this.parentProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = '" + this.accountTypeValue.getId() + "'");
        }

        this.accountTypeField.setLabel(Model.of("Account Type"));
        this.accountTypeField.add(new OnChangeAjaxBehavior(this::accountTypeFieldUpdate));
        this.parentField.setLabel(Model.of("Parent Account"));
        this.glCodeField.setLabel(Model.of("GL Code"));
        this.accountNameField.setLabel(Model.of("Account Name"));
        this.accountUsageField.setLabel(Model.of("Account Usage"));
        this.tagField.setLabel(Model.of("Tag"));
        this.descriptionField.setLabel(Model.of("Description"));
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

        JsonNode node = GLAccountHelper.update((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(AccountBrowsePage.class);
    }

}
