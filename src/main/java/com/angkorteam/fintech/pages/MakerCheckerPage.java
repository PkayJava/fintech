package com.angkorteam.fintech.pages;

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

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.RoleHelper;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.provider.MultipleChoiceProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.models.PageBreadcrumb;
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
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class MakerCheckerPage extends Page {

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

    protected static final List<PageBreadcrumb> BREADCRUMB;

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
            breadcrumb.setLabel("Maker Checker Setting");
            BREADCRUMB.add(breadcrumb);
        }
    }

    @Override
    protected void initData() {
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
        this.permissionProvider = new MultipleChoiceProvider("m_permission", "code", "code", "concat(code,' ', '[', grouping,']',  ' ', '[', IF(entity_name is NULL , 'N/A', entity_name), ']',' ', '[' , IF(action_name is NULL , 'N/A', action_name),']')");
        this.permissionProvider.applyWhere("maker", "can_maker_checker = 0");
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
        this.dataProvider = new JdbcProvider("m_permission");
        this.dataProvider.boardField("m_permission.grouping", "grouping", String.class);
        this.dataProvider.boardField("m_permission.code", "code", String.class);
        this.dataProvider.boardField("m_permission.entity_name", "entity_name", String.class);
        this.dataProvider.boardField("m_permission.action_name", "action_name", String.class);
        this.dataProvider.boardField("m_permission.can_maker_checker", "can_maker_checker", String.class);
        this.dataProvider.setSort("grouping", SortOrder.ASCENDING);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Grouping"), "grouping", "grouping", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Code"), "code", "code", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Entity"), "entity_name", "entity_name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Operation"), "action_name", "action_name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Is Enabled ?"), "can_maker_checker", "can_maker_checker", this::dataColumn));
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

    protected void dataClick(String s, Map<String, Object> model, AjaxRequestTarget target) {
        String code = (String) model.get("code");
        Map<String, Boolean> permissions = Maps.newHashMap();
        if ("disable".equals(s)) {
            permissions.put(code, false);
        } else if ("enable".equals(s)) {
            permissions.put(code, true);
        }

        JsonNode node = null;
        try {
            node = RoleHelper.makerCheckerPermission((Session) getSession(), permissions);
        } catch (UnirestException e) {
            error(e.getMessage());
            setResponsePage(MakerCheckerPage.class);
            return;
        }
        if (reportError(node)) {
            setResponsePage(MakerCheckerPage.class);
            return;
        }
        if (target != null) {
            target.add(this.dataTable);
        }
    }

    protected List<ActionItem> dataAction(String s, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        Boolean enabled = (Boolean) model.get("can_maker_checker");
        if (enabled != null && enabled) {
            actions.add(new ActionItem("disable", Model.of("Disable"), ItemCss.INFO));
        } else {
            actions.add(new ActionItem("enable", Model.of("Enable"), ItemCss.INFO));
        }
        return actions;

    }

    protected void addButtonSubmit(Button button) {
        Map<String, Boolean> permissions = Maps.newHashMap();
        if (this.permissionValue != null) {
            for (Option option : this.permissionValue) {
                permissions.put(option.getId(), true);
            }
        }

        JsonNode node = null;
        try {
            node = RoleHelper.makerCheckerPermission((Session) getSession(), permissions);
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(MakerCheckerPage.class);
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("grouping".equals(column) || "action_name".equals(column) || "code".equals(column) || "entity_name".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("can_maker_checker".equals(column)) {
            Boolean value = (Boolean) model.get(column);
            if (value != null && value) {
                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
            } else {
                return new BadgeCell(BadgeType.Danger, Model.of("No"));
            }
        }
        throw new WicketRuntimeException("Unknown " + column);
    }
}
