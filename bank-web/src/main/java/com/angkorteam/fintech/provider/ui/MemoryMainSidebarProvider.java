package com.angkorteam.fintech.provider.ui;

import com.angkorteam.fintech.BootApplication;
import com.angkorteam.fintech.boot.AppProperties;
import com.angkorteam.fintech.factory.WebSession;
import com.angkorteam.fintech.pages.SimulatorPage;
import com.angkorteam.fintech.pages.TodoPage;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.model.Brand;
import com.angkorteam.webui.frmk.model.MainSidebar;
import com.angkorteam.webui.frmk.model.UserPanel;
import com.angkorteam.webui.frmk.model.menu.sidebar.SidebarMenu;
import com.angkorteam.webui.frmk.model.menu.sidebar.SidebarMenuItem;
import com.angkorteam.webui.frmk.provider.IMainSidebarProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class MemoryMainSidebarProvider implements IMainSidebarProvider {

    private WebSession session;

    public MemoryMainSidebarProvider(WebSession session) {
        this.session = session;
    }

    @Override
    public MainSidebar fetchMainSidebar() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        AppProperties properties = context.getBean(AppProperties.class);
        Brand brand = new Brand(properties.getName(), new PackageResourceReference(BootApplication.class, "logo.png"), (Class<? extends WebPage>) WebApplication.get().getHomePage());
        UserPanel userPanel = new UserPanel(new PackageResourceReference(BootApplication.class, "user.png"), "N/A", SimulatorPage.class);
        List<SidebarMenu> children = new ArrayList<>();

        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Keyboard Shortcuts", null, TodoPage.class));
        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Navigation", null, TodoPage.class));
        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Checker Inbox & Tasks", null, TodoPage.class));
        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Collection Sheet", null, TodoPage.class));
        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Individual Collection Sheet", null, TodoPage.class));
        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Frequent Posting", null, TodoPage.class));
        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Add Journal Entries", null, TodoPage.class));
        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Closing Entries", null, TodoPage.class));
        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Closing Entries", null, TodoPage.class));
        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Chart of Accounts", null, TodoPage.class));
        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Notifications", null, TodoPage.class));
        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Client", null, TodoPage.class));
        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Group", null, TodoPage.class));
        children.add(new SidebarMenuItem("fas fa-tachometer-alt", "Center", null, TodoPage.class));

        MainSidebar mainSidebar = new MainSidebar();
        mainSidebar.setBrand(brand);
        mainSidebar.setUserPanel(userPanel);
        mainSidebar.setSidebarMenu(children);
        mainSidebar.setSearchable(false);
        return mainSidebar;
    }

}
