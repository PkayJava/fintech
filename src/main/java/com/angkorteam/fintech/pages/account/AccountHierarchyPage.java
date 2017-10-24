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

    private NestedTree<Map<String, Object>> accountTree;
    private AccountHierarchyProvider accountProvider;

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
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initComponent() {
        this.accountProvider = new AccountHierarchyProvider();

        this.accountTree = new NestedTree<>("accountTree", this.accountProvider, this::accountCreateLabel, this::accountCreateLink);
        add(this.accountTree);

        this.browseLink = new BookmarkablePageLink<>("browseLink", AccountBrowsePage.class);
        add(this.browseLink);

        this.createLink = new BookmarkablePageLink<>("createLink", AccountCreatePage.class);
        add(this.createLink);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    private MarkupContainer accountCreateLink(String s, IModel<Map<String, Object>> model) {
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

    private Component accountCreateLabel(String s, IModel<Map<String, Object>> mapIModel) {
        return new Label(s, (String) mapIModel.getObject().get("name"));
    }

}
