package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.pages.code.CodeBrowsePage;
import com.angkorteam.fintech.pages.hook.HookBrowsePage;
import com.angkorteam.fintech.pages.role.RoleBrowsePage;
import com.angkorteam.fintech.pages.table.DataTableBrowsePage;
import com.angkorteam.framework.BackgroundColor;
import com.angkorteam.framework.Emoji;
import com.angkorteam.framework.models.InfoBox;
import com.angkorteam.framework.panels.InfoBoxPanel;
import org.apache.wicket.model.Model;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class SystemDashboardPage extends Page {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        InfoBoxPanel manageDataTablePage = new InfoBoxPanel("manageDataTablePage", Model.of(new InfoBox().setPage(DataTableBrowsePage.class).setTitle("Manage Data Tables").setDescription("Add new extra fields to any entity in the form of data table").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(manageDataTablePage);

        InfoBoxPanel auditTrailPage = new InfoBoxPanel("auditTrailPage", Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Audit Trails").setDescription("Audit logs of all the activities, such as create client, disburse loans etc").setIcon(Emoji.ion_alert)));
        add(auditTrailPage);

        InfoBoxPanel manageCodePage = new InfoBoxPanel("manageCodePage", Model.of(new InfoBox().setPage(CodeBrowsePage.class).setTitle("Manage Codes").setDescription("Codes are used to define drop down values").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(manageCodePage);

        InfoBoxPanel manageReportPage = new InfoBoxPanel("manageReportPage", Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Manage Reports").setDescription("Add new report and classify reports").setIcon(Emoji.ion_alert)));
        add(manageReportPage);

        InfoBoxPanel manageRolePage = new InfoBoxPanel("manageRolePage", Model.of(new InfoBox().setPage(RoleBrowsePage.class).setTitle("Manage Roles & Permissions").setDescription("Define or modify roles and associated permissions").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(manageRolePage);

        InfoBoxPanel scheduleJobPage = new InfoBoxPanel("scheduleJobPage", Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Scheduler Jobs").setDescription("Schedule a job, modify or delete jobs").setIcon(Emoji.ion_alert)));
        add(scheduleJobPage);

        InfoBoxPanel makerCheckerPage = new InfoBoxPanel("makerCheckerPage", Model.of(new InfoBox().setPage(MakerCheckerPage.class).setTitle("Configure Maker Checker Tasks").setDescription("Define or modify maker checker tasks").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(makerCheckerPage);

        InfoBoxPanel configurationPage = new InfoBoxPanel("configurationPage", Model.of(new InfoBox().setPage(GlobalConfigurationPage.class).setTitle("Configurations").setDescription("Global configurations & Cache settings").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(configurationPage);

        InfoBoxPanel manageHookPage = new InfoBoxPanel("manageHookPage", Model.of(new InfoBox().setPage(HookBrowsePage.class).setTitle("Manage Hooks").setDescription("Define hooks").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(manageHookPage);

        InfoBoxPanel accountNumberPage = new InfoBoxPanel("accountNumberPage", Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Account Number Preferences").setDescription("Preferences for generating account number for client, loan and savings accounts").setIcon(Emoji.ion_alert)));
        add(accountNumberPage);

        InfoBoxPanel manageEntityPage = new InfoBoxPanel("manageEntityPage", Model.of(new InfoBox().setPage(IndexPage.class).setTitle("Entity To Entity Mapping").setDescription("Define or modify entity to entity mappings").setIcon(Emoji.ion_alert)));
        add(manageEntityPage);

        InfoBoxPanel externalServicePage = new InfoBoxPanel("externalServicePage", Model.of(new InfoBox().setPage(ServiceDashboardPage.class).setTitle("External Services").setDescription("External Services Configuration").setBackgroundColor(BackgroundColor.AquaActive).setIcon(Emoji.ion_alert)));
        add(externalServicePage);

    }
}
