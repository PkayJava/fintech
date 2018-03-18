package com.angkorteam.fintech.provider;

import com.angkorteam.fintech.ddl.MOrganisationCurrency;

public class CurrencyProvider extends SingleChoiceProvider {

    public CurrencyProvider() {
        super(MOrganisationCurrency.NAME, MOrganisationCurrency.Field.CODE, MOrganisationCurrency.Field.NAME, "CONCAT(" + MOrganisationCurrency.Field.NAME + ",' [', " + MOrganisationCurrency.Field.CODE + ",']')");
    }

}
