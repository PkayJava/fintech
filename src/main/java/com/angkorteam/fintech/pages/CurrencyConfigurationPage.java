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
import com.angkorteam.fintech.helper.CurrencyHelper;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
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
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CurrencyConfigurationPage extends Page {

    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected WebMarkupBlock currencyBlock;
    protected WebMarkupContainer currencyIContainer;
    protected SingleChoiceProvider currencyProvider;
    protected Option currencyValue;
    protected Select2SingleChoice<Option> currencyField;
    protected TextFeedbackPanel currencyFeedback;

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
            breadcrumb.setLabel("Organization");
            breadcrumb.setPage(OrganizationDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Currency Configuration");
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
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

        initCurrencyBlock();
    }

    protected void initCurrencyBlock() {
        this.currencyBlock = new WebMarkupBlock("currencyBlock", Size.Twelve_12);
        this.form.add(this.currencyBlock);
        this.currencyIContainer = new WebMarkupContainer("currencyIContainer");
        this.currencyBlock.add(this.currencyIContainer);
        this.currencyProvider = new SingleChoiceProvider("m_currency", "code", "name", "concat(name,' [', code,']')");
        this.currencyProvider.applyWhere("code", "code not in (select code from m_organisation_currency)");
        this.currencyField = new Select2SingleChoice<>("currencyField", new PropertyModel<>(this, "currencyValue"), this.currencyProvider);
        this.currencyField.setRequired(true);
        this.currencyIContainer.add(this.currencyField);
        this.currencyFeedback = new TextFeedbackPanel("currencyFeedback", this.currencyField);
        this.currencyIContainer.add(this.currencyFeedback);
    }

    protected void initDataBlock() {
        this.dataBlock = new WebMarkupBlock("dataBlock", Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);
        this.dataProvider = new JdbcProvider("m_organisation_currency");
        this.dataProvider.boardField("id", "id", Long.class);
        this.dataProvider.boardField("code", "code", String.class);
        this.dataProvider.boardField("name", "name", String.class);
        this.dataProvider.boardField("display_symbol", "symbol", String.class);
        this.dataProvider.setSort("name", SortOrder.ASCENDING);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("ID"), "id", "id", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Code"), "code", "code", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Symbol"), "symbol", "symbol", this::dataColumn));
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
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        List<String> codes = jdbcTemplate.queryForList("select code from m_organisation_currency where code not in (?)", String.class, model.get("code"));
        try {
            CurrencyHelper.update((Session) getSession(), codes);
        } catch (UnirestException e) {
        }
        setResponsePage(CurrencyConfigurationPage.class);
    }

    protected List<ActionItem> dataAction(String column, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void addButtonSubmit(Button button) {
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        List<String> temp = jdbcTemplate.queryForList("select code from m_organisation_currency", String.class);
        List<String> codes = Lists.newArrayList(temp);
        codes.add(this.currencyValue.getId());

        JsonNode node = null;
        try {
            node = CurrencyHelper.update((Session) getSession(), codes);
        } catch (UnirestException e) {
            error(e.getMessage());
            return;
        }
        if (reportError(node)) {
            return;
        }
        setResponsePage(CurrencyConfigurationPage.class);
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("id".equals(column)) {
            Long value = (Long) model.get(column);
            return new TextCell(value);
        } else if ("code".equals(column) || "name".equals(column) || "symbol".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
