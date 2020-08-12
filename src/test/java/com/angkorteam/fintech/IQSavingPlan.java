package com.angkorteam.fintech;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IQSavingPlan {

    public static void main(String[] args) throws IOException, ParseException {
        NumberFormat format = new DecimalFormat("0.00");
        NumberFormat format1 = new DecimalFormat("00");
        List<Integer> operators = new ArrayList<>();
//        operators.add(48);
//        operators.add(36);
//        operators.add(30);
//        operators.add(24);
//        operators.add(18);
//        operators.add(12);
//        operators.add(9);
//        operators.add(6);
//        operators.add(3);
        operators.add(1);
        List<Integer> loop = new ArrayList<>();
        loop.addAll(operators);
        double saving = 1000d;
        for (Integer op : loop) {
            double minPrinciple = 0d;
            double principle = saving;
            List<TermDeposit> deposits = new ArrayList<>();
            java.time.LocalDate currentDate = java.time.LocalDate.of(2020, 1, 1);
            java.time.LocalDate maxDate = java.time.LocalDate.of(currentDate.getYear() + 4, 12, 31);
            int month = 0;
            double basePrinciple = 0;
            while (true) {
                if (principle > 0) {
                    if (minPrinciple == 0) {
                        TermDeposit deposit = invest(operators, currentDate, maxDate, principle);
                        if (deposit != null) {
                            deposits.add(deposit);
                            principle = 0;
                        }
                    } else {
                        double invalid = 0;
                        while (principle >= minPrinciple) {
                            TermDeposit deposit = invest(operators, currentDate, maxDate, minPrinciple);
                            if (deposit != null) {
                                deposits.add(deposit);
                            } else {
                                invalid = invalid + minPrinciple;
                            }
                            principle = principle - minPrinciple;
                        }
                        principle = principle + invalid;
                    }
                }
                if (currentDate.getDayOfMonth() == (currentDate.isLeapYear() ? currentDate.getMonth().maxLength() : currentDate.getMonth().minLength())) {
                    principle = principle + collectInterest(deposits, currentDate);
                    principle = principle + saving;
                    basePrinciple = basePrinciple + saving;
                    month = month + 1;
                }
                currentDate = currentDate.plusDays(1);
                if (currentDate.isAfter(maxDate)) {
                    break;
                }
            }
            int pad = 10;
            System.out.println(format1.format(op) + " month " + format1.format(month) + " - principle " + StringUtils.leftPad(format.format(basePrinciple), pad) + " total " + StringUtils.leftPad(format.format(principle), pad) + " revenue " + StringUtils.leftPad(format.format(principle - basePrinciple), pad));
            operators.remove(op);
        }
    }

    public static double collectInterest(List<TermDeposit> deposits, java.time.LocalDate currentDate) {
        double principle = 0;
        for (TermDeposit deposit : deposits) {
            if (currentDate.isBefore(deposit.maturityDate) || currentDate.isEqual(deposit.maturityDate)) {
                double newPrinciple = deposit.getInterest(currentDate);
                principle = principle + newPrinciple;
                if (currentDate.isEqual(deposit.maturityDate)) {
                    principle = principle + deposit.principle;
                }
            }
        }
        return principle;
    }

    public static TermDeposit invest(List<Integer> operators, java.time.LocalDate currentDate, java.time.LocalDate maxDate, double principle) {
        if (operators.contains(48) && (currentDate.plusMonths(48).minusDays(1).isBefore(maxDate) || currentDate.plusMonths(48).minusDays(1).equals(maxDate))) {
            return new TermDeposit(7.25, principle, currentDate.plusMonths(48).minusDays(1), true);
        }
        if (operators.contains(36) && currentDate.plusMonths(36).minusDays(1).isBefore(maxDate) || currentDate.plusMonths(36).minusDays(1).equals(maxDate)) {
            return new TermDeposit(7.25, principle, currentDate.plusMonths(36).minusDays(1), true);
        }
        if (operators.contains(30) && currentDate.plusMonths(30).minusDays(1).isBefore(maxDate) || currentDate.plusMonths(30).minusDays(1).equals(maxDate)) {
            return new TermDeposit(7.25, principle, currentDate.plusMonths(30).minusDays(1), true);
        }
        if (operators.contains(24) && currentDate.plusMonths(24).minusDays(1).isBefore(maxDate) || currentDate.plusMonths(24).minusDays(1).equals(maxDate)) {
            return new TermDeposit(7.25, principle, currentDate.plusMonths(24).minusDays(1), true);
        }
        if (operators.contains(18) && currentDate.plusMonths(18).minusDays(1).isBefore(maxDate) || currentDate.plusMonths(18).minusDays(1).equals(maxDate)) {
            return new TermDeposit(7.00, principle, currentDate.plusMonths(18).minusDays(1), true);
        }
        if (operators.contains(12) && currentDate.plusMonths(12).minusDays(1).isBefore(maxDate) || currentDate.plusMonths(12).minusDays(1).equals(maxDate)) {
            return new TermDeposit(7.00, principle, currentDate.plusMonths(12).minusDays(1), true);
        }
        if (operators.contains(9) && currentDate.plusMonths(9).minusDays(1).isBefore(maxDate) || currentDate.plusMonths(9).minusDays(1).equals(maxDate)) {
            return new TermDeposit(6.00, principle, currentDate.plusMonths(9).minusDays(1), true);
        }
        if (operators.contains(6) && currentDate.plusMonths(6).minusDays(1).isBefore(maxDate) || currentDate.plusMonths(6).minusDays(1).equals(maxDate)) {
            return new TermDeposit(5.50, principle, currentDate.plusMonths(6).minusDays(1), true);
        }
        if (operators.contains(3) && currentDate.plusMonths(3).minusDays(1).isBefore(maxDate) || currentDate.plusMonths(3).minusDays(1).equals(maxDate)) {
            return new TermDeposit(4.50, principle, currentDate.plusMonths(3).minusDays(1), true);
        }
        if (operators.contains(1) && currentDate.plusMonths(1).minusDays(1).isBefore(maxDate) || currentDate.plusMonths(1).minusDays(1).equals(maxDate)) {
            return new TermDeposit(3.50, principle, currentDate.plusMonths(1).minusDays(1), true);
        }
        return null;
    }

    public static class TermDeposit {

        private double interest;
        private double principle;
        private java.time.LocalDate maturityDate;
        private boolean interestAtMaturity;
        private double block = 0;

        public TermDeposit(double interest, double principle, LocalDate maturityDate, boolean interestAtMaturity) {
            this.interest = interest;
            this.principle = principle;
            this.maturityDate = LocalDate.of(maturityDate.getYear(), maturityDate.getMonthValue(), maturityDate.getDayOfMonth());
            this.interestAtMaturity = interestAtMaturity;
        }

        public double getInterest(LocalDate currentDate) {
            if (this.interestAtMaturity) {
                if (currentDate.equals(this.maturityDate)) {
                    return principle * (interest / 100 / 12) + block;
                } else {
                    this.block = block + (interest / 100 / 12);
                    return 0;
                }
            } else {
                return principle * (interest / 100 / 12);
            }
        }

    }

}
