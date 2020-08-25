package com.angkorteam.fintech.pages.admin.organization.offices;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.data.MySQLDataProvider;
import com.angkorteam.fintech.meta.tenant.MOffice;
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
@Bookmark("/admin/organization/offices/browse")
public class OfficeTablePage extends MasterPage {

    protected Form<Void> filterForm;

    protected UIRow row1;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected Button searchButton;
    protected BookmarkablePageLink<Void> clearButton;

    protected BookmarkablePageLink<Void> createLink;
    protected BookmarkablePageLink<Void> treeviewLink;

    protected FilterForm<Map<String, Expression>> officeBrowseForm;
    protected QueryDataProvider officeBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> officeBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> officeBrowseTable;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MOffice mOffice = MOffice.staticInitialize(dataContext);

        this.officeBrowseProvider = new MySQLDataProvider(mOffice.getName() + " office");
        this.officeBrowseProvider.applyJoin("m_office", "LEFT JOIN " + mOffice.getName() + " parent ON office." + mOffice.PARENT_ID.getName() + " = parent." + mOffice.ID.getName());

        this.officeBrowseProvider.setCountField("office." + mOffice.ID.getName());
        this.officeBrowseProvider.selectField("office." + mOffice.ID.getName(), "officeId");

        this.officeBrowseColumn = new ArrayList<>();
        this.officeBrowseColumn.add(Column.text(Model.of("External ID"), "office." + mOffice.EXTERNAL_ID.getName(), "externalId", this.officeBrowseProvider));
        this.officeBrowseColumn.add(Column.text(Model.of("Office Name"), "office." + mOffice.NAME.getName(), "officeName", this.officeBrowseProvider));
        this.officeBrowseColumn.add(Column.text(Model.of("Parent Office"), "parent." + mOffice.NAME.getName(), "parentOffice", this.officeBrowseProvider));
        this.officeBrowseColumn.add(Column.datetime(Model.of("Opened On"), "office." + mOffice.OPENING_DATE.getName(), "openedOn", this.officeBrowseProvider, "dd MMM yyyy"));
        this.officeBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::officeBrowseActionLink, this::officeBrowseActionClick));
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

        this.clearButton = new BookmarkablePageLink<>("clearButton", OfficeTablePage.class);
        this.filterForm.add(this.clearButton);

        this.createLink = new BookmarkablePageLink<>("createLink", OfficeCreatePage.class);
        body.add(this.createLink);
        this.treeviewLink = new BookmarkablePageLink<>("treeviewLink", OfficeHierarchyPage.class);
        body.add(this.treeviewLink);

        this.officeBrowseForm = new FilterForm<>("officeBrowseForm", this.officeBrowseProvider);
        body.add(this.officeBrowseForm);

        this.officeBrowseTable = new DataTable<>("officeBrowseTable", this.officeBrowseColumn, this.officeBrowseProvider, 20);
        this.officeBrowseForm.add(this.officeBrowseTable);
    }

    protected List<ActionItem> officeBrowseActionLink(String link, Map<String, Object> model) {
        List<ActionItem> actions = new ArrayList<>();
        actions.add(new ActionItem("Edit", Model.of("Edit"), ItemCss.WARNING));
        return actions;
    }

    protected void officeBrowseActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        long officeId = (long) model.get("officeId");
        PageParameters parameters = new PageParameters();
        parameters.add("officeId", officeId);
        setResponsePage(OfficeModifyPage.class, parameters);
    }

    protected void searchButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MOffice mOffice = MOffice.staticInitialize(dataContext);

        this.officeBrowseProvider.removeWhere("name");
        if (this.nameValue != null && !"".equals(this.nameValue)) {
            this.officeBrowseProvider.applyWhere("name", "office." + mOffice.NAME.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.nameValue) + "%'");
        }
    }

}
