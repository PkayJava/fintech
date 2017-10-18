package com.angkorteam.fintech.pages.client.client;

import java.util.Arrays;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.widget.client.AccountPreviewCharge;
import com.angkorteam.fintech.widget.client.AccountPreviewTransaction;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingAccountPreviewPage extends Page {

    protected String clientId;
    protected String accountId;

    protected String interestEarnedValue;
    protected Label interestEarnedField;

    protected String interestValue;
    protected Label interestField;

    protected String nominalInterestRateValue;
    protected Label nominalInterestRateField;

    protected String interestCompoundingPeriodValue;
    protected Label interestCompoundingPeriodField;

    protected String interestPostingPeriodValue;
    protected Label interestPostingPeriodField;

    protected String interestCalculatedUsingValue;
    protected Label interestCalculatedUsingField;

    protected String lastActiveTransactionDateValue;
    protected Label lastActiveTransactionDateField;

    protected String interestRecalculationDateValue;
    protected Label interestRecalculationDateField;

    protected String activationDateValue;
    protected Label activationDateField;

    protected String officerValue;
    protected Label officerField;

    protected String externalIdValue;
    protected Label externalIdField;

    protected String currencyValue;
    protected Label currencyField;

    protected String detailNominalInterestRateValue;
    protected Label detailNominalInterestRateField;

    protected String totalDepositValue;
    protected Label totalDepositField;

    protected String daysInYearValue;
    protected Label daysInYearField;

    protected String totalInterestEarnedValue;
    protected Label totalInterestEarnedField;

    protected AjaxTabbedPanel<ITab> tab;

    protected BookmarkablePageLink<Void> closeLink;
    protected BookmarkablePageLink<Void> depositLink;
    protected BookmarkablePageLink<Void> withdrawLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        this.interestEarnedField = new Label("interestEarnedField", new PropertyModel<>(this, "interestEarnedValue"));
        add(this.interestEarnedField);

        this.interestField = new Label("interestField", new PropertyModel<>(this, "interestValue"));
        add(this.interestField);

        this.nominalInterestRateField = new Label("nominalInterestRateField", new PropertyModel<>(this, "nominalInterestRateValue"));
        add(this.nominalInterestRateField);

        this.interestCompoundingPeriodField = new Label("interestCompoundingPeriodField", new PropertyModel<>(this, "interestCompoundingPeriodValue"));
        add(this.interestCompoundingPeriodField);

        this.interestPostingPeriodField = new Label("interestPostingPeriodField", new PropertyModel<>(this, "interestPostingPeriodValue"));
        add(this.interestPostingPeriodField);

        this.interestCalculatedUsingField = new Label("interestCalculatedUsingField", new PropertyModel<>(this, "interestCalculatedUsingValue"));
        add(this.interestCalculatedUsingField);

        this.lastActiveTransactionDateField = new Label("lastActiveTransactionDateField", new PropertyModel<>(this, "lastActiveTransactionDateValue"));
        add(this.lastActiveTransactionDateField);

        this.interestRecalculationDateField = new Label("interestRecalculationDateField", new PropertyModel<>(this, "interestRecalculationDateValue"));
        add(this.interestRecalculationDateField);

        this.activationDateField = new Label("activationDateField", new PropertyModel<>(this, "activationDateValue"));
        add(this.activationDateField);

        this.officerField = new Label("officerField", new PropertyModel<>(this, "officerValue"));
        add(this.officerField);

        this.externalIdField = new Label("externalIdField", new PropertyModel<>(this, "externalIdValue"));
        add(this.externalIdField);

        this.currencyField = new Label("currencyField", new PropertyModel<>(this, "currencyValue"));
        add(this.currencyField);

        this.detailNominalInterestRateField = new Label("detailNominalInterestRateField", new PropertyModel<>(this, "detailNominalInterestRateValue"));
        add(this.detailNominalInterestRateField);

        this.totalDepositField = new Label("totalDepositField", new PropertyModel<>(this, "totalDepositValue"));
        add(this.totalDepositField);

        this.daysInYearField = new Label("daysInYearField", new PropertyModel<>(this, "daysInYearValue"));
        add(this.daysInYearField);

        this.totalInterestEarnedField = new Label("totalInterestEarnedField", new PropertyModel<>(this, "totalInterestEarnedValue"));
        add(this.totalInterestEarnedField);

        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new AccountPreviewTransaction(this), new AccountPreviewCharge(this)));
        add(this.tab);

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        parameters.add("accountId", this.accountId);

        this.closeLink = new BookmarkablePageLink<>("closeLink", SavingAccountClosePage.class, parameters);
        add(this.closeLink);

        this.depositLink = new BookmarkablePageLink<>("depositLink", SavingAccountDepositPage.class, parameters);
        add(this.depositLink);

        this.withdrawLink = new BookmarkablePageLink<>("withdrawLink", SavingAccountWithdrawPage.class, parameters);
        add(this.withdrawLink);
    }

    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
        this.accountId = getPageParameters().get("accountId").toString();
    }

}
