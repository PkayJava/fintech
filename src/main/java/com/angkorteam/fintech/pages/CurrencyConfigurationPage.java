package com.angkorteam.fintech.pages;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.MCurrency;
import com.angkorteam.fintech.ddl.MOrganisationCurrency;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.helper.CurrencyHelper;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.provider.SingleChoiceProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.TextFeedbackPanel;
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
import com.angkorteam.framework.wicket.markup.html.form.select2.Select2SingleChoice;
import com.google.common.collect.Lists;

import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class CurrencyConfigurationPage extends Page {

    protected Form<Void> form1;
    protected Button addButton;

    protected UIRow row1;
    protected UIBlock currencyBlock;
    protected UIContainer currencyIContainer;
    protected SingleChoiceProvider currencyProvider;
    protected Option currencyValue;
    protected Select2SingleChoice<Option> currencyField;
    protected TextFeedbackPanel currencyFeedback;

    protected FilterForm<Map<String, String>> form2;

    protected UIRow row2;
    protected UIBlock dataBlock;
    protected UIContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;

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
        this.currencyProvider = new SingleChoiceProvider(MCurrency.NAME, MCurrency.Field.CODE, MCurrency.Field.NAME, "CONCAT(" + MCurrency.Field.NAME + ",' [', " + MCurrency.Field.CODE + ",']')");
        this.currencyProvider.applyWhere("code", MCurrency.Field.CODE + " NOT IN (SELECT " + MOrganisationCurrency.Field.CODE + " FROM " + MOrganisationCurrency.NAME + ")");

        this.dataProvider = new JdbcProvider(MOrganisationCurrency.NAME);
        this.dataProvider.boardField(MOrganisationCurrency.Field.ID, "id", Long.class);
        this.dataProvider.boardField(MOrganisationCurrency.Field.CODE, "code", String.class);
        this.dataProvider.boardField(MOrganisationCurrency.Field.NAME, "name", String.class);
        this.dataProvider.boardField(MOrganisationCurrency.Field.DISPLAY_SYMBOL, "symbol", String.class);
        this.dataProvider.setSort("name", SortOrder.ASCENDING);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("ID"), "id", "id", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Code"), "code", "code", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Symbol"), "symbol", "symbol", this::dataColumn));
        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));

    }

    @Override
    protected void initComponent() {
        this.form1 = new Form<>("form1");
        add(this.form1);

        this.addButton = new Button("addButton");
        this.addButton.setOnSubmit(this::addButtonSubmit);
        this.form1.add(this.addButton);

        this.row1 = UIRow.newUIRow("row1", this.form1);

        this.currencyBlock = this.row1.newUIBlock("currencyBlock", Size.Twelve_12);
        this.currencyIContainer = this.currencyBlock.newUIContainer("currencyIContainer");
        this.currencyField = new Select2SingleChoice<>("currencyField", new PropertyModel<>(this, "currencyValue"), this.currencyProvider);
        this.currencyIContainer.add(this.currencyField);
        this.currencyIContainer.newFeedback("currencyFeedback", this.currencyField);

        this.form2 = new FilterForm<>("form2", this.dataProvider);
        add(this.form2);

        this.row2 = UIRow.newUIRow("row2", this.form2);
        this.dataBlock = this.row2.newUIBlock("dataBlock", Size.Twelve_12);
        this.dataIContainer = this.dataBlock.newUIContainer("dataIContainer");
        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.form2));
        this.dataIContainer.add(this.dataTable);
    }

    @Override
    protected void configureMetaData() {
        this.currencyField.setRequired(true);
    }

    protected void dataClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MOrganisationCurrency.NAME);
        selectQuery.addField(MOrganisationCurrency.Field.CODE);
        selectQuery.addWhere(MOrganisationCurrency.Field.CODE + " NOT IN (:code)", "code", model.get("code"));
        List<String> codes = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), String.class);
        CurrencyHelper.update((Session) getSession(), codes);

        setResponsePage(CurrencyConfigurationPage.class);
    }

    protected List<ActionItem> dataAction(String column, Map<String, Object> model) {
        return Lists.newArrayList(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
    }

    protected void addButtonSubmit(Button button) {
        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
        SelectQuery selectQuery = null;
        selectQuery = new SelectQuery(MOrganisationCurrency.NAME);
        selectQuery.addField(MOrganisationCurrency.Field.CODE);
        List<String> temp = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), String.class);
        List<String> codes = Lists.newArrayList(temp);
        codes.add(this.currencyValue.getId());

        JsonNode node = CurrencyHelper.update((Session) getSession(), codes);

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
