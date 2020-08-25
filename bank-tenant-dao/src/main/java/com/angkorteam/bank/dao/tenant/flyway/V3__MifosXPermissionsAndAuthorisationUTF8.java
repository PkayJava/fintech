package com.angkorteam.bank.dao.tenant.flyway;

import com.angkorteam.bank.dao.flyway.LiquibaseJavaMigration;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.insert.RowInsertionBuilder;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.jdbc.PublicSqlKeywords;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V3__MifosXPermissionsAndAuthorisationUTF8 extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V3__MifosXPermissionsAndAuthorisationUTF8;
    }

    @Override
    public void migrate(Context context) throws Exception {
        new PublicSqlKeywords();
        JdbcDataContext dataContext = lookupDataContext(context);
        dataContext.refreshSchemas();
        Schema schema = dataContext.getDefaultSchema();
        {
            Table m_permission = schema.getTableByName("m_permission");
            dataContext.executeUpdate(callback -> {
                insert_m_permission(m_permission, callback, "special", "ALL_FUNCTIONS", null, null, 0);
                insert_m_permission(m_permission, callback, "special", "ALL_FUNCTIONS_READ", null, null, 0);
                insert_m_permission(m_permission, callback, "special", "CHECKER_SUPER_USER", null, null, 0);
                insert_m_permission(m_permission, callback, "special", "REPORTING_SUPER_USER", null, null, 0);
                insert_m_permission(m_permission, callback, "authorisation", "READ_PERMISSION", "PERMISSION", "READ", 0);
                insert_m_permission(m_permission, callback, "authorisation", "PERMISSIONS_ROLE", "ROLE", "PERMISSIONS", 1);
                insert_m_permission(m_permission, callback, "authorisation", "CREATE_ROLE", "ROLE", "CREATE", 1);
                insert_m_permission(m_permission, callback, "authorisation", "CREATE_ROLE_CHECKER", "ROLE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "authorisation", "READ_ROLE", "ROLE", "READ", 0);
                insert_m_permission(m_permission, callback, "authorisation", "UPDATE_ROLE", "ROLE", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "authorisation", "UPDATE_ROLE_CHECKER", "ROLE", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "authorisation", "DELETE_ROLE", "ROLE", "DELETE", 1);
                insert_m_permission(m_permission, callback, "authorisation", "DELETE_ROLE_CHECKER", "ROLE", "DELETE", 0);
                insert_m_permission(m_permission, callback, "authorisation", "CREATE_USER", "USER", "CREATE", 1);
                insert_m_permission(m_permission, callback, "authorisation", "CREATE_USER_CHECKER", "USER", "CREATE", 0);
                insert_m_permission(m_permission, callback, "authorisation", "READ_USER", "USER", "READ", 0);
                insert_m_permission(m_permission, callback, "authorisation", "UPDATE_USER", "USER", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "authorisation", "UPDATE_USER_CHECKER", "USER", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "authorisation", "DELETE_USER", "USER", "DELETE", 1);
                insert_m_permission(m_permission, callback, "authorisation", "DELETE_USER_CHECKER", "USER", "DELETE", 0);
                insert_m_permission(m_permission, callback, "configuration", "READ_CONFIGURATION", "CONFIGURATION", "READ", 1);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_CONFIGURATION", "CONFIGURATION", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_CONFIGURATION_CHECKER", "CONFIGURATION", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "READ_CODE", "CODE", "READ", 0);
                insert_m_permission(m_permission, callback, "configuration", "CREATE_CODE", "CODE", "CREATE", 1);
                insert_m_permission(m_permission, callback, "configuration", "CREATE_CODE_CHECKER", "CODE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_CODE", "CODE", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_CODE_CHECKER", "CODE", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "DELETE_CODE", "CODE", "DELETE", 1);
                insert_m_permission(m_permission, callback, "configuration", "DELETE_CODE_CHECKER", "CODE", "DELETE", 0);
                insert_m_permission(m_permission, callback, "configuration", "READ_CODEVALUE", "CODEVALUE", "READ", 0);
                insert_m_permission(m_permission, callback, "configuration", "CREATE_CODEVALUE", "CODEVALUE", "CREATE", 1);
                insert_m_permission(m_permission, callback, "configuration", "CREATE_CODEVALUE_CHECKER", "CODEVALUE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_CODEVALUE", "CODEVALUE", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_CODEVALUE_CHECKER", "CODEVALUE", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "DELETE_CODEVALUE", "CODEVALUE", "DELETE", 1);
                insert_m_permission(m_permission, callback, "configuration", "DELETE_CODEVALUE_CHECKER", "CODEVALUE", "DELETE", 0);
                insert_m_permission(m_permission, callback, "configuration", "READ_CURRENCY", "CURRENCY", "READ", 0);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_CURRENCY", "CURRENCY", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_CURRENCY_CHECKER", "CURRENCY", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_PERMISSION", "PERMISSION", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_PERMISSION_CHECKER", "PERMISSION", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "READ_DATATABLE", "DATATABLE", "READ", 0);
                insert_m_permission(m_permission, callback, "configuration", "REGISTER_DATATABLE", "DATATABLE", "REGISTER", 1);
                insert_m_permission(m_permission, callback, "configuration", "REGISTER_DATATABLE_CHECKER", "DATATABLE", "REGISTER", 0);
                insert_m_permission(m_permission, callback, "configuration", "DEREGISTER_DATATABLE", "DATATABLE", "DEREGISTER", 1);
                insert_m_permission(m_permission, callback, "configuration", "DEREGISTER_DATATABLE_CHECKER", "DATATABLE", "DEREGISTER", 0);
                insert_m_permission(m_permission, callback, "configuration", "READ_AUDIT", "AUDIT", "READ", 0);
                insert_m_permission(m_permission, callback, "configuration", "CREATE_CALENDAR", "CALENDAR", "CREATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "READ_CALENDAR", "CALENDAR", "READ", 0);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_CALENDAR", "CALENDAR", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "DELETE_CALENDAR", "CALENDAR", "DELETE", 0);
                insert_m_permission(m_permission, callback, "configuration", "CREATE_CALENDAR_CHECKER", "CALENDAR", "CREATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "UPDATE_CALENDAR_CHECKER", "CALENDAR", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "configuration", "DELETE_CALENDAR_CHECKER", "CALENDAR", "DELETE", 0);
                insert_m_permission(m_permission, callback, "organisation", "READ_MAKERCHECKER", "MAKERCHECKER", "READ", 0);
                insert_m_permission(m_permission, callback, "organisation", "READ_CHARGE", "CHARGE", "READ", 0);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_CHARGE", "CHARGE", "CREATE", 1);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_CHARGE_CHECKER", "CHARGE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "organisation", "UPDATE_CHARGE", "CHARGE", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "organisation", "UPDATE_CHARGE_CHECKER", "CHARGE", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "organisation", "DELETE_CHARGE", "CHARGE", "DELETE", 1);
                insert_m_permission(m_permission, callback, "organisation", "DELETE_CHARGE_CHECKER", "CHARGE", "DELETE", 0);
                insert_m_permission(m_permission, callback, "organisation", "READ_FUND", "FUND", "READ", 0);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_FUND", "FUND", "CREATE", 1);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_FUND_CHECKER", "FUND", "CREATE", 0);
                insert_m_permission(m_permission, callback, "organisation", "UPDATE_FUND", "FUND", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "organisation", "UPDATE_FUND_CHECKER", "FUND", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "organisation", "DELETE_FUND", "FUND", "DELETE", 1);
                insert_m_permission(m_permission, callback, "organisation", "DELETE_FUND_CHECKER", "FUND", "DELETE", 0);
                insert_m_permission(m_permission, callback, "organisation", "READ_LOANPRODUCT", "LOANPRODUCT", "READ", 0);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_LOANPRODUCT", "LOANPRODUCT", "CREATE", 1);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_LOANPRODUCT_CHECKER", "LOANPRODUCT", "CREATE", 0);
                insert_m_permission(m_permission, callback, "organisation", "UPDATE_LOANPRODUCT", "LOANPRODUCT", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "organisation", "UPDATE_LOANPRODUCT_CHECKER", "LOANPRODUCT", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "organisation", "DELETE_LOANPRODUCT", "LOANPRODUCT", "DELETE", 1);
                insert_m_permission(m_permission, callback, "organisation", "DELETE_LOANPRODUCT_CHECKER", "LOANPRODUCT", "DELETE", 0);
                insert_m_permission(m_permission, callback, "organisation", "READ_OFFICE", "OFFICE", "READ", 0);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_OFFICE", "OFFICE", "CREATE", 1);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_OFFICE_CHECKER", "OFFICE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "organisation", "UPDATE_OFFICE", "OFFICE", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "organisation", "UPDATE_OFFICE_CHECKER", "OFFICE", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "organisation", "READ_OFFICETRANSACTION", "OFFICETRANSACTION", "READ", 0);
                insert_m_permission(m_permission, callback, "organisation", "DELETE_OFFICE_CHECKER", "OFFICE", "DELETE", 0);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_OFFICETRANSACTION", "OFFICETRANSACTION", "CREATE", 1);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_OFFICETRANSACTION_CHECKER", "OFFICETRANSACTION", "CREATE", 0);
                insert_m_permission(m_permission, callback, "organisation", "DELETE_OFFICETRANSACTION", "OFFICETRANSACTION", "DELETE", 1);
                insert_m_permission(m_permission, callback, "organisation", "DELETE_OFFICETRANSACTION_CHECKER", "OFFICETRANSACTION", "DELETE", 0);
                insert_m_permission(m_permission, callback, "organisation", "READ_STAFF", "STAFF", "READ", 0);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_STAFF", "STAFF", "CREATE", 1);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_STAFF_CHECKER", "STAFF", "CREATE", 0);
                insert_m_permission(m_permission, callback, "organisation", "UPDATE_STAFF", "STAFF", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "organisation", "UPDATE_STAFF_CHECKER", "STAFF", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "organisation", "DELETE_STAFF", "STAFF", "DELETE", 1);
                insert_m_permission(m_permission, callback, "organisation", "DELETE_STAFF_CHECKER", "STAFF", "DELETE", 0);
                insert_m_permission(m_permission, callback, "organisation", "READ_SAVINGSPRODUCT", "SAVINGSPRODUCT", "READ", 0);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_SAVINGSPRODUCT", "SAVINGSPRODUCT", "CREATE", 1);
                insert_m_permission(m_permission, callback, "organisation", "CREATE_SAVINGSPRODUCT_CHECKER", "SAVINGSPRODUCT", "CREATE", 0);
                insert_m_permission(m_permission, callback, "organisation", "UPDATE_SAVINGSPRODUCT", "SAVINGSPRODUCT", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "organisation", "UPDATE_SAVINGSPRODUCT_CHECKER", "SAVINGSPRODUCT", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "organisation", "DELETE_SAVINGSPRODUCT", "SAVINGSPRODUCT", "DELETE", 1);
                insert_m_permission(m_permission, callback, "organisation", "DELETE_SAVINGSPRODUCT_CHECKER", "SAVINGSPRODUCT", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_LOAN", "LOAN", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_LOAN", "LOAN", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_LOAN_CHECKER", "LOAN", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_LOAN", "LOAN", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_LOAN_CHECKER", "LOAN", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_LOAN", "LOAN", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_LOAN_CHECKER", "LOAN", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_CLIENT", "CLIENT", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_CLIENT", "CLIENT", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_CLIENT_CHECKER", "CLIENT", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_CLIENT", "CLIENT", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_CLIENT_CHECKER", "CLIENT", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_CLIENT", "CLIENT", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_CLIENT_CHECKER", "CLIENT", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_CLIENTIMAGE", "CLIENTIMAGE", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_CLIENTIMAGE", "CLIENTIMAGE", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_CLIENTIMAGE_CHECKER", "CLIENTIMAGE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_CLIENTIMAGE", "CLIENTIMAGE", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_CLIENTIMAGE_CHECKER", "CLIENTIMAGE", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_CLIENTNOTE", "CLIENTNOTE", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_CLIENTNOTE", "CLIENTNOTE", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_CLIENTNOTE_CHECKER", "CLIENTNOTE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_CLIENTNOTE", "CLIENTNOTE", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_CLIENTNOTE_CHECKER", "CLIENTNOTE", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_CLIENTNOTE", "CLIENTNOTE", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_CLIENTNOTE_CHECKER", "CLIENTNOTE", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_GROUPNOTE", "GROUPNOTE", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_GROUPNOTE", "GROUPNOTE", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_GROUPNOTE", "GROUPNOTE", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_GROUPNOTE", "GROUPNOTE", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_GROUPNOTE_CHECKER", "GROUPNOTE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_GROUPNOTE_CHECKER", "GROUPNOTE", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_GROUPNOTE_CHECKER", "GROUPNOTE", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_LOANNOTE", "LOANNOTE", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_LOANNOTE", "LOANNOTE", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_LOANNOTE", "LOANNOTE", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_LOANNOTE", "LOANNOTE", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_LOANNOTE_CHECKER", "LOANNOTE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_LOANNOTE_CHECKER", "LOANNOTE", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_LOANNOTE_CHECKER", "LOANNOTE", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_LOANTRANSACTIONNOTE", "LOANTRANSACTIONNOTE", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_LOANTRANSACTIONNOTE", "LOANTRANSACTIONNOTE", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_LOANTRANSACTIONNOTE", "LOANTRANSACTIONNOTE", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_LOANTRANSACTIONNOTE", "LOANTRANSACTIONNOTE", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_LOANTRANSACTIONNOTE_CHECKER", "LOANTRANSACTIONNOTE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_LOANTRANSACTIONNOTE_CHECKER", "LOANTRANSACTIONNOTE", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_LOANTRANSACTIONNOTE_CHECKER", "LOANTRANSACTIONNOTE", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_SAVINGNOTE", "SAVINGNOTE", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_SAVINGNOTE", "SAVINGNOTE", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_SAVINGNOTE", "SAVINGNOTE", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_SAVINGNOTE", "SAVINGNOTE", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_SAVINGNOTE_CHECKER", "SAVINGNOTE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_SAVINGNOTE_CHECKER", "SAVINGNOTE", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_SAVINGNOTE_CHECKER", "SAVINGNOTE", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_CLIENTIDENTIFIER", "CLIENTIDENTIFIER", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_CLIENTIDENTIFIER", "CLIENTIDENTIFIER", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_CLIENTIDENTIFIER_CHECKER", "CLIENTIDENTIFIER", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_CLIENTIDENTIFIER", "CLIENTIDENTIFIER", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_CLIENTIDENTIFIER_CHECKER", "CLIENTIDENTIFIER", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_CLIENTIDENTIFIER", "CLIENTIDENTIFIER", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_CLIENTIDENTIFIER_CHECKER", "CLIENTIDENTIFIER", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_DOCUMENT", "DOCUMENT", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_DOCUMENT", "DOCUMENT", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_DOCUMENT_CHECKER", "DOCUMENT", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_DOCUMENT", "DOCUMENT", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_DOCUMENT_CHECKER", "DOCUMENT", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_DOCUMENT", "DOCUMENT", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_DOCUMENT_CHECKER", "DOCUMENT", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_GROUP", "GROUP", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_GROUP", "GROUP", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_GROUP_CHECKER", "GROUP", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_GROUP", "GROUP", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_GROUP_CHECKER", "GROUP", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_GROUP", "GROUP", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_GROUP_CHECKER", "GROUP", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UNASSIGNSTAFF_GROUP", "GROUP", "UNASSIGNSTAFF", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UNASSIGNSTAFF_GROUP_CHECKER", "GROUP", "UNASSIGNSTAFF", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_LOANCHARGE", "LOANCHARGE", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_LOANCHARGE_CHECKER", "LOANCHARGE", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_LOANCHARGE", "LOANCHARGE", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_LOANCHARGE_CHECKER", "LOANCHARGE", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_LOANCHARGE", "LOANCHARGE", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_LOANCHARGE_CHECKER", "LOANCHARGE", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "WAIVE_LOANCHARGE", "LOANCHARGE", "WAIVE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "WAIVE_LOANCHARGE_CHECKER", "LOANCHARGE", "WAIVE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_SAVINGSACCOUNT", "SAVINGSACCOUNT", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_SAVINGSACCOUNT", "SAVINGSACCOUNT", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_SAVINGSACCOUNT", "SAVINGSACCOUNT", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_SAVINGSACCOUNT", "SAVINGSACCOUNT", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_GUARANTOR", "GUARANTOR", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_GUARANTOR", "GUARANTOR", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_GUARANTOR_CHECKER", "GUARANTOR", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_GUARANTOR", "GUARANTOR", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_GUARANTOR_CHECKER", "GUARANTOR", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_GUARANTOR", "GUARANTOR", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_GUARANTOR_CHECKER", "GUARANTOR", "DELETE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "READ_COLLATERAL", "COLLATERAL", "READ", 0);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_COLLATERAL", "COLLATERAL", "CREATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_COLLATERAL", "COLLATERAL", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_COLLATERAL", "COLLATERAL", "DELETE", 1);
                insert_m_permission(m_permission, callback, "portfolio", "CREATE_COLLATERAL_CHECKER", "COLLATERAL", "CREATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "UPDATE_COLLATERAL_CHECKER", "COLLATERAL", "UPDATE", 0);
                insert_m_permission(m_permission, callback, "portfolio", "DELETE_COLLATERAL_CHECKER", "COLLATERAL", "DELETE", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "APPROVE_LOAN", "LOAN", "APPROVE", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "APPROVEINPAST_LOAN", "LOAN", "APPROVEINPAST", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "REJECT_LOAN", "LOAN", "REJECT", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "REJECTINPAST_LOAN", "LOAN", "REJECTINPAST", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "WITHDRAW_LOAN", "LOAN", "WITHDRAW", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "WITHDRAWINPAST_LOAN", "LOAN", "WITHDRAWINPAST", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "APPROVALUNDO_LOAN", "LOAN", "APPROVALUNDO", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "DISBURSE_LOAN", "LOAN", "DISBURSE", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "DISBURSEINPAST_LOAN", "LOAN", "DISBURSEINPAST", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "DISBURSALUNDO_LOAN", "LOAN", "DISBURSALUNDO", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "REPAYMENT_LOAN", "LOAN", "REPAYMENT", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "REPAYMENTINPAST_LOAN", "LOAN", "REPAYMENTINPAST", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "ADJUST_LOAN", "LOAN", "ADJUST", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "WAIVEINTERESTPORTION_LOAN", "LOAN", "WAIVEINTERESTPORTION", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "WRITEOFF_LOAN", "LOAN", "WRITEOFF", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "CLOSE_LOAN", "LOAN", "CLOSE", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "CLOSEASRESCHEDULED_LOAN", "LOAN", "CLOSEASRESCHEDULED", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "UPDATELOANOFFICER_LOAN", "LOAN", "UPDATELOANOFFICER", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "UPDATELOANOFFICER_LOAN_CHECKER", "LOAN", "UPDATELOANOFFICER", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "REMOVELOANOFFICER_LOAN", "LOAN", "REMOVELOANOFFICER", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "REMOVELOANOFFICER_LOAN_CHECKER", "LOAN", "REMOVELOANOFFICER", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "BULKREASSIGN_LOAN", "LOAN", "BULKREASSIGN", 1);
                insert_m_permission(m_permission, callback, "transaction_loan", "BULKREASSIGN_LOAN_CHECKER", "LOAN", "BULKREASSIGN", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "APPROVE_LOAN_CHECKER", "LOAN", "APPROVE", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "APPROVEINPAST_LOAN_CHECKER", "LOAN", "APPROVEINPAST", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "REJECT_LOAN_CHECKER", "LOAN", "REJECT", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "REJECTINPAST_LOAN_CHECKER", "LOAN", "REJECTINPAST", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "WITHDRAW_LOAN_CHECKER", "LOAN", "WITHDRAW", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "WITHDRAWINPAST_LOAN_CHECKER", "LOAN", "WITHDRAWINPAST", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "APPROVALUNDO_LOAN_CHECKER", "LOAN", "APPROVALUNDO", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "DISBURSE_LOAN_CHECKER", "LOAN", "DISBURSE", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "DISBURSEINPAST_LOAN_CHECKER", "LOAN", "DISBURSEINPAST", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "DISBURSALUNDO_LOAN_CHECKER", "LOAN", "DISBURSALUNDO", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "REPAYMENT_LOAN_CHECKER", "LOAN", "REPAYMENT", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "REPAYMENTINPAST_LOAN_CHECKER", "LOAN", "REPAYMENTINPAST", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "ADJUST_LOAN_CHECKER", "LOAN", "ADJUST", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "WAIVEINTERESTPORTION_LOAN_CHECKER", "LOAN", "WAIVEINTERESTPORTION", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "WRITEOFF_LOAN_CHECKER", "LOAN", "WRITEOFF", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "CLOSE_LOAN_CHECKER", "LOAN", "CLOSE", 0);
                insert_m_permission(m_permission, callback, "transaction_loan", "CLOSEASRESCHEDULED_LOAN_CHECKER", "LOAN", "CLOSEASRESCHEDULED", 0);
                insert_m_permission(m_permission, callback, "transaction_savings", "DEPOSIT_SAVINGSACCOUNT", "SAVINGSACCOUNT", "DEPOSIT", 1);
                insert_m_permission(m_permission, callback, "transaction_savings", "DEPOSIT_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "DEPOSIT", 0);
                insert_m_permission(m_permission, callback, "transaction_savings", "WITHDRAWAL_SAVINGSACCOUNT", "SAVINGSACCOUNT", "WITHDRAWAL", 1);
                insert_m_permission(m_permission, callback, "transaction_savings", "WITHDRAWAL_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "WITHDRAWAL", 0);
                insert_m_permission(m_permission, callback, "transaction_savings", "ACTIVATE_SAVINGSACCOUNT", "SAVINGSACCOUNT", "ACTIVATE", 1);
                insert_m_permission(m_permission, callback, "transaction_savings", "ACTIVATE_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "ACTIVATE", 0);
                insert_m_permission(m_permission, callback, "transaction_savings", "CALCULATEINTEREST_SAVINGSACCOUNT", "SAVINGSACCOUNT", "CALCULATEINTEREST", 1);
                insert_m_permission(m_permission, callback, "transaction_savings", "CALCULATEINTEREST_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "CALCULATEINTEREST", 0);
                insert_m_permission(m_permission, callback, "accounting", "CREATE_GLACCOUNT", "GLACCOUNT", "CREATE", 1);
                insert_m_permission(m_permission, callback, "accounting", "UPDATE_GLACCOUNT", "GLACCOUNT", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "accounting", "DELETE_GLACCOUNT", "GLACCOUNT", "DELETE", 1);
                insert_m_permission(m_permission, callback, "accounting", "CREATE_GLCLOSURE", "GLCLOSURE", "CREATE", 1);
                insert_m_permission(m_permission, callback, "accounting", "UPDATE_GLCLOSURE", "GLCLOSURE", "UPDATE", 1);
                insert_m_permission(m_permission, callback, "accounting", "DELETE_GLCLOSURE", "GLCLOSURE", "DELETE", 1);
                insert_m_permission(m_permission, callback, "accounting", "CREATE_JOURNALENTRY", "JOURNALENTRY", "CREATE", 1);
                insert_m_permission(m_permission, callback, "accounting", "REVERSE_JOURNALENTRY", "JOURNALENTRY", "REVERSE", 1);
            });
        }
        {
            Table m_role = schema.getTableByName("m_role");
            dataContext.executeUpdate(callback -> {
                insert_m_role(m_role, callback, 1, "Super user", "This role provides all application permissions.");
            });
        }
        {
            Table m_role_permission = schema.getTableByName("m_role_permission");
            dataContext.executeUpdate(callback -> {
                long role_id = lookup_m_role(dataContext, "Super user");
                long permission_id = lookup_m_permission(dataContext, "ALL_FUNCTIONS");
                insert_m_role_permission(m_role_permission, callback, role_id, permission_id);
            });
        }
        {
            Table m_appuser = schema.getTableByName("m_appuser");
            dataContext.executeUpdate(callback -> {
                long office_id = lookup_m_office(dataContext, "Head Office");
                insert_m_appuser(m_appuser, callback, 1, office_id, "mifos", "App", "Administrator", "5787039480429368bf94732aacc771cd0a3ea02bcf504ffe1185ab94213bc63a", "demomfi@mifos.org", 0, false, false, false, false);
            });
        }
        {
            Table m_appuser_role = schema.getTableByName("m_appuser_role");
            dataContext.executeUpdate(callback -> {
                long appuser_id = lookup_m_appuser(dataContext, "mifos");
                long role_id = lookup_m_role(dataContext, "Super user");
                insert_m_appuser_role(m_appuser_role, callback, appuser_id, role_id);
            });
        }
        {
            Table x_registered_table = schema.getTableByName("x_registered_table");
            Table m_permission = schema.getTableByName("m_permission");
            try (DataSet rows = dataContext.query().from(x_registered_table).selectAll().execute()) {
                while (rows.next()) {
                    dataContext.executeUpdate(callback -> {
                        insert_m_permission(m_permission, callback, "datatable", "CREATE_" + rows.getRow().getValue(x_registered_table.getColumnByName("registered_table_name")), (String) rows.getRow().getValue(x_registered_table.getColumnByName("registered_table_name")), "CREATE");
                        insert_m_permission(m_permission, callback, "datatable", "READ_" + rows.getRow().getValue(x_registered_table.getColumnByName("registered_table_name")), (String) rows.getRow().getValue(x_registered_table.getColumnByName("registered_table_name")), "READ");
                        insert_m_permission(m_permission, callback, "datatable", "UPDATE_" + rows.getRow().getValue(x_registered_table.getColumnByName("registered_table_name")), (String) rows.getRow().getValue(x_registered_table.getColumnByName("registered_table_name")), "UPDATE");
                        insert_m_permission(m_permission, callback, "datatable", "DELETE_" + rows.getRow().getValue(x_registered_table.getColumnByName("registered_table_name")), (String) rows.getRow().getValue(x_registered_table.getColumnByName("registered_table_name")), "DELETE");
                    });
                }
            }
        }
        {
            Table m_permission = schema.getTableByName("m_permission");
            dataContext.executeUpdate(callback -> {
                callback.update(m_permission)
                        .value(m_permission.getColumnByName("can_maker_checker"), false)
                        .execute();
            });
        }
    }

    protected long lookup_m_appuser(DataContext dataContext, String username) {
        Table table = dataContext.getDefaultSchema().getTableByName("m_appuser");
        try (DataSet rows = dataContext.query().from(table).select(table.getColumnByName("id")).where(table.getColumnByName("username")).eq(username).execute()) {
            rows.next();
            return (long) rows.getRow().getValue(0);
        }
    }

    protected void insert_m_appuser_role(Table table, RowInsertable callback, long appuser_id, long role_id) {
        callback.insertInto(table)
                .value(table.getColumnByName("appuser_id"), appuser_id)
                .value(table.getColumnByName("role_id"), role_id)
                .execute();
    }

    protected void insert_m_appuser(Table table, RowInsertable callback, long id, long office_id, String username, String firstname, String lastname, String password, String email, long firsttime_login_remaining, boolean nonexpired, boolean nonlocked, boolean nonexpired_credentials, boolean enabled) {
        callback.insertInto(table)
                .value(table.getColumnByName("id"), id)
                .value(table.getColumnByName("office_id"), office_id)
                .value(table.getColumnByName("username"), username)
                .value(table.getColumnByName("firstname"), firstname)
                .value(table.getColumnByName("lastname"), lastname)
                .value(table.getColumnByName("password"), password)
                .value(table.getColumnByName("email"), email)
                .value(table.getColumnByName("firsttime_login_remaining"), firsttime_login_remaining)
                .value(table.getColumnByName("nonexpired"), nonexpired)
                .value(table.getColumnByName("nonlocked"), nonlocked)
                .value(table.getColumnByName("nonexpired_credentials"), nonexpired_credentials)
                .value(table.getColumnByName("enabled"), enabled)
                .execute();
    }

    protected void insert_m_role_permission(Table table, RowInsertable callback, long role_id, long permission_id) {
        callback.insertInto(table)
                .value(table.getColumnByName("role_id"), role_id)
                .value(table.getColumnByName("permission_id"), permission_id)
                .execute();
    }

    protected long lookup_m_office(DataContext dataContext, String name) {
        Table table = dataContext.getDefaultSchema().getTableByName("m_office");
        try (DataSet rows = dataContext.query().from(table).select(table.getColumnByName("id")).where(table.getColumnByName("name")).eq(name).execute()) {
            rows.next();
            return (long) rows.getRow().getValue(0);
        }
    }

    protected long lookup_m_permission(DataContext dataContext, String code) {
        Table table = dataContext.getDefaultSchema().getTableByName("m_permission");
        try (DataSet rows = dataContext.query().from(table).select(table.getColumnByName("id")).where(table.getColumnByName("code")).eq(code).execute()) {
            rows.next();
            return (long) rows.getRow().getValue(0);
        }
    }

    protected long lookup_m_role(DataContext dataContext, String name) {
        Table table = dataContext.getDefaultSchema().getTableByName("m_role");
        try (DataSet rows = dataContext.query().from(table).select(table.getColumnByName("id")).where(table.getColumnByName("name")).eq(name).execute()) {
            rows.next();
            return (long) rows.getRow().getValue(0);
        }
    }

    protected void insert_m_role(Table table, RowInsertable callback, long id, String name, String description) {
        callback.insertInto(table)
                .value(table.getColumnByName("id"), id)
                .value(table.getColumnByName("name"), name)
                .value(table.getColumnByName("description"), description)
                .execute();
    }

    protected void insert_m_permission(Table table, RowInsertable callback, String grouping, String code, String entity_name, String action_name, long can_maker_checker) {
        RowInsertionBuilder builder = callback.insertInto(table);
        builder.value(table.getColumnByName("grouping"), grouping);
        builder.value(table.getColumnByName("code"), code);
        builder.value(table.getColumnByName("entity_name"), entity_name);
        builder.value(table.getColumnByName("action_name"), action_name);
        builder.value(table.getColumnByName("can_maker_checker"), can_maker_checker);
        builder.execute();
    }

    protected void insert_m_permission(Table table, RowInsertable callback, String grouping, String code, String entity_name, String action_name) {
        RowInsertionBuilder builder = callback.insertInto(table);
        builder.value(table.getColumnByName("grouping"), grouping);
        builder.value(table.getColumnByName("code"), code);
        builder.value(table.getColumnByName("entity_name"), entity_name);
        builder.value(table.getColumnByName("action_name"), action_name);
        builder.execute();
    }
}