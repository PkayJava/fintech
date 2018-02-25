package com.angkorteam.fintech.ddl;

public interface SchemaVersion {

    public static final String NAME = "schema_version";

    public interface Field {

        public static final String VERSION_RANK = "version_rank";

        public static final String INSTALLED_RANK = "installed_rank";

        public static final String VERSION = "version";

        public static final String DESCRIPTION = "description";

        public static final String TYPE = "type";

        public static final String SCRIPT = "script";

        public static final String CHECKSUM = "checksum";

        public static final String INSTALLED_BY = "installed_by";

        public static final String INSTALLED_ON = "installed_on";

        public static final String EXECUTION_TIME = "execution_time";

        public static final String SUCCESS = "success";

    }

}
