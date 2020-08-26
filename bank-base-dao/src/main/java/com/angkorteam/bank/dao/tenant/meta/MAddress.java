package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.STREET = getInternalColumnByName("street");
        this.ADDRESS_LINE_1 = getInternalColumnByName("address_line_1");
        this.ADDRESS_LINE_2 = getInternalColumnByName("address_line_2");
        this.ADDRESS_LINE_3 = getInternalColumnByName("address_line_3");
        this.TOWN_VILLAGE = getInternalColumnByName("town_village");
        this.CITY = getInternalColumnByName("city");
        this.COUNTY_DISTRICT = getInternalColumnByName("county_district");
        this.STATE_PROVINCE_ID = getInternalColumnByName("state_province_id");
        this.COUNTRY_ID = getInternalColumnByName("country_id");
        this.POSTAL_CODE = getInternalColumnByName("postal_code");
        this.LATITUDE = getInternalColumnByName("latitude");
        this.LONGITUDE = getInternalColumnByName("longitude");
        this.CREATED_BY = getInternalColumnByName("created_by");
        this.CREATED_ON = getInternalColumnByName("created_on");
        this.UPDATED_BY = getInternalColumnByName("updated_by");
        this.UPDATED_ON = getInternalColumnByName("updated_on");
    }

}
