package com.angkorteam.fintech.pages.client.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.ReadOnlyView;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.SpringBean;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.spring.JdbcTemplate;
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

    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;

    protected WebMarkupBlock chargeBlock;
    protected WebMarkupContainer chargeVContainer;
    protected String chargeValue;
    protected ReadOnlyView chargeView;

    protected WebMarkupBlock currencyBlock;
    protected WebMarkupContainer currencyVContainer;
    protected String currencyValue;
    protected ReadOnlyView currencyView;

    protected WebMarkupBlock dueDateBlock;
    protected WebMarkupContainer dueDateVContainer;
    protected Date dueDateValue;
    protected ReadOnlyView dueDateView;

    protected WebMarkupBlock chargeTypeBlock;
    protected WebMarkupContainer chargeTypeVContainer;
    protected Option chargeTypeValue;
    protected ReadOnlyView chargeTypeView;

    protected WebMarkupBlock chargeCalculationBlock;
    protected WebMarkupContainer chargeCalculationVContainer;
    protected Option chargeCalculationValue;
    protected ReadOnlyView chargeCalculationView;

    protected WebMarkupBlock dueBlock;
    protected WebMarkupContainer dueVContainer;
    protected Double dueValue;
    protected ReadOnlyView dueView;

    protected WebMarkupBlock paidBlock;
    protected WebMarkupContainer paidVContainer;
    protected Double paidValue;
    protected ReadOnlyView paidView;

    protected WebMarkupBlock waivedBlock;
    protected WebMarkupContainer waivedVContainer;
    protected Double waivedValue;
    protected ReadOnlyView waivedView;

    protected WebMarkupBlock outstandingBlock;
    protected WebMarkupContainer outstandingVContainer;
    protected Double outstandingValue;
    protected ReadOnlyView outstandingView;

    @Override
    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
        this.chargeId = getPageParameters().get("chargeId").toString();
        JdbcTemplate jdbcTemplate = SpringBean.getBean(JdbcTemplate.class);
        Map<String, Object> clientObject = jdbcTemplate.queryForMap("select display_name from m_client where id = ?", this.clientId);
        this.clientDisplayName = (String) clientObject.get("display_name");
        Map<String, Object> chargeObject = jdbcTemplate.queryForMap("select * from m_client_charge inner join m_charge on m_client_charge.charge_id = m_charge.id inner join m_currency on m_currency.code = m_charge.currency_code where m_client_charge.id = ?", this.chargeId);

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

        this.dataBlock = new WebMarkupBlock("dataBlock", Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);

        this.dataProvider = new JdbcProvider("m_client_charge_paid_by");
        this.dataProvider.applyJoin("m_client_transaction", "INNER JOIN m_client_transaction ON m_client_charge_paid_by.client_transaction_id = m_client_transaction.id");
        this.dataProvider.applyJoin("m_office", "INNER JOIN m_office ON m_office.id = m_client_transaction.office_id");

        this.dataProvider.boardField("m_client_transaction.id", "id", Long.class);
        this.dataProvider.boardField("m_office.name", "office", String.class);
        this.dataProvider.boardField("case m_client_transaction.transaction_type_enum when 1 then 'Paid' when 2 then 'Waived' else concat(m_client_transaction.transaction_type_enum, '') end", "transaction_type", String.class);
        this.dataProvider.boardField("m_client_transaction.transaction_date", "transaction_date", Calendar.Date);
        this.dataProvider.boardField("m_client_transaction.amount", "amount", Double.class);

        this.dataProvider.applyWhere("client_charge_id", "m_client_charge_paid_by.client_charge_id = " + this.chargeId);

        this.dataProvider.selectField("id", Long.class);

        this.dataProvider.setSort("id", SortOrder.DESCENDING);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Office"), "office", "office", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Type"), "transaction_type", "transaction_type", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Transaction Date"), "transaction_date", "transaction_date", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Amount"), "amount", "amount", this::dataColumn));
        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataIContainer.add(this.dataTable);

        this.chargeBlock = new WebMarkupBlock("chargeBlock", Size.Twelve_12);
        add(this.chargeBlock);
        this.chargeVContainer = new WebMarkupContainer("chargeVContainer");
        this.chargeBlock.add(this.chargeVContainer);
        this.chargeView = new ReadOnlyView("chargeView", new PropertyModel<>(this, "chargeValue"));
        this.chargeVContainer.add(this.chargeView);

        this.currencyBlock = new WebMarkupBlock("currencyBlock", Size.Six_6);
        add(this.currencyBlock);
        this.currencyVContainer = new WebMarkupContainer("currencyVContainer");
        this.currencyBlock.add(this.currencyVContainer);
        this.currencyView = new ReadOnlyView("currencyView", new PropertyModel<>(this, "currencyValue"));
        this.currencyVContainer.add(this.currencyView);

        this.chargeTypeBlock = new WebMarkupBlock("chargeTypeBlock", Size.Six_6);
        add(this.chargeTypeBlock);
        this.chargeTypeVContainer = new WebMarkupContainer("chargeTypeVContainer");
        this.chargeTypeBlock.add(this.chargeTypeVContainer);
        this.chargeTypeView = new ReadOnlyView("chargeTypeView", new PropertyModel<>(this, "chargeTypeValue"));
        this.chargeTypeVContainer.add(this.chargeTypeView);

        this.chargeCalculationBlock = new WebMarkupBlock("chargeCalculationBlock", Size.Six_6);
        add(this.chargeCalculationBlock);
        this.chargeCalculationVContainer = new WebMarkupContainer("chargeCalculationVContainer");
        this.chargeCalculationBlock.add(this.chargeCalculationVContainer);
        this.chargeCalculationView = new ReadOnlyView("chargeCalculationView", new PropertyModel<>(this, "chargeCalculationValue"));
        this.chargeCalculationVContainer.add(this.chargeCalculationView);

        this.dueDateBlock = new WebMarkupBlock("dueDateBlock", Size.Six_6);
        add(this.dueDateBlock);
        this.dueDateVContainer = new WebMarkupContainer("dueDateVContainer");
        this.dueDateBlock.add(this.dueDateVContainer);
        this.dueDateView = new ReadOnlyView("dueDateView", new PropertyModel<>(this, "dueDateValue"), "yyyy-MM-dd");
        this.dueDateVContainer.add(this.dueDateView);

        this.dueBlock = new WebMarkupBlock("dueBlock", Size.Three_3);
        add(this.dueBlock);
        this.dueVContainer = new WebMarkupContainer("dueVContainer");
        this.dueBlock.add(this.dueVContainer);
        this.dueView = new ReadOnlyView("dueView", new PropertyModel<>(this, "dueValue"), "#,###,##0.00");
        this.dueVContainer.add(this.dueView);

        this.paidBlock = new WebMarkupBlock("paidBlock", Size.Three_3);
        add(this.paidBlock);
        this.paidVContainer = new WebMarkupContainer("paidVContainer");
        this.paidBlock.add(this.paidVContainer);
        this.paidView = new ReadOnlyView("paidView", new PropertyModel<>(this, "paidValue"), "#,###,##0.00");
        this.paidVContainer.add(this.paidView);

        this.waivedBlock = new WebMarkupBlock("waivedBlock", Size.Three_3);
        add(this.waivedBlock);
        this.waivedVContainer = new WebMarkupContainer("waivedVContainer");
        this.waivedBlock.add(this.waivedVContainer);
        this.waivedView = new ReadOnlyView("waivedView", new PropertyModel<>(this, "waivedValue"), "#,###,##0.00");
        this.waivedVContainer.add(this.waivedView);

        this.outstandingBlock = new WebMarkupBlock("outstandingBlock", Size.Three_3);
        add(this.outstandingBlock);
        this.outstandingVContainer = new WebMarkupContainer("outstandingVContainer");
        this.outstandingBlock.add(this.outstandingVContainer);
        this.outstandingView = new ReadOnlyView("outstandingView", new PropertyModel<>(this, "outstandingValue"), "#,###,##0.00");
        this.outstandingVContainer.add(this.outstandingView);
    }

    @Override
    protected void configureRequiredValidation() {
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
