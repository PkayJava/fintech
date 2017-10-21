package com.angkorteam.fintech.pages.account;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

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
public class FinancialActivityModifyPage extends Page {

    protected String financialActivityId;

    protected Form<Void> form;
    protected Button saveButton;
    protected BookmarkablePageLink<Void> closeLink;

    protected FinancialActivityProvider financialActivityProvider;
    protected Option financialActivityValue;
    protected Select2SingleChoice<Option> financialActivityField;
    protected TextFeedbackPanel financialActivityFeedback;

    protected SingleChoiceProvider accountProvider;
    protected Option accountValue;
    protected Select2SingleChoice<Option> accountField;
    protected TextFeedbackPanel accountFeedback;

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
            breadcrumb.setLabel("Financial Activity Mapping");
            breadcrumb.setPage(FinancialActivityBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Financial Activity Mapping Modify");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();
        this.financialActivityId = parameters.get("financialActivityId").toString("");

        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> financialActivityObject = jdbcTemplate.queryForMap("select * from acc_gl_financial_activity_account where id = ?", financialActivityId);

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
        this.financialActivityValue = FinancialActivityTypeEnum.optionLiteral(String.valueOf(financialActivityObject.get("financial_activity_type")));

        this.financialActivityField.add(new OnChangeAjaxBehavior(this::financialActivityFieldUpdate, this::financialActivityFieldError));

        if (financialActivityObject.get("gl_account_id") != null) {
            this.accountValue = jdbcTemplate.queryForObject("select id, name text from acc_gl_account where id = ?", Option.MAPPER, financialActivityObject.get("gl_account_id"));
        }
        this.accountProvider = new SingleChoiceProvider("acc_gl_account", "id", "name");
        this.accountProvider.applyWhere("usage", AccountUsage.Detail.getLiteral());
        this.accountProvider.setDisabled(true);
        this.accountField = new Select2SingleChoice<>("accountField", 0, new PropertyModel<>(this, "accountValue"), this.accountProvider);
        this.form.add(this.accountField);
        this.accountFeedback = new TextFeedbackPanel("accountFeedback", this.accountField);
        this.form.add(this.accountFeedback);

        financialActivityFieldUpdate(null);
    }

    protected boolean financialActivityFieldUpdate(AjaxRequestTarget target) {
        if (this.financialActivityValue == null) {
            this.accountValue = null;
            this.accountProvider.setDisabled(true);
        } else {
            FinancialActivityTypeEnum a = FinancialActivityTypeEnum.valueOf(this.financialActivityValue.getId());
            AccountType classification_enum = a.getAccountType();
            this.accountProvider.setDisabled(false);
            this.accountProvider.applyWhere("classification_enum", "classification_enum = " + classification_enum.getLiteral());
        }
        if (target != null) {
            target.add(this.form);
        }
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
        builder.withId(this.financialActivityId);
        if (this.financialActivityValue != null) {
            builder.withFinancialActivityId(FinancialActivityTypeEnum.valueOf(this.financialActivityValue.getId()));
        }
        if (this.accountValue != null) {
            builder.withGlAccountId(this.accountValue.getId());
        }
        JsonNode node = null;
        try {
            node = FinancialActivityHelper.update((Session) getSession(), builder.build());
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
