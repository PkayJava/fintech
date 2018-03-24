package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;

public class MixedBuilder implements Serializable {

    private String loanId;
    private boolean hasLoanId;

    public MixedBuilder withLoanId(String loanId) {
        this.loanId = loanId;
        this.hasLoanId = true;
        return this;
    }

    private List<Long> restrictedProducts = Lists.newArrayList();
    private boolean hasRestrictedProducts;

    public MixedBuilder withRestrictedProduct(Long restrictedProduct) {
        this.restrictedProducts.add(restrictedProduct);
        this.hasRestrictedProducts = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasLoanId) {
            object.getObject().put("loanId", this.loanId);
        }

        if (this.hasRestrictedProducts) {
            object.getObject().put("restrictedProducts", this.restrictedProducts);
        }

        return object;
    }

}
