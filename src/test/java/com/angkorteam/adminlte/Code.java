package com.angkorteam.adminlte;

public enum Code {
    
    CustomerIdentifier           ("1"),
    LoanCollateral               ("2"),
    LoanPurpose                  ("3"),
    Gender                       ("4"),
    YesNo                        ("5"),
    GuarantorRelationship        ("6"),
    AssetAccountTags             ("7"),
    LiabilityAccountTags         ("8"),
    EquityAccountTags            ("9"),
    IncomeAccountTags            ("10"),
    ExpenseAccountTags           ("11"),
    GROUPROLE                    ("13"),
    ClientClosureReason          ("14"),
    GroupClosureReason           ("15"),
    ClientType                   ("16"),
    ClientClassification         ("17"),
    ClientSubStatus              ("18"),
    ClientRejectReason           ("19"),
    ClientWithdrawReason         ("20"),
    EntitytoEntityAccessTypes    ("21"),
    CenterClosureReason          ("22"),
    LoanRescheduleReason         ("23"),
    Constitution                 ("24"),
    MainBusinessLine             ("25"),
    WriteOffReasons              ("26"),
    STATE                        ("27"),
    COUNTRY                      ("28"),
    ADDRESS_TYPE                 ("29");

    private String id;

    private Code(String id) {
	this.id = id;
    }

    public String getId() {
	return id;
    }

}
