package com.angkorteam.fintech.ddl;

public interface MPasswordValidationPolicy {

    public static final String NAME = "m_password_validation_policy";

    public interface Field {

        public static final String ID = "id";

        public static final String REGEX = "regex";

        public static final String DESCRIPTION = "description";

        public static final String ACTIVE = "active";

        public static final String KEY = "key";

    }

}
