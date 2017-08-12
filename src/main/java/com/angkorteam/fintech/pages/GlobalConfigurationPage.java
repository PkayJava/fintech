package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.GlobalConfigurationHelper;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class GlobalConfigurationPage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> closeLink;

    private SingleChoiceProvider nameProvider;
    private Option nameValue;
    private Select2SingleChoice<Option> nameField;
    private TextFeedbackPanel nameFeedback;

    private String valueValue;
    private TextField<String> valueField;
    private TextFeedbackPanel valueFeedback;

    private Form<Void> form;
    private Button saveButton;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("c_configuration");
        this.provider.boardField("id", "id", Long.class);
        this.provider.boardField("name", "name", String.class);
        this.provider.boardField("enabled", "enabled", Boolean.class);
        this.provider.boardField("value", "value", Integer.class);

        this.provider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "name", "name",
                this::nameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Enabled ?"), "enabled", "enabled",
                this::enabledColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Integer, Model.of("Value"), "value", "value",
                this::valueColumn));
        columns.add(new ActionFilterColumn<>(Model.of("Action"), this::actionItem, this::actionClick));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.closeLink = new BookmarkablePageLink<>("closeLink", SystemDashboardPage.class);
        add(this.closeLink);

        this.form = new Form<>("form");
        add(this.form);

        this.saveButton = new Button("saveButton");
        this.saveButton.setOnSubmit(this::saveButtonSubmit);
        this.form.add(this.saveButton);

        this.nameProvider = new SingleChoiceProvider("c_configuration", "id", "name");
        this.nameField = new Select2SingleChoice<>("nameField", 0, new PropertyModel<>(this, "nameValue"),
                this.nameProvider);
        this.nameField.setRequired(true);
        this.form.add(this.nameField);
        this.nameFeedback = new TextFeedbackPanel("nameFeedback", this.nameField);
        this.form.add(this.nameFeedback);

        this.valueField = new TextField<>("valueField", new PropertyModel<>(this, "valueValue"));
        this.valueField.setRequired(true);
        this.form.add(this.valueField);
        this.valueFeedback = new TextFeedbackPanel("valueFeedback", this.valueField);
        this.form.add(this.valueFeedback);
    }

    private void saveButtonSubmit(Button button) {
        JsonNode node = null;
        try {
            node = GlobalConfigurationHelper.updateValueForGlobalConfiguration((Session) getSession(),
                    this.nameValue.getId(), this.valueValue);
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(GlobalConfigurationPage.class);
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        try {
            Long id = (Long) stringObjectMap.get("id");
            if ("enable".equals(s)) {
                GlobalConfigurationHelper.updateEnabledFlagForGlobalConfiguration((Session) getSession(),
                        String.valueOf(id), true);
                ajaxRequestTarget.add(this.dataTable);
            } else if ("disable".equals(s)) {
                GlobalConfigurationHelper.updateEnabledFlagForGlobalConfiguration((Session) getSession(),
                        String.valueOf(id), false);
                ajaxRequestTarget.add(this.dataTable);
            }
        } catch (UnirestException e) {
        }
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
        List<ActionItem> actions = Lists.newArrayList();
        Boolean enabled = (Boolean) stringObjectMap.get("enabled");
        if (enabled != null && enabled) {
            actions.add(new ActionItem("disable", Model.of("Disable"), ItemCss.PRIMARY));
        } else {
            actions.add(new ActionItem("enable", Model.of("Enable"), ItemCss.PRIMARY));
        }
        return actions;
    }

    private ItemPanel enabledColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean enabled = (Boolean) model.get(jdbcColumn);
        if (enabled != null && enabled) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String name = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(name));
    }

    private ItemPanel valueColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Integer value = (Integer) model.get(jdbcColumn);
        return new TextCell(Model.of(value == null ? "" : String.valueOf(value)));
    }

}
