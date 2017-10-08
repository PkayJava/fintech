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
public class FinancialActivityCreatePage extends Page {

    private Form<Void> form;
    private Button saveButton;
    private BookmarkablePageLink<Void> closeLink;

    private FinancialActivityProvider financialActivityProvider;
    private Option financialActivityValue;
    private Select2SingleChoice<Option> financialActivityField;
    private TextFeedbackPanel financialActivityFeedback;

    private SingleChoiceProvider accountProvider;
    private Option accountValue;
    private Select2SingleChoice<Option> accountField;
    private TextFeedbackPanel accountFeedback;

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
            breadcrumb.setLabel("Financial Activity Mapping");
            breadcrumb.setPage(FinancialActivityBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Financial Activity Mapping Create");
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

        this.closeLink = new BookmarkablePageLink<>("closeLink", FinancialActivityBrowsePage.class);
        this.form.add(this.closeLink);

        this.financialActivityProvider = new FinancialActivityProvider();
        this.financialActivityField = new Select2SingleChoice<>("financialActivityField", 0, new PropertyModel<>(this, "financialActivityValue"), this.financialActivityProvider);
        this.form.add(this.financialActivityField);
        this.financialActivityFeedback = new TextFeedbackPanel("financialActivityFeedback", this.financialActivityField);
        this.form.add(this.financialActivityFeedback);
        this.financialActivityField.add(new OnChangeAjaxBehavior(this::financialActivityFieldUpdate, this::financialActivityFieldError));

        this.accountProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.accountProvider.applyWhere("usage", AccountUsage.Detail.getLiteral());
        this.accountProvider.setDisabled(true);
        this.accountField = new Select2SingleChoice<>("accountField", 0, new PropertyModel<>(this, "accountValue"), this.accountProvider);
        this.form.add(this.accountField);
        this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
        this.form.add(this.accountFeedback);
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
            this.accountProvider.applyWhere("classification_enum", "classification_enum = " + classification_enum.getLiteral());
        }
        target.add(this.form);
        return false;
    }

    protected boolean financialActivityFieldError(AjaxRequestTarget target, RuntimeException e) {
        if (e != null) {
            throw e;
        }
        target.add(this.form);
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
        JsonNode node = null;
        try {
            node = FinancialActivityHelper.create((Session) getSession(), builder.build());
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(FinancialActivityBrowsePage.class);
    }

}
