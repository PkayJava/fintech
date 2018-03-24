package com.angkorteam.fintech.pages.account;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.builder.FinancialActivityBuilder;
import com.angkorteam.fintech.dto.constant.FinancialActivityTypeEnum;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.AccountUsage;
import com.angkorteam.fintech.helper.FinancialActivityHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.FinancialActivityProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.ajax.form.OnChangeAjaxBehavior;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FinancialActivityCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock financialActivityBlock;
    protected WebMarkupContainer financialActivityIContainer;
    protected FinancialActivityProvider financialActivityProvider;
    protected Option financialActivityValue;
    protected Select2SingleChoice<Option> financialActivityField;
    protected TextFeedbackPanel financialActivityFeedback;

    protected WebMarkupBlock accountBlock;
    protected WebMarkupContainer accountIContainer;
    protected SingleChoiceProvider accountProvider;
    protected Option accountValue;
    protected Select2SingleChoice<Option> accountField;
    protected TextFeedbackPanel accountFeedback;

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
            breadcrumb.setLabel("Financial Activity Mapping");
            breadcrumb.setPage(FinancialActivityBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Financial Activity Mapping Create");
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

        this.closeLink = new BookmarkablePageLink<>("closeLink", FinancialActivityBrowsePage.class);
        this.form.add(this.closeLink);

        initFinancialActivityBlock();

        initAccountBlock();
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initFinancialActivityBlock() {
        this.financialActivityBlock = new WebMarkupBlock("financialActivityBlock", Size.Twelve_12);
        this.form.add(this.financialActivityBlock);
        this.financialActivityIContainer = new WebMarkupContainer("financialActivityIContainer");
        this.financialActivityBlock.add(this.financialActivityIContainer);
        this.financialActivityProvider = new FinancialActivityProvider();
        this.financialActivityField = new Select2SingleChoice<>("financialActivityField", new PropertyModel<>(this, "financialActivityValue"), this.financialActivityProvider);
        this.financialActivityIContainer.add(this.financialActivityField);
        this.financialActivityFeedback = new TextFeedbackPanel("financialActivityFeedback", this.financialActivityField);
        this.financialActivityIContainer.add(this.financialActivityFeedback);
        this.financialActivityField.add(new OnChangeAjaxBehavior(this::financialActivityFieldUpdate));
    }

    protected void initAccountBlock() {
        this.accountBlock = new WebMarkupBlock("accountBlock", Size.Twelve_12);
        this.form.add(this.accountBlock);
        this.accountIContainer = new WebMarkupContainer("accountIContainer");
        this.accountBlock.add(this.accountIContainer);
        this.accountProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accountProvider.applyWhere("usage", AccGLAccount.Field.ACCOUNT_USAGE + " = '" + AccountUsage.Detail.getLiteral() + "'");
        this.accountProvider.setDisabled(true);
        this.accountField = new Select2SingleChoice<>("accountField", new PropertyModel<>(this, "accountValue"), this.accountProvider);
        this.accountIContainer.add(this.accountField);
        this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
        this.accountIContainer.add(this.accountFeedback);
    }

    protected boolean financialActivityFieldUpdate(AjaxRequestTarget target) {
        if (this.financialActivityValue == null) {
            this.accountValue = null;
            this.accountProvider.setDisabled(true);
        } else {
            AccountType classification_enum = null;
            FinancialActivityTypeEnum a = null;
            if (this.financialActivityValue != null) {
                a = FinancialActivityTypeEnum.valueOf(this.financialActivityValue.getId());
                classification_enum = a.getAccountType();
            }
            this.accountValue = null;
            this.accountProvider.setDisabled(false);
            this.accountProvider.applyWhere("classification_enum", AccGLAccount.Field.CLASSIFICATION_ENUM + " = '" + classification_enum.getLiteral() + "'");
        }
        if (target != null) {
            target.add(this.form);
        }
        return false;
    }

    protected void saveButtonSubmit(Button button) {
        FinancialActivityBuilder builder = new FinancialActivityBuilder();
        if (this.financialActivityValue != null) {
            builder.withFinancialActivityId(FinancialActivityTypeEnum.valueOf(this.financialActivityValue.getId()));
        }
        if (this.accountValue != null) {
            builder.withGlAccountId(this.accountValue.getId());
        }
        JsonNode node = FinancialActivityHelper.create((Session) getSession(), builder.build());

        if (reportError(node)) {
            return;
        }
        setResponsePage(FinancialActivityBrowsePage.class);
    }

}
