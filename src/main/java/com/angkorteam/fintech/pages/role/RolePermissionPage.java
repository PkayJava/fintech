package com.angkorteam.fintech.pages.role;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MPermission;
import com.angkorteam.fintech.ddl.MRolePermission;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.RoleHelper;
import com.angkorteam.fintech.pages.SystemDashboardPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class RolePermissionPage extends Page {

    protected String roleId;

    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected WebMarkupBlock permissionBlock;
    protected WebMarkupContainer permissionIContainer;
    protected MultipleChoiceProvider permissionProvider;
    protected List<Option> permissionValue;
    protected Select2MultipleChoice<Option> permissionField;
    protected TextFeedbackPanel permissionFeedback;

    protected Form<Void> form;
    protected Button addButton;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
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
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initData() {
        PageParameters parameters = getPageParameters();
        this.roleId = parameters.get("roleId").toString("");
    }

    @Override
    protected void initComponent() {

        initDataBlock();

        this.form = new Form<>("form");
        add(this.form);

        this.addButton = new Button("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.form.add(this.addButton);

        initPermissionBlock();
    }

    protected void initPermissionBlock() {
        this.permissionBlock = new WebMarkupBlock("permissionBlock", Size.Twelve_12);
        this.form.add(this.permissionBlock);
        this.permissionIContainer = new WebMarkupContainer("permissionIContainer");
        this.permissionBlock.add(this.permissionIContainer);
        this.permissionProvider = new MultipleChoiceProvider(MPermission.NAME, MPermission.Field.CODE, MPermission.Field.CODE, "CONCAT(" + MPermission.Field.CODE + ",' ', '[', " + MPermission.Field.GROUPING + ",']',  ' ', '[', IF(" + MPermission.Field.ENTITY_NAME + " is NULL , 'N/A', " + MPermission.Field.ENTITY_NAME + "), ']',' ', '[' , IF(" + MPermission.Field.ACTION_NAME + " is NULL , 'N/A', " + MPermission.Field.ACTION_NAME + "),']')");
        this.permissionProvider.applyWhere("id", MPermission.Field.ID + " NOT IN (SELECT " + MRolePermission.Field.PERMISSION_ID + " from " + MRolePermission.NAME + " where " + MRolePermission.Field.ROLE_ID + " = " + this.roleId + ")");
        this.permissionField = new Select2MultipleChoice<>("permissionField", new PropertyModel<>(this, "permissionValue"), this.permissionProvider);
        this.permissionField.setRequired(true);
        this.permissionIContainer.add(this.permissionField);
        this.permissionFeedback = new TextFeedbackPanel("permissionFeedback", this.permissionField);
        this.permissionIContainer.add(this.permissionFeedback);
    }

    protected void initDataBlock() {
        this.dataBlock = new WebMarkupBlock("dataBlock", Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);
        this.dataProvider = new JdbcProvider(MRolePermission.NAME);
        this.dataProvider.applyJoin("m_permission", "INNER JOIN " + MPermission.NAME + " ON " + MRolePermission.NAME + "." + MRolePermission.Field.PERMISSION_ID + " = " + MPermission.NAME + "." + MPermission.Field.ID);
        this.dataProvider.applyWhere("role", MRolePermission.NAME + "." + MRolePermission.Field.ROLE_ID + " = " + this.roleId);
        this.dataProvider.boardField(MPermission.NAME + "." + MPermission.Field.GROUPING, "grouping", String.class);
        this.dataProvider.boardField(MPermission.NAME + "." + MPermission.Field.CODE, "code", String.class);
        this.dataProvider.boardField(MPermission.NAME + "." + MPermission.Field.ENTITY_NAME, "entity_name", String.class);
        this.dataProvider.boardField(MPermission.NAME + "." + MPermission.Field.ACTION_NAME, "action_name", String.class);
        this.dataProvider.setSort("grouping", SortOrder.ASCENDING);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Grouping"), "grouping", "grouping", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Code"), "code", "code", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Entity"), "entity_name", "entity_name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Operation"), "action_name", "action_name", this::dataColumn));
        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        this.dataIContainer.add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void dataClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
        if ("delete".equals(column)) {
            String falseCode = (String) model.get("code");
            JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

            SelectQuery selectQuery = null;

            selectQuery = new SelectQuery(MRolePermission.NAME);
            selectQuery.addJoin("INNER JOIN " + MPermission.NAME + " ON " + MRolePermission.NAME + "." + MRolePermission.Field.PERMISSION_ID + " = " + MPermission.NAME + "." + MPermission.Field.ID);
            selectQuery.addField(MPermission.NAME + "." + MPermission.Field.CODE);
            selectQuery.addWhere(MRolePermission.NAME + "." + MRolePermission.Field.ROLE_ID + " = :" + MRolePermission.Field.ROLE_ID, this.roleId);
            List<String> trueCodes = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), String.class);

            selectQuery = new SelectQuery(MPermission.NAME);
            selectQuery.addField(MPermission.Field.CODE);
            List<String> allCodes = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), String.class);

            Map<String, Boolean> permissions = Maps.newHashMap();
            for (String code : allCodes) {
                permissions.put(code, false);
            }
            for (String code : trueCodes) {
                permissions.put(code, true);
            }

            permissions.put(falseCode, false);

            JsonNode node = RoleHelper.assignPermission((Session) getSession(), this.roleId, permissions);

            if (reportError(node)) {
                return;
            }
            PageParameters parameters = new PageParameters();
            parameters.add("roleId", this.roleId);
            setResponsePage(RolePermissionPage.class, parameters);
        }
    }

    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void addButtonSubmit(Button button) {
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MRolePermission.NAME);
        selectQuery.addJoin("INNER JOIN " + MPermission.NAME + " ON " + MRolePermission.NAME + "." + MRolePermission.Field.PERMISSION_ID + " = " + MPermission.NAME + "." + MPermission.Field.ID);
        selectQuery.addField(MPermission.NAME + "." + MPermission.Field.CODE);
        selectQuery.addWhere(MRolePermission.NAME + "." + MRolePermission.Field.ROLE_ID + " = :" + MRolePermission.Field.ROLE_ID, this.roleId);
        List<String> trueCodes = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), String.class);

        selectQuery = new SelectQuery(MPermission.NAME);
        selectQuery.addField(MPermission.Field.CODE);
        List<String> allCodes = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), String.class);

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

        JsonNode node = RoleHelper.assignPermission((Session) getSession(), this.roleId, permissions);

        if (reportError(node)) {
            return;
        }
        PageParameters parameters = new PageParameters();
        parameters.add("roleId", this.roleId);
        setResponsePage(RolePermissionPage.class, parameters);
    }

    protected ItemPanel idColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Long value = (Long) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("grouping".equals(column) || "code".equals(column) || "entity_name".equals(column) || "action_name".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }
}
