package com.angkorteam.fintech.pages.admin.system.configuration;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.data.MySQLDataProvider;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.meta.CConfiguration;
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
public class ConfigurationBrowsePage extends MasterPage {

    protected Form<Void> filterForm;

    protected UIRow row1;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected Button searchButton;
    protected BookmarkablePageLink<Void> clearButton;

    protected BookmarkablePageLink<Void> createLink;

    protected FilterForm<Map<String, Expression>> configurationBrowseForm;
    protected QueryDataProvider configurationBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> configurationBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> configurationBrowseTable;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        CConfiguration cConfiguration = CConfiguration.staticInitialize(dataContext);

        this.configurationBrowseProvider = new MySQLDataProvider(cConfiguration.getName());

        this.configurationBrowseProvider.setCountField(cConfiguration.getName() + "." + cConfiguration.ID.getName());
        this.configurationBrowseProvider.selectField(cConfiguration.getName() + "." + cConfiguration.ID.getName(), "configurationId");

        this.configurationBrowseColumn = new ArrayList<>();
        this.configurationBrowseColumn.add(Column.text(Model.of("Name"), cConfiguration.getName() + "." + cConfiguration.NAME.getName(), "name", this.configurationBrowseProvider));
        this.configurationBrowseColumn.add(Column.number(Model.of("Value"), cConfiguration.getName() + "." + cConfiguration.VALUE.getName(), "value", this.configurationBrowseProvider));
        this.configurationBrowseColumn.add(Column.datetime(Model.of("Date Value"), cConfiguration.getName() + "." + cConfiguration.DATE_VALUE.getName(), "dateValue", this.configurationBrowseProvider, "dd MMM yyyy"));
        this.configurationBrowseColumn.add(Column.yesno(Model.of("Enabled"), cConfiguration.getName() + "." + cConfiguration.ENABLED.getName(), "enabled", this.configurationBrowseProvider));
        this.configurationBrowseColumn.add(Column.yesno(Model.of("Trap Door"), cConfiguration.getName() + "." + cConfiguration.TRAP_DOOR.getName(), "trap_door", this.configurationBrowseProvider));
        this.configurationBrowseColumn.add(Column.text(Model.of("Description"), cConfiguration.getName() + "." + cConfiguration.DESCRIPTION.getName(), "description", this.configurationBrowseProvider));
        this.configurationBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::configurationBrowseActionLink, this::configurationBrowseActionClick));
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

        this.clearButton = new BookmarkablePageLink<>("clearButton", ConfigurationBrowsePage.class);
        this.filterForm.add(this.clearButton);

        this.createLink = new BookmarkablePageLink<>("createLink", ConfigurationCreatePage.class);
        body.add(this.createLink);

        this.configurationBrowseForm = new FilterForm<>("configurationBrowseForm", this.configurationBrowseProvider);
        body.add(this.configurationBrowseForm);

        this.configurationBrowseTable = new DataTable<>("configurationBrowseTable", this.configurationBrowseColumn, this.configurationBrowseProvider, 20);
        this.configurationBrowseForm.add(this.configurationBrowseTable);
    }

    protected List<ActionItem> configurationBrowseActionLink(String link, Map<String, Object> model) {
        List<ActionItem> actions = new ArrayList<>();
        actions.add(new ActionItem("Edit", Model.of("Edit"), ItemCss.WARNING));
        return actions;
    }

    protected void configurationBrowseActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        long configurationId = (long) model.get("configurationId");
        PageParameters parameters = new PageParameters();
        parameters.add("configurationId", configurationId);
        setResponsePage(ConfigurationModifyPage.class, parameters);
    }

    protected void searchButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        CConfiguration cConfiguration = CConfiguration.staticInitialize(dataContext);

        this.configurationBrowseProvider.removeWhere("name");
        if (this.nameValue != null && !"".equals(this.nameValue)) {
            this.configurationBrowseProvider.applyWhere("name", cConfiguration.getName() + "." + cConfiguration.NAME.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.nameValue) + "%'");
        }
    }

}
