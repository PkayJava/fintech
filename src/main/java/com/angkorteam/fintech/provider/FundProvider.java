package com.angkorteam.fintech.provider;

import com.angkorteam.fintech.ddl.MFund;

public class FundProvider extends SingleChoiceProvider {

    public FundProvider() {
        super(MFund.NAME, MFund.Field.ID, MFund.Field.NAME);
    }

}
