package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MCurrency extends AbstractTable {

    public final Column ID;

    public final Column CODE;

    public final Column DECIMAL_PLACE;

    public final Column CURRENCY_MULTIPLE_OF;

    public final Column DISPLAY_SYMBOL;

    public final Column NAME;

    public final Column INTERNATIONALIZED_NAME_CODE;

    public static MCurrency staticInitialize(DataContext dataContext) {
        return new MCurrency(dataContext);
    }

    private MCurrency(DataContext dataContext) {
        super(dataContext, "m_currency");
        this.ID = getInternalColumnByName("id");
        this.CODE = getInternalColumnByName("code");
        this.DECIMAL_PLACE = getInternalColumnByName("decimal_places");
        this.CURRENCY_MULTIPLE_OF = getInternalColumnByName("currency_multiplesof");
        this.DISPLAY_SYMBOL = getInternalColumnByName("display_symbol");
        this.NAME = getInternalColumnByName("name");
        this.INTERNATIONALIZED_NAME_CODE = getInternalColumnByName("internationalized_name_code");
    }

}
