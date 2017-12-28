package com.angkorteam.fintech.dto.builder;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class ShareAccountBuilder {

    private String productId;

    private Date submittedDate;

    private String externalId;

    private Long requestedShares;

    private Long unitPrice;

    private String savingsAccountId;

    private Long minimumActivePeriod;

    private String minimumActivePeriodFrequencyType;

    private String lockinPeriodFrequency;

    private String lockinPeriodFrequencyType;

    private Date applicationDate;

    private Boolean allowDividendCalculationForInactiveClients;

    private List<Map<String, Object>> charges = Lists.newArrayList();

}
