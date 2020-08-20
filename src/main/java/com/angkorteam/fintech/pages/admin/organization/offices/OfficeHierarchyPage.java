package com.angkorteam.fintech.pages.admin.organization.offices;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.data.OfficeHierarchyProvider;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.webui.frmk.common.Bookmark;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/organization/offices/tree")
public class OfficeHierarchyPage extends MasterPage {

    protected BookmarkablePageLink<Void> createLink;
    protected BookmarkablePageLink<Void> tableLink;

    protected NestedTree<Map<String, Object>> officeBrowseTree;
    protected OfficeHierarchyProvider officeBrowseProvider;

    @Override
    protected void onInitData() {
        super.onInitData();
        this.officeBrowseProvider = new OfficeHierarchyProvider();
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.createLink = new BookmarkablePageLink<>("createLink", OfficeCreatePage.class);
        body.add(this.createLink);
        this.tableLink = new BookmarkablePageLink<>("tableLink", OfficeTablePage.class);
        body.add(this.tableLink);

        this.officeBrowseTree = new NestedTree<>("officeBrowseTree", this.officeBrowseProvider, this::officeBrowseLabel, this::officeBrowseLink);
        body.add(this.officeBrowseTree);
    }

    protected MarkupContainer officeBrowseLink(String s, IModel<Map<String, Object>> model) {
        Long id = (Long) model.getObject().get("id");
        PageParameters parameters = new PageParameters();
        parameters.add("officeId", id);
        BookmarkablePageLink<Void> link = new BookmarkablePageLink<>(s, OfficeModifyPage.class, parameters);
        return link;
    }

    protected Component officeBrowseLabel(String id, IModel<Map<String, Object>> model) {
        return new Label(id, (String) model.getObject().get("name"));
    }

}
