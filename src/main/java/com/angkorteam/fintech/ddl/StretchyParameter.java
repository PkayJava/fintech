package com.angkorteam.fintech.ddl;

public interface StretchyParameter {

    public static final String NAME = "stretchy_parameter";

    public interface Field {

        public static final String ID = "id";

        public static final String PARAMETER_NAME = "parameter_name";

        public static final String PARAMETER_VARIABLE = "parameter_variable";

        public static final String PARAMETER_LABEL = "parameter_label";

        public static final String PARAMETER_DISPLAY_TYPE = "parameter_displayType";

        public static final String PARAMETER_FORMAT_TYPE = "parameter_FormatType";

        public static final String PARAMETER_DEFAULT = "parameter_default";

        public static final String SPECIAL = "special";

        public static final String SELECT_ONE = "selectOne";

        public static final String SELECT_ALL = "selectAll";

        public static final String PARAMETER_SQL = "parameter_sql";

        public static final String PARENT_ID = "parent_id";

    }

}
