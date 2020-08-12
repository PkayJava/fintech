package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.CODE = this.delegate.getColumnByName("code");
        this.DECIMAL_PLACE = this.delegate.getColumnByName("decimal_places");
        this.CURRENCY_MULTIPLE_OF = this.delegate.getColumnByName("currency_multiplesof");
        this.DISPLAY_SYMBOL = this.delegate.getColumnByName("display_symbol");
        this.NAME = this.delegate.getColumnByName("name");
        this.INTERNATIONALIZED_NAME_CODE = this.delegate.getColumnByName("internationalized_name_code");
    }

}
