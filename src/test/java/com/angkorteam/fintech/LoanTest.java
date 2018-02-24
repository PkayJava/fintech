package com.angkorteam.fintech;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.Finance;

public class LoanTest {

    private static final DecimalFormat FORMAT = new DecimalFormat("#,###,##0.00");

    public static void main(String[] args) {

        System.out.println(calculateMonthlyPayment(100000, 5, 12));

        double r = 0.12d / 12;
        int nper = 5 * 12;
        double pv = 100000;
        double fv = 0;
        int type = 0;
        double pmt = Finance.pmt(r, nper, pv, fv, type) * -1;
        double remain = 0;
        int space = 15;
        System.out.println(StringUtils.rightPad("No", 4) + StringUtils.rightPad("Installment", space) + StringUtils.rightPad("Principle", space) + StringUtils.rightPad("Interest", space) + StringUtils.rightPad(FORMAT.format(pv - (remain)), space));
        for (int per = 1; per <= nper; per++) {
            double ppmt = Finance.ppmt(r, per, nper, pv, fv, type) * -1;
            remain = remain + ppmt;
            double ipmt = Finance.ipmt(r, per, nper, pv, fv, type) * -1;
            System.out.println(StringUtils.rightPad(String.valueOf(per), 4) + StringUtils.rightPad(FORMAT.format(pmt), space) + StringUtils.rightPad(FORMAT.format(ppmt), space) + StringUtils.rightPad(FORMAT.format(ipmt), space) + StringUtils.rightPad(FORMAT.format(pv - (remain)), space));
        }

        // Amortization : Equal Installment
        // Interest Method : Declining Balance

        // Amortization : Equal Installment
        // Interest Method : Flat Rate

        // Amortization : Equal Principal Payment
        // Interest Method : Declining Balance

        // Amortization : Equal Principal Payment
        // Interest Method : Flat Rate

        // Amortization
        // - Equal Installment
        // - Equal Principal Payment = principle / number of repayment

        // Interest Method
        // - Flat Rate : it is not refresh balance
        // - Declining Balance : it is refresh new interest calculate with new principle
    }

    public static double calculateMonthlyPayment(int loanAmount, int termInYears, double interestRate) {

        // Convert interest rate into a decimal
        // eg. 6.5% = 0.065

        interestRate /= 100.0;

        // Monthly interest rate
        // is the yearly rate divided by 12

        double monthlyRate = interestRate / 12.0;

        // The length of the term in months
        // is the number of years times 12

        int termInMonths = termInYears * 12;

        // Calculate the monthly payment
        // Typically this formula is provided so
        // we won't go into the details

        // The Math.pow() method is used calculate values raised to a power

        double monthlyPayment = (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -termInMonths));

        return monthlyPayment;
    }

}
