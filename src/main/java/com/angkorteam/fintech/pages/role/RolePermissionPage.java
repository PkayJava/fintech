package com.angkorteam.fintech.pages.role;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.RoleHelper;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RolePermissionPage extends Page {

    private String roleId;

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private MultipleChoiceProvider permissionProvider;
    private List<Option> permissionValue;
    private Select2MultipleChoice<Option> permissionField;
    private TextFeedbackPanel permissionFeedback;

    private Form<Void> form;
    private Button addButton;

    private static final List<PageBreadcrumb> BREADCRUMB;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        return Model.ofList(BREADCRUMB);
    }

    static {
        BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Admin");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("System");
            breadcrumb.setPage(SystemDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Role");
            breadcrumb.setPage(RoleBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Permission");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters parameters = getPageParameters();
        this.roleId = parameters.get("roleId").toString("");

        this.provider = new JdbcProvider("m_role_permission");
        this.provider.addJoin("INNER JOIN m_permission ON m_role_permission.permission_id = m_permission.id");
        this.provider.applyWhere("role", "m_role_permission.role_id = " + this.roleId);
        // this.provider.boardField("id", "id", Long.class);
        this.provider.boardField("m_permission.grouping", "grouping", String.class);
        this.provider.boardField("m_permission.code", "code", String.class);
        this.provider.boardField("m_permission.entity_name", "entity_name", String.class);
        this.provider.boardField("m_permission.action_name", "action_name", String.class);
        this.provider.setSort("grouping", SortOrder.ASCENDING);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        // columns.add(new TextFilterColumn(this.provider, ItemClass.Long,
        // Model.of("ID"), "id", "id", this::idColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Grouping"), "grouping", "grouping",
                this::groupingColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Code"), "code", "code",
                this::codeColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Entity"), "entity_name",
                "entity_name", this::entityColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Operation"), "action_name",
                "action_name", this::operationColumn));
        columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.form = new Form<>("form");
        add(this.form);

        this.addButton = new Button("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.form.add(this.addButton);

        this.permissionProvider = new MultipleChoiceProvider("m_permission", "code", "code",
                "concat(code,' ', '[', grouping,']',  ' ', '[', IF(entity_name is NULL , 'N/A', entity_name), ']',' ', '[' , IF(action_name is NULL , 'N/A', action_name),']')");
        this.permissionProvider.applyWhere("id",
                "id not IN (SELECT permission_id from m_role_permission where  role_id = " + this.roleId + ")");
        this.permissionField = new Select2MultipleChoice<>("permissionField",
                new PropertyModel<>(this, "permissionValue"), this.permissionProvider);
        this.permissionField.setRequired(true);
        this.form.add(this.permissionField);
        this.permissionFeedback = new TextFeedbackPanel("permissionFeedback", this.permissionField);
        this.form.add(this.permissionFeedback);
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        if ("delete".equals(s)) {
            String falseCode = (String) stringObjectMap.get("code");
            JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

            List<String> trueCodes = jdbcTemplate.queryForList(
                    "select m_permission.code from m_role_permission INNER JOIN m_permission ON m_role_permission.permission_id = m_permission.id where m_role_permission.role_id = ?",
                    String.class, this.roleId);
            List<String> allCodes = jdbcTemplate.queryForList("select code from m_permission", String.class);
            Map<String, Boolean> permissions = Maps.newHashMap();
            for (String code : allCodes) {
                permissions.put(code, false);
            }
            for (String code : trueCodes) {
                permissions.put(code, true);
            }

            permissions.put(falseCode, false);

            JsonNode node = null;
            try {
                node = RoleHelper.assignPermission((Session) getSession(), this.roleId, permissions);
            } catch (UnirestException e) {
                error(e.getMessage());
                return;
            }
            if (reportError(node)) {
                return;
            }
            PageParameters parameters = new PageParameters();
            parameters.add("roleId", this.roleId);
            setResponsePage(RolePermissionPage.class, parameters);
        }
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private void addButtonSubmit(Button button) {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        List<String> trueCodes = jdbcTemplate.queryForList(
                "select m_permission.code from m_role_permission INNER JOIN m_permission ON m_role_permission.permission_id = m_permission.id where m_role_permission.role_id = ?",
                String.class, this.roleId);
        List<String> allCodes = jdbcTemplate.queryForList("select code from m_permission", String.class);
        Map<String, Boolean> permissions = Maps.newHashMap();
        for (String code : allCodes) {
            permissions.put(code, false);
        }
        for (String code : trueCodes) {
            permissions.put(code, true);
        }
        if (this.permissionValue != null) {
            for (Option option : this.permissionValue) {
                permissions.put(option.getId(), true);
            }
        }

        JsonNode node = null;
        try {
            node = RoleHelper.assignPermission((Session) getSession(), this.roleId, permissions);
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        PageParameters parameters = new PageParameters();
        parameters.add("roleId", this.roleId);
        setResponsePage(RolePermissionPage.class, parameters);
    }

    private ItemPanel idColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Long id = (Long) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(id)));
    }

    private ItemPanel codeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String externalId = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(externalId));
    }

    private ItemPanel entityColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String entity = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(entity));
    }

    private ItemPanel groupingColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String grouping = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(grouping));
    }

    private ItemPanel operationColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String operation = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(operation));
    }
}
