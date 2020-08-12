package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.COUNTRY_CODE = this.delegate.getColumnByName("country_code");
        this.TIME_ZONE_NAME = this.delegate.getColumnByName("timezonename");
        this.COMMENT = this.delegate.getColumnByName("comments");
    }

}
