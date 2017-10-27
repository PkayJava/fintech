package com.angkorteam.fintech.pages;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.tax.TaxComponentBrowsePage;
import com.angkorteam.fintech.pages.tax.TaxGroupBrowsePage;
import com.angkorteam.framework.BackgroundColor;
import com.angkorteam.framework.Emoji;
import com.angkorteam.framework.models.InfoBox;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.panels.InfoBoxPanel;
import com.google.common.collect.Lists;

/**
 * Created by socheatkhauv on 7/16/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class TaxDashboardPage extends Page {

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
            breadcrumb.setPage(ProductDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Tax");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        InfoBoxPanel manageTaxComponentsPage = new InfoBoxPanel("manageTaxComponentsPage", Model.of(new InfoBox().setPage(TaxComponentBrowsePage.class).setTitle("Manage Tax Components").setDescription("Define Tax components").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(manageTaxComponentsPage);

        InfoBoxPanel manageTaxGroupsPage = new InfoBoxPanel("manageTaxGroupsPage", Model.of(new InfoBox().setPage(TaxGroupBrowsePage.class).setTitle("Manage Tax Groups").setDescription("Define Tax Groups").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(manageTaxGroupsPage);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

}
