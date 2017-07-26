package com.angkorteam.fintech.pages.account;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.provider.AccountHierarchyProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Map;

/**
 * Created by socheatkhauv on 6/27/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class AccountHierarchyPage extends Page {

    private NestedTree<Map<String, Object>> tree;

    private AccountHierarchyProvider provider;

    private BookmarkablePageLink<Void> browseLink;

    private BookmarkablePageLink<Void> createLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.provider = new AccountHierarchyProvider();

        this.tree = new NestedTree<>("tree", this.provider, this::newLabel, this::newLink);
        add(this.tree);

        this.browseLink = new BookmarkablePageLink<>("browseLink", AccountBrowsePage.class);
        add(this.browseLink);

        this.createLink = new BookmarkablePageLink<>("createLink", AccountCreatePage.class);
        add(this.createLink);
    }

    private MarkupContainer newLink(String s, IModel<Map<String, Object>> model) {
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

    private Component newLabel(String s, IModel<Map<String, Object>> mapIModel) {
        return new Label(s, (String) mapIModel.getObject().get("name"));
    }

}
