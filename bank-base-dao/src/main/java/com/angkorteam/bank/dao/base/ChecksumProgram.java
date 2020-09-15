package com.angkorteam.bank.dao.base;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

public class ChecksumProgram {
    public static void main(String[] args) throws IOException {
        File folder = new File("bank-base-dao/src/main/java/com/angkorteam/bank/dao/base/flyway");

        Map<String, String> files = new TreeMap<>();
        int numberSize = 0;
        for (File temp : folder.listFiles()) {
            int i = temp.getName().indexOf('_');
            numberSize = Math.max(temp.getName().substring(1, i).length(), numberSize);
        }
        String zero = StringUtils.repeat("0", numberSize);
        DecimalFormat format = new DecimalFormat(zero);
        for (File temp : folder.listFiles()) {
            int i = temp.getName().indexOf('_');
            int ver = Integer.valueOf(temp.getName().substring(1, i));
            files.put(format.format(ver), temp.getName());
        }

        for (String file : files.values()) {
            HashCode hashCode = Hashing.crc32().hashBytes(FileUtils.readFileToByteArray(new File(folder, file)));
            System.out.println("int " + FilenameUtils.getBaseName(file) + " = " + Math.abs(hashCode.asInt()) + ";");
        }
    }
}
