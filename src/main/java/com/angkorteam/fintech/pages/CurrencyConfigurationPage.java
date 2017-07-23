package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.helper.CurrencyHelper;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.share.provider.JdbcProvider;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.*;
import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.angkorteam.framework.wicket.markup.html.form.select2.OptionSingleChoiceProvider;
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class CurrencyConfigurationPage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private OptionSingleChoiceProvider currencyProvider;
    private Option currencyValue;
    private Select2SingleChoice<Option> currencyField;
    private TextFeedbackPanel currencyFeedback;

    private Form<Void> form;
    private Button addButton;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("m_organisation_currency");
        this.provider.boardField("id", "id", Long.class);
        this.provider.boardField("code", "code", String.class);
        this.provider.boardField("name", "name", String.class);
        this.provider.boardField("display_symbol", "symbol", String.class);
        this.provider.setSort("name", SortOrder.ASCENDING);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.Long, Model.of("ID"), "id", "id", this::idColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Code"), "code", "code", this::codeColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "name", "name", this::nameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Symbol"), "symbol", "symbol", this::symbolColumn));
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

        this.currencyProvider = new OptionSingleChoiceProvider("m_currency", "code", "name", "concat(name,' [', code,']')");
        this.currencyProvider.applyWhere("code", "code not in (select code from m_organisation_currency)");
        this.currencyField = new Select2SingleChoice<>("currencyField", new PropertyModel<>(this, "currencyValue"), this.currencyProvider);
        this.currencyField.setRequired(true);
        this.form.add(this.currencyField);
        this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
        this.form.add(this.currencyFeedback);
    }

    private void actionClick(String s, Map<String, Object> stringObjectMap, AjaxRequestTarget ajaxRequestTarget) {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        List<String> codes = jdbcTemplate.queryForList("select code from m_organisation_currency where code not in (?)", String.class, stringObjectMap.get("code"));
        JsonNode node = null;
        try {
            node = CurrencyHelper.update(codes);
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (node.getObject().has("errors")) {
            error((String) node.getObject().get("defaultUserMessage"));
        } else if (node.getObject().has("error")) {
            error((String) node.getObject().get("error"));
        }
        setResponsePage(CurrencyConfigurationPage.class);
    }

    private List<ActionItem> actionItem(String s, Map<String, Object> stringObjectMap) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    private void addButtonSubmit(Button button) {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        List<String> temp = jdbcTemplate.queryForList("select code from m_organisation_currency", String.class);
        List<String> codes = Lists.newArrayList(temp);
        codes.add(this.currencyValue.getId());

        JsonNode node = null;
        try {
            node = CurrencyHelper.update(codes);
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(CurrencyConfigurationPage.class);
    }

    private ItemPanel idColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Long id = (Long) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(id)));
    }

    private ItemPanel codeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String externalId = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(externalId));
    }

    private ItemPanel symbolColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String symbol = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(symbol));
    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String name = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(name));
    }
}
