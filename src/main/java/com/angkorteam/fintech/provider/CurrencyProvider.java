package com.angkorteam.fintech.provider;

public class CurrencyProvider extends SingleChoiceProvider {

    public CurrencyProvider() {
        super("m_organisation_currency", "code", "name", "concat(name,' [', code,']')");
    }

}
