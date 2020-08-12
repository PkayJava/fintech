package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MAddress extends AbstractTable {

    public final Column ID;

    public final Column STREET;

    public final Column ADDRESS_LINE_1;

    public final Column ADDRESS_LINE_2;

    public final Column ADDRESS_LINE_3;

    public final Column TOWN_VILLAGE;

    public final Column CITY;

    public final Column COUNTY_DISTRICT;

    public final Column STATE_PROVINCE_ID;

    public final Column COUNTRY_ID;

    public final Column POSTAL_CODE;

    public final Column LATITUDE;

    public final Column LONGITUDE;

    public final Column CREATED_BY;

    public final Column CREATED_ON;

    public final Column UPDATED_BY;

    public final Column UPDATED_ON;

    public static MAddress staticInitialize(DataContext dataContext) {
        return new MAddress(dataContext);
    }

    private MAddress(DataContext dataContext) {
        super(dataContext, "m_address");
        this.ID = this.delegate.getColumnByName("id");
        this.STREET = this.delegate.getColumnByName("street");
        this.ADDRESS_LINE_1 = this.delegate.getColumnByName("address_line_1");
        this.ADDRESS_LINE_2 = this.delegate.getColumnByName("address_line_2");
        this.ADDRESS_LINE_3 = this.delegate.getColumnByName("address_line_3");
        this.TOWN_VILLAGE = this.delegate.getColumnByName("town_village");
        this.CITY = this.delegate.getColumnByName("city");
        this.COUNTY_DISTRICT = this.delegate.getColumnByName("county_district");
        this.STATE_PROVINCE_ID = this.delegate.getColumnByName("state_province_id");
        this.COUNTRY_ID = this.delegate.getColumnByName("country_id");
        this.POSTAL_CODE = this.delegate.getColumnByName("postal_code");
        this.LATITUDE = this.delegate.getColumnByName("latitude");
        this.LONGITUDE = this.delegate.getColumnByName("longitude");
        this.CREATED_BY = this.delegate.getColumnByName("created_by");
        this.CREATED_ON = this.delegate.getColumnByName("created_on");
        this.UPDATED_BY = this.delegate.getColumnByName("updated_by");
        this.UPDATED_ON = this.delegate.getColumnByName("updated_on");
    }

}
