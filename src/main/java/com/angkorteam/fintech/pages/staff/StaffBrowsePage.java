package com.angkorteam.fintech.pages.staff;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class StaffBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> createLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("m_staff");
        this.provider.addJoin("LEFT JOIN m_office ON m_staff.office_id = m_office.id");
        this.provider.boardField("m_staff.id", "id", Long.class);
        this.provider.boardField("m_staff.firstname", "firstname", String.class);
        this.provider.boardField("m_staff.lastname", "lastname", String.class);
        this.provider.boardField("m_staff.is_loan_officer", "loan_officer", Boolean.class);
        this.provider.boardField("m_staff.is_active", "active", Boolean.class);
        this.provider.boardField("m_office.name", "office", String.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.Long, Model.of("ID"), "id", "id", this::idColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("First Name"), "firstname", "firstname", this::firstNameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Last Name"), "lastname", "lastname", this::lastNameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Boolean, Model.of("Is Loan Officer"), "loan_officer", "loan_officer", this::loanOfficerColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Boolean, Model.of("Active"), "active", "active", this::activeColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Office"), "office", "office", this::officeColumn));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.createLink = new BookmarkablePageLink<>("createLink", StaffCreatePage.class);
        add(this.createLink);
    }

    private ItemPanel idColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Long id = (Long) model.get(jdbcColumn);
        PageParameters parameters = new PageParameters();
        parameters.add("staffId", model.get("id"));
        return new LinkCell(StaffModifyPage.class, parameters, Model.of(String.valueOf(id)));
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

    private ItemPanel activeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean active = (Boolean) model.get(jdbcColumn);
        if (active != null && active) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
    }

    private ItemPanel loanOfficerColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean loanOfficer = (Boolean) model.get(jdbcColumn);
        if (loanOfficer != null && loanOfficer) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
    }

}
