package com.angkorteam.fintech.widget.center;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.pages.client.center.ChargePreviewPage;
import com.angkorteam.fintech.pages.product.ShareCreatePage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

public class AccountPreviewChargePanel extends Panel {

    protected String accountId;

    protected Page itemPage;

    protected DataTable<Map<String, Object>, String> dataTable;

    protected JdbcProvider dataProvider;

    public AccountPreviewChargePanel(String id, Page itemPage) {
        super(id);
        this.itemPage = itemPage;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        initData();

        this.dataProvider = new JdbcProvider("m_savings_account_charge");
        this.dataProvider.addJoin("INNER JOIN m_charge ON m_savings_account_charge.charge_id = m_charge.id");
        this.dataProvider.boardField("m_savings_account_charge.id", "id", Long.class);
        this.dataProvider.boardField("m_charge.name", "name", String.class);
        this.dataProvider.boardField("case m_savings_account_charge.is_penalty when 1 then 'Penalty' when 0 then 'Fee' else concat(m_savings_account_charge.is_penalty,'') end", "chargeType", String.class);
        this.dataProvider.boardField("case m_savings_account_charge.charge_time_enum when 7 then 'Monthly' else concat(m_savings_account_charge.charge_time_enum, '') end", "paymentDueAt", String.class);
        this.dataProvider.boardField("m_savings_account_charge.charge_due_date", "dueAsOf", Date.class);
        this.dataProvider.boardField("concat(lpad(m_savings_account_charge.fee_on_day, 2, 0), ' ', CASE m_savings_account_charge.fee_on_month WHEN 1 THEN 'Jan' WHEN 2 THEN 'Feb' WHEN 3 THEN 'Mar' WHEN 4 THEN 'Apr' WHEN 5 THEN 'May' WHEN 6 THEN 'Jun' WHEN 7 THEN 'Jul' WHEN 8 THEN 'Aug' WHEN 9 THEN 'Sep' WHEN 10 THEN 'Oct' WHEN 11 THEN 'Nov' WHEN 12 THEN 'Dec' ELSE m_savings_account_charge.fee_on_month END)", "repeatOn", String.class);
        this.dataProvider.boardField("case m_savings_account_charge.charge_calculation_enum when 1 then 'Flat' else concat(m_savings_account_charge.charge_calculation_enum, '') end", "calculationType", String.class);
        this.dataProvider.boardField("m_savings_account_charge.amount", "due", Double.class);
        this.dataProvider.boardField("m_savings_account_charge.amount_paid_derived", "paid", Double.class);
        this.dataProvider.boardField("m_savings_account_charge.amount_waived_derived", "waived", Double.class);
        this.dataProvider.boardField("m_savings_account_charge.amount_outstanding_derived", "outstanding", Double.class);
        this.dataProvider.boardField("case m_savings_account_charge.is_active when 1 then 'Yes' when 0 then 'No' else concat(m_savings_account_charge.is_active, '') end", "active", String.class);
        this.dataProvider.applyWhere("savings_account_id", "m_savings_account_charge.savings_account_id = " + this.accountId);

        List<IColumn<Map<String, Object>, String>> dataColumns = Lists.newArrayList();
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::nameDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Fee/Penalty"), "chargeType", "chargeType", this::chargeTypeDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Payment due at"), "paymentDueAt", "paymentDueAt", this::paymentDueAtDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Due as of"), "dueAsOf", "dueAsOf", this::dueAsOfDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Repeats On"), "repeatOn", "repeatOn", this::repeatOnDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Calculation Type"), "calculationType", "calculationType", this::calculationTypeDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Due"), "due", "due", this::dueDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Paid"), "paid", "paid", this::paidDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Waived"), "waived", "waived", this::waivedDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Outstanding"), "outstanding", "outstanding", this::outstandingDataColumn));
        dataColumns.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Active"), "active", "active", this::activeDataColumn));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.dataProvider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", dataColumns, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

    }

    protected void initData() {
        this.accountId = new PropertyModel<String>(this.itemPage, "accountId").getObject();
    }

    protected ItemPanel chargeTypeDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel calculationTypeDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel repeatOnDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel activeDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        if ("Yes".equals(value)) {
            return new BadgeCell(BadgeType.Success, value);
        } else if ("No".equals(value)) {
            return new BadgeCell(BadgeType.Danger, value);
        } else {
            return new TextCell(value);
        }
    }

    protected ItemPanel outstandingDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        BigDecimal value = (BigDecimal) model.get(jdbcColumn);
        return new TextCell(value, "#,###.00");
    }

    protected ItemPanel waivedDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        BigDecimal value = (BigDecimal) model.get(jdbcColumn);
        return new TextCell(value, "#,###.00");
    }

    protected ItemPanel paidDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        BigDecimal value = (BigDecimal) model.get(jdbcColumn);
        return new TextCell(value, "#,###.00");
    }

    protected ItemPanel dueDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        BigDecimal value = (BigDecimal) model.get(jdbcColumn);
        return new TextCell(value, "#,###.00");
    }

    protected ItemPanel dueAsOfDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Date value = (Date) model.get(jdbcColumn);
        return new TextCell(value, "dd/MM/yyyy");
    }

    protected ItemPanel paymentDueAtDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new TextCell(value);
    }

    protected ItemPanel nameDataColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String value = (String) model.get(jdbcColumn);
        return new LinkCell(ChargePreviewPage.class, null, value);
    }

    protected ItemPanel dataIdColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Long name = (Long) model.get(jdbcColumn);
        PageParameters parameters = new PageParameters();
        parameters.add("shareId", model.get("id"));
        return new LinkCell(ShareCreatePage.class, parameters, Model.of(name));
    }

}
