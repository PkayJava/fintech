package com.angkorteam.fintech.pages.client.common;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MGroup;
import com.angkorteam.fintech.ddl.MSavingsAccount;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.client.center.CenterBrowsePage;
import com.angkorteam.fintech.pages.client.center.CenterPreviewPage;
import com.angkorteam.fintech.pages.client.client.ClientBrowsePage;
import com.angkorteam.fintech.pages.client.client.ClientPreviewPage;
import com.angkorteam.fintech.pages.client.group.GroupBrowsePage;
import com.angkorteam.fintech.pages.client.group.GroupPreviewPage;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.fintech.widget.client.common.SavingAccountPreviewCharge;
import com.angkorteam.fintech.widget.client.common.SavingAccountPreviewTransaction;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.AjaxTabbedPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.tabs.ITab;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class SavingAccountPreviewPage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String savingId;
    protected String savingAccountNo;
    protected String clientDisplayName;
    protected String groupDisplayName;
    protected String centerDisplayName;

    protected WebMarkupBlock interestEarnedBlock;
    protected WebMarkupContainer interestEarnedVContainer;
    protected String interestEarnedValue;
    protected ReadOnlyView interestEarnedView;

    protected WebMarkupBlock interestBlock;
    protected WebMarkupContainer interestVContainer;
    protected String interestValue;
    protected ReadOnlyView interestView;

    protected WebMarkupBlock nominalInterestRateBlock;
    protected WebMarkupContainer nominalInterestRateVContainer;
    protected String nominalInterestRateValue;
    protected ReadOnlyView nominalInterestRateView;

    protected WebMarkupBlock interestCompoundingPeriodBlock;
    protected WebMarkupContainer interestCompoundingPeriodVContainer;
    protected String interestCompoundingPeriodValue;
    protected ReadOnlyView interestCompoundingPeriodView;

    protected WebMarkupBlock interestPostingPeriodBlock;
    protected WebMarkupContainer interestPostingPeriodVContainer;
    protected String interestPostingPeriodValue;
    protected ReadOnlyView interestPostingPeriodView;

    protected WebMarkupBlock interestCalculatedUsingBlock;
    protected WebMarkupContainer interestCalculatedUsingVContainer;
    protected String interestCalculatedUsingValue;
    protected ReadOnlyView interestCalculatedUsingView;

    protected WebMarkupBlock lastActiveTransactionDateBlock;
    protected WebMarkupContainer lastActiveTransactionDateVContainer;
    protected String lastActiveTransactionDateValue;
    protected ReadOnlyView lastActiveTransactionDateView;

    protected WebMarkupBlock interestRecalculationDateBlock;
    protected WebMarkupContainer interestRecalculationDateVContainer;
    protected String interestRecalculationDateValue;
    protected ReadOnlyView interestRecalculationDateView;

    protected WebMarkupBlock activationDateBlock;
    protected WebMarkupContainer activationDateVContainer;
    protected String activationDateValue;
    protected ReadOnlyView activationDateView;

    protected WebMarkupBlock officerBlock;
    protected WebMarkupContainer officerVContainer;
    protected String officerValue;
    protected ReadOnlyView officerView;

    protected WebMarkupBlock externalIdBlock;
    protected WebMarkupContainer externalIdVContainer;
    protected String externalIdValue;
    protected ReadOnlyView externalIdView;

    protected WebMarkupBlock currencyBlock;
    protected WebMarkupContainer currencyVContainer;
    protected String currencyValue;
    protected ReadOnlyView currencyView;

    protected WebMarkupBlock detailNominalInterestRateBlock;
    protected WebMarkupContainer detailNominalInterestRateVContainer;
    protected String detailNominalInterestRateValue;
    protected ReadOnlyView detailNominalInterestRateView;

    protected WebMarkupBlock totalDepositBlock;
    protected WebMarkupContainer totalDepositVContainer;
    protected String totalDepositValue;
    protected ReadOnlyView totalDepositView;

    protected WebMarkupBlock daysInYearBlock;
    protected WebMarkupContainer daysInYearVContainer;
    protected String daysInYearValue;
    protected ReadOnlyView daysInYearView;

    protected WebMarkupBlock totalInterestEarnedBlock;
    protected WebMarkupContainer totalInterestEarnedVContainer;
    protected String totalInterestEarnedValue;
    protected ReadOnlyView totalInterestEarnedView;

    protected AjaxTabbedPanel<ITab> tab;

    protected BookmarkablePageLink<Void> closeLink;
    protected BookmarkablePageLink<Void> depositLink;
    protected BookmarkablePageLink<Void> withdrawLink;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                breadcrumb.setLabel("Clients");
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setLabel("Groups");
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setLabel("Centers");
            }
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                breadcrumb.setLabel("Clients");
                breadcrumb.setPage(ClientBrowsePage.class);
            } else if (this.client == ClientEnum.Group) {
                breadcrumb.setLabel("Groups");
                breadcrumb.setPage(GroupBrowsePage.class);
            } else if (this.client == ClientEnum.Center) {
                breadcrumb.setLabel("Centers");
                breadcrumb.setPage(CenterBrowsePage.class);
            }
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            if (this.client == ClientEnum.Client) {
                parameters.add("clientId", this.clientId);
                breadcrumb.setLabel(this.clientDisplayName);
                breadcrumb.setPage(ClientPreviewPage.class);
            } else if (this.client == ClientEnum.Group) {
                parameters.add("groupId", this.groupId);
                breadcrumb.setLabel(this.groupDisplayName);
                breadcrumb.setPage(GroupPreviewPage.class);
            } else if (this.client == ClientEnum.Center) {
                parameters.add("centerId", this.centerId);
                breadcrumb.setLabel(this.centerDisplayName);
                breadcrumb.setPage(CenterPreviewPage.class);
            }
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel(this.savingAccountNo);
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {
        this.interestEarnedBlock = new WebMarkupBlock("interestEarnedBlock", Size.Six_6);
        add(this.interestEarnedBlock);
        this.interestEarnedVContainer = new WebMarkupContainer("interestEarnedVContainer");
        this.interestEarnedBlock.add(this.interestEarnedVContainer);
        this.interestEarnedView = new ReadOnlyView("interestEarnedView", new PropertyModel<>(this, "interestEarnedValue"));
        this.interestEarnedVContainer.add(this.interestEarnedView);

        this.interestBlock = new WebMarkupBlock("interestBlock", Size.Six_6);
        add(this.interestBlock);
        this.interestVContainer = new WebMarkupContainer("interestVContainer");
        this.interestBlock.add(this.interestVContainer);
        this.interestView = new ReadOnlyView("interestView", new PropertyModel<>(this, "interestValue"));
        this.interestVContainer.add(this.interestView);

        this.nominalInterestRateBlock = new WebMarkupBlock("nominalInterestRateBlock", Size.Six_6);
        add(this.nominalInterestRateBlock);
        this.nominalInterestRateVContainer = new WebMarkupContainer("nominalInterestRateVContainer");
        this.nominalInterestRateBlock.add(this.nominalInterestRateVContainer);
        this.nominalInterestRateView = new ReadOnlyView("nominalInterestRateView", new PropertyModel<>(this, "nominalInterestRateValue"));
        this.nominalInterestRateVContainer.add(this.nominalInterestRateView);

        this.interestCompoundingPeriodBlock = new WebMarkupBlock("interestCompoundingPeriodBlock", Size.Six_6);
        add(this.interestCompoundingPeriodBlock);
        this.interestCompoundingPeriodVContainer = new WebMarkupContainer("interestCompoundingPeriodVContainer");
        this.interestCompoundingPeriodBlock.add(this.interestCompoundingPeriodVContainer);
        this.interestCompoundingPeriodView = new ReadOnlyView("interestCompoundingPeriodView", new PropertyModel<>(this, "interestCompoundingPeriodValue"));
        this.interestCompoundingPeriodVContainer.add(this.interestCompoundingPeriodView);

        this.interestPostingPeriodBlock = new WebMarkupBlock("interestPostingPeriodBlock", Size.Six_6);
        add(this.interestPostingPeriodBlock);
        this.interestPostingPeriodVContainer = new WebMarkupContainer("interestPostingPeriodVContainer");
        this.interestPostingPeriodBlock.add(this.interestPostingPeriodVContainer);
        this.interestPostingPeriodView = new ReadOnlyView("interestPostingPeriodView", new PropertyModel<>(this, "interestPostingPeriodValue"));
        this.interestPostingPeriodVContainer.add(this.interestPostingPeriodView);

        this.interestCalculatedUsingBlock = new WebMarkupBlock("interestCalculatedUsingBlock", Size.Six_6);
        add(this.interestCalculatedUsingBlock);
        this.interestCalculatedUsingVContainer = new WebMarkupContainer("interestCalculatedUsingVContainer");
        this.interestCalculatedUsingBlock.add(this.interestCalculatedUsingVContainer);
        this.interestCalculatedUsingView = new ReadOnlyView("interestCalculatedUsingView", new PropertyModel<>(this, "interestCalculatedUsingValue"));
        this.interestCalculatedUsingVContainer.add(this.interestCalculatedUsingView);

        this.lastActiveTransactionDateBlock = new WebMarkupBlock("lastActiveTransactionDateBlock", Size.Six_6);
        add(this.lastActiveTransactionDateBlock);
        this.lastActiveTransactionDateVContainer = new WebMarkupContainer("lastActiveTransactionDateVContainer");
        this.lastActiveTransactionDateBlock.add(this.lastActiveTransactionDateVContainer);
        this.lastActiveTransactionDateView = new ReadOnlyView("lastActiveTransactionDateView", new PropertyModel<>(this, "lastActiveTransactionDateValue"));
        this.lastActiveTransactionDateVContainer.add(this.lastActiveTransactionDateView);

        this.interestRecalculationDateBlock = new WebMarkupBlock("interestRecalculationDateBlock", Size.Six_6);
        add(this.interestRecalculationDateBlock);
        this.interestRecalculationDateVContainer = new WebMarkupContainer("interestRecalculationDateVContainer");
        this.interestRecalculationDateBlock.add(this.interestRecalculationDateVContainer);
        this.interestRecalculationDateView = new ReadOnlyView("interestRecalculationDateView", new PropertyModel<>(this, "interestRecalculationDateValue"));
        this.interestRecalculationDateVContainer.add(this.interestRecalculationDateView);

        this.activationDateBlock = new WebMarkupBlock("activationDateBlock", Size.Six_6);
        add(this.activationDateBlock);
        this.activationDateVContainer = new WebMarkupContainer("activationDateVContainer");
        this.activationDateBlock.add(this.activationDateVContainer);
        this.activationDateView = new ReadOnlyView("activationDateView", new PropertyModel<>(this, "activationDateValue"));
        this.activationDateVContainer.add(this.activationDateView);

        this.officerBlock = new WebMarkupBlock("officerBlock", Size.Six_6);
        add(this.officerBlock);
        this.officerVContainer = new WebMarkupContainer("officerVContainer");
        this.officerBlock.add(this.officerVContainer);
        this.officerView = new ReadOnlyView("officerView", new PropertyModel<>(this, "officerValue"));
        this.officerVContainer.add(this.officerView);

        this.externalIdBlock = new WebMarkupBlock("externalIdBlock", Size.Six_6);
        add(this.externalIdBlock);
        this.externalIdVContainer = new WebMarkupContainer("externalIdVContainer");
        this.externalIdBlock.add(this.externalIdVContainer);
        this.externalIdView = new ReadOnlyView("externalIdView", new PropertyModel<>(this, "externalIdValue"));
        this.externalIdVContainer.add(this.externalIdView);

        this.currencyBlock = new WebMarkupBlock("currencyBlock", Size.Six_6);
        add(this.currencyBlock);
        this.currencyVContainer = new WebMarkupContainer("currencyVContainer");
        this.currencyBlock.add(this.currencyVContainer);
        this.currencyView = new ReadOnlyView("currencyView", new PropertyModel<>(this, "currencyValue"));
        this.currencyVContainer.add(this.currencyView);

        this.detailNominalInterestRateBlock = new WebMarkupBlock("detailNominalInterestRateBlock", Size.Six_6);
        add(this.detailNominalInterestRateBlock);
        this.detailNominalInterestRateVContainer = new WebMarkupContainer("detailNominalInterestRateVContainer");
        this.detailNominalInterestRateBlock.add(this.detailNominalInterestRateVContainer);
        this.detailNominalInterestRateView = new ReadOnlyView("detailNominalInterestRateView", new PropertyModel<>(this, "detailNominalInterestRateValue"));
        this.detailNominalInterestRateVContainer.add(this.detailNominalInterestRateView);

        this.totalDepositBlock = new WebMarkupBlock("totalDepositBlock", Size.Six_6);
        add(this.totalDepositBlock);
        this.totalDepositVContainer = new WebMarkupContainer("totalDepositVContainer");
        this.totalDepositBlock.add(this.totalDepositVContainer);
        this.totalDepositView = new ReadOnlyView("totalDepositView", new PropertyModel<>(this, "totalDepositValue"));
        this.totalDepositVContainer.add(this.totalDepositView);

        this.daysInYearBlock = new WebMarkupBlock("daysInYearBlock", Size.Six_6);
        add(this.daysInYearBlock);
        this.daysInYearVContainer = new WebMarkupContainer("daysInYearVContainer");
        this.daysInYearBlock.add(this.daysInYearVContainer);
        this.daysInYearView = new ReadOnlyView("daysInYearView", new PropertyModel<>(this, "daysInYearValue"));
        this.daysInYearVContainer.add(this.daysInYearView);

        this.totalInterestEarnedBlock = new WebMarkupBlock("totalInterestEarnedBlock", Size.Six_6);
        add(this.totalInterestEarnedBlock);
        this.totalInterestEarnedVContainer = new WebMarkupContainer("totalInterestEarnedVContainer");
        this.totalInterestEarnedBlock.add(this.totalInterestEarnedVContainer);
        this.totalInterestEarnedView = new ReadOnlyView("totalInterestEarnedView", new PropertyModel<>(this, "totalInterestEarnedValue"));
        this.totalInterestEarnedVContainer.add(this.totalInterestEarnedView);

        this.tab = new AjaxTabbedPanel<>("tab", Arrays.asList(new SavingAccountPreviewTransaction(this), new SavingAccountPreviewCharge(this)));
        add(this.tab);

        PageParameters parameters = new PageParameters();
        parameters.add("client", this.client.name());
        if (this.client == ClientEnum.Client) {
            parameters.add("clientId", this.clientId);
        } else if (this.client == ClientEnum.Center) {
            parameters.add("centerId", this.centerId);
        } else if (this.client == ClientEnum.Group) {
            parameters.add("groupId", this.groupId);
        }
        parameters.add("savingId", this.savingId);

        this.closeLink = new BookmarkablePageLink<>("closeLink", SavingAccountClosePage.class, parameters);
        add(this.closeLink);

        this.depositLink = new BookmarkablePageLink<>("depositLink", SavingAccountDepositPage.class, parameters);
        add(this.depositLink);

        this.withdrawLink = new BookmarkablePageLink<>("withdrawLink", SavingAccountWithdrawPage.class, parameters);
        add(this.withdrawLink);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.savingId = getPageParameters().get("savingId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        if (this.client == ClientEnum.Client) {
            selectQuery = new SelectQuery(MClient.NAME);
            selectQuery.addField(MClient.Field.DISPLAY_NAME);
            selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
            this.clientDisplayName = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
        } else if (this.client == ClientEnum.Group) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.groupId);
            this.groupDisplayName = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
        } else if (this.client == ClientEnum.Center) {
            selectQuery = new SelectQuery(MGroup.NAME);
            selectQuery.addField(MGroup.Field.DISPLAY_NAME);
            selectQuery.addWhere(MGroup.Field.ID + " = :" + MGroup.Field.ID, this.centerId);
            this.centerDisplayName = named.queryForObject(selectQuery.toSQL(), selectQuery.getParam(), String.class);
        }

        selectQuery = new SelectQuery(MSavingsAccount.NAME);
        selectQuery.addField(MSavingsAccount.Field.ACCOUNT_NO);
        selectQuery.addWhere(MSavingsAccount.Field.ID + " = :" + MSavingsAccount.Field.ID, this.savingId);
        Map<String, Object> savingObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.savingAccountNo = (String) savingObject.get("account_no");
    }

}
