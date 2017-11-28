package com.angkorteam.fintech.pages.client.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.Calendar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ChargeOverviewPage extends Page {

    protected String clientId;

    protected BookmarkablePageLink<Void> closeLink;

    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    @Override
    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
    }

    @Override
    protected void initComponent() {

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        add(this.closeLink);

        this.dataBlock = new WebMarkupBlock("dataBlock", Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);

        this.dataProvider = new JdbcProvider("m_client_charge");
        this.dataProvider.applyJoin("m_charge", "inner join m_charge on m_client_charge.charge_id = m_charge.id");

        this.dataProvider.boardField("m_client_charge.id", "id", Long.class);
        this.dataProvider.boardField("m_charge.name", "name", String.class);
        this.dataProvider.boardField("m_client_charge.charge_due_date", "due_date", Calendar.Date);
        this.dataProvider.boardField("m_client_charge.amount", "due", Double.class);
        this.dataProvider.boardField("m_client_charge.amount_paid_derived", "paid", Double.class);
        this.dataProvider.boardField("m_client_charge.amount_waived_derived", "waived", Double.class);
        this.dataProvider.boardField("m_client_charge.amount_outstanding_derived", "outstanding", Double.class);

        this.dataProvider.applyWhere("amount_outstanding_derived", "m_client_charge.amount_outstanding_derived = 0");
        this.dataProvider.applyWhere("client_id", "m_client_charge.client_id = " + this.clientId);

        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Due as of"), "due_date", "due_date", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Due"), "due", "due", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Paid"), "paid", "paid", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Waived"), "waived", "waived", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Outstanding"), "outstanding", "outstanding", this::dataColumn));

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

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column)) {
            String value = (String) model.get(column);
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            parameters.add("chargeId", model.get("id"));
            return new LinkCell(ChargeTransactionPage.class, parameters, value);
        } else if ("due_date".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "yyyy-MM-dd");
        } else if ("due".equals(column) || "paid".equals(column) || "waived".equals(column) || "outstanding".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value == null ? 0 : value, "#,###,##0.00");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
