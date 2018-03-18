package com.angkorteam.fintech.provider;

import com.angkorteam.fintech.ddl.MSavingsAccount;
import com.angkorteam.fintech.ddl.MSavingsProduct;

public class ClientDepositAccountProvider extends SingleChoiceProvider {

    public ClientDepositAccountProvider() {
        super(MSavingsAccount.NAME, MSavingsAccount.NAME + "." + MSavingsAccount.Field.ID, MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_NO, MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_NO, "CONCAT(" + MSavingsAccount.NAME + "." + MSavingsAccount.Field.ACCOUNT_NO + ", ' [', " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.SHORT_NAME + ", ']')");
        applyJoin("m_savings_product", "INNER JOIN " + MSavingsProduct.NAME + " ON " + MSavingsAccount.NAME + "." + MSavingsAccount.Field.PRODUCT_ID + " = " + MSavingsProduct.NAME + "." + MSavingsProduct.Field.ID);
    }

}