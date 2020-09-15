package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.UpdateQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V18__UpdateStretchyReportingReportSql extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V18__UpdateStretchyReportingReportSql;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        UpdateQuery updateQuery = null;
        {
            dataContext.refreshSchemas();
            Table stretchy_parameter = dataContext.getDefaultSchema().getTableByName("stretchy_parameter");
            updateQuery = new UpdateQuery(stretchy_parameter.getName());
            updateQuery.addValue(stretchy_parameter.getColumnByName("parameter_sql").getName(), "select sp.parameter_name, sp.parameter_variable, sp.parameter_label, sp.parameter_displayType, \n sp.parameter_FormatType, sp.parameter_default, sp.selectOne,  sp.selectAll, spp.parameter_name as parentParameterName\n from stretchy_parameter sp\n left join stretchy_parameter spp on spp.id = sp.parent_id\n where sp.special is null\n and exists \n \t(select 'f' \n \tfrom stretchy_report sr\n \tjoin stretchy_report_parameter srp on srp.report_id = sr.id\n \twhere sr.report_name in(${reportListing})\n \tand srp.parameter_id = sp.id\n \t)\n order by sp.id");
            updateQuery.addWhere(stretchy_parameter.getColumnByName("id").getName() + " = :id", 1002);
            named.update(updateQuery.toSQL(), updateQuery.toParam());

            updateQuery = new UpdateQuery(stretchy_parameter.getName());
            updateQuery.addValue(stretchy_parameter.getColumnByName("parameter_sql").getName(), "select  r.id as report_id, r.report_name, r.report_type, r.report_subtype, r.report_category,\nrp.id as parameter_id, rp.report_parameter_name, p.parameter_name\n  from stretchy_report r\n  left join stretchy_report_parameter rp on rp.report_id = r.id \n  left join stretchy_parameter p on p.id = rp.parameter_id\n  where r.use_report is true\n  and exists\n  ( select 'f'\n  from m_appuser_role ur \n  join m_role r on r.id = ur.role_id\n  join m_role_permission rp on rp.role_id = r.id\n  join m_permission p on p.id = rp.permission_id\n  where ur.appuser_id = ${currentUserId}\n  and (p.code in ('ALL_FUNCTIONS_READ', 'ALL_FUNCTIONS') or p.code = concat(\"READ_\", r.report_name)) )\n  order by r.report_category, r.report_name, rp.id");
            updateQuery.addWhere(stretchy_parameter.getColumnByName("id").getName() + " = :id", 1001);
            named.update(updateQuery.toSQL(), updateQuery.toParam());

            updateQuery = new UpdateQuery(stretchy_parameter.getName());
            updateQuery.addValue(stretchy_parameter.getColumnByName("parameter_sql").getName(), "select  r.id as report_id, r.report_name, r.report_type, r.report_subtype, r.report_category,\n  rp.id as parameter_id, rp.report_parameter_name, p.parameter_name\n  from stretchy_report r\n  left join stretchy_report_parameter rp on rp.report_id = r.id\n  left join stretchy_parameter p on p.id = rp.parameter_id\n  where r.report_category = '${reportCategory}'\n  and r.use_report is true\n  and exists\n  (select 'f'\n  from m_appuser_role ur \n  join m_role r on r.id = ur.role_id\n  join m_role_permission rp on rp.role_id = r.id\n  join m_permission p on p.id = rp.permission_id\n  where ur.appuser_id = ${currentUserId}\n  and (p.code in ('ALL_FUNCTIONS_READ', 'ALL_FUNCTIONS') or p.code = concat(\"READ_\", r.report_name)) )\n  order by r.report_category, r.report_name, rp.id");
            updateQuery.addWhere(stretchy_parameter.getColumnByName("id").getName() + " = :id", 1003);
            named.update(updateQuery.toSQL(), updateQuery.toParam());
        }
    }

}
