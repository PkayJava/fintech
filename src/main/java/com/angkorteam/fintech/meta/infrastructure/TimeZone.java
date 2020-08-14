package com.angkorteam.fintech.meta.infrastructure;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class TimeZone extends AbstractTable {

    public final Column ID;

    public final Column COUNTRY_CODE;

    public final Column TIME_ZONE_NAME;

    public final Column COMMENT;

    public static TimeZone staticInitialize(DataContext dataContext) {
        return new TimeZone(dataContext);
    }

    private TimeZone(DataContext dataContext) {
        super(dataContext, "timezones");
        this.ID = getInternalColumnByName("id");
        this.COUNTRY_CODE = getInternalColumnByName("country_code");
        this.TIME_ZONE_NAME = getInternalColumnByName("timezonename");
        this.COMMENT = getInternalColumnByName("comments");
    }

}
