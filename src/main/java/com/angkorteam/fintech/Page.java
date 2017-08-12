package com.angkorteam.fintech;

import java.util.List;
import java.util.MissingResourceException;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.json.JSONArray;
import org.json.JSONObject;

import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.pages.IndexPage;
import com.angkorteam.fintech.pages.LogoutPage;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.pages.staff.UserBrowsePage;
import com.angkorteam.framework.Emoji;
import com.angkorteam.framework.models.NavBarMenu;
import com.angkorteam.framework.models.NavBarMenuItem;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.models.PageFooter;
import com.angkorteam.framework.models.PageHeader;
import com.angkorteam.framework.models.PageLogo;
import com.angkorteam.framework.models.SideMenu;
import com.angkorteam.framework.models.UserInfo;
import com.angkorteam.framework.wicket.DashboardPage;
import com.angkorteam.framework.wicket.markup.html.panel.FeedbackPanel;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/17/17.
 */
public class Page extends DashboardPage {

    private FeedbackPanel feedbackPanel;

    public Page() {
    }

    public Page(IModel<?> model) {
        super(model);
    }

    public Page(PageParameters parameters) {
        super(parameters);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forCSS(".dropdown-menu {  z-index: 100060 !important; }", "menu"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.feedbackPanel = new FeedbackPanel("feedbackPanel", this::report);
        this.add(this.feedbackPanel);
        this.setOutputMarkupId(true);
    }

    private boolean report(FeedbackMessage feedbackMessage) {
        if (feedbackMessage.getReporter() instanceof org.apache.wicket.Page) {
            return true;
        }
        return false;
    }

    protected boolean reportError(JsonNode node) {
        if (node.getObject().has("errors")) {
            JSONArray array = (JSONArray) node.getObject().get("errors");
            for (Object object : array) {
                JSONObject o = (JSONObject) object;
                String defaultUserMessage = (String) o.get("defaultUserMessage");
                String userMessageGlobalisationCode = (String) o.get("userMessageGlobalisationCode");
                if (userMessageGlobalisationCode != null && !"".equals(userMessageGlobalisationCode)) {
                    String message = null;
                    try {
                        message = getString(userMessageGlobalisationCode);
                    } catch (MissingResourceException e) {
                    }
                    if (message == null || "".equals(message)) {
                        error(defaultUserMessage + " due to this reason " + userMessageGlobalisationCode);
                    } else {
                        error(defaultUserMessage + " due to this reason '" + message + "'");
                    }
                } else {
                    error(defaultUserMessage);
                }
            }
            return true;
        } else {
            if (node.getObject().has("exception") || node.getObject().has("error")) {
                if (node.getObject().has("exception")) {
                    error((String) node.getObject().get("exception"));
                }
                if (node.getObject().has("error")) {
                    error((String) node.getObject().get("error"));
                }
                if (node.getObject().has("message")) {
                    error((String) node.getObject().get("message"));
                }
                return true;
            }
        }
        return false;
    }

    protected void reportError(JsonNode node, AjaxRequestTarget target) {
        if (node == null) {
            return;
        }
        if (node.getObject().has("errors")) {
            JSONArray array = (JSONArray) node.getObject().get("errors");
            for (Object object : array) {
                JSONObject o = (JSONObject) object;
                error((String) o.get("defaultUserMessage"));
            }
        } else {
            if (node.getObject().has("exception") || node.getObject().has("error")) {
                if (node.getObject().has("exception")) {
                    error((String) node.getObject().get("exception"));
                }
                if (node.getObject().has("error")) {
                    error((String) node.getObject().get("error"));
                }
                if (node.getObject().has("message")) {
                    error((String) node.getObject().get("message"));
                }
            }
        }
        target.add(this.feedbackPanel);
    }

    @Override
    public IModel<List<SideMenu>> buildSideMenu() {
        List<SideMenu> sideMenus = Lists.newArrayList();
        sideMenus.add(new SideMenu().buildTypeHeader("MAIN NAVIGATION"));
        sideMenus.add(new SideMenu().buildTypeMenu(IndexPage.class, null, Emoji.fa_dashboard, "Navigation"));
        sideMenus.add(new SideMenu().buildTypeMenu(IndexPage.class, null, Emoji.fa_dashboard, "Checker Inbox & Tasks"));
        sideMenus.add(new SideMenu().buildTypeMenu(IndexPage.class, null, Emoji.fa_dashboard, "Collection Sheet"));
        sideMenus.add(
                new SideMenu().buildTypeMenu(IndexPage.class, null, Emoji.fa_dashboard, "Individual Collection Sheet"));
        sideMenus.add(new SideMenu().buildTypeMenu(IndexPage.class, null, Emoji.fa_dashboard, "Frequent Posting"));
        sideMenus.add(new SideMenu().buildTypeMenu(IndexPage.class, null, Emoji.fa_dashboard, "Add Journal Entries"));
        sideMenus.add(new SideMenu().buildTypeMenu(IndexPage.class, null, Emoji.fa_dashboard, "Closing Entries"));
        sideMenus.add(new SideMenu().buildTypeMenu(IndexPage.class, null, Emoji.fa_dashboard, "Chart of Accounts"));
        sideMenus.add(new SideMenu().buildTypeMenu(IndexPage.class, null, Emoji.fa_dashboard, "Client"));
        sideMenus.add(new SideMenu().buildTypeMenu(IndexPage.class, null, Emoji.fa_dashboard, "Group"));
        sideMenus.add(new SideMenu().buildTypeMenu(IndexPage.class, null, Emoji.fa_dashboard, "Center"));
        sideMenus.add(new SideMenu().buildTypeMenu(IndexPage.class, null, Emoji.fa_dashboard, "Help"));
        return Model.ofList(sideMenus);
    }

    @Override
    public IModel<PageHeader> buildPageHeader() {
        return Model.of(new PageHeader().setTitle("FinTech").setDescription("FinTech"));
    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return null;
    }

    @Override
    public IModel<List<NavBarMenu>> buildNavBarMenu() {

        NavBarMenu clientsMenu = new NavBarMenu().buildTypeIcon(Emoji.fa_dashboard, "Clients", null,
                new NavBarMenuItem().buildTypeIcon(IndexPage.class, null, Emoji.fa_dashboard, "Clients"),
                new NavBarMenuItem().buildTypeIcon(IndexPage.class, null, Emoji.fa_dashboard, "Groups"),
                new NavBarMenuItem().buildTypeIcon(IndexPage.class, null, Emoji.fa_dashboard, "Centers"));

        NavBarMenu accountingMenu = new NavBarMenu().buildTypeIcon(AccountingPage.class, null, Emoji.fa_dashboard,
                "Accounting", null);

        NavBarMenu reportsMenu = new NavBarMenu().buildTypeIcon(Emoji.fa_dashboard, "Reports", null,
                new NavBarMenuItem().buildTypeIcon(IndexPage.class, null, Emoji.fa_dashboard, "All"),
                new NavBarMenuItem().buildTypeIcon(IndexPage.class, null, Emoji.fa_dashboard, "Clients"),
                new NavBarMenuItem().buildTypeIcon(IndexPage.class, null, Emoji.fa_dashboard, "Loans"),
                new NavBarMenuItem().buildTypeIcon(IndexPage.class, null, Emoji.fa_dashboard, "Savings"),
                new NavBarMenuItem().buildTypeIcon(IndexPage.class, null, Emoji.fa_dashboard, "Funds"),
                new NavBarMenuItem().buildTypeIcon(IndexPage.class, null, Emoji.fa_dashboard, "Accounting"),
                new NavBarMenuItem().buildTypeIcon(IndexPage.class, null, Emoji.fa_dashboard, "XBRL"));

        NavBarMenu adminMenu = new NavBarMenu().buildTypeIcon(Emoji.fa_dashboard, "Admin", null,
                new NavBarMenuItem().buildTypeIcon(UserBrowsePage.class, null, Emoji.fa_dashboard, "Users"),
                new NavBarMenuItem().buildTypeIcon(OrganizationDashboardPage.class, null, Emoji.fa_dashboard,
                        "Organization"),
                new NavBarMenuItem().buildTypeIcon(SystemDashboardPage.class, null, Emoji.fa_dashboard, "System"),
                new NavBarMenuItem().buildTypeIcon(ProductDashboardPage.class, null, Emoji.fa_dashboard, "Products"),
                new NavBarMenuItem().buildTypeIcon(IndexPage.class, null, Emoji.fa_dashboard, "Templates"));

        NavBarMenu profileMenu = new NavBarMenu().buildTypeIcon(Emoji.fa_dashboard, "Profile", null,
                new NavBarMenuItem().buildTypeIcon(LogoutPage.class, null, Emoji.fa_sign_out, "Logout"));

        List<NavBarMenu> menus = Lists.newArrayList(clientsMenu, accountingMenu, reportsMenu, adminMenu, profileMenu);
        return Model.ofList(menus);
    }

    @Override
    public IModel<UserInfo> buildUserInfo() {
        return null;
    }

    @Override
    protected IModel<PageLogo> buildPageLogo() {
        PageLogo pageLogo = new PageLogo("<b>A</b>NG", "<b>Angkor</b> FinTech");
        return Model.of(pageLogo);
    }

    @Override
    protected IModel<PageFooter> buildPageFooter() {
        PageFooter pageFooter = new PageFooter();
        pageFooter.setCompany("AngkorTeam");
        return Model.of(pageFooter);
    }

    @Override
    public IModel<Boolean> hasSearchForm() {
        return Model.of(false);
    }

    @Override
    public void onSearchClick(String searchValue) {

    }
}
