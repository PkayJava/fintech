package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.pages.entity.CheckerBrowsePage;
import com.angkorteam.fintech.pages.fund.FundBrowsePage;
import com.angkorteam.fintech.pages.holiday.HolidayBrowsePage;
import com.angkorteam.fintech.pages.office.OfficeBrowsePage;
import com.angkorteam.fintech.pages.payment.PaymentTypeBrowsePage;
import com.angkorteam.fintech.pages.staff.StaffBrowsePage;
import com.angkorteam.fintech.pages.teller.TellerBrowsePage;
import com.angkorteam.framework.BackgroundColor;
import com.angkorteam.framework.Emoji;
import com.angkorteam.framework.panels.InfoBoxPanel;
import com.angkorteam.framework.models.InfoBox;
import org.apache.wicket.model.Model;

/**
 * Created by socheatkhauv on 6/22/17.
 */
public class OrganizationDashboardPage extends Page {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        InfoBoxPanel manageOfficePage = new InfoBoxPanel("manageOfficePage", Model.of(new InfoBox().setPage(OfficeBrowsePage.class).setTitle("Manage Offices").setDescription("Add new office or modify or deactivate office or modify office hierarchy").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(manageOfficePage);

        InfoBoxPanel manageHolidayPage = new InfoBoxPanel("manageHolidayPage", Model.of(new InfoBox().setPage(HolidayBrowsePage.class).setTitle("Manage Holidays").setDescription("Define holidays for office").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(manageHolidayPage);

        InfoBoxPanel manageEmployeePage = new InfoBoxPanel("manageEmployeePage", Model.of(new InfoBox().setPage(StaffBrowsePage.class).setTitle("Manage Employees").setDescription("A employee represents loan officers with no access to systems").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(manageEmployeePage);

        InfoBoxPanel standingInstructionHistoryPage = new InfoBoxPanel("standingInstructionHistoryPage", Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Standing Instructions History").setDescription("View logged history of standing instructions").setIcon(Emoji.ion_alert)));
        add(standingInstructionHistoryPage);

        InfoBoxPanel fundMappingPage = new InfoBoxPanel("fundMappingPage", Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Fund Mapping").setDescription("Bulk entry screen for mapping fund sources to loans").setIcon(Emoji.ion_alert)));
        add(fundMappingPage);

        InfoBoxPanel passwordPreferencePage = new InfoBoxPanel("passwordPreferencePage", Model.of(new InfoBox().setPage(PasswordPreferencesPage.class).setTitle("Password Preferences").setDescription("Define standards for enforcing the usage of stronger passwords").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(passwordPreferencePage);

        InfoBoxPanel loanProvisioningCriteriaPage = new InfoBoxPanel("loanProvisioningCriteriaPage", Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Loan Provisioning Criteria").setDescription("Define Loan Provisioning Criteria for Organization").setIcon(Emoji.ion_alert)));
        add(loanProvisioningCriteriaPage);

        InfoBoxPanel entryDataTableCheckPage = new InfoBoxPanel("entryDataTableCheckPage", Model.of(new InfoBox().setPage(CheckerBrowsePage.class).setTitle("Entity Data Table Checks").setDescription("Define Loan Provisioning Criteria for Organization").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(entryDataTableCheckPage);

        InfoBoxPanel currencyConfigurationPage = new InfoBoxPanel("currencyConfigurationPage", Model.of(new InfoBox().setPage(CurrencyConfigurationPage.class).setTitle("Currency Configuration").setDescription("Currencies available across organization for different products").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(currencyConfigurationPage);

        InfoBoxPanel manageFundPage = new InfoBoxPanel("manageFundPage", Model.of(new InfoBox().setTitle("Manage Funds").setPage(FundBrowsePage.class).setDescription("Funds are associated with loans").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(manageFundPage);

        InfoBoxPanel bulkLoanReassignmentPage = new InfoBoxPanel("bulkLoanReassignmentPage", Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Bulk Loan Reassignment").setDescription("Easy way to reassign all the loan from one LO to another LO").setIcon(Emoji.ion_alert)));
        add(bulkLoanReassignmentPage);

        InfoBoxPanel tellerCashierManagementPage = new InfoBoxPanel("tellerCashierManagementPage", Model.of(new InfoBox().setPage(TellerBrowsePage.class).setTitle("Teller / Cashier Management").setDescription("Manage Tellers / Cashiers and Cash Allocation and Settlement").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(tellerCashierManagementPage);

        InfoBoxPanel workingDayPage = new InfoBoxPanel("workingDayPage", Model.of(new InfoBox().setPage(WorkingDayPage.class).setTitle("Working Days").setDescription("Define working days and configure behavior of payments due on holidays").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(workingDayPage);

        InfoBoxPanel paymentTypePage = new InfoBoxPanel("paymentTypePage", Model.of(new InfoBox().setPage(PaymentTypeBrowsePage.class).setTitle("Payment Type").setDescription("Manage payment types").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(paymentTypePage);

        InfoBoxPanel smsCampaignPage = new InfoBoxPanel("smsCampaignPage", Model.of(new InfoBox().setPage(IndexPage.class).setTitle("SMS Campaigns").setDescription("Define SMS Campaigns for Organization").setIcon(Emoji.ion_alert)));
        add(smsCampaignPage);

    }
}
