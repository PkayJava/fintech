package com.angkorteam.fintech.pages.group;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.provider.GroupHierarchyProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class GroupHierarchyPage extends Page {

    private NestedTree<Map<String, Object>> tree;

    private GroupHierarchyProvider provider;

    private BookmarkablePageLink<Void> browseLink;

    private BookmarkablePageLink<Void> createLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.provider = new GroupHierarchyProvider();

        this.tree = new NestedTree<>("tree", this.provider, this::newLabel, this::newLink);
        add(this.tree);

        this.browseLink = new BookmarkablePageLink<>("browseLink", GroupBrowsePage.class);
        add(this.browseLink);

        this.createLink = new BookmarkablePageLink<>("createLink", GroupCreatePage.class);
        add(this.createLink);
    }

    private MarkupContainer newLink(String s, IModel<Map<String, Object>> model) {
        Long id = (Long) model.getObject().get("id");
        PageParameters parameters = new PageParameters();
        parameters.add("groupId", id);
        BookmarkablePageLink<Void> link = new BookmarkablePageLink<>(s, GroupModifyPage.class, parameters);
        return link;
    }

    private Component newLabel(String s, IModel<Map<String, Object>> mapIModel) {
        return new Label(s, (String) mapIModel.getObject().get("display_name"));
    }

}
