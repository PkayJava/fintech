package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.database.Database;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V17__UpdateStretchyReportingDdl extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return getInternalChecksum(
                "V17__update_stretchy_reporting_ddl-001.xml",
                "V17__update_stretchy_reporting_ddl-003.xml",
                "V17__update_stretchy_reporting_ddl-005.xml",
                "V17__update_stretchy_reporting_ddl-007.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            // sub change 001
            updateLiquibase(database, "V17__update_stretchy_reporting_ddl-001.xml");
        }
        {
            // sub change 002
            dataContext.refreshSchemas();
            Table stretchy_report = dataContext.getDefaultSchema().getTableByName("stretchy_report");
            Table stretchy_report_tmp = dataContext.getDefaultSchema().getTableByName("stretchy_report_tmp");
            try (DataSet rows = dataContext.query().from(stretchy_report).selectAll().execute()) {
                while (rows.next()) {
                    dataContext.executeUpdate(callback -> {
                        callback.insertInto(stretchy_report_tmp)
                                .value(stretchy_report_tmp.getColumnByName("id"), rows.getRow().getValue(stretchy_report.getColumnByName("report_id")))
                                .value(stretchy_report_tmp.getColumnByName("report_name"), rows.getRow().getValue(stretchy_report.getColumnByName("report_name")))
                                .value(stretchy_report_tmp.getColumnByName("report_type"), rows.getRow().getValue(stretchy_report.getColumnByName("report_type")))
                                .value(stretchy_report_tmp.getColumnByName("report_subtype"), rows.getRow().getValue(stretchy_report.getColumnByName("report_subtype")))
                                .value(stretchy_report_tmp.getColumnByName("report_category"), rows.getRow().getValue(stretchy_report.getColumnByName("report_category")))
                                .value(stretchy_report_tmp.getColumnByName("report_sql"), rows.getRow().getValue(stretchy_report.getColumnByName("report_sql")))
                                .value(stretchy_report_tmp.getColumnByName("description"), rows.getRow().getValue(stretchy_report.getColumnByName("description")))
                                .value(stretchy_report_tmp.getColumnByName("core_report"), rows.getRow().getValue(stretchy_report.getColumnByName("core_report")))
                                .value(stretchy_report_tmp.getColumnByName("use_report"), rows.getRow().getValue(stretchy_report.getColumnByName("use_report")))
                                .execute();
                    });
                }
            }
        }
        {
            // sub change 003
            updateLiquibase(database, "V17__update_stretchy_reporting_ddl-003.xml");
        }
        {
            // sub change 004
            dataContext.refreshSchemas();
            Table stretchy_parameter = dataContext.getDefaultSchema().getTableByName("stretchy_parameter");
            Table stretchy_parameter_tmp = dataContext.getDefaultSchema().getTableByName("stretchy_parameter_tmp");
            try (DataSet rows = dataContext.query().from(stretchy_parameter).selectAll().execute()) {
                while (rows.next()) {
                    dataContext.executeUpdate(callback -> {
                        callback.insertInto(stretchy_parameter_tmp)
                                .value(stretchy_parameter_tmp.getColumnByName("id"), rows.getRow().getValue(stretchy_parameter.getColumnByName("parameter_id")))
                                .value(stretchy_parameter_tmp.getColumnByName("parameter_name"), rows.getRow().getValue(stretchy_parameter.getColumnByName("parameter_name")))
                                .value(stretchy_parameter_tmp.getColumnByName("parameter_variable"), rows.getRow().getValue(stretchy_parameter.getColumnByName("parameter_variable")))
                                .value(stretchy_parameter_tmp.getColumnByName("parameter_label"), rows.getRow().getValue(stretchy_parameter.getColumnByName("parameter_label")))
                                .value(stretchy_parameter_tmp.getColumnByName("parameter_displayType"), rows.getRow().getValue(stretchy_parameter.getColumnByName("parameter_displayType")))
                                .value(stretchy_parameter_tmp.getColumnByName("parameter_FormatType"), rows.getRow().getValue(stretchy_parameter.getColumnByName("parameter_FormatType")))
                                .value(stretchy_parameter_tmp.getColumnByName("parameter_default"), rows.getRow().getValue(stretchy_parameter.getColumnByName("parameter_default")))
                                .value(stretchy_parameter_tmp.getColumnByName("special"), rows.getRow().getValue(stretchy_parameter.getColumnByName("special")))
                                .value(stretchy_parameter_tmp.getColumnByName("selectOne"), rows.getRow().getValue(stretchy_parameter.getColumnByName("selectOne")))
                                .value(stretchy_parameter_tmp.getColumnByName("selectAll"), rows.getRow().getValue(stretchy_parameter.getColumnByName("selectAll")))
                                .value(stretchy_parameter_tmp.getColumnByName("parameter_sql"), rows.getRow().getValue(stretchy_parameter.getColumnByName("parameter_sql")))
                                .value(stretchy_parameter_tmp.getColumnByName("parent_id"), rows.getRow().getValue(stretchy_parameter.getColumnByName("parent_parameter_id")))
                                .execute();
                    });
                }
            }
        }
        {
            // sub change 005
            updateLiquibase(database, "V17__update_stretchy_reporting_ddl-005.xml");
        }
        {
            // sub change 006
            dataContext.refreshSchemas();
            Table stretchy_report_parameter = dataContext.getDefaultSchema().getTableByName("stretchy_report_parameter");
            Table stretchy_report_parameter_tmp = dataContext.getDefaultSchema().getTableByName("stretchy_report_parameter_tmp");
            try (DataSet rows = dataContext.query().from(stretchy_report_parameter).selectAll().execute()) {
                while (rows.next()) {
                    dataContext.executeUpdate(callback -> {
                        callback.insertInto(stretchy_report_parameter_tmp)
                                .value(stretchy_report_parameter_tmp.getColumnByName("report_id"), rows.getRow().getValue(stretchy_report_parameter.getColumnByName("report_id")))
                                .value(stretchy_report_parameter_tmp.getColumnByName("parameter_id"), rows.getRow().getValue(stretchy_report_parameter.getColumnByName("parameter_id")))
                                .value(stretchy_report_parameter_tmp.getColumnByName("report_parameter_name"), rows.getRow().getValue(stretchy_report_parameter.getColumnByName("report_parameter_name")))
                                .execute();
                    });
                }
            }
        }
        {
            // sub change 007
            updateLiquibase(database, "V17__update_stretchy_reporting_ddl-007.xml");
        }
    }

}
