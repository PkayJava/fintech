package com.angkorteam.fintech.pages.account;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.AccAccountingRule;
import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.ddl.AccRuleTags;
import com.angkorteam.fintech.ddl.MCodeValue;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.AccountRuleBuilder;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.helper.AccountingRuleHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 7/3/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RuleModifyPage extends Page {

    public static final String FIXED = "Fixed Account";
    public static final String TAG = "List of Accounts";

    protected String ruleId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock ruleNameBlock;
    protected UIContainer ruleNameIContainer;
    protected String ruleNameValue;
    protected TextField<String> ruleNameField;

    protected UIBlock officeBlock;
    protected UIContainer officeIContainer;
    protected SingleChoiceProvider officeProvider;
    protected Option officeValue;
    protected Select2SingleChoice<Option> officeField;

    protected UIRow row2;

    protected UIBlock descriptionBlock;
    protected UIContainer descriptionIContainer;
    protected String descriptionValue;
    protected TextArea<String> descriptionField;

    protected UIRow row3;

    protected UIBlock debitRuleTypeBlock;
    protected UIContainer debitRuleTypeIContainer;
    protected List<String> debitRuleTypeProvider;
    protected String debitRuleTypeValue;
    protected DropDownChoice<String> debitRuleTypeField;

    protected UIBlock debitAccountBlock;
    protected UIContainer debitAccountIContainer;
    protected SingleChoiceProvider debitAccountProvider;
    protected Option debitAccountValue;
    protected Select2SingleChoice<Option> debitAccountField;
    protected UIContainer debitTagIContainer;
    protected MultipleChoiceProvider debitTagProvider;
    protected List<Option> debitTagValue;
    protected Select2MultipleChoice<Option> debitTagField;

    protected UIBlock multipleDebitBlock;
    protected UIContainer multipleDebitIContainer;
    protected Boolean multipleDebitValue;
    protected CheckBox multipleDebitField;

    protected UIRow row4;

    protected UIBlock creditRuleTypeBlock;
    protected UIContainer creditRuleTypeIContainer;
    protected List<String> creditRuleTypeProvider;
    protected String creditRuleTypeValue;
    protected DropDownChoice<String> creditRuleTypeField;

    protected UIBlock creditAccountBlock;
    protected UIContainer creditAccountIContainer;
    protected SingleChoiceProvider creditAccountProvider;
    protected Option creditAccountValue;
    protected Select2SingleChoice<Option> creditAccountField;
    protected UIContainer creditTagIContainer;
    protected MultipleChoiceProvider creditTagProvider;
    protected List<Option> creditTagValue;
    protected Select2MultipleChoice<Option> creditTagField;

    protected UIBlock multipleCreditBlock;
    protected UIContainer multipleCreditIContainer;
    protected Boolean multipleCreditValue;
    protected CheckBox multipleCreditField;

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
            breadcrumb.setLabel("Accounting Rule");
            breadcrumb.setPage(RuleBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting Rule Modify");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.ruleId = parameters.get("ruleId").toString("");

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(AccAccountingRule.NAME);
        selectQuery.addWhere(AccAccountingRule.Field.ID + " = :" + AccAccountingRule.Field.ID, this.ruleId);
        selectQuery.addField(AccAccountingRule.Field.ID);
        selectQuery.addField(AccAccountingRule.Field.NAME);
        selectQuery.addField(AccAccountingRule.Field.DESCRIPTION);
        selectQuery.addField(AccAccountingRule.Field.OFFICE_ID);
        selectQuery.addField(AccAccountingRule.Field.DEBIT_ACCOUNT_ID);
        selectQuery.addField(AccAccountingRule.Field.ALLOW_MULTIPLE_DEBITS);
        selectQuery.addField(AccAccountingRule.Field.ALLOW_MULTIPLE_CREDITS);
        selectQuery.addField(AccAccountingRule.Field.CREDIT_ACCOUNT_ID);
        Map<String, Object> ruleObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.ruleNameValue = (String) ruleObject.get(AccAccountingRule.Field.NAME);
        this.descriptionValue = (String) ruleObject.get(AccAccountingRule.Field.DESCRIPTION);

        selectQuery = new SelectQuery(MOffice.NAME);
        selectQuery.addField(MOffice.Field.ID);
        selectQuery.addField(MOffice.Field.NAME + " as text");
        selectQuery.addWhere(MOffice.Field.ID + " = :" + MOffice.Field.ID, ruleObject.get(AccAccountingRule.Field.OFFICE_ID));
        this.officeValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        selectQuery = new SelectQuery(AccGLAccount.NAME);
        selectQuery.addField(AccGLAccount.Field.ID);
        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, ruleObject.get(AccAccountingRule.Field.DEBIT_ACCOUNT_ID));
        this.debitAccountValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        if (this.debitAccountValue != null) {
            this.debitRuleTypeValue = FIXED;
        } else {
            this.debitRuleTypeValue = TAG;
        }

        selectQuery = new SelectQuery(AccRuleTags.NAME);
        selectQuery.addField(MCodeValue.NAME + "." + MCodeValue.Field.ID);
        selectQuery.addField(MCodeValue.NAME + "." + MCodeValue.Field.CODE_VALUE + " as text");
        selectQuery.addJoin("INNER JOIN " + MCodeValue.NAME + " ON " + AccRuleTags.NAME + "." + AccRuleTags.Field.TAG_ID + " = " + MCodeValue.NAME + "." + MCodeValue.Field.ID);
        selectQuery.addWhere(AccRuleTags.NAME + "." + AccRuleTags.Field.ACC_TYPE_ENUM + " = " + RuleBrowsePage.DEBIT);
        selectQuery.addWhere(AccRuleTags.NAME + "." + AccRuleTags.Field.ACC_RULE_ID + " = :" + AccRuleTags.Field.ACC_RULE_ID, ruleObject.get(AccAccountingRule.Field.ID));
        this.debitTagValue = named.query(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        selectQuery = new SelectQuery(AccRuleTags.NAME);
        selectQuery.addField(MCodeValue.NAME + "." + MCodeValue.Field.ID);
        selectQuery.addField(MCodeValue.NAME + "." + MCodeValue.Field.CODE_VALUE + " as text");
        selectQuery.addJoin("INNER JOIN " + MCodeValue.NAME + " ON " + AccRuleTags.NAME + "." + AccRuleTags.Field.TAG_ID + " = " + MCodeValue.NAME + "." + MCodeValue.Field.ID);
        selectQuery.addWhere(AccRuleTags.NAME + "." + AccRuleTags.Field.ACC_TYPE_ENUM + " = " + RuleBrowsePage.CREDIT);
        selectQuery.addWhere(AccRuleTags.NAME + "." + AccRuleTags.Field.ACC_RULE_ID + " = :" + AccRuleTags.Field.ACC_RULE_ID, ruleObject.get(AccAccountingRule.Field.ID));
        this.creditTagValue = named.query(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        this.multipleDebitValue = (Boolean) ruleObject.get(AccAccountingRule.Field.ALLOW_MULTIPLE_DEBITS);
        this.multipleCreditValue = (Boolean) ruleObject.get(AccAccountingRule.Field.ALLOW_MULTIPLE_CREDITS);

        selectQuery = new SelectQuery(AccGLAccount.NAME);
        selectQuery.addField(AccGLAccount.Field.ID);
        selectQuery.addField(AccGLAccount.Field.NAME + " as text");
        selectQuery.addWhere(AccGLAccount.Field.ID + " = :" + AccGLAccount.Field.ID, ruleObject.get(AccAccountingRule.Field.CREDIT_ACCOUNT_ID));
        this.creditAccountValue = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), Option.MAPPER);

        if (this.creditAccountValue != null) {
            this.creditRuleTypeValue = FIXED;
        } else {
            this.creditRuleTypeValue = TAG;
        }

        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
        this.debitRuleTypeProvider = Arrays.asList(FIXED, TAG);
        this.creditRuleTypeProvider = Arrays.asList(FIXED, TAG);

        this.debitAccountProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.debitAccountProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.debitTagProvider = new MultipleChoiceProvider(MCodeValue.NAME, MCodeValue.Field.ID, MCodeValue.Field.CODE_VALUE);
        this.debitTagProvider.applyWhere("code_id", MCodeValue.Field.CODE_ID + " IN (7,8,9,10,11)");

        this.creditAccountProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.creditAccountProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.creditTagProvider = new MultipleChoiceProvider(MCodeValue.NAME, MCodeValue.Field.ID, MCodeValue.Field.CODE_VALUE);
        this.creditTagProvider.applyWhere("code_id", MCodeValue.Field.CODE_ID + " IN (7,8,9,10,11)");
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.ruleNameBlock = this.row1.newUIBlock("ruleNameBlock", Size.Six_6);
        this.ruleNameIContainer = this.ruleNameBlock.newUIContainer("ruleNameIContainer");
        this.ruleNameField = new TextField<>("ruleNameField", new PropertyModel<>(this, "ruleNameValue"));
        this.ruleNameIContainer.add(this.ruleNameField);
        this.ruleNameIContainer.newFeedback("ruleNameFeedback", this.ruleNameField);

        this.officeBlock = this.row1.newUIBlock("officeBlock", Size.Six_6);
        this.officeIContainer = this.officeBlock.newUIContainer("officeIContainer");
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
        this.officeIContainer.add(this.officeField);
        this.officeIContainer.newFeedback("officeFeedback", this.officeField);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.descriptionBlock = this.row2.newUIBlock("descriptionBlock", Size.Twelve_12);
        this.descriptionIContainer = this.descriptionBlock.newUIContainer("descriptionIContainer");
        this.descriptionField = new TextArea<>("descriptionField", new PropertyModel<>(this, "descriptionValue"));
        this.descriptionIContainer.add(this.descriptionField);
        this.descriptionIContainer.newFeedback("descriptionFeedback", this.descriptionField);

        this.row3 = UIRow.newUIRow("row3", this.form);

        this.debitRuleTypeBlock = this.row3.newUIBlock("debitRuleTypeBlock", Size.Four_4);
        this.debitRuleTypeIContainer = this.debitRuleTypeBlock.newUIContainer("debitRuleTypeIContainer");
        this.debitRuleTypeField = new DropDownChoice<>("debitRuleTypeField", new PropertyModel<>(this, "debitRuleTypeValue"), this.debitRuleTypeProvider);
        this.debitRuleTypeIContainer.add(this.debitRuleTypeField);
        this.debitRuleTypeIContainer.newFeedback("debitRuleTypeFeedback", this.debitRuleTypeField);

        this.debitAccountBlock = this.row3.newUIBlock("debitAccountBlock", Size.Four_4);
        this.debitAccountIContainer = this.debitAccountBlock.newUIContainer("debitAccountIContainer");
        this.debitAccountField = new Select2SingleChoice<>("debitAccountField", new PropertyModel<>(this, "debitAccountValue"), this.debitAccountProvider);
        this.debitAccountIContainer.add(this.debitAccountField);
        this.debitAccountIContainer.newFeedback("debitAccountFeedback", this.debitAccountField);
        this.debitTagIContainer = this.debitAccountBlock.newUIContainer("debitTagIContainer");
        this.debitTagField = new Select2MultipleChoice<>("debitTagField", new PropertyModel<>(this, "debitTagValue"), this.debitTagProvider);
        this.debitTagIContainer.add(this.debitTagField);
        this.debitTagIContainer.newFeedback("debitTagFeedback", this.debitTagField);

        this.multipleDebitBlock = this.row3.newUIBlock("multipleDebitBlock", Size.Four_4);
        this.multipleDebitIContainer = this.multipleDebitBlock.newUIContainer("multipleDebitIContainer");
        this.multipleDebitField = new CheckBox("multipleDebitField", new PropertyModel<>(this, "multipleDebitValue"));
        this.multipleDebitIContainer.add(this.multipleDebitField);
        this.multipleDebitIContainer.newFeedback("multipleDebitFeedback", this.multipleDebitField);

        this.row4 = UIRow.newUIRow("row4", this.form);

        this.creditRuleTypeBlock = this.row4.newUIBlock("creditRuleTypeBlock", Size.Four_4);
        this.creditRuleTypeIContainer = this.creditRuleTypeBlock.newUIContainer("creditRuleTypeIContainer");
        this.creditRuleTypeField = new DropDownChoice<>("creditRuleTypeField", new PropertyModel<>(this, "creditRuleTypeValue"), this.creditRuleTypeProvider);
        this.creditRuleTypeIContainer.add(this.creditRuleTypeField);
        this.creditRuleTypeIContainer.newFeedback("creditRuleTypeFeedback", this.creditRuleTypeField);

        this.creditAccountBlock = this.row4.newUIBlock("creditAccountBlock", Size.Four_4);
        this.creditAccountIContainer = this.creditAccountBlock.newUIContainer("creditAccountIContainer");
        this.creditAccountField = new Select2SingleChoice<>("creditAccountField", new PropertyModel<>(this, "creditAccountValue"), this.creditAccountProvider);
        this.creditAccountIContainer.add(this.creditAccountField);
        this.creditAccountIContainer.newFeedback("creditAccountFeedback", this.creditAccountField);
        this.creditTagIContainer = this.creditAccountBlock.newUIContainer("creditTagIContainer");
        this.creditTagField = new Select2MultipleChoice<>("creditTagField", new PropertyModel<>(this, "creditTagValue"), this.creditTagProvider);
        this.creditTagIContainer.add(this.creditTagField);
        this.creditTagIContainer.newFeedback("creditTagFeedback", this.creditTagField);

        this.multipleCreditBlock = this.row4.newUIBlock("multipleCreditBlock", Size.Four_4);
        this.multipleCreditIContainer = this.multipleCreditBlock.newUIContainer("multipleCreditIContainer");
        this.multipleCreditField = new CheckBox("multipleCreditField", new PropertyModel<>(this, "multipleCreditValue"));
        this.multipleCreditIContainer.add(this.multipleCreditField);
        this.multipleCreditIContainer.newFeedback("multipleCreditFeedback", this.multipleCreditField);
    }

    @Override
    protected void configureMetaData() {
        this.debitRuleTypeField.setRequired(true);
        this.creditRuleTypeField.setRequired(true);
        this.debitRuleTypeField.add(new OnChangeAjaxBehavior(this::debitRuleTypeFieldUpdate));
        this.creditRuleTypeField.add(new OnChangeAjaxBehavior(this::creditRuleTypeFieldUpdate));
        this.ruleNameField.setRequired(true);
        this.officeField.setRequired(true);
        this.descriptionField.setRequired(true);
        this.multipleDebitField.setRequired(true);
        this.multipleCreditField.setRequired(true);

        creditRuleTypeFieldUpdate(null);

        debitRuleTypeFieldUpdate(null);
    }

    protected boolean creditRuleTypeFieldUpdate(AjaxRequestTarget target) {
        if (FIXED.equals(this.creditRuleTypeValue)) {
            this.creditAccountIContainer.setVisible(true);
            this.creditTagIContainer.setVisible(false);
            this.multipleCreditIContainer.setVisible(false);
        } else if (TAG.equals(this.creditRuleTypeValue)) {
            this.creditAccountIContainer.setVisible(false);
            this.creditTagIContainer.setVisible(true);
            this.multipleCreditIContainer.setVisible(true);
        } else {
            this.creditAccountIContainer.setVisible(false);
            this.creditTagIContainer.setVisible(false);
            this.multipleCreditIContainer.setVisible(false);
        }

        if (target != null) {
            target.add(this.creditRuleTypeBlock);
            target.add(this.creditAccountBlock);
            target.add(this.multipleCreditBlock);
        }

        return false;
    }

    protected boolean debitRuleTypeFieldUpdate(AjaxRequestTarget target) {
        if (FIXED.equals(this.debitRuleTypeValue)) {
            this.debitAccountIContainer.setVisible(true);
            this.debitTagIContainer.setVisible(false);
            this.multipleDebitIContainer.setVisible(false);
        } else if (TAG.equals(this.debitRuleTypeValue)) {
            this.debitAccountIContainer.setVisible(false);
            this.debitTagIContainer.setVisible(true);
            this.multipleDebitIContainer.setVisible(true);
        } else {
            this.debitAccountIContainer.setVisible(false);
            this.debitTagIContainer.setVisible(false);
            this.multipleDebitIContainer.setVisible(false);
        }

        if (target != null) {
            target.add(this.debitRuleTypeBlock);
            target.add(this.debitAccountBlock);
            target.add(this.multipleDebitBlock);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        AccountRuleBuilder builder = new AccountRuleBuilder();
        builder.withId(this.ruleId);
        builder.withName(this.ruleNameValue);
        builder.withDescription(this.descriptionValue);
        if (this.officeValue != null) {
            builder.withOfficeId(this.officeValue.getId());
        }

        if (FIXED.equals(this.debitRuleTypeValue)) {
            builder.withAccountToDebit(this.debitAccountValue.getId());
        } else if (TAG.equals(this.debitRuleTypeValue)) {
            for (Option tag : this.debitTagValue) {
                builder.withDebitTags(tag.getId());
            }
            builder.withAllowMultipleDebitEntries(this.multipleDebitValue == null ? false : this.multipleDebitValue);
        }

        if (FIXED.equalsIgnoreCase(this.creditRuleTypeValue)) {
            builder.withAccountToCredit(this.creditAccountValue.getId());
        } else if (TAG.equalsIgnoreCase(this.creditRuleTypeValue)) {
            for (Option tag : this.creditTagValue) {
                builder.withCreditTags(tag.getId());
            }
            builder.withAllowMultipleCreditEntries(this.multipleCreditValue == null ? false : this.multipleCreditValue);
        }

        JsonNode node = AccountingRuleHelper.update((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(RuleBrowsePage.class);

    }

}
