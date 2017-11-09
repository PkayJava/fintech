package com.angkorteam.fintech.pages.client.client;

import java.util.Arrays;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.widget.client.AccountPreviewCharge;
import com.angkorteam.fintech.widget.client.AccountPreviewTransaction;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingAccountPreviewPage extends DeprecatedPage {

    protected String clientId;
    protected String accountId;

    protected String interestEarnedValue;
    protected Label interestEarnedView;

    protected String interestValue;
    protected Label interestView;

    protected String nominalInterestRateValue;
    protected Label nominalInterestRateView;

    protected String interestCompoundingPeriodValue;
    protected Label interestCompoundingPeriodView;

    protected String interestPostingPeriodValue;
    protected Label interestPostingPeriodView;

    protected String interestCalculatedUsingValue;
    protected Label interestCalculatedUsingView;

    protected String lastActiveTransactionDateValue;
    protected Label lastActiveTransactionDateView;

    protected String interestRecalculationDateValue;
    protected Label interestRecalculationDateView;

    protected String activationDateValue;
    protected Label activationDateView;

    protected String officerValue;
    protected Label officerView;

    protected String externalIdValue;
    protected Label externalIdView;

    protected String currencyValue;
    protected Label currencyView;

    protected String detailNominalInterestRateValue;
    protected Label detailnominalInterestRateView;

    protected String totalDepositValue;
    protected Label totalDepositView;

    protected String daysInYearValue;
    protected Label daysInYearView;

    protected String totalInterestEarnedValue;
    protected Label totalinterestEarnedView;

    protected AjaxTabbedPanel<ITab> tab;

    protected BookmarkablePageLink<Void> closeLink;
    protected BookmarkablePageLink<Void> depositLink;
    protected BookmarkablePageLink<Void> withdrawLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        initData();

        this.interestEarnedView = new Label("interestEarnedView", new PropertyModel<>(this, "interestEarnedValue"));
        add(this.interestEarnedView);

        this.interestView = new Label("interestView", new PropertyModel<>(this, "interestValue"));
        add(this.interestView);

        this.nominalInterestRateView = new Label("nominalInterestRateView", new PropertyModel<>(this, "nominalInterestRateValue"));
        add(this.nominalInterestRateView);

        this.interestCompoundingPeriodView = new Label("interestCompoundingPeriodView", new PropertyModel<>(this, "interestCompoundingPeriodValue"));
        add(this.interestCompoundingPeriodView);

        this.interestPostingPeriodView = new Label("interestPostingPeriodView", new PropertyModel<>(this, "interestPostingPeriodValue"));
        add(this.interestPostingPeriodView);

        this.interestCalculatedUsingView = new Label("interestCalculatedUsingView", new PropertyModel<>(this, "interestCalculatedUsingValue"));
        add(this.interestCalculatedUsingView);

        this.lastActiveTransactionDateView = new Label("lastActiveTransactionDateView", new PropertyModel<>(this, "lastActiveTransactionDateValue"));
        add(this.lastActiveTransactionDateView);

        this.interestRecalculationDateView = new Label("interestRecalculationDateView", new PropertyModel<>(this, "interestRecalculationDateValue"));
        add(this.interestRecalculationDateView);

        this.activationDateView = new Label("activationDateView", new PropertyModel<>(this, "activationDateValue"));
        add(this.activationDateView);

        this.officerView = new Label("officerView", new PropertyModel<>(this, "officerValue"));
        add(this.officerView);

        this.externalIdView = new Label("externalIdView", new PropertyModel<>(this, "externalIdValue"));
        add(this.externalIdView);

        this.currencyView = new Label("currencyView", new PropertyModel<>(this, "currencyValue"));
        add(this.currencyView);

        this.detailnominalInterestRateView = new Label("detailnominalInterestRateView", new PropertyModel<>(this, "detailNominalInterestRateValue"));
        add(this.detailnominalInterestRateView);

        this.totalDepositView = new Label("totalDepositView", new PropertyModel<>(this, "totalDepositValue"));
        add(this.totalDepositView);

        this.daysInYearView = new Label("daysInYearView", new PropertyModel<>(this, "daysInYearValue"));
        add(this.daysInYearView);

        this.totalinterestEarnedView = new Label("totalinterestEarnedView", new PropertyModel<>(this, "totalInterestEarnedValue"));
        add(this.totalinterestEarnedView);

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
