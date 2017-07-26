package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.dto.Function;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.pages.tax.TaxComponentBrowsePage;
import com.angkorteam.fintech.pages.tax.TaxGroupBrowsePage;
import com.angkorteam.framework.BackgroundColor;
import com.angkorteam.framework.Emoji;
import com.angkorteam.framework.models.InfoBox;
import com.angkorteam.framework.panels.InfoBoxPanel;

/**
 * Created by socheatkhauv on 7/16/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class TaxDashboardPage extends Page {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        InfoBoxPanel manageTaxComponentsPage = new InfoBoxPanel("manageTaxComponentsPage",
                Model.of(new InfoBox().setPage(TaxComponentBrowsePage.class).setTitle("Manage Tax Components")
                        .setDescription("Define Tax components").setBackgroundColor(BackgroundColor.AquaActive)
                        .setIcon(Emoji.ion_alert)));
        add(manageTaxComponentsPage);

        InfoBoxPanel manageTaxGroupsPage = new InfoBoxPanel("manageTaxGroupsPage",
                Model.of(new InfoBox().setPage(TaxGroupBrowsePage.class).setTitle("Manage Tax Groups")
                        .setDescription("Define Tax Groups").setBackgroundColor(BackgroundColor.AquaActive)
                        .setIcon(Emoji.ion_alert)));
        add(manageTaxGroupsPage);

    }

}
