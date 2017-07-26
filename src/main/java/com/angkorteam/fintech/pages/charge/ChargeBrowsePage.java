package com.angkorteam.fintech.pages.charge;

import java.util.List;
import java.util.Map;

import com.angkorteam.fintech.dto.Function;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.ChargeType;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
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
        this.provider.applyWhere("is_deleted", "is_deleted = 0");
        this.provider.boardField("id", "id", Long.class);
        this.provider.boardField("name", "name", String.class);
        this.provider.boardField("case charge_applies_to_enum when " + ChargeType.Client.getLiteral() + " then '" + ChargeType.Client.getDescription() + "' when " + ChargeType.Loan.getLiteral() + " then '" + ChargeType.Loan.getDescription() + "' when " + ChargeType.SavingDeposit.getLiteral() + " then '" + ChargeType.SavingDeposit.getDescription() + "' when " + ChargeType.Share.getLiteral() + " then '" + ChargeType.Share.getDescription() + "' else 'N/A' end", "charge_apply", String.class);
        this.provider.boardField("is_penalty", "penalty", Boolean.class);
        this.provider.boardField("is_active", "active", Boolean.class);

        this.provider.selectField("id", Long.class);

        List<IColumn<Map<String, Object>, String>> columns = Lists.newArrayList();
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Name"), "name", "name", this::nameColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.String, Model.of("Charge Applies to"), "charge_apply", "charge_apply", this::chargeApplyColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Boolean, Model.of("Is Penalty"), "penalty", "penalty", this::penaltyColumn));
        columns.add(new TextFilterColumn(this.provider, ItemClass.Boolean, Model.of("Is Active"), "active", "active", this::activeColumn));

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

    private ItemPanel penaltyColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean penalty = (Boolean) model.get(jdbcColumn);
        if (penalty != null && penalty) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
    }

    private ItemPanel activeColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        Boolean active = (Boolean) model.get(jdbcColumn);
        if (active != null && active) {
            return new BadgeCell(BadgeType.Success, Model.of("Yes"));
        } else {
            return new BadgeCell(BadgeType.Danger, Model.of("No"));
        }
    }

    private ItemPanel chargeApplyColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String chargeApply = (String) model.get(jdbcColumn);
        return new TextCell(Model.of(chargeApply));
    }

    private ItemPanel nameColumn(String jdbcColumn, IModel<String> display, Map<String, Object> model) {
        String name = (String) model.get(jdbcColumn);
        String charge = (String) model.get("charge_apply");
        PageParameters parameters = new PageParameters();
        parameters.add("chargeId", model.get("id"));
        if (ChargeType.Loan.getDescription().equals(charge)) {
            return new LinkCell(LoanChargeModifyPage.class, parameters, Model.of(name));
        } else if (ChargeType.Client.getDescription().equals(charge)) {
            return new LinkCell(ClientChargeModifyPage.class, parameters, Model.of(name));
        } else if (ChargeType.Share.getDescription().equals(charge)) {
            return new LinkCell(ShareChargeModifyPage.class, parameters, Model.of(name));
        } else if (ChargeType.SavingDeposit.getDescription().equals(charge)) {
            return new LinkCell(SavingDepositChargeModifyPage.class, parameters, Model.of(name));
        }
        return null;
    }

}
