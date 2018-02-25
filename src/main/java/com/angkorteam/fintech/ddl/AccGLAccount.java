package com.angkorteam.fintech.ddl;

public interface AccGLAccount {

    public static final String NAME = "acc_gl_account";

    public interface Field {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String PARENT_ID = "parent_id";

        public static final String HIERARCHY = "hierarchy";

        public static final String GL_CODE = "gl_code";

        public static final String DISABLED = "disabled";

        public static final String MANUAL_JOURNAL_ENTRIES_ALLOWED = "manual_journal_entries_allowed";

        public static final String ACCOUNT_USAGE = "account_usage";

        public static final String CLASSIFICATION_ENUM = "classification_enum";

        public static final String TAG_ID = "tag_id";

        public static final String DESCRIPTION = "description";

    }

}
