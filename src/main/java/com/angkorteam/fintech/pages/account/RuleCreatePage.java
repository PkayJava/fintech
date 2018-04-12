package com.angkorteam.fintech.pages.account;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import com.angkorteam.fintech.widget.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.ddl.MCodeValue;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.AccountRuleBuilder;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.helper.AccountingRuleHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.framework.models.PageBreadcrumb;
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
public class RuleCreatePage extends Page {

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
            breadcrumb.setLabel("Accounting Rule Create");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
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
        this.creditTagProvider = new MultipleChoiceProvider(MCodeValue.NAME, MCodeValue.Field.ID, MCodeValue.Field.CODE_VALUE);
        this.creditTagProvider.applyWhere("code_id", MCodeValue.Field.CODE_ID + " IN (7,8,9,10,11)");
        this.creditTagField = new Select2MultipleChoice<>("creditTagField", new PropertyModel<>(this, "creditTagValue"), this.creditTagProvider);
        this.creditTagIContainer.add(this.creditTagField);
        this.creditTagFeedback = new TextFeedbackPanel("creditTagFeedback", this.creditTagField);
        this.creditTagIContainer.add(this.creditTagFeedback);
    }

    protected void initCreditAccountBlock() {
        this.creditAccountBlock = new WebMarkupBlock("creditAccountBlock", Size.Four_4);
        this.form.add(this.creditAccountBlock);
        this.creditAccountIContainer = new WebMarkupContainer("creditAccountIContainer");
        this.creditAccountBlock.add(this.creditAccountIContainer);
        this.creditAccountProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.creditAccountProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.creditAccountField = new Select2SingleChoice<>("creditAccountField", new PropertyModel<>(this, "creditAccountValue"), this.creditAccountProvider);
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
        this.debitTagProvider = new MultipleChoiceProvider(MCodeValue.NAME, MCodeValue.Field.ID, MCodeValue.Field.CODE_VALUE);
        this.debitTagProvider.applyWhere("code_id", MCodeValue.Field.CODE_ID + " IN (7,8,9,10,11)");
        this.debitTagField = new Select2MultipleChoice<>("debitTagField", new PropertyModel<>(this, "debitTagValue"), this.debitTagProvider);
        this.debitTagIContainer.add(this.debitTagField);
        this.debitTagFeedback = new TextFeedbackPanel("debitTagFeedback", this.debitTagField);
        this.debitTagIContainer.add(this.debitTagFeedback);
    }

    protected void initDebitAccountBlock() {
        this.debitAccountBlock = new WebMarkupBlock("debitAccountBlock", Size.Four_4);
        this.form.add(this.debitAccountBlock);
        this.debitAccountIContainer = new WebMarkupContainer("debitAccountIContainer");
        this.debitAccountBlock.add(this.debitAccountIContainer);
        this.debitAccountProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.debitAccountProvider.applyWhere("account_usage", AccGLAccount.Field.ACCOUNT_USAGE + " = " + AccountUsage.Detail.getLiteral());
        this.debitAccountField = new Select2SingleChoice<>("debitAccountField", new PropertyModel<>(this, "debitAccountValue"), this.debitAccountProvider);
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
        this.officeProvider = new SingleChoiceProvider(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
        this.officeField = new Select2SingleChoice<>("officeField", new PropertyModel<>(this, "officeValue"), this.officeProvider);
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
    protected void configureMetaData() {
    }

    protected void saveButtonSubmit(Button button) {
        AccountRuleBuilder builder = new AccountRuleBuilder();
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

        JsonNode node = AccountingRuleHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(RuleBrowsePage.class);

    }

}
