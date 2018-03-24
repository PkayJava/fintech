package com.angkorteam.fintech.pages.client.common;

import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.ddl.MCodeValue;
import com.angkorteam.fintech.ddl.MGuarantor;
import com.angkorteam.fintech.dto.ClientEnum;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class LoanGuarantorBrowsePage extends Page {

    protected ClientEnum client;

    protected String clientId;
    protected String groupId;
    protected String centerId;

    protected String loanId;

    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;
    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected FilterForm<Map<String, String>> dataFilterForm;

    protected BookmarkablePageLink<Void> createExternalLink;
    protected BookmarkablePageLink<Void> createInternalLink;

    @Override
    protected void initData() {
        this.client = ClientEnum.valueOf(getPageParameters().get("client").toString());

        this.clientId = getPageParameters().get("clientId").toString();
        this.groupId = getPageParameters().get("groupId").toString();
        this.centerId = getPageParameters().get("centerId").toString();

        this.loanId = getPageParameters().get("loanId").toString();
    }

    @Override
    protected void initComponent() {

        this.dataProvider = new JdbcProvider(MGuarantor.NAME);
        this.dataProvider.applyJoin("relationship", "INNER JOIN " + MCodeValue.NAME + " relationship ON relationship." + MCodeValue.Field.ID + " = " + MGuarantor.NAME + "." + MGuarantor.Field.CLIENT_RELN_CV_ID);
        this.dataProvider.boardField(MGuarantor.NAME + "." + MGuarantor.Field.ID, "id", Long.class);
        this.dataProvider.boardField(MGuarantor.NAME + "." + MGuarantor.Field.FIRST_NAME, "firstname", String.class);
        this.dataProvider.boardField(MGuarantor.NAME + "." + MGuarantor.Field.LAST_NAME, "lastname", String.class);
        this.dataProvider.boardField("relationship." + MCodeValue.Field.CODE_VALUE, "relationship", String.class);

        this.dataProvider.applyWhere("loan_id", MGuarantor.NAME + "." + MGuarantor.Field.LOAN_ID + " = '" + this.loanId + "'");

        this.dataProvider.selectField("id", Long.class);

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("First Name"), "firstname", "firstname", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Last Name"), "lastname", "lastname", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Relationship"), "relationship", "relationship", this::dataColumn));

        this.dataFilterForm = new FilterForm<>("dataFilterForm", this.dataProvider);
        add(this.dataFilterForm);

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        this.dataTable.addTopToolbar(new FilterToolbar(this.dataTable, this.dataFilterForm));
        this.dataFilterForm.add(this.dataTable);

        PageParameters parameters = new PageParameters();
        parameters.add("client", this.client.name());
        if (this.client == ClientEnum.Client) {
            parameters.add("clientId", this.clientId);
        } else if (this.client == ClientEnum.Group) {
            parameters.add("groupId", this.groupId);
        } else if (this.client == ClientEnum.Center) {
            parameters.add("centerId", this.centerId);
        }
        parameters.add("loanId", this.loanId);

        this.createExternalLink = new BookmarkablePageLink<>("createExternalLink", LoanGuarantorCreateExternalPage.class, parameters);
        add(this.createExternalLink);

        this.createInternalLink = new BookmarkablePageLink<>("createInternalLink", LoanGuarantorCreateInternalPage.class, parameters);
        add(this.createInternalLink);
    }

    @Override
    protected void configureMetaData() {
    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("firstname".equals(column) || "lastname".equals(column) || "relationship".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

}
