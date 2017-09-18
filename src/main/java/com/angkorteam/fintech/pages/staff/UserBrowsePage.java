package com.angkorteam.fintech.pages.staff;

import java.util.List;
import java.util.Map;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class UserBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> createLink;

    private static final List<PageBreadcrumb> BREADCRUMB;

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("User");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("m_appuser");
        this.provider.addJoin("LEFT JOIN m_office ON m_appuser.office_id = m_office.id");
        this.provider.boardField("m_appuser.id", "id", Long.class);
        this.provider.boardField("m_appuser.username", "username", String.class);
        this.provider.boardField("m_appuser.firstname", "firstname", String.class);
        this.provider.boardField("m_appuser.lastname", "lastname", String.class);
        this.provider.boardField("m_appuser.email", "email", String.class);
        this.provider.boardField("m_office.name", "office", String.class);

        this.provider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Login"), "username", "username", this::loginColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("First Name"), "firstname", "firstname", this::firstNameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Last Name"), "lastname", "lastname", this::lastNameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Email"), "email", "email", this::emailColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Office"), "office", "office", this::officeColumn));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.createLink = new BookmarkablePageLink<>("createLink", UserCreatePage.class);
        add(this.createLink);
    }

    private ItemPanel firstNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String firstName = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(firstName));
    }

    private ItemPanel lastNameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String lastName = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(lastName));
    }

    private ItemPanel officeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String office = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(office));
    }

    private ItemPanel emailColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String email = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(email));
    }

    private ItemPanel loginColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String login = (String) model.get(jdbcColumn);
        PageParameters parameters = new PageParameters();
        parameters.add("userId", model.get("id"));
        return new LinkCell(UserModifyPage.class, parameters, Model.of(String.valueOf(login)));
    }

}
