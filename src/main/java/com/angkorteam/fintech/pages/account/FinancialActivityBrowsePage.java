package com.angkorteam.fintech.pages.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.Session;
import com.angkorteam.fintech.ddl.AccGLAccount;
import com.angkorteam.fintech.ddl.AccGLFinancialActivityAccount;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.constant.FinancialActivityTypeEnum;
import com.angkorteam.fintech.helper.FinancialActivityHelper;
import com.angkorteam.fintech.pages.AccountingPage;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.fintech.widget.WebMarkupBlock;
import com.angkorteam.fintech.widget.WebMarkupBlock.Size;
import com.angkorteam.framework.models.PageBreadcrumb;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionFilterColumn;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ActionItem;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemCss;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

/**
 * Created by socheatkhauv on 7/12/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class FinancialActivityBrowsePage extends Page {

    protected WebMarkupBlock dataBlock;
    protected WebMarkupContainer dataIContainer;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected BookmarkablePageLink<Void> createLink;

    @Override
    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Accounting");
            breadcrumb.setPage(AccountingPage.class);
            BREADCRUMB.add(breadcrumb);
        }
        {
            PageBreadcrumb breadcrumb = new PageBreadcrumb();
            breadcrumb.setLabel("Financial Activity Mapping");
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

        this.createLink = new BookmarkablePageLink<>("createLink", FinancialActivityCreatePage.class);
        add(this.createLink);
    }

    protected void initDataBlock() {
        this.dataBlock = new WebMarkupBlock("dataBlock", Size.Twelve_12);
        add(this.dataBlock);
        this.dataIContainer = new WebMarkupContainer("dataIContainer");
        this.dataBlock.add(this.dataIContainer);

        this.dataProvider = new JdbcProvider(AccGLFinancialActivityAccount.NAME);
        this.dataProvider.applyJoin("acc_gl_account", "LEFT JOIN " + AccGLAccount.NAME + " ON " + AccGLFinancialActivityAccount.NAME + "." + AccGLFinancialActivityAccount.Field.GL_ACCOUNT_ID + " = " + AccGLAccount.NAME + "." + AccGLAccount.Field.ID);
        this.dataProvider.boardField(AccGLFinancialActivityAccount.NAME + "." + AccGLFinancialActivityAccount.Field.ID, "id", Long.class);
        this.dataProvider.boardField(AccGLAccount.NAME + "." + AccGLAccount.Field.NAME, "account", String.class);
        this.dataProvider.boardField(AccGLFinancialActivityAccount.NAME + "." + AccGLFinancialActivityAccount.Field.FINANCIAL_ACTIVITY_TYPE, "type", Long.class);

        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = new ArrayList<>();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Long, Model.of("Financial Activity"), "type", "type", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Account Name"), "account", "account", this::dataColumn));
        this.dataColumn.add(new ActionFilterColumn<>(Model.of("Action"), this::dataAction, this::dataClick));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        this.dataIContainer.add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);
    }

    @Override
    protected void configureMetaData() {
    }

    protected void dataClick(String column, Map<String, Object> model, AjaxRequestTarget target) {
        Long value = (Long) model.get("id");
        if ("modify".equals(column)) {
            PageParameters parameters = new PageParameters();
            parameters.add("financialActivityId", value);
            setResponsePage(FinancialActivityModifyPage.class, parameters);
        } else if ("delete".equals(column)) {
            JsonNode node = FinancialActivityHelper.delete((Session) getSession(), String.valueOf(value));

            reportError(node, target);
            target.add(this.dataTable);
        }
    }

    protected List<ActionItem> dataAction(String column, Map<String, Object> model) {
        List<ActionItem> actions = Lists.newArrayList();
        actions.add(new ActionItem("modify", Model.of("Modify"), ItemCss.INFO));
        actions.add(new ActionItem("delete", Model.of("Delete"), ItemCss.DANGER));
        return actions;
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("type".equals(column)) {
            Long value = (Long) model.get(column);
            String text = null;
            if (FinancialActivityTypeEnum.AssetTransfer.getLiteral().equals(String.valueOf(value))) {
                text = value + "." + FinancialActivityTypeEnum.AssetTransfer.getDescription();
            } else if (FinancialActivityTypeEnum.CashAtTeller.getLiteral().equals(String.valueOf(value))) {
                text = value + "." + FinancialActivityTypeEnum.CashAtTeller.getDescription();
            } else if (FinancialActivityTypeEnum.AssetFundSource.getLiteral().equals(String.valueOf(value))) {
                text = value + "." + FinancialActivityTypeEnum.AssetFundSource.getDescription();
            } else if (FinancialActivityTypeEnum.CashAtMainvault.getLiteral().equals(String.valueOf(value))) {
                text = value + "." + FinancialActivityTypeEnum.CashAtMainvault.getDescription();
            } else if (FinancialActivityTypeEnum.OpeningBalancesTransferContra.getLiteral().equals(String.valueOf(value))) {
                text = value + "." + FinancialActivityTypeEnum.OpeningBalancesTransferContra.getDescription();
            } else if (FinancialActivityTypeEnum.PayableDividends.getLiteral().equals(String.valueOf(value))) {
                text = value + "." + FinancialActivityTypeEnum.PayableDividends.getDescription();
            } else if (FinancialActivityTypeEnum.LiabilityTransfer.getLiteral().equals(String.valueOf(value))) {
                text = value + "." + FinancialActivityTypeEnum.LiabilityTransfer.getDescription();
            }
            return new TextCell(text);
        } else if ("account".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
