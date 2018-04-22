package com.angkorteam.fintech.pages.client.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MCharge;
import com.angkorteam.fintech.ddl.MClient;
import com.angkorteam.fintech.ddl.MClientCharge;
import com.angkorteam.fintech.ddl.MClientChargePaidBy;
import com.angkorteam.fintech.ddl.MClientTransaction;
import com.angkorteam.fintech.ddl.MCurrency;
import com.angkorteam.fintech.ddl.MOffice;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.layout.Size;
import com.angkorteam.fintech.layout.UIBlock;
import com.angkorteam.fintech.layout.UIContainer;
import com.angkorteam.fintech.layout.UIRow;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.Calendar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ChargeTransactionPage extends Page {

    public static final String TYPE_WAIVE = "2";

    public static final String TYPE_PAY = "1";

    protected String clientId;
    protected String chargeId;

    protected String clientDisplayName;
    protected String chargeName;

    protected BookmarkablePageLink<Void> closeLink;

    protected UIRow row1;

    protected UIBlock chargeBlock;
    protected UIContainer chargeVContainer;
    protected String chargeValue;
    protected ReadOnlyView chargeView;

    protected UIBlock row1Block1;

    protected UIRow row2;

    protected UIBlock currencyBlock;
    protected UIContainer currencyVContainer;
    protected String currencyValue;
    protected ReadOnlyView currencyView;

    protected UIBlock dueDateBlock;
    protected UIContainer dueDateVContainer;
    protected Date dueDateValue;
    protected ReadOnlyView dueDateView;

    protected UIRow row3;

    protected UIBlock chargeTypeBlock;
    protected UIContainer chargeTypeVContainer;
    protected Option chargeTypeValue;
    protected ReadOnlyView chargeTypeView;

    protected UIBlock chargeCalculationBlock;
    protected UIContainer chargeCalculationVContainer;
    protected Option chargeCalculationValue;
    protected ReadOnlyView chargeCalculationView;

    protected UIRow row4;

    protected UIBlock dueBlock;
    protected UIContainer dueVContainer;
    protected Double dueValue;
    protected ReadOnlyView dueView;

    protected UIBlock paidBlock;
    protected UIContainer paidVContainer;
    protected Double paidValue;
    protected ReadOnlyView paidView;

    protected UIBlock waivedBlock;
    protected UIContainer waivedVContainer;
    protected Double waivedValue;
    protected ReadOnlyView waivedView;

    protected UIBlock outstandingBlock;
    protected UIContainer outstandingVContainer;
    protected Double outstandingValue;
    protected ReadOnlyView outstandingView;

    protected UIRow row5;

    protected UIBlock dataBlock;
    protected UIContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;

    @Override
    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
        this.chargeId = getPageParameters().get("chargeId").toString();

        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);

        SelectQuery selectQuery = null;

        selectQuery = new SelectQuery(MClient.NAME);
        selectQuery.addField(MClient.Field.DISPLAY_NAME);
        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
        this.clientDisplayName = (String) clientObject.get("display_name");

        selectQuery = new SelectQuery(MClientCharge.NAME);
        selectQuery.addJoin("INNER JOIN " + MCharge.NAME + " ON " + MClientCharge.NAME + "." + MClientCharge.Field.CHARGE_ID + " = " + MCharge.NAME + "." + MCharge.Field.ID);
        selectQuery.addJoin("INNER JOIN " + MCurrency.NAME + " ON " + MCurrency.NAME + "." + MCurrency.Field.CODE + " = " + MCharge.NAME + "." + MCharge.Field.CURRENCY_CODE);
        selectQuery.addWhere(MClientCharge.NAME + "." + MClientCharge.Field.ID + " = :" + MClientCharge.Field.ID, this.chargeId);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.NAME);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_TIME_ENUM);
        selectQuery.addField(MCharge.NAME + "." + MCharge.Field.CHARGE_CALCULATION_ENUM);
        selectQuery.addField(MCurrency.NAME + "." + MCurrency.Field.NAME);
        selectQuery.addField(MClientCharge.NAME + "." + MClientCharge.Field.CHARGE_DUE_DATE);
        selectQuery.addField(MClientCharge.NAME + "." + MClientCharge.Field.AMOUNT);
        selectQuery.addField(MClientCharge.NAME + "." + MClientCharge.Field.AMOUNT_PAID_DERIVED);
        selectQuery.addField(MClientCharge.NAME + "." + MClientCharge.Field.AMOUNT_WAIVED_DERIVED);
        selectQuery.addField(MClientCharge.NAME + "." + MClientCharge.Field.AMOUNT_OUTSTANDING_DERIVED);
        Map<String, Object> chargeObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());

        this.chargeValue = (String) chargeObject.get("m_charge.name");
        this.chargeName = (String) chargeObject.get("m_charge.name");

        this.currencyValue = (String) chargeObject.get("m_currency.name");
        this.dueDateValue = (Date) chargeObject.get("m_client_charge.charge_due_date");

        this.chargeTypeValue = ChargeTime.optionLiteral(String.valueOf(chargeObject.get("m_charge.charge_time_enum")));
        this.chargeCalculationValue = ChargeCalculation.optionLiteral(String.valueOf(chargeObject.get("m_charge.charge_calculation_enum")));

        this.dueValue = chargeObject.get("m_client_charge.amount") == null ? 0 : (Double) chargeObject.get("m_client_charge.amount");
        this.paidValue = chargeObject.get("m_client_charge.amount_paid_derived") == null ? 0 : (Double) chargeObject.get("m_client_charge.amount_paid_derived");
        this.waivedValue = chargeObject.get("m_client_charge.amount_waived_derived") == null ? 0 : (Double) chargeObject.get("m_client_charge.amount_waived_derived");
        this.outstandingValue = chargeObject.get("m_client_charge.amount_outstanding_derived") == null ? 0 : (Double) chargeObject.get("m_client_charge.amount_outstanding_derived");

        this.dataProvider = new JdbcProvider(MClientChargePaidBy.NAME);
        this.dataProvider.applyJoin("m_client_transaction", "INNER JOIN " + MClientTransaction.NAME + " ON " + MClientChargePaidBy.NAME + "." + MClientChargePaidBy.Field.CLIENT_TRANSACTION_ID + " = " + MClientTransaction.NAME + "." + MClientTransaction.Field.ID);
        this.dataProvider.applyJoin("m_office", "INNER JOIN " + MOffice.NAME + " ON " + MOffice.NAME + "." + MOffice.Field.ID + " = " + MClientTransaction.NAME + "." + MClientTransaction.Field.OFFICE_ID);
        this.dataProvider.boardField(MClientTransaction.NAME + "." + MClientTransaction.Field.ID, "id", Long.class);
        this.dataProvider.boardField(MOffice.NAME + "." + MOffice.Field.NAME, "office", String.class);
        this.dataProvider.boardField("CASE " + MClientTransaction.NAME + "." + MClientTransaction.Field.TRANSACTION_TYPE_ENUM + " WHEN 1 THEN 'Paid' WHEN 2 THEN 'Waived' ELSE CONCAT(" + MClientTransaction.NAME + "." + MClientTransaction.Field.TRANSACTION_TYPE_ENUM + ", '') END", "transaction_type", String.class);
        this.dataProvider.boardField(MClientTransaction.NAME + "." + MClientTransaction.Field.TRANSACTION_DATE, "transaction_date", Calendar.Date);
        this.dataProvider.boardField(MClientTransaction.NAME + "." + MClientTransaction.Field.AMOUNT, "amount", Double.class);
        this.dataProvider.applyWhere("client_charge_id", MClientChargePaidBy.NAME + "." + MClientChargePaidBy.Field.CLIENT_CHARGE_ID + " = " + this.chargeId);
        this.dataProvider.selectField("id", Long.class);
        this.dataProvider.setSort("id", SortOrder.DESCENDING);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Office"), "office", "office", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Type"), "transaction_type", "transaction_type", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Transaction Date"), "transaction_date", "transaction_date", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Amount"), "amount", "amount", this::dataColumn));
    }

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Clients");
            breadcrumb.setPage(ClientBrowsePage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel(this.clientDisplayName);
            breadcrumb.setPage(ClientPreviewPage.class);
            breadcrumb.setParameters(parameters);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageParameters parameters = new PageParameters();
            parameters.add("clientId", this.clientId);
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Charge Overview");
            breadcrumb.setParameters(parameters);
            breadcrumb.setPage(ChargeOverviewPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel(this.chargeName);
            BREADCRUMB.add(breadcrumb);
        }
        return Model.ofList(BREADCRUMB);
    }

    @Override
    protected void initComponent() {

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        this.closeLink = new BookmarkablePageLink<>("closeLink", ClientPreviewPage.class, parameters);
        add(this.closeLink);

        this.row1 = UIRow.newUIRow("row1", this);

        this.chargeBlock = this.row1.newUIBlock("chargeBlock", Size.Six_6);
        this.chargeVContainer = this.chargeBlock.newUIContainer("chargeVContainer");
        this.chargeView = new ReadOnlyView("chargeView", new PropertyModel<>(this, "chargeValue"));
        this.chargeVContainer.add(this.chargeView);

        this.row1Block1 = this.row1.newUIBlock("row1Block1", Size.Six_6);

        this.row2 = UIRow.newUIRow("row2", this);

        this.currencyBlock = this.row2.newUIBlock("currencyBlock", Size.Six_6);
        this.currencyVContainer = this.currencyBlock.newUIContainer("currencyVContainer");
        this.currencyView = new ReadOnlyView("currencyView", new PropertyModel<>(this, "currencyValue"));
        this.currencyVContainer.add(this.currencyView);

        this.dueDateBlock = this.row2.newUIBlock("dueDateBlock", Size.Six_6);
        this.dueDateVContainer = this.dueDateBlock.newUIContainer("dueDateVContainer");
        this.dueDateView = new ReadOnlyView("dueDateView", new PropertyModel<>(this, "dueDateValue"), "yyyy-MM-dd");
        this.dueDateVContainer.add(this.dueDateView);

        this.row3 = UIRow.newUIRow("row3", this);

        this.chargeTypeBlock = this.row3.newUIBlock("chargeTypeBlock", Size.Six_6);
        this.chargeTypeVContainer = this.chargeTypeBlock.newUIContainer("chargeTypeVContainer");
        this.chargeTypeView = new ReadOnlyView("chargeTypeView", new PropertyModel<>(this, "chargeTypeValue"));
        this.chargeTypeVContainer.add(this.chargeTypeView);

        this.chargeCalculationBlock = this.row3.newUIBlock("chargeCalculationBlock", Size.Six_6);
        this.chargeCalculationVContainer = this.chargeCalculationBlock.newUIContainer("chargeCalculationVContainer");
        this.chargeCalculationView = new ReadOnlyView("chargeCalculationView", new PropertyModel<>(this, "chargeCalculationValue"));
        this.chargeCalculationVContainer.add(this.chargeCalculationView);

        this.row4 = UIRow.newUIRow("row4", this);

        this.dueBlock = this.row4.newUIBlock("dueBlock", Size.Three_3);
        this.dueVContainer = this.dueBlock.newUIContainer("dueVContainer");
        this.dueView = new ReadOnlyView("dueView", new PropertyModel<>(this, "dueValue"), "#,###,##0.00");
        this.dueVContainer.add(this.dueView);

        this.paidBlock = this.row4.newUIBlock("paidBlock", Size.Three_3);
        this.paidVContainer = this.paidBlock.newUIContainer("paidVContainer");
        this.paidView = new ReadOnlyView("paidView", new PropertyModel<>(this, "paidValue"), "#,###,##0.00");
        this.paidVContainer.add(this.paidView);

        this.waivedBlock = this.row4.newUIBlock("waivedBlock", Size.Three_3);
        this.waivedVContainer = this.waivedBlock.newUIContainer("waivedVContainer");
        this.waivedView = new ReadOnlyView("waivedView", new PropertyModel<>(this, "waivedValue"), "#,###,##0.00");
        this.waivedVContainer.add(this.waivedView);

        this.outstandingBlock = this.row4.newUIBlock("outstandingBlock", Size.Three_3);
        this.outstandingVContainer = this.outstandingBlock.newUIContainer("outstandingVContainer");
        this.outstandingView = new ReadOnlyView("outstandingView", new PropertyModel<>(this, "outstandingValue"), "#,###,##0.00");
        this.outstandingVContainer.add(this.outstandingView);

        this.row5 = UIRow.newUIRow("row5", this);

        this.dataBlock = this.row5.newUIBlock("dataBlock", Size.Twelve_12);
        this.dataIContainer = this.dataBlock.newUIContainer("dataIContainer");
        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataIContainer.add(this.dataTable);

    }

    @Override
    protected void configureMetaData() {
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("office".equals(column) || "transaction_type".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("transaction_date".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "yyyy-MM-dd");
        } else if ("amount".equals(column)) {
            Double value = (Double) model.get(column);
            return new TextCell(value == null ? 0 : value, "#,###,##0.00");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
