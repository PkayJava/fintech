package com.angkorteam.fintech.pages.office;

import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.DeprecatedPage;
import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.pages.OrganizationDashboardPage;
import com.angkorteam.fintech.provider.OfficeHierarchyProvider;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.tree.NestedTree;
import com.google.common.collect.Lists;

/**
 * Created by socheatkhauv on 6/22/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class OfficeHierarchyPage extends DeprecatedPage {

    private NestedTree<Map<String, Object>> tree;

    private OfficeHierarchyProvider provider;

    private BookmarkablePageLink<Void> browseLink;

    private BookmarkablePageLink<Void> createLink;

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
            breadcrumb.setLabel("Organization");
            breadcrumb.setPage(OrganizationDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Office");
            breadcrumb.setPage(OfficeBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Office Hierarchy");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.provider = new OfficeHierarchyProvider();

        this.tree = new NestedTree<>("tree", this.provider, this::newLabel, this::newLink);
        add(this.tree);

        this.browseLink = new BookmarkablePageLink<>("browseLink", OfficeBrowsePage.class);
        add(this.browseLink);

        this.createLink = new BookmarkablePageLink<>("createLink", OfficeCreatePage.class);
        add(this.createLink);
    }

    private MarkupContainer newLink(String s, IModel<Map<String, Object>> model) {
        Long id = (Long) model.getObject().get("id");
        PageParameters parameters = new PageParameters();
        parameters.add("officeId", id);
        BookmarkablePageLink<Void> link = new BookmarkablePageLink<>(s, OfficeModifyPage.class, parameters);
        return link;
    }

    private Component newLabel(String s, IModel<Map<String, Object>> mapIModel) {
        return new Label(s, (String) mapIModel.getObject().get("name"));
    }

}
