package com.angkorteam.fintech.pages.charge;

import java.util.List;
import java.util.Map;

import com.angkorteam.fintech.ddl.MCharge;
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
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.pages.ProductDashboardPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.BadgeCell;
import com.angkorteam.fintech.table.LinkCell;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.BadgeType;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ChargeBrowsePage extends Page {

    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected BookmarkablePageLink<Void> createLoanChargeLink;
    protected BookmarkablePageLink<Void> createClientChargeLink;
    protected BookmarkablePageLink<Void> createSavingDepositChargeLink;
    protected BookmarkablePageLink<Void> createShareChargeLink;

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
            breadcrumb.setLabel("Product");
            breadcrumb.setPage(ProductDashboardPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Charge");
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

        this.createLoanChargeLink = new BookmarkablePageLink<>("createLoanChargeLink", LoanChargeCreatePage.class);
        add(this.createLoanChargeLink);

        this.createSavingDepositChargeLink = new BookmarkablePageLink<>("createSavingDepositChargeLink", SavingDepositChargeCreatePage.class);
        add(this.createSavingDepositChargeLink);

        this.createClientChargeLink = new BookmarkablePageLink<>("createClientChargeLink", ClientChargeCreatePage.class);
        add(this.createClientChargeLink);

        this.createShareChargeLink = new BookmarkablePageLink<>("createShareChargeLink", ShareChargeCreatePage.class);
        add(this.createShareChargeLink);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

    protected void initDataBlock() {
        this.dataBlock = new WebMarkupBlock("dataBlock", Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);
        this.dataProvider = new JdbcProvider(MCharge.NAME);
        this.dataProvider.applyWhere("is_deleted", MCharge.Field.IS_DELETED + " = 0");
        this.dataProvider.boardField(MCharge.Field.ID, "id", Long.class);
        this.dataProvider.boardField(MCharge.Field.NAME, "name", String.class);
        this.dataProvider.boardField("case " + MCharge.Field.CHARGE_APPLIES_TO_ENUM + " when " + ChargeType.Client.getLiteral() + " then '" + ChargeType.Client.getDescription() + "' when " + ChargeType.Loan.getLiteral() + " then '" + ChargeType.Loan.getDescription() + "' when " + ChargeType.SavingDeposit.getLiteral() + " then '" + ChargeType.SavingDeposit.getDescription() + "' when " + ChargeType.Share.getLiteral() + " then '" + ChargeType.Share.getDescription() + "' else 'N/A' end", "charge_apply", String.class);
        this.dataProvider.boardField(MCharge.Field.IS_PENALTY, "penalty", Boolean.class);
        this.dataProvider.boardField(MCharge.Field.IS_ACTIVE, "active", Boolean.class);

        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Name"), "name", "name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Charge Applies to"), "charge_apply", "charge_apply", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Boolean, Model.of("Is Penalty"), "penalty", "penalty", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Boolean, Model.of("Is Active"), "active", "active", this::dataColumn));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        this.dataIContainer.add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("name".equals(column)) {
            String value = (String) model.get(column);
            String charge = (String) model.get("charge_apply");
            PageParameters parameters = new PageParameters();
            parameters.add("chargeId", model.get("id"));
            if (ChargeType.Loan.getDescription().equals(charge)) {
                return new LinkCell(LoanChargeModifyPage.class, parameters, value);
            } else if (ChargeType.Client.getDescription().equals(charge)) {
                return new LinkCell(ClientChargeModifyPage.class, parameters, value);
            } else if (ChargeType.Share.getDescription().equals(charge)) {
                return new LinkCell(ShareChargeModifyPage.class, parameters, value);
            } else if (ChargeType.SavingDeposit.getDescription().equals(charge)) {
                return new LinkCell(SavingDepositChargeModifyPage.class, parameters, value);
            }
        } else if ("charge_apply".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("penalty".equals(column)) {
            Boolean value = (Boolean) model.get(column);
            if (value != null && value) {
                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
            } else {
                return new BadgeCell(BadgeType.Danger, Model.of("No"));
            }
        } else if ("active".equals(column)) {
            Boolean value = (Boolean) model.get(column);
            if (value != null && value) {
                return new BadgeCell(BadgeType.Success, Model.of("Yes"));
            } else {
                return new BadgeCell(BadgeType.Danger, Model.of("No"));
            }
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
