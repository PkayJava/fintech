package com.angkorteam.fintech;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.Finance;

public class LoanTest {

    private static final DecimalFormat FORMAT = new DecimalFormat("#,###,##0.00");

    public static void main(String[] args) {
        double r = 0.05d / 12;
        int nper = 5 * 12;
        double pv = 20000;
        double fv = 20000;
        int type = 0;
        double pmt = Finance.pmt(r, nper, pv, fv, type) * -1;
        double remain = 0;
        for (int per = 1; per <= nper; per++) {
            double ppmt = Finance.ppmt(r, per, nper, pv, fv, type) * -1;
            remain = remain + ppmt;
            double ipmt = Finance.ipmt(r, per, nper, pv, fv, type) * -1;
            System.out.println(StringUtils.rightPad(String.valueOf(per), 10) + StringUtils.rightPad(FORMAT.format(pmt), 10) + StringUtils.rightPad(FORMAT.format(ppmt), 10) + StringUtils.rightPad(FORMAT.format(ipmt), 10) + StringUtils.rightPad(FORMAT.format(pv - (remain)), 10));
        }
    }

}
