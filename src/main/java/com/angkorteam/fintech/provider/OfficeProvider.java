package com.angkorteam.fintech.provider;

import com.angkorteam.fintech.ddl.MOffice;

public class OfficeProvider extends SingleChoiceProvider {

    public OfficeProvider() {
        super(MOffice.NAME, MOffice.Field.ID, MOffice.Field.NAME);
    }

}
