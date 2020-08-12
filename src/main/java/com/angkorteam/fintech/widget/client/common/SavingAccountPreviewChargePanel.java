//package com.angkorteam.fintech.widget.client.common;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.Page;
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.model.PropertyModel;
//
//import com.angkorteam.fintech.ddl.MCharge;
//import com.angkorteam.fintech.ddl.MSavingsAccountCharge;
//import com.angkorteam.fintech.pages.client.center.ChargePreviewPage;
//import com.angkorteam.fintech.provider.JdbcProvider;
//import com.angkorteam.fintech.table.BadgeCell;
//import com.angkorteam.fintech.table.LinkCell;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.fintech.widget.Panel;
//import com.angkorteam.framework.BadgeType;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
//import com.google.common.collect.Lists;
//
//public class SavingAccountPreviewChargePanel extends Panel {
//
//    protected String savingId;
//
//    protected Page itemPage;
//
//    protected List<IColumn<Map<String, Object>, String>> dataColumn;
//    protected DataTable<Map<String, Object>, String> dataTable;
//    protected JdbcProvider dataProvider;
//    protected FilterForm<Map<String, String>> dataFilterForm;
//
//    public SavingAccountPreviewChargePanel(String id, Page itemPage) {
//        super(id);
//        this.itemPage = itemPage;
//    }
//
//    @Override
//    protected void initComponent() {
//
//        this.dataProvider = new JdbcProvider(MSavingsAccountCharge.NAME);
//        this.dataProvider.applyJoin("m_charge", "INNER JOIN " + MCharge.NAME + " ON " + MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.CHARGE_ID + " = " + MCharge.NAME + "." + MCharge.Field.ID);
//
//        this.dataProvider.boardField(MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.ID, "id", Long.class);
//        this.dataProvider.boardField(MCharge.NAME + "." + MCharge.Field.NAME, "name", String.class);
//        this.dataProvider.boardField("CASE " + MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.IS_PENALTY + " WHEN 1 THEN 'Penalty' WHEN 0 THEN 'Fee' ELSE CONCAT(" + MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.IS_PENALTY + ",'') END", "chargeType", String.class);
//        this.dataProvider.boardField("CASE " + MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.CHARGE_TIME_ENUM + " WHEN 7 THEN 'Monthly' ELSE CONCAT(" + MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.CHARGE_TIME_ENUM + ", '') END", "paymentDueAt", String.class);
//        this.dataProvider.boardField("" + MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.CHARGE_DUE_DATE, "dueAsOf", Date.class);
//        this.dataProvider.boardField("CONCAT(lpad(" + MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.FEE_ON_DAY + ", 2, 0), ' ', CASE " + MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.FEE_ON_MONTH + " WHEN 1 THEN 'Jan' WHEN 2 THEN 'Feb' WHEN 3 THEN 'Mar' WHEN 4 THEN 'Apr' WHEN 5 THEN 'May' WHEN 6 THEN 'Jun' WHEN 7 THEN 'Jul' WHEN 8 THEN 'Aug' WHEN 9 THEN 'Sep' WHEN 10 THEN 'Oct' WHEN 11 THEN 'Nov' WHEN 12 THEN 'Dec' ELSE " + MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.FEE_ON_MONTH + " END)", "repeatOn", String.class);
//        this.dataProvider.boardField("CASE " + MSavingsAccountCharge.NAME + ".charge_calculation_enum WHEN 1 THEN 'Flat' ELSE CONCAT(" + MSavingsAccountCharge.NAME + ".charge_calculation_enum, '') END", "calculationType", String.class);
//        this.dataProvider.boardField(MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.AMOUNT, "due", Double.class);
//        this.dataProvider.boardField(MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.AMOUNT_PAID_DERIVED, "paid", Double.class);
//        this.dataProvider.boardField(MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.AMOUNT_WAIVED_DERIVED, "waived", Double.class);
//        this.dataProvider.boardField(MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.AMOUNT_OUTSTANDING_DERIVED, "outstanding", Double.class);
//        this.dataProvider.boardField("CASE " + MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.IS_ACTIVE + " WHEN 1 THEN 'Yes' WHEN 0 THEN 'No' ELSE CONCAT(" + MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.IS_ACTIVE + ", '') END", "active", String.class);
//        this.dataProvider.applyWhere("savings_account_id", MSavingsAccountCharge.NAME + "." + MSavingsAccountCharge.Field.SAVINGS_ACCOUNT_ID + " = " + this.savingId);
//
//        this.dataColumn = Lists.newArrayList();
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Fee/Penalty"), "chargeType", "chargeType", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Payment due at"), "paymentDueAt", "paymentDueAt", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Due as of"), "dueAsOf", "dueAsOf", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Repeats On"), "repeatOn", "repeatOn", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Calculation Type"), "calculationType", "calculationType", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Due"), "due", "due", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Paid"), "paid", "paid", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Waived"), "waived", "waived", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Outstanding"), "outstanding", "outstanding", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Active"), "active", "active", this::dataColumn));
//
//        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
//        add(this.dataFilterForm);
//
//        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
//        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
//        this.dataFilterForm.add(this.dataTable);
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//    @Override
//    protected void initData() {
//        this.savingId = new PropertyModel<String>(this.itemPage, "savingId").getObject();
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("name".equals(column)) {
//            String value = (String) model.get(column);
//            return new LinkCell(ChargePreviewPage.class, null, value);
//        } else if ("chargeType".equals(column) || "calculationType".equals(column) || "paymentDueAt".equals(column) || "repeatOn".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        } else if ("dueAsOf".equals(column)) {
//            Date value = (Date) model.get(column);
//            return new TextCell(value, "dd/MM/yyyy");
//        } else if ("due".equals(column) || "paid".equals(column) || "waived".equals(column) || "outstanding".equals(column)) {
//            Double value = (Double) model.get(column);
//            return new TextCell(value, "#,###,##0.00");
//        } else if ("active".equals(column)) {
//            String value = (String) model.get(column);
//            if ("Yes".equals(value)) {
//                return new BadgeCell(BadgeType.Success, value);
//            } else if ("No".equals(value)) {
//                return new BadgeCell(BadgeType.Danger, value);
//            } else {
//                return new TextCell(value);
//            }
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//}
