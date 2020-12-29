package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.jdbc.query.SelectQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

public class V17__UpdateStretchyReportingDdl extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V17__UpdateStretchyReportingDdl + getInternalChecksum(
                "V17__update_stretchy_reporting_ddl-001.xml",
                "V17__update_stretchy_reporting_ddl-003.xml",
                "V17__update_stretchy_reporting_ddl-005.xml",
                "V17__update_stretchy_reporting_ddl-007.xml");
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        SelectQuery selectQuery = null;
        {
            // sub change 001
            updateLiquibase("V17__update_stretchy_reporting_ddl-001.xml");
        }
        {
            // sub change 002
            dataContext.refreshSchemas();
            Table stretchy_report = dataContext.getDefaultSchema().getTableByName("stretchy_report");
            Table stretchy_report_tmp = dataContext.getDefaultSchema().getTableByName("stretchy_report_tmp");
            selectQuery = new SelectQuery(stretchy_report.getName());
            for (Map<String, Object> record : named.queryForList(selectQuery.toSQL(), selectQuery.toParam())) {
                Integer report_id = (Integer) record.get(stretchy_report.getColumnByName("report_id").getName());
                String report_name = (String) record.get(stretchy_report.getColumnByName("report_name").getName());
                String report_type = (String) record.get(stretchy_report.getColumnByName("report_type").getName());
                String report_subtype = (String) record.get(stretchy_report.getColumnByName("report_subtype").getName());
                String report_category = (String) record.get(stretchy_report.getColumnByName("report_category").getName());
                String report_sql = (String) record.get(stretchy_report.getColumnByName("report_sql").getName());
                String description = (String) record.get(stretchy_report.getColumnByName("description").getName());
                Integer core_report = (Integer) record.get(stretchy_report.getColumnByName("core_report").getName());
                Integer use_report = (Integer) record.get(stretchy_report.getColumnByName("use_report").getName());
                insert_stretchy_report_tmp(named, stretchy_report_tmp, report_id, report_name, report_type, report_subtype, report_category, report_sql, description, core_report, use_report);
            }
        }
        {
            // sub change 003
            updateLiquibase("V17__update_stretchy_reporting_ddl-003.xml");
        }
        {
            // sub change 004
            dataContext.refreshSchemas();
            Table stretchy_parameter = dataContext.getDefaultSchema().getTableByName("stretchy_parameter");
            Table stretchy_parameter_tmp = dataContext.getDefaultSchema().getTableByName("stretchy_parameter_tmp");
            selectQuery = new SelectQuery(stretchy_parameter.getName());
            for (Map<String, Object> record : named.queryForList(selectQuery.toSQL(), selectQuery.toParam())) {
                Integer parameter_id = (Integer) record.get("parameter_id");
                String parameter_name = (String) record.get("parameter_name");
                String parameter_variable = (String) record.get("parameter_variable");
                String parameter_label = (String) record.get("parameter_label");
                String parameter_displayType = (String) record.get("parameter_displayType");
                String parameter_FormatType = (String) record.get("parameter_FormatType");
                String parameter_default = (String) record.get("parameter_default");
                String special = (String) record.get("special");
                String selectOne = (String) record.get("selectOne");
                String selectAll = (String) record.get("selectAll");
                String parameter_sql = (String) record.get("parameter_sql");
                Integer parent_parameter_id = (Integer) record.get("parent_parameter_id");
                insert_stretchy_parameter_tmp(named, stretchy_parameter_tmp, parameter_id, parameter_name, parameter_variable, parameter_label, parameter_displayType, parameter_FormatType, parameter_default, special, selectOne, selectAll, parameter_sql, parent_parameter_id);
            }
        }
        {
            // sub change 005
            updateLiquibase("V17__update_stretchy_reporting_ddl-005.xml");
        }
        {
            // sub change 006
            dataContext.refreshSchemas();
            Table stretchy_report_parameter = dataContext.getDefaultSchema().getTableByName("stretchy_report_parameter");
            Table stretchy_report_parameter_tmp = dataContext.getDefaultSchema().getTableByName("stretchy_report_parameter_tmp");
            selectQuery = new SelectQuery(stretchy_report_parameter.getName());
            for (Map<String, Object> record : named.queryForList(selectQuery.toSQL(), selectQuery.toParam())) {
                Integer report_id = (Integer) record.get(stretchy_report_parameter.getColumnByName("report_id").getName());
                Integer parameter_id = (Integer) record.get(stretchy_report_parameter.getColumnByName("parameter_id").getName());
                String report_parameter_name = (String) record.get(stretchy_report_parameter.getColumnByName("report_parameter_name").getName());
                insert_stretchy_report_parameter_tmp(named, stretchy_report_parameter_tmp, report_id, parameter_id, report_parameter_name);
            }
        }
        {
            // sub change 007
            updateLiquibase("V17__update_stretchy_reporting_ddl-007.xml");
        }
    }

    protected void insert_stretchy_parameter_tmp(NamedParameterJdbcTemplate named, Table table, Integer id, String parameter_name, String parameter_variable, String parameter_label, String parameter_displayType, String parameter_FormatType, String parameter_default, String special, String selectOne, String selectAll, String parameter_sql, Integer parent_id) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("id").getName(), id);
        insertQuery.addValue(table.getColumnByName("parameter_name").getName(), parameter_name);
        insertQuery.addValue(table.getColumnByName("parameter_variable").getName(), parameter_variable);
        insertQuery.addValue(table.getColumnByName("parameter_label").getName(), parameter_label);
        insertQuery.addValue(table.getColumnByName("parameter_displayType").getName(), parameter_displayType);
        insertQuery.addValue(table.getColumnByName("parameter_FormatType").getName(), parameter_FormatType);
        insertQuery.addValue(table.getColumnByName("parameter_default").getName(), parameter_default);
        insertQuery.addValue(table.getColumnByName("special").getName(), special);
        insertQuery.addValue(table.getColumnByName("selectOne").getName(), selectOne);
        insertQuery.addValue(table.getColumnByName("selectAll").getName(), selectAll);
        insertQuery.addValue(table.getColumnByName("parameter_sql").getName(), parameter_sql);
        insertQuery.addValue(table.getColumnByName("parent_id").getName(), parent_id);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_stretchy_report_tmp(NamedParameterJdbcTemplate named, Table table, Integer id, String report_name, String report_type, String report_subtype, String report_category, String report_sql, String description, Integer core_report, Integer use_report) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("id").getName(), id);
        insertQuery.addValue(table.getColumnByName("report_name").getName(), report_name);
        insertQuery.addValue(table.getColumnByName("report_type").getName(), report_type);
        insertQuery.addValue(table.getColumnByName("report_subtype").getName(), report_subtype);
        insertQuery.addValue(table.getColumnByName("report_category").getName(), report_category);
        insertQuery.addValue(table.getColumnByName("report_sql").getName(), report_sql);
        insertQuery.addValue(table.getColumnByName("description").getName(), description);
        insertQuery.addValue(table.getColumnByName("core_report").getName(), core_report);
        insertQuery.addValue(table.getColumnByName("use_report").getName(), use_report);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_stretchy_report_parameter_tmp(NamedParameterJdbcTemplate named, Table table, Integer report_id, Integer parameter_id, String report_parameter_name) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("report_id").getName(), report_id);
        insertQuery.addValue(table.getColumnByName("parameter_id").getName(), parameter_id);
        insertQuery.addValue(table.getColumnByName("report_parameter_name").getName(), report_parameter_name);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }
}
