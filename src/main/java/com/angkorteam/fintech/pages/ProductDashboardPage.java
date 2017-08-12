package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.charge.ChargeBrowsePage;
import com.angkorteam.fintech.pages.rate.FloatingRateBrowsePage;
import com.angkorteam.framework.BackgroundColor;
import com.angkorteam.framework.Emoji;
import com.angkorteam.framework.models.InfoBox;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.panels.InfoBoxPanel;
import com.google.common.collect.Lists;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Created by socheatkhauv on 7/16/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ProductDashboardPage extends Page {

    private static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Product");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        InfoBoxPanel loanProductsPage = new InfoBoxPanel("loanProductsPage",
                Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Loan Products")
                        .setDescription("Add new loan product or modify or inactivate loan product")
                        .setIcon(Emoji.ion_alert)));
        add(loanProductsPage);

        InfoBoxPanel productsMixPage = new InfoBoxPanel("productsMixPage",
                Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Products Mix")
                        .setDescription("Defines rules for taking multiple rules").setIcon(Emoji.ion_alert)));
        add(productsMixPage);

        InfoBoxPanel savingsProductsPage = new InfoBoxPanel("savingsProductsPage",
                Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Savings Products")
                        .setDescription("Add new savings product or modify or inactivate savings product")
                        .setIcon(Emoji.ion_alert)));
        add(savingsProductsPage);

        InfoBoxPanel fixedDepositProductsPage = new InfoBoxPanel("fixedDepositProductsPage",
                Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Fixed Deposit Products")
                        .setDescription("Add, modify or inactivate a Fixed deposit product").setIcon(Emoji.ion_alert)));
        add(fixedDepositProductsPage);

        InfoBoxPanel shareProductsPage = new InfoBoxPanel("shareProductsPage",
                Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Share Products")
                        .setDescription("Add new share product or modify or inactivate share product")
                        .setIcon(Emoji.ion_alert)));
        add(shareProductsPage);

        InfoBoxPanel recurringDepositProductsPage = new InfoBoxPanel("recurringDepositProductsPage",
                Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Recurring Deposit Products")
                        .setDescription("Add, modify or inactivate a Recurring Deposit product")
                        .setIcon(Emoji.ion_alert)));
        add(recurringDepositProductsPage);

        InfoBoxPanel chargesProductPage = new InfoBoxPanel("chargesProductPage",
                Model.of(new InfoBox().setPage(ChargeBrowsePage.class).setTitle("Charges")
                        .setDescription("Define charges/penalties for loan products, savings and deposit products")
                        .setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(chargesProductPage);

        InfoBoxPanel manageTaxConfigurationsPage = new InfoBoxPanel("manageTaxConfigurationsPage",
                Model.of(new InfoBox().setPage(TaxDashboardPage.class).setTitle("Manage Tax Configurations")
                        .setDescription("Define Tax components and Tax groups")
                        .setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(manageTaxConfigurationsPage);

        InfoBoxPanel floatingRatesPage = new InfoBoxPanel("floatingRatesPage",
                Model.of(new InfoBox().setPage(FloatingRateBrowsePage.class).setTitle("Floating Rates")
                        .setDescription("Define floating rates for loan products")
                        .setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(floatingRatesPage);

    }

}
