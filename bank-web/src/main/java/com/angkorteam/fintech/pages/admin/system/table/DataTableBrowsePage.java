package com.angkorteam.fintech.pages.admin.system.table;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.client.enums.TableTypeEnum;
import com.angkorteam.fintech.client.renums.DataTableCategoryEnum;
import com.angkorteam.fintech.data.MySQLDataProvider;
import com.angkorteam.fintech.meta.tenant.CConfiguration;
import com.angkorteam.fintech.meta.tenant.XRegisteredTable;
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
import org.apache.commons.lang3.StringUtils;
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
@Bookmark("/admin/system/datatable/browse")
public class DataTableBrowsePage extends MasterPage {

    protected Form<Void> filterForm;

    protected UIRow row1;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected Button searchButton;
    protected BookmarkablePageLink<Void> clearButton;

    protected BookmarkablePageLink<Void> createLink;

    protected FilterForm<Map<String, Expression>> dataBrowseForm;
    protected QueryDataProvider dataBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> dataBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> dataBrowseTable;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        XRegisteredTable xRegisteredTable = XRegisteredTable.staticInitialize(dataContext);

        this.dataBrowseProvider = new MySQLDataProvider(xRegisteredTable.getName());

        this.dataBrowseProvider.setCountField(xRegisteredTable.getName() + "." + xRegisteredTable.REGISTERED_TABLE_NAME.getName());
        this.dataBrowseProvider.selectField(xRegisteredTable.getName() + "." + xRegisteredTable.REGISTERED_TABLE_NAME.getName(), "dataId");

        this.dataBrowseColumn = new ArrayList<>();
        this.dataBrowseColumn.add(Column.text(Model.of("Table Name"), xRegisteredTable.getName() + "." + xRegisteredTable.REGISTERED_TABLE_NAME.getName(), "tableName", this.dataBrowseProvider));
        {
            List<String> whens = new ArrayList<>();
            for (TableTypeEnum c : TableTypeEnum.values()) {
                whens.add("WHEN '" + c.getLiteral() + "' THEN '" + c.getDescription() + "'");
            }
            whens.add("ELSE 'N/A'");
            this.dataBrowseColumn.add(Column.text(Model.of("Associated"), "CASE " + xRegisteredTable.getName() + "." + xRegisteredTable.APPLICATION_TABLE_NAME.getName() + " " + StringUtils.join(whens, " ") + " END", "associated", this.dataBrowseProvider));
        }
        {
            List<String> whens = new ArrayList<>();
            for (DataTableCategoryEnum c : DataTableCategoryEnum.values()) {
                whens.add("WHEN " + c.getLiteral() + " THEN '" + c.getDescription() + "'");
            }
            whens.add("ELSE 'N/A'");
            this.dataBrowseColumn.add(Column.text(Model.of("Category"), "CASE " + xRegisteredTable.getName() + "." + xRegisteredTable.CATEGORY.getName() + " " + StringUtils.join(whens, " ") + " END", "category", this.dataBrowseProvider));
        }
        this.dataBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::dataBrowseActionLink, this::dataBrowseActionClick));
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

        this.clearButton = new BookmarkablePageLink<>("clearButton", DataTableBrowsePage.class);
        this.filterForm.add(this.clearButton);

        this.createLink = new BookmarkablePageLink<>("createLink", DataTableCreatePage.class);
        body.add(this.createLink);

        this.dataBrowseForm = new FilterForm<>("dataBrowseForm", this.dataBrowseProvider);
        body.add(this.dataBrowseForm);

        this.dataBrowseTable = new DataTable<>("dataBrowseTable", this.dataBrowseColumn, this.dataBrowseProvider, 20);
        this.dataBrowseForm.add(this.dataBrowseTable);
    }

    protected List<ActionItem> dataBrowseActionLink(String link, Map<String, Object> model) {
        List<ActionItem> actions = new ArrayList<>();
        actions.add(new ActionItem("Edit", Model.of("Edit"), ItemCss.WARNING));
        return actions;
    }

    protected void dataBrowseActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        String tableName = (String) model.get("tableName");
        PageParameters parameters = new PageParameters();
        parameters.add("tableName", tableName);
        setResponsePage(DataTableModifyPage.class, parameters);
    }

    protected void searchButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        CConfiguration cConfiguration = CConfiguration.staticInitialize(dataContext);

        this.dataBrowseProvider.removeWhere("name");
        if (this.nameValue != null && !"".equals(this.nameValue)) {
            this.dataBrowseProvider.applyWhere("name", cConfiguration.getName() + "." + cConfiguration.NAME.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.nameValue) + "%'");
        }
    }

}
