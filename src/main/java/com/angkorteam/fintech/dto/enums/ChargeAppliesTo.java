package com.angkorteam.fintech.dto.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

/**
 * charge_applies_to_enum
 * 
 * @author socheat
 *
 */
public enum ChargeAppliesTo {

    Invalid("0", "Loan",null,null),
    Loan("1", "Loan", new ChargeTimeType[] { ChargeTimeType.Disbursement, ChargeTimeType.SpecifiedDueDate, ChargeTimeType.InstallmentFee, ChargeTimeType.OverdraftFee, ChargeTimeType.TrancheDisbursement }, new ChargeCalculationType[] { ChargeCalculationType.Flat, ChargeCalculationType.PercentOfAmount, ChargeCalculationType.PercentOfAmountAndInterest, ChargeCalculationType.PercentOfInterest}),
    SavingDeposit("2", "Savings and Deposits", new ChargeTimeType[] { ChargeTimeType.SpecifiedDueDate, ChargeTimeType.SavingActivation, ChargeTimeType.WithdrawalFee, ChargeTimeType.AnnualFee, ChargeTimeType.MonthlyFee, ChargeTimeType.WeeklyFee, ChargeTimeType.OverdraftFee, ChargeTimeType.SavingNoActivityFee }, new ChargeCalculationType[] { ChargeCalculationType.Flat, ChargeCalculationType.PercentOfAmount}),
    Client("3", "Client", new ChargeTimeType[] { ChargeTimeType.SpecifiedDueDate }, new ChargeCalculationType[] { ChargeCalculationType.Flat }),
    Share("4", "Shares", new ChargeTimeType[] { ChargeTimeType.ShareAccountActivate, ChargeTimeType.SharePurchase, ChargeTimeType.ShareRedeem }, new ChargeCalculationType[] { ChargeCalculationType.Flat, ChargeCalculationType.PercentOfAmount});

    private String literal;

    private String description;

    private ChargeTimeType[] chargeTime;

    private ChargeCalculationType[] chargeCalculation;

    ChargeAppliesTo(String literal, String description, ChargeTimeType[] chargeTime, ChargeCalculationType[] chargeCalculation) {
        this.literal = literal;
        this.description = description;
        this.chargeTime = chargeTime;
        this.chargeCalculation = chargeCalculation;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }

    public Option toOption() {
        return new Option(this.name(), this.description);
    }

    public ChargeTimeType[] getChargeTime() {
        return chargeTime;
    }

    public ChargeCalculationType[] getChargeCalculation() {
        return chargeCalculation;
    }

    public static ChargeAppliesTo parseLiteral(String literal) {
        for (ChargeAppliesTo value : ChargeAppliesTo.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        ChargeAppliesTo value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
