package com.angkorteam.fintech.provider.ui;

import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.pages.TodoPage;
import com.angkorteam.fintech.pages.admin.OrganizationPage;
import com.angkorteam.fintech.pages.admin.ProductsPage;
import com.angkorteam.fintech.pages.admin.SystemPage;
import com.angkorteam.fintech.pages.admin.UserBrowsePage;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.model.Navbar;
import com.angkorteam.webui.frmk.model.menu.left.TopLeftMenu;
import com.angkorteam.webui.frmk.model.menu.left.TopLeftMenuDropdown;
import com.angkorteam.webui.frmk.model.menu.left.TopLeftMenuItem;
import com.angkorteam.webui.frmk.model.menu.left.TopLeftSubMenuItem;
import com.angkorteam.webui.frmk.provider.INavbarProvider;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class MemoryNavbarProvider implements INavbarProvider {

    private WebSession session;

    public MemoryNavbarProvider(WebSession session) {
        this.session = session;
    }

    @Override
    public Navbar fetchNavbar() {
        Navbar navbar = new Navbar();
        navbar.setSearchable(false);

        ApplicationContext context = WicketFactory.getApplicationContext();

        List<TopLeftMenu> leftMenu = new ArrayList<>();
        {
            TopLeftMenuItem welcomeMenu = new TopLeftMenuItem(String.format("Welcome [%s]", "N/A"));
            leftMenu.add(welcomeMenu);
        }
        leftMenu.add(dropdownInstitution());
        leftMenu.add(new TopLeftMenuItem("Accounting", AccountingPage.class));
        leftMenu.add(dropdownReport());
        leftMenu.add(dropdownAdmin());
        navbar.setLeft(leftMenu);

        return navbar;
    }

    protected TopLeftMenuDropdown dropdownInstitution() {
        TopLeftSubMenuItem client = new TopLeftSubMenuItem("Clients", TodoPage.class);
        TopLeftSubMenuItem group = new TopLeftSubMenuItem("Groups", TodoPage.class);
        TopLeftSubMenuItem center = new TopLeftSubMenuItem("Centers", TodoPage.class);
        return new TopLeftMenuDropdown("Clients", client, group, center);
    }

    protected TopLeftMenuDropdown dropdownAdmin() {
        TopLeftSubMenuItem user = new TopLeftSubMenuItem("Users", UserBrowsePage.class);
        TopLeftSubMenuItem organization = new TopLeftSubMenuItem("Organization", OrganizationPage.class);
        TopLeftSubMenuItem system = new TopLeftSubMenuItem("System", SystemPage.class);
        TopLeftSubMenuItem product = new TopLeftSubMenuItem("Products", ProductsPage.class);
        TopLeftSubMenuItem template = new TopLeftSubMenuItem("Templates", TodoPage.class);
        return new TopLeftMenuDropdown("Admin", user, organization, system, product, template);
    }

    protected TopLeftMenuDropdown dropdownReport() {
        TopLeftSubMenuItem all = new TopLeftSubMenuItem("All", TodoPage.class);
        TopLeftSubMenuItem client = new TopLeftSubMenuItem("Clients", TodoPage.class);
        TopLeftSubMenuItem loans = new TopLeftSubMenuItem("Loans", TodoPage.class);
        TopLeftSubMenuItem saving = new TopLeftSubMenuItem("Savings", TodoPage.class);
        TopLeftSubMenuItem fund = new TopLeftSubMenuItem("Funds", TodoPage.class);
        TopLeftSubMenuItem accounting = new TopLeftSubMenuItem("Accounting", TodoPage.class);
        TopLeftSubMenuItem xbrl = new TopLeftSubMenuItem("XBRL", TodoPage.class);
        return new TopLeftMenuDropdown("Report", all, client, loans, saving, fund, accounting, xbrl);
    }

}
