package com.angkorteam.fintech.pages.admin.system.hook;

import com.angkorteam.fintech.MasterPage;
import com.angkorteam.fintech.MifosDataContextManager;
import com.angkorteam.fintech.client.Function;
import com.angkorteam.fintech.data.MySQLDataProvider;
import com.angkorteam.fintech.meta.tenant.MHook;
import com.angkorteam.fintech.meta.tenant.MHookTemplate;
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
import org.apache.metamodel.data.DataSet;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
@Bookmark("/admin/system/hook/browse")
public class HookBrowsePage extends MasterPage {

    protected Form<Void> filterForm;

    protected UIRow row1;

    protected UIColumn nameColumn;
    protected UIContainer nameContainer;
    protected TextField<String> nameField;
    protected String nameValue;

    protected Button searchButton;
    protected BookmarkablePageLink<Void> clearButton;

    protected FilterForm<Map<String, Expression>> hookBrowseForm;
    protected QueryDataProvider hookBrowseProvider;
    protected List<IColumn<Map<String, Object>, String>> hookBrowseColumn;
    protected AbstractDataTable<Map<String, Object>, String> hookBrowseTable;

    @Override
    protected void onInitData() {
        super.onInitData();
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MHook mHook = MHook.staticInitialize(dataContext);
        MHookTemplate mHookTemplate = MHookTemplate.staticInitialize(dataContext);

        this.hookBrowseProvider = new MySQLDataProvider(mHook.getName());
        this.hookBrowseProvider.applyJoin(mHookTemplate.getName(), "LEFT JOIN " + mHookTemplate.getName() + " ON " + mHook.getName() + "." + mHook.TEMPLATE_ID.getName() + " = " + mHookTemplate.getName() + "." + mHookTemplate.ID.getName());

        this.hookBrowseProvider.setCountField(mHook.getName() + "." + mHook.ID.getName());
        this.hookBrowseProvider.selectField(mHook.getName() + "." + mHook.ID.getName(), "hookId");

        this.hookBrowseColumn = new ArrayList<>();
        this.hookBrowseColumn.add(Column.text(Model.of("Name"), mHook.getName() + "." + mHook.NAME.getName(), "name", this.hookBrowseProvider));
        this.hookBrowseColumn.add(Column.text(Model.of("Template"), mHookTemplate.getName() + "." + mHookTemplate.NAME.getName(), "template", this.hookBrowseProvider));
        this.hookBrowseColumn.add(Column.yesno(Model.of("Active"), mHook.getName() + "." + mHook.ACTIVE.getName(), "active", this.hookBrowseProvider));
        this.hookBrowseColumn.add(new ActionFilteredColumn<>(Model.of("Action"), this::hookBrowseActionLink, this::hookBrowseActionClick));
    }

    @Override
    protected void onInitHtml(MarkupContainer body) {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MHookTemplate mHookTemplate = MHookTemplate.staticInitialize(dataContext);

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

        this.clearButton = new BookmarkablePageLink<>("clearButton", HookBrowsePage.class);
        this.filterForm.add(this.clearButton);

        RepeatingView createLink = new RepeatingView("createLink");
        body.add(createLink);
        try (DataSet rows = dataContext.query().from(mHookTemplate).selectAll().execute()) {
            while (rows.next()) {
                String id = createLink.newChildId();
                WebMarkupContainer container = new WebMarkupContainer(id);
                createLink.add(container);
                PageParameters parameters = new PageParameters();
                parameters.add("templateId", rows.getRow().getValue(mHookTemplate.ID));
                BookmarkablePageLink<Void> link = new BookmarkablePageLink<>("link", HookCreatePage.class, parameters);
                container.add(link);
                Label text = new Label("text", (String) rows.getRow().getValue(mHookTemplate.NAME));
                link.add(text);
            }
        }

        this.hookBrowseForm = new FilterForm<>("hookBrowseForm", this.hookBrowseProvider);
        body.add(this.hookBrowseForm);

        this.hookBrowseTable = new DataTable<>("hookBrowseTable", this.hookBrowseColumn, this.hookBrowseProvider, 20);
        this.hookBrowseForm.add(this.hookBrowseTable);
    }

    protected List<ActionItem> hookBrowseActionLink(String link, Map<String, Object> model) {
        List<ActionItem> actions = new ArrayList<>();
        actions.add(new ActionItem("Edit", Model.of("Edit"), ItemCss.WARNING));
        return actions;
    }

    protected void hookBrowseActionClick(String link, Map<String, Object> model, AjaxRequestTarget target) {
        long hookId = (long) model.get("hookId");
        PageParameters parameters = new PageParameters();
        parameters.add("hookId", hookId);
        setResponsePage(HookModifyPage.class, parameters);
    }

    protected void searchButtonSubmit() {
        ApplicationContext context = WicketFactory.getApplicationContext();
        MifosDataContextManager mifosDataContextManager = context.getBean(MifosDataContextManager.class);
        DataContext dataContext = mifosDataContextManager.getDataContext(getSession().getIdentifier());
        MHook mHook = MHook.staticInitialize(dataContext);

        this.hookBrowseProvider.removeWhere("name");
        if (this.nameValue != null && !"".equals(this.nameValue)) {
            this.hookBrowseProvider.applyWhere("name", mHook.getName() + "." + mHook.NAME.getName() + " LIKE '" + StringEscapeUtils.escapeSql(this.nameValue) + "%'");
        }
    }

}
