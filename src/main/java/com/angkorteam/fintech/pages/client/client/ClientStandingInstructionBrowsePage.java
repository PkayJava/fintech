//package com.angkorteam.fintech.pages.client.client;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.wicket.WicketRuntimeException;
//import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
//import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
//import org.apache.wicket.markup.html.link.BookmarkablePageLink;
//import org.apache.wicket.model.IModel;
//import org.apache.wicket.model.Model;
//import org.apache.wicket.request.mapper.parameter.PageParameters;
//
//import com.angkorteam.fintech.Page;
//import com.angkorteam.fintech.ddl.MAccountTransferDetails;
//import com.angkorteam.fintech.ddl.MAccountTransferStandingInstructions;
//import com.angkorteam.fintech.ddl.MClient;
//import com.angkorteam.fintech.ddl.MSavingsAccount;
//import com.angkorteam.fintech.dto.Function;
//import com.angkorteam.fintech.dto.enums.InstructionType;
//import com.angkorteam.fintech.provider.JdbcProvider;
//import com.angkorteam.fintech.table.TextCell;
//import com.angkorteam.framework.SpringBean;
//import com.angkorteam.framework.jdbc.SelectQuery;
//import com.angkorteam.framework.models.PageBreadcrumb;
//import com.angkorteam.framework.spring.JdbcNamed;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.Calendar;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemClass;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.ItemPanel;
//import com.angkorteam.framework.wicket.extensions.markup.html.repeater.data.table.filter.TextFilterColumn;
//import com.google.common.collect.Lists;
//
//@AuthorizeInstantiation(Function.ALL_FUNCTION)
//public class ClientStandingInstructionBrowsePage extends Page {
//
//    protected String clientId;
//
//    protected String clientDisplayName;
//
//    protected List<IColumn<Map<String, Object>, String>> dataColumn;
//    protected DataTable<Map<String, Object>, String> dataTable;
//    protected JdbcProvider dataProvider;
//
//    protected BookmarkablePageLink<Void> createLink;
//
//    @Override
//    protected void initData() {
//        this.clientId = getPageParameters().get("clientId").toString();
//        JdbcNamed named = SpringBean.getBean(JdbcNamed.class);
//        SelectQuery selectQuery = null;
//
//        selectQuery = new SelectQuery(MClient.NAME);
//        selectQuery.addField(MClient.Field.DISPLAY_NAME);
//        selectQuery.addWhere(MClient.Field.ID + " = :" + MClient.Field.ID, this.clientId);
//        Map<String, Object> clientObject = named.queryForMap(selectQuery.toSQL(), selectQuery.getParam());
//        this.clientDisplayName = (String) clientObject.get("display_name");
//    }
//
//    @Override
//    public IModel<List<PageBreadcrumb>> buildPageBreadcrumb() {
//        List<PageBreadcrumb> BREADCRUMB = Lists.newArrayList();
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Clients");
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Clients");
//            breadcrumb.setPage(ClientBrowsePage.class);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageParameters parameters = new PageParameters();
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            parameters.add("clientId", this.clientId);
//            breadcrumb.setLabel(this.clientDisplayName);
//            breadcrumb.setPage(ClientPreviewPage.class);
//            breadcrumb.setParameters(parameters);
//            BREADCRUMB.add(breadcrumb);
//        }
//        {
//            PageBreadcrumb breadcrumb = new PageBreadcrumb();
//            breadcrumb.setLabel("Standing Instruction");
//            BREADCRUMB.add(breadcrumb);
//        }
//        return Model.ofList(BREADCRUMB);
//    }
//
//    @Override
//    protected void initComponent() {
//        this.dataProvider = new JdbcProvider(MAccountTransferDetails.NAME);
//        this.dataProvider.applyJoin("from_client", "INNER JOIN " + MClient.NAME + " from_client ON " + MAccountTransferDetails.NAME + "." + MAccountTransferDetails.Field.FROM_CLIENT_ID + " = from_client." + MClient.Field.ID);
//        this.dataProvider.applyJoin("to_client", "INNER JOIN " + MClient.NAME + " to_client ON " + MAccountTransferDetails.NAME + "." + MAccountTransferDetails.Field.TO_CLIENT_ID + " = to_client." + MClient.Field.ID);
//        this.dataProvider.applyJoin("from_account", "INNER JOIN " + MSavingsAccount.NAME + " from_account ON from_account." + MSavingsAccount.Field.ID + " = " + MAccountTransferDetails.NAME + "." + MAccountTransferDetails.Field.FROM_SAVINGS_ACCOUNT_ID);
//        this.dataProvider.applyJoin("to_account", "INNER JOIN " + MSavingsAccount.NAME + " to_account ON to_account." + MSavingsAccount.Field.ID + " = " + MAccountTransferDetails.NAME + "." + MAccountTransferDetails.Field.TO_SAVINGS_ACCOUNT_ID);
//        this.dataProvider.applyJoin("m_account_transfer_standing_instructions", "INNER JOIN " + MAccountTransferStandingInstructions.NAME + " ON " + MAccountTransferDetails.NAME + "." + MAccountTransferDetails.Field.ID + " = " + MAccountTransferStandingInstructions.NAME + "." + MAccountTransferStandingInstructions.Field.ACCOUNT_TRANSFER_DETAILS_ID);
//
//        this.dataProvider.boardField("from_account." + MSavingsAccount.Field.ACCOUNT_NO, "account_no", String.class);
//        this.dataProvider.boardField("to_client." + MClient.Field.DISPLAY_NAME, "beneficiary_name", String.class);
//        this.dataProvider.boardField("to_account." + MSavingsAccount.Field.ACCOUNT_NO, "beneficiary_account", String.class);
//        this.dataProvider.boardField(MAccountTransferStandingInstructions.NAME + "." + MAccountTransferStandingInstructions.Field.AMOUNT, "amount", Double.class);
//        this.dataProvider.boardField(MAccountTransferStandingInstructions.NAME + "." + MAccountTransferStandingInstructions.Field.INSTRUCTION_TYPE, "instruction_type", Long.class);
//        this.dataProvider.boardField(MAccountTransferStandingInstructions.NAME + "." + MAccountTransferStandingInstructions.Field.VALID_FROM, "valid_from", Calendar.Date);
//        this.dataProvider.boardField(MAccountTransferStandingInstructions.NAME + "." + MAccountTransferStandingInstructions.Field.VALID_TILL, "valid_till", Calendar.Date);
//
//        this.dataProvider.selectField("instruction_type", Long.class);
//
//        this.dataProvider.applyWhere("client_id", "from_client." + MClient.Field.ID + " = '" + this.clientId + "'");
//
//        this.dataColumn = Lists.newArrayList();
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Account"), "account_no", "account_no", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Bene. Name"), "beneficiary_name", "beneficiary_name", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.String, Model.of("Bene. Account"), "beneficiary_account", "beneficiary_account", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Double, Model.of("Amount"), "amount", "amount", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Valid From"), "valid_from", "valid_from", this::dataColumn));
//        this.dataColumn.add(new TextFilterColumn(this.dataProvider, ItemClass.Date, Model.of("Valid Until"), "valid_till", "valid_till", this::dataColumn));
//
//        this.dataTable = new DefaultDataTable<>("dataTable", this.dataColumn, this.dataProvider, 20);
//        add(this.dataTable);
//
//        PageParameters parameters = new PageParameters();
//        parameters.add("clientId", this.clientId);
//        this.createLink = new BookmarkablePageLink<>("createLink", ClientStandingInstructionCreatePage.class, parameters);
//        add(this.createLink);
//
//    }
//
//    protected ItemPanel dataColumn(String column, IModel<String> display, Map<String, Object> model) {
//        if ("account_no".equals(column) || "beneficiary_name".equals(column) || "beneficiary_account".equals(column)) {
//            String value = (String) model.get(column);
//            return new TextCell(value);
//        } else if ("amount".equals(column)) {
//            Long instruction_type = (Long) model.get("instruction_type");
//            Double value = (Double) model.get(column);
//            InstructionType type = InstructionType.parseLiteral(String.valueOf(instruction_type));
//            return new TextCell(type.getDescription() + "/" + value);
//        } else if ("valid_from".equals(column) || "valid_till".equals(column)) {
//            Date value = (Date) model.get(column);
//            return new TextCell(value, "yyyy-MM-dd");
//        }
//        throw new WicketRuntimeException("Unknown " + column);
//    }
//
//    @Override
//    protected void configureMetaData() {
//    }
//
//}
