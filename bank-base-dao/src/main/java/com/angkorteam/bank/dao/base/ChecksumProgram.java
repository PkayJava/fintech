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
        files.add("V1__MifosPlatformCoreDdlLatest");
        files.add("V2__MifosXBaseReferenceDataUTF8");
        files.add("V3__MifosXPermissionsAndAuthorisationUTF8");
        files.add("V4__MifosXCoreReportsUTF8");
        files.add("V7__RemoveReadMakerCheckerPermission");
        files.add("V8__DepositTransactionPermissionsIfTheyExist");
        files.add("V10__InterestPostingFieldsForSavings");
        files.add("V13__AddGroupAndClientPendingConfiguration");
        files.add("V14__RenameStatusIdToEnum");
        files.add("V15__CenterPermissions");
        files.add("V18__UpdateStretchyReportingReportSql");
        files.add("V19__ReportMaintenancePermissions");
        files.add("V20__ReportMaintPermsReallyConfiguration");
        files.add("V21__ActivationPermissionsForClients");
        files.add("V22__AlterGroupForConsistencyAddPermissions");
        files.add("V23__RemoveEnableDisableConfigurationForClientGroupStatus");
        files.add("V25__UpdateClientReportsForStatusAndActivationChange");
        files.add("V32__AssociateDisassociateClientsFromGroupPermissions");
        files.add("V36__AddTagIdColumnForAccGlAccount");
        files.add("V37__AddCenterGroupCollectionSheetPermissions");
        files.add("V38__AddGroupSummaryDetailsReport");
        files.add("V39__PaymentChannelsUpdates");
        files.add("V40__AddPermissionsForAccountingRule");
        files.add("V41__GroupSummaryReports");
        File folder = new File("bank-base-dao/src/main/java/com/angkorteam/bank/dao/base/flyway");

        for (String file : files) {
            HashCode hashCode = Hashing.crc32().hashBytes(FileUtils.readFileToByteArray(new File(folder, file + ".java")));
            System.out.println(file + " -> " + hashCode.asInt());
        }
    }
}
