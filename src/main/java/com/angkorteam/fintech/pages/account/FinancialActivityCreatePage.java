package com.angkorteam.fintech.pages.account;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
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
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.FinancialActivityProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.framework.models.PageBreadcrumb;
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
public class FinancialActivityCreatePage extends Page {

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock financialActivityBlock;
    protected UIContainer financialActivityIContainer;
    protected FinancialActivityProvider financialActivityProvider;
    protected Option financialActivityValue;
    protected Select2SingleChoice<Option> financialActivityField;

    protected UIBlock row1Block1;

    protected UIRow row2;

    protected UIBlock accountBlock;
    protected UIContainer accountIContainer;
    protected SingleChoiceProvider accountProvider;
    protected Option accountValue;
    protected Select2SingleChoice<Option> accountField;

    protected UIBlock row2Block1;

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
        this.financialActivityProvider = new FinancialActivityProvider();

        this.accountProvider = new SingleChoiceProvider(AccGLAccount.NAME, AccGLAccount.Field.ID, AccGLAccount.Field.NAME);
        this.accountProvider.applyWhere("usage", AccGLAccount.Field.ACCOUNT_USAGE + " = '" + AccountUsage.Detail.getLiteral() + "'");
        this.accountProvider.setDisabled(true);
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

        this.row1 = UIRow.newUIRow("row1", this.form);

        this.financialActivityBlock = this.row1.newUIBlock("financialActivityBlock", Size.Four_4);
        this.financialActivityIContainer = this.financialActivityBlock.newUIContainer("financialActivityIContainer");
        this.financialActivityField = new Select2SingleChoice<>("financialActivityField", new PropertyModel<>(this, "financialActivityValue"), this.financialActivityProvider);
        this.financialActivityIContainer.add(this.financialActivityField);
        this.financialActivityIContainer.newFeedback("financialActivityFeedback", this.financialActivityField);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Eight_8);

        this.row2 = UIRow.newUIRow("row2", this.form);

        this.accountBlock = this.row2.newUIBlock("accountBlock", Size.Four_4);
        this.accountIContainer = this.accountBlock.newUIContainer("accountIContainer");
        this.accountField = new Select2SingleChoice<>("accountField", new PropertyModel<>(this, "accountValue"), this.accountProvider);
        this.accountIContainer.add(this.accountField);
        this.accountIContainer.newFeedback("accountFeedback", this.accountField);

        this.row2Block1 = this.row2.newUIBlock("row2Block1", Size.Eight_8);
    }

    @Override
    protected void configureMetaData() {
        this.financialActivityField.add(new OnChangeAjaxBehavior(this::financialActivityFieldUpdate));
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
