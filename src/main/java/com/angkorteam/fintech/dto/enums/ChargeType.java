package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

/**
 * charge_applies_to_enum
 * 
 * @author socheat
 *
 */
public enum ChargeType {

    Loan("1", "Loan", new ChargeTime[] { ChargeTime.Disbursement, ChargeTime.SpecifiedDueDate, ChargeTime.InstallmentFee, ChargeTime.OverdraftFee, ChargeTime.TrancheDisbursement }, new ChargeCalculation[] { ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount, ChargeCalculation.LoanAmountInterest, ChargeCalculation.Interest }), 
    SavingDeposit("2", "Savings and Deposits", new ChargeTime[] { ChargeTime.SpecifiedDueDate, ChargeTime.SavingsActivation, ChargeTime.WithdrawalFee, ChargeTime.AnnualFee, ChargeTime.MonthlyFee, ChargeTime.WeeklyFee, ChargeTime.OverdraftFee, ChargeTime.SavingNoActivityFee }, new ChargeCalculation[] { ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount }), 
    Client("3", "Client", new ChargeTime[] { ChargeTime.SpecifiedDueDate }, new ChargeCalculation[] { ChargeCalculation.Flat }), 
    Share("4", "Shares", new ChargeTime[] { ChargeTime.ShareAccountActivate, ChargeTime.SharePurchase, ChargeTime.ShareRedeem }, new ChargeCalculation[] { ChargeCalculation.Flat, ChargeCalculation.ApprovedAmount });

    private String literal;

    private String description;

    private ChargeTime[] chargeTime;

    private ChargeCalculation[] chargeCalculation;

    ChargeType(String literal, String description, ChargeTime[] chargeTime, ChargeCalculation[] chargeCalculation) {
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

    public ChargeTime[] getChargeTime() {
        return chargeTime;
    }

    public ChargeCalculation[] getChargeCalculation() {
        return chargeCalculation;
    }

    public static ChargeType parseLiteral(String literal) {
        for (ChargeType value : ChargeType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        ChargeType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
