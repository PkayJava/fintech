package org.apache.metamodel.jdbc;

import java.lang.reflect.Field;
import java.util.Set;

public class PublicSqlKeywords extends SqlKeywords {

    static {
        try {
            Field field = SqlKeywords.class.getDeclaredField("KEYWORDS");
            field.setAccessible(true);
            Set<String> KEYWORDS = (Set<String>) field.get(null);
            KEYWORDS.add("GROUPING");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public PublicSqlKeywords() {
    }

}
