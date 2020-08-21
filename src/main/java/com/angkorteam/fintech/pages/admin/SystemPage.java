package com.angkorteam.fintech.pages.admin;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.pages.TodoPage;
import com.angkorteam.fintech.pages.admin.system.code.CodeBrowsePage;
import com.angkorteam.fintech.pages.admin.system.configuration.ConfigurationBrowsePage;
import com.angkorteam.fintech.pages.admin.system.role.RoleBrowsePage;
import com.angkorteam.fintech.pages.admin.system.table.DataTableBrowsePage;
import com.angkorteam.webui.frmk.common.Bookmark;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/system")
public class SystemPage extends MasterPage {

    private BookmarkablePageLink<Void> manageDataTablesLink;

    private BookmarkablePageLink<Void> auditTrailsLink;

    private BookmarkablePageLink<Void> manageCodesLink;

    private BookmarkablePageLink<Void> manageReportsLink;

    private BookmarkablePageLink<Void> manageRolesAndPermissionsLink;

    private BookmarkablePageLink<Void> schedulerJobsLink;

    private BookmarkablePageLink<Void> configureMakerCheckerTasksLink;

    private BookmarkablePageLink<Void> configurationsLink;

    private BookmarkablePageLink<Void> manageHooksLink;

    private BookmarkablePageLink<Void> accountNumberPreferencesLink;

    private BookmarkablePageLink<Void> entityToEntityMappingLink;

    private BookmarkablePageLink<Void> externalServicesLink;

    private BookmarkablePageLink<Void> manageSurveysLink;

    private BookmarkablePageLink<Void> twoFactorConfigurationLink;

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.manageDataTablesLink = new BookmarkablePageLink<>("manageDataTablesLink", DataTableBrowsePage.class);
        body.add(this.manageDataTablesLink);

        this.auditTrailsLink = new BookmarkablePageLink<>("auditTrailsLink", TodoPage.class);
        body.add(this.auditTrailsLink);

        this.manageCodesLink = new BookmarkablePageLink<>("manageCodesLink", CodeBrowsePage.class);
        body.add(this.manageCodesLink);

        this.manageReportsLink = new BookmarkablePageLink<>("manageReportsLink", TodoPage.class);
        body.add(this.manageReportsLink);

        this.manageRolesAndPermissionsLink = new BookmarkablePageLink<>("manageRolesAndPermissionsLink", RoleBrowsePage.class);
        body.add(this.manageRolesAndPermissionsLink);

        this.schedulerJobsLink = new BookmarkablePageLink<>("schedulerJobsLink", TodoPage.class);
        body.add(this.schedulerJobsLink);

        this.configureMakerCheckerTasksLink = new BookmarkablePageLink<>("configureMakerCheckerTasksLink", TodoPage.class);
        body.add(this.configureMakerCheckerTasksLink);

        this.configurationsLink = new BookmarkablePageLink<>("configurationsLink", ConfigurationBrowsePage.class);
        body.add(this.configurationsLink);

        this.manageHooksLink = new BookmarkablePageLink<>("manageHooksLink", TodoPage.class);
        body.add(this.manageHooksLink);

        this.accountNumberPreferencesLink = new BookmarkablePageLink<>("accountNumberPreferencesLink", TodoPage.class);
        body.add(this.accountNumberPreferencesLink);

        this.entityToEntityMappingLink = new BookmarkablePageLink<>("entityToEntityMappingLink", TodoPage.class);
        body.add(this.entityToEntityMappingLink);

        this.externalServicesLink = new BookmarkablePageLink<>("externalServicesLink", TodoPage.class);
        body.add(this.externalServicesLink);

        this.manageSurveysLink = new BookmarkablePageLink<>("manageSurveysLink", TodoPage.class);
        body.add(this.manageSurveysLink);

        this.twoFactorConfigurationLink = new BookmarkablePageLink<>("twoFactorConfigurationLink", TodoPage.class);
        body.add(this.twoFactorConfigurationLink);
    }

}
