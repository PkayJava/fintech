package com.angkorteam.fintech.pages.admin.system.role;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.data.MySQLDataProvider;
import com.angkorteam.fintech.meta.tenant.MRole;
import com.angkorteam.webui.frmk.common.Bookmark;
import com.angkorteam.webui.frmk.common.WicketFactory;
import com.angkorteam.webui.frmk.provider.QueryDataProvider;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.AbstractDataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.webui.frmk.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.webui.frmk.wicket.layout.Size;
import com.angkorteam.webui.frmk.wicket.layout.UIColumn;
import com.angkorteam.webui.frmk.wicket.layout.UIContainer;
import com.angkorteam.webui.frmk.wicket.layout.UIRow;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.metamodel.DataContext;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/system/role/browse")
public class RoleBrowsePage extends MasterPage {

    protected Form<Void> filterForm;

    protected UIRow row1;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected Button searchButton;
    protected BookmarkablePageLink<Void> clearButton;

    protected BookmarkablePageLink<Void> createLink;

    protected FilterForm<Map<String, Expression>> roleBrowseForm;
    protected QueryDataProvider roleBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> roleBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> roleBrowseTable;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MRole mRole = MRole.staticInitialize(dataContext);

        this.roleBrowseProvider = new MySQLDataProvider(mRole.getName());
        this.roleBrowseProvider.setCountField(mRole.getName() + "." + mRole.ID.getName());
        this.roleBrowseProvider.selectField(mRole.getName() + "." + mRole.ID.getName(), "roleId");

        this.roleBrowseColumn = new ArrayList<>();
        this.roleBrowseColumn.add(Column.text(Model.of("Name"), mRole.getName() + "." + mRole.NAME.getName(), "name", this.roleBrowseProvider));
        this.roleBrowseColumn.add(Column.text(Model.of("Description"), mRole.getName() + "." + mRole.DESCRIPTION.getName(), "description", this.roleBrowseProvider));
        this.roleBrowseColumn.add(Column.yesno(Model.of("Disabled"), mRole.getName() + "." + mRole.DISABLED.getName(), "disabled", this.roleBrowseProvider));
        this.roleBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::roleBrowseActionLink, this::roleBrowseActionClick));
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        this.filterForm = new Form<>("filterForm");
        body.add(this.filterForm);

        this.row1 = UIRow.newUIRow("row1", this.filterForm);

        this.nameColumn = this.row1.newUIColumn("nameColumn", Size.Three_3);
        this.nameContainer = this.nameColumn.newUIContainer("nameContainer");
        this.nameField = new TextField<>("nameField", new PropertyModel<>(this, "nameValue"));
        this.nameField.setLabel(Model.of("Name"));
        this.nameContainer.add(this.nameField);
        this.nameContainer.newFeedback("nameFeedback", this.nameField);

        this.row1.newUIColumn("lastColumn");

        this.searchButton = new Button("searchButton") {
            @Override
            public void onSubmit() {
                searchButtonSubmit();
            }
        };
        this.filterForm.add(this.searchButton);

        this.clearButton = new BookmarkablePageLink<>("clearButton", RoleBrowsePage.class);
        this.filterForm.add(this.clearButton);

        this.createLink = new BookmarkablePageLink<>("createLink", RoleCreatePage.class);
        body.add(this.createLink);

        this.roleBrowseForm = new FilterForm<>("roleBrowseForm", this.roleBrowseProvider);
        body.add(this.roleBrowseForm);

        this.roleBrowseTable = new DataTable<>("roleBrowseTable", this.roleBrowseColumn, this.roleBrowseProvider, 20);
        this.roleBrowseForm.add(this.roleBrowseTable);
    }

    protected List<ActionItem> roleBrowseActionLink(String link, Map<String, Object> model) {
        int disabled = model.get("disabled") == null ? 0 : (int) model.get("disabled");
        List<ActionItem> actions = new ArrayList<>();
        actions.add(new ActionItem("Permission", Model.of("Permission"), ItemCss.WARNING));
        actions.add(new ActionItem("Edit", Model.of("Edit"), ItemCss.WARNING));
        actions.add(new ActionItem("Delete", Model.of("Delete"), ItemCss.WARNING));
        if (disabled == 0) {
            actions.add(new ActionItem("Enable", Model.of("Enable"), ItemCss.WARNING));
        } else {
            actions.add(new ActionItem("Disable", Model.of("Disable"), ItemCss.WARNING));
        }
        return actions;
    }

    protected void roleBrowseActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        long roleId = (long) model.get("roleId");
        if ("Edit".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("roleId", roleId);
            setResponsePage(RoleModifyPage.class, parameters);
        } else if ("Delete".equals(link)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            FineractClient client = context.getBean(FineractClient.class);
            client.roleDelete(getSession().getIdentifier(), getSession().getToken(), roleId);
            target.add(this.roleBrowseTable);
        } else if ("Enable".equals(link)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            FineractClient client = context.getBean(FineractClient.class);
            client.roleEnable(getSession().getIdentifier(), getSession().getToken(), roleId);
            target.add(this.roleBrowseTable);
        } else if ("Disable".equals(link)) {
            ApplicationContext context = WicketFactory.getApplicationContext();
            FineractClient client = context.getBean(FineractClient.class);
            client.roleDisable(getSession().getIdentifier(), getSession().getToken(), roleId);
            target.add(this.roleBrowseTable);
        } else if ("Permission".equals(link)) {
            PageParameters parameters = new PageParameters();
            parameters.add("roleId", roleId);
            setResponsePage(RolePermissionPage.class, parameters);
        }
    }

    protected void searchButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MRole mRole = MRole.staticInitialize(dataContext);

        this.roleBrowseProvider.removeWhere("name");
        if (this.nameValue != null && !"".equals(this.nameValue)) {
            this.roleBrowseProvider.applyWhere("name", mRole.getName() + "." + mRole.NAME.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.nameValue) + "%'");
        }
    }

}
