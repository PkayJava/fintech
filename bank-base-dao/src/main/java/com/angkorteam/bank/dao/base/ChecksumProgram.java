package com.angkorteam.bank.dao.base;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class ChecksumProgram {
    public static void main(String[] args) throws IOException {
        File folder = new File("bank-base-dao/src/main/java/com/angkorteam/bank/dao/base/flyway");

        Map<Double, String> files = new TreeMap<>();
        int numberSize = 0;
        for (File temp : folder.listFiles()) {
            int i = temp.getName().indexOf("__");
            numberSize = Math.max(temp.getName().substring(1, i).length(), numberSize);
        }
        for (File temp : folder.listFiles()) {
            int i = temp.getName().indexOf("__");
            double ver = Double.parseDouble(temp.getName().substring(1, i).replace('_', '.'));
            files.put(ver, temp.getName());
        }

        for (String file : files.values()) {
            HashCode hashCode = Hashing.crc32().hashBytes(FileUtils.readFileToByteArray(new File(folder, file)));
            System.out.println("int " + FilenameUtils.getBaseName(file) + " = " + Math.abs(hashCode.asInt()) + ";");
        }
    }
}
