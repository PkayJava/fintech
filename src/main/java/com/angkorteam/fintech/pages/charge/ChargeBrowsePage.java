package com.angkorteam.fintech.pages.charge;

import java.util.List;
import java.util.Map;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.ChargeType;
import com.angkorteam.fintech.pages.fund.FundModifyPage;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.share.provider.JdbcProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

public class ChargeBrowsePage extends Page {

    private DataTable<Map<String, Object>, String> dataTable;

    private JdbcProvider provider;

    private BookmarkablePageLink<Void> createLoanChargeLink;
    private BookmarkablePageLink<Void> createClientChargeLink;
    private BookmarkablePageLink<Void> createSavingDepositChargeLink;
    private BookmarkablePageLink<Void> createShareChargeLink;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.provider = new JdbcProvider("m_charge");
        this.provider.boardField("id", "id", Long.class);
        this.provider.boardField("name", "name", String.class);
        this.provider.boardField("case charge_applies_to_enum when " + ChargeType.Client.getLiteral() + " then '" + ChargeType.Client.getDescription() + "' when " + ChargeType.Loan.getLiteral() + " then '" + ChargeType.Loan.getDescription() + "' when " + ChargeType.SavingDeposit.getLiteral() + " then '" + ChargeType.SavingDeposit.getDescription() + "' when " + ChargeType.Share.getLiteral() + " then '" + ChargeType.Share.getDescription() + "' end", "charge_apply", String.class);
        this.provider.boardField("is_penalty", "penalty", Boolean.class);
        this.provider.boardField("is_active", "active", Boolean.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.Long, Model.of("ID"), "id", "id", this::idColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("External ID"), "external_id", "external_id", this::externalIdColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "name", "name", this::nameColumn));

        FilterForm<Map<String, String>> filterForm = new FilterForm<>("filter-form", this.provider);
        add(filterForm);

        this.dataTable = new DefaultDataTable<>("table", columns, this.provider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, filterForm));
        filterForm.add(this.dataTable);

        this.createLoanChargeLink = new BookmarkablePageLink<>("createLoanChargeLink", LoanChargeCreatePage.class);
        add(this.createLoanChargeLink);

        this.createSavingDepositChargeLink = new BookmarkablePageLink<>("createSavingDepositChargeLink", SavingDepositChargeCreatePage.class);
        add(this.createSavingDepositChargeLink);

        this.createClientChargeLink = new BookmarkablePageLink<>("createClientChargeLink", ClientChargeCreatePage.class);
        add(this.createClientChargeLink);

        this.createShareChargeLink = new BookmarkablePageLink<>("createShareChargeLink", ShareChargeCreatePage.class);
        add(this.createShareChargeLink);
    }

    private ItemPanel idColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Long id = (Long) model.get(jdbcColumn);
        return new TextCell(Model.of(String.valueOf(id)));
    }

    private ItemPanel externalIdColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String externalId = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(externalId));
    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String name = (String) model.get(jdbcColumn);
        PageParameters parameters = new PageParameters();
        parameters.add("fundId", model.get("id"));
        return new LinkCell(FundModifyPage.class, parameters, Model.of(name));
    }

}
