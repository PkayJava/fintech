package com.angkorteam.bank.dao.base;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChecksumProgram {
    public static void main(String[] args) throws IOException {
        List<String> files = new ArrayList<>();
        files.add("V15__CenterPermissions.java");
        File folder = new File("bank-tenant-dao/src/main/java/com/angkorteam/bank/dao/tenant/flyway");
        int checksum = 0;
        for (String file : files) {
            HashCode hashCode = Hashing.crc32().hashBytes(FileUtils.readFileToByteArray(new File(folder, file)));
            checksum = checksum + hashCode.asInt();
        }
        System.out.println(checksum);
    }
}
