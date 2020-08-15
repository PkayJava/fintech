package com.angkorteam.fintech.pages.admin;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.pages.SimulatorPage;
import com.angkorteam.fintech.pages.admin.organization.currency.CurrencyConfigurationPage;
import com.angkorteam.fintech.pages.admin.organization.funds.FundBrowsePage;
import com.angkorteam.fintech.pages.admin.organization.holidays.HolidayBrowsePage;
import com.angkorteam.fintech.pages.admin.organization.offices.OfficeTablePage;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class OrganizationPage extends MasterPage {

    private BookmarkablePageLink<Void> manageOfficesLink;

    private BookmarkablePageLink<Void> currencyConfigurationLink;

    private BookmarkablePageLink<Void> manageHolidaysLink;

    private BookmarkablePageLink<Void> manageFundsLink;

    private BookmarkablePageLink<Void> manageEmployeesLink;

    private BookmarkablePageLink<Void> bulkLoanReassignmentLink;

    private BookmarkablePageLink<Void> standingInstructionsHistoryLink;

    private BookmarkablePageLink<Void> tellerLink;

    private BookmarkablePageLink<Void> fundMappingLink;

    private BookmarkablePageLink<Void> workingDaysLink;

    private BookmarkablePageLink<Void> passwordPreferencesLink;

    private BookmarkablePageLink<Void> paymentTypeLink;

    private BookmarkablePageLink<Void> loanProvisioningCriteriaLink;

    private BookmarkablePageLink<Void> smsCampaignsLink;

    private BookmarkablePageLink<Void> entityDataTableChecksLink;

    private BookmarkablePageLink<Void> adHocQueryLink;

    private BookmarkablePageLink<Void> bulkImportLink;

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.manageOfficesLink = new BookmarkablePageLink<>("manageOfficesLink", OfficeTablePage.class);
        body.add(this.manageOfficesLink);

        this.currencyConfigurationLink = new BookmarkablePageLink<>("currencyConfigurationLink", CurrencyConfigurationPage.class);
        body.add(this.currencyConfigurationLink);

        this.manageHolidaysLink = new BookmarkablePageLink<>("manageHolidaysLink", HolidayBrowsePage.class);
        body.add(this.manageHolidaysLink);

        this.manageFundsLink = new BookmarkablePageLink<>("manageFundsLink", FundBrowsePage.class);
        body.add(this.manageFundsLink);

        this.manageEmployeesLink = new BookmarkablePageLink<>("manageEmployeesLink", SimulatorPage.class);
        body.add(this.manageEmployeesLink);

        this.bulkLoanReassignmentLink = new BookmarkablePageLink<>("bulkLoanReassignmentLink", SimulatorPage.class);
        body.add(this.bulkLoanReassignmentLink);

        this.standingInstructionsHistoryLink = new BookmarkablePageLink<>("standingInstructionsHistoryLink", SimulatorPage.class);
        body.add(this.standingInstructionsHistoryLink);

        this.tellerLink = new BookmarkablePageLink<>("tellerLink", SimulatorPage.class);
        body.add(this.tellerLink);

        this.fundMappingLink = new BookmarkablePageLink<>("fundMappingLink", SimulatorPage.class);
        body.add(this.fundMappingLink);

        this.workingDaysLink = new BookmarkablePageLink<>("workingDaysLink", SimulatorPage.class);
        body.add(this.workingDaysLink);

        this.passwordPreferencesLink = new BookmarkablePageLink<>("passwordPreferencesLink", SimulatorPage.class);
        body.add(this.passwordPreferencesLink);

        this.paymentTypeLink = new BookmarkablePageLink<>("paymentTypeLink", SimulatorPage.class);
        body.add(this.paymentTypeLink);

        this.loanProvisioningCriteriaLink = new BookmarkablePageLink<>("loanProvisioningCriteriaLink", SimulatorPage.class);
        body.add(this.loanProvisioningCriteriaLink);

        this.smsCampaignsLink = new BookmarkablePageLink<>("smsCampaignsLink", SimulatorPage.class);
        body.add(this.smsCampaignsLink);

        this.entityDataTableChecksLink = new BookmarkablePageLink<>("entityDataTableChecksLink", SimulatorPage.class);
        body.add(this.entityDataTableChecksLink);

        this.adHocQueryLink = new BookmarkablePageLink<>("adHocQueryLink", SimulatorPage.class);
        body.add(this.adHocQueryLink);

        this.bulkImportLink = new BookmarkablePageLink<>("bulkImportLink", SimulatorPage.class);
        body.add(this.bulkImportLink);
    }

}
