package com.angkorteam.bank.dao.tenant;

import com.angkorteam.metamodel.utility.ChecksumUtility;

import java.io.File;
import java.io.IOException;

public class ChecksumProgram {
    public static void main(String[] args) throws IOException {
        File folder = new File("bank-tenant-dao/src/main/java/com/angkorteam/bank/dao/tenant/flyway");
        ChecksumUtility.process(folder);
    }
}
