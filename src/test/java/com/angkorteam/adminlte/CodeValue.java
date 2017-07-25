package com.angkorteam.adminlte;

public enum CodeValue {

    Code0001(Code.LoanCollateral, "LoanCollateral01", "Loan Collateral 01"),
    Code0002(Code.LoanCollateral, "LoanCollateral02", "Loan Collateral 02"),
    Code0003(Code.LoanPurpose, "LoanPurpose01", "Loan Purpose 01"),
    Code0004(Code.LoanPurpose, "LoanPurpose02", "Loan Purpose 02"),
    Code0005(Code.Gender, "F", "Female"),
    Code0006(Code.Gender, "M", "Male"),
    Code0007(Code.YesNo, "Y", "Yes"),
    Code0008(Code.YesNo, "N", "No"),
    Code0009(Code.AssetAccountTags, "Asset", "Asset"),
    Code0010(Code.LiabilityAccountTags, "Liability", "Liability"),
    Code0011(Code.EquityAccountTags, "Equity", "Equity"),
    Code0012(Code.IncomeAccountTags, "Income", "Income"),
    Code0013(Code.ExpenseAccountTags, "Expense", "Expense"),
    Code0014(Code.GROUPROLE, "GroupRole01", "Group Role 01"),
    Code0015(Code.GROUPROLE, "GroupRole02", "Group Role 02"),
    Code0016(Code.ClientClosureReason, "ClientClosureReason01", "Client Closure Reason 01"),
    Code0017(Code.ClientClosureReason, "ClientClosureReason02", "Client Closure Reason 02"),
    Code0018(Code.GroupClosureReason, "GroupClosureReason01", "Group Closure Reason 01"),
    Code0019(Code.GroupClosureReason, "GroupClosureReason02", "Group Closure Reason 02"),
    Code0020(Code.ClientType, "ClientType01", "Client Type 01"),
    Code0021(Code.ClientType, "ClientType02", "Client Type 02"),
    Code0022(Code.ClientClassification, "ClientClassification01", "Client Classification 01"),
    Code0023(Code.ClientClassification, "ClientClassification02", "Client Classification 02"),
    Code0024(Code.ClientSubStatus, "ClientSubStatus01", "Client Sub Status 01"),
    Code0025(Code.ClientSubStatus, "ClientSubStatus02", "Client Sub Status 02"),
    Code0026(Code.ClientRejectReason, "ClientRejectReason01", "Client Reject Reason 01"),
    Code0027(Code.ClientRejectReason, "ClientRejectReason02", "Client Reject Reason 02"),
    Code0028(Code.ClientWithdrawReason, "ClientWithdrawReason01", "Client Withdraw Reason 01"),
    Code0029(Code.ClientWithdrawReason, "ClientWithdrawReason02", "Client Withdraw Reason 02"),
    Code0030(Code.CenterClosureReason, "CenterClosureReason01", "Center Closure Reason 01"),
    Code0031(Code.CenterClosureReason, "CenterClosureReason02", "Center Closure Reason 02"),
    Code0032(Code.LoanRescheduleReason, "LoanRescheduleReason01", "Loan Reschedule Reason 01"),
    Code0033(Code.LoanRescheduleReason, "LoanRescheduleReason02", "Loan Reschedule Reason 02"),
    Code0034(Code.Constitution, "Constitution01", "Constitution 01"),
    Code0035(Code.Constitution, "Constitution02", "Constitution 02"),
    Code0036(Code.MainBusinessLine, "MainBusinessLine01", "Main Business Line 01"),
    Code0037(Code.MainBusinessLine, "MainBusinessLine02", "Main Business Line 02"),
    Code0038(Code.WriteOffReasons, "WriteOffReasons01", "Write Off Reasons 01"),
    Code0039(Code.WriteOffReasons, "WriteOffReasons02", "Write Off Reasons 02"),
    Code0040(Code.STATE, "State01", "State 01"),
    Code0041(Code.STATE, "State02", "State 02"),
    Code0042(Code.COUNTRY, "Country01", "Country 01"),
    Code0043(Code.COUNTRY, "Country01", "Country 02"),
    Code0044(Code.ADDRESS_TYPE, "AddressType01", "Address Type 01"),
    Code0045(Code.ADDRESS_TYPE, "AddressType01", "Address Type 02");
    
    private Code code;

    private String value;

    private String description;

    private CodeValue(Code code, String value, String description) {
	this.code = code;
	this.value = value;
	this.description = description;
    }

    public Code getCode() {
	return code;
    }

    public String getValue() {
	return value;
    }

    public String getDescription() {
	return description;
    }

}
