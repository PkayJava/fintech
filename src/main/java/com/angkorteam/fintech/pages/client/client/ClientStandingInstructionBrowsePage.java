package com.angkorteam.fintech.pages.client.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import com.angkorteam.fintech.dto.enums.InstructionType;
import com.angkorteam.fintech.provider.JdbcProvider;
import com.angkorteam.fintech.table.TextCell;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.Calendar;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
import com.google.common.collect.Lists;

@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class ClientStandingInstructionBrowsePage extends Page {

    protected String clientId;

    protected List<IColumn<Map<String, Object>, String>> dataColumn;
    protected DataTable<Map<String, Object>, String> dataTable;
    protected JdbcProvider dataProvider;

    protected BookmarkablePageLink<Void> createLink;

    @Override
    protected void initData() {
        this.clientId = getPageParameters().get("clientId").toString();
    }

    @Override
    protected void initComponent() {

        this.dataProvider = new JdbcProvider("m_account_transfer_details");
        this.dataProvider.applyJoin("from_client", "inner join m_client from_client on m_account_transfer_details.from_client_id = from_client.id");
        this.dataProvider.applyJoin("to_client", "inner join m_client to_client on m_account_transfer_details.to_client_id = to_client.id");
        this.dataProvider.applyJoin("from_account", "inner join m_savings_account from_account on from_account.id = m_account_transfer_details.from_savings_account_id");
        this.dataProvider.applyJoin("to_account", "inner join m_savings_account to_account on to_account.id = m_account_transfer_details.to_savings_account_id");
        this.dataProvider.applyJoin("m_account_transfer_standing_instructions", "inner join m_account_transfer_standing_instructions on m_account_transfer_details.id = m_account_transfer_standing_instructions.account_transfer_details_id");

        this.dataProvider.boardField("from_account.account_no", "account_no", String.class);
        this.dataProvider.boardField("to_client.display_name", "beneficiary_name", String.class);
        this.dataProvider.boardField("to_account.account_no", "beneficiary_account", String.class);
        this.dataProvider.boardField("m_account_transfer_standing_instructions.amount", "amount", Double.class);
        this.dataProvider.boardField("m_account_transfer_standing_instructions.instruction_type", "instruction_type", Long.class);
        this.dataProvider.boardField("m_account_transfer_standing_instructions.valid_from", "valid_from", Calendar.Date);
        this.dataProvider.boardField("m_account_transfer_standing_instructions.valid_till", "valid_till", Calendar.Date);

        this.dataProvider.selectField("instruction_type", Long.class);

        this.dataProvider.applyWhere("client_id", "from_client.id = '" + this.clientId + "'");

        this.dataColumn = Lists.newArrayList();
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Account"), "account_no", "account_no", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Bene. Name"), "beneficiary_name", "beneficiary_name", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Bene. Account"), "beneficiary_account", "beneficiary_account", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Amount"), "amount", "amount", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Valid From"), "valid_from", "valid_from", this::dataColumn));
        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Valid Until"), "valid_till", "valid_till", this::dataColumn));

        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
        add(this.dataTable);

        PageParameters parameters = new PageParameters();
        parameters.add("clientId", this.clientId);
        this.createLink = new BookmarkablePageLink<>("createLink", ClientStandingInstructionCreatePage.class, parameters);
        add(this.createLink);

    }

    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
        if ("account_no".equals(column) || "beneficiary_name".equals(column) || "beneficiary_account".equals(column)) {
            String value = (String) model.get(column);
            return new TextCell(value);
        } else if ("amount".equals(column)) {
            Long instruction_type = (Long) model.get("instruction_type");
            Double value = (Double) model.get(column);
            InstructionType type = InstructionType.parseLiteral(String.valueOf(instruction_type));
            return new TextCell(type.getDescription() + "/" + value);
        } else if ("valid_from".equals(column) || "valid_till".equals(column)) {
            Date value = (Date) model.get(column);
            return new TextCell(value, "yyyy-MM-dd");
        }
        throw new WicketRuntimeException("Unknown " + column);
    }

    @Override
    protected void configureRequiredValidation() {
    }

    @Override
    protected void configureMetaData() {
    }

}
