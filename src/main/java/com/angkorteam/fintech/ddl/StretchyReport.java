package com.angkorteam.fintech.ddl;

public interface StretchyReport {

    public static final String NAME = "stretchy_report";

    public interface Field {

        public static final String ID = "id";

        public static final String REPORT_NAME = "report_name";

        public static final String REPORT_TYPE = "report_type";

        public static final String REPORT_SUB_TYPE = "report_subtype";

        public static final String REPORT_CATEGORY = "report_category";

        public static final String REPORT_SQL = "report_sql";

        public static final String DESCRIPTION = "description";

        public static final String CORE_REPORT = "core_report";

        public static final String USE_REPORT = "use_report";

    }

}
