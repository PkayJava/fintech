package com.angkorteam.bank.dao.base;

import com.angkorteam.metamodel.utility.ChecksumUtility;

import java.io.File;
import java.io.IOException;

public class ChecksumProgram {
    public static void main(String[] args) throws IOException {
        File folder = new File("bank-base-dao/src/main/java/com/angkorteam/bank/dao/base/flyway");
        ChecksumUtility.process(folder);
    }
}
