package com.angkorteam.fintech.client.function;

import com.angkorteam.fintech.client.dto.*;

public interface PortfolioApi {

    FineractResponse fundCreate(String tenant, String token, PostFundsRequest requestBody);

    FineractResponse fundUpdate(String tenant, String token, long fundId, PutFundsFundIdRequest requestBody);

    FineractResponse paymentTypeCreate(String tenant, String token, PostPaymentTypesRequest requestBody);

    FineractResponse paymentTypeUpdate(String tenant, String token, long paymentTypeId, PutPaymentTypesPaymentTypeIdRequest requestBody);

    FineractResponse paymentTypeDelete(String tenant, String token, long paymentTypeId);

    FineractResponse taxComponentCreate(String tenant, String token, PostTaxesComponentsRequest requestBody);

    FineractResponse taxGroupCreate(String tenant, String token, PostTaxesGroupRequest requestBody);

    FineractResponse floatingRateCreate(String tenant, String token, PostFloatingRatesRequest requestBody);

    FineractResponse chargeCreate(String tenant, String token, PostChargesRequest requestBody);

    FineractResponse clientCreate(String tenant, String token, PostClientRequest requestBody);

    FineractResponse groupCreate(String tenant, String token, PostGroupRequest requestBody);

    FineractResponse centerCreate(String tenant, String token, PostCenterRequest requestBody);

}
