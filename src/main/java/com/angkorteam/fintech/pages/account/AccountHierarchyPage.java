package com.angkorteam.fintech.pages.account;

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

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.AccountHierarchyProvider;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.tree.NestedTree;
import com.google.common.collect.Lists;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class AccountHierarchyPage extends Page {

    protected UIRow row1;

    protected UIBlock dataBlock;
    protected UIContainer dataIContainer;
    protected NestedTree<Map<String, Object>> accountTree;
    protected AccountHierarchyProvider accountProvider;

    protected BookmarkablePageLink<Void> browseLink;

    protected BookmarkablePageLink<Void> createLink;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting");
            breadcrumb.setPage(AccountingPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Chart of Accounts");
            breadcrumb.setPage(AccountBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounts Hierarchy");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        this.accountProvider = new AccountHierarchyProvider();
    }

    @Override
    protected void initComponent() {
        this.row1 = UIRow.newUIRow("row1", this);

        this.dataBlock = this.row1.newUIBlock("dataBlock", Size.Twelve_12);
        this.dataIContainer = this.dataBlock.newUIContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);
        this.accountTree = new NestedTree<>("accountTree", this.accountProvider, this::accountCreateLabel, this::accountCreateLink);
        this.dataIContainer.add(this.accountTree);

        this.browseLink = new BookmarkablePageLink<>("browseLink", AccountBrowsePage.class);
        add(this.browseLink);

        this.createLink = new BookmarkablePageLink<>("createLink", AccountCreatePage.class);
        add(this.createLink);
    }

    @Override
    protected void configureMetaData() {
    }

    protected MarkupContainer accountCreateLink(String s, IModel<Map<String, Object>> model) {
        if (Boolean.TRUE.equals(model.getObject().get("memory"))) {
            return null;
        } else {
            Long id = (Long) model.getObject().get("id");
            PageParameters parameters = new PageParameters();
            parameters.add("accountId", id);
            BookmarkablePageLink<Void> link = new BookmarkablePageLink<>(s, AccountModifyPage.class, parameters);
            return link;
        }
    }

    protected Component accountCreateLabel(String s, IModel<Map<String, Object>> model) {
        return new Label(s, (String) model.getObject().get("name"));
    }

}
