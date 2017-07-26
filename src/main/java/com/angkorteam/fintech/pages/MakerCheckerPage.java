package com.angkorteam.fintech.pages;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.helper.RoleHelper;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.share.provider.JdbcProvider;
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
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionMultipleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2MultipleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class MakerCheckerPage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private OptionMultipleChoiceProvider permissionProvider;
    private List<Option> permissionValue;
    private Select2MultipleChoice<Option> permissionField;
    private TextFeedbackPanel permissionFeedback;

    private Form<Void> form;
    private Button addButton;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.provider = new JdbcProvider("m_permission");
        // this.provider.boardField("id", "id", Long.class);
        this.provider.boardField("m_permission.grouping", "grouping", String.class);
        this.provider.boardField("m_permission.code", "code", String.class);
        this.provider.boardField("m_permission.entity_name", "entity_name", String.class);
        this.provider.boardField("m_permission.action_name", "action_name", String.class);
        this.provider.boardField("m_permission.can_maker_checker", "can_maker_checker", String.class);
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
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Is Enabled ?"), "can_maker_checker",
                "can_maker_checker", this::enabledColumn));
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

        this.permissionProvider = new OptionMultipleChoiceProvider("m_permission", "code", "code",
                "concat(code,' ', '[', grouping,']',  ' ', '[', IF(entity_name is NULL , 'N/A', entity_name), ']',' ', '[' , IF(action_name is NULL , 'N/A', action_name),']')");
        this.permissionProvider.applyWhere("maker", "can_maker_checker = 0");
        this.permissionField = new Select2MultipleChoice<>("permissionField",
                new PropertyModel<>(this, "permissionValue"), this.permissionProvider);
        this.permissionField.setRequired(true);
        this.form.add(this.permissionField);
        this.permissionFeedback = new TextFeedbackPanel("permissionFeedback", this.permissionField);
        this.form.add(this.permissionFeedback);
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {

        String falseCode = (String) stringObjectMap.get("code");
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        List<String> trueCodes = jdbcTemplate.queryForList(
                "select m_permission.code from m_permission where can_maker_checker = ?", String.class, 1);
        List<String> allCodes = jdbcTemplate.queryForList("select code from m_permission", String.class);
        Map<String, Boolean> permissions = Maps.newHashMap();
        for (String code : allCodes) {
            permissions.put(code, false);
        }
        for (String code : trueCodes) {
            permissions.put(code, true);
        }

        if ("disable".equals(s)) {
            permissions.put(falseCode, false);
        } else if ("enable".equals(s)) {
            permissions.put(falseCode, true);
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
        ajaxRequestTarget.add(this.dataTable);
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
        List<ActionItem> actions = Lists.newArrayList();
        Boolean enabled = (Boolean) stringObjectMap.get("can_maker_checker");
        if (enabled != null && enabled) {
            actions.add(new ActionItem("disable", Model.of("Disable"), ItemCss.INFO));
        } else {
            actions.add(new ActionItem("enable", Model.of("Enable"), ItemCss.INFO));
        }
        return actions;

    }

    private void addButtonSubmit(Button button) {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);

        List<String> trueCodes = jdbcTemplate.queryForList(
                "select m_permission.code from m_permission where can_maker_checker = ?", String.class, 1);
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

    private ItemPanel enabledColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean enabled = (Boolean) model.get(jdbcColumn);
        if (enabled != null && enabled) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
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
