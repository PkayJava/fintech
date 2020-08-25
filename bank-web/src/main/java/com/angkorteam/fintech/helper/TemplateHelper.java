package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/27/17.
 */
public class TemplateHelper {

    public static JsonNode retrieveLoanAccountPrepay(IMifos session, String loanId) {
        return Helper.performServerGetJsonNode(session, "/api/v1/loans/" + loanId + "/transactions/template?command=prepayLoan");
    }

    public static JsonNode retrieveLoanAccountForeclosure(IMifos session, String loanId) {
        return Helper.performServerGetJsonNode(session, "/api/v1/loans/" + loanId + "/transactions/template?command=foreclosure");
    }

}
