package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.jdbc.query.SelectQuery;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

public class V3__MifosXPermissionsAndAuthorisationUTF8 extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V3__MifosXPermissionsAndAuthorisationUTF8;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        {
            dataContext.refreshSchemas();
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            insert_m_permission(named, m_permission, "special", "ALL_FUNCTIONS", null, null, 0);
            insert_m_permission(named, m_permission, "special", "ALL_FUNCTIONS_READ", null, null, 0);
            insert_m_permission(named, m_permission, "special", "CHECKER_SUPER_USER", null, null, 0);
            insert_m_permission(named, m_permission, "special", "REPORTING_SUPER_USER", null, null, 0);
            insert_m_permission(named, m_permission, "authorisation", "READ_PERMISSION", "PERMISSION", "READ", 0);
            insert_m_permission(named, m_permission, "authorisation", "PERMISSIONS_ROLE", "ROLE", "PERMISSIONS", 1);
            insert_m_permission(named, m_permission, "authorisation", "CREATE_ROLE", "ROLE", "CREATE", 1);
            insert_m_permission(named, m_permission, "authorisation", "CREATE_ROLE_CHECKER", "ROLE", "CREATE", 0);
            insert_m_permission(named, m_permission, "authorisation", "READ_ROLE", "ROLE", "READ", 0);
            insert_m_permission(named, m_permission, "authorisation", "UPDATE_ROLE", "ROLE", "UPDATE", 1);
            insert_m_permission(named, m_permission, "authorisation", "UPDATE_ROLE_CHECKER", "ROLE", "UPDATE", 0);
            insert_m_permission(named, m_permission, "authorisation", "DELETE_ROLE", "ROLE", "DELETE", 1);
            insert_m_permission(named, m_permission, "authorisation", "DELETE_ROLE_CHECKER", "ROLE", "DELETE", 0);
            insert_m_permission(named, m_permission, "authorisation", "CREATE_USER", "USER", "CREATE", 1);
            insert_m_permission(named, m_permission, "authorisation", "CREATE_USER_CHECKER", "USER", "CREATE", 0);
            insert_m_permission(named, m_permission, "authorisation", "READ_USER", "USER", "READ", 0);
            insert_m_permission(named, m_permission, "authorisation", "UPDATE_USER", "USER", "UPDATE", 1);
            insert_m_permission(named, m_permission, "authorisation", "UPDATE_USER_CHECKER", "USER", "UPDATE", 0);
            insert_m_permission(named, m_permission, "authorisation", "DELETE_USER", "USER", "DELETE", 1);
            insert_m_permission(named, m_permission, "authorisation", "DELETE_USER_CHECKER", "USER", "DELETE", 0);
            insert_m_permission(named, m_permission, "configuration", "READ_CONFIGURATION", "CONFIGURATION", "READ", 1);
            insert_m_permission(named, m_permission, "configuration", "UPDATE_CONFIGURATION", "CONFIGURATION", "UPDATE", 1);
            insert_m_permission(named, m_permission, "configuration", "UPDATE_CONFIGURATION_CHECKER", "CONFIGURATION", "UPDATE", 0);
            insert_m_permission(named, m_permission, "configuration", "READ_CODE", "CODE", "READ", 0);
            insert_m_permission(named, m_permission, "configuration", "CREATE_CODE", "CODE", "CREATE", 1);
            insert_m_permission(named, m_permission, "configuration", "CREATE_CODE_CHECKER", "CODE", "CREATE", 0);
            insert_m_permission(named, m_permission, "configuration", "UPDATE_CODE", "CODE", "UPDATE", 1);
            insert_m_permission(named, m_permission, "configuration", "UPDATE_CODE_CHECKER", "CODE", "UPDATE", 0);
            insert_m_permission(named, m_permission, "configuration", "DELETE_CODE", "CODE", "DELETE", 1);
            insert_m_permission(named, m_permission, "configuration", "DELETE_CODE_CHECKER", "CODE", "DELETE", 0);
            insert_m_permission(named, m_permission, "configuration", "READ_CODEVALUE", "CODEVALUE", "READ", 0);
            insert_m_permission(named, m_permission, "configuration", "CREATE_CODEVALUE", "CODEVALUE", "CREATE", 1);
            insert_m_permission(named, m_permission, "configuration", "CREATE_CODEVALUE_CHECKER", "CODEVALUE", "CREATE", 0);
            insert_m_permission(named, m_permission, "configuration", "UPDATE_CODEVALUE", "CODEVALUE", "UPDATE", 1);
            insert_m_permission(named, m_permission, "configuration", "UPDATE_CODEVALUE_CHECKER", "CODEVALUE", "UPDATE", 0);
            insert_m_permission(named, m_permission, "configuration", "DELETE_CODEVALUE", "CODEVALUE", "DELETE", 1);
            insert_m_permission(named, m_permission, "configuration", "DELETE_CODEVALUE_CHECKER", "CODEVALUE", "DELETE", 0);
            insert_m_permission(named, m_permission, "configuration", "READ_CURRENCY", "CURRENCY", "READ", 0);
            insert_m_permission(named, m_permission, "configuration", "UPDATE_CURRENCY", "CURRENCY", "UPDATE", 1);
            insert_m_permission(named, m_permission, "configuration", "UPDATE_CURRENCY_CHECKER", "CURRENCY", "UPDATE", 0);
            insert_m_permission(named, m_permission, "configuration", "UPDATE_PERMISSION", "PERMISSION", "UPDATE", 1);
            insert_m_permission(named, m_permission, "configuration", "UPDATE_PERMISSION_CHECKER", "PERMISSION", "UPDATE", 0);
            insert_m_permission(named, m_permission, "configuration", "READ_DATATABLE", "DATATABLE", "READ", 0);
            insert_m_permission(named, m_permission, "configuration", "REGISTER_DATATABLE", "DATATABLE", "REGISTER", 1);
            insert_m_permission(named, m_permission, "configuration", "REGISTER_DATATABLE_CHECKER", "DATATABLE", "REGISTER", 0);
            insert_m_permission(named, m_permission, "configuration", "DEREGISTER_DATATABLE", "DATATABLE", "DEREGISTER", 1);
            insert_m_permission(named, m_permission, "configuration", "DEREGISTER_DATATABLE_CHECKER", "DATATABLE", "DEREGISTER", 0);
            insert_m_permission(named, m_permission, "configuration", "READ_AUDIT", "AUDIT", "READ", 0);
            insert_m_permission(named, m_permission, "configuration", "CREATE_CALENDAR", "CALENDAR", "CREATE", 0);
            insert_m_permission(named, m_permission, "configuration", "READ_CALENDAR", "CALENDAR", "READ", 0);
            insert_m_permission(named, m_permission, "configuration", "UPDATE_CALENDAR", "CALENDAR", "UPDATE", 0);
            insert_m_permission(named, m_permission, "configuration", "DELETE_CALENDAR", "CALENDAR", "DELETE", 0);
            insert_m_permission(named, m_permission, "configuration", "CREATE_CALENDAR_CHECKER", "CALENDAR", "CREATE", 0);
            insert_m_permission(named, m_permission, "configuration", "UPDATE_CALENDAR_CHECKER", "CALENDAR", "UPDATE", 0);
            insert_m_permission(named, m_permission, "configuration", "DELETE_CALENDAR_CHECKER", "CALENDAR", "DELETE", 0);
            insert_m_permission(named, m_permission, "organisation", "READ_MAKERCHECKER", "MAKERCHECKER", "READ", 0);
            insert_m_permission(named, m_permission, "organisation", "READ_CHARGE", "CHARGE", "READ", 0);
            insert_m_permission(named, m_permission, "organisation", "CREATE_CHARGE", "CHARGE", "CREATE", 1);
            insert_m_permission(named, m_permission, "organisation", "CREATE_CHARGE_CHECKER", "CHARGE", "CREATE", 0);
            insert_m_permission(named, m_permission, "organisation", "UPDATE_CHARGE", "CHARGE", "UPDATE", 1);
            insert_m_permission(named, m_permission, "organisation", "UPDATE_CHARGE_CHECKER", "CHARGE", "UPDATE", 0);
            insert_m_permission(named, m_permission, "organisation", "DELETE_CHARGE", "CHARGE", "DELETE", 1);
            insert_m_permission(named, m_permission, "organisation", "DELETE_CHARGE_CHECKER", "CHARGE", "DELETE", 0);
            insert_m_permission(named, m_permission, "organisation", "READ_FUND", "FUND", "READ", 0);
            insert_m_permission(named, m_permission, "organisation", "CREATE_FUND", "FUND", "CREATE", 1);
            insert_m_permission(named, m_permission, "organisation", "CREATE_FUND_CHECKER", "FUND", "CREATE", 0);
            insert_m_permission(named, m_permission, "organisation", "UPDATE_FUND", "FUND", "UPDATE", 1);
            insert_m_permission(named, m_permission, "organisation", "UPDATE_FUND_CHECKER", "FUND", "UPDATE", 0);
            insert_m_permission(named, m_permission, "organisation", "DELETE_FUND", "FUND", "DELETE", 1);
            insert_m_permission(named, m_permission, "organisation", "DELETE_FUND_CHECKER", "FUND", "DELETE", 0);
            insert_m_permission(named, m_permission, "organisation", "READ_LOANPRODUCT", "LOANPRODUCT", "READ", 0);
            insert_m_permission(named, m_permission, "organisation", "CREATE_LOANPRODUCT", "LOANPRODUCT", "CREATE", 1);
            insert_m_permission(named, m_permission, "organisation", "CREATE_LOANPRODUCT_CHECKER", "LOANPRODUCT", "CREATE", 0);
            insert_m_permission(named, m_permission, "organisation", "UPDATE_LOANPRODUCT", "LOANPRODUCT", "UPDATE", 1);
            insert_m_permission(named, m_permission, "organisation", "UPDATE_LOANPRODUCT_CHECKER", "LOANPRODUCT", "UPDATE", 0);
            insert_m_permission(named, m_permission, "organisation", "DELETE_LOANPRODUCT", "LOANPRODUCT", "DELETE", 1);
            insert_m_permission(named, m_permission, "organisation", "DELETE_LOANPRODUCT_CHECKER", "LOANPRODUCT", "DELETE", 0);
            insert_m_permission(named, m_permission, "organisation", "READ_OFFICE", "OFFICE", "READ", 0);
            insert_m_permission(named, m_permission, "organisation", "CREATE_OFFICE", "OFFICE", "CREATE", 1);
            insert_m_permission(named, m_permission, "organisation", "CREATE_OFFICE_CHECKER", "OFFICE", "CREATE", 0);
            insert_m_permission(named, m_permission, "organisation", "UPDATE_OFFICE", "OFFICE", "UPDATE", 1);
            insert_m_permission(named, m_permission, "organisation", "UPDATE_OFFICE_CHECKER", "OFFICE", "UPDATE", 0);
            insert_m_permission(named, m_permission, "organisation", "READ_OFFICETRANSACTION", "OFFICETRANSACTION", "READ", 0);
            insert_m_permission(named, m_permission, "organisation", "DELETE_OFFICE_CHECKER", "OFFICE", "DELETE", 0);
            insert_m_permission(named, m_permission, "organisation", "CREATE_OFFICETRANSACTION", "OFFICETRANSACTION", "CREATE", 1);
            insert_m_permission(named, m_permission, "organisation", "CREATE_OFFICETRANSACTION_CHECKER", "OFFICETRANSACTION", "CREATE", 0);
            insert_m_permission(named, m_permission, "organisation", "DELETE_OFFICETRANSACTION", "OFFICETRANSACTION", "DELETE", 1);
            insert_m_permission(named, m_permission, "organisation", "DELETE_OFFICETRANSACTION_CHECKER", "OFFICETRANSACTION", "DELETE", 0);
            insert_m_permission(named, m_permission, "organisation", "READ_STAFF", "STAFF", "READ", 0);
            insert_m_permission(named, m_permission, "organisation", "CREATE_STAFF", "STAFF", "CREATE", 1);
            insert_m_permission(named, m_permission, "organisation", "CREATE_STAFF_CHECKER", "STAFF", "CREATE", 0);
            insert_m_permission(named, m_permission, "organisation", "UPDATE_STAFF", "STAFF", "UPDATE", 1);
            insert_m_permission(named, m_permission, "organisation", "UPDATE_STAFF_CHECKER", "STAFF", "UPDATE", 0);
            insert_m_permission(named, m_permission, "organisation", "DELETE_STAFF", "STAFF", "DELETE", 1);
            insert_m_permission(named, m_permission, "organisation", "DELETE_STAFF_CHECKER", "STAFF", "DELETE", 0);
            insert_m_permission(named, m_permission, "organisation", "READ_SAVINGSPRODUCT", "SAVINGSPRODUCT", "READ", 0);
            insert_m_permission(named, m_permission, "organisation", "CREATE_SAVINGSPRODUCT", "SAVINGSPRODUCT", "CREATE", 1);
            insert_m_permission(named, m_permission, "organisation", "CREATE_SAVINGSPRODUCT_CHECKER", "SAVINGSPRODUCT", "CREATE", 0);
            insert_m_permission(named, m_permission, "organisation", "UPDATE_SAVINGSPRODUCT", "SAVINGSPRODUCT", "UPDATE", 1);
            insert_m_permission(named, m_permission, "organisation", "UPDATE_SAVINGSPRODUCT_CHECKER", "SAVINGSPRODUCT", "UPDATE", 0);
            insert_m_permission(named, m_permission, "organisation", "DELETE_SAVINGSPRODUCT", "SAVINGSPRODUCT", "DELETE", 1);
            insert_m_permission(named, m_permission, "organisation", "DELETE_SAVINGSPRODUCT_CHECKER", "SAVINGSPRODUCT", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_LOAN", "LOAN", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_LOAN", "LOAN", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_LOAN_CHECKER", "LOAN", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_LOAN", "LOAN", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_LOAN_CHECKER", "LOAN", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_LOAN", "LOAN", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_LOAN_CHECKER", "LOAN", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_CLIENT", "CLIENT", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_CLIENT", "CLIENT", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_CLIENT_CHECKER", "CLIENT", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_CLIENT", "CLIENT", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_CLIENT_CHECKER", "CLIENT", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_CLIENT", "CLIENT", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_CLIENT_CHECKER", "CLIENT", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_CLIENTIMAGE", "CLIENTIMAGE", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_CLIENTIMAGE", "CLIENTIMAGE", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_CLIENTIMAGE_CHECKER", "CLIENTIMAGE", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_CLIENTIMAGE", "CLIENTIMAGE", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_CLIENTIMAGE_CHECKER", "CLIENTIMAGE", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_CLIENTNOTE", "CLIENTNOTE", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_CLIENTNOTE", "CLIENTNOTE", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_CLIENTNOTE_CHECKER", "CLIENTNOTE", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_CLIENTNOTE", "CLIENTNOTE", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_CLIENTNOTE_CHECKER", "CLIENTNOTE", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_CLIENTNOTE", "CLIENTNOTE", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_CLIENTNOTE_CHECKER", "CLIENTNOTE", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_GROUPNOTE", "GROUPNOTE", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_GROUPNOTE", "GROUPNOTE", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_GROUPNOTE", "GROUPNOTE", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_GROUPNOTE", "GROUPNOTE", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_GROUPNOTE_CHECKER", "GROUPNOTE", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_GROUPNOTE_CHECKER", "GROUPNOTE", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_GROUPNOTE_CHECKER", "GROUPNOTE", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_LOANNOTE", "LOANNOTE", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_LOANNOTE", "LOANNOTE", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_LOANNOTE", "LOANNOTE", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_LOANNOTE", "LOANNOTE", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_LOANNOTE_CHECKER", "LOANNOTE", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_LOANNOTE_CHECKER", "LOANNOTE", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_LOANNOTE_CHECKER", "LOANNOTE", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_LOANTRANSACTIONNOTE", "LOANTRANSACTIONNOTE", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_LOANTRANSACTIONNOTE", "LOANTRANSACTIONNOTE", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_LOANTRANSACTIONNOTE", "LOANTRANSACTIONNOTE", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_LOANTRANSACTIONNOTE", "LOANTRANSACTIONNOTE", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_LOANTRANSACTIONNOTE_CHECKER", "LOANTRANSACTIONNOTE", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_LOANTRANSACTIONNOTE_CHECKER", "LOANTRANSACTIONNOTE", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_LOANTRANSACTIONNOTE_CHECKER", "LOANTRANSACTIONNOTE", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_SAVINGNOTE", "SAVINGNOTE", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_SAVINGNOTE", "SAVINGNOTE", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_SAVINGNOTE", "SAVINGNOTE", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_SAVINGNOTE", "SAVINGNOTE", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_SAVINGNOTE_CHECKER", "SAVINGNOTE", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_SAVINGNOTE_CHECKER", "SAVINGNOTE", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_SAVINGNOTE_CHECKER", "SAVINGNOTE", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_CLIENTIDENTIFIER", "CLIENTIDENTIFIER", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_CLIENTIDENTIFIER", "CLIENTIDENTIFIER", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_CLIENTIDENTIFIER_CHECKER", "CLIENTIDENTIFIER", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_CLIENTIDENTIFIER", "CLIENTIDENTIFIER", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_CLIENTIDENTIFIER_CHECKER", "CLIENTIDENTIFIER", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_CLIENTIDENTIFIER", "CLIENTIDENTIFIER", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_CLIENTIDENTIFIER_CHECKER", "CLIENTIDENTIFIER", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_DOCUMENT", "DOCUMENT", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_DOCUMENT", "DOCUMENT", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_DOCUMENT_CHECKER", "DOCUMENT", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_DOCUMENT", "DOCUMENT", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_DOCUMENT_CHECKER", "DOCUMENT", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_DOCUMENT", "DOCUMENT", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_DOCUMENT_CHECKER", "DOCUMENT", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_GROUP", "GROUP", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_GROUP", "GROUP", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_GROUP_CHECKER", "GROUP", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_GROUP", "GROUP", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_GROUP_CHECKER", "GROUP", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_GROUP", "GROUP", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_GROUP_CHECKER", "GROUP", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UNASSIGNSTAFF_GROUP", "GROUP", "UNASSIGNSTAFF", 1);
            insert_m_permission(named, m_permission, "portfolio", "UNASSIGNSTAFF_GROUP_CHECKER", "GROUP", "UNASSIGNSTAFF", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_LOANCHARGE", "LOANCHARGE", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_LOANCHARGE_CHECKER", "LOANCHARGE", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_LOANCHARGE", "LOANCHARGE", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_LOANCHARGE_CHECKER", "LOANCHARGE", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_LOANCHARGE", "LOANCHARGE", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_LOANCHARGE_CHECKER", "LOANCHARGE", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "WAIVE_LOANCHARGE", "LOANCHARGE", "WAIVE", 1);
            insert_m_permission(named, m_permission, "portfolio", "WAIVE_LOANCHARGE_CHECKER", "LOANCHARGE", "WAIVE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_SAVINGSACCOUNT", "SAVINGSACCOUNT", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_SAVINGSACCOUNT", "SAVINGSACCOUNT", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_SAVINGSACCOUNT", "SAVINGSACCOUNT", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_SAVINGSACCOUNT", "SAVINGSACCOUNT", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_GUARANTOR", "GUARANTOR", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_GUARANTOR", "GUARANTOR", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_GUARANTOR_CHECKER", "GUARANTOR", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_GUARANTOR", "GUARANTOR", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_GUARANTOR_CHECKER", "GUARANTOR", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_GUARANTOR", "GUARANTOR", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_GUARANTOR_CHECKER", "GUARANTOR", "DELETE", 0);
            insert_m_permission(named, m_permission, "portfolio", "READ_COLLATERAL", "COLLATERAL", "READ", 0);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_COLLATERAL", "COLLATERAL", "CREATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_COLLATERAL", "COLLATERAL", "UPDATE", 1);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_COLLATERAL", "COLLATERAL", "DELETE", 1);
            insert_m_permission(named, m_permission, "portfolio", "CREATE_COLLATERAL_CHECKER", "COLLATERAL", "CREATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "UPDATE_COLLATERAL_CHECKER", "COLLATERAL", "UPDATE", 0);
            insert_m_permission(named, m_permission, "portfolio", "DELETE_COLLATERAL_CHECKER", "COLLATERAL", "DELETE", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "APPROVE_LOAN", "LOAN", "APPROVE", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "APPROVEINPAST_LOAN", "LOAN", "APPROVEINPAST", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "REJECT_LOAN", "LOAN", "REJECT", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "REJECTINPAST_LOAN", "LOAN", "REJECTINPAST", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "WITHDRAW_LOAN", "LOAN", "WITHDRAW", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "WITHDRAWINPAST_LOAN", "LOAN", "WITHDRAWINPAST", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "APPROVALUNDO_LOAN", "LOAN", "APPROVALUNDO", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "DISBURSE_LOAN", "LOAN", "DISBURSE", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "DISBURSEINPAST_LOAN", "LOAN", "DISBURSEINPAST", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "DISBURSALUNDO_LOAN", "LOAN", "DISBURSALUNDO", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "REPAYMENT_LOAN", "LOAN", "REPAYMENT", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "REPAYMENTINPAST_LOAN", "LOAN", "REPAYMENTINPAST", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "ADJUST_LOAN", "LOAN", "ADJUST", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "WAIVEINTERESTPORTION_LOAN", "LOAN", "WAIVEINTERESTPORTION", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "WRITEOFF_LOAN", "LOAN", "WRITEOFF", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "CLOSE_LOAN", "LOAN", "CLOSE", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "CLOSEASRESCHEDULED_LOAN", "LOAN", "CLOSEASRESCHEDULED", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "UPDATELOANOFFICER_LOAN", "LOAN", "UPDATELOANOFFICER", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "UPDATELOANOFFICER_LOAN_CHECKER", "LOAN", "UPDATELOANOFFICER", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "REMOVELOANOFFICER_LOAN", "LOAN", "REMOVELOANOFFICER", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "REMOVELOANOFFICER_LOAN_CHECKER", "LOAN", "REMOVELOANOFFICER", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "BULKREASSIGN_LOAN", "LOAN", "BULKREASSIGN", 1);
            insert_m_permission(named, m_permission, "transaction_loan", "BULKREASSIGN_LOAN_CHECKER", "LOAN", "BULKREASSIGN", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "APPROVE_LOAN_CHECKER", "LOAN", "APPROVE", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "APPROVEINPAST_LOAN_CHECKER", "LOAN", "APPROVEINPAST", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "REJECT_LOAN_CHECKER", "LOAN", "REJECT", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "REJECTINPAST_LOAN_CHECKER", "LOAN", "REJECTINPAST", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "WITHDRAW_LOAN_CHECKER", "LOAN", "WITHDRAW", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "WITHDRAWINPAST_LOAN_CHECKER", "LOAN", "WITHDRAWINPAST", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "APPROVALUNDO_LOAN_CHECKER", "LOAN", "APPROVALUNDO", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "DISBURSE_LOAN_CHECKER", "LOAN", "DISBURSE", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "DISBURSEINPAST_LOAN_CHECKER", "LOAN", "DISBURSEINPAST", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "DISBURSALUNDO_LOAN_CHECKER", "LOAN", "DISBURSALUNDO", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "REPAYMENT_LOAN_CHECKER", "LOAN", "REPAYMENT", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "REPAYMENTINPAST_LOAN_CHECKER", "LOAN", "REPAYMENTINPAST", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "ADJUST_LOAN_CHECKER", "LOAN", "ADJUST", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "WAIVEINTERESTPORTION_LOAN_CHECKER", "LOAN", "WAIVEINTERESTPORTION", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "WRITEOFF_LOAN_CHECKER", "LOAN", "WRITEOFF", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "CLOSE_LOAN_CHECKER", "LOAN", "CLOSE", 0);
            insert_m_permission(named, m_permission, "transaction_loan", "CLOSEASRESCHEDULED_LOAN_CHECKER", "LOAN", "CLOSEASRESCHEDULED", 0);
            insert_m_permission(named, m_permission, "transaction_savings", "DEPOSIT_SAVINGSACCOUNT", "SAVINGSACCOUNT", "DEPOSIT", 1);
            insert_m_permission(named, m_permission, "transaction_savings", "DEPOSIT_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "DEPOSIT", 0);
            insert_m_permission(named, m_permission, "transaction_savings", "WITHDRAWAL_SAVINGSACCOUNT", "SAVINGSACCOUNT", "WITHDRAWAL", 1);
            insert_m_permission(named, m_permission, "transaction_savings", "WITHDRAWAL_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "WITHDRAWAL", 0);
            insert_m_permission(named, m_permission, "transaction_savings", "ACTIVATE_SAVINGSACCOUNT", "SAVINGSACCOUNT", "ACTIVATE", 1);
            insert_m_permission(named, m_permission, "transaction_savings", "ACTIVATE_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "ACTIVATE", 0);
            insert_m_permission(named, m_permission, "transaction_savings", "CALCULATEINTEREST_SAVINGSACCOUNT", "SAVINGSACCOUNT", "CALCULATEINTEREST", 1);
            insert_m_permission(named, m_permission, "transaction_savings", "CALCULATEINTEREST_SAVINGSACCOUNT_CHECKER", "SAVINGSACCOUNT", "CALCULATEINTEREST", 0);
            insert_m_permission(named, m_permission, "accounting", "CREATE_GLACCOUNT", "GLACCOUNT", "CREATE", 1);
            insert_m_permission(named, m_permission, "accounting", "UPDATE_GLACCOUNT", "GLACCOUNT", "UPDATE", 1);
            insert_m_permission(named, m_permission, "accounting", "DELETE_GLACCOUNT", "GLACCOUNT", "DELETE", 1);
            insert_m_permission(named, m_permission, "accounting", "CREATE_GLCLOSURE", "GLCLOSURE", "CREATE", 1);
            insert_m_permission(named, m_permission, "accounting", "UPDATE_GLCLOSURE", "GLCLOSURE", "UPDATE", 1);
            insert_m_permission(named, m_permission, "accounting", "DELETE_GLCLOSURE", "GLCLOSURE", "DELETE", 1);
            insert_m_permission(named, m_permission, "accounting", "CREATE_JOURNALENTRY", "JOURNALENTRY", "CREATE", 1);
            insert_m_permission(named, m_permission, "accounting", "REVERSE_JOURNALENTRY", "JOURNALENTRY", "REVERSE", 1);
        }
        {
            Table m_role = dataContext.getDefaultSchema().getTableByName("m_role");
            insert_m_role(named, m_role, 1, "Super user", "This role provides all application permissions.");
        }
        {
            Table m_role_permission = dataContext.getDefaultSchema().getTableByName("m_role_permission");
            long role_id = lookup_m_role(named, dataContext, "Super user");
            long permission_id = lookup_m_permission(named, dataContext, "ALL_FUNCTIONS");
            insert_m_role_permission(named, m_role_permission, role_id, permission_id);
        }
        {
            Table m_appuser = dataContext.getDefaultSchema().getTableByName("m_appuser");
            long office_id = lookup_m_office(named, dataContext, "Head Office");
            insert_m_appuser(named, m_appuser, 1, office_id, "mifos", "App", "Administrator", "5787039480429368bf94732aacc771cd0a3ea02bcf504ffe1185ab94213bc63a", "demomfi@mifos.org", 0, false, false, false, false);
        }
        {
            Table m_appuser_role = dataContext.getDefaultSchema().getTableByName("m_appuser_role");
            long appuser_id = lookup_m_appuser(named, dataContext, "mifos");
            long role_id = lookup_m_role(named, dataContext, "Super user");
            insert_m_appuser_role(named, m_appuser_role, appuser_id, role_id);
        }
        {
            Table x_registered_table = dataContext.getDefaultSchema().getTableByName("x_registered_table");
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            SelectQuery selectQuery = new SelectQuery(x_registered_table.getName());
            for (Map<String, Object> record : named.queryForList(selectQuery.toSQL(), selectQuery.toParam())) {
                insert_m_permission(named, m_permission, "datatable", "CREATE_" + record.get(x_registered_table.getColumnByName("registered_table_name").getName()), (String) record.get(x_registered_table.getColumnByName("registered_table_name").getName()), "CREATE");
                insert_m_permission(named, m_permission, "datatable", "READ_" + record.get(x_registered_table.getColumnByName("registered_table_name").getName()), (String) record.get(x_registered_table.getColumnByName("registered_table_name").getName()), "READ");
                insert_m_permission(named, m_permission, "datatable", "UPDATE_" + record.get(x_registered_table.getColumnByName("registered_table_name").getName()), (String) record.get(x_registered_table.getColumnByName("registered_table_name").getName()), "UPDATE");
                insert_m_permission(named, m_permission, "datatable", "DELETE_" + record.get(x_registered_table.getColumnByName("registered_table_name").getName()), (String) record.get(x_registered_table.getColumnByName("registered_table_name").getName()), "DELETE");
            }
        }
        {
            Table m_permission = dataContext.getDefaultSchema().getTableByName("m_permission");
            UpdateQuery updateQuery = new UpdateQuery(m_permission.getName());
            updateQuery.addValue(m_permission.getColumnByName("can_maker_checker").getName(), false);
            named.update(updateQuery.toSQL(), updateQuery.toParam());
        }
    }

    protected long lookup_m_appuser(NamedParameterJdbcTemplate named, DataContext dataContext, String username) {
        Table table = dataContext.getDefaultSchema().getTableByName("m_appuser");
        SelectQuery selectQuery = new SelectQuery(table.getName());
        selectQuery.addField(table.getColumnByName("id").getName());
        selectQuery.addWhere(table.getColumnByName("username").getName() + " = :username", username);
        Long id = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), long.class);
        return id == null ? 0 : id;
    }

    protected void insert_m_appuser_role(NamedParameterJdbcTemplate named, Table table, long appuser_id, long role_id) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("appuser_id").getName(), appuser_id);
        insertQuery.addValue(table.getColumnByName("role_id").getName(), role_id);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_m_appuser(NamedParameterJdbcTemplate named, Table table, long id, long office_id, String username, String firstname, String lastname, String password, String email, long firsttime_login_remaining, boolean nonexpired, boolean nonlocked, boolean nonexpired_credentials, boolean enabled) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("id").getName(), id);
        insertQuery.addValue(table.getColumnByName("office_id").getName(), office_id);
        insertQuery.addValue(table.getColumnByName("username").getName(), username);
        insertQuery.addValue(table.getColumnByName("firstname").getName(), firstname);
        insertQuery.addValue(table.getColumnByName("lastname").getName(), lastname);
        insertQuery.addValue(table.getColumnByName("password").getName(), password);
        insertQuery.addValue(table.getColumnByName("email").getName(), email);
        insertQuery.addValue(table.getColumnByName("firsttime_login_remaining").getName(), firsttime_login_remaining);
        insertQuery.addValue(table.getColumnByName("nonexpired").getName(), nonexpired);
        insertQuery.addValue(table.getColumnByName("nonlocked").getName(), nonlocked);
        insertQuery.addValue(table.getColumnByName("nonexpired_credentials").getName(), nonexpired_credentials);
        insertQuery.addValue(table.getColumnByName("enabled").getName(), enabled);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_m_role_permission(NamedParameterJdbcTemplate named, Table table, long role_id, long permission_id) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("role_id").getName(), role_id);
        insertQuery.addValue(table.getColumnByName("permission_id").getName(), permission_id);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected long lookup_m_office(NamedParameterJdbcTemplate named, DataContext dataContext, String name) {
        Table table = dataContext.getDefaultSchema().getTableByName("m_office");
        SelectQuery selectQuery = new SelectQuery(table.getName());
        selectQuery.addField(table.getColumnByName("id").getName());
        selectQuery.addWhere(table.getColumnByName("name").getName() + " = :name", name);
        Long id = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), long.class);
        return id == null ? 0 : id;
    }

    protected long lookup_m_permission(NamedParameterJdbcTemplate named, DataContext dataContext, String code) {
        Table table = dataContext.getDefaultSchema().getTableByName("m_permission");
        SelectQuery selectQuery = new SelectQuery(table.getName());
        selectQuery.addField(table.getColumnByName("id").getName());
        selectQuery.addWhere(table.getColumnByName("code").getName() + " = :code", code);
        Long id = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), long.class);
        return id == null ? 0 : id;
    }

    protected long lookup_m_role(NamedParameterJdbcTemplate named, DataContext dataContext, String name) {
        Table table = dataContext.getDefaultSchema().getTableByName("m_role");
        SelectQuery selectQuery = new SelectQuery(table.getName());
        selectQuery.addField(table.getColumnByName("id").getName());
        selectQuery.addWhere(table.getColumnByName("name").getName() + " = :name", name);
        Long id = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), long.class);
        return id == null ? 0 : id;
    }

    protected void insert_m_role(NamedParameterJdbcTemplate named, Table table, long id, String name, String description) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("id").getName(), id);
        insertQuery.addValue(table.getColumnByName("name").getName(), name);
        insertQuery.addValue(table.getColumnByName("description").getName(), description);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_m_permission(NamedParameterJdbcTemplate named, Table table, String grouping, String code, String entity_name, String action_name, long can_maker_checker) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("grouping").getQualifiedLabel(), grouping);
        insertQuery.addValue(table.getColumnByName("code").getName(), code);
        insertQuery.addValue(table.getColumnByName("entity_name").getName(), entity_name);
        insertQuery.addValue(table.getColumnByName("action_name").getName(), action_name);
        insertQuery.addValue(table.getColumnByName("can_maker_checker").getName(), can_maker_checker);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_m_permission(NamedParameterJdbcTemplate named, Table table, String grouping, String code, String entity_name, String action_name) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("grouping").getQualifiedLabel(), grouping);
        insertQuery.addValue(table.getColumnByName("code").getName(), code);
        insertQuery.addValue(table.getColumnByName("entity_name").getName(), entity_name);
        insertQuery.addValue(table.getColumnByName("action_name").getName(), action_name);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }
}